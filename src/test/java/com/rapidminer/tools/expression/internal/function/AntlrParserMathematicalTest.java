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
 * Tests the results of {@link AntlrParser#parse(String)} for mathematical functions.
 *
 * @author David Arnu, Marcel Seifert
 */
public class AntlrParserMathematicalTest extends AntlrParserTest {

    /**
     * Sqrt tests
     */
    @Test
	public void sqrtInt() {
		try {
			Expression expression = getExpressionWithFunctionContext("sqrt(16)");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(4, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Sqrt double.
     */
    @Test
	public void sqrtDouble() {
		try {
			Expression expression = getExpressionWithFunctionContext("sqrt(12.5)");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(Math.sqrt(12.5), expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Sqrt neg.
     */
    @Test
	public void sqrtNeg() {
		try {
			Expression expression = getExpressionWithFunctionContext("sqrt(-4)");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(Double.NaN, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Sqrt na n.
     */
    @Test
	public void sqrtNaN() {
		try {
			Expression expression = getExpressionWithFunctionContext("sqrt(MISSING_NUMERICAL)");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(Double.NaN, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Sqrt empty.
     */
    @Test
	public void sqrtEmpty() {
		try {
			getExpressionWithFunctionContext("sqrt()");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Sqrt string.
     */
    @Test
	public void sqrtString() {
		try {
			getExpressionWithFunctionContext("sqrt( \"blup\")");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * exp tests
     */
    @Test
	public void expInt() {
		try {
			Expression expression = getExpressionWithFunctionContext("exp(1)");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(Math.E, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Exp double.
     */
    @Test
	public void expDouble() {
		try {
			Expression expression = getExpressionWithFunctionContext("exp(1.0)");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(Math.E, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Exp neg inf.
     */
    @Test
	public void expNegInf() {
		try {
			Expression expression = getExpressionWithFunctionContext("exp(-INFINITY)");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(0.0, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Exp pos inf.
     */
    @Test
	public void expPosInf() {
		try {
			Expression expression = getExpressionWithFunctionContext("exp(INFINITY)");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(Double.POSITIVE_INFINITY, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Exp zero.
     */
    @Test
	public void expZero() {
		try {
			Expression expression = getExpressionWithFunctionContext("exp(0)");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(1, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Exp na n.
     */
    @Test
	public void expNaN() {
		try {
			Expression expression = getExpressionWithFunctionContext("exp(MISSING_NUMERICAL)");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(Double.NaN, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Exp empty.
     */
    @Test
	public void expEmpty() {
		try {
			getExpressionWithFunctionContext("exp()");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Exp string.
     */
    @Test
	public void expString() {
		try {
			getExpressionWithFunctionContext("exp( \"blup\")");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * ln tests
     */
    @Test
	public void lnInt() {
		try {
			Expression expression = getExpressionWithFunctionContext("ln(1)");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(0.0, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Ln double.
     */
    @Test
	public void lnDouble() {
		try {
			Expression expression = getExpressionWithFunctionContext("ln(1.0)");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(0.0, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Ln negative.
     */
    @Test
	public void lnNegative() {
		try {
			Expression expression = getExpressionWithFunctionContext("ln(-1.0)");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(Double.NaN, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Ln neg inf.
     */
    @Test
	public void lnNegInf() {
		try {
			Expression expression = getExpressionWithFunctionContext("ln(-INFINITY)");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(Double.NaN, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Ln pos inf.
     */
    @Test
	public void lnPosInf() {
		try {
			Expression expression = getExpressionWithFunctionContext("ln(INFINITY)");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(Double.POSITIVE_INFINITY, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Ln zero.
     */
    @Test
	public void lnZero() {
		try {
			Expression expression = getExpressionWithFunctionContext("ln(0.0)");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(Double.NEGATIVE_INFINITY, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Ln na n.
     */
    @Test
	public void lnNaN() {
		try {
			Expression expression = getExpressionWithFunctionContext("ln(MISSING_NUMERICAL)");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(Double.NaN, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Ln empty.
     */
    @Test
	public void lnEmpty() {
		try {
			getExpressionWithFunctionContext("ln()");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Ln string.
     */
    @Test
	public void lnString() {
		try {
			getExpressionWithFunctionContext("ln( \"blup\")");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * log tests
     */
    @Test
	public void logInt() {
		try {
			Expression expression = getExpressionWithFunctionContext("log(1)");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(0.0, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Log double.
     */
    @Test
	public void logDouble() {
		try {
			Expression expression = getExpressionWithFunctionContext("log(1.0)");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(0.0, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Log negative.
     */
    @Test
	public void logNegative() {
		try {
			Expression expression = getExpressionWithFunctionContext("log(-1.0)");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(Double.NaN, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Log neg inf.
     */
    @Test
	public void logNegInf() {
		try {
			Expression expression = getExpressionWithFunctionContext("log(-INFINITY)");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(Double.NaN, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Log pos inf.
     */
    @Test
	public void logPosInf() {
		try {
			Expression expression = getExpressionWithFunctionContext("log(INFINITY)");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(Double.POSITIVE_INFINITY, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Log zero.
     */
    @Test
	public void logZero() {
		try {
			Expression expression = getExpressionWithFunctionContext("log(0.0)");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(Double.NEGATIVE_INFINITY, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Log na n.
     */
    @Test
	public void logNaN() {
		try {
			Expression expression = getExpressionWithFunctionContext("log(MISSING_NUMERICAL)");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(Double.NaN, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Log empty.
     */
    @Test
	public void logEmpty() {
		try {
			getExpressionWithFunctionContext("log()");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Log string.
     */
    @Test
	public void logString() {
		try {
			getExpressionWithFunctionContext("log( \"blup\")");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * ld tests
     */
    @Test
	public void ldInt() {
		try {
			Expression expression = getExpressionWithFunctionContext("ld(1)");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(0.0, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Ld double.
     */
    @Test
	public void ldDouble() {
		try {
			Expression expression = getExpressionWithFunctionContext("ld(1.0)");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(0.0, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Ld negative.
     */
    @Test
	public void ldNegative() {
		try {
			Expression expression = getExpressionWithFunctionContext("ld(-1.0)");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(Double.NaN, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Ld neg inf.
     */
    @Test
	public void ldNegInf() {
		try {
			Expression expression = getExpressionWithFunctionContext("ld(-INFINITY)");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(Double.NaN, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Ld pos inf.
     */
    @Test
	public void ldPosInf() {
		try {
			Expression expression = getExpressionWithFunctionContext("ld(INFINITY)");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(Double.POSITIVE_INFINITY, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Ld zero.
     */
    @Test
	public void ldZero() {
		try {
			Expression expression = getExpressionWithFunctionContext("ld(0.0)");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(Double.NEGATIVE_INFINITY, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Ld na n.
     */
    @Test
	public void ldNaN() {
		try {
			Expression expression = getExpressionWithFunctionContext("ld(MISSING_NUMERICAL)");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(Double.NaN, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Ld empty.
     */
    @Test
	public void ldEmpty() {
		try {
			getExpressionWithFunctionContext("ld()");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Ld string.
     */
    @Test
	public void ldString() {
		try {
			getExpressionWithFunctionContext("ld( \"blup\")");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * sgn tests
     */
    @Test
	public void sgnInt() {
		try {
			Expression expression = getExpressionWithFunctionContext("sgn(1)");
			assertEquals(ExpressionType.INTEGER, expression.getExpressionType());
			assertEquals(1, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Sgn double.
     */
    @Test
	public void sgnDouble() {
		try {
			Expression expression = getExpressionWithFunctionContext("sgn(5.4)");
			assertEquals(ExpressionType.INTEGER, expression.getExpressionType());
			assertEquals(1, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Sgn negative.
     */
    @Test
	public void sgnNegative() {
		try {
			Expression expression = getExpressionWithFunctionContext("sgn(-8.7)");
			assertEquals(ExpressionType.INTEGER, expression.getExpressionType());
			assertEquals(-1, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Sgn neg inf.
     */
    @Test
	public void sgnNegInf() {
		try {
			Expression expression = getExpressionWithFunctionContext("sgn(-INFINITY)");
			assertEquals(ExpressionType.INTEGER, expression.getExpressionType());
			assertEquals(-1, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Sgn pos inf.
     */
    @Test
	public void sgnPosInf() {
		try {
			Expression expression = getExpressionWithFunctionContext("sgn(INFINITY)");
			assertEquals(ExpressionType.INTEGER, expression.getExpressionType());
			assertEquals(1, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Sgn zero.
     */
    @Test
	public void sgnZero() {
		try {
			Expression expression = getExpressionWithFunctionContext("sgn(0.0)");
			assertEquals(ExpressionType.INTEGER, expression.getExpressionType());
			assertEquals(0, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Sgn neg zero.
     */
    @Test
	public void sgnNegZero() {
		try {
			Expression expression = getExpressionWithFunctionContext("sgn(-0.0)");
			assertEquals(ExpressionType.INTEGER, expression.getExpressionType());
			assertEquals(-0, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Sgn na n.
     */
    @Test
	public void sgnNaN() {
		try {
			Expression expression = getExpressionWithFunctionContext("sgn(MISSING_NUMERICAL)");
			assertEquals(ExpressionType.INTEGER, expression.getExpressionType());
			assertEquals(Double.NaN, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Sgn missing int.
     */
    @Test
	public void sgnMissingInt() {
		try {
			ExampleSet exampleSet = makeMissingIntegerExampleSet();
			ExampleResolver resolver = new ExampleResolver(exampleSet);
			Expression expression = getExpressionWithFunctionsAndExamples("sgn([integer])", resolver);
			resolver.bind(exampleSet.getExample(0));
			assertEquals(ExpressionType.INTEGER, expression.getExpressionType());
			assertEquals(Double.NaN, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Sgn empty.
     */
    @Test
	public void sgnEmpty() {
		try {
			getExpressionWithFunctionContext("sgn()");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Sgn string.
     */
    @Test
	public void sgnString() {
		try {
			getExpressionWithFunctionContext("sgn( \"blup\")");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * abs tests
     */
    @Test
	public void absInt() {
		try {
			Expression expression = getExpressionWithFunctionContext("abs(1)");
			assertEquals(ExpressionType.INTEGER, expression.getExpressionType());
			assertEquals(1, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Abs double.
     */
    @Test
	public void absDouble() {
		try {
			Expression expression = getExpressionWithFunctionContext("abs(5.4)");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(5.4, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Abs negative.
     */
    @Test
	public void absNegative() {
		try {
			Expression expression = getExpressionWithFunctionContext("abs(-8.7)");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(8.7, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Abs neg inf.
     */
    @Test
	public void absNegInf() {
		try {
			Expression expression = getExpressionWithFunctionContext("abs(-INFINITY)");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(Double.POSITIVE_INFINITY, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Abs pos inf.
     */
    @Test
	public void absPosInf() {
		try {
			Expression expression = getExpressionWithFunctionContext("abs(INFINITY)");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(Double.POSITIVE_INFINITY, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Abs zero.
     */
    @Test
	public void absZero() {
		try {
			Expression expression = getExpressionWithFunctionContext("abs(0)");
			assertEquals(ExpressionType.INTEGER, expression.getExpressionType());
			assertEquals(0, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Abs na n.
     */
    @Test
	public void absNaN() {
		try {
			Expression expression = getExpressionWithFunctionContext("sgn(MISSING_NUMERICAL)");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(Double.NaN, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Abs missing int.
     */
    @Test
	public void absMissingInt() {
		try {
			ExampleSet exampleSet = makeMissingIntegerExampleSet();
			ExampleResolver resolver = new ExampleResolver(exampleSet);
			Expression expression = getExpressionWithFunctionsAndExamples("abs([integer])", resolver);
			resolver.bind(exampleSet.getExample(0));
			assertEquals(ExpressionType.INTEGER, expression.getExpressionType());
			assertEquals(Double.NaN, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Abs empty.
     */
    @Test
	public void absEmpty() {
		try {
			getExpressionWithFunctionContext("abs()");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Abs string.
     */
    @Test
	public void absString() {
		try {
			getExpressionWithFunctionContext("abs( \"blup\")");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

}
