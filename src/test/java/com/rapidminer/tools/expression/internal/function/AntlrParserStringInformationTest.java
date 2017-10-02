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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.rapidminer.tools.expression.Expression;
import com.rapidminer.tools.expression.ExpressionException;
import com.rapidminer.tools.expression.ExpressionType;
import com.rapidminer.tools.expression.internal.antlr.AntlrParser;


/**
 * Tests the results of {@link AntlrParser#parse(String)} for String information functions.
 *
 * @author David Arnu, Thilo Kamradt
 */
public class AntlrParserStringInformationTest extends AntlrParserTest {

    /**
     * Compare equal.
     */
// compare
	@Test
	public void compareEqual() {
		try {
			Expression expression = getExpressionWithFunctionContext("compare(\"abc\", \"abc\")");
			assertEquals(ExpressionType.INTEGER, expression.getExpressionType());
			assertEquals(0, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Compare one smaller.
     */
    @Test
	public void compareOneSmaller() {
		try {
			Expression expression = getExpressionWithFunctionContext("compare(\"abc\", \"abcd\")");
			assertEquals(ExpressionType.INTEGER, expression.getExpressionType());
			assertEquals(-1, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Compare two smaller.
     */
    @Test
	public void compareTwoSmaller() {
		try {
			Expression expression = getExpressionWithFunctionContext("compare(\"abc\", \"abcde\")");
			assertEquals(ExpressionType.INTEGER, expression.getExpressionType());
			assertEquals(-2, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Compare larger.
     */
    @Test
	public void compareLarger() {
		try {
			Expression expression = getExpressionWithFunctionContext("compare(\"babc\", \"abc\")");
			assertEquals(ExpressionType.INTEGER, expression.getExpressionType());
			assertEquals(1, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Compare missing 1.
     */
    @Test
	public void compareMissing1() {
		try {
			Expression expression = getExpressionWithFunctionContext("compare( MISSING_NOMINAL, \"abc\")");
			assertEquals(ExpressionType.INTEGER, expression.getExpressionType());
			assertEquals(Double.NaN, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Compare missing 2.
     */
    @Test
	public void compareMissing2() {
		try {
			Expression expression = getExpressionWithFunctionContext("compare(\"abc\", MISSING_NOMINAL)");
			assertEquals(ExpressionType.INTEGER, expression.getExpressionType());
			assertEquals(Double.NaN, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Compare wrong first type.
     */
    @Test
	public void compareWrongFirstType() {
		try {
			getExpressionWithFunctionContext(" compare(5, \"abc\")");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Compare wrong second type.
     */
    @Test
	public void compareWrongSecondType() {
		try {
			getExpressionWithFunctionContext(" compare(\"abc\",5)");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Compare wrong argument number.
     */
    @Test
	public void compareWrongArgumentNumber() {
		try {
			getExpressionWithFunctionContext(" compare(\"abc\")");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Compare empty.
     */
    @Test
	public void compareEmpty() {
		try {
			getExpressionWithFunctionContext(" compare()");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Contains true.
     */
// contains
	@Test
	public void containsTrue() {
		try {
			Expression expression = getExpressionWithFunctionContext("contains(\"abcd\", \"abc\")");
			assertEquals(ExpressionType.BOOLEAN, expression.getExpressionType());
			assertTrue(expression.evaluateBoolean());
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Contains false.
     */
    @Test
	public void containsFalse() {
		try {
			Expression expression = getExpressionWithFunctionContext("contains(\"aaa\", \"bbb\")");
			assertEquals(ExpressionType.BOOLEAN, expression.getExpressionType());
			assertTrue(!expression.evaluateBoolean());
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Contains missing 1.
     */
    @Test
	public void containsMissing1() {
		try {
			Expression expression = getExpressionWithFunctionContext("contains( MISSING_NOMINAL, \"abc\")");
			assertEquals(ExpressionType.BOOLEAN, expression.getExpressionType());
			assertEquals(null, expression.evaluateBoolean());
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Contains missing 2.
     */
    @Test
	public void containsMissing2() {
		try {
			Expression expression = getExpressionWithFunctionContext("contains(\"abc\", MISSING_NOMINAL)");
			assertEquals(ExpressionType.BOOLEAN, expression.getExpressionType());
			assertEquals(null, expression.evaluateBoolean());
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Contains empty.
     */
    @Test
	public void containsEmpty() {
		try {
			getExpressionWithFunctionContext(" contains()");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Contains wrong number of arguments.
     */
    @Test
	public void containsWrongNumberOfArguments() {
		try {
			getExpressionWithFunctionContext(" contains(\"aaa\")");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Contains wrong first type.
     */
    @Test
	public void containsWrongFirstType() {
		try {
			getExpressionWithFunctionContext(" contains(5, \"abc\")");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Contains wrong second type.
     */
    @Test
	public void containsWrongSecondType() {
		try {
			getExpressionWithFunctionContext(" contains(\"abc\",5)");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

	// Matches

    /**
     * Matches true.
     */
    @Test
	public void matchesTrue() {
		try {
			Expression expression = getExpressionWithFunctionContext("matches(\"abcd\", \"a[bxyz]c[abcdxyz]\")");
			assertEquals(ExpressionType.BOOLEAN, expression.getExpressionType());
			assertTrue(expression.evaluateBoolean());
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Matches false.
     */
    @Test
	public void matchesFalse() {
		try {
			Expression expression = getExpressionWithFunctionContext("matches(\"abcd\", \"a[xyz]c[abcxyz]\")");
			assertEquals(ExpressionType.BOOLEAN, expression.getExpressionType());
			assertTrue(!expression.evaluateBoolean());
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Matches illegal expression.
     */
    @Test
	public void matchesIllegalExpression() {
		try {
			Expression expression = getExpressionWithFunctionContext("matches(\"abcd\", \"a[xyz]c[abcxyz]{1,3\")");
			assertEquals(ExpressionType.BOOLEAN, expression.getExpressionType());
			expression.evaluateBoolean();
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Matches missing 1.
     */
    @Test
	public void matchesMissing1() {
		try {
			Expression expression = getExpressionWithFunctionContext("matches( MISSING_NOMINAL, \"abc\")");
			assertEquals(ExpressionType.BOOLEAN, expression.getExpressionType());
			assertEquals(null, expression.evaluateBoolean());
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Matches missing 2.
     */
    @Test
	public void matchesMissing2() {
		try {
			Expression expression = getExpressionWithFunctionContext("matches(\"abc\", MISSING_NOMINAL)");
			assertEquals(ExpressionType.BOOLEAN, expression.getExpressionType());
			assertEquals(null, expression.evaluateBoolean());
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Matches empty.
     */
    @Test
	public void matchesEmpty() {
		try {
			getExpressionWithFunctionContext(" matches()");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Matches wrong number of arguments.
     */
    @Test
	public void matchesWrongNumberOfArguments() {
		try {
			getExpressionWithFunctionContext("matches(\"aaa\")");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Matches wrong first type.
     */
    @Test
	public void matchesWrongFirstType() {
		try {
			getExpressionWithFunctionContext("matches(5, \"abc\")");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Matches wrong second type.
     */
    @Test
	public void matchesWrongSecondType() {
		try {
			getExpressionWithFunctionContext("matches(\"abc\",5)");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

	// equals

    /**
     * Equals true.
     */
    @Test
	public void equalsTrue() {
		try {
			Expression expression = getExpressionWithFunctionContext("equals(\"abcd\", \"abcd\")");
			assertEquals(ExpressionType.BOOLEAN, expression.getExpressionType());
			assertTrue(expression.evaluateBoolean());
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Equals false.
     */
    @Test
	public void equalsFalse() {
		try {
			Expression expression = getExpressionWithFunctionContext("equals(\"abcd\", \"Zer0 als Nummer\")");
			assertEquals(ExpressionType.BOOLEAN, expression.getExpressionType());
			assertTrue(!expression.evaluateBoolean());
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Equals missing 1.
     */
    @Test
	public void equalsMissing1() {
		try {
			Expression expression = getExpressionWithFunctionContext("equals( MISSING_NOMINAL, \"abc\")");
			assertEquals(ExpressionType.BOOLEAN, expression.getExpressionType());
			assertEquals(null, expression.evaluateBoolean());
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Equals missing 2.
     */
    @Test
	public void equalsMissing2() {
		try {
			Expression expression = getExpressionWithFunctionContext("equals(\"abc\", MISSING_NOMINAL)");
			assertEquals(ExpressionType.BOOLEAN, expression.getExpressionType());
			assertEquals(null, expression.evaluateBoolean());
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Equals empty.
     */
    @Test
	public void equalsEmpty() {
		try {
			getExpressionWithFunctionContext("equals()");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Equals wrong number of arguments.
     */
    @Test
	public void equalsWrongNumberOfArguments() {
		try {
			getExpressionWithFunctionContext("equals(\"aaa\")");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Equals wrong first type.
     */
    @Test
	public void equalsWrongFirstType() {
		try {
			getExpressionWithFunctionContext("equals(5, \"abc\")");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Equals wrong second type.
     */
    @Test
	public void equalsWrongSecondType() {
		try {
			getExpressionWithFunctionContext("equals(\"abc\",5)");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

	// starts

    /**
     * Starts true.
     */
    @Test
	public void startsTrue() {
		try {
			Expression expression = getExpressionWithFunctionContext("starts(\"abcd\", \"ab\")");
			assertEquals(ExpressionType.BOOLEAN, expression.getExpressionType());
			assertTrue(expression.evaluateBoolean());
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Starts false.
     */
    @Test
	public void startsFalse() {
		try {
			Expression expression = getExpressionWithFunctionContext("starts(\"abcd\", \"bi\")");
			assertEquals(ExpressionType.BOOLEAN, expression.getExpressionType());
			assertTrue(!expression.evaluateBoolean());
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Starts missing 1.
     */
    @Test
	public void startsMissing1() {
		try {
			Expression expression = getExpressionWithFunctionContext("starts( MISSING_NOMINAL, \"abc\")");
			assertEquals(ExpressionType.BOOLEAN, expression.getExpressionType());
			assertEquals(null, expression.evaluateBoolean());
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Starts missing 2.
     */
    @Test
	public void startsMissing2() {
		try {
			Expression expression = getExpressionWithFunctionContext("starts(\"abc\", MISSING_NOMINAL)");
			assertEquals(ExpressionType.BOOLEAN, expression.getExpressionType());
			assertEquals(null, expression.evaluateBoolean());
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Stats empty.
     */
    @Test
	public void statsEmpty() {
		try {
			getExpressionWithFunctionContext("starts()");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Starts wrong number of arguments.
     */
    @Test
	public void startsWrongNumberOfArguments() {
		try {
			getExpressionWithFunctionContext("starts(\"aaa\")");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Starts wrong first type.
     */
    @Test
	public void startsWrongFirstType() {
		try {
			getExpressionWithFunctionContext("starts(5, \"abc\")");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Starts wrong second type.
     */
    @Test
	public void startsWrongSecondType() {
		try {
			getExpressionWithFunctionContext("starts(\"abc\",5)");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

	// ends

    /**
     * Ends true.
     */
    @Test
	public void endsTrue() {
		try {
			Expression expression = getExpressionWithFunctionContext("ends(\"abcd\", \"cd\")");
			assertEquals(ExpressionType.BOOLEAN, expression.getExpressionType());
			assertTrue(expression.evaluateBoolean());
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Ends false.
     */
    @Test
	public void endsFalse() {
		try {
			Expression expression = getExpressionWithFunctionContext("ends(\"abcd\", \"bi\")");
			assertEquals(ExpressionType.BOOLEAN, expression.getExpressionType());
			assertTrue(!expression.evaluateBoolean());
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Ends missing 1.
     */
    @Test
	public void endsMissing1() {
		try {
			Expression expression = getExpressionWithFunctionContext("ends( MISSING_NOMINAL, \"abc\")");
			assertEquals(ExpressionType.BOOLEAN, expression.getExpressionType());
			assertEquals(null, expression.evaluateBoolean());
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Ends missing 2.
     */
    @Test
	public void endsMissing2() {
		try {
			Expression expression = getExpressionWithFunctionContext("ends(\"abc\", MISSING_NOMINAL)");
			assertEquals(ExpressionType.BOOLEAN, expression.getExpressionType());
			assertEquals(null, expression.evaluateBoolean());
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Ends empty.
     */
    @Test
	public void endsEmpty() {
		try {
			getExpressionWithFunctionContext("ends()");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Ends wrong number of arguments.
     */
    @Test
	public void endsWrongNumberOfArguments() {
		try {
			getExpressionWithFunctionContext("ends(\"aaa\")");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Ends wrong first type.
     */
    @Test
	public void endsWrongFirstType() {
		try {
			getExpressionWithFunctionContext("ends(5, \"abc\")");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Ends wrong second type.
     */
    @Test
	public void endsWrongSecondType() {
		try {
			getExpressionWithFunctionContext("ends(\"abc\",5)");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

	// finds

    /**
     * Finds true.
     */
    @Test
	public void findsTrue() {
		try {
			Expression expression = getExpressionWithFunctionContext("finds(\"abcd\", \"cd\")");
			assertEquals(ExpressionType.BOOLEAN, expression.getExpressionType());
			assertTrue(expression.evaluateBoolean());
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Finds false.
     */
    @Test
	public void findsFalse() {
		try {
			Expression expression = getExpressionWithFunctionContext("finds(\"abcd\", \"bi\")");
			assertEquals(ExpressionType.BOOLEAN, expression.getExpressionType());
			assertTrue(!expression.evaluateBoolean());
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Finds empty in string.
     */
    @Test
	public void findsEmptyInString() {
		try {
			Expression expression = getExpressionWithFunctionContext("finds(\"abcd\", \"\")");
			assertEquals(ExpressionType.BOOLEAN, expression.getExpressionType());
			assertTrue(expression.evaluateBoolean());
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Finds string in empty.
     */
    @Test
	public void findsStringInEmpty() {
		try {
			Expression expression = getExpressionWithFunctionContext("finds(\"\", \"bi\")");
			assertEquals(ExpressionType.BOOLEAN, expression.getExpressionType());
			assertTrue(!expression.evaluateBoolean());
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Finds illegal expression.
     */
    @Test
	public void findsIllegalExpression() {
		try {
			Expression expression = getExpressionWithFunctionContext("finds(\"abcd\", \"[xyz]c[abcxyz]{1,3\")");
			assertEquals(ExpressionType.BOOLEAN, expression.getExpressionType());
			expression.evaluateBoolean();
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Finds missing 1.
     */
    @Test
	public void findsMissing1() {
		try {
			Expression expression = getExpressionWithFunctionContext("finds( MISSING_NOMINAL, \"abc\")");
			assertEquals(ExpressionType.BOOLEAN, expression.getExpressionType());
			assertEquals(null, expression.evaluateBoolean());
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Finds missing 2.
     */
    @Test
	public void findsMissing2() {
		try {
			Expression expression = getExpressionWithFunctionContext("finds(\"abc\", MISSING_NOMINAL)");
			assertEquals(ExpressionType.BOOLEAN, expression.getExpressionType());
			assertEquals(null, expression.evaluateBoolean());
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Finds empty.
     */
    @Test
	public void findsEmpty() {
		try {
			getExpressionWithFunctionContext("finds()");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Finds wrong number of arguments.
     */
    @Test
	public void findsWrongNumberOfArguments() {
		try {
			getExpressionWithFunctionContext("finds(\"aaa\")");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Finds wrong first type.
     */
    @Test
	public void findsWrongFirstType() {
		try {
			getExpressionWithFunctionContext("finds(5, \"abc\")");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Finds wrong second type.
     */
    @Test
	public void findsWrongSecondType() {
		try {
			getExpressionWithFunctionContext("finds(\"abc\",5)");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

	// index

    /**
     * Index matching string.
     */
    @Test
	public void indexMatchingString() {
		try {
			Expression expression = getExpressionWithFunctionContext("index(\"abcd\",\"c\")");
			assertEquals(ExpressionType.INTEGER, expression.getExpressionType());
			assertEquals(2, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Index not matching string.
     */
    @Test
	public void indexNotMatchingString() {
		try {
			Expression expression = getExpressionWithFunctionContext("index(\"abcd\",\"t\")");
			assertEquals(ExpressionType.INTEGER, expression.getExpressionType());
			assertEquals(-1, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Index empty substring.
     */
    @Test
	public void indexEmptySubstring() {
		try {
			Expression expression = getExpressionWithFunctionContext("index(\"abcd\",\"\")");
			assertEquals(ExpressionType.INTEGER, expression.getExpressionType());
			assertEquals(0, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Index empty main string.
     */
    @Test
	public void indexEmptyMainString() {
		try {
			Expression expression = getExpressionWithFunctionContext("index(\"\",\"c\")");
			assertEquals(ExpressionType.INTEGER, expression.getExpressionType());
			assertEquals(-1, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Index missing 1.
     */
    @Test
	public void indexMissing1() {
		try {
			Expression expression = getExpressionWithFunctionContext("index( MISSING_NOMINAL, \"abc\")");
			assertEquals(ExpressionType.INTEGER, expression.getExpressionType());
			assertEquals(Double.NaN, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Index missing 2.
     */
    @Test
	public void indexMissing2() {
		try {
			Expression expression = getExpressionWithFunctionContext("index(\"abc\", MISSING_NOMINAL)");
			assertEquals(ExpressionType.INTEGER, expression.getExpressionType());
			assertEquals(Double.NaN, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Index empty.
     */
    @Test
	public void indexEmpty() {
		try {
			getExpressionWithFunctionContext("index()");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Index wrong number of arguments.
     */
    @Test
	public void indexWrongNumberOfArguments() {
		try {
			getExpressionWithFunctionContext("index(\"aaa\")");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Index wrong first type.
     */
    @Test
	public void indexWrongFirstType() {
		try {
			getExpressionWithFunctionContext("index(5, \"abc\")");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Index wrong second type.
     */
    @Test
	public void indexWrongSecondType() {
		try {
			getExpressionWithFunctionContext("index(\"abc\",5)");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

	// length

    /**
     * Length simple use.
     */
    @Test
	public void lengthSimpleUse() {
		try {
			Expression expression = getExpressionWithFunctionContext("length(\"abcd\")");
			assertEquals(ExpressionType.INTEGER, expression.getExpressionType());
			assertEquals(4, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Length empty string.
     */
    @Test
	public void lengthEmptyString() {
		try {
			Expression expression = getExpressionWithFunctionContext("length(\"\")");
			assertEquals(ExpressionType.INTEGER, expression.getExpressionType());
			assertEquals(0, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Length missing.
     */
    @Test
	public void lengthMissing() {
		try {
			Expression expression = getExpressionWithFunctionContext("length(MISSING_NOMINAL)");
			assertEquals(ExpressionType.INTEGER, expression.getExpressionType());
			assertEquals(Double.NaN, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Lengths empty.
     */
    @Test
	public void lengthsEmpty() {
		try {
			getExpressionWithFunctionContext("length()");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Length wrong number of arguments.
     */
    @Test
	public void lengthWrongNumberOfArguments() {
		try {
			getExpressionWithFunctionContext("length(\"aaa\",\"aaa\")");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Length wrong type.
     */
    @Test
	public void lengthWrongType() {
		try {
			getExpressionWithFunctionContext("length(5)");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

}
