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

import java.awt.*;
import java.awt.geom.Point2D;


/**
 * A color plotter point which can be used to identify a point in a two-dimensional space with an id
 * and a specific color.
 *
 * @author Ingo Mierswa
 */
public class ColorPlotterPoint {

	private final ScatterPlotter plotter;

	private String id = null;

	private double x;

	private double y;

	private double color;

	private Color borderColor;

    /**
     * Instantiates a new Color plotter point.
     *
     * @param plotter     the plotter
     * @param id          the id
     * @param x           the x
     * @param y           the y
     * @param color       the color
     * @param borderColor the border color
     */
    public ColorPlotterPoint(ScatterPlotter plotter, String id, double x, double y, double color, Color borderColor) {
		this.plotter = plotter;
		this.id = id;
		this.x = x;
		this.y = y;
		this.color = color;
		this.borderColor = borderColor;
	}

    /**
     * Gets id.
     *
     * @return the id
     */
    public String getId() {
		return id;
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
     * Sets x.
     *
     * @param x the x
     */
    public void setX(double x) {
		this.x = x;
	}

    /**
     * Sets y.
     *
     * @param y the y
     */
    public void setY(double y) {
		this.y = y;
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

    /**
     * Contains boolean.
     *
     * @param x the x
     * @param y the y
     * @return the boolean
     */
    public boolean contains(int x, int y) {
		Point2D point = this.plotter.transform.transform(new Point2D.Double(this.plotter.xTransformation.transform(this.x),
				this.plotter.yTransformation.transform(this.y)), null);
		if ((Math.abs(point.getX() - x) < 2) && (Math.abs(point.getY() - y) < 2)) {
			return true;
		} else {
			return false;
		}
	}

    /**
     * Is in boolean.
     *
     * @param minX the min x
     * @param maxX the max x
     * @param minY the min y
     * @param maxY the max y
     * @return the boolean
     */
    public boolean isIn(double minX, double maxX, double minY, double maxY) {
		return (x >= minX) && (x <= maxX) && (y >= minY) && (y <= maxY);
	}
}
