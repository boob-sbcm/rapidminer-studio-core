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
package com.rapidminer.operator.learner.tree.criterions;

import com.rapidminer.example.Attribute;
import com.rapidminer.example.Example;
import com.rapidminer.example.ExampleSet;
import com.rapidminer.operator.OperatorException;


/**
 * The criterion for a splitted example set. Possible implementations are for example accuracy or
 * information gain.
 *
 * @author Sebastian Land, Ingo Mierswa
 */
public interface Criterion {

    /**
     * Gets nominal benefit.
     *
     * @param exampleSet the example set
     * @param attribute  the attribute
     * @return the nominal benefit
     * @throws OperatorException the operator exception
     */
    public double getNominalBenefit(ExampleSet exampleSet, Attribute attribute) throws OperatorException;

    /**
     * Gets numerical benefit.
     *
     * @param exampleSet the example set
     * @param attribute  the attribute
     * @param splitValue the split value
     * @return the numerical benefit
     * @throws OperatorException the operator exception
     */
    public double getNumericalBenefit(ExampleSet exampleSet, Attribute attribute, double splitValue)
			throws OperatorException;

    /**
     * Supports incremental calculation boolean.
     *
     * @return the boolean
     */
    public boolean supportsIncrementalCalculation();

    /**
     * Start incremental calculation.
     *
     * @param exampleSet the example set
     * @throws OperatorException the operator exception
     */
    public void startIncrementalCalculation(ExampleSet exampleSet) throws OperatorException;

    /**
     * Swap example.
     *
     * @param example the example
     */
    public void swapExample(Example example);

    /**
     * Gets incremental benefit.
     *
     * @return the incremental benefit
     */
    public double getIncrementalBenefit();

    /**
     * This method will return the calculated benefit if the weights would have distributed over the
     * labels as given. The first index specifies the split fraction, the second index the label.
     * Henve the first index is always between 0 and 1 included for numerical splits, since only two
     * sides can occur there. For splits on nominal attributes, the number of split sides is
     * determined by the number of possible values.
     *
     * @param weightCounts the weight counts
     * @return the benefit
     */
    public double getBenefit(double[][] weightCounts);
}
