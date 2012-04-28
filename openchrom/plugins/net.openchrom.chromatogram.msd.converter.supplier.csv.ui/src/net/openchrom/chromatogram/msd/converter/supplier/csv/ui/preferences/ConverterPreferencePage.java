/*******************************************************************************
 * Copyright (c) 2011, 2012 Philip (eselmeister) Wenig.
 * 
 * All rights reserved.
 *******************************************************************************/
package net.openchrom.chromatogram.msd.converter.supplier.csv.ui.preferences;

import net.openchrom.chromatogram.msd.converter.supplier.csv.preferences.BundleProductPreferences;
import net.openchrom.chromatogram.msd.converter.supplier.csv.ui.Activator;
import net.openchrom.keys.preferences.IProductPreferences;
import net.openchrom.support.ui.preferences.fieldeditors.LabelFieldEditor;
import net.openchrom.support.ui.preferences.fieldeditors.SpacerFieldEditor;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class ConverterPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public ConverterPreferencePage() {

		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Modify the CSV Chromatogram Converter Settings.");
	}

	@Override
	protected void createFieldEditors() {

		// -----------------------PRODUCT_SERIAL
		IProductPreferences productPreferences = BundleProductPreferences.getProductPreferences();
		int remainingTrialDays = productPreferences.getTrialDaysRemaining();
		/*
		 * Show the remaining trial days or a message to store a valid serial
		 * key.
		 */
		if(remainingTrialDays > 0) {
			addField(new LabelFieldEditor("Remaining Trial Days: " + remainingTrialDays, getFieldEditorParent()));
		} else {
			addField(new LabelFieldEditor("To activate the module, type in a valid product serial key.", getFieldEditorParent()));
		}
		addField(new StringFieldEditor(BundleProductPreferences.P_PRODUCT_SERIAL_KEY, "Serial Key", getFieldEditorParent()));
		addField(new SpacerFieldEditor(getFieldEditorParent()));
		// -----------------------PRODUCT_SERIAL
	}

	@Override
	public void init(IWorkbench workbench) {

	}
}
