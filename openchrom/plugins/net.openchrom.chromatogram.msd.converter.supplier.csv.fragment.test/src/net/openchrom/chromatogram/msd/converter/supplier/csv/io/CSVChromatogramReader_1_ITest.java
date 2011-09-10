/*******************************************************************************
 * Copyright (c) 2011 Philip (eselmeister) Wenig.
 * 
 * All rights reserved.
 *******************************************************************************/
package net.openchrom.chromatogram.msd.converter.supplier.csv.io;

import net.openchrom.chromatogram.msd.converter.supplier.csv.TestPathHelper;

public class CSVChromatogramReader_1_ITest extends ChromatogramWriterTestCase {

	@Override
	protected void setUp() throws Exception {

		/*
		 * Import
		 */
		pathImport = TestPathHelper.getAbsolutePath(TestPathHelper.TESTFILE_IMPORT_OP17760);
		extensionPointImport = "net.openchrom.chromatogram.msd.converter.supplier.agilent";
		/*
		 * Export/Reimport
		 */
		pathExport = TestPathHelper.getAbsolutePath(TestPathHelper.TESTFILE_EXPORT_TEST);
		extensionPointExportReimport = "net.openchrom.chromatogram.msd.converter.supplier.csv";
		super.setUp();
	}

	public void testReimport_1() {

		assertNotNull(chromatogram);
	}

	public void testReimport_2() {

		assertEquals(5726, chromatogram.getNumberOfScans());
	}
}
