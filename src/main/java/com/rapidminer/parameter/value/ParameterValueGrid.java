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
package com.rapidminer.parameter.value;

import com.rapidminer.operator.Operator;
import com.rapidminer.parameter.ParameterType;
import com.rapidminer.parameter.ParameterTypeInt;


/**
 * A grid of numerical parameter values.
 *
 * @author Tobias Malbrecht
 */
public class ParameterValueGrid extends ParameterValues {

    /**
     * The constant SCALE_LINEAR.
     */
    public static final int SCALE_LINEAR = 0;

    /**
     * The constant SCALE_QUADRATIC.
     */
    public static final int SCALE_QUADRATIC = 1;

    /**
     * The constant SCALE_LOGARITHMIC.
     */
    public static final int SCALE_LOGARITHMIC = 2;

    /**
     * The constant SCALE_LOGARITHMIC_LEGACY.
     */
    public static final int SCALE_LOGARITHMIC_LEGACY = 3;

    /**
     * The constant SCALES.
     */
    public static final String[] SCALES = { "linear", "quadratic", "logarithmic", "logarithmic (legacy)" };

    /**
     * The constant DEFAULT_STEPS.
     */
    public static final int DEFAULT_STEPS = 10;

    /**
     * The constant DEFAULT_SCALE.
     */
    public static final int DEFAULT_SCALE = SCALE_LINEAR;

	private String min;

	private String max;

	private String steps;

	private String stepSize;

	private int scale;

    /**
     * Instantiates a new Parameter value grid.
     *
     * @param operator the operator
     * @param type     the type
     * @param min      the min
     * @param max      the max
     */
    public ParameterValueGrid(Operator operator, ParameterType type, String min, String max) {
		this(operator, type, min, max, Integer.toString(DEFAULT_STEPS), DEFAULT_SCALE);
	}

    /**
     * Instantiates a new Parameter value grid.
     *
     * @param operator the operator
     * @param type     the type
     * @param min      the min
     * @param max      the max
     * @param stepSize the step size
     */
    public ParameterValueGrid(Operator operator, ParameterType type, String min, String max, String stepSize) {
		super(operator, type);
		this.min = min;
		this.max = max;
		this.steps = null;
		this.stepSize = stepSize;
		this.scale = SCALE_LINEAR;
	}

    /**
     * Instantiates a new Parameter value grid.
     *
     * @param operator the operator
     * @param type     the type
     * @param min      the min
     * @param max      the max
     * @param steps    the steps
     * @param scale    the scale
     */
    public ParameterValueGrid(Operator operator, ParameterType type, String min, String max, String steps, int scale) {
		super(operator, type);
		this.min = min;
		this.max = max;
		this.steps = steps;
		this.scale = scale;
	}

    /**
     * Instantiates a new Parameter value grid.
     *
     * @param operator  the operator
     * @param type      the type
     * @param min       the min
     * @param max       the max
     * @param steps     the steps
     * @param scaleName the scale name
     */
    public ParameterValueGrid(Operator operator, ParameterType type, String min, String max, String steps, String scaleName) {
		super(operator, type);
		this.min = min;
		this.max = max;
		this.steps = steps;
		this.scale = SCALE_LINEAR;
		for (int i = 0; i < SCALES.length; i++) {
			if (scaleName.equals(SCALES[i])) {
				this.scale = i;
				break;
			}
		}
	}

    /**
     * Sets min.
     *
     * @param min the min
     */
    public void setMin(String min) {
		this.min = min;
	}

    /**
     * Gets min.
     *
     * @return the min
     */
    public String getMin() {
		return min;
	}

    /**
     * Sets max.
     *
     * @param max the max
     */
    public void setMax(String max) {
		this.max = max;
	}

    /**
     * Gets max.
     *
     * @return the max
     */
    public String getMax() {
		return max;
	}

    /**
     * Sets steps.
     *
     * @param steps the steps
     */
    public void setSteps(String steps) {
		this.steps = steps;
	}

