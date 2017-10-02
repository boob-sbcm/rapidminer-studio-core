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

import com.rapidminer.gui.new_plotter.configuration.RangeAxisConfig;
import com.rapidminer.gui.new_plotter.configuration.ValueSource;
import com.rapidminer.gui.new_plotter.configuration.event.AxisParallelLinesConfigurationChangeEvent;

import java.awt.*;


/**
 * The type Range axis config change event.
 *
 * @author Nils Woehler
 */
public class RangeAxisConfigChangeEvent implements ConfigurationChangeEvent {

    /**
     * The enum Range axis config change type.
     */
    public enum RangeAxisConfigChangeType {
        /**
         * Value source added range axis config change type.
         */
        VALUE_SOURCE_ADDED, // a value source was added
        /**
         * Value source removed range axis config change type.
         */
        VALUE_SOURCE_REMOVED, // a value source was removed
        /**
         * Value source moved range axis config change type.
         */
        VALUE_SOURCE_MOVED, // a value sources index was changed
        /**
         * Value source changed range axis config change type.
         */
        VALUE_SOURCE_CHANGED, /**
         * Cleared range axis config change type.
         */
        CLEARED, // all value sources were removed
        /**
         * Label range axis config change type.
         */
        LABEL, // range axis label has changed
        /**
         * Scaling range axis config change type.
         */
        SCALING, // range axis scaling has changed
        /**
         * Auto naming range axis config change type.
         */
        AUTO_NAMING,  // auto naming has been toggled
        /**
         * Range changed range axis config change type.
         */
        RANGE_CHANGED, // there has been some changed concerning the value range
        /**
         * Crosshair lines changed range axis config change type.
         */
        CROSSHAIR_LINES_CHANGED,
	}

	private final RangeAxisConfigChangeType type;
	private final RangeAxisConfig source;

	private ValueSource valueSource = null;
	private Integer index = null;

	private String label = null;

	private Boolean logarithmic = null;
	private Boolean includeZero = null;
	private Boolean autoNaming = null;

	private ValueSourceChangeEvent valueSourceChange = null;
	private Color rangeAxisLineColor;
	private Float rangeAxisLineWidth;

	private ValueRangeChangeEvent valueRangeChange = null;
	private AxisParallelLinesConfigurationChangeEvent crosshairChange;

    /**
     * Creates a {@link RangeAxisConfigChangeEvent} with {@link RangeAxisConfigChangeType} CLEARED.
     *
     * @param source the source
     */
    public RangeAxisConfigChangeEvent(RangeAxisConfig source) {
		this.source = source;
		this.type = RangeAxisConfigChangeType.CLEARED;
	}

    /**
     * Allowed {@link RangeAxisConfigChangeType}s are VALUE_SOURCE_ADDED, VALUE_SOURCE_REMOVED or
     * VALUE_SOURCE_MOVED
     *
     * @param source      the source
     * @param type        the type
     * @param valueSource the value source
     * @param index       the index
     */
    public RangeAxisConfigChangeEvent(RangeAxisConfig source, RangeAxisConfigChangeType type, ValueSource valueSource,
			Integer index) {
		this.type = type;
		if ((type != RangeAxisConfigChangeType.VALUE_SOURCE_ADDED)
				&& (type != RangeAxisConfigChangeType.VALUE_SOURCE_REMOVED)
				&& (type != RangeAxisConfigChangeType.VALUE_SOURCE_MOVED)) {
			throw new RuntimeException(type + " is not allowed calling this constructor.");
		}
		this.source = source;
		this.valueSource = valueSource;
		this.index = index;
	}

    /**
     * Instantiates a new Range axis config change event.
     *
     * @param source the source
     * @param label  the label
     */
    public RangeAxisConfigChangeEvent(RangeAxisConfig source, String label) {
		this.type = RangeAxisConfigChangeType.LABEL;
		this.source = source;
		this.label = label;
	}

