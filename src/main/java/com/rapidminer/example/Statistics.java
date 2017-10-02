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
package com.rapidminer.example;

import java.io.Serializable;


/**
 * The superclass for all attribute statistics objects.
 *
 * @author Ingo Mierswa
 */
public interface Statistics extends Serializable, Cloneable {

    /**
     * The constant UNKNOWN.
     */
    public static final String UNKNOWN = "unknown";
    /**
     * The constant AVERAGE.
     */
    public static final String AVERAGE = "average";
    /**
     * The constant AVERAGE_WEIGHTED.
     */
    public static final String AVERAGE_WEIGHTED = "average_weighted";
    /**
     * The constant VARIANCE.
     */
    public static final String VARIANCE = "variance";
    /**
     * The constant VARIANCE_WEIGHTED.
     */
    public static final String VARIANCE_WEIGHTED = "variance_weighted";
    /**
     * The constant MINIMUM.
     */
    public static final String MINIMUM = "minimum";
    /**
     * The constant MAXIMUM.
     */
    public static final String MAXIMUM = "maximum";
    /**
     * The constant MODE.
     */
    public static final String MODE = "mode";
    /**
     * The constant LEAST.
     */
    public static final String LEAST = "least";
    /**
     * The constant COUNT.
     */
    public static final String COUNT = "count";
    /**
     * The constant SUM.
     */
    public static final String SUM = "sum";
    /**
     * The constant SUM_WEIGHTED.
     */
    public static final String SUM_WEIGHTED = "sum_weighted";

	public Object clone();

    /**
     * Start counting.
     *
     * @param attribute the attribute
     */
    public void startCounting(Attribute attribute);

    /**
     * Count.
     *
     * @param value  the value
     * @param weight the weight
     */
    public void count(double value, double weight);

    /**
     * Handle statistics boolean.
     *
     * @param statisticsName the statistics name
     * @return the boolean
     */
    public boolean handleStatistics(String statisticsName);

    /**
     * Gets statistics.
     *
     * @param attribute      the attribute
     * @param statisticsName the statistics name
     * @param parameter      the parameter
     * @return the statistics
     */
    public double getStatistics(Attribute attribute, String statisticsName, String parameter);

}
