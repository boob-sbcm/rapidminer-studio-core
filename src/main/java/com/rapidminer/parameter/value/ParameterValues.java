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
package com.rapidminer.parameter.value;

import com.rapidminer.operator.Operator;
import com.rapidminer.parameter.ParameterType;
import com.rapidminer.parameter.ParameterTypeTupel;


/**
 * Allows the specification of parameter values as a basis of e.g. optimization.
 *
 * @author Tobias Malbrecht
 */
public abstract class ParameterValues {

    /**
     * The Operator.
     */
    protected transient Operator operator;

    /**
     * The Type.
     */
    protected transient ParameterType type;

    /**
     * The Key.
     */
    protected String key;

    /**
     * Instantiates a new Parameter values.
     *
     * @param operator the operator
     * @param type     the type
     */
    public ParameterValues(Operator operator, ParameterType type) {
		this.operator = operator;
		this.type = type;
	}

    /**
     * Gets operator.
     *
     * @return the operator
     */
    public Operator getOperator() {
		return operator;
	}

    /**
     * Gets parameter type.
     *
     * @return the parameter type
     */
    public ParameterType getParameterType() {
		return type;
	}

    /**
     * Gets key.
     *
     * @return the key
     */
    public String getKey() {
		return ParameterTypeTupel.transformTupel2String(operator.getName(), type.getKey());
	}

    /**
     * Gets number of values.
     *
     * @return the number of values
     */
    public abstract int getNumberOfValues();

    /**
     * Gets values string.
     *
     * @return the values string
     */
    public abstract String getValuesString();

    /**
     * Move.
     *
     * @param index     the index
     * @param direction the direction
     */
    public abstract void move(int index, int direction);

    /**
     * Get values array string [ ].
     *
     * @return the string [ ]
     */
    public String[] getValuesArray() {
		return null;
	}

    /**
     * Is valid numerical parameter boolean.
     *
     * @param value the value
     * @return the boolean
     */
    public static boolean isValidNumericalParameter(String value) {
		if (value.startsWith("%{") && value.endsWith("}")) {
			return true;
		}
		try {
			Double.valueOf(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

}
