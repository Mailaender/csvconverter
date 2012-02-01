/*******************************************************************************
 * Copyright (c) 2011, 2012 Philip (eselmeister) Wenig.
 * 
 * All rights reserved.
 *******************************************************************************/
package net.openchrom.chromatogram.msd.converter.supplier.csv.converter;

import java.io.File;

import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.chromatogram.msd.converter.chromatogram.AbstractChromatogramImportConverter;
import net.openchrom.chromatogram.msd.converter.processing.chromatogram.ChromatogramImportConverterProcessingInfo;
import net.openchrom.chromatogram.msd.converter.processing.chromatogram.ChromatogramOverviewImportConverterProcessingInfo;
import net.openchrom.chromatogram.msd.converter.processing.chromatogram.IChromatogramImportConverterProcessingInfo;
import net.openchrom.chromatogram.msd.converter.processing.chromatogram.IChromatogramOverviewImportConverterProcessingInfo;
import net.openchrom.chromatogram.msd.converter.supplier.csv.Activator;
import net.openchrom.chromatogram.msd.converter.supplier.csv.internal.converter.SpecificationValidator;
import net.openchrom.chromatogram.msd.converter.supplier.csv.internal.support.IConstants;
import net.openchrom.chromatogram.msd.converter.supplier.csv.io.CSVChromatogramReader;
import net.openchrom.chromatogram.msd.converter.supplier.csv.io.ICSVChromatogramReader;
import net.openchrom.chromatogram.msd.model.core.IChromatogram;
import net.openchrom.chromatogram.msd.model.core.IChromatogramOverview;
import net.openchrom.logging.core.Logger;
import net.openchrom.processing.core.IProcessingInfo;

public class CSVChromatogramImportConverter extends AbstractChromatogramImportConverter {

	private static final Logger logger = Logger.getLogger(CSVChromatogramImportConverter.class);
	private static final String DESCRIPTION = "CSV Import Converter";

	@Override
	public IChromatogramImportConverterProcessingInfo convert(File file, IProgressMonitor monitor) {

		IChromatogramImportConverterProcessingInfo processingInfo = new ChromatogramImportConverterProcessingInfo();
		/*
		 * Check the key.
		 */
		if(!Activator.isValidVersion()) {
			processingInfo.addErrorMessage(DESCRIPTION, "The CSV chromatogram import converter has no valid licence.");
			return processingInfo;
		}
		/*
		 * Validate the file.
		 */
		IProcessingInfo processingInfoValidate = super.validate(file);
		if(processingInfoValidate.hasErrorMessages()) {
			processingInfo.addMessages(processingInfoValidate);
		} else {
			/*
			 * Read the chromatogram.
			 */
			file = SpecificationValidator.validateCSVSpecification(file);
			ICSVChromatogramReader reader = new CSVChromatogramReader();
			monitor.subTask(IConstants.IMPORT_CSV_CHROMATOGRAM);
			try {
				IChromatogram chromatogram = reader.read(file, monitor);
				processingInfo.setChromatogram(chromatogram);
			} catch(Exception e) {
				logger.warn(e);
				processingInfo.addErrorMessage(DESCRIPTION, "Something has definitely gone wrong with the file: " + file.getAbsolutePath());
			}
		}
		return processingInfo;
	}

	@Override
	public IChromatogramOverviewImportConverterProcessingInfo convertOverview(File file, IProgressMonitor monitor) {

		IChromatogramOverviewImportConverterProcessingInfo processingInfo = new ChromatogramOverviewImportConverterProcessingInfo();
		/*
		 * Check the key.
		 */
		if(!Activator.isValidVersion()) {
			processingInfo.addErrorMessage(DESCRIPTION, "The CSV chromatogram overview import converter has no valid licence.");
			return processingInfo;
		}
		/*
		 * Validate the file.
		 */
		IProcessingInfo processingInfoValidate = super.validate(file);
		if(processingInfoValidate.hasErrorMessages()) {
			processingInfo.addMessages(processingInfoValidate);
		} else {
			/*
			 * Read the chromatogram overview.
			 */
			file = SpecificationValidator.validateCSVSpecification(file);
			ICSVChromatogramReader reader = new CSVChromatogramReader();
			monitor.subTask(IConstants.IMPORT_CSV_CHROMATOGRAM_OVERVIEW);
			try {
				IChromatogramOverview chromatogramOverview = reader.readOverview(file, monitor);
				processingInfo.setChromatogramOverview(chromatogramOverview);
			} catch(Exception e) {
				logger.warn(e);
				processingInfo.addErrorMessage(DESCRIPTION, "Something has definitely gone wrong with the file: " + file.getAbsolutePath());
			}
		}
		return processingInfo;
	}
}