    /**
     * Allowed {@link RangeAxisConfigChangeType}s are INCLUDE_ZERO, AUTO_NAMING or SCALING
     *
     * @param source the source
     * @param type   the type
     * @param bool   the bool
     */
    public RangeAxisConfigChangeEvent(RangeAxisConfig source, RangeAxisConfigChangeType type, Boolean bool) {
		this.type = type;
		if ((type != RangeAxisConfigChangeType.SCALING) && (type != RangeAxisConfigChangeType.AUTO_NAMING)) {
			throw new RuntimeException(type + " is not allowed calling this constructor.");
		}
		this.source = source;
		if (type == RangeAxisConfigChangeType.SCALING) {
			this.logarithmic = bool;
		} else {
			this.autoNaming = bool;
		}
	}

    /**
     * Instantiates a new Range axis config change event.
     *
     * @param source           the source
     * @param valueRangeChange the value range change
     */
    public RangeAxisConfigChangeEvent(RangeAxisConfig source, ValueRangeChangeEvent valueRangeChange) {
		this.source = source;
		this.type = RangeAxisConfigChangeType.RANGE_CHANGED;
		this.valueRangeChange = valueRangeChange;
	}

    /**
     * Instantiates a new Range axis config change event.
     *
     * @param source            the source
     * @param valueSourceChange the value source change
     */
    public RangeAxisConfigChangeEvent(RangeAxisConfig source, ValueSourceChangeEvent valueSourceChange) {
		this.source = source;
		this.type = RangeAxisConfigChangeType.VALUE_SOURCE_CHANGED;
		this.valueSourceChange = valueSourceChange;
	}

    /**
     * Instantiates a new Range axis config change event.
     *
     * @param rangeAxisConfig the range axis config
     * @param e               the e
     */
    public RangeAxisConfigChangeEvent(RangeAxisConfig rangeAxisConfig, AxisParallelLinesConfigurationChangeEvent e) {
		this.type = RangeAxisConfigChangeType.CROSSHAIR_LINES_CHANGED;
		this.source = rangeAxisConfig;
		this.crosshairChange = e;
	}

    /**
     * Gets value source change.
     *
     * @return the value source change
     */
    public ValueSourceChangeEvent getValueSourceChange() {
		return valueSourceChange;
	}

    /**
     * Gets value source.
     *
     * @return the valueSource
     */
    public ValueSource getValueSource() {
		return valueSource;
	}

    /**
     * Gets index.
     *
     * @return the index
     */
    public Integer getIndex() {
		return index;
	}

    /**
     * Gets label.
     *
     * @return the label
     */
    public String getLabel() {
		return label;
	}

    /**
     * Gets logarithmic.
     *
     * @return the logarithmic
     */
    public Boolean getLogarithmic() {
		return logarithmic;
	}

    /**
     * Gets include zero.
     *
     * @return the includeZero
     */
    public Boolean getIncludeZero() {
		return includeZero;
	}

    /**
     * Gets type.
     *
     * @return the type
     */
    public RangeAxisConfigChangeType getType() {
		return type;
	}

    /**
     * Gets value range change.
     *
     * @return the valueRangeChange
     */
    public ValueRangeChangeEvent getValueRangeChange() {
		return valueRangeChange;
	}

    /**
     * Gets source.
     *
     * @return the source
     */
    public RangeAxisConfig getSource() {
		return source;
	}

	@Override
	public ConfigurationChangeType getConfigurationChangeType() {
		return ConfigurationChangeType.RANGE_AXIS_CONFIG_CHANGE;
	}

    /**
     * Gets auto naming.
     *
     * @return the auto naming
     */
    public Boolean getAutoNaming() {
		return autoNaming;
	}

	@Override
	public String toString() {
		return getType().toString();
	}

    /**
     * Gets range axis line color.
     *
     * @return range axis line color
     */
    public Color getRangeAxisLineColor() {
		return rangeAxisLineColor;
	}

    /**
     * Gets range axis line width.
     *
     * @return range axis line width
     */
    public Float getRangeAxisLineWidth() {
		return rangeAxisLineWidth;
	}

    /**
     * Gets crosshair change.
     *
     * @return the crosshair change
     */
    public AxisParallelLinesConfigurationChangeEvent getCrosshairChange() {
		return crosshairChange;
	}
}
