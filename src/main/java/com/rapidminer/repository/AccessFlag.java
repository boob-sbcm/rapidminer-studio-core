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
package com.rapidminer.repository;

/**
 * The enum Access flag.
 *
 * @author Simon Fischer
 */
public enum AccessFlag {
    /**
     * Ignore access flag.
     */
    IGNORE('%', "shape_circle.png"), /**
     * Grant access flag.
     */
    GRANT('+', "ok.png"), /**
     * Reject access flag.
     */
    REJECT('-', "sign_forbidden.png");

	private AccessFlag(char symbol, String icon) {
		this.symbol = symbol;
		this.icon = icon;
	}

	private char symbol;
	private String icon;

    /**
     * Gets symbol.
     *
     * @return the symbol
     */
    public char getSymbol() {
		return symbol;
	}

    /**
     * Gets icon.
     *
     * @return the icon
     */
    public String getIcon() {
		return icon;
	}

    /**
     * Rotate access flag.
     *
     * @return the access flag
     */
    public AccessFlag rotate() {
		return AccessFlag.values()[(this.ordinal() + 1) % AccessFlag.values().length];
	}
}
