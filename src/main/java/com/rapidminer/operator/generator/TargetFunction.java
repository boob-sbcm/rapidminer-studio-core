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
package com.rapidminer.operator.generator;

import com.rapidminer.example.Attribute;
import com.rapidminer.operator.ports.metadata.ExampleSetMetaData;
import com.rapidminer.tools.RandomGenerator;


/**
 * A target function which is used for the {@link ExampleSetGenerator} operator. All target function
 * will need an empty constructor since they are initialized via reflection.
 *
 * @author Ingo Mierswa
 */
public interface TargetFunction {

    /**
     * Will be thrown if an error occurs during the calculation of the target function.
     */
    public static class FunctionException extends Exception {

		private static final long serialVersionUID = -990633489806141677L;

		private String functionName;

        /**
         * Instantiates a new Function exception.
         *
         * @param functionName the function name
         * @param message      the message
         */
        public FunctionException(String functionName, String message) {
			super(message);
			this.functionName = functionName;
		}

        /**
         * Gets function name.
         *
         * @return the function name
         */
        public String getFunctionName() {
			return functionName;
		}
	}

    /**
     * Should be called before the data is created.  @param random the random
     *
     * @param random the random
     */
    public void init(RandomGenerator random);

    /**
     * Calculates the target function on arguments.  @param args the args
     *
     * @param args the args
     * @return the double
     * @throws FunctionException the function exception
     */
    public double calculate(double[] args) throws FunctionException;

    /**
     * Returns the label attribute.  @return the label
     *
     * @return the label
     */
    public Attribute getLabel();

    /**
     * Creates and returns a number of arguments.  @param dimension the dimension
     *
     * @param dimension the dimension
     * @param random    the random
     * @return the double [ ]
     * @throws FunctionException the function exception
     */
    public double[] createArguments(int dimension, RandomGenerator random) throws FunctionException;

    /**
     * Sets the lower bound for the arguments.  @param lower the lower
     *
     * @param lower the lower
     */
    public void setLowerArgumentBound(double lower);

    /**
     * Sets the upper bound for the arguments.  @param upper the upper
     *
     * @param upper the upper
     */
    public void setUpperArgumentBound(double upper);

    /**
     * Sets the maximal number of examples. This might be used by some target functions in order to
     * create proper arguments.
     *
     * @param number the number
     */
    public void setTotalNumberOfExamples(int number);

    /**
     * Sets the maximal number of attributes. This might be used by some target functions in order
     * to create proper arguments.
     *
     * @param number the number
     */
    public void setTotalNumberOfAttributes(int number);

    /**
     * This method returns the meta data of the generated data.
     *
     * @return the generated meta data
     */
    public ExampleSetMetaData getGeneratedMetaData();

    /**
     * Gets min number of attributes.
     *
     * @return The minimum number of attributes this functions must produce
     */
    public int getMinNumberOfAttributes();

    /**
     * Gets max number of attributes.
     *
     * @return The maximum number of attributes this functions can produce
     */
    public int getMaxNumberOfAttributes();
}
