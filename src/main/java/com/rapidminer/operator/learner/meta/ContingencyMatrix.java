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
package com.rapidminer.operator.learner.meta;

import java.io.Serializable;


/**
 * This class computes the contingency matrix of classifiers, supports weighted example sets and
 * contains some convenience methods to query for some evaluation metrics that can directly be
 * computed from this matrix.
 *
 * @author Martin Scholz Exp $
 */
public class ContingencyMatrix implements Serializable {

	private static final long serialVersionUID = -4919765585974259997L;

	private final double[][] matrix;

	private final double[] rowSums;

	private final double[] colSums;

	private final double total;

    /**
     * The contigency matrix in the format [true label][predicted label]
     *
     * @param contigencyMatrix the contigency matrix
     */
    public ContingencyMatrix(double[][] contigencyMatrix) {
		this.matrix = new double[contigencyMatrix.length][];
		for (int i = 0; i < matrix.length; i++) {
			this.matrix[i] = new double[contigencyMatrix[i].length];
			System.arraycopy(contigencyMatrix[i], 0, this.matrix[i], 0, this.matrix[i].length);
		}

		double totalSum = 0;
		this.rowSums = new double[this.matrix.length];
		for (int row = 0; row < this.rowSums.length; row++) {
			double prior = 0;
			double[] entries = this.matrix[row];
			for (int i = 0; i < entries.length; i++) {
				prior += entries[i];
			}
			this.rowSums[row] = prior;
			totalSum += prior;
		}
		this.total = totalSum;

		this.colSums = new double[this.matrix.length > 0 ? this.matrix[0].length : 0];
		for (int col = 0; col < colSums.length; col++) {
			double sum = 0;
			for (int i = 0; i < this.matrix.length; i++) {
				sum += this.matrix[i][col];
			}
			this.colSums[col] = sum;
		}

	}

    /**
     * Get matrix double [ ] [ ].
     *
     * @return the double [ ] [ ]
     */
    public double[][] getMatrix() {
		double[][] result = new double[this.matrix.length][];
		for (int i = 0; i < result.length; i++) {
			result[i] = new double[this.matrix[i].length];
			System.arraycopy(this.matrix[i], 0, result[i], 0, this.matrix[i].length);
		}
		return result;
	}

    /**
     * Gets number of classes.
     *
     * @return the number of classes
     */
    public int getNumberOfClasses() {
		return this.rowSums.length;
	}

    /**
     * Gets number of predictions.
     *
     * @return the number of predictions
     */
    public int getNumberOfPredictions() {
		return this.colSums.length;
	}

    /**
     * Get priors double [ ].
     *
     * @return the double [ ]
     */
    public double[] getPriors() {
		double[] priors = new double[rowSums.length];
		for (int i = 0; i < priors.length; i++) {
			priors[i] = this.getPrior(i);
		}
		return priors;
	}

    /**
     * Gets prior.
     *
     * @param trueLabel the true label
     * @return the prior
     */
    public double getPrior(int trueLabel) {
		return rowSums[trueLabel] / total;
	}

    /**
     * Gets coverage.
     *
     * @param predictedLabel the predicted label
     * @return the coverage
     */
    public double getCoverage(int predictedLabel) {
		return colSums[predictedLabel] / total;
	}

    /**
     * Gets probability.
     *
     * @param trueLabel      the true label
     * @param predictedLabel the predicted label
     * @return the probability
     */
    public double getProbability(int trueLabel, int predictedLabel) {
		return this.matrix[trueLabel][predictedLabel] / total;
	}

    /**
     * Gets precision.
     *
     * @param trueLabel      the true label
     * @param predictedLabel the predicted label
     * @return the precision
     */
    public double getPrecision(int trueLabel, int predictedLabel) {
		return this.getProbability(trueLabel, predictedLabel) / this.getCoverage(predictedLabel);
	}

    /**
     * Gets lift.
     *
     * @param trueLabel      the true label
     * @param predictedLabel the predicted label
     * @return the lift
     */
    public double getLift(int trueLabel, int predictedLabel) {
		if (this.getCoverage(predictedLabel) <= 0) {
			return WeightedPerformanceMeasures.RULE_DOES_NOT_APPLY; // isNaN
		}

		return (this.getPrecision(trueLabel, predictedLabel) / this.getPrior(trueLabel));
	}

    /**
     * Gets lift ratio.
     *
     * @param trueLabel      the true label
     * @param predictedLabel the predicted label
     * @return the lift ratio
     */
    public double getLiftRatio(int trueLabel, int predictedLabel) {
		double liftPos = this.getLift(trueLabel, predictedLabel);

		if (Double.isNaN(liftPos) || Double.isInfinite(liftPos)) {
			return liftPos;
		}

		double precNeg = 1.0d - this.getPrecision(trueLabel, predictedLabel);
		double priorNeg = 1.0d - this.getPrior(trueLabel);

		if (priorNeg <= 0) {
			return Double.POSITIVE_INFINITY;
		}

		double liftNeg = precNeg / priorNeg;
		return (liftPos / liftNeg);
	}

    /**
     * Get lift ratios for prediction double [ ].
     *
     * @param predictedLabel the predicted label
     * @return the double [ ]
     */
    public double[] getLiftRatiosForPrediction(int predictedLabel) {
		double[] liftRatios = new double[this.matrix.length];
		for (int i = 0; i < liftRatios.length; i++) {
			liftRatios[i] = this.getLiftRatio(i, predictedLabel);
		}
		return liftRatios;
	}

    /**
     * Gets accuracy.
     *
     * @return the accuracy
     */
// makes sense only for square confusion matrices where
	// the diagonal captures the correct predictions
	public double getAccuracy() {
		double sum = 0;
		for (int row = 0; row < this.matrix.length; row++) {
			if (this.matrix[row].length > row) {
				sum += this.matrix[row][row];
			}
		}
		return (sum / total);
	}

    /**
     * Gets error rate.
     *
     * @return the error rate
     */
// makes sense only for square confusion matrices where
	// the diagonal captures the correct predictions
	public double getErrorRate() {
		return (1.0d - this.getAccuracy());
	}

    /**
     * Gets total weight.
     *
     * @return the total weight
     */
// Returns the total sum of the confusion matrix.
	// Only interesting if the matrix has not been normalized.
	public double getTotalWeight() {
		return this.total;
	}

	@Override
	public ContingencyMatrix clone() {
		return new ContingencyMatrix(this.matrix);
	}

}
