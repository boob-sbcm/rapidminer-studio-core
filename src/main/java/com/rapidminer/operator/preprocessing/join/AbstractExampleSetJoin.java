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
package com.rapidminer.operator.preprocessing.join;

import com.rapidminer.example.Attribute;
import com.rapidminer.example.AttributeRole;
import com.rapidminer.example.Attributes;
import com.rapidminer.example.ExampleSet;
import com.rapidminer.example.utils.ExampleSetBuilder;
import com.rapidminer.operator.*;
import com.rapidminer.operator.ProcessSetupError.Severity;
import com.rapidminer.operator.ports.InputPort;
import com.rapidminer.operator.ports.OutputPort;
import com.rapidminer.operator.ports.metadata.AttributeMetaData;
import com.rapidminer.operator.ports.metadata.ExampleSetMetaData;
import com.rapidminer.operator.ports.metadata.ExampleSetUnionRule;
import com.rapidminer.operator.ports.metadata.SimpleMetaDataError;
import com.rapidminer.parameter.ParameterType;
import com.rapidminer.parameter.ParameterTypeBoolean;
import com.rapidminer.tools.Ontology;
import com.rapidminer.tools.container.Pair;

import java.util.*;


/**
 * <p>
 * Build the join of two example sets.
 * </p>
 * <p>
 * Please note that this check for duplicate attributes will only be applied for regular attributes.
 * Special attributes of the second input example set which do not exist in the first example set
 * will simply be added. If they already exist they are simply skipped.
 * </p>
 *
 * @author Ingo Mierswa
 */
public abstract class AbstractExampleSetJoin extends Operator {

    /**
     * The constant VERSION_SWAPPED_INPUT_PORTS.
     */
    public static final OperatorVersion VERSION_SWAPPED_INPUT_PORTS = new OperatorVersion(5, 1, 8);

    /**
     * The constant LEFT_EXAMPLE_SET_INPUT.
     */
    protected static final String LEFT_EXAMPLE_SET_INPUT = "left";
    /**
     * The constant RIGHT_EXAMPLE_SET_INPUT.
     */
    protected static final String RIGHT_EXAMPLE_SET_INPUT = "right";

	private InputPort leftInput = getInputPorts().createPort(LEFT_EXAMPLE_SET_INPUT);
	private InputPort rightInput = getInputPorts().createPort(RIGHT_EXAMPLE_SET_INPUT);
	private OutputPort joinOutput = getOutputPorts().createPort("join");

    /**
     * The parameter name for &quot;Indicates if double attributes should be removed or
     * renamed&quot;
     */
    public static final String PARAMETER_REMOVE_DOUBLE_ATTRIBUTES = "remove_double_attributes";

    /**
     * Helper class to find the correct data for all union attributes.
     */
    protected static class AttributeSource {

        /**
         * The constant FIRST_SOURCE.
         */
        protected static final int FIRST_SOURCE = 1;

        /**
         * The constant SECOND_SOURCE.
         */
        protected static final int SECOND_SOURCE = 2;

        /**
         * The Source.
         */
        protected int source;

        /**
         * The Attribute.
         */
        protected Attribute attribute;

        /**
         * Instantiates a new Attribute source.
         *
         * @param source    the source
         * @param attribute the attribute
         */
        public AttributeSource(int source, Attribute attribute) {
			this.source = source;
			this.attribute = attribute;
		}

        /**
         * Gets source.
         *
         * @return the source
         */
        protected int getSource() {
			return source;
		}

        /**
         * Gets attribute.
         *
         * @return the attribute
         */
        protected Attribute getAttribute() {
			return attribute;
		}
	}

    /**
     * Instantiates a new Abstract example set join.
     *
     * @param description the description
     */
    public AbstractExampleSetJoin(OperatorDescription description) {
		super(description);
		getTransformer().addRule(new ExampleSetUnionRule(leftInput, rightInput, joinOutput, "_from_ES2") {

			@Override
			protected String getPrefix() {
				return getParameterAsBoolean(PARAMETER_REMOVE_DOUBLE_ATTRIBUTES) ? null : "_from_ES2";
			}

			@Override
			protected ExampleSetMetaData modifyMetaData(ExampleSetMetaData leftEMD, ExampleSetMetaData rightEMD) {
				List<AttributeMetaData> joinedAttributesMetaData = getUnionAttributesMetaData(leftEMD, rightEMD);
				ExampleSetMetaData joinedEMD = new ExampleSetMetaData();
				joinedEMD.addAllAttributes(joinedAttributesMetaData);
				return joinedEMD;
			}
		});

	}

    /**
     * Gets left input.
     *
     * @return the left input
     */
    public InputPort getLeftInput() {
		return leftInput;
	}

