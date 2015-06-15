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
package net.openchrom.msd.converter.supplier.csv.converter;

import java.io.File;

import org.eclipse.chemclipse.converter.processing.chromatogram.ChromatogramOverviewImportConverterProcessingInfo;
import org.eclipse.chemclipse.converter.processing.chromatogram.IChromatogramOverviewImportConverterProcessingInfo;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.core.IChromatogramOverview;
import org.eclipse.chemclipse.msd.converter.chromatogram.AbstractChromatogramMSDImportConverter;
import org.eclipse.chemclipse.msd.converter.io.IChromatogramMSDReader;
import org.eclipse.chemclipse.msd.converter.processing.chromatogram.ChromatogramMSDImportConverterProcessingInfo;
import org.eclipse.chemclipse.msd.converter.processing.chromatogram.IChromatogramMSDImportConverterProcessingInfo;
import org.eclipse.chemclipse.msd.model.core.IChromatogramMSD;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.msd.converter.supplier.csv.internal.converter.SpecificationValidator;
import net.openchrom.msd.converter.supplier.csv.internal.support.IConstants;
import net.openchrom.msd.converter.supplier.csv.io.ChromatogramReader;

public class ChromatogramImportConverter extends AbstractChromatogramMSDImportConverter {

	private static final Logger logger = Logger.getLogger(ChromatogramImportConverter.class);
	private static final String DESCRIPTION = "CSV Import Converter";

	@Override
	public IChromatogramMSDImportConverterProcessingInfo convert(File file, IProgressMonitor monitor) {

		IChromatogramMSDImportConverterProcessingInfo processingInfo = new ChromatogramMSDImportConverterProcessingInfo();
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
			IChromatogramMSDReader reader = new ChromatogramReader();
			monitor.subTask(IConstants.IMPORT_CSV_CHROMATOGRAM);
			try {
				IChromatogramMSD chromatogram = reader.read(file, monitor);
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
		 * /*
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
			IChromatogramMSDReader reader = new ChromatogramReader();
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
