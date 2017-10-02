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
package com.rapidminer.operator.learner.functions.kernel.gaussianprocess;

import com.rapidminer.operator.learner.functions.kernel.rvm.kernel.Kernel;


/**
 * Holds the data defining the regression / classification problem to be learned. - All input
 * vectors are assumed to have the same dimension. - All target vectors are assumed to have the same
 * dimension
 *
 * @author Piotr Kasprzak
 */
public abstract class Problem {

	private double[][] x; // Input vectors

	private Kernel kernel;

    /**
     * The Sigma 0 2.
     */
/* The variance^2 of the gaussian noise */
	public double sigma_0_2 = 0.09;

	/** Problem types */

    /**
     * Constructor  @param x the x
     *
     * @param x      the x
     * @param kernel the kernel
     */
    public Problem(double[][] x, Kernel kernel) {
		this.x = x;
		this.kernel = kernel;
	}

    /**
     * Getters  @return the problem size
     *
     * @return the problem size
     */
    public int getProblemSize() {
		return x.length;
	}

    /**
     * Gets input dimension.
     *
     * @return the input dimension
     */
    public int getInputDimension() {
		return x[0].length;
	}

    /**
     * Get input vectors double [ ] [ ].
     *
     * @return the double [ ] [ ]
     */
    public double[][] getInputVectors() {
		return x;
	}

    /**
     * Gets target dimension.
     *
     * @return the target dimension
     */
    abstract public int getTargetDimension();

    /**
     * Gets kernel.
     *
     * @return the kernel
     */
    public Kernel getKernel() {
		return kernel;
	}
}
