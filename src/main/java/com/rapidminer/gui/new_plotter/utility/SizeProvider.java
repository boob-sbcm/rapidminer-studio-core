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

/**
 * The interface Size provider.
 *
 * @author Marius Helf
 */
public interface SizeProvider {

    /**
     * Gets scaling factor for value.
     *
     * @param value the value
     * @return scaling factor for value
     */
    public double getScalingFactorForValue(double value);

    /**
     * Supports categorical values boolean.
     *
     * @return the boolean
     */
    public boolean supportsCategoricalValues();

    /**
     * Supports numerical values boolean.
     *
     * @return the boolean
     */
    public boolean supportsNumericalValues();

	public SizeProvider clone();

    /**
     * Gets min scaling factor.
     *
     * @return the min scaling factor
     */
    public double getMinScalingFactor();

    /**
     * Gets max scaling factor.
     *
     * @return the max scaling factor
     */
    public double getMaxScalingFactor();
}
