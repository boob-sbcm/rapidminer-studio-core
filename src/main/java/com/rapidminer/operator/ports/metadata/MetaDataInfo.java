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

import java.io.Serializable;


/**
 * An answer to a meta data question that cannot always be answered with certainty. In that case
 * {@link #UNKNOWN} is returned.
 *
 * @author Simon Fischer
 */
public enum MetaDataInfo implements Serializable {

    /**
     * Yes meta data info.
     */
    YES, /**
     * No meta data info.
     */
    NO, /**
     * Unknown meta data info.
     */
    UNKNOWN;

    /**
     * From boolean meta data info.
     *
     * @param b the b
     * @return the meta data info
     */
    public static MetaDataInfo fromBoolean(boolean b) {
		return b ? YES : NO;
	}

}
