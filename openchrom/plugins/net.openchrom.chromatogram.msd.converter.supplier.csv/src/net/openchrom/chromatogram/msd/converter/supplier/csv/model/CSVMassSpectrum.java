/*******************************************************************************
 * Copyright (c) 2011 Philip (eselmeister) Wenig.
 * 
 * All rights reserved.
 *******************************************************************************/
package net.openchrom.chromatogram.msd.converter.supplier.csv.model;

import net.openchrom.chromatogram.msd.model.core.AbstractSupplierMassSpectrum;
import net.openchrom.chromatogram.msd.model.core.IMassFragment;
import net.openchrom.chromatogram.msd.model.exceptions.AbundanceLimitExceededException;
import net.openchrom.chromatogram.msd.model.exceptions.MZLimitExceededException;
import net.openchrom.logging.core.Logger;

public class CSVMassSpectrum extends AbstractSupplierMassSpectrum implements ICSVMassSpectrum {

	/**
	 * Renew the serialVersionUID any time you have changed some fields or
	 * methods.
	 */
	private static final long serialVersionUID = -8416701833314906892L;
	private static final Logger logger = Logger.getLogger(CSVMassSpectrum.class);
	/*
	 * MIN/MAX Bounds
	 */
	public static final int MAX_MASSFRAGMENTS = 2000;
	public static final int MIN_RETENTION_TIME = 0;
	public static final int MAX_RETENTION_TIME = Integer.MAX_VALUE;

	// -------------------------------------------ISupplierMassSpectrum
	@Override
	public int getMaxPossibleMassFragments() {

		return MAX_MASSFRAGMENTS;
	}

	@Override
	public int getMaxPossibleRetentionTime() {

		return MAX_RETENTION_TIME;
	}

	@Override
	public int getMinPossibleRetentionTime() {

		return MIN_RETENTION_TIME;
	}

	// -------------------------------------------ISupplierMassSpectrum
	// -------------------------------equals, hashCode, toString
	@Override
	public String toString() {

		StringBuilder builder = new StringBuilder();
		builder.append(super.toString());
		builder.append(getClass().getName());
		builder.append("maxPossibleMassFragments=" + getMaxPossibleMassFragments());
		builder.append(",");
		builder.append("maxPossibleRetentionTime=" + getMaxPossibleRetentionTime());
		builder.append(",");
		builder.append("minPossibleRetentionTime=" + getMinPossibleRetentionTime());
		builder.append("]");
		return builder.toString();
	}

	// -------------------------------equals, hashCode, toString
	// -------------------------------IMassSpectrumCloneable
	/**
	 * Keep in mind, it is a covariant return.<br/>
	 * IMassSpectrum is needed. IAgilentMassSpectrum is a subtype of
	 * ISupplierMassSpectrum is a subtype of IMassSpectrum.
	 */
	@Override
	public ICSVMassSpectrum makeDeepCopy() throws CloneNotSupportedException {

		ICSVMassSpectrum massSpectrum = (ICSVMassSpectrum)super.clone();
		ICSVMassFragment massFragment;
		/*
		 * The instance variables have been copied by super.clone();.<br/> The
		 * mass fragments in the mass fragment list need not to be removed via
		 * removeAllMassFragments as the method super.clone() has created a new
		 * list.<br/> It is necessary to fill the list again, as the abstract
		 * super class does not know each available type of mass fragment.<br/>
		 * Make a deep copy of all mass fragments.
		 */
		for(IMassFragment mf : getMassFragments()) {
			try {
				massFragment = new CSVMassFragment(mf.getMZ(), mf.getAbundance());
				massSpectrum.addMassFragment(massFragment);
			} catch(AbundanceLimitExceededException e) {
				logger.warn(e);
			} catch(MZLimitExceededException e) {
				logger.warn(e);
			}
		}
		return massSpectrum;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {

		return makeDeepCopy();
	}
	// -------------------------------IMassSpectrumCloneable
}
