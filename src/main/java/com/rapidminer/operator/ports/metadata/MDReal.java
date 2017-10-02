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
package com.rapidminer.operator.ports.metadata;

/**
 * The type Md real.
 *
 * @author Simon Fischer
 */
public class MDReal extends MDNumber<Double> {

	private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new Md real.
     */
    public MDReal() {
		super();
	}

    /**
     * Instantiates a new Md real.
     *
     * @param number the number
     */
    public MDReal(Double number) {
		super(number);
	}

    /**
     * Instantiates a new Md real.
     *
     * @param real the real
     */
    public MDReal(MDReal real) {
		super(real);
	}

	@Override
	public MDReal add(Double add) {
		setNumber(getNumber() + add);
		return this;
	}

	@Override
	public MDReal multiply(double factor) {
		Double current = getNumber();
		if (current != null) {
			setNumber(current * factor);
		}
		return this;
	}

	@Override
	public MDReal copy() {
		return new MDReal(this);
	}

}
