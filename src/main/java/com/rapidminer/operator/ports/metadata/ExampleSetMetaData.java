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
package com.rapidminer.operator.ports.metadata;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.rapidminer.RapidMiner;
import com.rapidminer.example.AttributeRole;
import com.rapidminer.example.Attributes;
import com.rapidminer.example.ExampleSet;
import com.rapidminer.tools.Ontology;
import com.rapidminer.tools.ParameterService;
import com.rapidminer.tools.Tools;


/**
 * This class stores detailed meta data information about ExampleSets.
 *
 * @author Simon Fischer, Sebastian Land
 */
public class ExampleSetMetaData extends MetaData {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private SetRelation attributesRelation = SetRelation.EQUAL;

	private MDInteger numberOfExamples = new MDInteger();

	private Map<String, AttributeMetaData> attributeMetaData = new LinkedHashMap<String, AttributeMetaData>();

	private boolean nominalDataWasShrinked = false;

    /**
     * Instantiates a new Example set meta data.
     */
    public ExampleSetMetaData() {
		super(ExampleSet.class);
	}

    /**
     * Instantiates a new Example set meta data.
     *
     * @param keyValueMap the key value map
     */
    public ExampleSetMetaData(Map<String, Object> keyValueMap) {
		super(ExampleSet.class, keyValueMap);
	}

    /**
     * Instantiates a new Example set meta data.
     *
     * @param key   the key
     * @param value the value
     */
    public ExampleSetMetaData(String key, Object value) {
		super(ExampleSet.class, key, value);
	}

    /**
     * Instantiates a new Example set meta data.
     *
     * @param attributeMetaData the attribute meta data
     */
    public ExampleSetMetaData(List<AttributeMetaData> attributeMetaData) {
		super(ExampleSet.class);
		addAllAttributes(attributeMetaData);
	}

    /**
     * Instantiates a new Example set meta data.
     *
     * @param clazz the clazz
     */
    public ExampleSetMetaData(Class<? extends ExampleSet> clazz) {
		super(clazz);
	}

    /**
     * Instantiates a new Example set meta data.
     *
     * @param clazz             the clazz
     * @param attributeMetaData the attribute meta data
     */
    public ExampleSetMetaData(Class<? extends ExampleSet> clazz, List<AttributeMetaData> attributeMetaData) {
		super(clazz);
		addAllAttributes(attributeMetaData);
	}

    /**
     * This constructor will generate a complete meta data description of the given example set.
     * Please pay attention to the fact that it might be very big since the meta data will contain
     * each nominal value stored in the data. With large, id-like data this will become very big.
     *
     * @param exampleSet the example set
     */
    public ExampleSetMetaData(ExampleSet exampleSet) {
		this(exampleSet, false, true);
	}

    /**
     * Creates an {@link ExampleSetMetaData} object for the provided ExampleSet object.
     *
     * @param exampleSet the ExampleSet object the meta-data should be constructed for
     * @param shortened  whether the meta data should be shortened AND whether the statistics should be            recalculated
     * @deprecated use {@link #ExampleSetMetaData(ExampleSet, boolean, boolean)} instead
     */
    @Deprecated
	public ExampleSetMetaData(ExampleSet exampleSet, boolean shortened) {
		this(exampleSet, shortened, !shortened);
	}

    /**
     * Creates an {@link ExampleSetMetaData} object for the provided ExampleSet object.
     *
     * @param exampleSet            the ExampleSet object the meta-data should be constructed for
     * @param shortened             whether the meta data should be shortened. In case it should be shortened the            meta-data will contain at most {@link #getMaximumNumberOfAttributes()} attributes
     * @param recalculateStatistics defines whether the ExampleSet statistics should be recalculated
     */
    public ExampleSetMetaData(ExampleSet exampleSet, boolean shortened, boolean recalculateStatistics) {
		super(ExampleSet.class);
		int maxNumber = Integer.MAX_VALUE;
		if (shortened) {
			maxNumber = getMaximumNumberOfAttributes();
		}
		if (recalculateStatistics) {
			try {
				exampleSet.recalculateAllAttributeStatistics();
			} catch (UnsupportedOperationException e) {
				// May not be supported by HeaderExampleSet
			}
		}
		Iterator<AttributeRole> i = exampleSet.getAttributes().allAttributeRoles();
		while (i.hasNext()) {
			AttributeRole role = i.next();
			addAttribute(new AttributeMetaData(role, exampleSet, shortened));
			maxNumber--;
			if (maxNumber == 0) {
				break;
			}
		}
		numberOfExamples = new MDInteger(exampleSet.size());
	}

