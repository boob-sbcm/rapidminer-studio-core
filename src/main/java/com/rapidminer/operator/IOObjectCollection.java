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
package com.rapidminer.operator;

import java.util.*;


/**
 * A collection of similar IOObjects that can be created, e.g. in an iteration.
 *
 * @param <T> the type parameter
 * @author Simon Fischer
 */
public class IOObjectCollection<T extends IOObject> extends ResultObjectAdapter {

	private static final long serialVersionUID = 1L;

	private List<T> objects;

    /**
     * Instantiates a new Io object collection.
     */
    public IOObjectCollection() {
		this.objects = new LinkedList<T>();
	}

    /**
     * Instantiates a new Io object collection.
     *
     * @param objects the objects
     */
    public IOObjectCollection(List<T> objects) {
		this.objects = objects;
	}

    /**
     * Size int.
     *
     * @return the int
     */
    public int size() {
		return objects.size();
	}

    /**
     * Add.
     *
     * @param ioobject the ioobject
     */
    public void add(T ioobject) {
		this.objects.add(ioobject);
	}

    /**
     * Instantiates a new Io object collection.
     *
     * @param objects the objects
     */
    public IOObjectCollection(T[] objects) {
		this(Arrays.asList(objects));
	}

    /**
     * Gets objects.
     *
     * @return the objects
     */
    public List<T> getObjects() {
		return Collections.unmodifiableList(objects);
	}

	/** Copies the children. */
	@Override
	@SuppressWarnings("unchecked")
	public IOObjectCollection<T> copy() {
		List<T> copiedElements = new ArrayList<T>(objects.size());
		for (T ioo : objects) {
			copiedElements.add((T) ioo.copy());
		}
		IOObjectCollection<T> copy = new IOObjectCollection<T>(copiedElements);
		copy.cloneAnnotationsFrom(this);
		return copy;
	}

	@Override
	public String toResultString() {
		StringBuilder b = new StringBuilder();
		b.append(objects.size() + " objects:\n");
		for (T o : objects) {
			b.append(o.toString());
			b.append("\n");
		}
		return b.toString();
	}

	@Override
	public String toString() {
		return "Collection of size " + objects.size();
	}

    /**
     * Gets element class.
     *
     * @param recursive the recursive
     * @return the element class
     */
    public Class<? extends IOObject> getElementClass(boolean recursive) {
		if (objects == null || objects.isEmpty()) {
			return IOObject.class;
		} else {
			T first = objects.get(0);
			if (!recursive) {
				return first.getClass();
			} else {
				if (first instanceof IOObjectCollection) {
					return ((IOObjectCollection<?>) first).getElementClass(true);
				} else {
					return first.getClass();
				}
			}
		}
	}

    /**
     * Gets objects recursive.
     *
     * @return the objects recursive
     */
    public List<IOObject> getObjectsRecursive() {
		ArrayList<IOObject> allChildren = new ArrayList<IOObject>();
		getElementsRecursive(allChildren);
		return allChildren;
	}

	@SuppressWarnings("unchecked")
	private void getElementsRecursive(List<IOObject> result) {
		for (IOObject element : objects) {
			if (element instanceof IOObjectCollection) {
				((IOObjectCollection<IOObject>) element).getElementsRecursive(result);
			} else {
				result.add(element);
			}
		}
	}

    /**
     * Gets element.
     *
     * @param index     the index
     * @param recursive the recursive
     * @return the element
     */
    public IOObject getElement(int index, boolean recursive) {
		if (!recursive) {
			return objects.get(index);
		} else {
			return getObjectsRecursive().get(index);
		}
	}

}
