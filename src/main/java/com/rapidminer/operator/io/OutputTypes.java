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
package com.rapidminer.operator.io;

/**
 * This enumeration contains the possible save types.
 *
 * @author Ingo Mierswa
 */
public interface OutputTypes {

    /**
     * The constant OUTPUT_TYPE_XML.
     */
    public static final int OUTPUT_TYPE_XML = 0;
    /**
     * The constant OUTPUT_TYPE_XML_ZIPPED.
     */
    public static final int OUTPUT_TYPE_XML_ZIPPED = 1;
    /**
     * The constant OUTPUT_TYPE_BINARY.
     */
    public static final int OUTPUT_TYPE_BINARY = 2;

    /**
     * The constant OUTPUT_TYPES.
     */
    public static final String[] OUTPUT_TYPES = { "XML", "XML Zipped", "Binary" };

}
