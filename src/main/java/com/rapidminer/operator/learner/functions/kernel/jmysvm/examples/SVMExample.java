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
package com.rapidminer.operator.learner.functions.kernel.jmysvm.examples;

import java.io.Serializable;


/**
 * An Example for the kernel based algorithms provided by Stefan Rueping. Since RapidMiner cannot
 * deliver the example with index i directly, a new data structure is needed.
 *
 * @author Stefan Rueping, Ingo Mierswa
 */
public class SVMExample implements Serializable {

	private static final long serialVersionUID = 8539279195547132597L;

    /**
     * The Index.
     */
    public int[] index;

    /**
     * The Att.
     */
    public double[] att;

    /**
     * Instantiates a new Svm example.
     */
    public SVMExample() {
		index = null;
		att = null;
	}

    /**
     * Instantiates a new Svm example.
     *
     * @param values the values
     */
    public SVMExample(double[] values) {
		index = new int[values.length];
		for (int i = 0; i < index.length; i++) {
			index[i] = i;
		}
		this.att = values;
	}

    /**
     * Instantiates a new Svm example.
     *
     * @param e the e
     */
/*
     * For internal purposes only!!!
	 */
	public SVMExample(SVMExample e) {
		this.index = e.index;
		this.att = e.att;
	}

    /**
     * Instantiates a new Svm example.
     *
     * @param new_index the new index
     * @param new_att   the new att
     */
    public SVMExample(int[] new_index, double[] new_att) {
		index = new_index;
		att = new_att;
	}

    /**
     * To dense double [ ].
     *
     * @param dim the dim
     * @return the double [ ]
     */
    public double[] toDense(int dim) {
		double[] dense;
		dense = new double[dim];
		int pos = 0;
		if (index != null) {
			for (int i = 0; i < index.length; i++) {
				while (pos < index[i]) {
					dense[pos] = 0.0;
					pos++;
				}
				dense[pos] = att[i];
				pos++;
			}
		}
		while (pos < dim) {
			dense[pos] = 0.0;
			pos++;
		}
		return dense;
	}
}
