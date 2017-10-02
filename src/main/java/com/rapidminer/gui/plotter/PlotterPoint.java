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

import java.awt.Color;


/**
 * Helper class for the plotter point positions and colors.
 *
 * @author Ingo Mierswa
 */
public class PlotterPoint {

	private double x;
	private double y;
	private double color;
	private Color borderColor;

    /**
     * Instantiates a new Plotter point.
     *
     * @param x           the x
     * @param y           the y
     * @param color       the color
     * @param borderColor the border color
     */
    public PlotterPoint(double x, double y, double color, Color borderColor) {
		this.x = x;
		this.y = y;
		this.color = color;
		this.borderColor = borderColor;
	}

    /**
     * Gets x.
     *
     * @return the x
     */
    public double getX() {
		return x;
	}

    /**
     * Gets y.
     *
     * @return the y
     */
    public double getY() {
		return y;
	}

    /**
     * Gets color.
     *
     * @return the color
     */
    public double getColor() {
		return color;
	}

    /**
     * Gets border color.
     *
     * @return the border color
     */
    public Color getBorderColor() {
		return borderColor;
	}
}
