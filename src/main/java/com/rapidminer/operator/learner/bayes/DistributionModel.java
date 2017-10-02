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
package com.rapidminer.operator.learner.bayes;

import java.util.Collection;

import com.rapidminer.example.Attribute;
import com.rapidminer.example.ExampleSet;
import com.rapidminer.example.set.ExampleSetUtilities;
import com.rapidminer.operator.ProcessStoppedException;
import com.rapidminer.operator.learner.UpdateablePredictionModel;
import com.rapidminer.tools.math.distribution.Distribution;


/**
 * DistributionModel is a model for learners which estimate distributions of attribute values from
 * example sets like NaiveBayes.
 * <p>
 * Predictions are calculated as product of the conditional probabilities for all attributes times
 * the class probability.
 * <p>
 * The basic learning concept is to simply count occurances of classes and attribute values. This
 * means no propabilities are calculated during the learning step. This is only done before output.
 * Optionally, this calculation can apply a Laplace correction which means in particular that zero
 * probabilities are avoided which would hide information in distributions of other attributes.
 *
 * @author Tobias Malbrecht
 */
public abstract class DistributionModel extends UpdateablePredictionModel {

	private static final long serialVersionUID = -402827845291958569L;

    /**
     * Instantiates a new Distribution model.
     *
     * @param exampleSet         the example set
     * @param setsCompareOption  the sets compare option
     * @param typesCompareOption the types compare option
     */
    public DistributionModel(ExampleSet exampleSet, ExampleSetUtilities.SetsCompareOption setsCompareOption,
			ExampleSetUtilities.TypesCompareOption typesCompareOption) {
		super(exampleSet, setsCompareOption, typesCompareOption);
	}

    /**
     * Get attribute names string [ ].
     *
     * @return the string [ ]
     */
    public abstract String[] getAttributeNames();

    /**
     * Gets number of attributes.
     *
     * @return the number of attributes
     */
    public abstract int getNumberOfAttributes();

    /**
     * Gets lower bound.
     *
     * @param attributeIndex the attribute index
     * @return the lower bound
     */
    public abstract double getLowerBound(int attributeIndex);

    /**
     * Gets upper bound.
     *
     * @param attributeIndex the attribute index
     * @return the upper bound
     */
    public abstract double getUpperBound(int attributeIndex);

    /**
     * Is discrete boolean.
     *
     * @param attributeIndex the attribute index
     * @return the boolean
     */
    public abstract boolean isDiscrete(int attributeIndex);

    /**
     * Gets class indices.
     *
     * @return the class indices
     */
    public abstract Collection<Integer> getClassIndices();

    /**
     * Gets number of classes.
     *
     * @return the number of classes
     */
    public abstract int getNumberOfClasses();

    /**
     * Gets class name.
     *
     * @param index the index
     * @return the class name
     */
    public abstract String getClassName(int index);

    /**
     * Gets distribution.
     *
     * @param classIndex     the class index
     * @param attributeIndex the attribute index
     * @return the distribution
     */
    public abstract Distribution getDistribution(int classIndex, int attributeIndex);

	@Override
	public abstract ExampleSet performPrediction(ExampleSet exampleSet, Attribute predictedLabel) throws ProcessStoppedException;

}