    /**
     * Gets right input.
     *
     * @return the right input
     */
    public InputPort getRightInput() {
		return rightInput;
	}

    /**
     * Gets join output.
     *
     * @return the join output
     */
    public OutputPort getJoinOutput() {
		return joinOutput;
	}

    /**
     * Join data example set builder.
     *
     * @param es1                      the es 1
     * @param es2                      the es 2
     * @param originalAttributeSources the original attribute sources
     * @param unionAttributeList       the union attribute list
     * @return the example set builder
     * @throws OperatorException the operator exception
     */
    protected abstract ExampleSetBuilder joinData(ExampleSet es1, ExampleSet es2,
			List<AttributeSource> originalAttributeSources, List<Attribute> unionAttributeList) throws OperatorException;

    /**
     * Is id needed boolean.
     *
     * @return the boolean
     */
    protected abstract boolean isIdNeeded();

	@Override
	public void doWork() throws OperatorException {
		ExampleSet es1;
		ExampleSet es2;
		if (getCompatibilityLevel().isAtMost(VERSION_SWAPPED_INPUT_PORTS)) {
			/*
			 * please note the order of calls: As a result from the transformation from process tree
			 * to process flow this error was introduced. We introduced an incompatibly version
			 * change to overcome this.
			 */
			es2 = leftInput.getData(ExampleSet.class);
			es1 = rightInput.getData(ExampleSet.class);
		} else {
			/*
			 * This is the correct order used by all operators that using a more current version
			 * than VERSION_SWAPPED_INPUT_PORTS
			 */
			es1 = leftInput.getData(ExampleSet.class);
			es2 = rightInput.getData(ExampleSet.class);
		}

		if (this.isIdNeeded()) {
			Attribute id1 = es1.getAttributes().getId();
			Attribute id2 = es2.getAttributes().getId();

			// sanity checks
			if (id1 == null || id2 == null) {
				throw new UserError(this, 129);
			}
			if (!Ontology.ATTRIBUTE_VALUE_TYPE.isA(id1.getValueType(), id2.getValueType())
					&& !Ontology.ATTRIBUTE_VALUE_TYPE.isA(id2.getValueType(), id1.getValueType())) {
				// if (id1.getValueType() != id2.getValueType()) {
				throw new UserError(this, 120, new Object[] { id2.getName(), Ontology.VALUE_TYPE_NAMES[id2.getValueType()],
						Ontology.VALUE_TYPE_NAMES[id1.getValueType()] });
			}
		}

		Set<Pair<Integer, Attribute>> excludedAttributes = getExcludedAttributes(es1, es2);

		// regular attributes
		List<AttributeSource> originalAttributeSources = new LinkedList<>();
		List<Attribute> unionAttributeList = new LinkedList<>();
		for (Attribute attribute : es1.getAttributes()) {
			if (!excludedAttributes.contains(new Pair<>(AttributeSource.FIRST_SOURCE, attribute))) {
				originalAttributeSources.add(new AttributeSource(AttributeSource.FIRST_SOURCE, attribute));
				unionAttributeList.add((Attribute) attribute.clone());
			}
		}
		boolean removeDoubleAttributes = getParameterAsBoolean(PARAMETER_REMOVE_DOUBLE_ATTRIBUTES);
		for (Attribute attribute : es2.getAttributes()) {
			if (!excludedAttributes.contains(new Pair<>(AttributeSource.SECOND_SOURCE, attribute))) {
				Attribute cloneAttribute = (Attribute) attribute.clone();
				if (containsAttribute(unionAttributeList, attribute)) { // in list...
					if (!removeDoubleAttributes) { // ... but should not be removed --> rename
						originalAttributeSources.add(new AttributeSource(AttributeSource.SECOND_SOURCE, attribute));
						cloneAttribute.setName(cloneAttribute.getName() + "_from_ES2");
						if (containsAttribute(unionAttributeList, cloneAttribute)) {
							cloneAttribute.setName(cloneAttribute.getName() + "_from_ES2");
						}
						unionAttributeList.add(cloneAttribute);
					} // else do nothing, i.e. remove
				} else { // not in list --> add
					originalAttributeSources.add(new AttributeSource(AttributeSource.SECOND_SOURCE, attribute));
					unionAttributeList.add(cloneAttribute);
				}
			}
		}

		// special attributes
		Map<Attribute, String> unionSpecialAttributes = new LinkedHashMap<>();
		Set<String> usedSpecialAttributes = new HashSet<>();

		// first example set's special attributes
		Iterator<AttributeRole> s = es1.getAttributes().specialAttributes();
		while (s.hasNext()) {
			AttributeRole role = s.next();
			Attribute specialAttribute = role.getAttribute();
			Attribute specialAttributeClone = (Attribute) specialAttribute.clone();

			Iterator<Attribute> ia = unionAttributeList.iterator();
			while (ia.hasNext()) {
				Attribute unionAttribute = ia.next();
				if (unionAttribute.getName().equals(specialAttribute.getName())) {
					ia.remove();
				}
			}

			Iterator<AttributeSource> ias = originalAttributeSources.iterator();
			while (ias.hasNext()) {
				AttributeSource unionAttributeSource = ias.next();
				if (unionAttributeSource.getAttribute().getName().equals(specialAttribute.getName())) {
					ias.remove();
				}
			}

			unionAttributeList.add(specialAttributeClone);
			originalAttributeSources.add(new AttributeSource(AttributeSource.FIRST_SOURCE, specialAttribute));
			unionSpecialAttributes.put(specialAttributeClone, role.getSpecialName());
			usedSpecialAttributes.add(role.getSpecialName());
		}
		// second example set's special attributes
		s = es2.getAttributes().specialAttributes();
		while (s.hasNext()) {
			AttributeRole role = s.next();
			String specialName = role.getSpecialName();
			Attribute specialAttribute = role.getAttribute();
			if (!usedSpecialAttributes.contains(specialName)
					&& !excludedAttributes.contains(new Pair<>(AttributeSource.SECOND_SOURCE, specialAttribute))) { // not
																													 // there
				Attribute specialAttributeClone = (Attribute) specialAttribute.clone();
				boolean addToUnionList = true;
				for (Attribute unionAttribute : unionAttributeList) {
					if (unionAttribute.getName().equals(specialAttribute.getName())) {
						addToUnionList = false;
						break;
					}
				}
				if (addToUnionList) {
					originalAttributeSources.add(new AttributeSource(AttributeSource.SECOND_SOURCE, specialAttribute));
					unionAttributeList.add(specialAttributeClone);
					unionSpecialAttributes.put(specialAttributeClone, specialName);
					usedSpecialAttributes.add(specialName);
				}

			} else {
				if (!isKeyAttribute(role)) {
					logWarning("Special attribute '" + specialName + "' already exist, skipping!");
				}
			}
		}

		// join data
		ExampleSetBuilder unionBuilder = joinData(es1, es2, originalAttributeSources, unionAttributeList);

		// create new example set
		ExampleSet result = unionBuilder.withRoles(unionSpecialAttributes).build();
		result.getAnnotations().addAll(es1.getAnnotations());
		joinOutput.deliver(result);
	}

