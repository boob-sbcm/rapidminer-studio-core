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

import com.rapidminer.operator.Operator;
import com.rapidminer.operator.OperatorException;
import com.rapidminer.operator.UserError;


/**
 * The type Malformed repository location exception.
 *
 * @author Simon Fischer
 */
public class MalformedRepositoryLocationException extends OperatorException {

	private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new Malformed repository location exception.
     *
     * @param message the message
     */
    public MalformedRepositoryLocationException(String message) {
		super(message);
	}

    /**
     * Make user error user error.
     *
     * @param operator the operator
     * @return the user error
     */
    public UserError makeUserError(Operator operator) {
		return new UserError(operator, this, 319, this.getMessage());
	}
}
