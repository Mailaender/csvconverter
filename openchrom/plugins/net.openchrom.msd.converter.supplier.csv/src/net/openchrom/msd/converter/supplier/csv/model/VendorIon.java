/*******************************************************************************
 * Copyright (c) 2011, 2015 Philip (eselmeister) Wenig.
 * 
 * All rights reserved.
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.csv.model;

import net.chemclipse.model.exceptions.AbundanceLimitExceededException;
import net.chemclipse.msd.model.core.AbstractScanIon;
import net.chemclipse.msd.model.exceptions.IonLimitExceededException;

public class VendorIon extends AbstractScanIon implements IVendorIon {

	/**
	 * Renew the serialVersionUID any time you have changed some fields or
	 * methods.
	 */
	private static final long serialVersionUID = -8157753037973736403L;
	public static final int BINARY_ION_LENGTH_IN_BYTES = 4;
	public static final float MIN_ABUNDANCE = 0.0f;
	public static final float MAX_ABUNDANCE = Float.MAX_VALUE;
	public static final double MIN_ION = 1.0d;
	public static final double MAX_ION = 50000.0d;

	public VendorIon(double ion) throws IonLimitExceededException {

		super(ion);
	}

	public VendorIon(double ion, boolean ignoreAbundanceLimit) throws IonLimitExceededException {

		super(ion);
		setIgnoreAbundanceLimit(ignoreAbundanceLimit);
	}

	public VendorIon(double ion, float abundance) throws AbundanceLimitExceededException, IonLimitExceededException {

		super(ion, abundance);
	}

	@Override
	public float getMaxPossibleAbundanceValue() {

		return MAX_ABUNDANCE;
	}

	@Override
	public double getMaxPossibleIonValue() {

		return MAX_ION;
	}

	@Override
	public float getMinPossibleAbundanceValue() {

		return MIN_ABUNDANCE;
	}

	@Override
	public double getMinPossibleIonValue() {

		return MIN_ION;
	}

	// -------------------------------equals, hashCode, toString
	@Override
	public String toString() {

		StringBuilder builder = new StringBuilder();
		builder.append(super.toString());
		builder.append(getClass().getName());
		builder.append("maxPossibleAbundanceValue=" + getMaxPossibleAbundanceValue());
		builder.append(",");
		builder.append("maxPossibleIonValue=" + getMaxPossibleIonValue());
		builder.append(",");
		builder.append("minPossibleAbundanceValue=" + getMinPossibleAbundanceValue());
		builder.append(",");
		builder.append("minPossibleIonValue=" + getMinPossibleIonValue());
		builder.append("]");
		return builder.toString();
	}
	// -------------------------------equals, hashCode, toString
}
