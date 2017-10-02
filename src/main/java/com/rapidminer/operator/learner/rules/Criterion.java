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
package com.rapidminer.operator.learner.rules;

import com.rapidminer.example.Example;
import com.rapidminer.example.ExampleSet;


/**
 * Calculates the benefit for the given example set.
 *
 * @author Sebastian Land, Ingo Mierswa
 */
public interface Criterion {

    /**
     * Get benefit double [ ].
     *
     * @param coveredSet   the covered set
     * @param uncoveredSet the uncovered set
     * @param labelName    the label name
     * @return the double [ ]
     */
    public double[] getBenefit(ExampleSet coveredSet, ExampleSet uncoveredSet, String labelName);

    /**
     * Get online benefit double [ ].
     *
     * @param example    the example
     * @param labelIndex the label index
     * @return the double [ ]
     */
    public double[] getOnlineBenefit(Example example, int labelIndex);

    /**
     * Get online benefit double [ ].
     *
     * @param example the example
     * @return the double [ ]
     */
    public double[] getOnlineBenefit(Example example);

    /**
     * Reinit online counting.
     *
     * @param exampleSet the example set
     */
    public void reinitOnlineCounting(ExampleSet exampleSet);

    /**
     * Update.
     *
     * @param example the example
     */
    public void update(Example example);
}
