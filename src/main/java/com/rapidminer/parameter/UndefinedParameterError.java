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
package com.rapidminer.parameter;

import com.rapidminer.operator.Operator;
import com.rapidminer.operator.error.ParameterError;


/**
 * This exception will be thrown if a non-optional parameter has no default value and was not
 * defined by the user.
 *
 * @author Ingo Mierswa
 */
public class UndefinedParameterError extends ParameterError {

	private static final long serialVersionUID = -2861031839668411515L;

    /**
     * Creates a new UndefinedParameterError.  @param key the key
     *
     * @param key the key
     */
    public UndefinedParameterError(String key) {
		super(null, 205, key, "");
	}

    /**
     * Instantiates a new Undefined parameter error.
     *
     * @param key               the key
     * @param additionalMessage the additional message
     */
    public UndefinedParameterError(String key, String additionalMessage) {
		super(null, 205, key, additionalMessage);
	}

    /**
     * Instantiates a new Undefined parameter error.
     *
     * @param key      the key
     * @param operator the operator
     */
    public UndefinedParameterError(String key, Operator operator) {
		this(key, operator, "");
	}

    /**
     * Instantiates a new Undefined parameter error.
     *
     * @param key               the key
     * @param operator          the operator
     * @param additionalMessage the additional message
     */
    public UndefinedParameterError(String key, Operator operator, String additionalMessage) {
		super(operator, 217, key, key, operator, additionalMessage);
	}

    /**
     * Instantiates a new Undefined parameter error.
     *
     * @param operator       the operator
     * @param code           the code
     * @param additionalText the additional text
     */
    public UndefinedParameterError(Operator operator, String code, String additionalText) {
		super(operator, code, additionalText);
	}

    /**
     * Instantiates a new Undefined parameter error.
     *
     * @param operator     the operator
     * @param code         the code
     * @param parameterKey the parameter key
     * @param arguments    the arguments
     */
    public UndefinedParameterError(Operator operator, int code, String parameterKey, Object... arguments) {
		super(operator, code, parameterKey, arguments);
	}

}
