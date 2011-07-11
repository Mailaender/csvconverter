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
	
	public static final String RT_MILLISECONDS_COLUMN = "RT(milliseconds)";
		
	public static final String RT_MINUTES_COLUMN = "RT(minutes) - NOT USED BY IMPORT";
	public static final String RI_COLUMN = "RI";
	private static final float MINUTE_FACTOR = 1000.0f * 60;
	
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
		
		csvListWriter.close();
	}
	
	private void writeHeader(ICsvListWriter csvListWriter, int startMZ, int stopMZ) throws IOException {
		
		/*
		 * Write the header.
		 * RT(milliseconds), RT(minutes) - NOT USED BY IMPORT, RI, m/z 18, ...
		 */
		List<String> headerList = new ArrayList<String>();
		
		headerList.add(RT_MILLISECONDS_COLUMN);
		headerList.add(RT_MINUTES_COLUMN);
		headerList.add(RI_COLUMN);
		for(Integer mz = startMZ; mz <= stopMZ; mz++) {
		
			headerList.add(mz.toString());
		}
		String[] header = headerList.toArray(new String[]{});
		csvListWriter.writeHeader(header);
	}
	
	private void writeScans(ICsvListWriter csvListWriter, IExtractedIonSignals extractedIonSignals, int startMZ, int stopMZ) throws IOException {
		
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
			scanValues.add(milliseconds / MINUTE_FACTOR);
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