    /**
     * Gets attribute by name.
     *
     * @param name the name
     * @return the attribute by name
     */
    public AttributeMetaData getAttributeByName(String name) {
		return attributeMetaData.get(name);
	}

    /**
     * Gets attribute by role.
     *
     * @param role the role
     * @return the attribute by role
     */
    public AttributeMetaData getAttributeByRole(String role) {
		for (AttributeMetaData amd : attributeMetaData.values()) {
			String currentRole = amd.getRole();
			if (currentRole != null && currentRole.equals(role)) {
				return amd;
			}
		}
		return null;
	}

    /**
     * Add all attributes.
     *
     * @param attributes the attributes
     */
    public void addAllAttributes(Collection<AttributeMetaData> attributes) {
		for (AttributeMetaData amd : attributes) {
			addAttribute(amd);
		}
	}

	/*
	 * public void removeAllAttributes(List<AttributeMetaData> attributes) {
	 * attributeMetaData.removeAll(attributes); }
	 */

    /**
     * Gets all attributes.
     *
     * @return the all attributes
     */
    public Collection<AttributeMetaData> getAllAttributes() {
		return attributeMetaData.values();
	}

    /**
     * Remove attribute.
     *
     * @param attribute the attribute
     */
    public void removeAttribute(AttributeMetaData attribute) {
		attributeMetaData.remove(attribute.getName());
	}

    /**
     * Add attribute.
     *
     * @param attribute the attribute
     */
    public void addAttribute(AttributeMetaData attribute) {
		if (attributeMetaData == null) {
			attributeMetaData = new LinkedHashMap<String, AttributeMetaData>();
		}
		// registering this exampleSetmetaData as owner of the attribute.
		attribute = attribute.registerOwner(this);
		attributeMetaData.put(attribute.getName(), attribute);
	}

	@Override
	public String getDescription() {
		StringBuilder buf = new StringBuilder(super.getDescription());
		buf.append("<br/>Number of examples ");
		buf.append(numberOfExamples.toString());
		if (attributeMetaData != null) {
			buf.append("<br/>");
			switch (attributesRelation) {
				case SUBSET:
					buf.append("At most ");
					break;
				case SUPERSET:
					buf.append("At least ");
					break;
				default:
					// ignore, number of attributes will evaluate to "1 attribute" or "x attributes"
					break;
			}
			buf.append(attributeMetaData.size());
			buf.append(" attribute" + (attributeMetaData.size() != 1 ? "s" : "") + ": ");
			buf.append(
			        "<table><thead><tr><th>Role</th><th>Name</th><th>Type</th><th>Range</th><th>Missings</th><th>Comment</th></tr></thead><tbody>");
			// boolean first = true;
			for (AttributeMetaData amd : attributeMetaData.values()) {
				buf.append(amd.getDescriptionAsTableRow());
			}
			buf.append("</tbody></table>");
		}
		return buf.toString();
	}

    /**
     * Sets attributes.
     *
     * @param attributes the attributes
     */
    public void setAttributes(List<AttributeMetaData> attributes) {
		attributeMetaData.clear();
		addAllAttributes(attributes);
	}

