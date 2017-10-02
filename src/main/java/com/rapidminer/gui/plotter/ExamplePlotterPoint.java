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

/**
 * Helper class for the plotter point positions and colors. The positions are defined in plotter
 * space.
 *
 * @author Sebastian Land, Ingo Mierswa
 */
public class ExamplePlotterPoint {

	private int x;
	private int y;
	private int dataTableIndex;
	private int currentPertubatedX;
	private int currentPertubatedY;

    /**
     * Instantiates a new Example plotter point.
     *
     * @param dataTableIndex the data table index
     * @param x              the x
     * @param y              the y
     */
    public ExamplePlotterPoint(int dataTableIndex, int x, int y) {
		this.x = x;
		this.y = y;
		this.dataTableIndex = dataTableIndex;
	}

    /**
     * Gets x.
     *
     * @return the x
     */
    public int getX() {
		return x;
	}

    /**
     * Gets data table index.
     *
     * @return the data table index
     */
    public int getDataTableIndex() {
		return dataTableIndex;
	}

    /**
     * Gets y.
     *
     * @return the y
     */
    public int getY() {
		return y;
	}

    /**
     * Gets current pertubated x.
     *
     * @return the current pertubated x
     */
    public int getCurrentPertubatedX() {
		return currentPertubatedX;
	}

    /**
     * Sets current pertubated x.
     *
     * @param currentPertubatedX the current pertubated x
     */
    public void setCurrentPertubatedX(int currentPertubatedX) {
		this.currentPertubatedX = currentPertubatedX;
	}

    /**
     * Gets current pertubated y.
     *
     * @return the current pertubated y
     */
    public int getCurrentPertubatedY() {
		return currentPertubatedY;
	}

    /**
     * Sets current pertubated y.
     *
     * @param currentPertubatedY the current pertubated y
     */
    public void setCurrentPertubatedY(int currentPertubatedY) {
		this.currentPertubatedY = currentPertubatedY;
	}

    /**
     * Contains boolean.
     *
     * @param x the x
     * @param y the y
     * @return the boolean
     */
    public boolean contains(int x, int y) {
		if ((Math.abs(this.getCurrentPertubatedX() - x) < 3) && (Math.abs(this.getCurrentPertubatedY() - y) < 3)) {
			return true;
		}
		return false;
	}
}
