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
package com.rapidminer.operator.preprocessing.filter;

import com.rapidminer.example.Attribute;
import com.rapidminer.example.AttributeRole;
import com.rapidminer.example.Example;
import com.rapidminer.example.ExampleSet;
import com.rapidminer.example.table.AttributeFactory;
import com.rapidminer.operator.OperatorDescription;
import com.rapidminer.operator.OperatorException;
import com.rapidminer.operator.annotation.ResourceConsumptionEstimator;
import com.rapidminer.operator.error.AttributeNotFoundError;
import com.rapidminer.operator.ports.metadata.*;
import com.rapidminer.parameter.*;
import com.rapidminer.tools.Ontology;
import com.rapidminer.tools.OperatorResourceConsumptionHandler;

import java.util.List;


/**
 * This operator transforms a {@link Ontology#NUMERICAL} attribute into a {@link Ontology#DATE_TIME}
 * attribute. It take the numerical value as the milliseconds since 1.1.1970 00:00:00:00 and adds
 * the specified offset
 *
 * @author Sebastian Loh
 */
public class Numerical2Date extends AbstractDateDataProcessing {

    /**
     * The constant PARAMETER_ATTRIBUTE_NAME.
     */
    public static final String PARAMETER_ATTRIBUTE_NAME = "attribute_name";

    /**
     * The constant PARAMETER_KEEP_OLD_ATTRIBUTE.
     */
    public static final String PARAMETER_KEEP_OLD_ATTRIBUTE = "keep_old_attribute";

    /**
     * The constant PARMETER_TIME_OFFSET.
     */
    public static final String PARMETER_TIME_OFFSET = "time_offset";

    /**
     * Instantiates a new Numerical 2 date.
     *
     * @param description the description
     */
    public Numerical2Date(OperatorDescription description) {
		super(description);

		getExampleSetInputPort().addPrecondition(
				new AttributeSetPrecondition(getExampleSetInputPort(), AttributeSetPrecondition.getAttributesByParameter(
						this, PARAMETER_ATTRIBUTE_NAME)));
	}

	@Override
	protected MetaData modifyMetaData(ExampleSetMetaData metaData) throws UndefinedParameterError {
		AttributeMetaData amd = metaData.getAttributeByName(getParameterAsString(PARAMETER_ATTRIBUTE_NAME));
		if (amd != null) {
			AttributeMetaData newAttribute = amd.clone();
			newAttribute.setType(Ontology.DATE_TIME);
			newAttribute.getMean().setUnkown();
			newAttribute.setValueSetRelation(SetRelation.UNKNOWN);
			if (!getParameterAsBoolean(PARAMETER_KEEP_OLD_ATTRIBUTE)) {
				metaData.removeAttribute(amd);
			} else {
				newAttribute.setName(newAttribute.getName() + "_AS_DATE");
			}
			metaData.addAttribute(newAttribute);
		}
		return metaData;
	}

	@Override
	public ExampleSet apply(ExampleSet exampleSet) throws OperatorException {
		String attributeName = getParameterAsString(PARAMETER_ATTRIBUTE_NAME);
		Long offset = getParameterAsLong(PARMETER_TIME_OFFSET);

		Attribute numericalAttribute = exampleSet.getAttributes().get(attributeName);
		if (numericalAttribute == null) {
			throw new AttributeNotFoundError(this, PARAMETER_ATTRIBUTE_NAME, attributeName);
		}

		Attribute newAttribute = AttributeFactory.createAttribute(Ontology.DATE_TIME);
		exampleSet.getExampleTable().addAttribute(newAttribute);
		exampleSet.getAttributes().addRegular(newAttribute);

		for (Example example : exampleSet) {
			double value = example.getValue(numericalAttribute);
			if (Double.isNaN(value)) {
				example.setValue(newAttribute, value);
			} else {
				value += offset;
				example.setValue(newAttribute, value);
			}
		}

		if (!getParameterAsBoolean(PARAMETER_KEEP_OLD_ATTRIBUTE)) {
			AttributeRole oldRole = exampleSet.getAttributes().getRole(numericalAttribute);
			exampleSet.getAttributes().remove(numericalAttribute);
			newAttribute.setName(attributeName);
			exampleSet.getAttributes().setSpecialAttribute(newAttribute, oldRole.getSpecialName());
		} else {
			newAttribute.setName(attributeName + "_AS_DATE");
		}
		return exampleSet;
	}

	@Override
	public List<ParameterType> getParameterTypes() {
		List<ParameterType> types = super.getParameterTypes();
		types.add(new ParameterTypeAttribute(PARAMETER_ATTRIBUTE_NAME, "The attribute which should be transformed.",
				getExampleSetInputPort(), false, false, Ontology.NUMERICAL));
		types.add(new ParameterTypeBoolean(PARAMETER_KEEP_OLD_ATTRIBUTE,
				"Indicates if the original numerical attribute should be kept.", false));
		types.add(new ParameterTypeLong(PARMETER_TIME_OFFSET, "Time offset in milliseconds", Long.MIN_VALUE, Long.MAX_VALUE,
				0, true));
		return types;
	}

	@Override
	public boolean writesIntoExistingData() {
		return false;
	}

	@Override
	public ResourceConsumptionEstimator getResourceConsumptionEstimator() {
		return OperatorResourceConsumptionHandler
				.getResourceConsumptionEstimator(getInputPort(), Date2Numerical.class, null);
	}
}