	@Override
	public ExampleSetMetaData clone() {
		ExampleSetMetaData clone = (ExampleSetMetaData) super.clone();
		clone.attributesRelation = this.attributesRelation;
		clone.numberOfExamples = this.numberOfExamples.copy();
		if (this.attributeMetaData != null) {
			clone.attributeMetaData = new LinkedHashMap<String, AttributeMetaData>();
			for (AttributeMetaData attribute : this.attributeMetaData.values()) {
				clone.addAttribute(attribute.clone());
			}
		}
		clone.nominalDataWasShrinked = this.nominalDataWasShrinked;
		return clone;
	}

    /**
     * Contains attributes with value type meta data info.
     *
     * @param type            the type
     * @param includeSpecials the include specials
     * @return the meta data info
     */
    public MetaDataInfo containsAttributesWithValueType(int type, boolean includeSpecials) {
		if (attributeMetaData != null) {
			for (AttributeMetaData amd : attributeMetaData.values()) {
				SetRelation relation;
				if (amd.isSpecial()) {
					if (!includeSpecials) {
						continue;
					}
					relation = attributesRelation;
				} else {
					relation = attributesRelation;
				}
				if (amd.getRole() == null && Ontology.ATTRIBUTE_VALUE_TYPE.isA(amd.getValueType(), type)) {
					if (relation == SetRelation.EQUAL || relation == SetRelation.SUPERSET) {
						return MetaDataInfo.YES;
					} else {
						return MetaDataInfo.UNKNOWN;
					}
				}
			}
			if (attributesRelation == SetRelation.SUPERSET || attributesRelation == SetRelation.UNKNOWN) {
				return MetaDataInfo.UNKNOWN;
			} else {
				return MetaDataInfo.NO;
			}
		} else {
			return MetaDataInfo.UNKNOWN;
		}
	}

    /**
     * Gets special.
     *
     * @param role the role
     * @return the special
     */
    public AttributeMetaData getSpecial(String role) {
		if (attributeMetaData != null) {
			for (AttributeMetaData amd : attributeMetaData.values()) {
				if (role.equals(amd.getRole())) {
					return amd;
				}
			}
		}
		return null;
	}

    /**
     * Gets label meta data.
     *
     * @return the label meta data
     */
    public AttributeMetaData getLabelMetaData() {
		return getSpecial(Attributes.LABEL_NAME);
	}

    /**
     * This returns if an attribute with the given role exists in the example set. If the role is
     * confidence, then it checks not whether exactly the same role occurs, but if any role starts
     * with the confidence stem.
     *
     * @param role the role
     * @return the meta data info
     */
    public MetaDataInfo hasSpecial(String role) {
		if (attributeMetaData == null) {
			return MetaDataInfo.UNKNOWN;
		}
		// TODO: This is too slow
		if (role.equals(Attributes.CONFIDENCE_NAME)) {
			for (AttributeMetaData amd : attributeMetaData.values()) {
				String currentRole = amd.getRole();
				if (currentRole != null && currentRole.startsWith(role)) {
					return MetaDataInfo.YES;
				}
			}

		} else {
			for (AttributeMetaData amd : attributeMetaData.values()) {
				if (role.equals(amd.getRole())) {
					return MetaDataInfo.YES;
				}
			}
		}
		switch (attributesRelation) {
			case SUBSET:
				return MetaDataInfo.UNKNOWN;
			case SUPERSET:
			case EQUAL:
				return MetaDataInfo.NO;
			default:
				return MetaDataInfo.UNKNOWN;
		}
	}

