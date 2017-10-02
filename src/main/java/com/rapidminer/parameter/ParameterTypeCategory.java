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

import com.rapidminer.io.process.XMLTools;
import com.rapidminer.tools.XMLException;
import org.w3c.dom.Element;


/**
 * A parameter type for categories. These are several Strings and one of these is the default value.
 * Operators ask for the index of the selected value with
 * {@link com.rapidminer.operator.Operator#getParameterAsInt(String)}.
 *
 * @author Ingo Mierswa, Simon Fischer
 */
public class ParameterTypeCategory extends ParameterTypeSingle {

	private static final long serialVersionUID = 5747692587025691591L;

	private static final String ATTRIBUTE_DEFAULT = "default";

	private static final String ELEMENT_VALUES = "Values";

	private static final String ELEMENT_VALUE = "Value";

	private int defaultValue = 0;

	private String[] categories = new String[0];

    /**
     * Instantiates a new Parameter type category.
     *
     * @param element the element
     * @throws XMLException the xml exception
     */
    public ParameterTypeCategory(Element element) throws XMLException {
		super(element);

		defaultValue = Integer.parseInt(element.getAttribute(ATTRIBUTE_DEFAULT));
		categories = XMLTools.getChildTagsContentAsStringArray(XMLTools.getChildElement(element, ELEMENT_VALUES, true),
				ELEMENT_VALUE);
	}

    /**
     * Instantiates a new Parameter type category.
     *
     * @param key          the key
     * @param description  the description
     * @param categories   the categories
     * @param defaultValue the default value
     * @param expert       the expert
     */
    public ParameterTypeCategory(String key, String description, String[] categories, int defaultValue, boolean expert) {
		this(key, description, categories, defaultValue);
		setExpert(expert);
	}

    /**
     * Instantiates a new Parameter type category.
     *
     * @param key          the key
     * @param description  the description
     * @param categories   the categories
     * @param defaultValue the default value
     */
    public ParameterTypeCategory(String key, String description, String[] categories, int defaultValue) {
		super(key, description);
		this.categories = categories;
		this.defaultValue = defaultValue;
	}

    /**
     * Gets default.
     *
     * @return the default
     */
    public int getDefault() {
		return defaultValue;
	}

	@Override
	public Object getDefaultValue() {
		if (defaultValue == -1) {
			return null;
		} else {
			return categories[defaultValue];
		}
	}

	@Override
	public void setDefaultValue(Object defaultValue) {
		this.defaultValue = (Integer) defaultValue;
	}

	/** Returns false. */
	@Override
	public boolean isNumerical() {
		return false;
	}

    /**
     * Gets category.
     *
     * @param index the index
     * @return the category
     */
    public String getCategory(int index) {
		return categories[index];
	}

    /**
     * Gets index.
     *
     * @param string the string
     * @return the index
     */
    public int getIndex(String string) {
		for (int i = 0; i < categories.length; i++) {
			if (categories[i].equals(string)) {
				return Integer.valueOf(i);
			}
		}
		// try to interpret string as number
		try {
			return Integer.parseInt(string);
		} catch (NumberFormatException e) {
			return -1;
		}
	}

	@Override
	public String toString(Object value) {
		try {
			if (value == null) {
				return null;
			}
			int index = Integer.parseInt(value.toString());
			if (index >= categories.length) {
				return "";
			}
			return super.toString(categories[index]);
		} catch (NumberFormatException e) {
			return super.toString(value);
		}
	}

    /**
     * Get values string [ ].
     *
     * @return the string [ ]
     */
    public String[] getValues() {
		return categories;
	}

	@Override
	public String getRange() {
		StringBuffer values = new StringBuffer();
		for (int i = 0; i < categories.length; i++) {
			if (i > 0) {
				values.append(", ");
			}
			values.append(categories[i]);
		}
		return values.toString() + "; default: " + categories[defaultValue];
	}

    /**
     * Gets number of categories.
     *
     * @return the number of categories
     */
    public int getNumberOfCategories() {
		return categories.length;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @return always {@code false}
	 */
	@Override
	public boolean isSensitive() {
		return false;
	}

	@Override
	protected void writeDefinitionToXML(Element typeElement) {
		typeElement.setAttribute(ATTRIBUTE_DEFAULT, defaultValue + "");

		Element valuesElement = XMLTools.addTag(typeElement, ELEMENT_VALUES);
		for (String category : categories) {
			XMLTools.addTag(valuesElement, ELEMENT_VALUE, category);
		}
	}
}
