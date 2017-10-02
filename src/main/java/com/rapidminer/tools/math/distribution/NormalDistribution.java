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
package com.rapidminer.tools.math.distribution;

import com.rapidminer.tools.Tools;


/**
 * This class represents a gaussian normal distribution.
 *
 * @author Tobias Malbrecht, Sebastian Land <<<<<<< NormalDistribution.java
 * @version $Id : NormalDistribution.java,v 1.3.2.3 2009-04-08 14:40:23 tobiasmalbrecht Exp $ =======          >>>>>>> 1.3.2.2
 */
public class NormalDistribution extends ContinuousDistribution {

	private static final long serialVersionUID = -1819042904676198636L;

	private static final double BOUND_FACTOR = 5;

	private static final double SQRT_FACTOR = Math.sqrt(2 * Math.PI);

    /**
     * The Mean.
     */
    protected double mean;

    /**
     * The Standard deviation.
     */
    protected double standardDeviation;

    /**
     * Instantiates a new Normal distribution.
     *
     * @param mean              the mean
     * @param standardDeviation the standard deviation
     */
    public NormalDistribution(double mean, double standardDeviation) {
		this.mean = mean;
		this.standardDeviation = standardDeviation;
	}

	@Override
	public String getAttributeName() {
		return null;
	}

    /**
     * Gets probability.
     *
     * @param mean              the mean
     * @param standardDeviation the standard deviation
     * @param value             the value
     * @return the probability
     */
    public static double getProbability(double mean, double standardDeviation, double value) {
		double base = (value - mean) / standardDeviation;
		return Math.exp(-0.5 * (base * base)) / (standardDeviation * SQRT_FACTOR);
	}

    /**
     * Gets log probability.
     *
     * @param mean              the mean
     * @param standardDeviation the standard deviation
     * @param value             the value
     * @return the log probability
     */
    public static double getLogProbability(double mean, double standardDeviation, double value) {
		double base = (value - mean) / standardDeviation;
		return -Math.log(standardDeviation * SQRT_FACTOR) - 0.5 * (base * base);
	}

    /**
     * Gets lower bound.
     *
     * @param mean              the mean
     * @param standardDeviation the standard deviation
     * @return the lower bound
     */
    public static final double getLowerBound(double mean, double standardDeviation) {
		return mean - BOUND_FACTOR * standardDeviation;
	}

    /**
     * Gets upper bound.
     *
     * @param mean              the mean
     * @param standardDeviation the standard deviation
     * @return the upper bound
     */
    public static final double getUpperBound(double mean, double standardDeviation) {
		return mean + BOUND_FACTOR * standardDeviation;
	}

	@Override
	public double getProbability(double value) {
		return getProbability(mean, standardDeviation, value);
	}

    /**
     * Gets mean.
     *
     * @return the mean
     */
    public double getMean() {
		return mean;
	}

    /**
     * Gets standard deviation.
     *
     * @return the standard deviation
     */
    public double getStandardDeviation() {
		return standardDeviation;
	}

    /**
     * Gets variance.
     *
     * @return the variance
     */
    public double getVariance() {
		return standardDeviation * standardDeviation;
	}

	@Override
	public double getLowerBound() {
		return getLowerBound(mean, standardDeviation);
	}

	@Override
	public double getUpperBound() {
		return getUpperBound(mean, standardDeviation);
	}

	@Override
	public String toString() {
		return ("Normal distribution --> mean: " + Tools.formatNumber(mean) + ", standard deviation: " + Tools
				.formatNumber(standardDeviation));
	}

	@Override
	public int getNumberOfParameters() {
		return 2;
	}

	@Override
	public String getParameterName(int index) {
		if (index == 0) {
			return "mean";
		}
		return "standard deviation";
	}

	@Override
	public double getParameterValue(int index) {
		if (index == 0) {
			return mean;
		}
		return standardDeviation;
	}
}
