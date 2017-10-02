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
package com.rapidminer.tools.expression.internal.antlr;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.rapidminer.tools.expression.ExpressionException;


/**
 * Tests the {@link FunctionExpressionLexer} together with the {@link FunctionExpressionParser} on
 * various inputs using the {@link AntlrParser#parseExpression} method.
 *
 * @author Gisa Schaefer
 */
public class ExpressionParserTest {

	/**
	 * Tries to parse the input, returns {@code true} if successful.
	 *
	 * @param input
	 * @return
	 */
	private boolean parse(String input) {
		AntlrParser parser = new AntlrParser(null);
		try {
			parser.parseExpression(input);
			return true;
		} catch (ExpressionException e) {
			// //comment in to check the error messages
			// System.err.println(e.getMessage());
			return false;
		}

	}

    /**
     * Empty input.
     */
    @Test
	public void emptyInput() {
		boolean result = parse("");
		assertFalse(result);
	}

    /**
     * Integer input.
     */
    @Test
	public void integerInput() {
		boolean result = parse("2378423");
		assertTrue(result);
	}

    /**
     * Integer wrong input.
     */
    @Test
	public void integerWrongInput() {
		boolean result = parse("2378423)");
		assertFalse(result);
	}

    /**
     * Integer wrong bracket.
     */
    @Test
	public void integerWrongBracket() {
		boolean result = parse("(2378423))");
		assertFalse(result);
	}

    /**
     * Exponent wrong.
     */
    @Test
	public void exponentWrong() {
		boolean result = parse("2378423e-");
		assertFalse(result);
	}

    /**
     * Exponent wrong newline.
     */
    @Test
	public void exponentWrongNewline() {
		boolean result = parse("2378423e-\n one + two");
		assertFalse(result);
	}

    /**
     * Exponent right.
     */
    @Test
	public void exponentRight() {
		boolean result = parse("2378423e-10");
		assertTrue(result);
	}

    /**
     * Real.
     */
    @Test
	public void real() {
		boolean result = parse("123.141529");
		assertTrue(result);
	}

    /**
     * Real without non fractional digits.
     */
    @Test
	public void realWithoutNonFractionalDigits() {
		boolean result = parse(".141529");
		assertTrue(result);
	}

    /**
     * Real without non fractional digits and exponent.
     */
    @Test
	public void realWithoutNonFractionalDigitsAndExponent() {
		boolean result = parse(".141529e12");
		assertTrue(result);
	}

    /**
     * Real with negative exponent.
     */
    @Test
	public void realWithNegativeExponent() {
		boolean result = parse("3.141529E-12");
		assertTrue(result);
	}

    /**
     * Real with positive exponent.
     */
    @Test
	public void realWithPositiveExponent() {
		boolean result = parse("3.141529E+12");
		assertTrue(result);
	}

    /**
     * Integer wrong 2 input.
     */
    @Test
	public void integerWrong2Input() {
		boolean result = parse("2x");
		assertFalse(result);
	}

    /**
     * Simple addition.
     */
    @Test
	public void simpleAddition() {
		boolean result = parse("2+ 3+11");
		assertTrue(result);
	}

    /**
     * Simple substraction.
     */
    @Test
	public void simpleSubstraction() {
		boolean result = parse("11 -3-4- 1");
		assertTrue(result);
	}

    /**
     * Addition substraction.
     */
    @Test
	public void additionSubstraction() {
		boolean result = parse("11 +3-4+ 1");
		assertTrue(result);
	}

    /**
     * Multiplication division modulo.
     */
    @Test
	public void multiplicationDivisionModulo() {
		boolean result = parse("3*4/5*5%2 *223424");
		assertTrue(result);
	}

    /**
     * Addition multiplication mixed.
     */
    @Test
	public void additionMultiplicationMixed() {
		boolean result = parse("3 +4/5*5%2-1 -223424+ 11*2");
		assertTrue(result);
	}

    /**
     * Right associativity of power.
     */
    @Test
	public void rightAssociativityOfPower() {
		boolean result = parse("2^ 2^3");
		assertTrue(result);
	}

    /**
     * Unary plus minus.
     */
    @Test
	public void unaryPlusMinus() {
		boolean result = parse("3*-5+ -+ -+--11");
		assertTrue(result);
	}

    /**
     * Constant test.
     */
    @Test
	public void constantTest() {
		boolean result = parse("att2Blup");
		assertTrue(result);
	}

    /**
     * Big constant test.
     */
    @Test
	public void bigConstantTest() {
		boolean result = parse("DATE_UNIT_DATE");
		assertTrue(result);
	}

