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

import com.rapidminer.example.Attribute;
import com.rapidminer.example.ExampleSet;
import com.rapidminer.example.Statistics;
import com.rapidminer.operator.Model;
import com.rapidminer.operator.OperatorDescription;
import com.rapidminer.operator.OperatorException;
import com.rapidminer.operator.UserError;
import com.rapidminer.operator.learner.AbstractLearner;
import com.rapidminer.operator.learner.PredictionModel;
import com.rapidminer.operator.learner.tree.criterions.*;
import com.rapidminer.parameter.ParameterType;
import com.rapidminer.parameter.ParameterTypeDouble;
import com.rapidminer.parameter.ParameterTypeInt;
import com.rapidminer.parameter.ParameterTypeStringCategory;

import java.util.List;


/**
 * This is the abstract super class for all decision tree learners. The actual type of the tree is
 * determined by the criterion, e.g. using gain_ratio or Gini for CART / C4.5 and chi_squared for
 * CHAID.
 *
 * @author Sebastian Land, Ingo Mierswa
 */
public abstract class AbstractTreeLearner extends AbstractLearner {

    /**
     * The parameter name for &quot;Specifies the used criterion for selecting attributes and
     * numerical splits.&quot;
     */
    public static final String PARAMETER_CRITERION = "criterion";

    /**
     * The parameter name for &quot;The minimal size of all leaves.&quot;
     */
    public static final String PARAMETER_MINIMAL_SIZE_FOR_SPLIT = "minimal_size_for_split";

    /**
     * The parameter name for &quot;The minimal size of all leaves.&quot;
     */
    public static final String PARAMETER_MINIMAL_LEAF_SIZE = "minimal_leaf_size";

    /**
     * The parameter name for the minimal gain.
     */
    public static final String PARAMETER_MINIMAL_GAIN = "minimal_gain";

    /**
     * The constant CRITERIA_NAMES.
     */
    public static final String[] CRITERIA_NAMES = { "gain_ratio", "information_gain", "gini_index", "accuracy" };

    /**
     * The constant CRITERIA_CLASSES.
     */
    public static final Class<?>[] CRITERIA_CLASSES = { GainRatioCriterion.class, InfoGainCriterion.class,
		GiniIndexCriterion.class, AccuracyCriterion.class };

    /**
     * The constant CRITERION_GAIN_RATIO.
     */
    public static final int CRITERION_GAIN_RATIO = 0;

    /**
     * The constant CRITERION_INFO_GAIN.
     */
    public static final int CRITERION_INFO_GAIN = 1;

    /**
     * The constant CRITERION_GINI_INDEX.
     */
    public static final int CRITERION_GINI_INDEX = 2;

    /**
     * The constant CRITERION_ACCURACY.
     */
    public static final int CRITERION_ACCURACY = 3;

    /**
     * Instantiates a new Abstract tree learner.
     *
     * @param description the description
     */
    public AbstractTreeLearner(OperatorDescription description) {
		super(description);
	}

	@Override
	public Class<? extends PredictionModel> getModelClass() {
		return TreeModel.class;
	}

    /**
     * Returns all termination criteria.  @param exampleSet the example set
     *
     * @param exampleSet the example set
     * @return the termination criteria
     * @throws OperatorException the operator exception
     */
    public abstract List<Terminator> getTerminationCriteria(ExampleSet exampleSet) throws OperatorException;

    /**
     * Returns the pruner for this tree learner. If this method returns null, pruning will be
     * disabled.
     *
     * @return the pruner
     * @throws OperatorException the operator exception
     */
    public abstract Pruner getPruner() throws OperatorException;

    /**
     * The split preprocessing is applied before each new split The default implementation does
     * nothing and simply returns the given example set. Subclasses might want to override this in
     * order to perform some data preprocessing like random subset selections.
     *
     * @return the split preprocessing
     */
    public SplitPreprocessing getSplitPreprocessing() {
		return null;
	}

	@Override
	public Model learn(ExampleSet eSet) throws OperatorException {
		ExampleSet exampleSet = (ExampleSet) eSet.clone();

		// check if the label attribute contains any missing values
		Attribute labelAtt = exampleSet.getAttributes().getLabel();
		exampleSet.recalculateAttributeStatistics(labelAtt);
		if (exampleSet.getStatistics(labelAtt, Statistics.UNKNOWN) > 0) {
			throw new UserError(this, 162, labelAtt.getName());
		}

		// create tree builder
		TreeBuilder builder = getTreeBuilder(exampleSet);

		// learn tree
		Tree root = builder.learnTree(exampleSet);

		// create and return model
		return new TreeModel(exampleSet, root);
	}

    /**
     * Gets tree builder.
     *
     * @param exampleSet the example set
     * @return the tree builder
     * @throws OperatorException the operator exception
     */
    protected abstract TreeBuilder getTreeBuilder(ExampleSet exampleSet) throws OperatorException;

    /**
     * Create criterion criterion.
     *
     * @param minimalGain the minimal gain
     * @return the criterion
     * @throws OperatorException the operator exception
     */
    protected Criterion createCriterion(double minimalGain) throws OperatorException {
		return AbstractCriterion.createCriterion(this, minimalGain);
	}

	@Override
	public List<ParameterType> getParameterTypes() {
		List<ParameterType> types = super.getParameterTypes();
		ParameterType type = new ParameterTypeStringCategory(PARAMETER_CRITERION,
				"Specifies the used criterion for selecting attributes and numerical splits.", CRITERIA_NAMES,
				CRITERIA_NAMES[CRITERION_GAIN_RATIO], false);
		type.setExpert(false);
		types.add(type);
		type = new ParameterTypeInt(PARAMETER_MINIMAL_SIZE_FOR_SPLIT,
				"The minimal size of a node in order to allow a split.", 1, Integer.MAX_VALUE, 4);
		type.setExpert(false);
		types.add(type);
		type = new ParameterTypeInt(PARAMETER_MINIMAL_LEAF_SIZE, "The minimal size of all leaves.", 1, Integer.MAX_VALUE, 2);
		type.setExpert(false);
		types.add(type);
		types.add(new ParameterTypeDouble(PARAMETER_MINIMAL_GAIN,
				"The minimal gain which must be achieved in order to produce a split.", 0.0d, Double.POSITIVE_INFINITY, 0.1d));
		return types;
	}
}
