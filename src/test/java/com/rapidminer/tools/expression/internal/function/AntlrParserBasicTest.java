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
import static org.junit.Assert.fail;

import org.junit.Test;

import com.rapidminer.example.ExampleSet;
import com.rapidminer.tools.expression.ExampleResolver;
import com.rapidminer.tools.expression.Expression;
import com.rapidminer.tools.expression.ExpressionException;
import com.rapidminer.tools.expression.ExpressionType;
import com.rapidminer.tools.expression.internal.antlr.AntlrParser;


/**
 * Tests the results of {@link AntlrParser#parse(String)} from the basic functions block.
 *
 * @author Gisa Schaefer
 */
public class AntlrParserBasicTest extends AntlrParserTest {

    /**
     * Integer input.
     */
    @Test
	public void integerInput() {
		try {
			Expression expression = getExpressionWithoutContext("23643");
			assertEquals(ExpressionType.INTEGER, expression.getExpressionType());
			assertEquals(23643d, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Double input.
     */
    @Test
	public void doubleInput() {
		try {
			Expression expression = getExpressionWithoutContext("236.43");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(236.43, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Double scientific.
     */
    @Test
	public void doubleScientific() {
		try {
			Expression expression = getExpressionWithoutContext("2378423e-10");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(2378423e-10, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Double scientific positive.
     */
    @Test
	public void doubleScientificPositive() {
		try {
			Expression expression = getExpressionWithoutContext(".141529e12");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(.141529e12, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Double scientific plus.
     */
    @Test
	public void doubleScientificPlus() {
		try {
			Expression expression = getExpressionWithoutContext("3.141529E+12");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(3.141529e12, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * String input.
     */
    @Test
	public void stringInput() {
		try {
			Expression expression = getExpressionWithoutContext("\"bla blup\"");
			assertEquals(ExpressionType.STRING, expression.getExpressionType());
			assertEquals("bla blup", expression.evaluateNominal());
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * String with escaped.
     */
    @Test
	public void stringWithEscaped() {
		try {
			Expression expression = getExpressionWithoutContext("\"bla\\\"\\\\3\\\" blup\"");
			assertEquals(ExpressionType.STRING, expression.getExpressionType());
			assertEquals("bla\"\\3\" blup", expression.evaluateNominal());
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * String with unicode.
     */
    @Test
	public void stringWithUnicode() {
		try {
			Expression expression = getExpressionWithoutContext("\"\\u5f3e bla\\u234f blup\\u3333\"");
			assertEquals(ExpressionType.STRING, expression.getExpressionType());
			assertEquals("\u5f3e bla\u234f blup\u3333", expression.evaluateNominal());
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * String with tabs and newlines.
     */
    @Test
	public void stringWithTabsAndNewlines() {
		try {
			Expression expression = getExpressionWithoutContext("\"\\u5f3e bla\nhello\tworld\r\nblup!\"");
			assertEquals(ExpressionType.STRING, expression.getExpressionType());
			assertEquals("\u5f3e bla hello world blup!", expression.evaluateNominal());
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Multiply ints.
     */
    @Test
	public void multiplyInts() {
		try {
			Expression expression = getExpressionWithFunctionContext("3*5");
			assertEquals(ExpressionType.INTEGER, expression.getExpressionType());
			assertEquals(3 * 5, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Multiply doubles.
     */
    @Test
	public void multiplyDoubles() {
		try {
			Expression expression = getExpressionWithFunctionContext("3.0*5");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(3.0 * 5, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Divide ints.
     */
    @Test
	public void divideInts() {
		try {
			Expression expression = getExpressionWithFunctionContext("4 /2");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(4.0 / 2, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Divide doubles.
     */
    @Test
	public void divideDoubles() {
		try {
			Expression expression = getExpressionWithFunctionContext("5.0 /2");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(5.0 / 2, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Divide by zero.
     */
    @Test
	public void divideByZero() {
		try {
			Expression expression = getExpressionWithFunctionContext("5.0 /0");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(Double.POSITIVE_INFINITY, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Modulo ints.
     */
    @Test
	public void moduloInts() {
		try {
			Expression expression = getExpressionWithFunctionContext("5 %2");
			assertEquals(ExpressionType.INTEGER, expression.getExpressionType());
			assertEquals(5 % 2, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Modulo double.
     */
    @Test
	public void moduloDouble() {
		try {
			Expression expression = getExpressionWithFunctionContext("4.7 %1.5");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(0.2, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Different point operations.
     */
    @Test
	public void differentPointOperations() {
		try {
			Expression expression = getExpressionWithFunctionContext("4%3 *5/2");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(4 % 3 * 5 / 2.0, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Power ints.
     */
    @Test
	public void powerInts() {
		try {
			Expression expression = getExpressionWithFunctionContext("2^3^2");
			assertEquals(ExpressionType.INTEGER, expression.getExpressionType());
			assertEquals(Math.pow(2, Math.pow(3, 2)), expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Power doubles.
     */
    @Test
	public void powerDoubles() {
		try {
			Expression expression = getExpressionWithFunctionContext("2^3.0^2");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(Math.pow(2, Math.pow(3, 2)), expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * String multiplication.
     */
    @Test
	public void stringMultiplication() {
		try {
			getExpressionWithFunctionContext("3* \"blup\"");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * String division.
     */
    @Test
	public void stringDivision() {
		try {
			getExpressionWithFunctionContext("\"blup\" /4");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Unknown function.
     */
    @Test
	public void unknownFunction() {
		try {
			getExpressionWithFunctionContext("unknown(3)");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Power as function doubles.
     */
    @Test
	public void powerAsFunctionDoubles() {
		try {
			Expression expression = getExpressionWithFunctionContext("pow(2,0.5)");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(Math.pow(2, 0.5), expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Power as function ints.
     */
    @Test
	public void powerAsFunctionInts() {
		try {
			Expression expression = getExpressionWithFunctionContext("pow (2,3)");
			assertEquals(ExpressionType.INTEGER, expression.getExpressionType());
			assertEquals(Math.pow(2, 3), expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Power as function wrong number of arguments.
     */
    @Test
	public void powerAsFunctionWrongNumberOfArguments() {
		try {
			getExpressionWithFunctionContext("pow(2)");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Modulo as function doubles.
     */
    @Test
	public void moduloAsFunctionDoubles() {
		try {
			Expression expression = getExpressionWithFunctionContext("mod(2 ,1.5 )");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(2 % 1.5, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Minus one double.
     */
    @Test
	public void minusOneDouble() {
		try {
			Expression expression = getExpressionWithFunctionContext("- 1.5");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(-1.5, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Minus doubles.
     */
    @Test
	public void minusDoubles() {
		try {
			Expression expression = getExpressionWithFunctionContext("2- 1.5");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(2 - 1.5, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Minus one int.
     */
    @Test
	public void minusOneInt() {
		try {
			Expression expression = getExpressionWithFunctionContext("- -11");
			assertEquals(ExpressionType.INTEGER, expression.getExpressionType());
			assertEquals(11, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Minus ints.
     */
    @Test
	public void minusInts() {
		try {
			Expression expression = getExpressionWithFunctionContext("-3-12 -11");
			assertEquals(ExpressionType.INTEGER, expression.getExpressionType());
			assertEquals(-3 - 12 - 11, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Minus wrong.
     */
    @Test
	public void minusWrong() {
		try {
			getExpressionWithFunctionContext("-3-\"blup\"");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Minus wrong left.
     */
    @Test
	public void minusWrongLeft() {
		try {
			getExpressionWithFunctionContext("\"blup\"-5.678");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Minus wrong one.
     */
    @Test
	public void minusWrongOne() {
		try {
			getExpressionWithFunctionContext("-\"blup\"");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Plus one int.
     */
    @Test
	public void plusOneInt() {
		try {
			Expression expression = getExpressionWithFunctionContext("++11");
			assertEquals(ExpressionType.INTEGER, expression.getExpressionType());
			assertEquals(11, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Plus one double.
     */
    @Test
	public void plusOneDouble() {
		try {
			Expression expression = getExpressionWithFunctionContext("+11.06476");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(11.06476, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Plus one string.
     */
    @Test
	public void plusOneString() {
		try {
			getExpressionWithFunctionContext("+\"blup\"");
			fail();
		} catch (ExpressionException e) {
			assertNotNull(e.getMessage());
		}
	}

    /**
     * Plus ints.
     */
    @Test
	public void plusInts() {
		try {
			Expression expression = getExpressionWithFunctionContext("+12+11");
			assertEquals(ExpressionType.INTEGER, expression.getExpressionType());
			assertEquals(12 + 11, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Plus doubles.
     */
    @Test
	public void plusDoubles() {
		try {
			Expression expression = getExpressionWithFunctionContext(".123123+11.06476");
			assertEquals(ExpressionType.DOUBLE, expression.getExpressionType());
			assertEquals(0.123123 + 11.06476, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Plus strings.
     */
    @Test
	public void plusStrings() {
		try {
			Expression expression = getExpressionWithFunctionContext("\"hello \"+\"world\"");
			assertEquals(ExpressionType.STRING, expression.getExpressionType());
			assertEquals("hello world", expression.evaluateNominal());
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Plus string and double.
     */
    @Test
	public void plusStringAndDouble() {
		try {
			Expression expression = getExpressionWithFunctionContext("\"hello \"+3.5");
			assertEquals(ExpressionType.STRING, expression.getExpressionType());
			assertEquals("hello 3.5", expression.evaluateNominal());
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Plus string and missing double.
     */
    @Test
	public void plusStringAndMissingDouble() {
		try {
			Expression expression = getExpressionWithFunctionContext("\"hello \"+0/0");
			assertEquals(ExpressionType.STRING, expression.getExpressionType());
			assertEquals("hello ", expression.evaluateNominal());
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Plus string and int.
     */
    @Test
	public void plusStringAndInt() {
		try {
			Expression expression = getExpressionWithFunctionContext("\"hello \"+3");
			assertEquals(ExpressionType.STRING, expression.getExpressionType());
			assertEquals("hello 3", expression.evaluateNominal());
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Plus int and string.
     */
    @Test
	public void plusIntAndString() {
		try {
			Expression expression = getExpressionWithFunctionContext("3+\"hello \"");
			assertEquals(ExpressionType.STRING, expression.getExpressionType());
			assertEquals("3hello ", expression.evaluateNominal());
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Plus double and string.
     */
    @Test
	public void plusDoubleAndString() {
		try {
			Expression expression = getExpressionWithFunctionContext("3.1415+\"hello \"");
			assertEquals(ExpressionType.STRING, expression.getExpressionType());
			assertEquals("3.1415hello ", expression.evaluateNominal());
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Plus missing double and string.
     */
    @Test
	public void plusMissingDoubleAndString() {
		try {
			Expression expression = getExpressionWithFunctionContext("0/0+\"hello \"");
			assertEquals(ExpressionType.STRING, expression.getExpressionType());
			assertEquals("hello ", expression.evaluateNominal());
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * More plus double and string.
     */
    @Test
	public void morePlusDoubleAndString() {
		try {
			Expression expression = getExpressionWithFunctionContext("3.1+3+\"hello \"");
			assertEquals(ExpressionType.STRING, expression.getExpressionType());
			assertEquals("6.1hello ", expression.evaluateNominal());
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * More plus string and int.
     */
    @Test
	public void morePlusStringAndInt() {
		try {
			Expression expression = getExpressionWithFunctionContext("\"hello \"+3+4");
			assertEquals(ExpressionType.STRING, expression.getExpressionType());
			assertEquals("hello 34", expression.evaluateNominal());
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Empty string and int.
     */
    @Test
	public void emptyStringAndInt() {
		try {
			Expression expression = getExpressionWithFunctionContext("\"\"+3");
			assertEquals(ExpressionType.STRING, expression.getExpressionType());
			assertEquals("3", expression.evaluateNominal());
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * String and infinity.
     */
    @Test
	public void stringAndInfinity() {
		try {
			Expression expression = getExpressionWithFunctionContext("\"\"+INFINITY");
			assertEquals(ExpressionType.STRING, expression.getExpressionType());
			assertEquals("\u221E", expression.evaluateNominal());
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * String and minus infinity.
     */
    @Test
	public void stringAndMinusInfinity() {
		try {
			Expression expression = getExpressionWithFunctionContext("\"\"+ -INFINITY");
			assertEquals(ExpressionType.STRING, expression.getExpressionType());
			assertEquals("-\u221E", expression.evaluateNominal());
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

    /**
     * Eval attribute with second not constant 2.
     */
    @Test
	public void evalAttributeWithSecondNotConstant2() {
		try {
			ExampleSet exampleSet = makeMissingIntegerExampleSet();
			ExampleResolver resolver = new ExampleResolver(exampleSet);
			Expression expression = getExpressionWithFunctionsAndExamples("[integer]+[integer]", resolver);
			resolver.bind(exampleSet.getExample(0));

			assertEquals(ExpressionType.INTEGER, expression.getExpressionType());
			assertEquals(Double.NaN, expression.evaluateNumerical(), 1e-15);
		} catch (ExpressionException e) {
			fail(e.getMessage());
		}
	}

}
