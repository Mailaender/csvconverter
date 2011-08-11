/*******************************************************************************
 * Copyright (c) 2011 Philip (eselmeister) Wenig.
 * 
 * All rights reserved.
 *******************************************************************************/
package net.openchrom.chromatogram.msd.converter.supplier.csv.internal.converter;

import java.io.File;

public class SpecificationValidator {

	/**
	 * Use only static methods.
	 */
	private SpecificationValidator() {

	}

	public static File validateCSVSpecification(File file) {

		File validFile;
		String path = file.getAbsolutePath().toUpperCase();
		if(file.isDirectory()) {
			validFile = new File(file.getAbsolutePath() + File.separator + "CHROMATOGRAM.csv");
		} else {
			if(path.endsWith(".")) {
				validFile = new File(file.getAbsolutePath() + "csv");
			} else if(!path.endsWith(".csv")) {
				validFile = new File(file.getAbsolutePath() + ".csv");
			} else {
				validFile = file;
			}
		}
		return validFile;
	}
}
