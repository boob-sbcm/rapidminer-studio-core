/**
 * Copyright (C) 2001-2017 by RapidMiner and the contributors
 * 
 * Complete list of developers available at our web site:
 * 
 * http://rapidminer.com
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU Affero General Public License as published by the Free Software Foundation, either version 3
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License along with this program.
 * If not, see http://www.gnu.org/licenses/.
*/
package com.rapidminer.gui.new_plotter.utility;

import com.rapidminer.RapidMiner;
import com.rapidminer.datatable.DataTableRow;
import com.rapidminer.gui.new_plotter.listener.events.ValueRangeChangeEvent;
import com.rapidminer.gui.new_plotter.listener.events.ValueRangeChangeEvent.ValueRangeChangeType;
import com.rapidminer.tools.I18N;
import com.rapidminer.tools.ParameterService;

import java.sql.Date;
import java.text.DateFormat;


/**
 * A numerical range. By default the lower bound is included (">=" comparison),
 * the upper bound is not included in the range ("<" comparison).
 * This behavior can be changed by the properties includeLowerBound and includeUpperBound.
 *
 * @author Marius Helf, Nils Woehler
 */
public class NumericalValueRange extends AbstractValueRange {

	private double lowerBound;
	private double upperBound;

	private int columnIdx;

	private boolean includeLowerBound;
	private boolean includeUpperBound;

	private int upperPrecision = -Integer.parseInt(ParameterService
			.getParameterValue(RapidMiner.PROPERTY_RAPIDMINER_GENERAL_FRACTIONDIGITS_NUMBERS));
	private int lowerPrecision = upperPrecision;
	private DateFormat dateFormat;

    /**
     * Instantiates a new Numerical value range.
     *
     * @param lowerBound the lower bound
     * @param upperBound the upper bound
     * @param columnIdx  the column idx
     */
    public NumericalValueRange(double lowerBound, double upperBound, int columnIdx) {
		this(lowerBound, upperBound, columnIdx, true, false);
	}

    /**
     * Creates a new {@link NumericalValueRange}.
     * <p>
     * If isDate, the {@link #toString()} method converts the lower and upper bound to dates.
     *
     * @param lowerBound        the lower bound
     * @param upperBound        the upper bound
     * @param columnIdx         the column idx
     * @param dateFormat        the date format
     * @param includeLowerBound the include lower bound
     * @param includeUpperBound the include upper bound
     */
    public NumericalValueRange(double lowerBound, double upperBound, int columnIdx, DateFormat dateFormat,
			boolean includeLowerBound, boolean includeUpperBound) {
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
		this.includeLowerBound = includeLowerBound;
		this.includeUpperBound = includeUpperBound;
		this.columnIdx = columnIdx;
		this.dateFormat = dateFormat;
	}

    /**
     * Instantiates a new Numerical value range.
     *
     * @param lowerBound        the lower bound
     * @param upperBound        the upper bound
     * @param columnIdx         the column idx
     * @param includeLowerBound the include lower bound
     * @param includeUpperBound the include upper bound
     */
    public NumericalValueRange(double lowerBound, double upperBound, int columnIdx, boolean includeLowerBound,
			boolean includeUpperBound) {
		this(lowerBound, upperBound, columnIdx, null, includeLowerBound, includeUpperBound);
	}

	@Override
	public double getLowerBound() {
		return lowerBound;
	}

    /**
     * Sets lower bound.
     *
     * @param lowerBound the lower bound
     */
    public void setLowerBound(double lowerBound) {
		if (lowerBound != this.lowerBound) {
			this.lowerBound = lowerBound;
			fireValueRangeChanged(new ValueRangeChangeEvent(this, ValueRangeChangeType.LOWER_BOUND, lowerBound));
		}
	}

	@Override
	public double getUpperBound() {
		return upperBound;
	}

    /**
     * Sets upper bound.
     *
     * @param upperBound the upper bound
     */
    public void setUpperBound(double upperBound) {
		if (upperBound != this.upperBound) {
			this.upperBound = upperBound;
			fireValueRangeChanged(new ValueRangeChangeEvent(this, ValueRangeChangeType.UPPER_BOUND, upperBound));
		}
	}

    /**
     * Includes lower bound boolean.
     *
     * @return the boolean
     */
    public boolean includesLowerBound() {
		return includeLowerBound;
	}

    /**
     * Includes upper bound boolean.
     *
     * @return the boolean
     */
    public boolean includesUpperBound() {
		return includeUpperBound;
	}

    /**
     * Gets column idx.
     *
     * @return the column idx
     */
    public int getColumnIdx() {
		return columnIdx;
	}

