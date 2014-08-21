/*******************************************************************************
 * Copyright (c) 2011, 2014 Philip (eselmeister) Wenig.
 * 
 * All rights reserved.
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.csv.converter;

import java.io.File;

import org.eclipse.core.runtime.IProgressMonitor;

import net.chemclipse.converter.processing.chromatogram.ChromatogramExportConverterProcessingInfo;
import net.chemclipse.converter.processing.chromatogram.IChromatogramExportConverterProcessingInfo;
import net.chemclipse.msd.converter.chromatogram.AbstractChromatogramMSDExportConverter;
import net.chemclipse.msd.converter.io.IChromatogramMSDWriter;
import net.openchrom.msd.converter.supplier.csv.Activator;
import net.openchrom.msd.converter.supplier.csv.internal.converter.SpecificationValidator;
import net.openchrom.msd.converter.supplier.csv.internal.support.IConstants;
import net.openchrom.msd.converter.supplier.csv.io.ChromatogramWriter;
import net.chemclipse.msd.model.core.IChromatogramMSD;
import net.chemclipse.logging.core.Logger;
import net.chemclipse.processing.core.IProcessingInfo;

public class CSVChromatogramExportConverter extends AbstractChromatogramMSDExportConverter {

	private static final Logger logger = Logger.getLogger(CSVChromatogramExportConverter.class);
	private static final String DESCRIPTION = "CSV Export Converter";

	@Override
	public IChromatogramExportConverterProcessingInfo convert(File file, IChromatogramMSD chromatogram, IProgressMonitor monitor) {

		IChromatogramExportConverterProcessingInfo processingInfo = new ChromatogramExportConverterProcessingInfo();
		/*
		 * Check the key.
		 */
		if(!Activator.isValidVersion()) {
			processingInfo.addErrorMessage(DESCRIPTION, "The CSV chromatogram export converter has no valid licence.");
			return processingInfo;
		}
		/*
		 * Validate the file.
		 */
		file = SpecificationValidator.validateCSVSpecification(file);
		IProcessingInfo processingInfoValidate = super.validate(file);
		/*
		 * Don't process if errors have occurred.
		 */
		if(processingInfoValidate.hasErrorMessages()) {
			processingInfo.addMessages(processingInfoValidate);
		} else {
			IChromatogramMSDWriter writer = new ChromatogramWriter();
			monitor.subTask(IConstants.EXPORT_CSV_CHROMATOGRAM);
			try {
				writer.writeChromatogram(file, chromatogram, monitor);
				processingInfo.setFile(file);
			} catch(Exception e) {
				logger.warn(e);
				processingInfo.addErrorMessage(DESCRIPTION, "Something has definitely gone wrong with the file: " + file.getAbsolutePath());
			}
		}
		return processingInfo;
	}
}
