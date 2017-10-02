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
package com.rapidminer.operator.learner.subgroups.hypothesis;

import com.rapidminer.example.Example;
import com.rapidminer.operator.learner.subgroups.utility.UtilityFunction;
import com.rapidminer.tools.Tools;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;


/**
 * A rule for subgroup discovery.
 *
 * @author Tobias Malbrecht
 */
public class Rule implements Serializable {

	private static final long serialVersionUID = 1L;

    /**
     * The Hypothesis.
     */
    Hypothesis hypothesis;

    /**
     * The Prediction.
     */
    Literal prediction;

    /**
     * The Utility map.
     */
    LinkedHashMap<UtilityFunction, Double> utilityMap = new LinkedHashMap<>();

    /**
     * Instantiates a new Rule.
     *
     * @param hypothesis the hypothesis
     * @param prediction the prediction
     */
    public Rule(Hypothesis hypothesis, Literal prediction) {
		this.hypothesis = hypothesis;
		this.prediction = prediction;
	}

    /**
     * Applicable boolean.
     *
     * @param example the example
     * @return the boolean
     */
    public boolean applicable(Example example) {
		return hypothesis.applicable(example);
	}

    /**
     * Gets covered weight.
     *
     * @return the covered weight
     */
    public double getCoveredWeight() {
		return hypothesis.getCoveredWeight();
	}

    /**
     * Gets positive weight.
     *
     * @return the positive weight
     */
    public double getPositiveWeight() {
		return hypothesis.getPositiveWeight();
	}

    /**
     * Gets negative weight.
     *
     * @return the negative weight
     */
    public double getNegativeWeight() {
		return hypothesis.getCoveredWeight() - hypothesis.getPositiveWeight();
	}

    /**
     * Gets prediction weight.
     *
     * @return the prediction weight
     */
    public double getPredictionWeight() {
		if (predictsPositive()) {
			return getPositiveWeight();
		} else {
			return getNegativeWeight();
		}
	}

    /**
     * Predicts positive boolean.
     *
     * @return the boolean
     */
    public boolean predictsPositive() {
		return prediction.getValue() == prediction.getAttribute().getMapping().getPositiveIndex();
	}

    /**
     * Gets prediction.
     *
     * @return the prediction
     */
    public double getPrediction() {
		return prediction.getValue();
	}

    /**
     * Gets hypothesis.
     *
     * @return the hypothesis
     */
    public Hypothesis getHypothesis() {
		return hypothesis;
	}

    /**
     * Sets utility.
     *
     * @param function the function
     * @param utility  the utility
     */
    public void setUtility(UtilityFunction function, double utility) {
		utilityMap.put(function, utility);
	}

    /**
     * Gets utility.
     *
     * @param functionClass the function class
     * @return the utility
     */
    public double getUtility(Class<? extends UtilityFunction> functionClass) {
		for (UtilityFunction function : utilityMap.keySet()) {
			if (function.getClass().equals(functionClass)) {
				return utilityMap.get(function);
			}
		}
		return Double.NaN;
	}

    /**
     * Gets utility function.
     *
     * @param functionClass the function class
     * @return the utility function
     */
    public UtilityFunction getUtilityFunction(Class<? extends UtilityFunction> functionClass) {
		for (UtilityFunction function : utilityMap.keySet()) {
			if (function.getClass().equals(functionClass)) {
				return function;
			}
		}
		return null;
	}

    /**
     * Gets utility functions.
     *
     * @return the utility functions
     */
    public Collection<UtilityFunction> getUtilityFunctions() {
		return utilityMap.keySet();
	}

	@Override
	public boolean equals(Object object) {
		if (object == null) {
			return false;
		}
		if (this.getClass() != object.getClass()) {
			return false;
		}
		Rule otherRule = (Rule) object;
		return hypothesis.equals(otherRule.hypothesis) && prediction.equals(otherRule.prediction);
	}

	private String utilityString() {
		StringBuffer stringBuffer = new StringBuffer("[");
		stringBuffer.append("Pos=" + getPositiveWeight() + ", ");
		stringBuffer.append("Neg=" + getNegativeWeight() + ", ");
		stringBuffer.append("Size=" + getCoveredWeight() + ", ");
		for (UtilityFunction function : utilityMap.keySet()) {
			stringBuffer.append(function.getAbbreviation() + "=" + Tools.formatIntegerIfPossible(utilityMap.get(function))
					+ ", ");
		}
		stringBuffer.subSequence(0, stringBuffer.length() - 2);
		stringBuffer.append("]");
		return stringBuffer.toString();
	}

    /**
     * To string scored string.
     *
     * @return the string
     */
    public String toStringScored() {
		return toString() + "  " + utilityString();
	}

	@Override
	public String toString() {
		return hypothesis + " --> " + prediction;
	}

    /**
     * Gets premise.
     *
     * @return the premise
     */
    public Hypothesis getPremise() {
		return hypothesis;
	}

    /**
     * Gets conclusion.
     *
     * @return the conclusion
     */
    public Literal getConclusion() {
		return prediction;
	}
}
