/*******************************************************************************
 * Copyright (c) 2011 Philip (eselmeister) Wenig.
 * 
 * All rights reserved.
 *******************************************************************************/
package net.openchrom.chromatogram.msd.converter.supplier.csv.model;

import net.openchrom.chromatogram.msd.model.core.AbstractSupplierMassFragment;
import net.openchrom.chromatogram.msd.model.exceptions.AbundanceLimitExceededException;
import net.openchrom.chromatogram.msd.model.exceptions.MZLimitExceededException;

public class CSVMassFragment extends AbstractSupplierMassFragment implements ICSVMassFragment {

	/**
	 * Renew the serialVersionUID any time you have changed some fields or
	 * methods.
	 */
	private static final long serialVersionUID = -8157753037973736403L;
	public static final int BINARY_MASS_FRAGMENT_LENGTH_IN_BYTES = 4;
	public static final float MIN_ABUNDANCE = 0.0f;
	public static final float MAX_ABUNDANCE = Float.MAX_VALUE;
	public static final float MIN_MZ = 1.0f;
	public static final float MAX_MZ = 50000.0f;

	public CSVMassFragment(float mz) throws MZLimitExceededException {

		super(mz);
	}

	public CSVMassFragment(float mz, boolean ignoreAbundanceLimit) throws MZLimitExceededException {

		super(mz);
		setIgnoreAbundanceLimit(ignoreAbundanceLimit);
	}

	public CSVMassFragment(float mz, float abundance) throws AbundanceLimitExceededException, MZLimitExceededException {

		super(mz, abundance);
	}

	@Override
	public float getMaxPossibleAbundanceValue() {

		return MAX_ABUNDANCE;
	}

	@Override
	public float getMaxPossibleMZValue() {

		return MAX_MZ;
	}

	@Override
	public float getMinPossibleAbundanceValue() {

		return MIN_ABUNDANCE;
	}

	@Override
	public float getMinPossibleMZValue() {

		return MIN_MZ;
	}

	// -------------------------------equals, hashCode, toString
	@Override
	public String toString() {

		StringBuilder builder = new StringBuilder();
		builder.append(super.toString());
		builder.append(getClass().getName());
		builder.append("maxPossibleAbundanceValue=" + getMaxPossibleAbundanceValue());
		builder.append(",");
		builder.append("maxPossibleMZValue=" + getMaxPossibleMZValue());
		builder.append(",");
		builder.append("minPossibleAbundanceValue=" + getMinPossibleAbundanceValue());
		builder.append(",");
		builder.append("minPossibleMZValue=" + getMinPossibleMZValue());
		builder.append("]");
		return builder.toString();
	}
	// -------------------------------equals, hashCode, toString
}
