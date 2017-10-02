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

import com.rapidminer.example.table.DataRow;
import com.rapidminer.example.table.NominalMapping;
import com.rapidminer.operator.Annotations;

import java.io.Serializable;
import java.util.Iterator;


/**
 * Attributes should hold all information about a single attribute.
 * <ul>
 * <li>the name</li>
 * <li>the value type (nominal, numerical, ...)</li>
 * <li>the block type (single value, time series...)</li>
 * <li>a link to attribute statistics</li>
 * <li>a link to a nominal value mapping (if applicable)</li>
 * <li>a link to the information about how the attribute was generated</li>
 * </ul>
 *
 * @author Ingo Mierswa
 */
public interface Attribute extends Cloneable, Serializable {

    /**
     * Used to identify that this attribute is not part of any example table.
     */
    public static final int UNDEFINED_ATTRIBUTE_INDEX = -1;

    /**
     * Used to identify view attributes
     */
    public static final int VIEW_ATTRIBUTE_INDEX = -2;

    /**
     * Indicates a missing value for nominal values. For the internal values and numerical values,
     * Double.NaN is used which can be checked via {@link Double#isNaN(double)}.
     */
    public static final String MISSING_NOMINAL_VALUE = "?";

	/**
	 * Returns true if the given object is an attribute with the same name and table index.
	 */
	@Override
	public boolean equals(Object o);

	/**
	 * Returns the hash code. Please note that equal attributes must return the same hash code.
	 */
	@Override
	public int hashCode();

	/** Clones this attribute. */
	public Object clone();

	// ----------------------------------------------------------------------

    /**
     * Returns the name of the attribute.  @return the name
     *
     * @return the name
     */
    public String getName();

    /**
     * Sets the name of the attribute.  @param name the name
     *
     * @param name the name
     */
    public void setName(String name);

    /**
     * Returns the index in the example table.  @return the table index
     *
     * @return the table index
     */
    public int getTableIndex();

    /**
     * Sets the index in the example table.  @param index the index
     *
     * @param index the index
     */
    public void setTableIndex(int index);

	// ----------------------------------------------------------------------

    /**
     * Sets the Attributes instance to which this attribute belongs. This instance will be notified
     * when the attribute renames itself. This method must not be called except by the
     * {@link Attributes} to which this AttributeRole is added.
     *
     * @param attributes the attributes
     */
    public void addOwner(Attributes attributes);

    /**
     * Remove owner.
     *
     * @param attributes the attributes
     */
    public void removeOwner(Attributes attributes);

	// ----------------------------------------------------------------------

    /**
     * Returns the value for the column this attribute corresponds to in the given data row.  @param row the row
     *
     * @param row the row
     * @return the value
     */
    public double getValue(DataRow row);

    /**
     * Sets the value for the column this attribute corresponds to in the given data row.  @param row the row
     *
     * @param row   the row
     * @param value the value
     */
    public void setValue(DataRow row, double value);

    /**
     * Add transformation.
     *
     * @param transformation the transformation
     */
    public void addTransformation(AttributeTransformation transformation);

    /**
     * Gets last transformation.
     *
     * @return the last transformation
     */
    public AttributeTransformation getLastTransformation();

    /**
     * Clear all transformations.
     */
    public void clearTransformations();

	// ----------------------------------------------------------------------

    /**
     * Returns an iterator over all statistics objects available for this type of attribute.
     * Additional statistics can be registered via {@link #registerStatistics(Statistics)}.
     *
     * @return the all statistics
     */
    public Iterator<Statistics> getAllStatistics();

    /**
     * Registers the attribute statistics.  @param statistics the statistics
     *
     * @param statistics the statistics
     */
    public void registerStatistics(Statistics statistics);

    /**
     * Returns the attribute statistics.
     *
     * @param statisticsName the statistics name
     * @return the statistics
     * @deprecated Please use the method {@link ExampleSet#getStatistics(Attribute, String)}             instead.
     */
    @Deprecated
	public double getStatistics(String statisticsName);

    /**
     * Returns the attribute statistics with the given parameter.
     *
     * @param statisticsName the statistics name
     * @param parameter      the parameter
     * @return the statistics
     * @deprecated Please use the method {@link ExampleSet#getStatistics(Attribute, String, String)}             instead.
     */
    @Deprecated
	public double getStatistics(String statisticsName, String parameter);

    /**
     * Returns the construction description.  @return the construction
     *
     * @return the construction
     */
    public String getConstruction();

    /**
     * Sets the construction description.  @param description the description
     *
     * @param description the description
     */
    public void setConstruction(String description);

    /**
     * Returns the nominal mapping between nominal values and internal double representations.
     * Please note that invoking this method might result in an
     * {@link UnsupportedOperationException} for non-nominal attributes.
     *
     * @return the mapping
     */
    public NominalMapping getMapping();

    /**
     * Returns the nominal mapping between nominal values and internal double representations.
     * Please note that invoking this method might result in an exception for non-nominal
     * attributes.
     *
     * @param nominalMapping the nominal mapping
     */
    public void setMapping(NominalMapping nominalMapping);

	// ----------------------------------------------------------------------

    /**
     * Returns the block type of this attribute.
     *
     * @return the block type
     * @see com.rapidminer.tools.Ontology#ATTRIBUTE_BLOCK_TYPE com.rapidminer.tools.Ontology#ATTRIBUTE_BLOCK_TYPEcom.rapidminer.tools.Ontology#ATTRIBUTE_BLOCK_TYPE
     */
    public int getBlockType();

    /**
     * Sets the block type of this attribute.
     *
     * @param b the b
     * @see com.rapidminer.tools.Ontology#ATTRIBUTE_BLOCK_TYPE com.rapidminer.tools.Ontology#ATTRIBUTE_BLOCK_TYPEcom.rapidminer.tools.Ontology#ATTRIBUTE_BLOCK_TYPE
     */
    public void setBlockType(int b);

    /**
     * Returns the value type of this attribute.
     *
     * @return the value type
     * @see com.rapidminer.tools.Ontology#ATTRIBUTE_VALUE_TYPE com.rapidminer.tools.Ontology#ATTRIBUTE_VALUE_TYPEcom.rapidminer.tools.Ontology#ATTRIBUTE_VALUE_TYPE
     */
    public int getValueType();

	/** Returns a human readable string that describes this attribute. */
	@Override
	public String toString();

    /**
     * Sets the default value for this attribute.  @param value the value
     *
     * @param value the value
     */
    public void setDefault(double value);

    /**
     * Returns the default value for this attribute.  @return the default
     *
     * @return the default
     */
    public double getDefault();

    /**
     * Returns true if the attribute is nominal.  @return the boolean
     *
     * @return the boolean
     */
    public boolean isNominal();

    /**
     * Returns true if the attribute is numerical.  @return the boolean
     *
     * @return the boolean
     */
    public boolean isNumerical();

    /**
     * Returns true if the attribute is date_time.  @return the boolean
     *
     * @return the boolean
     */
    public boolean isDateTime();

    /**
     * Returns a formatted string of the given value according to the attribute type.  @param value the value
     *
     * @param value        the value
     * @param digits       the digits
     * @param quoteNominal the quote nominal
     * @return the as string
     */
    public String getAsString(double value, int digits, boolean quoteNominal);

    /**
     * Returns a set of annotations for this attribute.  @return the annotations
     *
     * @return the annotations
     */
    public Annotations getAnnotations();
}
