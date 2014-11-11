/*******************************************************************************
 * Copyright (c) 2011, 2014 Philip (eselmeister) Wenig.
 * 
 * All rights reserved.
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.csv.ui.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;

import net.openchrom.msd.converter.supplier.csv.preferences.BundleProductPreferences;
import net.openchrom.msd.converter.supplier.csv.preferences.PreferenceSupplier;
import net.openchrom.msd.converter.supplier.csv.ui.Activator;
import net.openchrom.keys.ui.preferences.AbstractCustomFieldEditorPreferencePage;
import net.openchrom.keys.ui.preferences.IKeyPreferencePage;

public class PreferencePage extends AbstractCustomFieldEditorPreferencePage implements IKeyPreferencePage {

	public PreferencePage() {

		super(Activator.getDefault().getPreferenceStore(), new BundleProductPreferences(), false);
	}

	@Override
	public void createSettingPages() {

		addField(new BooleanFieldEditor(PreferenceSupplier.P_USE_TIC, "Export only TIC values.", getFieldEditorParent()));
	}
}
