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
package com.rapidminer.example;

import com.rapidminer.tools.Ontology;

import java.io.Serializable;


/**
 * This class holds all basic information about an attribute. This is useful since a cloned
 * attribute can simply use the same reference to a description object wihtout the need of cloning
 * all information. This reduces the amount of used memory due to attribute clones.
 *
 * @author Ingo Mierswa
 */
public class AttributeDescription implements Serializable, Cloneable {

	private static final long serialVersionUID = 8641898727515830321L;

	/** The name of the attribute. */
	private String name;

	/**
	 * An int indicating the value type in terms of the Ontology.ATTRIBUTE_VALUE_TYPE.
	 */
	private int valueType = Ontology.ATTRIBUTE_VALUE;

	/**
	 * An int indicating the block type in terms of the Ontology.ATTRIBUTE_BLOCK_TYPE.
	 */
	private int blockType = Ontology.ATTRIBUTE_BLOCK;

	/** The default value for this Attribute. */
	private double defaultValue = 0.0;

	/** Index of this attribute in its ExampleTable. */
	private int index = Attribute.UNDEFINED_ATTRIBUTE_INDEX;

    /**
     * Instantiates a new Attribute description.
     *
     * @param attribute    the attribute
     * @param name         the name
     * @param valueType    the value type
     * @param blockType    the block type
     * @param defaultValue the default value
     * @param tableIndex   the table index
     */
    public AttributeDescription(Attribute attribute, String name, int valueType, int blockType, double defaultValue,
			int tableIndex) {
		this.name = name;
		this.valueType = valueType;
		this.blockType = blockType;
		this.defaultValue = defaultValue;
		this.index = tableIndex;

	}

	private AttributeDescription(AttributeDescription other) {
		this.name = other.name;
		this.valueType = other.valueType;
		this.blockType = other.blockType;
		this.defaultValue = other.defaultValue;
		this.index = other.index;
	}

	@Override
	public Object clone() {
		return new AttributeDescription(this);
	}

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
		return this.name;
	}

    /**
     * Sets name.
     *
     * @param newName the new name
     */
    public void setName(String newName) {
		this.name = newName;
	}

    /**
     * Gets value type.
     *
     * @return the value type
     */
    public int getValueType() {
		return this.valueType;
	}

    /**
     * Gets block type.
     *
     * @return the block type
     */
    public int getBlockType() {
		return this.blockType;
	}

    /**
     * Sets block type.
     *
     * @param b the b
     */
    public void setBlockType(int b) {
		this.blockType = b;
	}

    /**
     * Gets default.
     *
     * @return the default
     */
    public double getDefault() {
		return this.defaultValue;
	}

    /**
     * Sets default.
     *
     * @param defaultValue the default value
     */
    public void setDefault(double defaultValue) {
		this.defaultValue = defaultValue;
	}

    /**
     * Gets table index.
     *
     * @return the table index
     */
    public int getTableIndex() {
		return this.index;
	}

    /**
     * Sets table index.
     *
     * @param i the
     */
    public void setTableIndex(int i) {
		this.index = i;
	}

	/**
	 * Returns true if the given attribute has the same name and the same table index.
	 */
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof AttributeDescription)) {
			return false;
		}
		AttributeDescription a = (AttributeDescription) o;
		if (this.index != a.getTableIndex()) {
			return false;
		}
		if (!this.name.equals(a.getName())) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		return name.hashCode() ^ this.index;
	}
}
