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
 * A parameter type for integer values. Operators ask for the integer value with
 * {@link com.rapidminer.operator.Operator#getParameterAsInt(String)}. For infinite ranges
 * Integer.MIN_VALUE and Integer.MAX_VALUE should be used.
 *
 * @author Ingo Mierswa, Simon Fischer
 */
public class ParameterTypeInt extends ParameterTypeNumber {

	private static final long serialVersionUID = -7360090072467405524L;

	private static final String ATTRIBUTE_DEFAULT = "default";

	private static final String ATTRIBUTE_MAX = "max";

	private static final String ATTRIBUTE_MIN = "min";

	private int defaultValue = -1;

	private int min = Integer.MIN_VALUE;

	private int max = Integer.MAX_VALUE;

	private boolean noDefault = true;

    /**
     * Instantiates a new Parameter type int.
     *
     * @param element the element
     * @throws XMLException the xml exception
     */
    public ParameterTypeInt(Element element) throws XMLException {
		super(element);

		noDefault = element.hasAttribute(ATTRIBUTE_DEFAULT);
		if (!noDefault) {
			defaultValue = Integer.parseInt(element.getAttribute(ATTRIBUTE_DEFAULT));
		}
		max = Integer.parseInt(element.getAttribute(ATTRIBUTE_MAX));
		min = Integer.parseInt(element.getAttribute(ATTRIBUTE_MIN));
	}

    /**
     * Instantiates a new Parameter type int.
     *
     * @param key         the key
     * @param description the description
     * @param min         the min
     * @param max         the max
     */
    public ParameterTypeInt(String key, String description, int min, int max) {
		this(key, description, min, max, -1);
		this.noDefault = true;
		setOptional(false);
	}

    /**
     * Instantiates a new Parameter type int.
     *
     * @param key         the key
     * @param description the description
     * @param min         the min
     * @param max         the max
     * @param optional    the optional
     */
    public ParameterTypeInt(String key, String description, int min, int max, boolean optional) {
		this(key, description, min, max, -1);
		this.noDefault = true;
		setOptional(optional);
	}

    /**
     * Instantiates a new Parameter type int.
     *
     * @param key          the key
     * @param description  the description
     * @param min          the min
     * @param max          the max
     * @param defaultValue the default value
     */
    public ParameterTypeInt(String key, String description, int min, int max, int defaultValue) {
		super(key, description);
		this.defaultValue = defaultValue;
		this.min = min;
		this.max = max;
		this.noDefault = false;
	}

    /**
     * Instantiates a new Parameter type int.
     *
     * @param key          the key
     * @param description  the description
     * @param min          the min
     * @param max          the max
     * @param defaultValue the default value
     * @param expert       the expert
     */
    public ParameterTypeInt(String key, String description, int min, int max, int defaultValue, boolean expert) {
		this(key, description, min, max, defaultValue);
		setExpert(expert);
	}

    /**
     * Sets min value.
     *
     * @param min the min
     */
    public void setMinValue(int min) {
		this.min = min;
	}

    /**
     * Gets max value.
     *
     * @param max the max
     */
    public void getMaxValue(int max) {
		this.max = max;
	}

	@Override
	public double getMinValue() {
		return min;
	}

	@Override
	public double getMaxValue() {
		return max;
	}

    /**
     * Gets min value int.
     *
     * @return the min value int
     */
    public int getMinValueInt() {
		return min;
	}

    /**
     * Gets max value int.
     *
     * @return the max value int
     */
    public int getMaxValueInt() {
		return max;
	}

    /**
     * Gets default int.
     *
     * @return the default int
     */
    public int getDefaultInt() {
		return defaultValue;
	}

	@Override
	public Object getDefaultValue() {
		if (noDefault) {
			return null;
		} else {
			return Integer.valueOf(defaultValue);
		}
	}

	@Override
	public void setDefaultValue(Object defaultValue) {
		noDefault = false;
		this.defaultValue = (Integer) defaultValue;
	}

	/** Returns true. */
	@Override
	public boolean isNumerical() {
		return true;
	}

	@Override
	public String getRange() {
		String range = "integer; ";
		if (min == -Integer.MAX_VALUE) {
			range += "-\u221E";
		} else {
			range += min;
		}
		range += "-";
		if (max == Integer.MAX_VALUE) {
			range += "+\u221E";
		} else {
			range += max;
		}
		if (!noDefault) {
			range += "; default: " + getStringRepresentation(defaultValue);
		}
		return range;
	}

    /**
     * Gets string representation.
     *
     * @param value the value
     * @return the string representation
     */
    public String getStringRepresentation(int value) {
		String valueString = value + "";
		if (value == Integer.MAX_VALUE) {
			valueString = "+\u221E";
		} else if (value == Integer.MIN_VALUE) {
			valueString = "-\u221E";
		}
		return valueString;
	}

	@Override
	protected void writeDefinitionToXML(Element typeElement) {
		if (!noDefault) {
			typeElement.setAttribute(ATTRIBUTE_DEFAULT, defaultValue + "");
		}

		typeElement.setAttribute(ATTRIBUTE_MIN, min + "");
		typeElement.setAttribute(ATTRIBUTE_MAX, max + "");
	}
}
