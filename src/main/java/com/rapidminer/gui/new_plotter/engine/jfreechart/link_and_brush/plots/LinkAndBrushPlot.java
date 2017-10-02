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
package com.rapidminer.gui.new_plotter.engine.jfreechart.link_and_brush.plots;

import com.rapidminer.tools.container.Pair;

import java.awt.geom.Point2D;
import java.util.List;

import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.data.Range;


/**
 * The interface Link and brush plot.
 *
 * @author Nils Woehler
 */
public interface LinkAndBrushPlot {

    /**
     * Calculate domain axes zoom list.
     *
     * @param lowerPercent the lower percent
     * @param upperPercent the upper percent
     * @param zoomIn       the zoom in
     * @return the list
     */
    public List<Pair<Integer, Range>> calculateDomainAxesZoom(double lowerPercent, double upperPercent, boolean zoomIn);

    /**
     * Calculate range axes zoom list.
     *
     * @param lowerPercent the lower percent
     * @param upperPercent the upper percent
     * @param info         the info
     * @param source       the source
     * @param zoomIn       the zoom in
     * @return the list
     */
    public List<Pair<Integer, Range>> calculateRangeAxesZoom(double lowerPercent, double upperPercent,
			PlotRenderingInfo info, Point2D source, boolean zoomIn);

    /**
     * Restore auto domain axis bounds list.
     *
     * @param zoomOut the zoom out
     * @return the list
     */
    public List<Pair<Integer, Range>> restoreAutoDomainAxisBounds(boolean zoomOut);

    /**
     * Restore auto range axis bounds list.
     *
     * @param zoomOut the zoom out
     * @return the list
     */
    public List<Pair<Integer, Range>> restoreAutoRangeAxisBounds(boolean zoomOut);

}
