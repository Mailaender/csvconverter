/*******************************************************************************
 * Copyright (c) 2014 Philip (eselmeister) Wenig.
 * 
 * All rights reserved.
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.csv.preferences;

import net.chemclipse.support.preferences.AbstractExtendedPreferenceInitializer;

public class PreferenceInitializer extends AbstractExtendedPreferenceInitializer {

	public PreferenceInitializer() {

		super(PreferenceSupplier.INSTANCE());
	}
}