    /**
     * Not constant test.
     */
    @Test
	public void notConstantTest() {
		boolean result = parse("3DATE_UNIT_DATE");
		assertFalse(result);
	}

    /**
     * Not constant 2 test.
     */
    @Test
	public void notConstant2Test() {
		boolean result = parse("$DATE_UNIT_DATE");
		assertFalse(result);
	}

    /**
     * No parameter function.
     */
    @Test
	public void noParameterFunction() {
		boolean result = parse("date_now()");
		assertTrue(result);
	}

    /**
     * One parameter function.
     */
    @Test
	public void oneParameterFunction() {
		boolean result = parse("cos(1 )");
		assertTrue(result);
	}

    /**
     * Nested functions.
     */
    @Test
	public void nestedFunctions() {
		boolean result = parse("sin(cos(1 ) )");
		assertTrue(result);
	}

    /**
     * More parameter function.
     */
    @Test
	public void moreParameterFunction() {
		boolean result = parse("date_str_loc(att1,DATE_MEDIUM, DATE_SHOW_TIME_ONLY, \"us\")");
		assertTrue(result);
	}

    /**
     * Function mixed with calculation.
     */
    @Test
	public void functionMixedWithCalculation() {
		boolean result = parse("-dings(1,2,att45)*2+7*3^5");
		assertTrue(result);
	}

    /**
     * Parentheses.
     */
    @Test
	public void parentheses() {
		boolean result = parse("3*(2+5)");
		assertTrue(result);
	}

    /**
     * Superfluous parentheses.
     */
    @Test
	public void superfluousParentheses() {
		boolean result = parse("(3*(2+(5)))");
		assertTrue(result);
	}

    /**
     * Wrong parentheses.
     */
    @Test
	public void wrongParentheses() {
		boolean result = parse("((3*(2+5))");
		assertFalse(result);
	}

    /**
     * Attribute with spaces.
     */
    @Test
	public void attributeWithSpaces() {
		boolean result = parse("[my attribute! \" 1+2 \u0714 \u06dd \u199c $%-_]");
		assertTrue(result);
	}

    /**
     * Attribute with newline.
     */
    @Test
	public void attributeWithNewline() {
		boolean result = parse("[my attribute! \n 1+2]");
		assertFalse(result);
	}

    /**
     * Attribute missing bracket.
     */
    @Test
	public void attributeMissingBracket() {
		boolean result = parse("[my attribute! 1+2");
		assertFalse(result);
	}

    /**
     * Attribute missing opening bracket.
     */
    @Test
	public void attributeMissingOpeningBracket() {
		boolean result = parse("my attribute! 1+2]");
		assertFalse(result);
	}

    /**
     * String with newline.
     */
    @Test
	public void stringWithNewline() {
		boolean result = parse("\"bla blup \n !\"");
		assertTrue(result);
	}

    /**
     * String with missing closing.
     */
    @Test
	public void stringWithMissingClosing() {
		boolean result = parse("\"bla blup ?");
		assertFalse(result);
	}

    /**
     * String with quotes.
     */
    @Test
	public void stringWithQuotes() {
		boolean result = parse("\"bla blup \" !\"");
		assertFalse(result);
	}

    /**
     * String with escaped quotes.
     */
    @Test
	public void stringWithEscapedQuotes() {
		boolean result = parse("\"bla blup \\\" !\"");
		assertTrue(result);
	}

    /**
     * Empty string.
     */
    @Test
	public void emptyString() {
		boolean result = parse("\"\"");
		assertTrue(result);
	}

    /**
     * String with backslash.
     */
    @Test
	public void StringWithBackslash() {
		boolean result = parse("\"bla\\a \"");
		assertFalse(result);
	}

    /**
     * String with escaped backslash.
     */
    @Test
	public void StringWithEscapedBackslash() {
		boolean result = parse("\"bla\\\\a \"");
		assertTrue(result);
	}

    /**
     * String with unicode.
     */
    @Test
	public void StringWithUnicode() {
		boolean result = parse("\"bla\\u234f blup\"");
		assertTrue(result);
	}

    /**
     * Macro with spaces.
     */
    @Test
	public void macroWithSpaces() {
		boolean result = parse("%{my macro[\"3\"]\u0714}");
		assertTrue(result);
	}

    /**
     * Macro with tab.
     */
    @Test
	public void macroWithTab() {
		boolean result = parse("%{my macro\t blup}");
		assertFalse(result);
	}

    /**
     * Macro without closing.
     */
    @Test
	public void macroWithoutClosing() {
		boolean result = parse("%{my macro blup");
		assertFalse(result);
	}

