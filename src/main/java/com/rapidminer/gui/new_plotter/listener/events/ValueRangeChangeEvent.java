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
package com.rapidminer.gui.new_plotter.listener.events;

import com.rapidminer.gui.new_plotter.utility.ValueRange;


/**
 * The type Value range change event.
 *
 * @author Nils Woehler
 */
public class ValueRangeChangeEvent implements ConfigurationChangeEvent {

    /**
     * The enum Value range change type.
     */
    public enum ValueRangeChangeType {
        /**
         * Upper bound value range change type.
         */
        UPPER_BOUND, /**
         * Lower bound value range change type.
         */
        LOWER_BOUND, /**
         * Use upper bound value range change type.
         */
        USE_UPPER_BOUND, /**
         * Use lower bound value range change type.
         */
        USE_LOWER_BOUND, /**
         * Reset value range change type.
         */
        RESET,
	}

	private final ValueRange source;
	private final ValueRangeChangeType type;

	private Double upperBound = null;
	private Double lowerBound = null;
	private boolean useLowerBound;
	private boolean useUpperBound;

    /**
     * Allowed {@link ValueRangeChangeType}s are RESET or ABOUT_TO_CHANGE_AUTORANGE
     *
     * @param source the source
     * @param type   the type
     */
    public ValueRangeChangeEvent(ValueRange source, ValueRangeChangeType type) {
		if ((type != ValueRangeChangeType.RESET)) {
			throw new RuntimeException(type + " is not allowed calling this constructor.");
		}
		this.source = source;
		this.type = type;
	}

    /**
     * Allowed {@link ValueRangeChangeType}s are UPPER_BOUND or LOWER_BOUND
     *
     * @param source the source
     * @param type   the type
     * @param bound  the bound
     */
    public ValueRangeChangeEvent(ValueRange source, ValueRangeChangeType type, Double bound) {
		if ((type != ValueRangeChangeType.UPPER_BOUND) && (type != ValueRangeChangeType.LOWER_BOUND)) {
			throw new RuntimeException(type + " is not allowed calling this constructor.");
		}
		this.type = type;
		this.source = source;
		if (type == ValueRangeChangeType.UPPER_BOUND) {
			upperBound = bound;
		} else {
			lowerBound = bound;
		}
	}

    /**
     * Instantiates a new Value range change event.
     *
     * @param source      the source
     * @param type        the type
     * @param useBoundary the use boundary
     */
    public ValueRangeChangeEvent(ValueRange source, ValueRangeChangeType type, boolean useBoundary) {
		if ((type != ValueRangeChangeType.USE_UPPER_BOUND) && (type != ValueRangeChangeType.USE_LOWER_BOUND)) {
			throw new RuntimeException(type + " is not allowed calling this constructor.");
		}
		this.type = type;
		this.source = source;
		if (type == ValueRangeChangeType.USE_LOWER_BOUND) {
			useLowerBound = useBoundary;
		} else {
			useUpperBound = useBoundary;
		}
	}

    /**
     * Gets source.
     *
     * @return the source
     */
    public ValueRange getSource() {
		return source;
	}

    /**
     * Gets type.
     *
     * @return the type
     */
    public ValueRangeChangeType getType() {
		return type;
	}

    /**
     * Gets upper bound.
     *
     * @return the upperBound
     */
    public Double getUpperBound() {
		return upperBound;
	}

    /**
     * Gets lower bound.
     *
     * @return the lowerBound
     */
    public Double getLowerBound() {
		return lowerBound;
	}

	@Override
	public ConfigurationChangeType getConfigurationChangeType() {
		return ConfigurationChangeType.VALUE_RANGE_CHANGE;
	}

    /**
     * Gets use lower bound.
     *
     * @return the useLowerBound
     */
    public boolean getUseLowerBound() {
		return useLowerBound;
	}

    /**
     * Gets use upper bound.
     *
     * @return the useUpperBound
     */
    public boolean getUseUpperBound() {
		return useUpperBound;
	}

	@Override
	public String toString() {
		return getType().toString();
	}
}
