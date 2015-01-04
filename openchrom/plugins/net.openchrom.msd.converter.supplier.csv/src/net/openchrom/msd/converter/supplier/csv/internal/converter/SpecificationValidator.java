/*******************************************************************************
 * Copyright (c) 2011, 2015 Philip (eselmeister) Wenig.
 * 
 * All rights reserved.
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.csv.internal.converter;

import java.io.File;

public class SpecificationValidator {

	/**
	 * Use only static methods.
	 */
	private SpecificationValidator() {

	}

	public static File validateCSVSpecification(File file) {

		if(file == null) {
			return null;
		}
		/*
		 * Validate
		 */
		File validFile;
		String path = file.getAbsolutePath().toLowerCase();
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