    /**
     * The method isKeyAttribute can be overwritten by subclasses which are using key attributes, in
     * order to determine if a specific attribute is used as a join key. By default, there is no use
     * of key attributes, so the method returns false.
     *
     * @param attributeRole the attribute role
     * @return the boolean
     * @throws OperatorException the operator exception
     */
    protected boolean isKeyAttribute(AttributeRole attributeRole) throws OperatorException {
		return false;
	}

    /**
     * Returns a list of AttributeMetaData which contains the correctly joined MetaData arising from
     * both input ports.
     *
     * @param emd1 the emd 1
     * @param emd2 the emd 2
     * @return the union attributes meta data
     */
    protected List<AttributeMetaData> getUnionAttributesMetaData(ExampleSetMetaData emd1, ExampleSetMetaData emd2) {
		if (!leftInput.isConnected() || !rightInput.isConnected()) {
			return new LinkedList<>();
		}
		if (this.isIdNeeded()) {
			AttributeMetaData id1 = emd1.getSpecial(Attributes.ID_NAME);
			AttributeMetaData id2 = emd2.getSpecial(Attributes.ID_NAME);

			// sanity checks
			// if (id1 == null) leftInput.addError(new SimpleMetaDataError(Severity.ERROR,
			// leftInput, "missing_id"));
			// if (id2 == null) rightInput.addError(new SimpleMetaDataError(Severity.ERROR,
			// rightInput, "missing_id"));
			if (id1 == null || id2 == null) {
				return new LinkedList<>();
			}
			if (!Ontology.ATTRIBUTE_VALUE_TYPE.isA(id1.getValueType(), id2.getValueType())
					&& !Ontology.ATTRIBUTE_VALUE_TYPE.isA(id2.getValueType(), id1.getValueType())) {
				// this.addError(new SimpleProcessSetupError(Severity.ERROR, getPortOwner(),
				// "attributes_type_mismatch", id1.getName(), "left", id2.getName(), "right"));
				return new LinkedList<>();
			}
		}

		Set<Pair<Integer, AttributeMetaData>> excludedAttributes = new HashSet<>();
		try {
			excludedAttributes = getExcludedAttributesMD(emd1, emd2);
		} catch (OperatorException e) {
			excludedAttributes = Collections.emptySet();
		}

		// adding attributes
		List<AttributeMetaData> unionAttributeList = new LinkedList<>();
		List<String> unionSpecialRoleList = new LinkedList<>();
		for (AttributeMetaData attributeMD : emd1.getAllAttributes()) {
			if (!excludedAttributes.contains(new Pair<>(AttributeSource.FIRST_SOURCE, attributeMD))) {
				unionAttributeList.add(attributeMD.clone());
				if (attributeMD.isSpecial()) {
					unionSpecialRoleList.add(attributeMD.getRole());
				}
			}
		}

		boolean removeDoubleAttributes = getParameterAsBoolean(PARAMETER_REMOVE_DOUBLE_ATTRIBUTES);
		for (AttributeMetaData attributeMD : emd2.getAllAttributes()) {
			if (!excludedAttributes.contains(new Pair<>(AttributeSource.SECOND_SOURCE, attributeMD))) {
				AttributeMetaData cloneAttribute = attributeMD.clone();
				if (containsAttributeMD(unionAttributeList, attributeMD)) { // in list...
					if (!removeDoubleAttributes) { // ... but should not be removed --> rename
						if (attributeMD.isSpecial() && unionSpecialRoleList.contains(attributeMD.getRole())) {
							// this special attribute's role already exists
							rightInput.addError(new SimpleMetaDataError(Severity.WARNING, rightInput,
									"already_contains_role", attributeMD.getRole()));
							continue;
						}
						cloneAttribute.setName(cloneAttribute.getName() + "_from_ES2");
						if (containsAttributeMD(unionAttributeList, cloneAttribute)) {
							cloneAttribute.setName(cloneAttribute.getName() + "_from_ES2");
						}
						unionAttributeList.add(cloneAttribute);
					} // else do nothing, i.e. remove
				} else { // not in list --> add
					if (attributeMD.isSpecial() && unionSpecialRoleList.contains(attributeMD.getRole())) {
						// this special attribute's role already exists
						rightInput.addError(new SimpleMetaDataError(Severity.WARNING, rightInput, "already_contains_role",
								attributeMD.getRole()));
						continue;
					}
					unionAttributeList.add(cloneAttribute);
				}
			}
		}

		// special attributes check
		for (AttributeMetaData attributeMD : unionAttributeList) {
			if (attributeMD.isSpecial()) {

			}
		}

		return unionAttributeList;

	}

