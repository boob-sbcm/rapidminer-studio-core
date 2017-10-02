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
package com.rapidminer.operator.preprocessing.transformation.aggregation;

import com.rapidminer.example.Attribute;
import com.rapidminer.example.Example;
import com.rapidminer.example.table.DataRow;


/**
 * The interface Aggregator.
 *
 * @author Sebastian Land
 */
public interface Aggregator {

    /**
     * This will count the given example to the group this {@link Aggregator} belongs to. The
     * respective attribute will be queried from the {@link AggregationFunction} this was created
     * by.
     *
     * @param example the example
     */
    public void count(Example example);

    /**
     * This does the same as {@link #count(Example)}, but will take the weight of the current
     * example into account.
     *
     * @param example the example
     * @param weight  the weight
     */
    public void count(Example example, double weight);

    /**
     * This will set the result value into the data row onto the position of the given attribute.
     *
     * @param attribute the attribute
     * @param row       the row
     */
    public void set(Attribute attribute, DataRow row);

}
