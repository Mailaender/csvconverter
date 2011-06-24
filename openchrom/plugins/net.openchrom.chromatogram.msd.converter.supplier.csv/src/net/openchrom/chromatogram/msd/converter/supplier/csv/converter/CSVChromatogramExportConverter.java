/*******************************************************************************
 * Copyright (c) 2011 Philip (eselmeister) Wenig.
 * 
 * All rights reserved.
 *******************************************************************************/
package net.openchrom.chromatogram.msd.converter.supplier.csv.converter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.chromatogram.msd.converter.chromatogram.AbstractChromatogramExportConverter;
import net.openchrom.chromatogram.msd.converter.exceptions.FileIsNotWriteableException;
import net.openchrom.chromatogram.msd.converter.supplier.csv.Activator;
import net.openchrom.chromatogram.msd.converter.supplier.csv.internal.support.IConstants;
import net.openchrom.chromatogram.msd.converter.supplier.csv.io.CSVChromatogramWriter;
import net.openchrom.chromatogram.msd.converter.supplier.csv.io.ICSVChromatogramWriter;
import net.openchrom.chromatogram.msd.model.core.IChromatogram;

public class CSVChromatogramExportConverter extends AbstractChromatogramExportConverter {

	public CSVChromatogramExportConverter() {

	}

	@Override
	public File convert(File file, IChromatogram chromatogram, IProgressMonitor monitor) throws FileNotFoundException, FileIsNotWriteableException, IOException {

		/*
		 * Check the key.
		 */
		if(!Activator.isValidVersion()) {
			throw new FileIsNotWriteableException("The CSV Chromatogram converter has no valid licence");
		}
		super.validate(file);
		ICSVChromatogramWriter writer = new CSVChromatogramWriter();
		monitor.subTask(IConstants.EXPORT_CSV_CHROMATOGRAM);
		writer.writeChromatogram(file, chromatogram, monitor);
		return file;
	}
}
