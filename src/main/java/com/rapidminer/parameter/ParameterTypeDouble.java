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
 * A parameter type for double values. Operators ask for the double value with
 * {@link com.rapidminer.operator.Operator#getParameterAsDouble(String)}. For infinite ranges
 * Double.POSITIVE_INFINITY and Double.NEGATIVE_INFINITY should be used.
 *
 * @author Ingo Mierswa, Simon Fischer
 */
public class ParameterTypeDouble extends ParameterTypeNumber {

	private static final long serialVersionUID = 2455026868706964187L;

	private static final String ATTRIBUTE_DEFAULT = "default";

	private static final String ATTRIBUTE_MAX = "max";

	private static final String ATTRIBUTE_MIN = "min";

	private double defaultValue = Double.NaN;

	private double min = Double.NEGATIVE_INFINITY;

	private double max = Double.POSITIVE_INFINITY;

	private boolean noDefault = true;

    /**
     * Instantiates a new Parameter type double.
     *
     * @param element the element
     * @throws XMLException the xml exception
     */
    public ParameterTypeDouble(Element element) throws XMLException {
		super(element);

		noDefault = element.hasAttribute(ATTRIBUTE_DEFAULT);
		if (!noDefault) {
			defaultValue = Double.parseDouble(element.getAttribute(ATTRIBUTE_DEFAULT));
		}
		max = Double.parseDouble(element.getAttribute(ATTRIBUTE_MAX));
		min = Double.parseDouble(element.getAttribute(ATTRIBUTE_MIN));
	}

    /**
     * Instantiates a new Parameter type double.
     *
     * @param key         the key
     * @param description the description
     * @param min         the min
     * @param max         the max
     */
    public ParameterTypeDouble(String key, String description, double min, double max) {
		this(key, description, min, max, Double.NaN);
		this.noDefault = true;
	}

    /**
     * Instantiates a new Parameter type double.
     *
     * @param key         the key
     * @param description the description
     * @param min         the min
     * @param max         the max
     * @param optional    the optional
     */
    public ParameterTypeDouble(String key, String description, double min, double max, boolean optional) {
		this(key, description, min, max, Double.NaN);
		this.noDefault = true;
		setOptional(optional);
	}

    /**
     * Instantiates a new Parameter type double.
     *
     * @param key          the key
     * @param description  the description
     * @param min          the min
     * @param max          the max
     * @param defaultValue the default value
     */
    public ParameterTypeDouble(String key, String description, double min, double max, double defaultValue) {
		super(key, description);
		this.defaultValue = defaultValue;
		this.min = min;
		this.max = max;
		setExpert(false);
	}

    /**
     * Instantiates a new Parameter type double.
     *
     * @param key          the key
     * @param description  the description
     * @param min          the min
     * @param max          the max
     * @param defaultValue the default value
     * @param expert       the expert
     */
    public ParameterTypeDouble(String key, String description, double min, double max, double defaultValue, boolean expert) {
		super(key, description);
		this.defaultValue = defaultValue;
		this.min = min;
		this.max = max;
		setExpert(expert);
	}

	@Override
	public double getMinValue() {
		return min;
	}

	@Override
	public double getMaxValue() {
		return max;
	}

	@Override
	public Object getDefaultValue() {
		if (Double.isNaN(defaultValue)) {
			return null;
		} else {
			return Double.valueOf(defaultValue);
		}
	}

	@Override
	public void setDefaultValue(Object object) {
		this.defaultValue = (Double) object;
	}

	/** Returns true. */
	@Override
	public boolean isNumerical() {
		return true;
	}

	@Override
	public String getRange() {
		String range = "real; ";
		if (min == Double.NEGATIVE_INFINITY) {
			range += "-\u221E";
		} else {
			range += min;
		}
		range += "-";
		if (max == Double.POSITIVE_INFINITY) {
			range += "+\u221E";
		} else {
			range += max;
		}
		if (!noDefault) {
			range += "; default: " + defaultValue;
		}
		return range;
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
