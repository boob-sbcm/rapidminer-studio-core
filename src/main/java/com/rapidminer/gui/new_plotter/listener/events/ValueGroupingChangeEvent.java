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
import com.rapidminer.gui.new_plotter.configuration.ValueGrouping;

import java.text.DateFormat;


/**
 * The type Value grouping change event.
 *
 * @author Nils Woehler
 */
public class ValueGroupingChangeEvent implements ConfigurationChangeEvent {

    /**
     * The enum Value grouping change type.
     */
    public enum ValueGroupingChangeType {
        /**
         * Categorical value grouping change type.
         */
        CATEGORICAL,			// categorical has changed
        /**
         * Data table column value grouping change type.
         */
        DATA_TABLE_COLUMN,		// the data table column has been changed
        /**
         * Bin count value grouping change type.
         */
        BIN_COUNT,				// the group count has been changed
        /**
         * Reset value grouping change type.
         */
        RESET,					// total reset or exchange of the grouping
        /**
         * Date format value grouping change type.
         */
        DATE_FORMAT
	}

	private final ValueGrouping source;
	private final ValueGroupingChangeType type;

	private Integer binCount = null;
	private DataTableColumn dataTableColumn = null;
	private AggregationWindowing groupingCumulation = null;
	private Boolean categorical = null;

    /**
     * Instantiates a new Value grouping change event.
     *
     * @param source the source
     */
    public ValueGroupingChangeEvent(ValueGrouping source) {
		this.source = source;
		this.type = ValueGroupingChangeType.RESET;
	}

    /**
     * Instantiates a new Value grouping change event.
     *
     * @param source   the source
     * @param binCount the bin count
     */
    public ValueGroupingChangeEvent(ValueGrouping source, Integer binCount) {
		this.source = source;
		this.type = ValueGroupingChangeType.BIN_COUNT;
		this.binCount = binCount;
	}

    /**
     * Instantiates a new Value grouping change event.
     *
     * @param source          the source
     * @param dataTableColumn the data table column
     */
    public ValueGroupingChangeEvent(ValueGrouping source, DataTableColumn dataTableColumn) {
		this.source = source;
		this.type = ValueGroupingChangeType.DATA_TABLE_COLUMN;
		this.dataTableColumn = dataTableColumn;
	}

    /**
     * Instantiates a new Value grouping change event.
     *
     * @param source      the source
     * @param categorical the categorical
     */
    public ValueGroupingChangeEvent(ValueGrouping source, Boolean categorical) {
		this.source = source;
		this.type = ValueGroupingChangeType.CATEGORICAL;
		this.categorical = categorical;
	}

    /**
     * Instantiates a new Value grouping change event.
     *
     * @param source     the source
     * @param dateFormat the date format
     */
    public ValueGroupingChangeEvent(ValueGrouping source, DateFormat dateFormat) {
		this.source = source;
		this.type = ValueGroupingChangeType.DATE_FORMAT;
	}

    /**
     * Gets grouping cumulation.
     *
     * @return the grouping cumulation
     */
    public AggregationWindowing getGroupingCumulation() {
		return groupingCumulation;
	}

    /**
     * Gets data table column.
     *
     * @return the data table column
     */
    public DataTableColumn getDataTableColumn() {
		return dataTableColumn;
	}

    /**
     * Gets bin count.
     *
     * @return the bin count
     */
    public Integer getBinCount() {
		return binCount;
	}

    /**
     * Gets source.
     *
     * @return the source
     */
    public ValueGrouping getSource() {
		return source;
	}

    /**
     * Gets type.
     *
     * @return the type
     */
    public ValueGroupingChangeType getType() {
		return type;
	}

    /**
     * Gets categorical.
     *
     * @return the categorical
     */
    public Boolean getCategorical() {
		return categorical;
	}

	@Override
	public ConfigurationChangeType getConfigurationChangeType() {
		return ConfigurationChangeType.VALUE_GROUPING_CHANGE;
	}

	@Override
	public String toString() {
		return getType().toString();
	}

}
