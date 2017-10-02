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
 * The node for a ball tree.
 *
 * @param <T> This is the type of value with is stored with the points and retrieved on nearest            neighbour search
 * @author Sebastian Land
 */
public class BallTreeNode<T> implements Serializable {

	private static final long serialVersionUID = 5250382342093166168L;
	private double[] center;
	private double radius;
	private T value;

	private BallTreeNode<T> leftChild;
	private BallTreeNode<T> rightChild;

    /**
     * Instantiates a new Ball tree node.
     *
     * @param center the center
     * @param radius the radius
     * @param value  the value
     */
    public BallTreeNode(double[] center, double radius, T value) {
		this.center = center;
		this.radius = radius;
		this.value = value;
	}

    /**
     * Gets left child.
     *
     * @return the left child
     */
    public BallTreeNode<T> getLeftChild() {
		return leftChild;
	}

    /**
     * Sets left child.
     *
     * @param leftChild the left child
     */
    public void setLeftChild(BallTreeNode<T> leftChild) {
		this.leftChild = leftChild;
	}

    /**
     * Gets right child.
     *
     * @return the right child
     */
    public BallTreeNode<T> getRightChild() {
		return rightChild;
	}

    /**
     * Sets right child.
     *
     * @param rightChild the right child
     */
    public void setRightChild(BallTreeNode<T> rightChild) {
		this.rightChild = rightChild;
	}

    /**
     * Get center double [ ].
     *
     * @return the double [ ]
     */
    public double[] getCenter() {
		return center;
	}

    /**
     * Gets radius.
     *
     * @return the radius
     */
    public double getRadius() {
		return radius;
	}

    /**
     * Replace child.
     *
     * @param replaceNode     the replace node
     * @param replacementNode the replacement node
     */
    public void replaceChild(BallTreeNode<T> replaceNode, BallTreeNode<T> replacementNode) {
		if (leftChild == replaceNode) {
			leftChild = replacementNode;
		}
		if (rightChild == replaceNode) {
			rightChild = replacementNode;
		}
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("[");
		for (double centerDouble : center) {
			buffer.append(centerDouble + "  ");
		}
		buffer.append("]  ");
		buffer.append(radius);
		return buffer.toString();
	}

    /**
     * Sets radius.
     *
     * @param radius the radius
     */
    public void setRadius(double radius) {
		this.radius = radius;
	}

    /**
     * Is leaf boolean.
     *
     * @return the boolean
     */
    public boolean isLeaf() {
		return getLeftChild() == null && getRightChild() == null;
	}

    /**
     * Has two childs boolean.
     *
     * @return the boolean
     */
    public boolean hasTwoChilds() {
		return (getLeftChild() != null && getRightChild() != null);
	}

    /**
     * This method returns the left child if existing or the right child if left doesnt exist. If
     * right is null either, then null is returned
     *
     * @return the child
     */
    public BallTreeNode<T> getChild() {
		if (getLeftChild() != null) {
			return getLeftChild();
		} else {
			return getRightChild();
		}
	}

    /**
     * Sets child.
     *
     * @param node the node
     */
    public void setChild(BallTreeNode<T> node) {
		if (!hasLeftChild()) {
			setLeftChild(node);
		} else {
			setRightChild(node);
		}
	}

    /**
     * Gets store value.
     *
     * @return the store value
     */
    public T getStoreValue() {
		return value;
	}

    /**
     * Has left child boolean.
     *
     * @return the boolean
     */
    public boolean hasLeftChild() {
		return this.leftChild != null;
	}

    /**
     * Has right child boolean.
     *
     * @return the boolean
     */
    public boolean hasRightChild() {
		return this.rightChild != null;
	}
}
