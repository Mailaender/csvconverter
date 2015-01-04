/*******************************************************************************
 * Copyright (c) 2011, 2015 Philip (eselmeister) Wenig.
 * 
 * All rights reserved.
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.csv.model;

import net.chemclipse.msd.model.core.AbstractChromatogramMSD;

public class CSVChromatogram extends AbstractChromatogramMSD implements ICSVChromatogram {

	@Override
	public String getName() {

		return extractNameFromFile("CSVChromatogram");
	}
}