    /**
     * Returns a set of original attributes which will not be copied to the output example set. The
     * default implementation returns an empty set.
     *
     * @param es1 the es 1
     * @param es2 the es 2
     * @return the excluded attributes
     * @throws OperatorException the operator exception
     */
    protected Set<Pair<Integer, Attribute>> getExcludedAttributes(ExampleSet es1, ExampleSet es2) throws OperatorException {
		return new HashSet<>();
	}

    /**
     * Returns a set of original attributes which will not be copied to the output example set. The
     * default implementation returns an empty set.
     *
     * @param esm1 the esm 1
     * @param esm2 the esm 2
     * @return the excluded attributes md
     * @throws OperatorException the operator exception
     */
    protected Set<Pair<Integer, AttributeMetaData>> getExcludedAttributesMD(ExampleSetMetaData esm1, ExampleSetMetaData esm2)
			throws OperatorException {
		return new HashSet<>();
	}

    /**
     * Returns true if the list already contains an attribute with the given name. The method
     * contains from List cannot be used since the equals method of Attribute also checks for the
     * same table index which is not applicable here.
     *
     * @param attributeList the attribute list
     * @param attribute     the attribute
     * @return the boolean
     */
    public boolean containsAttribute(List<Attribute> attributeList, Attribute attribute) {
		Iterator<Attribute> i = attributeList.iterator();
		while (i.hasNext()) {
			if (i.next().getName().equals(attribute.getName())) {
				return true;
			}
		}
		return false;
	}

    /**
     * Returns true if the list already contains an attribute with the given name. The method
     * contains from List cannot be used since the equals method of Attribute also checks for the
     * same table index which is not applicable here.
     *
     * @param attributeMDList the attribute md list
     * @param attributeMD     the attribute md
     * @return the boolean
     */
    public boolean containsAttributeMD(List<AttributeMetaData> attributeMDList, AttributeMetaData attributeMD) {
		Iterator<AttributeMetaData> i = attributeMDList.iterator();
		while (i.hasNext()) {
			if (i.next().getName().equals(attributeMD.getName())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public OperatorVersion[] getIncompatibleVersionChanges() {
		return new OperatorVersion[] { VERSION_SWAPPED_INPUT_PORTS };
	}

	@Override
	public List<ParameterType> getParameterTypes() {
		List<ParameterType> types = super.getParameterTypes();
		types.add(new ParameterTypeBoolean(PARAMETER_REMOVE_DOUBLE_ATTRIBUTES,
				"Indicates if double attributes should be removed or renamed", true));
		return types;
	}
}
