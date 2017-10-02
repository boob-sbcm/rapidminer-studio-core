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
package com.rapidminer.operator;

/**
 * General purpose interface to convert objects to IOObjects.
 *
 * @param <T> the type parameter
 * @author Simon Fischer
 */
public interface IOObjectConverter<T> {

    /**
     * Convert io object.
     *
     * @param object the object
     * @return the io object
     * @throws OperatorException the operator exception
     */
    public IOObject convert(T object) throws OperatorException;

}
