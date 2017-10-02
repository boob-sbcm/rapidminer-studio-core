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
package com.rapidminer.gui.dialog;

/**
 * This interface wraps text components which provide the possibility to select segments.
 *
 * @author Ingo Mierswa
 */
public interface SearchableTextComponent {

    /**
     * Select.
     *
     * @param start the start
     * @param end   the end
     */
    public void select(int start, int end);

    /**
     * Request focus.
     */
    public void requestFocus();

    /**
     * Sets caret position.
     *
     * @param pos the pos
     */
    public void setCaretPosition(int pos);

    /**
     * Gets caret position.
     *
     * @return the caret position
     */
    public int getCaretPosition();

    /**
     * Gets text.
     *
     * @return the text
     */
    public String getText();

    /**
     * Replace selection.
     *
     * @param newString the new string
     */
    public void replaceSelection(String newString);

    /**
     * Can handle carriage return boolean.
     *
     * @return the boolean
     */
    public boolean canHandleCarriageReturn();
}
