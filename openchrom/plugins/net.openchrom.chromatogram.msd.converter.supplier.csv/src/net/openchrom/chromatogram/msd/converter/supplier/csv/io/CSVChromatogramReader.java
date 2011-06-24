/*******************************************************************************
 * Copyright (c) 2008, 2011 Philip (eselmeister) Wenig.
 * 
 * All rights reserved.
 *******************************************************************************/
package net.openchrom.chromatogram.msd.converter.supplier.csv.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.eclipse.core.runtime.IProgressMonitor;

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

		return null;
	}

	@Override
	public IChromatogramOverview readOverview(File file, IProgressMonitor monitor) throws FileNotFoundException, FileIsNotReadableException, FileIsEmptyException, IOException {

		return null;
	}
}
