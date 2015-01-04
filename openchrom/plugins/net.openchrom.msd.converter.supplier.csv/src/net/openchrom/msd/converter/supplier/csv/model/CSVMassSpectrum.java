/*******************************************************************************
 * Copyright (c) 2011, 2015 Philip (eselmeister) Wenig.
 * 
 * All rights reserved.
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.csv.model;

import net.chemclipse.model.exceptions.AbundanceLimitExceededException;
import net.chemclipse.msd.model.core.AbstractSupplierScanMassSpectrum;
import net.chemclipse.msd.model.core.IIon;
import net.chemclipse.msd.model.exceptions.IonLimitExceededException;
import net.chemclipse.logging.core.Logger;

public class CSVMassSpectrum extends AbstractSupplierScanMassSpectrum implements ICSVMassSpectrum {

	/**
	 * Renew the serialVersionUID any time you have changed some fields or
	 * methods.
	 */
	private static final long serialVersionUID = -8416701833314906892L;
	private static final Logger logger = Logger.getLogger(CSVMassSpectrum.class);
	/*
	 * MIN/MAX Bounds
	 */
	public static final int MAX_IONS = 2000;
	public static final int MIN_RETENTION_TIME = 0;
	public static final int MAX_RETENTION_TIME = Integer.MAX_VALUE;

	// -------------------------------------------ISupplierMassSpectrum
	@Override
	public int getMaxPossibleIons() {

		return MAX_IONS;
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
		builder.append("maxPossibleIons=" + getMaxPossibleIons());
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
		ICSVIon csvIon;
		/*
		 * The instance variables have been copied by super.clone();.<br/> The
		 * ions in the ion list need not to be removed via
		 * removeAllIons as the method super.clone() has created a new
		 * list.<br/> It is necessary to fill the list again, as the abstract
		 * super class does not know each available type of ion.<br/>
		 * Make a deep copy of all ions.
		 */
		for(IIon ion : getIons()) {
			try {
				csvIon = new CSVIon(ion.getIon(), ion.getAbundance());
				massSpectrum.addIon(csvIon);
			} catch(AbundanceLimitExceededException e) {
				logger.warn(e);
			} catch(IonLimitExceededException e) {
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
