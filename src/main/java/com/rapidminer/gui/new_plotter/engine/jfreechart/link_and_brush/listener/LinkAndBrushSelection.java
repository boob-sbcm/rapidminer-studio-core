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
package com.rapidminer.gui.new_plotter.engine.jfreechart.link_and_brush.listener;

import com.rapidminer.gui.new_plotter.data.PlotInstance;
import com.rapidminer.tools.container.Pair;
import org.jfree.data.Range;

import java.util.List;


/**
 * The type Link and brush selection.
 *
 * @author Nils Woehler
 */
public class LinkAndBrushSelection {

    /**
     * The enum Selection type.
     */
    public enum SelectionType {
        /**
         * Zoom in selection type.
         */
        ZOOM_IN, /**
         * Zoom out selection type.
         */
        ZOOM_OUT, /**
         * Restore auto bounds selection type.
         */
        RESTORE_AUTO_BOUNDS, /**
         * Selection selection type.
         */
        SELECTION, /**
         * Restore selection selection type.
         */
        RESTORE_SELECTION, /**
         * Color zoom selection type.
         */
        COLOR_ZOOM, /**
         * Color selection selection type.
         */
        COLOR_SELECTION, /**
         * Restore color selection type.
         */
        RESTORE_COLOR
	}

	private final List<Pair<Integer, Range>> domainAxisRanges;
	private final List<Pair<Integer, Range>> valueAxisRanges;
	private final SelectionType type;
	private Double minColorValue;
	private Double maxColorValue;
	private PlotInstance plotInstance;

    /**
     * Instantiates a new Link and brush selection.
     *
     * @param type             the zooming type
     * @param domainAxisRanges a list of pairs with indices for domain axis and their zoomed ranges
     * @param rangeAxisRanges  a list of pairs with indices for range axis and their zoomed ranges
     */
    public LinkAndBrushSelection(SelectionType type, List<Pair<Integer, Range>> domainAxisRanges,
			List<Pair<Integer, Range>> rangeAxisRanges) {
		this(type, domainAxisRanges, rangeAxisRanges, null, null, null);
	}

    /**
     * Instantiates a new Link and brush selection.
     *
     * @param type             the zooming type
     * @param domainAxisRanges a list of pairs with indices for domain axis and their zoomed ranges
     * @param rangeAxisRanges  a list of pairs with indices for range axis and their zoomed ranges
     * @param minColorValue    the min color value
     * @param maxColorValue    the max color value
     * @param plotInstance     the plot instance
     */
    public LinkAndBrushSelection(SelectionType type, List<Pair<Integer, Range>> domainAxisRanges,
			List<Pair<Integer, Range>> rangeAxisRanges, Double minColorValue, Double maxColorValue, PlotInstance plotInstance) {
		if (domainAxisRanges == null || rangeAxisRanges == null) {
			throw new IllegalArgumentException("Null range axes are not allowed!");
		}
		this.type = type;
		this.domainAxisRanges = domainAxisRanges;
		this.valueAxisRanges = rangeAxisRanges;
		this.minColorValue = minColorValue;
		this.maxColorValue = maxColorValue;
		this.plotInstance = plotInstance;
	}

    /**
     * Gets domain axis ranges.
     *
     * @return the domainRanges
     */
    public List<Pair<Integer, Range>> getDomainAxisRanges() {
		return domainAxisRanges;
	}

    /**
     * Gets domain axis range.
     *
     * @return the first new domain axis range. <code>null</code> if list is empty
     */
    public Pair<Integer, Range> getDomainAxisRange() {
		if (domainAxisRanges.size() > 0) {
			return domainAxisRanges.get(0);
		}
		return null;
	}

    /**
     * Gets type.
     *
     * @return the type
     */
    public SelectionType getType() {
		return type;
	}

    /**
     * Gets value axis ranges.
     *
     * @return the valueRanges
     */
    public List<Pair<Integer, Range>> getValueAxisRanges() {
		return valueAxisRanges;
	}

    /**
     * Gets min color value.
     *
     * @return the min color value
     */
    public Double getMinColorValue() {
		return minColorValue;
	}

    /**
     * Gets max color value.
     *
     * @return the max color value
     */
    public Double getMaxColorValue() {
		return maxColorValue;
	}

    /**
     * Gets plot instance.
     *
     * @return the plot instance
     */
    public PlotInstance getPlotInstance() {
		return plotInstance;
	}

    /**
     * Sets plot instance.
     *
     * @param plotInstance the plot instance
     */
    public void setPlotInstance(PlotInstance plotInstance) {
		this.plotInstance = plotInstance;
	}

}
