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
package com.rapidminer.tools.math.container;

import java.io.Serializable;


/**
 * The node for a KD tree.
 *
 * @param <T> This is the type of value with is stored with the points and retrieved on nearest            neighbour search
 * @author Sebastian Land
 */
public class KDTreeNode<T> implements Serializable {

	private static final long serialVersionUID = -4204535347268139613L;

	private T storeValue;
	private double[] values;
	private KDTreeNode<T> lesserChild;
	private KDTreeNode<T> greaterChild;
	private int comparationDimension;

    /**
     * Instantiates a new Kd tree node.
     *
     * @param values               the values
     * @param storeValue           the store value
     * @param comparationDimension the comparation dimension
     */
    public KDTreeNode(double[] values, T storeValue, int comparationDimension) {
		this.values = values;
		this.storeValue = storeValue;
		this.comparationDimension = comparationDimension;
	}

    /**
     * Gets near child.
     *
     * @param compare the compare
     * @return the near child
     */
    public KDTreeNode<T> getNearChild(double[] compare) {
		if (compare[comparationDimension] < values[comparationDimension]) {
			return lesserChild;
		} else {
			return greaterChild;
		}
	}

    /**
     * Gets far child.
     *
     * @param compare the compare
     * @return the far child
     */
    public KDTreeNode<T> getFarChild(double[] compare) {
		if (compare[comparationDimension] >= values[comparationDimension]) {
			return lesserChild;
		} else {
			return greaterChild;
		}
	}

    /**
     * Has near child boolean.
     *
     * @param compare the compare
     * @return the boolean
     */
    public boolean hasNearChild(double[] compare) {
		if (compare[comparationDimension] < values[comparationDimension]) {
			return lesserChild != null;
		} else {
			return greaterChild != null;
		}
	}

    /**
     * Has far child boolean.
     *
     * @param compare the compare
     * @return the boolean
     */
    public boolean hasFarChild(double[] compare) {
		if (compare[comparationDimension] >= values[comparationDimension]) {
			return lesserChild != null;
		} else {
			return greaterChild != null;
		}
	}

    /**
     * Sets child.
     *
     * @param node the node
     */
    public void setChild(KDTreeNode<T> node) {
		if (node.getValues()[comparationDimension] < values[comparationDimension]) {
			lesserChild = node;
		} else {
			greaterChild = node;
		}
	}

    /**
     * Gets store value.
     *
     * @return the store value
     */
    public T getStoreValue() {
		return storeValue;
	}

    /**
     * Gets lesser child.
     *
     * @return the lesser child
     */
    public KDTreeNode<T> getLesserChild() {
		return lesserChild;
	}

    /**
     * Sets lesser child.
     *
     * @param leftChild the left child
     */
    public void setLesserChild(KDTreeNode<T> leftChild) {
		this.lesserChild = leftChild;
	}

    /**
     * Gets greater child.
     *
     * @return the greater child
     */
    public KDTreeNode<T> getGreaterChild() {
		return greaterChild;
	}

    /**
     * Sets greater child.
     *
     * @param rightChild the right child
     */
    public void setGreaterChild(KDTreeNode<T> rightChild) {
		this.greaterChild = rightChild;
	}

    /**
     * Get values double [ ].
     *
     * @return the double [ ]
     */
    public double[] getValues() {
		return values;
	}

    /**
     * Gets compare value.
     *
     * @return the compare value
     */
    public double getCompareValue() {
		return values[comparationDimension];
	}

    /**
     * Gets compare dimension.
     *
     * @return the compare dimension
     */
    public int getCompareDimension() {
		return comparationDimension;
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < comparationDimension; i++) {
			buffer.append(values[i] + "  ");
		}
		buffer.append("[");
		buffer.append(values[comparationDimension]);
		buffer.append("]  ");
		for (int i = comparationDimension + 1; i < values.length; i++) {
			buffer.append(values[i] + "  ");
		}
		return buffer.toString();
	}

}
