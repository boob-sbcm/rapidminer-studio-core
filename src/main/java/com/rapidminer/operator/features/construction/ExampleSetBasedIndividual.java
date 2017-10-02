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
package com.rapidminer.operator.features.construction;

import com.rapidminer.example.AttributeWeights;
import com.rapidminer.example.set.AttributeWeightedExampleSet;
import com.rapidminer.operator.performance.PerformanceVector;


/**
 * Individuals contain all necessary informations about example sets for population based search
 * heuristics, including the performance. Each individiual can also handle a crowding distance for
 * multi-objecitve optimization approaches.
 *
 * @author Ingo Mierswa
 */
public class ExampleSetBasedIndividual {

	/** The example set. */
	private AttributeWeightedExampleSet exampleSet;

	/**
	 * The performance this example set has achieved during evaluation. Null if no evaluation has
	 * been performed so far.
	 */
	private PerformanceVector performanceVector = null;

	/** The crowding distance can used for multiobjective optimization schemes. */
	private double crowdingDistance = Double.NaN;

	/**
	 * Some search schemes use attribute weights to guide the search point operations.
	 */
	private AttributeWeights attributeWeights = null;

    /**
     * Creates a new individual.  @param exampleSet the example set
     *
     * @param exampleSet the example set
     */
    public ExampleSetBasedIndividual(AttributeWeightedExampleSet exampleSet) {
		this.exampleSet = exampleSet;
	}

    /**
     * Gets example set.
     *
     * @return the example set
     */
    public AttributeWeightedExampleSet getExampleSet() {
		return this.exampleSet;
	}

    /**
     * Gets performance.
     *
     * @return the performance
     */
    public PerformanceVector getPerformance() {
		return performanceVector;
	}

    /**
     * Sets performance.
     *
     * @param performanceVector the performance vector
     */
    public void setPerformance(PerformanceVector performanceVector) {
		this.performanceVector = performanceVector;
	}

    /**
     * Gets crowding distance.
     *
     * @return the crowding distance
     */
    public double getCrowdingDistance() {
		return this.crowdingDistance;
	}

    /**
     * Sets crowding distance.
     *
     * @param crowdingDistance the crowding distance
     */
    public void setCrowdingDistance(double crowdingDistance) {
		this.crowdingDistance = crowdingDistance;
	}

    /**
     * Gets attribute weights.
     *
     * @return the attribute weights
     */
    public AttributeWeights getAttributeWeights() {
		return this.attributeWeights;
	}

    /**
     * Sets attribute weights.
     *
     * @param weights the weights
     */
    public void setAttributeWeights(AttributeWeights weights) {
		this.attributeWeights = weights;
	}

	@Override
	public String toString() {
		return "#" + exampleSet.getNumberOfUsedAttributes();
	}
}
