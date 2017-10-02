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

import com.rapidminer.tools.expression.Expression;
import com.rapidminer.tools.expression.ExpressionException;
import com.rapidminer.tools.expression.ExpressionType;
import com.rapidminer.tools.expression.internal.antlr.AntlrParser;
import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Tests the results of {@link AntlrParser#parse(String)} for rounding functions.
 *
 * @author David Arnu, Thilo Kamradt
 */
public class AntlrParserRoundingTest extends AntlrParserTest {

	// round

    /**
     * Round down simple.
     */
    @Test
	public void roundDownSimple() {
		try {
			Expression expression = getExpressionWithFunctionContext("round(1.4)");
			assertEquals(ExpressionType.INTEGER, expression.getExpressionType());
			assertEquals(1, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Round up simple.
     */
    @Test
	public void roundUpSimple() {
		try {
			Expression expression = getExpressionWithFunctionContext("round(1.7)");
			assertEquals(ExpressionType.INTEGER, expression.getExpressionType());
			assertEquals(2, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Round 1 argument infinity.
     */
    @Test
	public void round1ArgumentInfinity() {
		try {
			Expression expression = getExpressionWithFunctionContext("round(INFINITY)");
			assertEquals(ExpressionType.INTEGER, expression.getExpressionType());
			assertEquals(Math.round(Double.POSITIVE_INFINITY), expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Round down 2 args.
     */
    @Test
	public void roundDown2Args() {
		try {
			Expression expression = getExpressionWithFunctionContext("round(1.3333,2)");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(1.33, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Round up 2 args.
     */
    @Test
	public void roundUp2Args() {
		try {
			Expression expression = getExpressionWithFunctionContext("round(1.666,2)");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(1.67, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Round up 2 args negative.
     */
    @Test
	public void roundUp2ArgsNegative() {
		try {
			Expression expression = getExpressionWithFunctionContext("round(-1.666,2)");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(-1.67, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Round up 2 args double.
     */
    @Test
	public void roundUp2ArgsDouble() {
		try {
			Expression expression = getExpressionWithFunctionContext("round(1.666,2.5)");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(1.67, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Round down 2 args double.
     */
    @Test
	public void roundDown2ArgsDouble() {
		try {
			Expression expression = getExpressionWithFunctionContext("round(1.3333,2.5)");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(1.33, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Round 2 argument infinity 1.
     */
    @Test
	public void round2ArgumentInfinity1() {
		try {
			Expression expression = getExpressionWithFunctionContext("round(INFINITY, 2)");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(Double.POSITIVE_INFINITY, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Round 2 argument infinity 2.
     */
    @Test
	public void round2ArgumentInfinity2() {
		try {
			Expression expression = getExpressionWithFunctionContext("round(2, INFINITY)");
			assertEquals(ExpressionType.INTEGER, expression.getExpressionType());
			assertEquals(0, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Round 2 argument neg infinity.
     */
    @Test
	public void round2ArgumentNegInfinity() {
		try {
			Expression expression = getExpressionWithFunctionContext("round(2, -INFINITY)");
			assertEquals(ExpressionType.INTEGER, expression.getExpressionType());
			assertEquals(Double.NaN, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Round 2 argument negative precission.
     */
    @Test
	public void round2ArgumentNegativePrecission() {
		try {
			Expression expression = getExpressionWithFunctionContext("round(2, -5)");
			assertEquals(ExpressionType.INTEGER, expression.getExpressionType());
			assertEquals(0, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Round missing 1 argument.
     */
    @Test
	public void roundMissing1Argument() {
		try {
			Expression expression = getExpressionWithFunctionContext("round(MISSING_NUMERIC)");
			assertEquals(ExpressionType.INTEGER, expression.getExpressionType());
			assertEquals(Double.NaN, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Round missing 2 argument first.
     */
    @Test
	public void roundMissing2ArgumentFirst() {
		try {
			Expression expression = getExpressionWithFunctionContext("round(MISSING_NUMERIC, 5)");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(Double.NaN, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Round missing 2 argument second.
     */
    @Test
	public void roundMissing2ArgumentSecond() {
		try {
			Expression expression = getExpressionWithFunctionContext("round(5.55,MISSING_NUMERIC)");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(6, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Round empty.
     */
    @Test
	public void roundEmpty() {
		try {
			getExpressionWithFunctionContext("round()");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Round wrong type.
     */
    @Test
	public void roundWrongType() {
		try {
			getExpressionWithFunctionContext("round(\"aa\")");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Round wrong types.
     */
    @Test
	public void roundWrongTypes() {
		try {
			getExpressionWithFunctionContext("round(\"aa\", \"bb\")");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

	// rint

    /**
     * Rint down simple.
     */
    @Test
	public void rintDownSimple() {
		try {
			Expression expression = getExpressionWithFunctionContext("rint(2.5)");
			assertEquals(ExpressionType.INTEGER, expression.getExpressionType());
			assertEquals(2, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Rint up simple.
     */
    @Test
	public void rintUpSimple() {
		try {
			Expression expression = getExpressionWithFunctionContext("rint(1.5)");
			assertEquals(ExpressionType.INTEGER, expression.getExpressionType());
			assertEquals(2, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Rint 1 argument infinity.
     */
    @Test
	public void rint1ArgumentInfinity() {
		try {
			Expression expression = getExpressionWithFunctionContext("rint(INFINITY)");
			assertEquals(ExpressionType.INTEGER, expression.getExpressionType());
			assertEquals(Math.rint(Double.POSITIVE_INFINITY), expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Rint down 2 args.
     */
    @Test
	public void rintDown2Args() {
		try {
			Expression expression = getExpressionWithFunctionContext("rint(1.3333,2)");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(1.33, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Rint up 2 args.
     */
    @Test
	public void rintUp2Args() {
		try {
			Expression expression = getExpressionWithFunctionContext("rint(1.666,2)");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(1.67, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Rint up 2 args negative.
     */
    @Test
	public void rintUp2ArgsNegative() {
		try {
			Expression expression = getExpressionWithFunctionContext("rint(-1.666,2)");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(-1.67, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Rint up 2 args double.
     */
    @Test
	public void rintUp2ArgsDouble() {
		try {
			Expression expression = getExpressionWithFunctionContext("rint(1.666,2.5)");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(1.67, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Rint down 2 args double.
     */
    @Test
	public void rintDown2ArgsDouble() {
		try {
			Expression expression = getExpressionWithFunctionContext("rint(1.3333,2.5)");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(1.33, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Rint 2 argument infinity 1.
     */
    @Test
	public void rint2ArgumentInfinity1() {
		try {
			Expression expression = getExpressionWithFunctionContext("rint(INFINITY, 2)");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(Double.POSITIVE_INFINITY, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Rint 2 argument infinity 2.
     */
    @Test
	public void rint2ArgumentInfinity2() {
		try {
			Expression expression = getExpressionWithFunctionContext("rint(2, INFINITY)");
			assertEquals(ExpressionType.INTEGER, expression.getExpressionType());
			assertEquals(Double.NaN, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Rint 2 argument neg infinity.
     */
    @Test
	public void rint2ArgumentNegInfinity() {
		try {
			Expression expression = getExpressionWithFunctionContext("rint(2, -INFINITY)");
			assertEquals(ExpressionType.INTEGER, expression.getExpressionType());
			assertEquals(Double.NaN, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Rint 2 argument negative precission.
     */
    @Test
	public void rint2ArgumentNegativePrecission() {
		try {
			Expression expression = getExpressionWithFunctionContext("round(2562, -3)");
			assertEquals(ExpressionType.INTEGER, expression.getExpressionType());
			assertEquals(3000, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Rint missing 1 argument.
     */
    @Test
	public void rintMissing1Argument() {
		try {
			Expression expression = getExpressionWithFunctionContext("rint(MISSING_NUMERIC)");
			assertEquals(ExpressionType.INTEGER, expression.getExpressionType());
			assertEquals(Double.NaN, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Rint missing 2 argument first.
     */
    @Test
	public void rintMissing2ArgumentFirst() {
		try {
			Expression expression = getExpressionWithFunctionContext("rint(MISSING_NUMERIC, 5)");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(Double.NaN, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Rint missing 2 argument second.
     */
    @Test
	public void rintMissing2ArgumentSecond() {
		try {
			Expression expression = getExpressionWithFunctionContext("rint(5.55,MISSING_NUMERIC)");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(6, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Rint empty.
     */
    @Test
	public void rintEmpty() {
		try {
			getExpressionWithFunctionContext("rint()");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Rint wrong type.
     */
    @Test
	public void rintWrongType() {
		try {
			getExpressionWithFunctionContext("rint(\"aa\")");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Rint wrong types.
     */
    @Test
	public void rintWrongTypes() {
		try {
			getExpressionWithFunctionContext("rint(\"aa\", \"bb\")");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

	// floor

    /**
     * Floor down simple.
     */
    @Test
	public void floorDownSimple() {
		try {
			Expression expression = getExpressionWithFunctionContext("floor(2.5)");
			assertEquals(ExpressionType.INTEGER, expression.getExpressionType());
			assertEquals(2, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Floor down simple negative.
     */
    @Test
	public void floorDownSimpleNegative() {
		try {
			Expression expression = getExpressionWithFunctionContext("floor(-2.5)");
			assertEquals(ExpressionType.INTEGER, expression.getExpressionType());
			assertEquals(-3, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Floor 1 argument infinity.
     */
    @Test
	public void floor1ArgumentInfinity() {
		try {
			Expression expression = getExpressionWithFunctionContext("floor(INFINITY)");
			assertEquals(ExpressionType.INTEGER, expression.getExpressionType());
			assertEquals(Double.POSITIVE_INFINITY, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Floor 1 argument neg infinity.
     */
    @Test
	public void floor1ArgumentNegInfinity() {
		try {
			Expression expression = getExpressionWithFunctionContext("floor(-INFINITY)");
			assertEquals(ExpressionType.INTEGER, expression.getExpressionType());
			assertEquals(Double.NEGATIVE_INFINITY, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Floor missing 1 argument.
     */
    @Test
	public void floorMissing1Argument() {
		try {
			Expression expression = getExpressionWithFunctionContext("floor(MISSING_NUMERIC)");
			assertEquals(ExpressionType.INTEGER, expression.getExpressionType());
			assertEquals(Double.NaN, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Floor empty.
     */
    @Test
	public void floorEmpty() {
		try {
			getExpressionWithFunctionContext("floor()");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Floor wrong type.
     */
    @Test
	public void floorWrongType() {
		try {
			getExpressionWithFunctionContext("floor(\"aa\")");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

	// ceil

    /**
     * Ceil simple.
     */
    @Test
	public void ceilSimple() {
		try {
			Expression expression = getExpressionWithFunctionContext("ceil(2.5)");
			assertEquals(ExpressionType.INTEGER, expression.getExpressionType());
			assertEquals(3, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Ceil simple negative.
     */
    @Test
	public void ceilSimpleNegative() {
		try {
			Expression expression = getExpressionWithFunctionContext("ceil(-2.5)");
			assertEquals(ExpressionType.INTEGER, expression.getExpressionType());
			assertEquals(-2, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Ceil 1 argument infinity.
     */
    @Test
	public void ceil1ArgumentInfinity() {
		try {
			Expression expression = getExpressionWithFunctionContext("ceil(INFINITY)");
			assertEquals(ExpressionType.INTEGER, expression.getExpressionType());
			assertEquals(Double.POSITIVE_INFINITY, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Ceil 1 argument neg infinity.
     */
    @Test
	public void ceil1ArgumentNegInfinity() {
		try {
			Expression expression = getExpressionWithFunctionContext("ceil(-INFINITY)");
			assertEquals(ExpressionType.INTEGER, expression.getExpressionType());
			assertEquals(Double.NEGATIVE_INFINITY, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Ceil missing 1 argument.
     */
    @Test
	public void ceilMissing1Argument() {
		try {
			Expression expression = getExpressionWithFunctionContext("ceil(MISSING_NUMERIC)");
			assertEquals(ExpressionType.INTEGER, expression.getExpressionType());
			assertEquals(Double.NaN, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Ceil empty.
     */
    @Test
	public void ceilEmpty() {
		try {
			getExpressionWithFunctionContext("ceil()");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Ceil wrong type.
     */
    @Test
	public void ceilWrongType() {
		try {
			getExpressionWithFunctionContext("ceil(\"aa\")");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

}
