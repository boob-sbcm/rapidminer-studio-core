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
 * Models a Regression-Problem.
 *
 * @author Piotr Kasprzak
 */
public class RegressionProblem extends Problem {

    /**
     * The Y.
     */
    double[][] y; // Target vectors

    /**
     * Instantiates a new Regression problem.
     *
     * @param x      the x
     * @param y      the y
     * @param kernel the kernel
     */
    public RegressionProblem(double[][] x, double[][] y, Kernel kernel) {
		super(x, kernel);
		this.y = y;
	}

	@Override
	public int getTargetDimension() {
		return y[0].length;
	}

    /**
     * Get target vectors double [ ] [ ].
     *
     * @return the double [ ] [ ]
     */
    public double[][] getTargetVectors() {
		return y;
	}
}
