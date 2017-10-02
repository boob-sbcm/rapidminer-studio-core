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
package com.rapidminer.operator.learner.subgroups.utility;

import com.rapidminer.operator.learner.subgroups.hypothesis.Hypothesis;
import com.rapidminer.operator.learner.subgroups.hypothesis.Rule;

import java.io.Serializable;


/**
 * This is the abstract superclass for all utility functions for calculating the utility of rules.
 *
 * @author Tobias Malbrecht
 */
public abstract class UtilityFunction implements Serializable {

	private static final long serialVersionUID = 1L;

    /**
     * The constant COVERAGE.
     */
    public static final int COVERAGE = 0;

    /**
     * The constant PRECISION.
     */
    public static final int PRECISION = 1;

    /**
     * The constant ACCURACY.
     */
    public static final int ACCURACY = 2;

    /**
     * The constant BIAS.
     */
    public static final int BIAS = 3;

    /**
     * The constant LIFT.
     */
    public static final int LIFT = 4;

    /**
     * The constant BINOMIAL.
     */
    public static final int BINOMIAL = 5;

    /**
     * The constant WRACC.
     */
    public static final int WRACC = 6;

    /**
     * The constant SQUARED.
     */
    public static final int SQUARED = 7;

    /**
     * The constant ODDS.
     */
    public static final int ODDS = 8;

    /**
     * The constant ODDS_RATIO.
     */
    public static final int ODDS_RATIO = 9;

    /**
     * The constant FUNCTIONS.
     */
    public static final String[] FUNCTIONS = { "Coverage", "Precision", "Accuracy", "Bias", "Lift", "Binomial", "WRAcc",
			"Squared", "Odds", "Odds Ratio" };

    /**
     * The constant POSITIVE_CLASS.
     */
    protected static final int POSITIVE_CLASS = 1;

    /**
     * The constant NEGATIVE_CLASS.
     */
    protected static final int NEGATIVE_CLASS = 0;

    /**
     * The Total weight.
     */
    protected double totalWeight = 0.0d;

    /**
     * The Total positive weight.
     */
    protected double totalPositiveWeight = 0.0d;

    /**
     * The Total negative weight.
     */
    protected double totalNegativeWeight = 0.0d;

    /**
     * The Priors.
     */
    double[] priors = new double[2];

    /**
     * Instantiates a new Utility function.
     *
     * @param totalWeight         the total weight
     * @param totalPositiveWeight the total positive weight
     */
    public UtilityFunction(double totalWeight, double totalPositiveWeight) {
		this.totalWeight = totalWeight;
		this.totalPositiveWeight = totalPositiveWeight;
		this.totalNegativeWeight = totalWeight - totalPositiveWeight;
		priors[POSITIVE_CLASS] = totalPositiveWeight / totalWeight;
		priors[NEGATIVE_CLASS] = 1.0d - priors[POSITIVE_CLASS];
	}

    /**
     * Utility double.
     *
     * @param rule the rule
     * @return the double
     */
    public abstract double utility(Rule rule);

    /**
     * Optimistic estimate double.
     *
     * @param hypothesis the hypothesis
     * @return the double
     */
    public abstract double optimisticEstimate(Hypothesis hypothesis);

    /**
     * Gets name.
     *
     * @return the name
     */
    public abstract String getName();

    /**
     * Gets abbreviation.
     *
     * @return the abbreviation
     */
    public abstract String getAbbreviation();

    /**
     * Gets total weight.
     *
     * @return the total weight
     */
    public double getTotalWeight() {
		return totalWeight;
	}

    /**
     * Gets total positive weight.
     *
     * @return the total positive weight
     */
    public double getTotalPositiveWeight() {
		return totalPositiveWeight;
	}

    /**
     * Gets total negative weight.
     *
     * @return the total negative weight
     */
    public double getTotalNegativeWeight() {
		return totalNegativeWeight;
	}

    /**
     * Gets utility function.
     *
     * @param utilityFunctionIndex the utility function index
     * @param totalWeight          the total weight
     * @param totalPositiveWeight  the total positive weight
     * @return the utility function
     */
    public static UtilityFunction getUtilityFunction(int utilityFunctionIndex, double totalWeight, double totalPositiveWeight) {
		switch (utilityFunctionIndex) {
			case UtilityFunction.COVERAGE:
				return new Coverage(totalWeight, totalPositiveWeight);
			case UtilityFunction.PRECISION:
				return new Precision(totalWeight, totalPositiveWeight);
			case UtilityFunction.ACCURACY:
				return new Accuracy(totalWeight, totalPositiveWeight);
			case UtilityFunction.BIAS:
				return new Bias(totalWeight, totalPositiveWeight);
			case UtilityFunction.LIFT:
				return new Lift(totalWeight, totalPositiveWeight);
			case UtilityFunction.BINOMIAL:
				return new Binomial(totalWeight, totalPositiveWeight);
			case UtilityFunction.WRACC:
				return new WRAcc(totalWeight, totalPositiveWeight);
			case UtilityFunction.SQUARED:
				return new Squared(totalWeight, totalPositiveWeight);
			case UtilityFunction.ODDS:
				return new Odds(totalWeight, totalPositiveWeight);
			case UtilityFunction.ODDS_RATIO:
				return new OddsRatio(totalWeight, totalPositiveWeight);
		}
		return new Coverage(totalWeight, totalPositiveWeight);
	}

    /**
     * Gets utility function class.
     *
     * @param utilityFunctionIndex the utility function index
     * @return the utility function class
     */
    public static Class<? extends UtilityFunction> getUtilityFunctionClass(int utilityFunctionIndex) {
		switch (utilityFunctionIndex) {
			case UtilityFunction.COVERAGE:
				return Coverage.class;
			case UtilityFunction.PRECISION:
				return Precision.class;
			case UtilityFunction.ACCURACY:
				return Accuracy.class;
			case UtilityFunction.BIAS:
				return Bias.class;
			case UtilityFunction.LIFT:
				return Lift.class;
			case UtilityFunction.BINOMIAL:
				return Binomial.class;
			case UtilityFunction.WRACC:
				return WRAcc.class;
			case UtilityFunction.SQUARED:
				return Squared.class;
			case UtilityFunction.ODDS:
				return Odds.class;
			case UtilityFunction.ODDS_RATIO:
				return OddsRatio.class;
		}
		return null;
	}

    /**
     * Get utility functions utility function [ ].
     *
     * @param totalWeight         the total weight
     * @param totalPositiveWeight the total positive weight
     * @return the utility function [ ]
     */
    public static UtilityFunction[] getUtilityFunctions(double totalWeight, double totalPositiveWeight) {
		UtilityFunction[] utilities = new UtilityFunction[FUNCTIONS.length];
		for (int i = 0; i < FUNCTIONS.length; i++) {
			utilities[i] = getUtilityFunction(i, totalWeight, totalPositiveWeight);
		}
		return utilities;
	}

    /**
     * Get utility function classes class [ ].
     *
     * @return the class [ ]
     */
    @SuppressWarnings("unchecked")
	public static Class<? extends UtilityFunction>[] getUtilityFunctionClasses() {
		Class<? extends UtilityFunction>[] utilityFunctionClasses = new Class[FUNCTIONS.length];
		for (int i = 0; i < FUNCTIONS.length; i++) {
			utilityFunctionClasses[i] = getUtilityFunctionClass(i);
		}
		return utilityFunctionClasses;
	}

	@Override
	public String toString() {
		return getName();
	}
}
