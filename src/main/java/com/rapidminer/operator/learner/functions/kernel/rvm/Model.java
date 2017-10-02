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
package com.rapidminer.operator.learner.functions.kernel.rvm;

import com.rapidminer.operator.learner.functions.kernel.rvm.kernel.KernelBasisFunction;

import java.io.Serializable;


/**
 * The lerned model.
 *
 * @author Piotr Kasprzak, Ingo Mierswa
 */
public class Model implements Serializable {

	private static final long serialVersionUID = -8223343885533884477L;

	private KernelBasisFunction[] kernels;			// The unpruned kernels
	private double[] weights;						// Associated weights

	private boolean bias;							// Bias

	private boolean regression;						// Regression or classification model

    /**
     * Constructors  @param weights the weights
     *
     * @param weights    the weights
     * @param kernels    the kernels
     * @param bias       the bias
     * @param regression the regression
     */
    public Model(double[] weights, KernelBasisFunction[] kernels, boolean bias, boolean regression) {
		this.kernels = kernels;
		this.weights = weights;
		this.bias = bias;
		this.regression = regression;
	}

    /**
     * Gets number of relevance vectors.
     *
     * @return the number of relevance vectors
     */
    public int getNumberOfRelevanceVectors() {
		return weights.length;
	}

    /**
     * Gets weight.
     *
     * @param index the index
     * @return the weight
     */
    public double getWeight(int index) {
		return weights[index];
	}

    /**
     * Model application. Returns the function value, not a crisp prediction.  @param vector the vector
     *
     * @param vector the vector
     * @return the double
     */
    public double applyToVector(double[] vector) {
		int j;
		double prediction;
		if (bias) {
			j = 1;
			prediction = weights[0];
		} else {
			j = 0;
			prediction = 0;
		}
		for (; j < weights.length; j++) {
			prediction += weights[j] * kernels[j].eval(vector);
		}

		return prediction;
	}

    /**
     * Apply double [ ].
     *
     * @param inputVectors the input vectors
     * @return the double [ ]
     */
    public double[] apply(double[][] inputVectors) {
		double[] prediction = new double[inputVectors.length];
		for (int i = 0; i < inputVectors.length; i++) {
			prediction[i] = applyToVector(inputVectors[i]);
			if (!regression) {
				if (prediction[i] > 0.0) {
					prediction[i] = 1.0;
				} else {
					prediction[i] = 0.0;
				}
			}
		}

		return prediction;
	}

    /**
     * Norm l 2 double.
     *
     * @param vector the vector
     * @return the double
     */
    public double norm_l2(double[] vector) {
		double result = 0;
		for (int i = 0; i < vector.length; i++) {
			result += vector[i] * vector[i];
		}
		return Math.sqrt(result);
	}
}
