/*******************************************************************************
 * Copyright (c) 2008, 2011 Philip (eselmeister) Wenig.
 * 
 * All rights reserved.
 *******************************************************************************/
package net.openchrom.chromatogram.msd.converter.supplier.csv.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.supercsv.io.CsvListReader;
import org.supercsv.io.ICsvListReader;
import org.supercsv.prefs.CsvPreference;

import net.openchrom.chromatogram.msd.converter.exceptions.FileIsEmptyException;
import net.openchrom.chromatogram.msd.converter.exceptions.FileIsNotReadableException;
import net.openchrom.chromatogram.msd.model.core.IChromatogram;
import net.openchrom.chromatogram.msd.model.core.IChromatogramOverview;

/**
 * This class is responsible to read a Agilent Chromatogram from its binary
 * file.
 * 
 * @author eselmeister
 */
public class CSVChromatogramReader implements ICSVChromatogramReader {

	public static final String VALID_FILE_FORMAT_CHECK = "GCMS DATA FILE";

	public CSVChromatogramReader() {

	}

	@Override
	public IChromatogram read(File file, IProgressMonitor monitor) throws FileNotFoundException, FileIsNotReadableException, FileIsEmptyException, IOException {

		if(isValidFileFormat(file)) {
			return null;	
		} else {
			return null;	
		}
	}

	@Override
	public IChromatogramOverview readOverview(File file, IProgressMonitor monitor) throws FileNotFoundException, FileIsNotReadableException, FileIsEmptyException, IOException {

		if(isValidFileFormat(file)) {
			return null;	
		} else {
			return null;	
		}
	}
	
	private boolean isValidFileFormat(File file) throws IOException {
		
		FileReader reader = new FileReader(file);
		ICsvListReader csvListReader = new CsvListReader(reader, CsvPreference.EXCEL_NORTH_EUROPE_PREFERENCE);
		String[] header = csvListReader.getCSVHeader(true);
		/*
		 * Check the first column
		 */
		String firstColumn = header[0];
		return firstColumn.equals(CSVChromatogramWriter.RT_MILLISECONDS_COLUMN);
	}
	
	private IChromatogram readChromatogram(File file) throws IOException {
		
		FileReader reader = new FileReader(file);
		ICsvListReader csvListReader = new CsvListReader(reader, CsvPreference.EXCEL_NORTH_EUROPE_PREFERENCE);
		
		String[] header = csvListReader.getCSVHeader(true);
		
		System.out.println(header[0]);
		
		
		List<String> line;
		while((line = csvListReader.read()) != null) {
			
			String retentionTimeInMilliseconds = line.get(0);
			String retentionTimeInMinutes = line.get(1);
			String retentionIndex = line.get(2);
			for(int index = 3; index < line.size(); index++) {
				String abundance = line.get(index);
			}
		}
		
		return null;
	}
}
