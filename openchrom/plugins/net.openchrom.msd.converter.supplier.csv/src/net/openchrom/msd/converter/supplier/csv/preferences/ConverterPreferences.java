/*******************************************************************************
 * Copyright (c) 2014 Dr. Philip Wenig.
 * 
 * All rights reserved.
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.csv.preferences;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.osgi.service.prefs.Preferences;

public class ConverterPreferences {

	public static final String P_USE_TIC = "useTic";
	public static final boolean DEF_USE_TIC = false;

	public static boolean isUseTic() {

		Preferences preferences = InstanceScope.INSTANCE.getNode(BundleProductPreferences.PREFERENCE_NODE);
		return preferences.getBoolean(P_USE_TIC, DEF_USE_TIC);
	}
}
