/*******************************************************************************
 * Copyright (c) 2011, 2015 Philip (eselmeister) Wenig.
 * 
 * All rights reserved.
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.csv.io;

import net.openchrom.msd.converter.supplier.csv.TestPathHelper;

public class ChromatogramReader_1_ITest extends ChromatogramWriterTestCase {

	@Override
	protected void setUp() throws Exception {

		/*
		 * Import
		 */
		pathImport = TestPathHelper.getAbsolutePath(TestPathHelper.TESTFILE_IMPORT_CHROMATOGRAM_1);
		extensionPointImport = "org.eclipse.chemclipse.xxd.converter.supplier.chemclipse";
		/*
		 * Export/Reimport
		 */
		pathExport = TestPathHelper.getAbsolutePath(TestPathHelper.TESTFILE_EXPORT_TEST);
		extensionPointExportReimport = "org.eclipse.chemclipse.msd.converter.supplier.csv";
		super.setUp();
	}

	public void testReimport_1() {

		assertNotNull(chromatogram);
	}

	public void testReimport_2() {

		assertEquals(5726, chromatogram.getNumberOfScans());
	}
}
