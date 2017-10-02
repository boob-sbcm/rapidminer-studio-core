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
package com.rapidminer.operator.preprocessing.transformation.aggregation;

import com.rapidminer.example.Attribute;


/**
 * This class implements the Mode Aggregation function. This will calculate the least value of the
 * attribute of the examples within a group, that at least occurrs once.
 *
 * @author Sebastian Land
 */
public class LeastOccurringAggregationFunction extends NominalAggregationFunction {

    /**
     * The constant FUNCTION_LEAST_OCCURRING.
     */
    public static final String FUNCTION_LEAST_OCCURRING = "leastO";

    /**
     * Instantiates a new Least occurring aggregation function.
     *
     * @param sourceAttribute    the source attribute
     * @param ignoreMissings     the ignore missings
     * @param countOnlyDisctinct the count only disctinct
     */
    public LeastOccurringAggregationFunction(Attribute sourceAttribute, boolean ignoreMissings, boolean countOnlyDisctinct) {
		super(sourceAttribute, ignoreMissings, countOnlyDisctinct, FUNCTION_LEAST_OCCURRING, FUNCTION_SEPARATOR_OPEN,
				FUNCTION_SEPARATOR_CLOSE);
	}

    /**
     * Instantiates a new Least occurring aggregation function.
     *
     * @param sourceAttribute    the source attribute
     * @param ignoreMissings     the ignore missings
     * @param countOnlyDisctinct the count only disctinct
     * @param functionName       the function name
     * @param separatorOpen      the separator open
     * @param separatorClose     the separator close
     */
    public LeastOccurringAggregationFunction(Attribute sourceAttribute, boolean ignoreMissings, boolean countOnlyDisctinct,
			String functionName, String separatorOpen, String separatorClose) {
		super(sourceAttribute, ignoreMissings, countOnlyDisctinct, functionName, separatorOpen, separatorClose);
	}

	@Override
	public Aggregator createAggregator() {
		return new LeastOccurringAggregator(this);
	}
}