    /**
     * Gets steps.
     *
     * @return the steps
     */
    public String getSteps() {
		return steps;
	}

    /**
     * Sets scale.
     *
     * @param scale the scale
     */
    public void setScale(int scale) {
		this.scale = scale;
	}

    /**
     * Gets scale.
     *
     * @return the scale
     */
    public int getScale() {
		return scale;
	}

	@Override
	public void move(int index, int direction) {}

	@Override
	public String[] getValuesArray() {
		double[] values = getValues();
		String[] valuesArray = new String[values.length];
		if (type instanceof ParameterTypeInt) {
			for (int i = 0; i < values.length; i++) {
				valuesArray[i] = Integer.toString((int) values[i]);
			}
		} else {
			for (int i = 0; i < values.length; i++) {
				valuesArray[i] = Double.toString(values[i]);
			}
		}
		return valuesArray;
	}

    /**
     * Get values double [ ].
     *
     * @return the double [ ]
     */
    public double[] getValues() {
		double[] values = null;
		if (stepSize != null && steps == null) {
			steps = Integer.toString((int) (Double.valueOf(max) - Double.valueOf(min)) / Integer.parseInt(stepSize));
		}
		switch (scale) {
			case SCALE_LINEAR:
				values = scalePolynomial(Integer.parseInt(steps), 1);
				break;
			case SCALE_QUADRATIC:
				values = scalePolynomial(Integer.parseInt(steps), 2);
				break;
			case SCALE_LOGARITHMIC:
				values = scaleLogarithmic(Integer.parseInt(steps));
				break;
			case SCALE_LOGARITHMIC_LEGACY:
				values = scaleLogarithmicLegacy(Integer.parseInt(steps));
				break;
			default:
				values = scalePolynomial(Integer.parseInt(steps), 1);
		}
		if (type instanceof ParameterTypeInt) {
			if (values.length > 0) {
				for (int i = 0; i < values.length; i++) {
					values[i] = Math.round(values[i]);
				}
				int count = 1;
				for (int i = 1; i < values.length; i++) {
					if (values[i] != values[i - 1]) {
						count++;
					}
				}
				double[] uniqueValues = new double[count];
				uniqueValues[0] = values[0];
				count = 1;
				for (int i = 1; i < values.length; i++) {
					if (values[i] != values[i - 1]) {
						uniqueValues[count] = values[i];
						count++;
					}
				}
				return uniqueValues;
			} else {
				return values;
			}
		}
		return values;
	}

	private double[] scalePolynomial(int steps, double power) {
		double[] values = new double[steps + 1];
		double minValue = Double.parseDouble(min);
		double maxValue = Double.parseDouble(max);
		for (int i = 0; i < steps + 1; i++) {
			values[i] = minValue + Math.pow((double) i / (double) steps, power) * (maxValue - minValue);
		}
		return values;
	}

	private double[] scaleLogarithmic(int steps) {
		double minValue = Double.parseDouble(min);
		double maxValue = Double.parseDouble(max);
		double[] values = new double[steps + 1];
		for (int i = 0; i < steps + 1; i++) {
			values[i] = Math.pow(maxValue / minValue, (double) i / (double) steps) * minValue;
		}
		return values;
	}

	private double[] scaleLogarithmicLegacy(int steps) {
		double minValue = Double.parseDouble(min);
		double maxValue = Double.parseDouble(max);
		double[] values = new double[steps + 1];
		double offset = 1 - minValue;
		for (int i = 0; i < steps + 1; i++) {
			values[i] = Math.pow(maxValue + offset, (double) i / (double) steps) - offset;
		}
		return values;
	}

	@Override
	public int getNumberOfValues() {
		return getValues().length;
	}

	@Override
	public String getValuesString() {
		return "[" + min + ";" + max + ";" + steps + ";" + SCALES[scale] + "]";
	}

	@Override
	public String toString() {
		return "grid: " + min + " - " + max + " (" + steps + ", " + SCALES[scale] + ")";
	}
}
