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
package com.rapidminer.tools.expression.internal.function;

import com.rapidminer.example.ExampleSet;
import com.rapidminer.tools.expression.ExampleResolver;
import com.rapidminer.tools.expression.Expression;
import com.rapidminer.tools.expression.ExpressionException;
import com.rapidminer.tools.expression.ExpressionType;
import com.rapidminer.tools.expression.internal.antlr.AntlrParser;
import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Tests the results of {@link AntlrParser#parse(String)} from the statistical function block.
 *
 * @author David Arnu
 */
public class AntlrParserStatisticalTest extends AntlrParserTest {

    /**
     * Average all equal.
     */
// average
	@Test
	public void averageAllEqual() {
		try {
			Expression expression = getExpressionWithFunctionContext("avg(2,2,2,2,2)");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(2, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Average single.
     */
    @Test
	public void averageSingle() {
		try {
			Expression expression = getExpressionWithFunctionContext("avg(2)");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(2, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Average int.
     */
    @Test
	public void averageInt() {
		try {
			Expression expression = getExpressionWithFunctionContext("avg(1,2,3,4,5)");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(3, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Average double.
     */
    @Test
	public void averageDouble() {
		try {
			Expression expression = getExpressionWithFunctionContext("avg(1.5, 2.5, 2.5, 2.5)");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(2.25, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Average all wrong type.
     */
    @Test
	public void averageAllWrongType() {
		try {
			getExpressionWithFunctionContext("avg(\"aa\", \"bb\")");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Average mixed wrong type.
     */
    @Test
	public void averageMixedWrongType() {
		try {
			getExpressionWithFunctionContext("avg(\"aa\", 1)");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Average infinite value.
     */
    @Test
	public void averageInfiniteValue() {
		try {
			Expression expression = getExpressionWithFunctionContext("avg(1, 1/0)");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(Double.POSITIVE_INFINITY, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Average na value.
     */
    @Test
	public void averageNAValue() {
		try {
			Expression expression = getExpressionWithFunctionContext("avg(1, 0/0)");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(Double.NaN, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Average empty.
     */
    @Test
	public void averageEmpty() {
		try {
			getExpressionWithFunctionContext("avg()");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Min single integer.
     */
// minimum
	@Test
	public void minSingleInteger() {
		try {
			Expression expression = getExpressionWithFunctionContext("min(1)");
			assertEquals(ExpressionType.INTEGER, expression.getExpressionType());
			assertEquals(1, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Min two integer.
     */
    @Test
	public void minTwoInteger() {
		try {
			Expression expression = getExpressionWithFunctionContext("min(1,2)");
			assertEquals(ExpressionType.INTEGER, expression.getExpressionType());
			assertEquals(1, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Min two double.
     */
    @Test
	public void minTwoDouble() {
		try {
			Expression expression = getExpressionWithFunctionContext("min(2.5,1.5)");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(1.5, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Min two mixed.
     */
    @Test
	public void minTwoMixed() {
		try {
			Expression expression = getExpressionWithFunctionContext("min(2,1.5)");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(1.5, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Min na.
     */
    @Test
	public void minNA() {
		try {
			Expression expression = getExpressionWithFunctionContext("min(2,0/0)");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(Double.NaN, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Min mixed wrong type.
     */
    @Test
	public void minMixedWrongType() {
		try {
			getExpressionWithFunctionContext("min(\"aa\", 1)");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Min wrong type.
     */
    @Test
	public void minWrongType() {
		try {
			getExpressionWithFunctionContext("min(\"aa\")");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Min all wrong type.
     */
    @Test
	public void minAllWrongType() {
		try {
			getExpressionWithFunctionContext("min(\"aa\", \"bb\", \"cc\")");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Min infinity.
     */
    @Test
	public void minInfinity() {
		try {
			Expression expression = getExpressionWithFunctionContext("min(1.5, 2.5, 3.5, 4.5, 5.5, 1/0)");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(1.5, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Min neg infinity.
     */
    @Test
	public void minNegInfinity() {
		try {
			Expression expression = getExpressionWithFunctionContext("min(1.5, 2.5, 3.5, 4.5, 5.5, -1/0)");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(Double.NEGATIVE_INFINITY, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Max single integer.
     */
// maximum
	@Test
	public void maxSingleInteger() {
		try {
			Expression expression = getExpressionWithFunctionContext("max(1)");
			assertEquals(ExpressionType.INTEGER, expression.getExpressionType());
			assertEquals(1, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Max two integer.
     */
    @Test
	public void maxTwoInteger() {
		try {
			Expression expression = getExpressionWithFunctionContext("max(1,2)");
			assertEquals(ExpressionType.INTEGER, expression.getExpressionType());
			assertEquals(2, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Max two double.
     */
    @Test
	public void maxTwoDouble() {
		try {
			Expression expression = getExpressionWithFunctionContext("max(2.5,1.5)");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(2.5, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Max two mixed.
     */
    @Test
	public void maxTwoMixed() {
		try {
			Expression expression = getExpressionWithFunctionContext("max(2,1.5)");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(2, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Max na.
     */
    @Test
	public void maxNA() {
		try {
			Expression expression = getExpressionWithFunctionContext("max(2,0/0)");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(Double.NaN, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Max infinity.
     */
    @Test
	public void maxInfinity() {
		try {
			Expression expression = getExpressionWithFunctionContext("max(1.5, 2.5, 3.5, 4.5, 5.5, 1/0)");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(Double.POSITIVE_INFINITY, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Max neg infinity.
     */
    @Test
	public void maxNegInfinity() {
		try {
			Expression expression = getExpressionWithFunctionContext("max(1.5, 2.5, 3.5, 4.5, 5.5, -1/0)");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(5.5, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Max mixed wrong type.
     */
    @Test
	public void maxMixedWrongType() {
		try {
			getExpressionWithFunctionContext("max(\"aa\", 1)");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Max wrong type.
     */
    @Test
	public void maxWrongType() {
		try {
			getExpressionWithFunctionContext("max(\"aa\")");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Max all wrong type.
     */
    @Test
	public void maxAllWrongType() {
		try {
			getExpressionWithFunctionContext("max(\"aa\", \"bb\", \"cc\")");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Binom simple n smaller.
     */
// binominal
	@Test
	public void binomSimpleNSmaller() {
		try {
			Expression expression = getExpressionWithFunctionContext("binom(1,2)");
			assertEquals(ExpressionType.INTEGER, expression.getExpressionType());
			assertEquals(0, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Binom simple k smaller.
     */
    @Test
	public void binomSimpleKSmaller() {
		try {
			Expression expression = getExpressionWithFunctionContext("binom(5,2)");
			assertEquals(ExpressionType.INTEGER, expression.getExpressionType());
			assertEquals(10, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Binom all wrong type.
     */
    @Test
	public void binomAllWrongType() {
		try {
			getExpressionWithFunctionContext("binom(\"aa\", \"bb\")");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Binom first wrong type.
     */
    @Test
	public void binomFirstWrongType() {
		try {
			getExpressionWithFunctionContext("binom(1.5, 1)");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Binom second wrong type.
     */
    @Test
	public void binomSecondWrongType() {
		try {
			getExpressionWithFunctionContext("binom(10, 1.5)");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Binom empty.
     */
    @Test
	public void binomEmpty() {
		try {
			getExpressionWithFunctionContext("binom()");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Binom large numbers.
     */
    @Test
	public void binomLargeNumbers() {
		try {
			Expression expression = getExpressionWithFunctionContext("binom(20000,4)");
			assertEquals(ExpressionType.INTEGER, expression.getExpressionType());
			assertEquals(6.664666849995E15, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Binom na first.
     */
    @Test
	public void binomNAFirst() {
		try {
			ExampleSet exampleSet = makeMissingIntegerExampleSet();
			ExampleResolver resolver = new ExampleResolver(exampleSet);
			Expression expression = getExpressionWithFunctionsAndExamples("binom(5,[integer])", resolver);
			resolver.bind(exampleSet.getExample(0));
			assertEquals(ExpressionType.INTEGER, expression.getExpressionType());
			assertEquals(Double.NaN, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Binom na second.
     */
    @Test
	public void binomNASecond() {
		try {
			ExampleSet exampleSet = makeMissingIntegerExampleSet();
			ExampleResolver resolver = new ExampleResolver(exampleSet);
			Expression expression = getExpressionWithFunctionsAndExamples("binom([integer],5)", resolver);
			resolver.bind(exampleSet.getExample(0));
			assertEquals(ExpressionType.INTEGER, expression.getExpressionType());
			assertEquals(Double.NaN, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Sum single integer.
     */
// sum
	@Test
	public void sumSingleInteger() {
		try {
			Expression expression = getExpressionWithFunctionContext("sum(1)");
			assertEquals(ExpressionType.INTEGER, expression.getExpressionType());
			assertEquals(1, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Sum two integer.
     */
    @Test
	public void sumTwoInteger() {
		try {
			Expression expression = getExpressionWithFunctionContext("sum(1,2)");
			assertEquals(ExpressionType.INTEGER, expression.getExpressionType());
			assertEquals(3, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Sum two double.
     */
    @Test
	public void sumTwoDouble() {
		try {
			Expression expression = getExpressionWithFunctionContext("sum(2.5,1.5)");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(4, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Sum na.
     */
    @Test
	public void sumNA() {
		try {
			Expression expression = getExpressionWithFunctionContext("sum(2,0/0)");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(Double.NaN, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Sum two mixed.
     */
    @Test
	public void sumTwoMixed() {
		try {
			Expression expression = getExpressionWithFunctionContext("sum(2,1.5)");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(3.5, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Sum mixed wrong type.
     */
    @Test
	public void sumMixedWrongType() {
		try {
			getExpressionWithFunctionContext("sum(\"aa\", 1)");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Sum wrong type.
     */
    @Test
	public void sumWrongType() {
		try {
			getExpressionWithFunctionContext("sum(\"aa\")");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Sum all wrong type.
     */
    @Test
	public void sumAllWrongType() {
		try {
			getExpressionWithFunctionContext("sum(\"aa\", \"bb\", \"cc\")");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Sum infinity.
     */
    @Test
	public void sumInfinity() {
		try {
			Expression expression = getExpressionWithFunctionContext("sum(1.5, 2.5, 3.5, 4.5, 5.5, 1/0)");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(Double.POSITIVE_INFINITY, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Sum neg infinity.
     */
    @Test
	public void sumNegInfinity() {
		try {
			Expression expression = getExpressionWithFunctionContext("sum(1.5, 2.5, 3.5, 4.5, 5.5, -1/0)");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(Double.NEGATIVE_INFINITY, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

}