    /**
     * Sets column idx.
     *
     * @param index the index
     */
    public void setColumnIdx(int index) {
		if (index != columnIdx) {
			this.columnIdx = index;
			fireValueRangeChanged(new ValueRangeChangeEvent(this, ValueRangeChangeType.RESET));
		}
	}

	@Override
	public boolean keepRow(DataTableRow row) {
		double value = row.getValue(columnIdx);

		boolean belowLowerBound = false;
		if (includeLowerBound && value < lowerBound) {
			belowLowerBound = true;
		} else if (!includeLowerBound && value <= lowerBound) {
			belowLowerBound = true;
		}

		if (belowLowerBound) {
			return false;
		}

		boolean aboveUpperBound = false;
		if (includeUpperBound && value > upperBound) {
			aboveUpperBound = true;
		} else if (!includeUpperBound && value >= upperBound) {
			aboveUpperBound = true;
		}

		if (aboveUpperBound) {
			return false;
		}

		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		if (!includesLowerBound() || Double.isInfinite(getLowerBound())) {
			builder.append("(");
		} else {
			builder.append("[");
		}

		if (dateFormat != null) {
			builder.append(getDateStringForValue(getLowerBound()));
			builder.append(", ");
			builder.append(getDateStringForValue(getUpperBound()));
		} else {
			double min = DataStructureUtils.roundToPowerOf10(getLowerBound(), lowerPrecision);
			double max = DataStructureUtils.roundToPowerOf10(getUpperBound(), upperPrecision);
			builder.append(DataStructureUtils.getRoundedString(min, lowerPrecision));
			builder.append(", ");
			builder.append(DataStructureUtils.getRoundedString(max, upperPrecision));
		}

		if (!includesUpperBound() || Double.isInfinite(getUpperBound())) {
			builder.append(")");
		} else {
			builder.append("]");
		}
		return builder.toString();
	}

	private String getDateStringForValue(double value) {
		if (Double.isNaN(value)) {
			return I18N.getGUILabel("plotter.unknown_value_label");
		} else if (Double.isInfinite(value)) {
			return Double.toString(value);
		} else {
			return dateFormat.format(new Date((long) value));
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof NumericalValueRange)) {
			return false;
		}

		NumericalValueRange otherRange = (NumericalValueRange) obj;

		if (otherRange.lowerBound != this.lowerBound) {
			return false;
		}

		if (otherRange.upperBound != this.upperBound) {
			return false;
		}

		if (otherRange.includeLowerBound != this.includeLowerBound) {
			return false;
		}

		if (otherRange.includeUpperBound != this.includeUpperBound) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		Double upperBoundDouble = this.upperBound;
		Double lowerBoundDouble = this.lowerBound;
		return upperBoundDouble.hashCode() + lowerBoundDouble.hashCode();
	}

	@Override
	public double getValue() {
		return (getUpperBound() + getLowerBound()) / 2.0;
	}

    /**
     * Sets visual precision.
     *
     * @param lowerPrecision the lower precision
     * @param upperPrecision the upper precision
     */
    public void setVisualPrecision(int lowerPrecision, int upperPrecision) {
		this.lowerPrecision = lowerPrecision;
		this.upperPrecision = upperPrecision;
	}

	@Override
	public NumericalValueRange clone() {
		return new NumericalValueRange(getLowerBound(), getUpperBound(), getColumnIdx(), includesLowerBound(),
				includesUpperBound());
	}

	@Override
	public boolean definesUpperLowerBound() {
		return true;
	}

    /**
     * Gets visual upper precision.
     *
     * @return the visual upper precision
     */
    public int getVisualUpperPrecision() {
		return upperPrecision;
	}

    /**
     * Gets visual lower precision.
     *
     * @return the visual lower precision
     */
    public int getVisualLowerPrecision() {
		return lowerPrecision;
	}

    /**
     * Sets visual upper precision.
     *
     * @param upperPrecision the upper precision
     */
    public void setVisualUpperPrecision(int upperPrecision) {
		this.upperPrecision = upperPrecision;
	}

    /**
     * Sets visual lower precision.
     *
     * @param lowerPrecision the lower precision
     */
    public void setVisualLowerPrecision(int lowerPrecision) {
		this.lowerPrecision = lowerPrecision;
	}

    /**
     * Is date boolean.
     *
     * @return the boolean
     */
    public boolean isDate() {
		return dateFormat != null;
	}

    /**
     * Gets date format.
     *
     * @return the date format
     */
    public DateFormat getDateFormat() {
		return this.dateFormat;
	}

    /**
     * Sets date format.
     *
     * @param dateFormat the date format
     */
    public void setDateFormat(DateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}

}
