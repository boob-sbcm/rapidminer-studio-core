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


/**
 * Delivers the union of two example sets to an output port. If a prefix is specified, duplicate
 * names will be renamed. Otherwise, duplicates are skipped.
 *
 * @author Simon Fischer
 */
public class ExampleSetUnionRule implements MDTransformationRule {

	private final InputPort inputPort1;
	private final InputPort inputPort2;
	private final OutputPort outputPort;
	private final String prefixForDuplicates;

    /**
     * Instantiates a new Example set union rule.
     *
     * @param inputPort1          the input port 1
     * @param inputPort2          the input port 2
     * @param outputPort          the output port
     * @param prefixForDuplicates the prefix for duplicates
     */
    public ExampleSetUnionRule(InputPort inputPort1, InputPort inputPort2, OutputPort outputPort, String prefixForDuplicates) {
		this.inputPort1 = inputPort1;
		this.inputPort2 = inputPort2;
		this.outputPort = outputPort;
		this.prefixForDuplicates = prefixForDuplicates;
	}

    /**
     * Gets prefix.
     *
     * @return the prefix
     */
    protected String getPrefix() {
		return prefixForDuplicates;
	}

	@Override
	public void transformMD() {
		MetaData md1 = inputPort1.getMetaData();
		MetaData md2 = inputPort2.getMetaData();
		if ((md1 != null) && (md2 != null)) {
			if ((md1 instanceof ExampleSetMetaData) && (md2 instanceof ExampleSetMetaData)) {
				ExampleSetMetaData emd1 = (ExampleSetMetaData) md1;
				ExampleSetMetaData emd2 = (ExampleSetMetaData) md2;
				ExampleSetMetaData joinedEmd = modifyMetaData(emd1, emd2);
				outputPort.deliverMD(joinedEmd);
			} else {
				outputPort.deliverMD(new ExampleSetMetaData());
			}
		} else {
			outputPort.deliverMD(null);
		}
	}

    /**
     * Transform added attribute md.
     *
     * @param emd          the emd
     * @param newAttribute the new attribute
     */
    protected void transformAddedAttributeMD(ExampleSetMetaData emd, AttributeMetaData newAttribute) {

	}

    /**
     * Modify meta data example set meta data.
     *
     * @param leftEMD  the left emd
     * @param rightEMD the right emd
     * @return the example set meta data
     */
    protected ExampleSetMetaData modifyMetaData(ExampleSetMetaData leftEMD, ExampleSetMetaData rightEMD) {
		// Just merge the left and right input together
		ExampleSetMetaData mergedEmd = new ExampleSetMetaData();
		mergedEmd.addAllAttributes(leftEMD.getAllAttributes());
		mergedEmd.addAllAttributes(rightEMD.getAllAttributes());
		for (AttributeMetaData possibleNew : rightEMD.getAllAttributes()) {
			if (leftEMD.containsAttributeName(possibleNew.getName()) != MetaDataInfo.YES) {
				transformAddedAttributeMD(mergedEmd, mergedEmd.getAttributeByName(possibleNew.getName()));
			}
		}
		return mergedEmd;
	}

}
