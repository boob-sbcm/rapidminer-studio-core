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
 * Tests the results of {@link AntlrParser#parse(String)} for bitwise functions.
 *
 * @author David Arnu
 */
public class AntlrParserBitwiseTest extends AntlrParserTest {

    /**
     * Bit or simple.
     */
    @Test
	public void bitOrSimple() {
		try {
			Expression expression = getExpressionWithFunctionContext("bit_or(2,1)");
			assertEquals(ExpressionType.INTEGER, expression.getExpressionType());
			assertEquals(3, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Bit or negative.
     */
    @Test
	public void bitOrNegative() {
		try {
			Expression expression = getExpressionWithFunctionContext("bit_or(-2,1)");
			assertEquals(ExpressionType.INTEGER, expression.getExpressionType());
			assertEquals(-1, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Bit or double integer.
     */
    @Test
	public void bitOrDoubleInteger() {
		try {
			getExpressionWithFunctionContext(" bit_or(2.5,1)");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Bit or integer double.
     */
    @Test
	public void bitOrIntegerDouble() {
		try {
			getExpressionWithFunctionContext(" bit_or(2,1.5)");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Bit or infinity.
     */
    @Test
	public void bitOrInfinity() {
		try {
			getExpressionWithFunctionContext(" bit_or(2,INFINITY)");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Bit or missing.
     */
    @Test
	public void bitOrMissing() {
		try {
			getExpressionWithFunctionContext(" bit_or(2,MISSING_NUMERIC)");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Bit or wrong type.
     */
    @Test
	public void bitOrWrongType() {
		try {
			getExpressionWithFunctionContext(" bit_or(\"aa\",1.5)");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Bit empty.
     */
    @Test
	public void bitEmpty() {
		try {
			getExpressionWithFunctionContext(" bit_or()");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

	// bit XOR

    /**
     * Bit xor simple.
     */
    @Test
	public void bitXorSimple() {
		try {
			Expression expression = getExpressionWithFunctionContext("bit_xor(6,5)");
			assertEquals(ExpressionType.INTEGER, expression.getExpressionType());
			assertEquals(3, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Bit xor negative.
     */
    @Test
	public void bitXorNegative() {
		try {
			Expression expression = getExpressionWithFunctionContext("bit_xor(-2,1)");
			assertEquals(ExpressionType.INTEGER, expression.getExpressionType());
			assertEquals(-1, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Bit xor double integer.
     */
    @Test
	public void bitXorDoubleInteger() {
		try {
			getExpressionWithFunctionContext(" bit_xor(2.5,1)");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Bit xor integer double.
     */
    @Test
	public void bitXorIntegerDouble() {
		try {
			getExpressionWithFunctionContext(" bit_xor(2,1.5)");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Bit xor infinity.
     */
    @Test
	public void bitXorInfinity() {
		try {
			getExpressionWithFunctionContext(" bit_xor(2,INFINITY)");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Bit xor missing.
     */
    @Test
	public void bitXorMissing() {
		try {
			getExpressionWithFunctionContext(" bit_xor(2,MISSING_NUMERIC)");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Bit xor wrong type.
     */
    @Test
	public void bitXorWrongType() {
		try {
			getExpressionWithFunctionContext(" bit_xor(\"aa\",1.5)");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Bit xor empty.
     */
    @Test
	public void bitXorEmpty() {
		try {
			getExpressionWithFunctionContext(" bit_xor()");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

	// bit AND

    /**
     * Bit and simple.
     */
    @Test
	public void bitAndSimple() {
		try {
			Expression expression = getExpressionWithFunctionContext("bit_and(6,5)");
			assertEquals(ExpressionType.INTEGER, expression.getExpressionType());
			assertEquals(4, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Bit and negative.
     */
    @Test
	public void bitAndNegative() {
		try {
			Expression expression = getExpressionWithFunctionContext("bit_and(-2,5)");
			assertEquals(ExpressionType.INTEGER, expression.getExpressionType());
			assertEquals(4, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Bit and double integer.
     */
    @Test
	public void bitAndDoubleInteger() {
		try {
			getExpressionWithFunctionContext("bit_and(2.5,1)");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Bit and integer double.
     */
    @Test
	public void bitAndIntegerDouble() {
		try {
			getExpressionWithFunctionContext(" bit_and(2,1.5)");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Bit and infinity.
     */
    @Test
	public void bitAndInfinity() {
		try {
			getExpressionWithFunctionContext(" bit_and(2,INFINITY)");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Bit and missing.
     */
    @Test
	public void bitAndMissing() {
		try {
			getExpressionWithFunctionContext(" bit_and(2,MISSING_NUMERIC)");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Bit and wrong type.
     */
    @Test
	public void bitAndWrongType() {
		try {
			getExpressionWithFunctionContext(" bit_and(\"aa\",1.5)");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Bit and empty.
     */
    @Test
	public void bitAndEmpty() {
		try {
			getExpressionWithFunctionContext(" bit_and()");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

	// bit NOT

    /**
     * Bit not simple.
     */
    @Test
	public void bitNotSimple() {
		try {
			Expression expression = getExpressionWithFunctionContext("bit_not(2)");
			assertEquals(ExpressionType.INTEGER, expression.getExpressionType());
			assertEquals(-3, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Bit not negative.
     */
    @Test
	public void bitNotNegative() {
		try {
			Expression expression = getExpressionWithFunctionContext("bit_not(-2)");
			assertEquals(ExpressionType.INTEGER, expression.getExpressionType());
			assertEquals(1, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Bit not double.
     */
    @Test
	public void bitNotDouble() {
		try {
			getExpressionWithFunctionContext(" bit_not(2.5)");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Bit not infinity.
     */
    @Test
	public void bitNotInfinity() {
		try {
			getExpressionWithFunctionContext(" bit_not(INFINITY)");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Bit not missing.
     */
    @Test
	public void bitNotMissing() {
		try {
			getExpressionWithFunctionContext(" bit_not(MISSING_NUMERIC)");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Bit not wrong type.
     */
    @Test
	public void bitNotWrongType() {
		try {
			getExpressionWithFunctionContext(" bit_not(\"aa\")");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Bit not empty.
     */
    @Test
	public void bitNotEmpty() {
		try {
			getExpressionWithFunctionContext(" bit_not()");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}
}
