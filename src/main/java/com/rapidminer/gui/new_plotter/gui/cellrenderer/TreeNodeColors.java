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
package com.rapidminer.gui.new_plotter.gui.cellrenderer;

import java.awt.*;


/**
 * The type Tree node colors.
 *
 * @author Nils Woehler
 */
public class TreeNodeColors {

    /**
     * Gets nominal color.
     *
     * @return the nominal color
     */
    public static Color getNominalColor() {
		return new Color(203, 0, 204);
	}

    /**
     * Gets numerical color.
     *
     * @return the numerical color
     */
    public static Color getNumericalColor() {
		return Color.blue;
	}

    /**
     * Gets date color.
     *
     * @return the date color
     */
    public static Color getDateColor() {
		return new Color(1, 102, 0);
	}

    /**
     * Gets invalid color.
     *
     * @return the invalid color
     */
    public static Color getInvalidColor() {
		return new Color(204, 0, 0);
	}

    /**
     * Gets unknown color.
     *
     * @return the unknown color
     */
    public static Color getUnknownColor() {
		return Color.gray.brighter();
	}

    /**
     * Gets warning color.
     *
     * @return the warning color
     */
    public static Color getWarningColor() {
		return new Color(204, 102, 0);
	}
}
