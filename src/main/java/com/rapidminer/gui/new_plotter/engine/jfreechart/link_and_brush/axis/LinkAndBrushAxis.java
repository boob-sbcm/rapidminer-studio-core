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
package com.rapidminer.gui.new_plotter.engine.jfreechart.link_and_brush.axis;

import org.jfree.data.Range;


/**
 * The interface Link and brush axis.
 *
 * @author Nils Woehler
 */
public interface LinkAndBrushAxis {

    /**
     * Calculate zoom range range.
     *
     * @param lowerPercent the lower percent
     * @param upperPercent the upper percent
     * @param zoomIn       the zoom in
     * @return the range
     */
    public Range calculateZoomRange(double lowerPercent, double upperPercent, boolean zoomIn);

    /**
     * Restore auto range range.
     *
     * @param zoomOut the zoom out
     * @return the range
     */
    public Range restoreAutoRange(boolean zoomOut);

    /**
     * Save upper bound.
     *
     * @param max         the max
     * @param maxWOMargin the max wo margin
     */
    public void saveUpperBound(double max, double maxWOMargin);

    /**
     * Save lower bound.
     *
     * @param min         the min
     * @param minWOMargin the min wo margin
     */
    public void saveLowerBound(double min, double minWOMargin);
}
