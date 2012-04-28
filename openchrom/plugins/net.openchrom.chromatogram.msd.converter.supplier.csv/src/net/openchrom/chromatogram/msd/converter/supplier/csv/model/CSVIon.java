/*******************************************************************************
 * Copyright (c) 2011, 2012 Philip (eselmeister) Wenig.
 * 
 * All rights reserved.
 *******************************************************************************/
package net.openchrom.chromatogram.msd.converter.supplier.csv.model;

import net.openchrom.chromatogram.msd.model.core.AbstractSupplierIon;
import net.openchrom.chromatogram.msd.model.exceptions.AbundanceLimitExceededException;
import net.openchrom.chromatogram.msd.model.exceptions.IonLimitExceededException;

public class CSVIon extends AbstractSupplierIon implements ICSVIon {

	/**
	 * Renew the serialVersionUID any time you have changed some fields or
	 * methods.
	 */
	private static final long serialVersionUID = -8157753037973736403L;
	public static final int BINARY_ION_LENGTH_IN_BYTES = 4;
	public static final float MIN_ABUNDANCE = 0.0f;
	public static final float MAX_ABUNDANCE = Float.MAX_VALUE;
	public static final float MIN_Ion = 1.0f;
	public static final float MAX_Ion = 50000.0f;

	public CSVIon(float ion) throws IonLimitExceededException {

		super(ion);
	}

	public CSVIon(float ion, boolean ignoreAbundanceLimit) throws IonLimitExceededException {

		super(ion);
		setIgnoreAbundanceLimit(ignoreAbundanceLimit);
	}

	public CSVIon(float ion, float abundance) throws AbundanceLimitExceededException, IonLimitExceededException {

		super(ion, abundance);
	}

	@Override
	public float getMaxPossibleAbundanceValue() {

		return MAX_ABUNDANCE;
	}

	@Override
	public float getMaxPossibleIonValue() {

		return MAX_Ion;
	}

	@Override
	public float getMinPossibleAbundanceValue() {

		return MIN_ABUNDANCE;
	}

	@Override
	public float getMinPossibleIonValue() {

		return MIN_Ion;
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
