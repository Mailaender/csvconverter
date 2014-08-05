/*******************************************************************************
 * Copyright (c) 2011, 2014 Philip (eselmeister) Wenig.
 * 
 * All rights reserved.
 *******************************************************************************/
package net.openchrom.chromatogram.msd.converter.supplier.csv.model;

import net.chemclipse.chromatogram.msd.model.core.AbstractChromatogramMSD;

public class CSVChromatogram extends AbstractChromatogramMSD implements ICSVChromatogram {

	@Override
	public String getName() {

		return extractNameFromFile("CSVChromatogram");
	}
}
