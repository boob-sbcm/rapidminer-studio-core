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
package com.rapidminer.parameter;

import com.rapidminer.tools.XMLException;

import org.w3c.dom.Element;


/**
 * This parameter type allows to select either Operator Values or Parameters for logging purposes.
 *
 * @author Ingo Mierswa, Simon Fischer
 */
public class ParameterTypeValue extends ParameterTypeSingle {

    /**
     * The type Operator value selection.
     */
    public static class OperatorValueSelection {

		private String operatorName;
		private String valueParameterName;
		private boolean isValue;

        /**
         * Instantiates a new Operator value selection.
         */
        public OperatorValueSelection() {}

        /**
         * Instantiates a new Operator value selection.
         *
         * @param operatorName         the operator name
         * @param isValue              the is value
         * @param valueOrParameterName the value or parameter name
         */
        public OperatorValueSelection(String operatorName, boolean isValue, String valueOrParameterName) {
			this.operatorName = operatorName;
			this.valueParameterName = valueOrParameterName;
			this.isValue = isValue;
		}

        /**
         * Gets operator.
         *
         * @return the operator
         */
        public String getOperator() {
			return operatorName;
		}

        /**
         * Gets parameter name.
         *
         * @return the parameter name
         */
        public String getParameterName() {
			if (!isValue) {
				return valueParameterName;
			}
			return null;
		}

        /**
         * Gets value name.
         *
         * @return the value name
         */
        public String getValueName() {
			if (isValue) {
				return valueParameterName;
			}
			return null;
		}

        /**
         * Is value boolean.
         *
         * @return the boolean
         */
        public boolean isValue() {
			return isValue;
		}
	}

	private static final long serialVersionUID = -5863628921324775010L;

	// only one character allowed
	private static final String ESCAPE_CHAR = "\\";
	private static final String ESCAPE_CHAR_REGEX = "\\\\";
	// only one character allowed
	private static final String SEPERATOR_CHAR_REGEX = "\\.";
	private static final String SEPERATOR_CHAR = ".";

    /**
     * Instantiates a new Parameter type value.
     *
     * @param element the element
     * @throws XMLException the xml exception
     */
    public ParameterTypeValue(Element element) throws XMLException {
		super(element);
		setOptional(false);
	}

    /**
     * Instantiates a new Parameter type value.
     *
     * @param key         the key
     * @param description the description
     */
    public ParameterTypeValue(String key, String description) {
		super(key, description);
		setOptional(false);
	}

	/** Returns null. */
	@Override
	public Object getDefaultValue() {
		return null;
	}

	/** Does nothing. */
	@Override
	public void setDefaultValue(Object defaultValue) {}

	@Override
	public String getRange() {
		return "values";
	}

	/** Returns false. */
	@Override
	public boolean isNumerical() {
		return false;
	}

	@Override
	public String notifyOperatorRenaming(String oldOperatorName, String newOperatorName, String parameterValue) {
		OperatorValueSelection selection = transformString2OperatorValueSelection(parameterValue);
		if (selection != null) {
			if (selection.getOperator().equals(oldOperatorName)) {
				selection.operatorName = newOperatorName;
			}
			return transformOperatorValueSelection2String(selection);
		}
		return parameterValue;
	}

    /**
     * Transform string 2 operator value selection operator value selection.
     *
     * @param parameterValue the parameter value
     * @return the operator value selection
     */
    public static OperatorValueSelection transformString2OperatorValueSelection(String parameterValue) {
		String[] unescaped = parameterValue.split("(?<=[^" + ESCAPE_CHAR_REGEX + "])" + SEPERATOR_CHAR_REGEX, -1);
		for (int i = 0; i < unescaped.length; i++) {
			unescaped[i] = unescape(unescaped[i]);
		}
		OperatorValueSelection selection = new OperatorValueSelection();
		if (unescaped.length == 4) {
			selection.operatorName = unescaped[1];
			selection.valueParameterName = unescaped[3];
			selection.isValue = unescaped[2].equals("value");
			return selection;
		}
		return null;
	}

	private static String unescape(String escapedString) {
		escapedString = escapedString.replace(ESCAPE_CHAR + SEPERATOR_CHAR, SEPERATOR_CHAR);
		escapedString = escapedString.replace(ESCAPE_CHAR + ESCAPE_CHAR, ESCAPE_CHAR);
		return escapedString;
	}

    /**
     * Transform operator value selection 2 string string.
     *
     * @param selection the selection
     * @return the string
     */
    public static String transformOperatorValueSelection2String(OperatorValueSelection selection) {
		String operator = selection.operatorName != null ? selection.getOperator().replace(ESCAPE_CHAR,
				ESCAPE_CHAR + ESCAPE_CHAR) : "";
		operator = operator.replace(SEPERATOR_CHAR, ESCAPE_CHAR + SEPERATOR_CHAR);
		String value = selection.valueParameterName != null ? selection.valueParameterName.replace(ESCAPE_CHAR, ESCAPE_CHAR
				+ ESCAPE_CHAR) : "";
		value = value.replace(SEPERATOR_CHAR, ESCAPE_CHAR + SEPERATOR_CHAR);
		return "operator" + SEPERATOR_CHAR + operator + SEPERATOR_CHAR + (selection.isValue ? "value" : "parameter")
				+ SEPERATOR_CHAR + value;
	}

	@Override
	protected void writeDefinitionToXML(Element typeElement) {}
}
