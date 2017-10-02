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
import com.rapidminer.example.set.SplittedExampleSet;
import com.rapidminer.operator.OperatorException;
import com.rapidminer.operator.learner.tree.criterions.Criterion;

import java.util.Collections;
import java.util.List;
import java.util.Vector;


/**
 * Build a tree from an example set.
 *
 * @author Ingo Mierswa
 */
public class TreeBuilder {

    /**
     * The Min leaf size terminator.
     */
    protected Terminator minLeafSizeTerminator;

	private List<Terminator> otherTerminators;

	private int minSizeForSplit = 2;

	private Criterion criterion;

	private NumericalSplitter splitter;

    /**
     * The Preprocessing.
     */
    protected SplitPreprocessing preprocessing = null;

	private Pruner pruner;

    /**
     * The Leaf creator.
     */
    protected LeafCreator leafCreator = new DecisionTreeLeafCreator();

    /**
     * The Number of prepruning alternatives.
     */
    protected int numberOfPrepruningAlternatives = 0;

    /**
     * The Use pre pruning.
     */
    protected boolean usePrePruning = true;

    /**
     * Instantiates a new Tree builder.
     *
     * @param criterion                      the criterion
     * @param terminationCriteria            the termination criteria
     * @param pruner                         the pruner
     * @param preprocessing                  the preprocessing
     * @param leafCreator                    the leaf creator
     * @param noPrePruning                   the no pre pruning
     * @param numberOfPrepruningAlternatives the number of prepruning alternatives
     * @param minSizeForSplit                the min size for split
     * @param minLeafSize                    the min leaf size
     */
    public TreeBuilder(Criterion criterion, List<Terminator> terminationCriteria, Pruner pruner,
			SplitPreprocessing preprocessing, LeafCreator leafCreator, boolean noPrePruning,
			int numberOfPrepruningAlternatives, int minSizeForSplit, int minLeafSize) {
		this.minLeafSizeTerminator = new MinSizeTermination(minLeafSize);
		this.otherTerminators = terminationCriteria;
		this.otherTerminators.add(this.minLeafSizeTerminator);

		this.usePrePruning = !noPrePruning;
		this.numberOfPrepruningAlternatives = Math.max(0, numberOfPrepruningAlternatives);
		this.minSizeForSplit = minSizeForSplit;

		this.leafCreator = leafCreator;
		this.criterion = criterion;
		this.splitter = new NumericalSplitter(this.criterion);
		this.pruner = pruner;
		this.preprocessing = preprocessing;
	}

    /**
     * Learn tree tree.
     *
     * @param exampleSet the example set
     * @return the tree
     * @throws OperatorException the operator exception
     */
    public Tree learnTree(ExampleSet exampleSet) throws OperatorException {

		// grow tree
		Tree root = new Tree((ExampleSet) exampleSet.clone());
		if (shouldStop(exampleSet, 0)) {
			leafCreator.changeTreeToLeaf(root, exampleSet);
		} else {
			buildTree(root, exampleSet, 1);
		}

		// prune
		if (pruner != null) {
			pruner.prune(root);
		}

		return root;
	}

    /**
     * This method calculates the benefit of the given attribute. This implementation utilizes the
     * defined {@link Criterion}. Subclasses might want to override this method in order to
     * calculate the benefit in other ways.
     *
     * @param trainingSet the training set
     * @param attribute   the attribute
     * @return the benefit
     * @throws OperatorException the operator exception
     */
    public Benefit calculateBenefit(ExampleSet trainingSet, Attribute attribute) throws OperatorException {
		if (attribute.isNominal()) {
			return new Benefit(criterion.getNominalBenefit(trainingSet, attribute), attribute);
		} else {
			// numerical attribute
			double splitValue = splitter.getBestSplit(trainingSet, attribute);
			if (!Double.isNaN(splitValue)) {
				return new Benefit(criterion.getNumericalBenefit(trainingSet, attribute, splitValue), attribute, splitValue);
			} else {
				return null;
			}
		}
	}

