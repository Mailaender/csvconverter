/*******************************************************************************
 * Copyright (c) 2011, 2012 Philip (eselmeister) Wenig.
 * 
 * All rights reserved.
 *******************************************************************************/
package net.openchrom.chromatogram.msd.converter.supplier.csv.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.supercsv.io.CsvListReader;
import org.supercsv.io.ICsvListReader;
import org.supercsv.prefs.CsvPreference;

import net.openchrom.chromatogram.converter.exceptions.FileIsEmptyException;
import net.openchrom.chromatogram.converter.exceptions.FileIsNotReadableException;
import net.openchrom.chromatogram.model.core.IChromatogramOverview;
import net.openchrom.chromatogram.msd.converter.io.IChromatogramReader;
import net.openchrom.chromatogram.msd.converter.supplier.csv.preferences.BundleProductPreferences;
import net.openchrom.chromatogram.msd.converter.supplier.csv.model.CSVChromatogram;
import net.openchrom.chromatogram.msd.converter.supplier.csv.model.CSVIon;
import net.openchrom.chromatogram.msd.converter.supplier.csv.model.CSVMassSpectrum;
import net.openchrom.chromatogram.msd.model.core.AbstractIon;
import net.openchrom.chromatogram.msd.model.core.IChromatogramMSD;
import net.openchrom.chromatogram.msd.model.core.IIon;
import net.openchrom.chromatogram.msd.model.core.ISupplierMassSpectrum;
import net.openchrom.chromatogram.msd.model.exceptions.AbundanceLimitExceededException;
import net.openchrom.chromatogram.msd.model.exceptions.IonLimitExceededException;
import net.openchrom.logging.core.Logger;

/**
 * This class is responsible to read a Agilent Chromatogram from its binary
 * file.
 * 
 * @author eselmeister
 */
public class ChromatogramReader implements IChromatogramReader {

	private static final Logger logger = Logger.getLogger(ChromatogramReader.class);
	private static final String ZERO_VALUE = "0.0";
	private static final int Ion_COLUMN_START = 3;

	public ChromatogramReader() {

	}

	@Override
	public IChromatogramMSD read(File file, IProgressMonitor monitor) throws FileNotFoundException, FileIsNotReadableException, FileIsEmptyException, IOException {

		if(isValidFileFormat(file)) {
			return readChromatogram(file, false);
		}
		return null;
	}

	@Override
	public IChromatogramOverview readOverview(File file, IProgressMonitor monitor) throws FileNotFoundException, FileIsNotReadableException, FileIsEmptyException, IOException {

		if(isValidFileFormat(file)) {
			return readChromatogram(file, true);
		}
		return null;
	}

	private boolean isValidFileFormat(File file) throws IOException {

		FileReader reader = new FileReader(file);
		ICsvListReader csvListReader = new CsvListReader(reader, CsvPreference.EXCEL_NORTH_EUROPE_PREFERENCE);
		String[] header = csvListReader.getCSVHeader(true);
		/*
		 * Check the first column header.
		 */
		String firstColumn = header[0];
		csvListReader.close();
		return firstColumn.equals(ChromatogramWriter.RT_MILLISECONDS_COLUMN);
	}

	private IChromatogramMSD readChromatogram(File file, boolean overview) throws IOException {

		FileReader reader = new FileReader(file);
		ICsvListReader csvListReader = new CsvListReader(reader, CsvPreference.EXCEL_NORTH_EUROPE_PREFERENCE);
		IChromatogramMSD chromatogram = new CSVChromatogram();
		if(!overview) {
			/*
			 * If the chromatogram shall be exportable, set the id otherwise it is null or "".
			 */
			chromatogram.setConverterId(BundleProductPreferences.CONVERTER_ID);
			chromatogram.setFile(file);
		}
		/*
		 * Get the header inclusive ion description.
		 */
		String[] header = csvListReader.getCSVHeader(true);
		Map<Integer, Float> ionsMap = getIonMap(header);
		List<String> lineEntries;
		while((lineEntries = csvListReader.read()) != null) {
			ISupplierMassSpectrum supplierMassSpectrum = getScan(lineEntries, ionsMap, overview);
			/*
			 * TODO setParentMassSpectrum automatisch beim Hinzufügen?
			 */
			supplierMassSpectrum.setParentChromatogram(chromatogram);
			chromatogram.addScan(supplierMassSpectrum);
		}
		int scanDelay = chromatogram.getScan(1).getRetentionTime();
		chromatogram.setScanDelay(scanDelay);
		return chromatogram;
	}

	private Map<Integer, Float> getIonMap(String[] header) {

		Map<Integer, Float> ions = new HashMap<Integer, Float>();
		for(int index = 3; index < header.length; index++) {
			float ion = Float.valueOf(header[index]);
			ions.put(index, ion);
		}
		return ions;
	}

	private ISupplierMassSpectrum getScan(List<String> lineEntries, Map<Integer, Float> ionsMap, boolean overview) {

		ISupplierMassSpectrum massSpectrum = new CSVMassSpectrum();
		String retentionTimeInMilliseconds = lineEntries.get(0);
		int retentionTime = Integer.valueOf(retentionTimeInMilliseconds);
		massSpectrum.setRetentionTime(retentionTime);
		/*
		 * The retention time in minutes will be not used.
		 */
		// String retentionTimeInMinutes = lineEntries.get(1);
		String retentionIndexValue = lineEntries.get(2);
		float retentionIndex = Float.valueOf(retentionIndexValue);
		massSpectrum.setRetentionIndex(retentionIndex);
		if(overview) {
			try {
				IIon ion = getIonsOverview(lineEntries);
				massSpectrum.addIon(ion);
			} catch(AbundanceLimitExceededException e) {
				logger.warn(e);
			} catch(IonLimitExceededException e) {
				logger.warn(e);
			}
		} else {
			List<IIon> ions = getIons(lineEntries, ionsMap);
			for(IIon ion : ions) {
				massSpectrum.addIon(ion);
			}
		}
		return massSpectrum;
	}

	private IIon getIonsOverview(List<String> lineEntries) throws AbundanceLimitExceededException, IonLimitExceededException {

		float abundanceTotalSignal = 0.0f;
		for(int index = Ion_COLUMN_START; index < lineEntries.size(); index++) {
			String abundanceValue = lineEntries.get(index);
			if(!abundanceValue.equals(ZERO_VALUE)) {
				float abundance = Float.valueOf(abundanceValue);
				abundanceTotalSignal += abundance;
			}
		}
		IIon ion = new CSVIon(AbstractIon.TIC_ION, abundanceTotalSignal);
		return ion;
	}

	private List<IIon> getIons(List<String> lineEntries, Map<Integer, Float> ionsMap) {

		List<IIon> ions = new ArrayList<IIon>();
		for(int index = Ion_COLUMN_START; index < lineEntries.size(); index++) {
			String abundanceValue = lineEntries.get(index);
			if(!abundanceValue.equals(ZERO_VALUE)) {
				float abundance = Float.valueOf(abundanceValue);
				float ion = ionsMap.get(index);
				try {
					IIon csvIon = new CSVIon(ion, abundance);
					ions.add(csvIon);
				} catch(AbundanceLimitExceededException e) {
					logger.warn(e);
				} catch(IonLimitExceededException e) {
					logger.warn(e);
				}
			}
		}
		return ions;
	}
}
