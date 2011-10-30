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

/**
 * This class initializes an ChromatogramReaderTest.
 * 
 * @author eselmeister
 */
public class ChromatogramReaderTestCase extends TestCase {

	protected IChromatogram chromatogram;
	protected String pathImport;
	protected File fileImport;
	private final static String EXTENSION_POINT_ID = "net.openchrom.chromatogram.msd.converter.supplier.agilent";

	@Override
	protected void setUp() throws Exception {

		super.setUp();
		fileImport = new File(this.pathImport);
		chromatogram = ChromatogramConverter.convert(fileImport, EXTENSION_POINT_ID, new NullProgressMonitor());
	}

	@Override
	protected void tearDown() throws Exception {

		pathImport = null;
		fileImport = null;
		chromatogram = null;
		super.tearDown();
	}
}
