/*******************************************************************************
 * Copyright (c) 2011 Philip (eselmeister) Wenig.
 * 
 * All rights reserved.
 *******************************************************************************/
package net.openchrom.chromatogram.msd.converter.supplier.csv.io;

import java.io.File;

import org.eclipse.core.runtime.NullProgressMonitor;

import net.openchrom.chromatogram.msd.converter.chromatogram.ChromatogramConverter;
import net.openchrom.chromatogram.msd.model.core.IChromatogram;
import junit.framework.TestCase;

public class ChromatogramWriterTestCase extends TestCase {

	protected IChromatogram chromatogramImport;
	protected IChromatogram chromatogram;
	protected String pathImport;
	protected String pathExport;
	protected File fileImport;
	protected File fileExport;
	protected String extensionPointImport;
	protected String extensionPointExportReimport;

	@Override
	protected void setUp() throws Exception {

		super.setUp();
		/*
		 * Import the chromatogram.
		 */
		fileImport = new File(this.pathImport);
		chromatogramImport = ChromatogramConverter.convert(fileImport, this.extensionPointImport, new NullProgressMonitor());
		/*
		 * Export the chromatogram.
		 */
		fileExport = new File(this.pathExport);
		fileExport = ChromatogramConverter.convert(fileExport, chromatogramImport, this.extensionPointExportReimport, new NullProgressMonitor());
		/*
		 * Reimport the exported chromatogram.
		 */
		chromatogramImport = null;
		chromatogram = ChromatogramConverter.convert(fileExport, this.extensionPointExportReimport, new NullProgressMonitor());
	}

	@Override
	protected void tearDown() throws Exception {

		pathImport = null;
		pathExport = null;
		fileImport = null;
		fileExport = null;
		chromatogramImport = null;
		chromatogram = null;
		super.tearDown();
	}
}