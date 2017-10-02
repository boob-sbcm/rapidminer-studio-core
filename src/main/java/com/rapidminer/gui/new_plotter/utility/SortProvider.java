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

import java.util.List;


/**
 * The interface Sort provider.
 *
 * @author Marius Helf
 */
public interface SortProvider {

    /**
     * The enum Sort criterion.
     */
    public enum SortCriterion {
        /**
         * Numerical sort criterion.
         */
        NUMERICAL, /**
         * Nominal sort criterion.
         */
        NOMINAL, /**
         * None sort criterion.
         */
        NONE
	}

    /**
     * The enum Sort order.
     */
    public enum SortOrder {
        /**
         * None sort order.
         */
        NONE, /**
         * Ascending sort order.
         */
        ASCENDING, /**
         * Descending sort order.
         */
        DESCENDING
	}

    /**
     * Sets sort criterion.
     *
     * @param sortCriterion the sort criterion
     */
    public void setSortCriterion(SortCriterion sortCriterion);

    /**
     * Sets sort order.
     *
     * @param sortOrder the sort order
     */
    public void setSortOrder(SortOrder sortOrder);

    /**
     * Gets sort criterion.
     *
     * @return the sort criterion
     */
    public SortCriterion getSortCriterion();

    /**
     * Gets sort order.
     *
     * @return the sort order
     */
    public SortOrder getSortOrder();

    /**
     * Sorts the provided list of values. Might change the original list.
     *
     * @param values the values
     * @return the list
     */
    public List<Double> sortValues(List<Double> values);

    /**
     * Sorts the provided list of {@link ValueRange}s. Might change the original list.
     *
     * @param valueGroups the value groups
     * @return the list
     */
    public List<ValueRange> sortGroups(List<ValueRange> valueGroups);

	public SortProvider clone();
}
