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
package com.rapidminer.tools.expression.internal.function.text;

import com.rapidminer.tools.Ontology;
import com.rapidminer.tools.expression.*;
import com.rapidminer.tools.expression.internal.SimpleExpressionEvaluator;
import com.rapidminer.tools.expression.internal.function.AbstractFunction;

import java.util.concurrent.Callable;


/**
 * Abstract class for a {@link Function} that has two String arguments and returns a boolean
 * argument.
 *
 * @author David Arnu
 */
public abstract class Abstract2StringInputBooleanOutputFunction extends AbstractFunction {

    /**
     * Constructs an AbstractFunction with {@link FunctionDescription} generated from the arguments
     * and the function name generated from the description.
     *
     * @param i18n the 18 n
     */
    public Abstract2StringInputBooleanOutputFunction(String i18n) {
		super(i18n, 2, Ontology.BINOMINAL);
	}

	@Override
	public ExpressionEvaluator compute(ExpressionEvaluator... inputEvaluators) {
		if (inputEvaluators.length != 2) {
			throw new FunctionInputException("expression_parser.function_wrong_input", getFunctionName(), 2,
					inputEvaluators.length);
		}
		ExpressionType type = getResultType(inputEvaluators);

		ExpressionEvaluator left = inputEvaluators[0];
		ExpressionEvaluator right = inputEvaluators[1];

		return new SimpleExpressionEvaluator(makeBooleanCallable(left, right), isResultConstant(inputEvaluators), type);
	}

    /**
     * Builds a Boolean callable from left and right using {@link #compute(String, String)}, where
     * constant child results are evaluated.
     *
     * @param left  the left input
     * @param right the right input
     * @return the resulting callable<Boolean>
     */
    protected Callable<Boolean> makeBooleanCallable(ExpressionEvaluator left, ExpressionEvaluator right) {
		final Callable<String> funcLeft = left.getStringFunction();
		final Callable<String> funcRight = right.getStringFunction();
		try {

			final String valueLeft = left.isConstant() ? funcLeft.call() : "";
			final String valueRight = right.isConstant() ? funcRight.call() : "";

			if (left.isConstant() && right.isConstant()) {
				final Boolean result = compute(valueLeft, valueRight);

				return new Callable<Boolean>() {

					@Override
					public Boolean call() throws Exception {
						return result;
					}

				};
			} else if (left.isConstant()) {
				return new Callable<Boolean>() {

					@Override
					public Boolean call() throws Exception {
						return compute(valueLeft, funcRight.call());
					}

				};

			} else if (right.isConstant()) {
				return new Callable<Boolean>() {

					@Override
					public Boolean call() throws Exception {
						return compute(funcLeft.call(), valueRight);
					}
				};

			} else {
				return new Callable<Boolean>() {

					@Override
					public Boolean call() throws Exception {
						return compute(funcLeft.call(), funcRight.call());
					}
				};
			}
		} catch (ExpressionParsingException e) {
			throw e;
		} catch (Exception e) {
			throw new ExpressionParsingException(e);
		}
	}

    /**
     * Computes the result for two input String values.
     *
     * @param value1 the value 1
     * @param value2 the value 2
     * @return the result of the computation.
     */
    protected abstract Boolean compute(String value1, String value2);

	@Override
	protected ExpressionType computeType(ExpressionType... inputTypes) {
		ExpressionType left = inputTypes[0];
		ExpressionType right = inputTypes[1];
		if (left == ExpressionType.STRING && right == ExpressionType.STRING) {
			return ExpressionType.BOOLEAN;
		} else {
			throw new FunctionInputException("expression_parser.function_wrong_type", getFunctionName(), "nominal");
		}
	}

}
