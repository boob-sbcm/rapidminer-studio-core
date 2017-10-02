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

/**
 * The interface Configuration change event.
 *
 * @author Nils Woehler
 */
public interface ConfigurationChangeEvent {

    /**
     * The enum Configuration change type.
     */
    public enum ConfigurationChangeType {
        /**
         * Plot configuration change configuration change type.
         */
        PLOT_CONFIGURATION_CHANGE, /**
         * Range axis config change configuration change type.
         */
        RANGE_AXIS_CONFIG_CHANGE, /**
         * Value source change configuration change type.
         */
        VALUE_SOURCE_CHANGE, /**
         * Series format change configuration change type.
         */
        SERIES_FORMAT_CHANGE, /**
         * Value grouping change configuration change type.
         */
        VALUE_GROUPING_CHANGE, /**
         * Value range change configuration change type.
         */
        VALUE_RANGE_CHANGE, /**
         * Dimension config change configuration change type.
         */
        DIMENSION_CONFIG_CHANGE, /**
         * Domain dimension change configuration change type.
         */
        DOMAIN_DIMENSION_CHANGE
	}

    /**
     * Gets configuration change type.
     *
     * @return the configuration change type
     */
    public ConfigurationChangeType getConfigurationChangeType();

}