    /**
     * Indirect macro with spaces.
     */
    @Test
	public void indirectMacroWithSpaces() {
		boolean result = parse("#{my macro[\"3\"]\u0714}");
		assertTrue(result);
	}

    /**
     * Indirect macro with tab.
     */
    @Test
	public void indirectMacroWithTab() {
		boolean result = parse("#{my macro\t blup}");
		assertFalse(result);
	}

    /**
     * Indirect macro without closing.
     */
    @Test
	public void indirectMacroWithoutClosing() {
		boolean result = parse("#{my macro blup");
		assertFalse(result);
	}

    /**
     * Comparision.
     */
    @Test
	public void comparision() {
		boolean result = parse("3+4 > [my attribute] <= TRUE");
		assertTrue(result);
	}

    /**
     * Comparision and equality.
     */
    @Test
	public void comparisionAndEquality() {
		boolean result = parse("TRUE == 3+4 > [my attribute]");
		assertTrue(result);
	}

    /**
     * Comparision and equality and and.
     */
    @Test
	public void comparisionAndEqualityAndAND() {
		boolean result = parse("TRUE == 3+4 > [my attribute] && 7< #{my macro}");
		assertTrue(result);
	}

    /**
     * Comparision and equality and or.
     */
    @Test
	public void comparisionAndEqualityAndOR() {
		boolean result = parse("TRUE == 3+4 > [my attribute] || 7< #{my macro}");
		assertTrue(result);
	}

    /**
     * And and or.
     */
    @Test
	public void andAndOr() {
		boolean result = parse("TRUE || 3>4 && [my attribute]");
		assertTrue(result);
	}

    /**
     * And and or and not.
     */
    @Test
	public void andAndOrAndNOT() {
		boolean result = parse("! TRUE || 3>4 && ![my attribute]");
		assertTrue(result);
	}

    /**
     * Attribute with wrong bracket.
     */
    @Test
	public void attributeWithWrongBracket() {
		boolean result = parse("[my attribut]e]");
		assertFalse(result);
	}

    /**
     * Attribute with escaped bracket.
     */
    @Test
	public void attributeWithEscapedBracket() {
		boolean result = parse("[my attribut\\]e]");
		assertTrue(result);
	}

    /**
     * Attribute with wrong open bracket.
     */
    @Test
	public void attributeWithWrongOpenBracket() {
		boolean result = parse("[my attribut[e]");
		assertFalse(result);
	}

    /**
     * Attribute with escaped open bracket.
     */
    @Test
	public void attributeWithEscapedOpenBracket() {
		boolean result = parse("[my attribut\\[e]");
		assertTrue(result);
	}

    /**
     * Two attributes.
     */
    @Test
	public void twoAttributes() {
		boolean result = parse("[my attribut]+[e]");
		assertTrue(result);
	}

    /**
     * Attribute with single backslash.
     */
    @Test
	public void attributeWithSingleBackslash() {
		boolean result = parse("[my attribut\\e]");
		assertFalse(result);
	}

    /**
     * Attribute with single backslash end.
     */
    @Test
	public void attributeWithSingleBackslashEnd() {
		boolean result = parse("[my attribut\\]");
		assertFalse(result);
	}

    /**
     * Attribute with double backslash.
     */
    @Test
	public void attributeWithDoubleBackslash() {
		boolean result = parse("[my attribut\\\\]");
		assertTrue(result);
	}

    /**
     * Macro with wrong bracket.
     */
    @Test
	public void macroWithWrongBracket() {
		boolean result = parse("%{my mac}ro}");
		assertFalse(result);
	}

    /**
     * Macro with escaped bracket.
     */
    @Test
	public void macroWithEscapedBracket() {
		boolean result = parse("%{my mac\\}ro}");
		assertTrue(result);
	}

    /**
     * Macro with wrong open bracket.
     */
    @Test
	public void macroWithWrongOpenBracket() {
		boolean result = parse("%{my mac{ro}");
		assertFalse(result);
	}

    /**
     * Macro with escaped open bracket.
     */
    @Test
	public void macroWithEscapedOpenBracket() {
		boolean result = parse("%{my mac\\{ro}");
		assertTrue(result);
	}

    /**
     * Macro with single backslash.
     */
    @Test
	public void macroWithSingleBackslash() {
		boolean result = parse("%{my mac\\ro}");
		assertFalse(result);
	}

    /**
     * Macro with double backslash.
     */
    @Test
	public void macroWithDoubleBackslash() {
		boolean result = parse("%{my mac\\\\ro}");
		assertTrue(result);
	}

}
