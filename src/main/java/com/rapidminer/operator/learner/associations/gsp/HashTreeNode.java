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
package com.rapidminer.operator.learner.associations.gsp;

import java.util.ArrayList;


/**
 * The interface Hash tree node.
 *
 * @author Sebastian Land
 */
public interface HashTreeNode {

    /**
     * Replace node.
     *
     * @param which       the which
     * @param replacement the replacement
     */
    public void replaceNode(Item which, HashTreeNode replacement);

    /**
     * Add sequence.
     *
     * @param candidate      the candidate
     * @param candidateIndex the candidate index
     * @param depth          the depth
     * @param father         the father
     * @param allCandidates  the all candidates
     */
    public void addSequence(Sequence candidate, int candidateIndex, int depth, HashTreeNode father,
			ArrayList<Sequence> allCandidates);

    /**
     * Count covered candidates.
     *
     * @param sequence the sequence
     * @param t        the t
     * @param counting the counting
     */
    public void countCoveredCandidates(DataSequence sequence, double t, CountingInformations counting);
}
