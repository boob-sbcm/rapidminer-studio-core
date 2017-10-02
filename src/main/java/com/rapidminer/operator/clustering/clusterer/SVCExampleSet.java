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
package com.rapidminer.operator.clustering.clusterer;

import com.rapidminer.operator.learner.functions.kernel.jmysvm.examples.SVMExamples;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Map;


/**
 * Example set that supports SVClustering.
 *
 * @author Stefan Rueping, Ingo Mierswa
 */
public class SVCExampleSet extends SVMExamples {

	private static final long serialVersionUID = 6624551227845929687L;

	private double r;

    /**
     * Instantiates a new Svc example set.
     *
     * @param size the size
     * @param b    the b
     */
    public SVCExampleSet(int size, double b) {
		super(size, b);
	}

    /**
     * Instantiates a new Svc example set.
     *
     * @param exampleSet the example set
     * @param scale      the scale
     */
    public SVCExampleSet(com.rapidminer.example.ExampleSet exampleSet, boolean scale) {
		super(exampleSet, null, scale);
	}

    /**
     * Instantiates a new Svc example set.
     *
     * @param exampleSet    the example set
     * @param meanVariances the mean variances
     */
    public SVCExampleSet(com.rapidminer.example.ExampleSet exampleSet, Map<Integer, SVMExamples.MeanVariance> meanVariances) {
		super(exampleSet, null, meanVariances);
	}

    /**
     * Instantiates a new Svc example set.
     *
     * @param in the in
     * @throws IOException the io exception
     */
    public SVCExampleSet(ObjectInputStream in) throws IOException {
		super(in);
	}

    /**
     * Gets r.
     *
     * @return the r
     */
    public double get_R() {
		return r;
	}

    /**
     * Sets r.
     *
     * @param r the r
     */
    public void set_R(double r) {
		this.r = r;
	}
}