    /**
     * Joins the attributes of both example sets.
     *
     * @param es2                 the es 2
     * @param prefixForDuplicates If this is non-null, attributes with duplicate names will be renamed. Otherwise,            only one will be kept.
     * @return the example set meta data
     */
    public ExampleSetMetaData joinAttributes(ExampleSetMetaData es2, String prefixForDuplicates) {
		ExampleSetMetaData result = this.clone();
		if (this.attributeMetaData == null || es2.attributeMetaData == null) {
			return result;
		}
		// joining
		for (AttributeMetaData a : es2.attributeMetaData.values()) {
			AttributeMetaData clone = a.clone();
			if (a.getRole() == null || !a.getRole().equals(Attributes.ID_NAME)) {
				switch (result.containsAttributeName(a.getName())) {
					case YES:
						if (prefixForDuplicates != null) {
							clone.setName(a.getName() + prefixForDuplicates);
							result.attributeMetaData.put(clone.getName(), clone);
						}
						break;
					case NO:
						result.attributeMetaData.put(clone.getName(), clone);
						break;
					case UNKNOWN:
						result.attributeMetaData.put(clone.getName(), clone);
						// at least one with this name will be there, but the duplicate may be as
						// well
						result.attributesAreSubset();
						break;
				}
			}
		}
		// check how sure we can be to have the correct attribute meta data
		if (this.attributesRelation == SetRelation.EQUAL && es2.attributesRelation == SetRelation.EQUAL) {
			result.attributesRelation = SetRelation.EQUAL;
		} else if (es2.attributesRelation == SetRelation.SUPERSET || attributesRelation == SetRelation.SUPERSET) {
			result.attributesRelation = SetRelation.SUPERSET;
		} else {
			result.attributesRelation = SetRelation.UNKNOWN;
		}
		return result;
	}

    /**
     * Contains attribute name meta data info.
     *
     * @param name the name
     * @return the meta data info
     */
    public MetaDataInfo containsAttributeName(String name) {
		if (attributeMetaData != null) {
			boolean contains = attributeMetaData.containsKey(name);
			switch (attributesRelation) {
				case EQUAL:
					return contains ? MetaDataInfo.YES : MetaDataInfo.NO;
				case SUPERSET:
					return contains ? MetaDataInfo.YES : MetaDataInfo.UNKNOWN;
				case SUBSET:
					return contains ? MetaDataInfo.UNKNOWN : MetaDataInfo.NO;
				case UNKNOWN:
				default: // cannot happen
					return MetaDataInfo.UNKNOWN;
			}
		} else {
			return MetaDataInfo.UNKNOWN;
		}
	}

    /**
     * Contains special attribute meta data info.
     *
     * @param role the role
     * @return the meta data info
     */
    public MetaDataInfo containsSpecialAttribute(String role) {
		if (attributeMetaData != null) {
			boolean contains = false;
			for (AttributeMetaData amd : getAllAttributes()) {
				String itsRole = amd.getRole();
				if (itsRole != null) {
					if (itsRole.equals(role)) {
						contains = true;
					}
				}
			}

			switch (attributesRelation) {
				case EQUAL:
					return contains ? MetaDataInfo.YES : MetaDataInfo.NO;
				case SUPERSET:
					return contains ? MetaDataInfo.YES : MetaDataInfo.UNKNOWN;
				case SUBSET:
					return contains ? MetaDataInfo.UNKNOWN : MetaDataInfo.NO;
				case UNKNOWN:
				default: // cannot happen
					return MetaDataInfo.UNKNOWN;
			}
		} else {
			return MetaDataInfo.UNKNOWN;
		}
	}

    /**
     * Changes the knowledge about the attributes in this set. Example: If we had full knowledge (
     * {@link SetRelation#EQUAL) and <code>relation</code> if {@link SetRelation#SUBSET}, our
     * knowledge changes to {@link SetRelation#SUBSET}. If the current knowledge is
     * {@link SetRelation#SUBSET} and <code>relation</code> is {@link SetRelation#SUPERSET}, our
     * knowledge changes to {@link SetRelation#UNKNOWN}*
     *
     * @param relation the relation*
     * @param relation the relation
     */
    public void mergeSetRelation(SetRelation relation) {
		this.attributesRelation = this.attributesRelation.merge(relation);
	}

    /**
     * Gets attribute set relation.
     *
     * @return the attribute set relation
     */
    public SetRelation getAttributeSetRelation() {
		return attributesRelation;
	}

    /**
     * Attributes are known.
     */
    public void attributesAreKnown() {
		attributesRelation = SetRelation.EQUAL;
	}

    /**
     * Declares that the attributes in this example set are a superset of {@link #attributeMetaData}
     * .
     */
    public void attributesAreSuperset() {
		mergeSetRelation(SetRelation.SUPERSET);
	}

