/*******************************************************************************
 * Copyright (c) 2011, 2014 Philip (eselmeister) Wenig.
 * 
 * All rights reserved.
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.csv.ui;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import net.openchrom.msd.converter.supplier.csv.preferences.BundleProductPreferences;
import net.openchrom.keys.preferences.IBundleProductPreferences;
import net.openchrom.keys.preferences.IProductPreferences;
import net.openchrom.keys.validator.ProductValidator;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "net.openchrom.msd.converter.supplier.csv.ui"; //$NON-NLS-1$
	// The shared instance
	private static Activator plugin;

	/**
	 * The constructor
	 */
	public Activator() {

	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {

		super.start(context);
		plugin = this;
		/*
		 * The key will be checked each time the plugin gets activated.<br/> The
		 * user can select the trial (if not expired) or full option.
		 */
		IBundleProductPreferences bundleProductPreferences = new BundleProductPreferences();
		IProductPreferences productPreferences = bundleProductPreferences.getProductPreferences();
		ProductValidator.isValidVersion(productPreferences, true, false);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {

		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static Activator getDefault() {

		return plugin;
	}
}