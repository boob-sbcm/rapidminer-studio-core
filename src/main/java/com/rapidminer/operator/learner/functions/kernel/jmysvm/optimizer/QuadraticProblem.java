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
package com.rapidminer.operator.learner.functions.kernel.jmysvm.optimizer;

/**
 * A quadratic optimization problem.
 *
 * @author Stefan Rueping
 */
public abstract class QuadraticProblem {

    /**
     * The N.
     */
// Public variables that describe the quadratic problem
	protected int n; // number of variables

    /**
     * The M.
     */
    static int m = 1; // number of linear constraints, 1 for now

    /**
     * The C.
     */
    public double[] c;

    /**
     * The H.
     */
    public double[] H; // c' * x + 1/2 x' * H * x -> min

    /**
     * The A.
     */
    public double[] A;

    /**
     * The B.
     */
    public double[] b; // A * x = b

    /**
     * The L.
     */
    public double[] l;

    /**
     * The U.
     */
    public double[] u; // l <= x <= u

    /**
     * The X.
     */
    public double[] x;

    /**
     * The Max allowed error.
     */
    public double max_allowed_error;

    /**
     * Instantiates a new Quadratic problem.
     */
    public QuadraticProblem() {
		n = 0;
		lambda_eq = 0.0d;
	};

    /**
     * Sets n.
     *
     * @param new_n the new n
     */
    public void set_n(int new_n) {
		n = new_n;
		c = new double[n];
		H = new double[n * n];
		A = new double[n];
		b = new double[n];
		l = new double[n];
		u = new double[n];
		x = new double[n];
	};

    /**
     * Gets n.
     *
     * @return the n
     */
    public int get_n() {
		return (n);
	};

    /**
     * The Lambda eq.
     */
    public double lambda_eq;

    /**
     * Calc lambda eq.
     */
    protected abstract void calc_lambda_eq();

    /**
     * Solve int.
     *
     * @return the int
     */
    public abstract int solve();
};
