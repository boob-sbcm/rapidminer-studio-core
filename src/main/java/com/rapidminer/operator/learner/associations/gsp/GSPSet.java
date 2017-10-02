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

import com.rapidminer.operator.ResultObjectAdapter;
import com.rapidminer.tools.Tools;
import com.rapidminer.tools.container.Tupel;

import java.util.TreeSet;


/**
 * The type Gsp set.
 *
 * @author Sebastian Land
 */
public class GSPSet extends ResultObjectAdapter {

	private static final long serialVersionUID = 4739128489323129098L;

	private TreeSet<Tupel<Sequence, Double>> sequences = new TreeSet<Tupel<Sequence, Double>>();

	private int maxTransactions = 0;

    /**
     * Instantiates a new Gsp set.
     */
    public GSPSet() {}

    /**
     * Add sequence.
     *
     * @param sequence the sequence
     * @param support  the support
     */
    public void addSequence(Sequence sequence, double support) {
		sequences.add(new Tupel<Sequence, Double>(sequence, support));
		maxTransactions = Math.max(maxTransactions, sequence.size());
	}

    /**
     * Gets max transactions.
     *
     * @return the maxTransactions
     */
    public int getMaxTransactions() {
		return maxTransactions;
	}

    /**
     * Gets number of sequences.
     *
     * @return the number of sequences
     */
    public int getNumberOfSequences() {
		return sequences.size();
	}

    /**
     * Get sequence array sequence [ ].
     *
     * @return the sequence [ ]
     */
    public Sequence[] getSequenceArray() {
		return sequences.stream().map(Tupel::getFirst).toArray(Sequence[]::new);
	}

    /**
     * Get support array double [ ].
     *
     * @return the double [ ]
     */
    public double[] getSupportArray() {
		return sequences.stream().mapToDouble(Tupel::getSecond).toArray();
	}

	@Override
	public String toString() {
		return "Set of generalized sequential patterns" + Tools.getLineSeparator() + "Set contains " + sequences.size()
				+ " patterns.";
	}

	@Override
	public String toResultString() {
		StringBuffer buffer = new StringBuffer();
		for (Tupel<Sequence, Double> sequencePair : sequences) {
			buffer.append(Tools.formatNumber(sequencePair.getSecond(), 3) + ": " + sequencePair.getFirst().toString()
					+ Tools.getLineSeparator());
		}
		return buffer.toString();
	}
}
