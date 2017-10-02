/**
 * Copyright (c) 2007-2014 The LIBLINEAR Project. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted
 * provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions
 * and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of
 * conditions and the following disclaimer in the documentation and/or other materials provided with
 * the distribution.
 *
 * 3. Neither name of copyright holders nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE REGENTS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF
 * THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package de.bwaldvogel.liblinear;

import static de.bwaldvogel.liblinear.Linear.copyOf;


/**
 * The type Parameter.
 */
public final class Parameter {

    /**
     * The C.
     */
    double C;

    /**
     * stopping criteria
     */
    double eps;

    /**
     * The Solver type.
     */
    SolverType solverType;

    /**
     * The Weight.
     */
    double[] weight = null;

    /**
     * The Weight label.
     */
    int[] weightLabel = null;

    /**
     * The P.
     */
    double p;

    /**
     * Instantiates a new Parameter.
     *
     * @param solver the solver
     * @param C      the c
     * @param eps    the eps
     */
    public Parameter(SolverType solver, double C, double eps) {
		this(solver, C, eps, 0.1);
	}

    /**
     * Instantiates a new Parameter.
     *
     * @param solverType the solver type
     * @param C          the c
     * @param eps        the eps
     * @param p          the p
     */
    public Parameter(SolverType solverType, double C, double eps, double p) {
		setSolverType(solverType);
		setC(C);
		setEps(eps);
		setP(p);
	}

    /**
     * <p>
     * nr_weight, weight_label, and weight are used to change the penalty for some classes (If the
     * weight for a class is not changed, it is set to 1). This is useful for training classifier
     * using unbalanced input data or with asymmetric misclassification cost.
     * </p>
     * <p>
     * <p>
     * Each weight[i] corresponds to weight_label[i], meaning that the penalty of class
     * weight_label[i] is scaled by a factor of weight[i].
     * </p>
     * <p>
     * <p>
     * If you do not want to change penalty for any of the classes, just set nr_weight to 0.
     * </p>
     *
     * @param weights      the weights
     * @param weightLabels the weight labels
     */
    public void setWeights(double[] weights, int[] weightLabels) {
		if (weights == null) {
			throw new IllegalArgumentException("'weight' must not be null");
		}
		if (weightLabels == null || weightLabels.length != weights.length) {
			throw new IllegalArgumentException("'weightLabels' must have same length as 'weight'");
		}
		this.weightLabel = copyOf(weightLabels, weightLabels.length);
		this.weight = copyOf(weights, weights.length);
	}

    /**
     * Get weights double [ ].
     *
     * @return the double [ ]
     * @see #setWeights(double[], int[]) #setWeights(double[], int[])#setWeights(double[], int[])
     */
    public double[] getWeights() {
		return copyOf(weight, weight.length);
	}

    /**
     * Get weight labels int [ ].
     *
     * @return the int [ ]
     * @see #setWeights(double[], int[]) #setWeights(double[], int[])#setWeights(double[], int[])
     */
    public int[] getWeightLabels() {
		return copyOf(weightLabel, weightLabel.length);
	}

    /**
     * the number of weights
     *
     * @return the num weights
     * @see #setWeights(double[], int[]) #setWeights(double[], int[])#setWeights(double[], int[])
     */
    public int getNumWeights() {
		if (weight == null) {
			return 0;
		}
		return weight.length;
	}

    /**
     * C is the cost of constraints violation. (we usually use 1 to 1000)
     *
     * @param C the c
     */
    public void setC(double C) {
		if (C <= 0) {
			throw new IllegalArgumentException("C must not be <= 0");
		}
		this.C = C;
	}

    /**
     * Gets c.
     *
     * @return the c
     */
    public double getC() {
		return C;
	}

    /**
     * eps is the stopping criterion. (we usually use 0.01).
     *
     * @param eps the eps
     */
    public void setEps(double eps) {
		if (eps <= 0) {
			throw new IllegalArgumentException("eps must not be <= 0");
		}
		this.eps = eps;
	}

    /**
     * Gets eps.
     *
     * @return the eps
     */
    public double getEps() {
		return eps;
	}

    /**
     * Sets solver type.
     *
     * @param solverType the solver type
     */
    public void setSolverType(SolverType solverType) {
		if (solverType == null) {
			throw new IllegalArgumentException("solver type must not be null");
		}
		this.solverType = solverType;
	}

    /**
     * Gets solver type.
     *
     * @return the solver type
     */
    public SolverType getSolverType() {
		return solverType;
	}

    /**
     * set the epsilon in loss function of epsilon-SVR (default 0.1)
     *
     * @param p the p
     */
    public void setP(double p) {
		if (p < 0) {
			throw new IllegalArgumentException("p must not be less than 0");
		}
		this.p = p;
	}

    /**
     * Gets p.
     *
     * @return the p
     */
    public double getP() {
		return p;
	}
}