    /**
     * Declares that the attributes in this example set are only a subset of
     * {@link #attributeMetaData}.
     */
    public void attributesAreSubset() {
		mergeSetRelation(SetRelation.SUBSET);
	}

    /**
     * Convenience method for setting the number of examples if the number is known exactly.
     *
     * @param num the num
     */
    public void setNumberOfExamples(int num) {
		numberOfExamples = new MDInteger(num);
	}

    /**
     * Method for setting the number of examples.
     *
     * @param num the num
     */
    public void setNumberOfExamples(MDInteger num) {
		numberOfExamples = num;
	}

    /**
     * Number of examples is unkown.
     */
    public void numberOfExamplesIsUnkown() {
		numberOfExamples.setUnkown();
	}

    /**
     * Gets number of examples.
     *
     * @return the number of examples
     */
    public MDInteger getNumberOfExamples() {
		return numberOfExamples;
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("ExampleSetMetaData: #examples: " + numberOfExamples + "; #attributes: " + getAllAttributes().size()
		        + Tools.getLineSeparator());
		for (AttributeMetaData amd : getAllAttributes()) {
			buffer.append(amd.toString() + Tools.getLineSeparator());
		}
		return buffer.toString();
	}

    /**
     * Transpose meta data.
     *
     * @return the meta data
     */
    public MetaData transpose() {
		ExampleSetMetaData transposedMD = new ExampleSetMetaData();
		transposedMD.addAttribute(new AttributeMetaData(Attributes.ID_NAME, Ontology.NOMINAL, Attributes.ID_NAME));
		if (this.numberOfExamples.isKnown()) {
			int num = this.numberOfExamples.getValue();
			int type;
			switch (this.containsAttributesWithValueType(Ontology.NOMINAL, true)) {
				case YES:
					type = Ontology.NOMINAL;
					break;
				case NO:
					type = Ontology.REAL;
					break;
				case UNKNOWN:
				default:
					type = Ontology.ATTRIBUTE_VALUE;
			}
			for (int i = 0; i < num; i++) {
				transposedMD.addAttribute(new AttributeMetaData("att_" + (i + 1), type));
			}
		} else {
			transposedMD.attributesAreSuperset();
		}
		transposedMD.numberOfExamples = new MDInteger(this.attributeMetaData.size());
		switch (this.attributesRelation) {
			case EQUAL:
				// do nothing
				break;
			case SUBSET:
				transposedMD.numberOfExamples.reduceByUnknownAmount();
				break;
			case SUPERSET:
				transposedMD.numberOfExamples.increaseByUnknownAmount();
				break;
			case UNKNOWN:
				transposedMD.numberOfExamples = new MDInteger();
			default:
		}
		return transposedMD;
	}

    /**
     * This method removes all regular attributes from this exampleSet meta data
     */
    public void clearRegular() {
		Iterator<AttributeMetaData> iterator = getAllAttributes().iterator();
		while (iterator.hasNext()) {
			AttributeMetaData amd = iterator.next();
			if (!amd.isSpecial()) {
				iterator.remove();
			}
		}
	}

    /**
     * This method removes every attribute
     */
    public void clear() {
		getAllAttributes().clear();
	}

    /**
     * Gets number of regular attributes.
     *
     * @return the number of regular attributes
     */
    public int getNumberOfRegularAttributes() {
		int regular = 0;
		for (AttributeMetaData amd : getAllAttributes()) {
			if (!amd.isSpecial()) {
				regular++;
			}
		}
		return regular;
	}

