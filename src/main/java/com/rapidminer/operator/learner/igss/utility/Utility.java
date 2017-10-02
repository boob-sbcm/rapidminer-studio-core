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
package com.rapidminer.operator.learner.igss.utility;

import com.rapidminer.operator.learner.igss.hypothesis.Hypothesis;


/**
 * Interface for all utility functions.
 *
 * @author Dirk Dach
 */
public interface Utility {

    /**
     * The constant UTILITY_TYPES.
     */
    public static final String[] UTILITY_TYPES = { "accuracy", "linear", "squared", "binomial", "wracc" };
    /**
     * The constant FIRST_TYPE_INDEX.
     */
    public static final int FIRST_TYPE_INDEX = 0;
    /**
     * The constant TYPE_ACCURACY.
     */
    public static final int TYPE_ACCURACY = 0;
    /**
     * The constant TYPE_LINEAR.
     */
    public static final int TYPE_LINEAR = 1;
    /**
     * The constant TYPE_SQUARED.
     */
    public static final int TYPE_SQUARED = 2;
    /**
     * The constant TYPE_BINOMIAL.
     */
    public static final int TYPE_BINOMIAL = 3;
    /**
     * The constant TYPE_WRACC.
     */
    public static final int TYPE_WRACC = 4;
    /**
     * The constant LAST_TYPE_INDEX.
     */
    public static final int LAST_TYPE_INDEX = 4;

    /**
     * Calculates the utility for the given number of examples,positive examples and hypothesis  @param totalWeight the total weight
     *
     * @param totalWeight         the total weight
     * @param totalPositiveWeight the total positive weight
     * @param hypo                the hypo
     * @return the double
     */
    public double utility(double totalWeight, double totalPositiveWeight, Hypothesis hypo);

    /**
     * Calculates the M-value needed for the GSS algorithm.  @param delta the delta
     *
     * @param delta   the delta
     * @param epsilon the epsilon
     * @return the double
     */
    public double calculateM(double delta, double epsilon);

    /**
     * Calculates the the unspecific confidence intervall. Uses Chernoff bounds if the number of
     * random experiments is too small and normal approximatione otherwise.
     *
     * @param totalWeight the total weight
     * @param delta       the delta
     * @return the double
     */
    public double confidenceIntervall(double totalWeight, double delta);

    /**
     * Calculates the the confidence intervall for a specific hypothesis. Uses Chernoff bounds if
     * the number of random experiments is too small and normal approximation otherwise.
     *
     * @param totalWeight         the total weight
     * @param totalPositiveWeight the total positive weight
     * @param hypo                the hypo
     * @param delta               the delta
     * @return the double
     */
    public double confidenceIntervall(double totalWeight, double totalPositiveWeight, Hypothesis hypo, double delta);

    /**
     * Returns an upper bound for the utility of refinements for the given hypothesis.  @param totalWeight the total weight
     *
     * @param totalWeight         the total weight
     * @param totalPositiveWeight the total positive weight
     * @param hypo                the hypo
     * @param delta               the delta
     * @return the upper bound
     */
    public double getUpperBound(double totalWeight, double totalPositiveWeight, Hypothesis hypo, double delta);
}