    /**
     * Should stop boolean.
     *
     * @param exampleSet the example set
     * @param depth      the depth
     * @return the boolean
     */
    protected boolean shouldStop(ExampleSet exampleSet, int depth) {
		if (usePrePruning && exampleSet.size() < minSizeForSplit) {
			return true;
		} else {
			for (Terminator terminator : otherTerminators) {
				if (terminator.shouldStop(exampleSet, depth)) {
					return true;
				}
			}
			return false;
		}
	}

    /**
     * Calculate all benefits vector.
     *
     * @param trainingSet the training set
     * @return the vector
     * @throws OperatorException the operator exception
     */
    protected Vector<Benefit> calculateAllBenefits(ExampleSet trainingSet) throws OperatorException {
		Vector<Benefit> benefits = new Vector<Benefit>();
		for (Attribute attribute : trainingSet.getAttributes()) {
			Benefit currentBenefit = calculateBenefit(trainingSet, attribute);
			if (currentBenefit != null) {
				benefits.add(currentBenefit);
			}
		}
		return benefits;
	}

    /**
     * Build tree.
     *
     * @param current    the current
     * @param exampleSet the example set
     * @param depth      the depth
     * @throws OperatorException the operator exception
     */
    protected void buildTree(Tree current, ExampleSet exampleSet, int depth) throws OperatorException {
		// terminate (beginning of recursive method!)
		if (shouldStop(exampleSet, depth)) {
			leafCreator.changeTreeToLeaf(current, exampleSet);
			return;
		}

		// preprocessing
		if (preprocessing != null) {
			exampleSet = preprocessing.preprocess(exampleSet);
		}

		ExampleSet trainingSet = (ExampleSet) exampleSet.clone();

		// calculate all benefits
		Vector<Benefit> benefits = calculateAllBenefits(exampleSet);

		// sort all benefits
		Collections.sort(benefits);

		// try at most k benefits and check if prepruning is fulfilled
		boolean splitFound = false;
		for (int a = 0; a < numberOfPrepruningAlternatives + 1; a++) {
			// break if no benefits are left
			if (benefits.size() <= 0) {
				break;
			}

			// search current best
			Benefit bestBenefit = benefits.remove(0);

			// check if minimum gain was reached
			if (usePrePruning && bestBenefit.getBenefit() <= 0) {
				continue;
			}

			// split by best attribute
			SplittedExampleSet splitted = null;
			Attribute bestAttribute = bestBenefit.getAttribute();
			double bestSplitValue = bestBenefit.getSplitValue();
			if (bestAttribute.isNominal()) {
				splitted = SplittedExampleSet.splitByAttribute(trainingSet, bestAttribute);
			} else {
				splitted = SplittedExampleSet.splitByAttribute(trainingSet, bestAttribute, bestSplitValue);
			}

			// check if children all have the minimum size
			boolean splitOK = true;
			if (usePrePruning) {
				for (int i = 0; i < splitted.getNumberOfSubsets(); i++) {
					splitted.selectSingleSubset(i);
					if (splitted.size() > 0 && minLeafSizeTerminator.shouldStop(splitted, depth)) {
						splitOK = false;
						break;
					}
				}
			}

			// if all have minimum size --> remove nominal attribute and recursive call for each
			// subset
			if (splitOK) {
				if (bestAttribute.isNominal()) {
					splitted.getAttributes().remove(bestAttribute);
				}
				for (int i = 0; i < splitted.getNumberOfSubsets(); i++) {
					splitted.selectSingleSubset(i);
					if (splitted.size() > 0) {
						Tree child = new Tree(splitted.clone());
						SplitCondition condition = null;
						if (bestAttribute.isNominal()) {
							condition = new NominalSplitCondition(bestAttribute, splitted.getExample(0).getValueAsString(
									bestAttribute));
						} else {
							if (i == 0) {
								condition = new LessEqualsSplitCondition(bestAttribute, bestSplitValue);
							} else {
								condition = new GreaterSplitCondition(bestAttribute, bestSplitValue);
							}
						}
						current.addChild(child, condition);
						buildTree(child, splitted, depth + 1);
					}
				}

				// end loop
				splitFound = true;
				break;
			} else {
				continue;
			}
		}

		// no split found --> change to leaf and return
		if (!splitFound) {
			leafCreator.changeTreeToLeaf(current, trainingSet);
		}
	}
}
