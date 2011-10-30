/*******************************************************************************
 * Copyright (c) 2011 Philip (eselmeister) Wenig.
 * 
 * All rights reserved.
 *******************************************************************************/
package net.openchrom.chromatogram.msd.converter.supplier.csv.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.chromatogram.msd.converter.exceptions.FileIsNotWriteableException;
import net.openchrom.chromatogram.msd.model.core.IChromatogram;

public interface ICSVChromatogramWriter {

	/**
	 * Writes the chromatogram to the given file.
	 * 
	 * @param file
	 * @param chromatogram
	 * @throws FileNotFoundException
	 * @throws FileIsNotWriteableException
	 * @throws IOException
	 */
	void writeChromatogram(File file, IChromatogram chromatogram, IProgressMonitor monitor) throws FileNotFoundException, FileIsNotWriteableException, IOException;
}