    /**
     * Checks if the attribute sets are equal.  @param other the other
     *
     * @param other the other
     * @return the meta data info
     */
    public MetaDataInfo equalHeader(ExampleSetMetaData other) {
		if (other == this) {
			return MetaDataInfo.YES;
		}
		if (other.getAllAttributes().size() != getAllAttributes().size()
		        && other.getAttributeSetRelation() == SetRelation.EQUAL && getAttributeSetRelation() == SetRelation.EQUAL) {
			return MetaDataInfo.NO;
		}
		if (other.getAllAttributes().size() == getAllAttributes().size()
		        && other.getAttributeSetRelation() == SetRelation.EQUAL && getAttributeSetRelation() == SetRelation.EQUAL) {
			for (AttributeMetaData amd : getAllAttributes()) {
				AttributeMetaData otherAMD = other.getAttributeByName(amd.getName());
				if (otherAMD == null) {
					return MetaDataInfo.NO;
				}
				String otherRole = otherAMD.getRole();
				if (otherRole != null) {
					if (!otherAMD.getRole().equals(amd.getRole())) {
						return MetaDataInfo.NO;
					}
				}
				if (otherAMD.getValueType() != amd.getValueType()) {
					return MetaDataInfo.NO;
				}
			}
			return MetaDataInfo.YES;
		}
		return MetaDataInfo.UNKNOWN;
	}

    /**
     * Gets attribute names by type.
     *
     * @param mustBeOfType the must be of type
     * @return the attribute names by type
     */
    public Collection<String> getAttributeNamesByType(int mustBeOfType) {
		Collection<String> names = new LinkedList<String>();
		for (AttributeMetaData attribute : getAllAttributes()) {
			if (Ontology.ATTRIBUTE_VALUE_TYPE.isA(attribute.getValueType(), mustBeOfType)) {
				names.add(attribute.getName());
			}
		}
		return names;
	}

    /**
     * Gets short description.
     *
     * @return the short description
     */
    public String getShortDescription() {
		StringBuilder buf = new StringBuilder(super.getDescription());
		buf.append("<br/>Number of examples ");
		buf.append(numberOfExamples.toString());
		if (attributeMetaData != null) {
			buf.append("<br/>");
			switch (attributesRelation) {
				case SUBSET:
					buf.append("At most ");
					break;
				case SUPERSET:
					buf.append("At least ");
					break;
				default:
					// ignore, number of attributes will evaluate to "1 attribute" or "x attributes"
					break;
			}
			buf.append(attributeMetaData.size());
			buf.append(" attribute" + (attributeMetaData.size() != 1 ? "s" : "") + ": ");
		}
		if (nominalDataWasShrinked) {
			buf.append(
			        "<br/><small><strong>Note:</strong> Some of the nominal values in this set were discarded due to performance reasons. You can change this behaviour in the preferences (<code>"
			                + RapidMiner.PROPERTY_RAPIDMINER_GENERAL_MAX_NOMINAL_VALUES + "</code>).</small>");
		}
		return buf.toString();
	}

    /**
     * This method must be called by attributes in order to inform the example set that they have
     * been renamed. Before calling this method, the amd already must have its new name.
     *
     * @param amd     the amd
     * @param oldName the old name
     */
/* pp */void attributeRenamed(AttributeMetaData amd, String oldName) {
		attributeMetaData.remove(oldName);
		attributeMetaData.put(amd.getName(), amd);
	}

    /**
     * Remove all attributes.
     */
    public void removeAllAttributes() {
		attributeMetaData.clear();
	}

    /**
     * Sets nominal data was shrinked.
     *
     * @param b the b
     */
    public void setNominalDataWasShrinked(boolean b) {
		nominalDataWasShrinked = true;
	}

    /**
     * Returns the maximum number of attributes to be used for shortened meta data generation as
     * specified by {@link RapidMiner#PROPERTY_RAPIDMINER_GENERAL_MAX_META_DATA_ATTRIBUTES}.
     *
     * @return the maximum number of attributes
     */
    public static int getMaximumNumberOfAttributes() {
		int maxSize = 250;
		String maxSizeString = ParameterService
		        .getParameterValue(RapidMiner.PROPERTY_RAPIDMINER_GENERAL_MAX_META_DATA_ATTRIBUTES);
		if (maxSizeString != null) {
			maxSize = Integer.parseInt(maxSizeString);
			if (maxSize == 0) {
				maxSize = Integer.MAX_VALUE;
			}
		}
		return maxSize;
	}
}
