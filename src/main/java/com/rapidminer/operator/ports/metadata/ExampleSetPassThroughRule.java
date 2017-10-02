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

import com.rapidminer.operator.ports.InputPort;
import com.rapidminer.operator.ports.OutputPort;
import com.rapidminer.parameter.UndefinedParameterError;


/**
 * The type Example set pass through rule.
 *
 * @author Simon Fischer
 */
public class ExampleSetPassThroughRule extends PassThroughRule {

    /**
     * The Relation.
     */
    SetRelation relation;

    /**
     * Instantiates a new Example set pass through rule.
     *
     * @param inputPort            the input port
     * @param outputPort           the output port
     * @param attributeSetRelation the attribute set relation
     */
    public ExampleSetPassThroughRule(InputPort inputPort, OutputPort outputPort, SetRelation attributeSetRelation) {
		super(inputPort, outputPort, false);
		this.relation = attributeSetRelation;
	}

	@Override
	public MetaData modifyMetaData(MetaData metaData) {
		if (metaData instanceof ExampleSetMetaData) {
			ExampleSetMetaData emd = (ExampleSetMetaData) metaData;
			if (relation != null) {
				emd.mergeSetRelation(relation);
			}
			try {
				return modifyExampleSet(emd);
			} catch (UndefinedParameterError e) {
				return emd;
			}

		} else {
			return metaData;
		}
	}

    /**
     * This method might be used for convenience for slight modifications of the exampleSet like
     * adding an attribute. Subclasses might override this method.
     *
     * @param metaData the meta data
     * @return the example set meta data
     * @throws UndefinedParameterError the undefined parameter error
     */
    public ExampleSetMetaData modifyExampleSet(ExampleSetMetaData metaData) throws UndefinedParameterError {
		return metaData;
	}

}
