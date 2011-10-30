/*******************************************************************************
 * Copyright (c) 2011 Philip (eselmeister) Wenig.
 * 
 * All rights reserved.
 *******************************************************************************/
package net.openchrom.chromatogram.msd.converter.supplier.csv.converter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.chromatogram.msd.converter.chromatogram.AbstractChromatogramImportConverter;
import net.openchrom.chromatogram.msd.converter.exceptions.FileIsEmptyException;
import net.openchrom.chromatogram.msd.converter.exceptions.FileIsNotReadableException;
import net.openchrom.chromatogram.msd.converter.supplier.csv.Activator;
import net.openchrom.chromatogram.msd.converter.supplier.csv.internal.converter.SpecificationValidator;
import net.openchrom.chromatogram.msd.converter.supplier.csv.internal.support.IConstants;
import net.openchrom.chromatogram.msd.converter.supplier.csv.io.CSVChromatogramReader;
import net.openchrom.chromatogram.msd.converter.supplier.csv.io.ICSVChromatogramReader;
import net.openchrom.chromatogram.msd.model.core.IChromatogram;
import net.openchrom.chromatogram.msd.model.core.IChromatogramOverview;

public class CSVChromatogramImportConverter extends AbstractChromatogramImportConverter {

	public CSVChromatogramImportConverter() {

	}

	@Override
	public IChromatogram convert(File chromatogram, IProgressMonitor monitor) throws FileNotFoundException, FileIsNotReadableException, FileIsEmptyException, IOException {

		super.validate(chromatogram);
		/*
		 * Check the key.
		 */
		if(!Activator.isValidVersion()) {
			throw new FileIsNotReadableException("The CSV Chromatogram converter has no valid licence");
		}
		chromatogram = SpecificationValidator.validateCSVSpecification(chromatogram);
		ICSVChromatogramReader reader = new CSVChromatogramReader();
		monitor.subTask(IConstants.IMPORT_CSV_CHROMATOGRAM);
		return reader.read(chromatogram, monitor);
	}

	@Override
	public IChromatogramOverview convertOverview(File chromatogram, IProgressMonitor monitor) throws FileNotFoundException, FileIsNotReadableException, FileIsEmptyException, IOException {

		super.validate(chromatogram);
		/*
		 * Check the key.
		 */
		if(!Activator.isValidVersion()) {
			throw new FileIsNotReadableException("The CSV Chromatogram converter has no valid licence");
		}
		chromatogram = SpecificationValidator.validateCSVSpecification(chromatogram);
		ICSVChromatogramReader reader = new CSVChromatogramReader();
		monitor.subTask(IConstants.IMPORT_CSV_CHROMATOGRAM_OVERVIEW);
		return reader.readOverview(chromatogram, monitor);
	}
}
