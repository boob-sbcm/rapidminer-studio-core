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
 * A parameter type for Long values. Operators ask for the Long value with
 * {@link com.rapidminer.operator.Operator#getParameterAslong(String)}. For infinite ranges
 * Long.MIN_VALUE and Long.MAX_VALUE should be used.
 *
 * @author Ingo Mierswa, Simon Fischer
 */
public class ParameterTypeLong extends ParameterTypeNumber {

	private static final long serialVersionUID = -7360090072467405524L;

	private static final String ATTRIBUTE_DEFAULT = "default";

	private static final String ATTRIBUTE_MAX = "max";

	private static final String ATTRIBUTE_MIN = "min";

	private long defaultValue = -1;

	private long min = Long.MIN_VALUE;

	private long max = Long.MAX_VALUE;

	private boolean noDefault = true;

    /**
     * Instantiates a new Parameter type long.
     *
     * @param element the element
     * @throws XMLException the xml exception
     */
    public ParameterTypeLong(Element element) throws XMLException {
		super(element);

		noDefault = element.hasAttribute(ATTRIBUTE_DEFAULT);
		if (!noDefault) {
			defaultValue = Long.parseLong(element.getAttribute(ATTRIBUTE_DEFAULT));
		}
		max = Long.parseLong(element.getAttribute(ATTRIBUTE_MAX));
		min = Long.parseLong(element.getAttribute(ATTRIBUTE_MIN));
	}

    /**
     * Instantiates a new Parameter type long.
     *
     * @param key         the key
     * @param description the description
     * @param min         the min
     * @param max         the max
     */
    public ParameterTypeLong(String key, String description, long min, long max) {
		this(key, description, min, max, -1);
		this.noDefault = true;
		setOptional(false);
	}

    /**
     * Instantiates a new Parameter type long.
     *
     * @param key         the key
     * @param description the description
     * @param min         the min
     * @param max         the max
     * @param optional    the optional
     */
    public ParameterTypeLong(String key, String description, long min, long max, boolean optional) {
		this(key, description, min, max, -1);
		this.noDefault = true;
		setOptional(optional);
	}

    /**
     * Instantiates a new Parameter type long.
     *
     * @param key          the key
     * @param description  the description
     * @param min          the min
     * @param max          the max
     * @param defaultValue the default value
     */
    public ParameterTypeLong(String key, String description, long min, long max, long defaultValue) {
		super(key, description);
		this.defaultValue = defaultValue;
		this.min = min;
		this.max = max;
		this.noDefault = false;
	}

    /**
     * Instantiates a new Parameter type long.
     *
     * @param key          the key
     * @param description  the description
     * @param min          the min
     * @param max          the max
     * @param defaultValue the default value
     * @param expert       the expert
     */
    public ParameterTypeLong(String key, String description, long min, long max, long defaultValue, boolean expert) {
		this(key, description, min, max, defaultValue);
		setExpert(expert);
	}

    /**
     * Sets min value.
     *
     * @param min the min
     */
    public void setMinValue(long min) {
		this.min = min;
	}

    /**
     * Gets max value.
     *
     * @param max the max
     */
    public void getMaxValue(long max) {
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
     * Gets min valuelong.
     *
     * @return the min valuelong
     */
    public long getMinValuelong() {
		return min;
	}

    /**
     * Gets max valuelong.
     *
     * @return the max valuelong
     */
    public long getMaxValuelong() {
		return max;
	}

    /**
     * Gets defaultlong.
     *
     * @return the defaultlong
     */
    public long getDefaultlong() {
		return defaultValue;
	}

	@Override
	public Object getDefaultValue() {
		if (noDefault) {
			return null;
		} else {
			return Long.valueOf(defaultValue);
		}
	}

	@Override
	public void setDefaultValue(Object defaultValue) {
		noDefault = false;
		this.defaultValue = (Long) defaultValue;
	}

	/** Returns true. */
	@Override
	public boolean isNumerical() {
		return true;
	}

	@Override
	public String getRange() {
		String range = "Long; ";
		if (min == -Long.MAX_VALUE) {
			range += "-\u221E";
		} else {
			range += min;
		}
		range += "-";
		if (max == Long.MAX_VALUE) {
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
     * @param defaultValue2 the default value 2
     * @return the string representation
     */
    public String getStringRepresentation(long defaultValue2) {
		String valueString = defaultValue2 + "";
		if (defaultValue2 == Long.MAX_VALUE) {
			valueString = "+\u221E";
		} else if (defaultValue2 == Long.MIN_VALUE) {
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
