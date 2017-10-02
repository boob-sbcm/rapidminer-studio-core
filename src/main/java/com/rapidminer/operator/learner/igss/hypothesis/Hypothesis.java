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
package com.rapidminer.operator.learner.igss.hypothesis;

import com.rapidminer.example.Attribute;
import com.rapidminer.example.Example;

import java.io.Serializable;
import java.util.LinkedList;


/**
 * Abstract superclass for all possible kinds of hypothesis.
 *
 * @author Dirk Dach
 */
public abstract class Hypothesis implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2081084133296284530L;

    /**
     * The constant HYPOTHESIS_SPACE_TYPES.
     */
    public static final String[] HYPOTHESIS_SPACE_TYPES = { "rule" };

    /**
     * The constant FIRST_TYPE_INDEX.
     */
    public static final int FIRST_TYPE_INDEX = 0;
    /**
     * The constant TYPE_RULE.
     */
    public static final int TYPE_RULE = 0;
    /**
     * The constant LAST_TYPE_INDEX.
     */
    public static final int LAST_TYPE_INDEX = 0;

    /**
     * The constant POSITIVE_CLASS.
     */
    public static final int POSITIVE_CLASS = 1;
    /**
     * The constant NEGATIVE_CLASS.
     */
    public static final int NEGATIVE_CLASS = 0;

	/** The label attribute. */
	private Attribute label;

    /**
     * Indicates if rejection sampling is used to draw the examples that this rule is applied to. In
     * this case (positive)example counter is incremented by one for every (positive)example the
     * rule is applicable to. In the other case(weights are used directly) the weight of the example
     * is added to the counters.
     */
    protected boolean rejectionSampling;

    /**
     * Stores the total weight of all examples covered by this hypothesis.
     */
    protected double coveredWeight;

    /**
     * Stores the weight of examples covered by this hypothesis with correct prediction.
     */
    protected double positiveWeight;

    /**
     * Create h->Y+/Y- or h->Y+ only.
     */
    protected boolean createAllHypothesis;

    /**
     * Create a new dummy hypothesis to allow calling the 'init' method, initialize the
     * regularAttributes, label and p0 fields.
     *
     * @param regulars  the regulars
     * @param l         the l
     * @param rs        the rs
     * @param createAll the create all
     */
    public Hypothesis(Attribute[] regulars, Attribute l, boolean rs, boolean createAll) {
		rejectionSampling = rs;
		createAllHypothesis = createAll;
		label = l;
	}

    /**
     * Instantiates a new Hypothesis.
     */
    public Hypothesis() {
		this.coveredWeight = 0.0d;
		this.positiveWeight = 0.0d;
	}

	/** Clone method. */
	@Override
	public abstract Hypothesis clone();

    /**
     * Returns the label index the hypothesis predicts.  @return the prediction
     *
     * @return the prediction
     */
    public abstract int getPrediction();

    /**
     * Returns the label.  @return the label
     *
     * @return the label
     */
    public Attribute getLabel() {
		return label;
	}

    /**
     * Sets 'coveredWeight' and 'positiveWeight' back to 0.0d.
     */
    public void reset() {
		this.coveredWeight = 0.0d;
		this.positiveWeight = 0.0d;
	}

    /**
     * Returns the covered weight of this hypothesis.  @return the covered weight
     *
     * @return the covered weight
     */
    public double getCoveredWeight() {
		return this.coveredWeight;
	}

    /**
     * Sets the covered weight of this hypothesis.  @param value the value
     *
     * @param value the value
     */
    public void setCoveredWeight(double value) {
		this.coveredWeight = value;
	}

    /**
     * Returns the covered positive weight of this hypothesis.  @return the positive weight
     *
     * @return the positive weight
     */
    public double getPositiveWeight() {
		return this.positiveWeight;
	}

    /**
     * Sets the covered positive weight of this hypothesis.  @param value the value
     *
     * @param value the value
     */
    public void setPositiveWeight(double value) {
		this.positiveWeight = value;
	}

    /**
     * Hypothesis is applied to the example and internal statistics of the hypothesis are updated.  @param e the e
     *
     * @param e the e
     */
    public abstract void apply(Example e);

    /**
     * Tests if the hypothesis is applicable to the example without updating the internal statistics
     * of the hypothesis.
     *
     * @param e the e
     * @return the boolean
     */
    public abstract boolean applicable(Example e);

    /**
     * Generates new hypothesis by adding one degree of complexity and creating all rules that are
     * possible now. Classes extending this abstract class define the way the hypothesis are
     * generated by the IGSS alsgorithm by implementing the refine() method. Be sure to avoid the
     * creation of duplicate hypothesis, for example by overwriting and using the equals method. For
     * example refine could increase the allowed depth of a decision tree or the number of literals
     * of a conjunctive rule. Must return null if the hypothesis cannot be refined any more.
     *
     * @return the linked list
     */
    public abstract LinkedList<Hypothesis> refine();

    /**
     * Returns true only if this hypothesis can still be refined.  @return the boolean
     *
     * @return the boolean
     */
    public abstract boolean canBeRefined();

    /**
     * Used to generate the first hypothesis or the first group of hypothesis.  @param minComplexity the min complexity
     *
     * @param minComplexity the min complexity
     * @return the linked list
     */
    public abstract LinkedList<Hypothesis> init(int minComplexity);

    /**
     * Returns complexity of the hypothesis.  @return the complexity
     *
     * @return the complexity
     */
    public abstract int getComplexity();

}
