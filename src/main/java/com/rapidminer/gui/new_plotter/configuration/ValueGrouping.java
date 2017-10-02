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
package com.rapidminer.gui.new_plotter.configuration;

import com.rapidminer.datatable.DataTable;
import com.rapidminer.gui.new_plotter.ChartConfigurationException;
import com.rapidminer.gui.new_plotter.configuration.DataTableColumn.ValueType;
import com.rapidminer.gui.new_plotter.listener.ValueGroupingListener;
import com.rapidminer.gui.new_plotter.utility.ValueRange;
import com.rapidminer.tools.I18N;

import java.text.DateFormat;
import java.util.List;


/**
 * Groups values by predefined criteria, e.g. binning of a numerical value source.
 *
 * @author Marius Helf, Nils Woehler
 */
public interface ValueGrouping {

    /**
     * The type Value grouping factory.
     */
    public class ValueGroupingFactory {

		private final static int binCount = 5;

        /**
         * Gets value grouping.
         *
         * @param type            the type
         * @param dataTableColumn the data table column
         * @param categorical     the categorical
         * @param dateFormat      the date format
         * @return the value grouping
         * @throws ChartConfigurationException the chart configuration exception
         */
        public static ValueGrouping getValueGrouping(GroupingType type, DataTableColumn dataTableColumn,
				boolean categorical, DateFormat dateFormat) throws ChartConfigurationException {
			switch (type) {
				case DISTINCT_VALUES:
					return new DistinctValueGrouping(dataTableColumn, categorical, dateFormat);
				case EQUAL_DATA_FRACTION:
					return new EqualDataFractionGrouping(dataTableColumn, binCount, categorical, dateFormat);
				case EQUIDISTANT_FIXED_BIN_COUNT:
					return new EquidistantFixedBinCountBinning(binCount, Double.NaN, Double.NaN, dataTableColumn,
							categorical, dateFormat);
				case NONE:
					return null;
				default:
					throw new RuntimeException("This cannot happen");
			}

		}
	}

    /**
     * The enum Grouping type.
     */
    public enum GroupingType {
        /**
         * None grouping type.
         */
        NONE(I18N.getGUILabel("plotter.grouping_type.NONE.label")), /**
         * Equidistant fixed bin count grouping type.
         */
        EQUIDISTANT_FIXED_BIN_COUNT(I18N
				.getGUILabel("plotter.grouping_type.EQUIDISTANT_FIXED_BIN_COUNT.label")), /**
         * Distinct values grouping type.
         */
        DISTINCT_VALUES(I18N
				.getGUILabel("plotter.grouping_type.DISTINCT_VALUES.label")), /**
         * Equal data fraction grouping type.
         */
        EQUAL_DATA_FRACTION(I18N
				.getGUILabel("plotter.grouping_type.EQUAL_DATA_FRACTION.label"));

		private final String name;

		private GroupingType(String name) {
			this.name = name;
		}

        /**
         * Gets name.
         *
         * @return The display name of the enum value.
         */
        public String getName() {
			return name;
		}
	}

    /**
     * Gets grouping model.
     *
     * @param data       the data
     * @param upperBoud  the upper boud
     * @param lowerBound the lower bound
     * @return the grouping model
     */
    public List<ValueRange> getGroupingModel(DataTable data, double upperBoud, double lowerBound);

    /**
     * Is categorical boolean.
     *
     * @return the boolean
     */
    public boolean isCategorical();

    /**
     * Returns the type of the grouping, like Distinct values or Equal data fraction grouping.
     *
     * @return the grouping type
     */
    public GroupingType getGroupingType();

    /**
     * Add listener.
     *
     * @param l the l
     */
    public void addListener(ValueGroupingListener l);

    /**
     * Remove listener.
     *
     * @param l the l
     */
    public void removeListener(ValueGroupingListener l);

	public ValueGrouping clone();

    /**
     * The type of the groups created by this ValueGrouping.
     *
     * @return the domain type
     */
    public ValueType getDomainType();

    /**
     * Returns true iff this ValueGrouping guarantees that each ValueRange in each possible
     * resulting grouping model defines upper and lower bounds.
     *
     * @return the boolean
     */
    public boolean definesUpperLowerBounds();

    /**
     * Gets date format.
     *
     * @return the date format
     */
    public DateFormat getDateFormat();

    /**
     * Sets date format.
     *
     * @param dateFormat the date format
     */
    public void setDateFormat(DateFormat dateFormat);

	@Override
	public boolean equals(Object obj);
}
