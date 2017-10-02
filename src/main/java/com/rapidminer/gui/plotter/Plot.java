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
package com.rapidminer.gui.plotter;

import com.rapidminer.gui.plotter.PlotterAdapter.LineStyle;
import com.rapidminer.gui.plotter.PlotterAdapter.PointStyle;

import java.util.LinkedList;


/**
 * This collection consists of all {@link com.rapidminer.gui.plotter.ColorPlotterPoint}s for a plot.
 *
 * @author Ingo Mierswa
 */
class Plot extends LinkedList<ColorPlotterPoint> {

	private static final long serialVersionUID = 3408030697850939063L;

	private String name;

	private int styleIndex;

    /**
     * Instantiates a new Plot.
     *
     * @param name       the name
     * @param styleIndex the style index
     */
    public Plot(String name, int styleIndex) {
		this.name = name;
		this.styleIndex = styleIndex;
	}

    /**
     * Gets line style.
     *
     * @return the line style
     */
    public LineStyle getLineStyle() {
		return PlotterAdapter.LINE_STYLES[styleIndex % PlotterAdapter.LINE_STYLES.length];
	}

    /**
     * Gets point style.
     *
     * @return the point style
     */
    public PointStyle getPointStyle() {
		return PlotterAdapter.POINT_STYLES[styleIndex % PlotterAdapter.POINT_STYLES.length];
	}

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
		return name;
	}
}
