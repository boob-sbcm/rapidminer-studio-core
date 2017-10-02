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

import com.rapidminer.tools.Tools;

import java.io.Serializable;
import java.util.*;


/**
 * Input for Operator.apply(). Instances of this class are containers for IOObjects. They are
 * available by calling one of the <tt>getInput</tt> methods. The operator can choose between
 * keeping the IOObject in the container or delete it using it.<br>
 * <p>
 * From Version 5.0 on, IOContainers are only used for compatibility reasons and are only a
 * collection of IOObjects. IOContainers are no longer passed between Operators and hence most of
 * its functionality is obsolete.
 *
 * @author Simon Fischer, Ingo Mierswa
 * @see com.rapidminer.operator.IOObject
 */
public class IOContainer implements Serializable {

	private static final long serialVersionUID = 8152465082153754473L;

	private final List<IOObject> ioObjects;

    /**
     * The constant DUMMY_IO_CONTAINER.
     */
    public static final IOContainer DUMMY_IO_CONTAINER = new IOContainer(new IOObject[] { new ResultObjectAdapter() {

		private static final long serialVersionUID = -5877096753744650074L;

		@Override
		public String getName() {
			return "Dummy";
		}

		@Override
		public String toString() {
			return "No intermediate results for this operator";
		}

	} });

    /**
     * Creates a new and empty IOContainer.
     */
    public IOContainer() {
		this(new IOObject[0]);
	}

    /**
     * Creates a new IOContainer containing the contents of the Collection which must contain only
     * IOObjects.
     *
     * @param objectCollection the object collection
     */
    public IOContainer(Collection<? extends IOObject> objectCollection) {
		ioObjects = new ArrayList<>(objectCollection.size());
		ioObjects.addAll(objectCollection);
	}

    /**
     * Instantiates a new Io container.
     *
     * @param objectArray the object array
     */
    public IOContainer(IOObject... objectArray) {
		ioObjects = new ArrayList<>(objectArray.length);
		for (int i = 0; i < objectArray.length; i++) {
			ioObjects.add(objectArray[i]);
		}
	}

	@Override
	public String toString() {
		StringBuffer result = new StringBuffer("IOContainer (" + ioObjects.size() + " objects):" + Tools.getLineSeparator());
		for (IOObject current : ioObjects) {
			if (current != null) {
				result.append(current.toString() + Tools.getLineSeparator() + (current.getSource() != null
						? "(created by " + current.getSource() + ")" + Tools.getLineSeparator() : ""));
			}
		}
		return result.toString();
	}

    /**
     * Returns the number of {@link IOObject}s in this container.  @return the int
     *
     * @return the int
     */
    public int size() {
		return ioObjects.size();
	}

    /**
     * Returns the n-th {@link IOObject} in this container.  @param index the index
     *
     * @param index the index
     * @return the element at
     */
    public IOObject getElementAt(int index) {
		return ioObjects.get(index);
	}

    /**
     * Removes and returns the n-th {@link IOObject} in this container.  @param index the index
     *
     * @param index the index
     * @return the io object
     */
    public IOObject removeElementAt(int index) {
		return ioObjects.remove(index);
	}

    /**
     * Returns all IOObjects.
     *
     * @return the io object [ ]
     */
    public IOObject[] getIOObjects() {
		return ioObjects.toArray(new IOObject[ioObjects.size()]);
	}

    /**
     * Gets the first IOObject which is of class cls.  @param <T>  the type parameter
     *
     * @param <T> the type parameter
     * @param cls the cls
     * @return the t
     * @throws MissingIOObjectException the missing io object exception
     */
    public <T extends IOObject> T get(Class<T> cls) throws MissingIOObjectException {
		return getInput(cls, 0, false);
	}

    /**
     * Gets the nr-th IOObject which is of class cls.  @param <T>  the type parameter
     *
     * @param <T> the type parameter
     * @param cls the cls
     * @param nr  the nr
     * @return the t
     * @throws MissingIOObjectException the missing io object exception
     */
    public <T extends IOObject> T get(Class<T> cls, int nr) throws MissingIOObjectException {
		return getInput(cls, nr, false);
	}

