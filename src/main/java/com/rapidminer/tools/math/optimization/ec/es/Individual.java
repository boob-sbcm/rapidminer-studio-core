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
package com.rapidminer.tools.math.optimization.ec.es;

import com.rapidminer.operator.performance.PerformanceVector;


/**
 * Individuals store information about the value vectors and the fitness.
 *
 * @author Ingo Mierswa
 */
public class Individual implements Cloneable {

	private double[] values;

	private PerformanceVector fitness;

	private double crowdingDistance;

    /**
     * Instantiates a new Individual.
     *
     * @param values the values
     */
    public Individual(double[] values) {
		this.values = values;
	}

	@Override
	public Object clone() {
		double[] alphaClone = new double[this.values.length];
		System.arraycopy(this.values, 0, alphaClone, 0, this.values.length);
		Individual clone = new Individual(alphaClone);
		return clone;
	}

    /**
     * Sets crowding distance.
     *
     * @param cd the cd
     */
    public void setCrowdingDistance(double cd) {
		this.crowdingDistance = cd;
	}

    /**
     * Gets crowding distance.
     *
     * @return the crowding distance
     */
    public double getCrowdingDistance() {
		return crowdingDistance;
	}

    /**
     * Get values double [ ].
     *
     * @return the double [ ]
     */
    public double[] getValues() {
		return values;
	}

    /**
     * Sets values.
     *
     * @param values the values
     */
    public void setValues(double[] values) {
		this.values = values;
	}

    /**
     * Get fitness values double [ ].
     *
     * @return the double [ ]
     */
    public double[] getFitnessValues() {
		double[] fitnessValues = new double[fitness.getSize()];
		for (int i = 0; i < fitnessValues.length; i++) {
			fitnessValues[i] = fitness.getCriterion(i).getFitness();
		}
		return fitnessValues;
	}

    /**
     * Gets fitness.
     *
     * @return the fitness
     */
    public PerformanceVector getFitness() {
		return fitness;
	}

    /**
     * Sets fitness.
     *
     * @param fitness the fitness
     */
    public void setFitness(PerformanceVector fitness) {
		this.fitness = fitness;
	}

	@Override
	public String toString() {
		StringBuffer result = new StringBuffer("values=[");
		for (int i = 0; i < values.length; i++) {
			if (i != 0) {
				result.append(",");
			}
			result.append(values[i]);
		}
		result.append(", fitness: [");
		double[] fitnessValues = getFitnessValues();
		for (double d : fitnessValues) {
			result.append(d);
		}
		result.append("]");
		return result.toString();
	}
}
