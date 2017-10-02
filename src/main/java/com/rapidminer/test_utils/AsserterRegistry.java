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
package com.rapidminer.test_utils;

import java.util.LinkedList;
import java.util.List;

import com.rapidminer.test.asserter.AsserterFactory;


/**
 * The type Asserter registry.
 *
 * @author Marius Helf
 */
public class AsserterRegistry {

	private List<Asserter> registeredAsserters = new LinkedList<Asserter>();

    /**
     * Register asserter.
     *
     * @param asserter the asserter
     */
    public void registerAsserter(Asserter asserter) {
		registeredAsserters.add(asserter);
	}

    /**
     * Register all asserters.
     *
     * @param factory the factory
     */
    public void registerAllAsserters(AsserterFactory factory) {
		for (Asserter asserter : factory.createAsserters()) {
			registerAsserter(asserter);
		}
	}

    /**
     * Gets asserter for object.
     *
     * @param object the object
     * @return the asserter for object
     */
    public List<Asserter> getAsserterForObject(Object object) {
		List<Asserter> availableAsserters = new LinkedList<Asserter>();
		for (Asserter asserter : registeredAsserters) {
			if (asserter.getAssertable().isInstance(object)) {
				availableAsserters.add(asserter);
			}
		}
		if (availableAsserters.isEmpty()) {
			return null;
		} else {
			return availableAsserters;
		}
	}

    /**
     * Gets asserter for objects.
     *
     * @param o1 the o 1
     * @param o2 the o 2
     * @return the asserter for objects
     */
    public List<Asserter> getAsserterForObjects(Object o1, Object o2) {
		List<Asserter> availableAsserters = new LinkedList<Asserter>();
		for (Asserter asserter : registeredAsserters) {
			Class<?> clazz = asserter.getAssertable();
			if (clazz.isInstance(o1) && clazz.isInstance(o2)) {
				availableAsserters.add(asserter);
			}
		}
		if (availableAsserters.isEmpty()) {
			return null;
		} else {
			return availableAsserters;
		}
	}

    /**
     * Gets asserter for class.
     *
     * @param clazz the clazz
     * @return the asserter for class
     */
    public List<Asserter> getAsserterForClass(Class<?> clazz) {
		List<Asserter> availableAsserters = new LinkedList<Asserter>();
		for (Asserter asserter : registeredAsserters) {
			if (asserter.getAssertable().isAssignableFrom(clazz)) {
				availableAsserters.add(asserter);
			}
		}
		if (availableAsserters.isEmpty()) {
			return null;
		} else {
			return availableAsserters;
		}
	}
}
