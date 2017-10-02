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
package com.rapidminer.tools.math.function.aggregation;

import com.rapidminer.example.Attribute;
import com.rapidminer.operator.ports.metadata.AttributeMetaData;
import com.rapidminer.tools.Ontology;


/**
 * An aggregation function which calculates the value for a given value array.
 *
 * @author Tobias Malbrecht, Ingo Mierswa
 */
public interface AggregationFunction {

    /**
     * Returns the name of the aggregation function.
     *
     * @return the name
     */
    public String getName();

    /**
     * Consider a new value and a corresponding weight by updating counters.
     *
     * @param value  the value
     * @param weight the weight
     */
    public void update(double value, double weight);

    /**
     * Consider a new value by updating counters.
     *
     * @param value the value
     */
    public void update(double value);

    /**
     * Returns the function value.
     *
     * @return the value
     */
    public double getValue();

    /**
     * Calculate function value for given values.
     * <p>
     * ATTENTION: counters might be reset and hence value history might be lost!
     *
     * @param values the values
     * @return the double
     */
    public double calculate(double[] values);

    /**
     * Calculate function value for given values and weights.
     * <p>
     * ATTENTION: counters might be reset and hence value history might be lost!
     *
     * @param values  the values
     * @param weights the weights
     * @return the double
     */
    public double calculate(double[] values, double[] weights);

    /**
     * Returns whether this function supports the given attribute.
     *
     * @param attribute the attribute
     * @return the boolean
     */
    public boolean supportsAttribute(Attribute attribute);

    /**
     * Returns whether this function supports the given attribute tested on the meta data.
     *
     * @param amd the amd
     * @return the boolean
     */
    public boolean supportsAttribute(AttributeMetaData amd);

    /**
     * Returns whether this function supports attributes of the given type, where valueType is one
     * of the static value types defined in {@link Ontology}.
     *
     * @param valueType the value type
     * @return the boolean
     */
    public boolean supportsValueType(int valueType);

    /**
     * Returns the result type of this {@link AggregationFunction} when applied on data of type
     * inputType as one of the static value types defined in {@link Ontology}.
     *
     * @param inputType the input type
     * @return the value type of result
     */
    public int getValueTypeOfResult(int inputType);
}