    /**
     * Removes the first IOObject which is of class cls. The removed object is returned.
     *
     * @param <T> the type parameter
     * @param cls the cls
     * @return the t
     * @throws MissingIOObjectException the missing io object exception
     */
    public <T extends IOObject> T remove(Class<T> cls) throws MissingIOObjectException {
		return getInput(cls, 0, true);
	}

    /**
     * Removes the nr-th IOObject which is of class cls. The removed object is returned.
     *
     * @param <T> the type parameter
     * @param cls the cls
     * @param nr  the nr
     * @return the t
     * @throws MissingIOObjectException the missing io object exception
     */
    public <T extends IOObject> T remove(Class<T> cls, int nr) throws MissingIOObjectException {
		return getInput(cls, nr, true);
	}

    /**
     * Returns true if this IOContainer containts an IOObject of the desired class.
     *
     * @param cls the cls
     * @return the boolean
     */
    public boolean contains(Class<? extends IOObject> cls) {
		try {
			getInput(cls, 0, false);
			return true;
		} catch (MissingIOObjectException e) {
			return false;
		}
	}

	/**
	 * Gets the nr-th IOObject which is of class cls. If remove is set to true, the object is
	 * afterwards removed from this IOContainer.
	 */
	private <T extends IOObject> T getInput(Class<T> cls, int nr, boolean remove) throws MissingIOObjectException {
		int n = 0;
		Iterator<IOObject> i = ioObjects.iterator();
		while (i.hasNext()) {
			IOObject object = i.next();
			if ((object != null) && (cls.isInstance(object))) {
				if (n == nr) {
					if (remove) {
						i.remove();
					}
					return cls.cast(object);
				} else {
					n++;
				}
			}
		}
		throw new MissingIOObjectException(cls);
	}

    /**
     * Creates a new IOContainer by adding all IOObjects of this container to the given IOObject.
     *
     * @param object the object
     * @return the io container
     */
    public IOContainer append(IOObject object) {
		return append(new IOObject[] { object });
	}

    /**
     * Creates a new IOContainer by adding all IOObjects of this container to the given IOObjects.
     *
     * @param output the output
     * @return the io container
     */
    public IOContainer append(IOObject[] output) {
		List<IOObject> newObjects = new LinkedList<>();
		for (int i = 0; i < output.length; i++) {
			newObjects.add(output[i]);
		}
		newObjects.addAll(ioObjects);
		return new IOContainer(newObjects);
	}

    /**
     * Creates a new IOContainer by adding the given object before the IOObjects of this container.
     *
     * @param object the object
     * @return the io container
     */
    public IOContainer prepend(IOObject object) {
		return prepend(new IOObject[] { object });
	}

    /**
     * Creates a new IOContainer by adding the given objects before the IOObjects of this container.
     *
     * @param output the output
     * @return the io container
     */
    public IOContainer prepend(IOObject[] output) {
		List<IOObject> newObjects = new LinkedList<>();
		newObjects.addAll(ioObjects);
		for (int i = 0; i < output.length; i++) {
			newObjects.add(output[i]);
		}
		return new IOContainer(newObjects);
	}

    /**
     * Appends this container's IOObjects to output.  @param output the output
     *
     * @param output the output
     * @return the io container
     */
    public IOContainer append(Collection<IOObject> output) {
		List<IOObject> newObjects = new LinkedList<>();
		newObjects.addAll(output);
		newObjects.addAll(ioObjects);
		return new IOContainer(newObjects);
	}

    /**
     * Copies the contents of this IOContainer by invoking the method copy of all IOObjects.  @return the io container
     *
     * @return the io container
     */
    public IOContainer copy() {
		List<IOObject> clones = new LinkedList<>();
		Iterator<IOObject> i = ioObjects.iterator();
		while (i.hasNext()) {
			clones.add((i.next()).copy());
		}
		return new IOContainer(clones);
	}

    /**
     * Removes all Objects from this IOContainer.
     */
    public void removeAll() {
		ioObjects.clear();
	}

    /**
     * As list list.
     *
     * @return the list
     */
    public List<IOObject> asList() {
		return Collections.unmodifiableList(ioObjects);
	}
}
