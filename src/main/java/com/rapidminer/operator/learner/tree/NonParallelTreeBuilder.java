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

import com.rapidminer.operator.Operator;
import com.rapidminer.operator.OperatorException;
import com.rapidminer.operator.learner.tree.criterions.ColumnCriterion;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Map;


/**
 * Build a tree from an example set, not in parallel.
 *
 * @author Gisa Schaefer
 */
public class NonParallelTreeBuilder extends AbstractParallelTreeBuilder {

    /**
     * Pipes the arguments to the super constructor and sets an additional parameter forbidding
     * parallel table creation.
     *
     * @param operator                       the operator
     * @param criterion                      the criterion
     * @param terminationCriteria            the termination criteria
     * @param pruner                         the pruner
     * @param preprocessing                  the preprocessing
     * @param prePruning                     the pre pruning
     * @param numberOfPrepruningAlternatives the number of prepruning alternatives
     * @param minSizeForSplit                the min size for split
     * @param minLeafSize                    the min leaf size
     */
    public NonParallelTreeBuilder(Operator operator, ColumnCriterion criterion, List<ColumnTerminator> terminationCriteria,
			Pruner pruner, AttributePreprocessing preprocessing, boolean prePruning, int numberOfPrepruningAlternatives,
			int minSizeForSplit, int minLeafSize) {
		super(operator, criterion, terminationCriteria, pruner, preprocessing, prePruning, numberOfPrepruningAlternatives,
				minSizeForSplit, minLeafSize, false);
	}

	@Override
	void startTree(Tree root, Map<Integer, int[]> allSelectedExamples, int[] selectedAttributes, int depth)
			throws OperatorException {
		NodeData rootNode = new NodeData(root, allSelectedExamples, selectedAttributes, depth);
		Deque<NodeData> queue = new ArrayDeque<>();
		queue.push(rootNode);
		while (!queue.isEmpty()) {
			queue.addAll(splitNode(queue.pop(), false));
		}

	}

	@Override
	boolean doStartSelectionInParallel() {
		return false;
	}

}
