/*******************************************************************************
 * Copyright (c) 2011, 2012 Philip (eselmeister) Wenig.
 * 
 * All rights reserved.
 *******************************************************************************/
package net.openchrom.chromatogram.msd.converter.supplier.csv.preferences;

import net.openchrom.keys.preferences.AbstractBundleProductPreferences;
import net.openchrom.keys.preferences.IBundleProductPreferences;

public class BundleProductPreferences extends AbstractBundleProductPreferences implements IBundleProductPreferences {

	public static final String PREFERENCE_NODE = "net.openchrom.chromatogram.msd.converter.supplier.csv.ui";
	/*
	 * Trial version (only read option). The key generator (php) needs to have
	 * the same product id (index.php $pluginIdentifier).
	 * Don't forget to disable the write extension and to include the classes.
	 * CONVERTER_ID is the id used in the declaration of the converter extension
	 * (MANIFEST.MF). It is needed to save the chromatogram when the user calls
	 * "save" or CTRL+S on an open chromatogram editor. The editor itself has a
	 * method "doSave(...)" which will be called.
	 */
	private static final String PRODUCT_NAME = "CSV MS Chromatogram Converter";
	/*
	 * READER VERSION
	 */
	private static final String PRODUCT_ID = "net.openchrom.chromatogram.msd.converter.supplier.csv";
	public static final String CONVERTER_ID = ""; // Must be "", it should be not possible to save the chromatogram using the chromatogram editor.
	private static final int TRIAL_DAYS = 30;

	public BundleProductPreferences() {

		super(PREFERENCE_NODE, PRODUCT_NAME, PRODUCT_ID, TRIAL_DAYS);
	}
}
