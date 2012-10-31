/*******************************************************************************
 * Copyright (c) 2011, 2012 Philip (eselmeister) Wenig.
 * 
 * All rights reserved.
 *******************************************************************************/
package net.openchrom.chromatogram.msd.converter.supplier.csv.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.eclipse.core.runtime.NullProgressMonitor;

import net.openchrom.chromatogram.msd.converter.exceptions.FileIsNotWriteableException;
import net.openchrom.chromatogram.msd.converter.io.IChromatogramWriter;
import net.openchrom.chromatogram.msd.converter.supplier.csv.TestPathHelper;

public class ChromatogramWriter_1_ITest extends ChromatogramReaderTestCase {

	private IChromatogramWriter chromatogramWriter;

	@Override
	protected void setUp() throws Exception {

		pathImport = TestPathHelper.getAbsolutePath(TestPathHelper.TESTFILE_IMPORT_OP17760);
		chromatogramWriter = new ChromatogramWriter();
		super.setUp();
	}

	public void testExport_1() {

		File file = new File("/home/wenigmedia/Desktop/Test/OP17760.csv");
		try {
			chromatogramWriter.writeChromatogram(file, chromatogram, new NullProgressMonitor());
			assertTrue(true);
		} catch(FileNotFoundException e) {
			assertTrue("FileNotFoundException", false);
		} catch(FileIsNotWriteableException e) {
			assertTrue("FileIsNotWriteableException", false);
		} catch(IOException e) {
			assertTrue("IOException", false);
		}
	}
}
