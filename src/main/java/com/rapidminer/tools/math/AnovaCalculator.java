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
package com.rapidminer.tools.math;

import com.rapidminer.tools.Tools;
import org.apache.commons.math3.distribution.FDistribution;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


/**
 * Determines if the null hypothesis (all actual mean values are the same) holds for the given
 * values. This class uses an ANalysis Of VAriances approach to determine probability that the null
 * hypothesis is wrong.
 *
 * @author Ingo Mierswa
 */
public class AnovaCalculator {

    /**
     * The type Anova significance test result.
     */
    public static class AnovaSignificanceTestResult extends SignificanceTestResult {

		private static final long serialVersionUID = 9007616378489018565L;

		private double sumSquaresBetween = 0.0d;

		private double sumSquaresResiduals = 0.0d;

		private double meanSquaresBetween = 0.0d;

		private double meanSquaresResiduals = 0.0d;

		private int df1 = 0;

		private int df2 = 0;

		private double alpha = 0.05;;

		private double fValue = 0.0d;

		private double prob = 0.0d;

        /**
         * Instantiates a new Anova significance test result.
         *
         * @param sumSquaresBetween   the sum squares between
         * @param sumSquaresResiduals the sum squares residuals
         * @param df1                 the df 1
         * @param df2                 the df 2
         * @param alpha               the alpha
         */
        public AnovaSignificanceTestResult(double sumSquaresBetween, double sumSquaresResiduals, int df1, int df2,
				double alpha) {
			this.sumSquaresBetween = sumSquaresBetween;
			this.sumSquaresResiduals = sumSquaresResiduals;
			this.df1 = df1;
			this.df2 = df2;
			this.alpha = alpha;
			this.meanSquaresBetween = sumSquaresBetween / df1;
			this.meanSquaresResiduals = sumSquaresResiduals / df2;
			this.fValue = meanSquaresBetween / meanSquaresResiduals;
			FDistribution fDist = new FDistribution(df1, df2);
			this.prob = 1.0d - fDist.cumulativeProbability(this.fValue);
		}

		@Override
		public String getName() {
			return "Anova Test";
		}

		@Override
		public String toString() {
			return "ANOVA result (f=" + Tools.formatNumber(fValue) + ", prob=" + Tools.formatNumber(prob) + ", alpha="
					+ Tools.formatNumber(alpha) + ")";
		}

		@Override
		public double getProbability() {
			return prob;
		}

        /**
         * Gets sum squares between.
         *
         * @return the sum squares between
         */
        public double getSumSquaresBetween() {
			return this.sumSquaresBetween;
		}

        /**
         * Gets sum squares residuals.
         *
         * @return the sum squares residuals
         */
        public double getSumSquaresResiduals() {
			return this.sumSquaresResiduals;
		}

        /**
         * Gets mean squares between.
         *
         * @return the mean squares between
         */
        public double getMeanSquaresBetween() {
			return this.meanSquaresBetween;
		}

        /**
         * Gets mean squares residuals.
         *
         * @return the mean squares residuals
         */
        public double getMeanSquaresResiduals() {
			return this.meanSquaresResiduals;
		}

        /**
         * Gets df 1.
         *
         * @return the df 1
         */
        public int getDf1() {
			return this.df1;
		}

        /**
         * Gets df 2.
         *
         * @return the df 2
         */
        public int getDf2() {
			return this.df2;
		}

        /**
         * Gets alpha.
         *
         * @return the alpha
         */
        public double getAlpha() {
			return this.alpha;
		}

        /**
         * Gets f value.
         *
         * @return the f value
         */
        public double getFValue() {
			return this.fValue;
		}
	}

	private double alpha = 0.05;

	private List<TestGroup> groups = new LinkedList<TestGroup>();

    /**
     * Sets alpha.
     *
     * @param alpha the alpha
     */
    public void setAlpha(double alpha) {
		this.alpha = alpha;
	}

    /**
     * Add group.
     *
     * @param group the group
     */
    public void addGroup(TestGroup group) {
		groups.add(group);
	}

    /**
     * Add group.
     *
     * @param numberOfValues the number of values
     * @param mean           the mean
     * @param variance       the variance
     */
    public void addGroup(double numberOfValues, double mean, double variance) {
		addGroup(new TestGroup(numberOfValues, mean, variance));
	}

    /**
     * Clear groups.
     */
    public void clearGroups() {
		groups.clear();
	}

    /**
     * Perform significance test significance test result.
     *
     * @return the significance test result
     * @throws SignificanceCalculationException the significance calculation exception
     */
    public SignificanceTestResult performSignificanceTest() throws SignificanceCalculationException {
		if (groups.size() < 2) {
			throw new SignificanceCalculationException("Not enough groups added (current number of groups: " + groups.size()
					+ ", must be at least 2");
		}

		double meanOfMeans = 0.0d;
		Iterator<TestGroup> i = groups.iterator();
		while (i.hasNext()) {
			TestGroup group = i.next();
			meanOfMeans += group.getMean();
		}
		meanOfMeans /= groups.size();

		double sumSquaresBetween = 0.0d;
		i = groups.iterator();
		while (i.hasNext()) {
			TestGroup group = i.next();
			double diff = group.getMean() - meanOfMeans;
			sumSquaresBetween += group.getNumber() * (diff * diff);
		}

		double sumSquaresResiduals = 0.0d;
		int counterSum = 0;
		i = groups.iterator();
		while (i.hasNext()) {
			TestGroup group = i.next();
			sumSquaresResiduals += (group.getNumber() - 1) * group.getVariance();
			counterSum += group.getNumber();
		}

		if (groups.size() == counterSum) {
			throw new SignificanceCalculationException("Number of groups can't be equal to the number of examples.");
		}

		return new AnovaSignificanceTestResult(sumSquaresBetween, sumSquaresResiduals, groups.size() - 1, counterSum
				- groups.size(), alpha);
	}
}
