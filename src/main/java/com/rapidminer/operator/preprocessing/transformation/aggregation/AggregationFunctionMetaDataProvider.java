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
package com.rapidminer.operator.preprocessing.transformation.aggregation;

import com.rapidminer.operator.ports.InputPort;
import com.rapidminer.operator.ports.metadata.AttributeMetaData;


/**
 * This is the interface for all classes, that implement the delivery of meta data for certain
 * {@link AggregationFunction}s.
 *
 * @author Sebastian Land
 */
public interface AggregationFunctionMetaDataProvider {

    /**
     * This method must return an {@link AttributeMetaData} for the target attribute, or null if the
     * source attribute's valuetype is incompatible. Then an error should be registered on the
     * inputport unless the port is null. In cases, where the sourceAttribute is unknown during the
     * aggregation, only a warning should be registered in the port, but it should be assumed, that
     * the attribute will be present.
     *
     * @param sourceAttribute the source attribute
     * @param port            the port
     * @return the target attribute meta data
     */
    public AttributeMetaData getTargetAttributeMetaData(AttributeMetaData sourceAttribute, InputPort port);
}
