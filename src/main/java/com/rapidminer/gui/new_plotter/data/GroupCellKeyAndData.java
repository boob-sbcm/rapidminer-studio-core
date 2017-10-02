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
package com.rapidminer.gui.new_plotter.data;

import com.rapidminer.gui.new_plotter.configuration.GroupCellKey;


/**
 * The type Group cell key and data.
 *
 * @author Marius Helf
 */
public class GroupCellKeyAndData {

    /**
     * The Key.
     */
    GroupCellKey key = new GroupCellKey();
    /**
     * The Data.
     */
    GroupCellData data = new GroupCellData();

    /**
     * Instantiates a new Group cell key and data.
     */
    public GroupCellKeyAndData() {
		super();
	}

    /**
     * Instantiates a new Group cell key and data.
     *
     * @param key  the key
     * @param data the data
     */
    public GroupCellKeyAndData(GroupCellKey key, GroupCellData data) {
		this.key = key;
		this.data = data;
	}

    /**
     * Gets key.
     *
     * @return the key
     */
    public GroupCellKey getKey() {
		return key;
	}

    /**
     * Gets data.
     *
     * @return the data
     */
    public GroupCellData getData() {
		return data;
	}

    /**
     * Sets key.
     *
     * @param key the key
     */
    public void setKey(GroupCellKey key) {
		this.key = key;
	}

    /**
     * Sets data.
     *
     * @param data the data
     */
    public void setData(GroupCellData data) {
		this.data = data;
	}
}
