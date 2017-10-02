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
package com.rapidminer.tools.math.container;

import com.rapidminer.tools.Tools;

import java.io.Serializable;


/**
 * The type Range.
 *
 * @author Sebastian Land
 */
public class Range implements Serializable {

	private static final long serialVersionUID = 1L;

	private double lower;
	private double upper;

    /**
     * Instantiates a new Range.
     */
    public Range() {
		this.lower = Double.NaN;
		this.upper = Double.NaN;
	}

    /**
     * Instantiates a new Range.
     *
     * @param lowerBound the lower bound
     * @param upperBound the upper bound
     */
    public Range(double lowerBound, double upperBound) {
		if (lowerBound > upperBound) {
			// TODO: This must be resolved but currently causes some read operators to quit
			// operation.
			// Needs to find solution for this before making it public.
			// throw new IllegalArgumentException("Range was tried to initialized with a " +
			// "lower bound > upper bound. Lower bound = "+lowerBound+" Upper = "+upperBound+".");
		}
		this.lower = lowerBound;
		this.upper = upperBound;
	}

    /**
     * Instantiates a new Range.
     *
     * @param valueRange the value range
     */
    public Range(Range valueRange) {
		if (valueRange != null) {
			this.lower = valueRange.getLower();
			this.upper = valueRange.getUpper();
		} else {
			this.lower = Double.NEGATIVE_INFINITY;
			this.upper = Double.POSITIVE_INFINITY;
		}

	}

    /**
     * This method increases the range size, if the value is not lying in between
     *
     * @param value the value
     */
    public void add(double value) {
		if (value < lower || Double.isNaN(lower)) {
			lower = value;
		}
		if (value > upper || Double.isNaN(upper)) {
			upper = value;
		}
	}

    /**
     * Union.
     *
     * @param range the range
     */
    public void union(Range range) {
		add(range.getLower());
		add(range.getUpper());
	}

    /**
     * Contains boolean.
     *
     * @param value the value
     * @return the boolean
     */
    public boolean contains(double value) {
		return value > lower && value < upper;
	}

	@Override
	public String toString() {
		if (Double.isNaN(lower) || Double.isNaN(upper)) {
			return "\u2205";
		}
		return "[" + Tools.formatIntegerIfPossible(lower) + " \u2013 " + Tools.formatIntegerIfPossible(upper) + "]";
	}

    /**
     * Gets upper.
     *
     * @return the upper
     */
    public double getUpper() {
		return upper;
	}

    /**
     * Gets lower.
     *
     * @return the lower
     */
    public double getLower() {
		return lower;
	}

    /**
     * Gets size.
     *
     * @return the size
     */
    public double getSize() {
		return upper - lower;
	}

    /**
     * Sets range.
     *
     * @param lower the lower
     * @param upper the upper
     */
    public void setRange(double lower, double upper) {
		this.lower = lower;
		this.upper = upper;
	}

    /**
     * Sets range.
     *
     * @param theNewRange the the new range
     */
    public void setRange(Range theNewRange) {
		setRange(theNewRange.getLower(), theNewRange.getUpper());
	}

    /**
     * Sets lower.
     *
     * @param newLowerBound the new lower bound
     */
    public void setLower(double newLowerBound) {
		this.lower = newLowerBound;
	}

    /**
     * Sets upper.
     *
     * @param newUpperBound the new upper bound
     */
    public void setUpper(double newUpperBound) {
		this.upper = newUpperBound;
	}

	@Override
	public boolean equals(Object range) {
		if (range instanceof Range) {
			Range other = (Range) range;
			return upper == other.upper && lower == other.lower;
		}
		return false;
	}

    /**
     * Contains boolean.
     *
     * @param range the range
     * @return the boolean
     */
    public boolean contains(Range range) {
		return (this.lower <= range.lower && this.upper >= range.upper);
	}

}
