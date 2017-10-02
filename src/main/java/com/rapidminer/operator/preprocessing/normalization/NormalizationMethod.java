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
package com.rapidminer.operator.preprocessing.normalization;

import com.rapidminer.example.ExampleSet;
import com.rapidminer.operator.Operator;
import com.rapidminer.operator.OperatorVersion;
import com.rapidminer.operator.UserError;
import com.rapidminer.operator.ports.InputPort;
import com.rapidminer.operator.ports.metadata.AttributeMetaData;
import com.rapidminer.operator.ports.metadata.ExampleSetMetaData;
import com.rapidminer.parameter.ParameterHandler;
import com.rapidminer.parameter.ParameterType;
import com.rapidminer.parameter.UndefinedParameterError;

import java.util.Collection;
import java.util.List;


/**
 * This is an interface for all Normalization methods. Each normalization method needs to have an
 * empty constructor.
 *
 * @author Sebastian Land
 */
public interface NormalizationMethod {

    /**
     * This modifies the meta data of the given attribute and returns a collection of all derived
     * attributes. In normal cases this is simply one single attribute.
     *
     * @param emd                 the emd
     * @param amd                 the amd
     * @param exampleSetInputPort the example set input port
     * @param parameterHandler    TODO
     * @return the collection
     * @throws UndefinedParameterError the undefined parameter error
     */
    public Collection<AttributeMetaData> modifyAttributeMetaData(ExampleSetMetaData emd, AttributeMetaData amd,
			InputPort exampleSetInputPort, ParameterHandler parameterHandler) throws UndefinedParameterError;

    /**
     * This method can be used to clear all member types right before the normalization model is
     * retrieved.
     */
    public void init();

    /**
     * This method will be called to build the normalization model from the given {@link ExampleSet}
     * . It will be called directly after init() is called.
     *
     * @param exampleSet the example set
     * @param operator   TODO
     * @return the normalization model
     * @throws UserError the user error
     */
    public AbstractNormalizationModel getNormalizationModel(ExampleSet exampleSet, Operator operator) throws UserError;

    /**
     * If this method needs additional parameter types, they can be returned here.
     *
     * @param handler the handler
     * @return the parameter types
     */
    public List<ParameterType> getParameterTypes(ParameterHandler handler);

    /**
     * Returns the versions of a NormalizationMethod <strong>after which its behavior incompatibly
     * changed</strong> in random order. Only the versions after which the new behavior was
     * introduced are returned. See comment of {@link OperatorVersion} for details.
     *
     * @return the operator version [ ]
     * @see Operator#getIncompatibleVersionChanges() Operator#getIncompatibleVersionChanges()Operator#getIncompatibleVersionChanges()
     * @since 7.6
     */
    default public OperatorVersion[] getIncompatibleVersionChanges() {
		return Operator.EMPTY_OPERATOR_VERSIONS_ARRAY;
	}

    /**
     * This just returns the name of the method.
     *
     * @return the name
     */
    public String getName();
}
