/*******************************************************************************
 * Copyright (c) 2011, 2015 Philip (eselmeister) Wenig.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip (eselmeister) Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.csv.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.eclipse.core.runtime.NullProgressMonitor;

import org.eclipse.chemclipse.converter.exceptions.FileIsNotWriteableException;
import org.eclipse.chemclipse.msd.converter.io.IChromatogramMSDWriter;
import net.openchrom.msd.converter.supplier.csv.TestPathHelper;

public class ChromatogramWriter_1_ITest extends ChromatogramReaderTestCase {

	private IChromatogramMSDWriter chromatogramWriter;

	@Override
	protected void setUp() throws Exception {

		pathImport = TestPathHelper.getAbsolutePath(TestPathHelper.TESTFILE_IMPORT_CHROMATOGRAM_1);
		chromatogramWriter = new ChromatogramWriter();
		super.setUp();
	}

	public void testExport_1() {

		File file = new File(TestPathHelper.getAbsolutePath(TestPathHelper.TESTFILE_EXPORT_TEST));
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
