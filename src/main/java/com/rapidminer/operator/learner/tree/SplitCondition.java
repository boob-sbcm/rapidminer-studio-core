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
package com.rapidminer.operator.learner.tree;

import com.rapidminer.example.Example;

import java.io.Serializable;


/**
 * A condition for a split in decision tree, rules etc. Subclasses should also implement a toString
 * method.
 *
 * @author Sebastian Land, Ingo Mierswa
 */
public interface SplitCondition extends Serializable {

    /**
     * Gets attribute name.
     *
     * @return the attribute name
     */
    public String getAttributeName();

    /**
     * Gets relation.
     *
     * @return the relation
     */
    public String getRelation();

    /**
     * Gets value string.
     *
     * @return the value string
     */
    public String getValueString();

    /**
     * Test boolean.
     *
     * @param example the example
     * @return the boolean
     */
    public boolean test(Example example);

}
