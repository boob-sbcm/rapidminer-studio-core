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
package com.rapidminer.operator.learner.functions.linear;

import com.rapidminer.example.ExampleSet;
import com.rapidminer.operator.ProcessStoppedException;
import com.rapidminer.parameter.ParameterType;
import com.rapidminer.parameter.UndefinedParameterError;

import java.util.List;


/**
 * This interface is for all classes that implement an integrated attribute selection algorithm for
 * the {@link LinearRegression} operator.
 * <p>
 * All subclasses need to have an empty constructor for being built by reflection.
 *
 * @author Sebastian Land
 */
public interface LinearRegressionMethod {

    /**
     * The type Linear regression result.
     */
    public static class LinearRegressionResult {

        /**
         * The Coefficients.
         */
        public double[] coefficients;
        /**
         * The Error.
         */
        public double error;
        /**
         * The Is used attribute.
         */
        public boolean[] isUsedAttribute;
	}

    /**
     * This method performs the actual regression. There are passed the linear regression operator
     * itself as well as data and it's properties. Before this method is called, the linear
     * regression already has performed a regression on the full data set. This resulted in the
     * given coefficients. Please note, that if useBias is true, the last coefficient is the bias.
     *
     * @param regression             the regression
     * @param useBias                the use bias
     * @param ridge                  the ridge
     * @param exampleSet             the example set
     * @param isUsedAttribute        the is used attribute
     * @param numberOfExamples       the number of examples
     * @param numberOfUsedAttributes the number of used attributes
     * @param means                  the means
     * @param labelMean              the label mean
     * @param standardDeviations     the standard deviations
     * @param labelStandardDeviation the label standard deviation
     * @param coefficientsOnFullData the coefficients on full data
     * @param errorOnFullData        the error on full data
     * @return the linear regression result
     * @throws UndefinedParameterError the undefined parameter error
     * @throws ProcessStoppedException the process stopped exception
     */
    public LinearRegressionResult applyMethod(LinearRegression regression, boolean useBias, double ridge,
			ExampleSet exampleSet, boolean[] isUsedAttribute, int numberOfExamples, int numberOfUsedAttributes,
			double[] means, double labelMean, double[] standardDeviations, double labelStandardDeviation,
			double[] coefficientsOnFullData, double errorOnFullData) throws UndefinedParameterError, ProcessStoppedException;

    /**
     * This method must return a List of needed Parameters.
     *
     * @return the parameter types
     */
    public List<ParameterType> getParameterTypes();

}
