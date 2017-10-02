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
package com.rapidminer.operator.learner.functions.neuralnet;

import com.rapidminer.example.Attribute;
import com.rapidminer.example.Example;


/**
 * The base node for reading the data from examples and feeding it into the neural net.
 *
 * @author Ingo Mierswa
 */
public class InputNode extends Node {

	private static final long serialVersionUID = -7509629595651602261L;

	private Attribute attribute;

	private double attributeRange;

	private double attributeBase;

	private boolean normalize;

    /**
     * Instantiates a new Input node.
     *
     * @param nodeName the node name
     */
    public InputNode(String nodeName) {
		super(nodeName, INPUT, INPUT);
	}

    /**
     * Sets attribute.
     *
     * @param attribute      the attribute
     * @param attributeRange the attribute range
     * @param attributeBase  the attribute base
     * @param normalize      the normalize
     */
    public void setAttribute(Attribute attribute, double attributeRange, double attributeBase, boolean normalize) {
		this.attribute = attribute;
		this.attributeRange = attributeRange;
		this.attributeBase = attributeBase;
		this.normalize = normalize;
	}

	@Override
	public double calculateValue(boolean shouldCalculate, Example example) {
		if (Double.isNaN(currentValue) && shouldCalculate) {
			Attribute testSetAttribute = example.getAttributes().get(attribute.getName());
			double value = example.getValue(testSetAttribute);
			if (Double.isNaN(value)) {
				currentValue = 0;
			} else {
				if (normalize) {
					if (attributeRange != 0) {
						currentValue = (value - attributeBase) / attributeRange;
					} else {
						currentValue = value - attributeBase;
					}
				} else {
					currentValue = value;
				}
			}
		}
		return currentValue;
	}

	@Override
	public double calculateError(boolean shouldCalculate, Example example) {
		if (!Double.isNaN(currentValue) && Double.isNaN(currentError) && shouldCalculate) {
			currentError = 0;
			for (int i = 0; i < outputNodes.length; i++) {
				currentError += outputNodes[i].calculateError(true, example);
			}
		}
		return currentError;
	}

    /**
     * Gets attribute.
     *
     * @return the attribute
     */
    public Attribute getAttribute() {
		return attribute;
	}

    /**
     * Gets attribute range.
     *
     * @return the attribute range
     */
    public double getAttributeRange() {
		return attributeRange;
	}

    /**
     * Gets attribute base.
     *
     * @return the attribute base
     */
    public double getAttributeBase() {
		return attributeBase;
	}

    /**
     * Is normalize boolean.
     *
     * @return the boolean
     */
    public boolean isNormalize() {
		return normalize;
	}

    /**
     * Gets current value.
     *
     * @return the current value
     */
    public double getCurrentValue() {
		return currentValue;
	}
}
