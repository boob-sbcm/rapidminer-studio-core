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

import com.rapidminer.gui.new_plotter.configuration.LineFormat;
import com.rapidminer.gui.new_plotter.configuration.LineFormat.LineStyle;

import java.awt.Color;


/**
 * The type Line format change event.
 *
 * @author Marius Helf
 */
public class LineFormatChangeEvent {

    /**
     * The enum Line format change type.
     */
    enum LineFormatChangeType {
        /**
         * Width line format change type.
         */
        WIDTH, /**
         * Style line format change type.
         */
        STYLE, /**
         * Color line format change type.
         */
        COLOR,
	}

	private LineFormatChangeType type;
	private float width;
	private LineStyle style = null;
	private Color color = null;

    /**
     * Instantiates a new Line format change event.
     *
     * @param lineFormat the line format
     * @param color      the color
     */
    public LineFormatChangeEvent(LineFormat lineFormat, Color color) {
		this.type = LineFormatChangeType.COLOR;
		this.color = color;
	}

    /**
     * Instantiates a new Line format change event.
     *
     * @param lineFormat the line format
     * @param style      the style
     */
    public LineFormatChangeEvent(LineFormat lineFormat, LineStyle style) {
		this.type = LineFormatChangeType.STYLE;
		this.style = style;
	}

    /**
     * Instantiates a new Line format change event.
     *
     * @param lineFormat the line format
     * @param width      the width
     */
    public LineFormatChangeEvent(LineFormat lineFormat, float width) {
		this.type = LineFormatChangeType.WIDTH;
		this.width = width;
	}

    /**
     * Gets type.
     *
     * @return the type
     */
    public LineFormatChangeType getType() {
		return type;
	}

    /**
     * Gets width.
     *
     * @return the width
     */
    public float getWidth() {
		return width;
	}

    /**
     * Gets style.
     *
     * @return the style
     */
    public LineStyle getStyle() {
		return style;
	}

    /**
     * Gets color.
     *
     * @return the color
     */
    public Color getColor() {
		return color;
	}
}
