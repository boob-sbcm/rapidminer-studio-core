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
package com.rapidminer.gui.new_plotter.engine.jfreechart.legend;

import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.text.AttributedString;

import org.jfree.chart.LegendItem;


/**
 * A {@link LegendItem} which additionally contains a custom shape.
 *
 * @author Marius Helf
 */
public class CustomLegendItem extends LegendItem {

	private static final long serialVersionUID = 1L;
	private Shape shape;

	// private boolean isShapeOutlineVisible;

    /**
     * Instantiates a new Custom legend item.
     *
     * @param label               the label
     * @param description         the description
     * @param toolTipText         the tool tip text
     * @param urlText             the url text
     * @param shapeVisible        the shape visible
     * @param shape               the shape
     * @param shapeFilled         the shape filled
     * @param fillPaint           the fill paint
     * @param shapeOutlineVisible the shape outline visible
     * @param outlinePaint        the outline paint
     * @param outlineStroke       the outline stroke
     * @param lineVisible         the line visible
     * @param line                the line
     * @param lineStroke          the line stroke
     * @param linePaint           the line paint
     */
    public CustomLegendItem(AttributedString label, String description, String toolTipText, String urlText,
			boolean shapeVisible, Shape shape, boolean shapeFilled, Paint fillPaint, boolean shapeOutlineVisible,
			Paint outlinePaint, Stroke outlineStroke, boolean lineVisible, Shape line, Stroke lineStroke, Paint linePaint) {
		super(label, description, toolTipText, urlText, shapeVisible, shape, shapeFilled, fillPaint, shapeOutlineVisible,
				outlinePaint, outlineStroke, lineVisible, line, lineStroke, linePaint);
		this.shape = shape;
	}

    /**
     * Instantiates a new Custom legend item.
     *
     * @param label         the label
     * @param description   the description
     * @param toolTipText   the tool tip text
     * @param urlText       the url text
     * @param shape         the shape
     * @param fillPaint     the fill paint
     * @param outlineStroke the outline stroke
     * @param outlinePaint  the outline paint
     */
    public CustomLegendItem(AttributedString label, String description, String toolTipText, String urlText, Shape shape,
			Paint fillPaint, Stroke outlineStroke, Paint outlinePaint) {
		super(label, description, toolTipText, urlText, shape, fillPaint, outlineStroke, outlinePaint);
		this.shape = shape;
	}

    /**
     * Instantiates a new Custom legend item.
     *
     * @param label       the label
     * @param description the description
     * @param toolTipText the tool tip text
     * @param urlText     the url text
     * @param shape       the shape
     * @param fillPaint   the fill paint
     */
    public CustomLegendItem(AttributedString label, String description, String toolTipText, String urlText, Shape shape,
			Paint fillPaint) {
		super(label, description, toolTipText, urlText, shape, fillPaint);
		this.shape = shape;
	}

    /**
     * Instantiates a new Custom legend item.
     *
     * @param label       the label
     * @param description the description
     * @param toolTipText the tool tip text
     * @param urlText     the url text
     * @param line        the line
     * @param lineStroke  the line stroke
     * @param linePaint   the line paint
     */
    public CustomLegendItem(AttributedString label, String description, String toolTipText, String urlText, Shape line,
			Stroke lineStroke, Paint linePaint) {
		super(label, description, toolTipText, urlText, line, lineStroke, linePaint);
	}

    /**
     * Instantiates a new Custom legend item.
     *
     * @param label the label
     * @param paint the paint
     */
    public CustomLegendItem(String label, Paint paint) {
		super(label, paint);
	}

    /**
     * Instantiates a new Custom legend item.
     *
     * @param label               the label
     * @param description         the description
     * @param toolTipText         the tool tip text
     * @param urlText             the url text
     * @param shapeVisible        the shape visible
     * @param shape               the shape
     * @param shapeFilled         the shape filled
     * @param fillPaint           the fill paint
     * @param shapeOutlineVisible the shape outline visible
     * @param outlinePaint        the outline paint
     * @param outlineStroke       the outline stroke
     * @param lineVisible         the line visible
     * @param line                the line
     * @param lineStroke          the line stroke
     * @param linePaint           the line paint
     */
    public CustomLegendItem(String label, String description, String toolTipText, String urlText, boolean shapeVisible,
			Shape shape, boolean shapeFilled, Paint fillPaint, boolean shapeOutlineVisible, Paint outlinePaint,
			Stroke outlineStroke, boolean lineVisible, Shape line, Stroke lineStroke, Paint linePaint) {
		super(label, description, toolTipText, urlText, shapeVisible, shape, shapeFilled, fillPaint, shapeOutlineVisible,
				outlinePaint, outlineStroke, lineVisible, line, lineStroke, linePaint);
		this.shape = shape;
	}

    /**
     * Instantiates a new Custom legend item.
     *
     * @param label         the label
     * @param description   the description
     * @param toolTipText   the tool tip text
     * @param urlText       the url text
     * @param shape         the shape
     * @param fillPaint     the fill paint
     * @param outlineStroke the outline stroke
     * @param outlinePaint  the outline paint
     */
    public CustomLegendItem(String label, String description, String toolTipText, String urlText, Shape shape,
			Paint fillPaint, Stroke outlineStroke, Paint outlinePaint) {
		super(label, description, toolTipText, urlText, shape, fillPaint, outlineStroke, outlinePaint);
		this.shape = shape;
	}

    /**
     * Instantiates a new Custom legend item.
     *
     * @param label       the label
     * @param description the description
     * @param toolTipText the tool tip text
     * @param urlText     the url text
     * @param shape       the shape
     * @param fillPaint   the fill paint
     */
    public CustomLegendItem(String label, String description, String toolTipText, String urlText, Shape shape,
			Paint fillPaint) {
		super(label, description, toolTipText, urlText, shape, fillPaint);
		this.shape = shape;
	}

    /**
     * Instantiates a new Custom legend item.
     *
     * @param label       the label
     * @param description the description
     * @param toolTipText the tool tip text
     * @param urlText     the url text
     * @param line        the line
     * @param lineStroke  the line stroke
     * @param linePaint   the line paint
     */
    public CustomLegendItem(String label, String description, String toolTipText, String urlText, Shape line,
			Stroke lineStroke, Paint linePaint) {
		super(label, description, toolTipText, urlText, line, lineStroke, linePaint);
	}

    /**
     * Instantiates a new Custom legend item.
     *
     * @param label the label
     */
    public CustomLegendItem(String label) {
		super(label);
	}

	@Override
	public void setShape(Shape shape) {
		this.shape = shape;
	}

	@Override
	public Shape getShape() {
		return shape;
	}

	// @Override
	// public boolean isShapeOutlineVisible() {
	// return isShapeOutlineVisible;
	// }
	//
	// public void setShapeOutlineVisible(boolean visible) {
	// isShapeOutlineVisible = visible;
	// }
}
