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
package com.rapidminer.gui.new_plotter.configuration.event;

import com.rapidminer.gui.new_plotter.configuration.AxisParallelLineConfiguration;
import com.rapidminer.gui.new_plotter.configuration.AxisParallelLinesConfiguration;
import com.rapidminer.gui.new_plotter.event.AxisParallelLineConfigurationChangeEvent;


/**
 * The type Axis parallel lines configuration change event.
 *
 * @author Marius Helf
 */
public class AxisParallelLinesConfigurationChangeEvent {

    /**
     * The enum Axis parallel line configurations change type.
     */
    public enum AxisParallelLineConfigurationsChangeType {
        /**
         * Line added axis parallel line configurations change type.
         */
        LINE_ADDED, /**
         * Line removed axis parallel line configurations change type.
         */
        LINE_REMOVED, /**
         * Line changed axis parallel line configurations change type.
         */
        LINE_CHANGED,
	}

	private AxisParallelLineConfigurationsChangeType type;
	private AxisParallelLinesConfiguration source;
	private AxisParallelLineConfiguration lineConfiguration;

    /**
     * Instantiates a new Axis parallel lines configuration change event.
     *
     * @param source the source
     * @param type   the type
     * @param line   the line
     */
    public AxisParallelLinesConfigurationChangeEvent(AxisParallelLinesConfiguration source,
			AxisParallelLineConfigurationsChangeType type, AxisParallelLineConfiguration line) {
		this.source = source;
		this.type = type;
		this.lineConfiguration = line;
	}

    /**
     * Instantiates a new Axis parallel lines configuration change event.
     *
     * @param source the source
     * @param e      the e
     */
    public AxisParallelLinesConfigurationChangeEvent(AxisParallelLinesConfiguration source,
			AxisParallelLineConfigurationChangeEvent e) {
		this.type = AxisParallelLineConfigurationsChangeType.LINE_CHANGED;
		this.source = source;
	}

    /**
     * Gets type.
     *
     * @return the type
     */
    public AxisParallelLineConfigurationsChangeType getType() {
		return type;
	}

    /**
     * Gets source.
     *
     * @return the source
     */
    public AxisParallelLinesConfiguration getSource() {
		return source;
	}

    /**
     * Gets line configuration.
     *
     * @return the line configuration
     */
    public AxisParallelLineConfiguration getLineConfiguration() {
		return lineConfiguration;
	}
}
