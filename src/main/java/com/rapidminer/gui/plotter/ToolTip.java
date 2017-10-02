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
 * Collects all necessary information about tool tips for plotters.
 *
 * @author Ingo Mierswa
 */
public class ToolTip {

	private String text;

	private int xPos;

	private int yPos;

    /**
     * Instantiates a new Tool tip.
     *
     * @param toolTip the tool tip
     * @param xPos    the x pos
     * @param yPos    the y pos
     */
    public ToolTip(String toolTip, int xPos, int yPos) {
		this.text = toolTip;
		this.xPos = xPos;
		this.yPos = yPos;
	}

    /**
     * Gets text.
     *
     * @return the text
     */
    public String getText() {
		return text;
	}

    /**
     * Gets x.
     *
     * @return the x
     */
    public int getX() {
		return xPos;
	}

    /**
     * Gets y.
     *
     * @return the y
     */
    public int getY() {
		return yPos;
	}
}
