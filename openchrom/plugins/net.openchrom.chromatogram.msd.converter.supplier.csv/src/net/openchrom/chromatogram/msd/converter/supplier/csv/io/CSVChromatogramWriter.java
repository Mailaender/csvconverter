/*******************************************************************************
 * Copyright (c) 2011 Philip (eselmeister) Wenig.
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

import net.openchrom.chromatogram.msd.converter.exceptions.FileIsNotWriteableException;
import net.openchrom.chromatogram.msd.model.core.IChromatogram;
import net.openchrom.chromatogram.msd.model.xic.IExtractedIonSignal;
import net.openchrom.chromatogram.msd.model.xic.IExtractedIonSignals;

public class CSVChromatogramWriter implements ICSVChromatogramWriter {

	private static final String HEADER_ELEMENT_SEPARATOR = " ";
	
	public CSVChromatogramWriter() {

	}

	@Override
	public void writeChromatogram(File file, IChromatogram chromatogram, IProgressMonitor monitor) throws FileNotFoundException, FileIsNotWriteableException, IOException {

		/*
		 * Create the list writer.
		 */
		FileWriter writer = new FileWriter(file);
		ICsvListWriter csvListWriter = new CsvListWriter(writer, CsvPreference.EXCEL_NORTH_EUROPE_PREFERENCE);
		/*
		 * Get the chromatographic data.
		 */
		IExtractedIonSignals extractedIonSignals = chromatogram.getExtractedIonSignals();
		int startMZ = extractedIonSignals.getStartMZ();
		int stopMZ = extractedIonSignals.getStopMZ();

		writeHeader(csvListWriter, startMZ, stopMZ);
		writeScans(csvListWriter, extractedIonSignals, startMZ, stopMZ);
	}
	
	private void writeHeader(ICsvListWriter csvListWriter, int startMZ, int stopMZ) throws IOException {
		
		/*
		 * Write the header.
		 * RT (minutes), RI, m/z 18, ...
		 */
		StringBuilder builder = new StringBuilder();
		builder.append("RT(minutes)");
		builder.append(HEADER_ELEMENT_SEPARATOR);
		builder.append("RI");
		for(Integer mz = startMZ; mz <= stopMZ; mz++) {
		
			builder.append(HEADER_ELEMENT_SEPARATOR);
			builder.append(mz);
		}
		String headerElements = builder.toString();
		String[] header = headerElements.split(HEADER_ELEMENT_SEPARATOR); 
		csvListWriter.writeHeader(header);
	}
	
	private void writeScans(ICsvListWriter csvListWriter, IExtractedIonSignals extractedIonSignals, int startMZ, int stopMZ) throws IOException {
		
		/*
		 * Write the data.
		 */
		List<Float> scanValues;
		for(IExtractedIonSignal extractedIonSignal : extractedIonSignals.getExtractedIonSignals()) {
			
			scanValues = new ArrayList<Float>();
			/*
			 * RT(minutes)
			 * RI
			 */
			scanValues.add(extractedIonSignal.getRetentionTime() / (1000.0f * 60));
			scanValues.add(extractedIonSignal.getRetentionIndex());
			/*
			 * m/z data
			 */
			for(int mz = startMZ; mz <= stopMZ; mz++) {
	
				scanValues.add(extractedIonSignal.getAbundance(mz));
			}
			csvListWriter.write(scanValues);
		}
		
	}
}
