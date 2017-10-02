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

import com.rapidminer.gui.new_plotter.configuration.AggregationWindowing;
import com.rapidminer.gui.new_plotter.configuration.DataTableColumn;
import com.rapidminer.gui.new_plotter.configuration.ValueSource;
import com.rapidminer.gui.new_plotter.configuration.ValueSource.SeriesUsageType;
import com.rapidminer.tools.math.function.aggregation.AbstractAggregationFunction.AggregationFunctionType;


/**
 * The type Value source change event.
 *
 * @author Nils Woehler
 */
public class ValueSourceChangeEvent implements ConfigurationChangeEvent {

    /**
     * The enum Value source change type.
     */
    public enum ValueSourceChangeType {
        /**
         * Datatable column map value source change type.
         */
        DATATABLE_COLUMN_MAP, /**
         * Aggregation function map value source change type.
         */
        AGGREGATION_FUNCTION_MAP,		// the aggregation function has been
        /**
         * Uses grouping value source change type.
         */
// exchanged
		USES_GROUPING, /**
         * Series format changed value source change type.
         */
        SERIES_FORMAT_CHANGED,			// the series format of a value source has been
        /**
         * The Aggregation windowing changed.
         */
// changed or exchanged
		AGGREGATION_WINDOWING_CHANGED,  // the aggregation windowing has changed
        /**
         * Use relative utilities value source change type.
         */
        USE_RELATIVE_UTILITIES,			// using relative or absolute values has changed
        /**
         * Updated value source change type.
         */
        UPDATED,						// has updated its values
        /**
         * Auto naming value source change type.
         */
        AUTO_NAMING, /**
         * Label value source change type.
         */
        LABEL
	}

	private final ValueSource source;
	private final ValueSourceChangeType type;

	private Boolean useRelative = null;
	private Boolean usesGrouping = null;
	private ValueRangeChangeEvent valueRangeChanged = null;
	private SeriesFormatChangeEvent seriesFormatChange = null;

	private DataTableColumn column = null;
	private AggregationFunctionType aggregationFunctionType = null;
	private SeriesUsageType seriesUsagType = null;
	private AggregationWindowing aggregationWindowing = null;
	private Boolean autoNaming;
	private String label;

    /**
     * Instantiates a new Value source change event.
     *
     * @param source      the source
     * @param column      the column
     * @param seriesUsage the series usage
     */
    public ValueSourceChangeEvent(ValueSource source, DataTableColumn column, SeriesUsageType seriesUsage) {
		this.source = source;
		this.type = ValueSourceChangeType.DATATABLE_COLUMN_MAP;
		this.column = column;
		this.seriesUsagType = seriesUsage;
	}

    /**
     * Instantiates a new Value source change event.
     *
     * @param source      the source
     * @param function    the function
     * @param seriesUsage the series usage
     */
    public ValueSourceChangeEvent(ValueSource source, AggregationFunctionType function, SeriesUsageType seriesUsage) {
		this.source = source;
		this.type = ValueSourceChangeType.AGGREGATION_FUNCTION_MAP;
		this.seriesUsagType = seriesUsage;
		this.aggregationFunctionType = function;
	}

    /**
     * Instantiates a new Value source change event.
     *
     * @param source the source
     * @param type   the type
     * @param bool   the bool
     */
    public ValueSourceChangeEvent(ValueSource source, ValueSourceChangeType type, Boolean bool) {
		if ((type != ValueSourceChangeType.USE_RELATIVE_UTILITIES) && (type != ValueSourceChangeType.USES_GROUPING)
				&& (type != ValueSourceChangeType.AUTO_NAMING)) {
			throw new RuntimeException(type + " is not allowed calling this constructor.");
		}
		this.source = source;
		this.type = type;
		if (type == ValueSourceChangeType.USE_RELATIVE_UTILITIES) {
			this.useRelative = bool;
		} else if (type == ValueSourceChangeType.USES_GROUPING) {
			this.usesGrouping = bool;
		} else {
			this.autoNaming = bool;
		}

	}

    /**
     * Instantiates a new Value source change event.
     *
     * @param source the source
     * @param label  the label
     */
    public ValueSourceChangeEvent(ValueSource source, String label) {
		this.source = source;
		this.type = ValueSourceChangeType.LABEL;
		this.label = label;
	}

    /**
     * Instantiates a new Value source change event.
     *
     * @param source             the source
     * @param seriesFormatChange the series format change
     */
    public ValueSourceChangeEvent(ValueSource source, SeriesFormatChangeEvent seriesFormatChange) {
		this.source = source;
		this.type = ValueSourceChangeType.SERIES_FORMAT_CHANGED;
		this.seriesFormatChange = seriesFormatChange;
	}

    /**
     * Instantiates a new Value source change event.
     *
     * @param source       the source
     * @param newWindowing the new windowing
     */
    public ValueSourceChangeEvent(ValueSource source, AggregationWindowing newWindowing) {
		this.source = source;
		this.type = ValueSourceChangeType.AGGREGATION_WINDOWING_CHANGED;
		this.aggregationWindowing = newWindowing;
	}

    /**
     * Creates an Event with {@link ValueSourceChangeType} UPDATED
     *
     * @param source the source
     */
    public ValueSourceChangeEvent(ValueSource source) {
		this.source = source;
		this.type = ValueSourceChangeType.UPDATED;
	}

	@Override
	public ConfigurationChangeType getConfigurationChangeType() {
		return ConfigurationChangeType.VALUE_SOURCE_CHANGE;
	}

    /**
     * Gets source.
     *
     * @return the source
     */
    public ValueSource getSource() {
		return source;
	}

    /**
     * Gets type.
     *
     * @return the type
     */
    public ValueSourceChangeType getType() {
		return type;
	}

    /**
     * Gets uses grouping.
     *
     * @return the usesGrouping
     */
    public Boolean getUsesGrouping() {
		return usesGrouping;
	}

    /**
     * Gets auto naming.
     *
     * @return the autoNaming
     */
    public Boolean getAutoNaming() {
		return autoNaming;
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
     * Gets value range changed.
     *
     * @return the valueRangeChanged
     */
    public ValueRangeChangeEvent getValueRangeChanged() {
		return valueRangeChanged;
	}

    /**
     * Gets use relative.
     *
     * @return the useRelative
     */
    public Boolean getUseRelative() {
		return useRelative;
	}

    /**
     * Gets series format change.
     *
     * @return the seriesFormatChange
     */
    public SeriesFormatChangeEvent getSeriesFormatChange() {
		return seriesFormatChange;
	}

    /**
     * Gets data table column.
     *
     * @return the column
     */
    public DataTableColumn getDataTableColumn() {
		return column;
	}

    /**
     * Gets aggregation function type.
     *
     * @return the aggregationFunctionType
     */
    public AggregationFunctionType getAggregationFunctionType() {
		return aggregationFunctionType;
	}

    /**
     * Gets series usag type.
     *
     * @return the seriesUsagType
     */
    public SeriesUsageType getSeriesUsagType() {
		return seriesUsagType;
	}

    /**
     * Gets aggregation windowing.
     *
     * @return the aggregation windowing
     */
    public AggregationWindowing getAggregationWindowing() {
		return aggregationWindowing;
	}

	@Override
	public String toString() {
		return getType().toString();
	}
}
