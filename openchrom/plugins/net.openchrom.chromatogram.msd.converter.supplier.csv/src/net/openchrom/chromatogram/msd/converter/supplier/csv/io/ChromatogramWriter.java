/*******************************************************************************
 * Copyright (c) 2011, 2014 Philip (eselmeister) Wenig.
 * 
 * All rights reserved.
 *******************************************************************************/
package net.openchrom.chromatogram.msd.converter.supplier.csv.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.supercsv.io.CsvListWriter;
import org.supercsv.io.ICsvListWriter;
import org.supercsv.prefs.CsvPreference;

import net.chemclipse.chromatogram.converter.exceptions.FileIsNotWriteableException;
import net.chemclipse.chromatogram.model.exceptions.ChromatogramIsNullException;
import net.chemclipse.chromatogram.model.signals.ITotalScanSignal;
import net.chemclipse.chromatogram.model.signals.ITotalScanSignals;
import net.chemclipse.chromatogram.msd.converter.io.IChromatogramMSDWriter;
import net.chemclipse.chromatogram.msd.model.core.IChromatogramMSD;
import net.chemclipse.chromatogram.msd.model.xic.ExtractedIonSignalExtractor;
import net.chemclipse.chromatogram.msd.model.xic.IExtractedIonSignal;
import net.chemclipse.chromatogram.msd.model.xic.IExtractedIonSignalExtractor;
import net.chemclipse.chromatogram.msd.model.xic.IExtractedIonSignals;
import net.chemclipse.chromatogram.msd.model.xic.ITotalIonSignalExtractor;
import net.chemclipse.chromatogram.msd.model.xic.TotalIonSignalExtractor;
import net.openchrom.chromatogram.msd.converter.supplier.csv.preferences.ConverterPreferences;

public class ChromatogramWriter implements IChromatogramMSDWriter {

	public static final String RT_MILLISECONDS_COLUMN = "RT(milliseconds)";
	public static final String RT_MINUTES_COLUMN = "RT(minutes) - NOT USED BY IMPORT";
	public static final String RI_COLUMN = "RI";
	public static final String TIC_COLUMN = "TIC";

	public ChromatogramWriter() {

	}

	@Override
	public void writeChromatogram(File file, IChromatogramMSD chromatogram, IProgressMonitor monitor) throws FileNotFoundException, FileIsNotWriteableException, IOException {

		/*
		 * Create the list writer.
		 */
		FileWriter writer = new FileWriter(file);
		ICsvListWriter csvListWriter = new CsvListWriter(writer, CsvPreference.EXCEL_NORTH_EUROPE_PREFERENCE);
		/*
		 * Get the chromatographic data.
		 */
		try {
			/*
			 * TIC / XIC
			 */
			if(ConverterPreferences.isUseTic()) {
				ITotalIonSignalExtractor totalIonSignalExtractor = new TotalIonSignalExtractor(chromatogram);
				ITotalScanSignals totalScanSignals = totalIonSignalExtractor.getTotalScanSignals();
				writeHeaderTIC(csvListWriter);
				writeScansTIC(csvListWriter, totalScanSignals);
			} else {
				IExtractedIonSignalExtractor extractedIonSignalExtractor = new ExtractedIonSignalExtractor(chromatogram);
				IExtractedIonSignals extractedIonSignals = extractedIonSignalExtractor.getExtractedIonSignals();
				int startIon = extractedIonSignals.getStartIon();
				int stopIon = extractedIonSignals.getStopIon();
				writeHeaderXIC(csvListWriter, startIon, stopIon);
				writeScansXIC(csvListWriter, extractedIonSignals, startIon, stopIon);
			}
		} catch(ChromatogramIsNullException e) {
			throw new IOException("The chromatogram is null.");
		} finally {
			csvListWriter.close();
		}
	}

	private void writeHeaderTIC(ICsvListWriter csvListWriter) throws IOException {

		/*
		 * Write the header.
		 * RT(milliseconds), RT(minutes) - NOT USED BY IMPORT, RI, ion 18, ...
		 */
		List<String> headerList = new ArrayList<String>();
		headerList.add(RT_MILLISECONDS_COLUMN);
		headerList.add(RT_MINUTES_COLUMN);
		headerList.add(RI_COLUMN);
		headerList.add(TIC_COLUMN);
		String[] header = headerList.toArray(new String[]{});
		csvListWriter.writeHeader(header);
	}

	private void writeHeaderXIC(ICsvListWriter csvListWriter, int startIon, int stopIon) throws IOException {

		/*
		 * Write the header.
		 * RT(milliseconds), RT(minutes) - NOT USED BY IMPORT, RI, ion 18, ...
		 */
		List<String> headerList = new ArrayList<String>();
		headerList.add(RT_MILLISECONDS_COLUMN);
		headerList.add(RT_MINUTES_COLUMN);
		headerList.add(RI_COLUMN);
		for(Integer ion = startIon; ion <= stopIon; ion++) {
			headerList.add(ion.toString());
		}
		String[] header = headerList.toArray(new String[]{});
		csvListWriter.writeHeader(header);
	}

	private void writeScansTIC(ICsvListWriter csvListWriter, ITotalScanSignals totalScanSignals) throws IOException {

		/*
		 * Write the data.
		 */
		List<Number> scanValues;
		for(ITotalScanSignal totalScanSignal : totalScanSignals.getTotalIonSignals()) {
			scanValues = new ArrayList<Number>();
			/*
			 * RT (milliseconds)
			 * RT(minutes)
			 * RI
			 */
			int milliseconds = totalScanSignal.getRetentionTime();
			scanValues.add(milliseconds);
			scanValues.add(milliseconds / IChromatogramMSD.MINUTE_CORRELATION_FACTOR);
			scanValues.add(totalScanSignal.getRetentionIndex());
			scanValues.add(totalScanSignal.getTotalSignal());
			csvListWriter.write(scanValues);
		}
	}

	private void writeScansXIC(ICsvListWriter csvListWriter, IExtractedIonSignals extractedIonSignals, int startIon, int stopIon) throws IOException {

		/*
		 * Write the data.
		 */
		List<Number> scanValues;
		for(IExtractedIonSignal extractedIonSignal : extractedIonSignals.getExtractedIonSignals()) {
			scanValues = new ArrayList<Number>();
			/*
			 * RT (milliseconds)
			 * RT(minutes)
			 * RI
			 */
			int milliseconds = extractedIonSignal.getRetentionTime();
			scanValues.add(milliseconds);
			scanValues.add(milliseconds / IChromatogramMSD.MINUTE_CORRELATION_FACTOR);
			scanValues.add(extractedIonSignal.getRetentionIndex());
			/*
			 * ion data
			 */
			for(int ion = startIon; ion <= stopIon; ion++) {
				scanValues.add(extractedIonSignal.getAbundance(ion));
			}
			csvListWriter.write(scanValues);
		}
	}
}
