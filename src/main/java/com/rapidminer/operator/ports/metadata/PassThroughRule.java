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
 * Assigns meta data received from an input port to an output port. Useful e.g. for preprocessing
 * operators. If meta data changes dynamically, can be modified by overriding
 * {@link #modifyMetaData(MetaData)}.
 *
 * @author Simon Fischer
 */
public class PassThroughRule implements MDTransformationRule {

	private final OutputPort outputPort;

	private final InputPort inputPort;
	private final boolean optional;

    /**
     * Instantiates a new Pass through rule.
     *
     * @param inputPort  the input port
     * @param outputPort the output port
     * @param mandatory  the mandatory
     */
    public PassThroughRule(InputPort inputPort, OutputPort outputPort, boolean mandatory) {
		super();
		this.outputPort = outputPort;
		this.inputPort = inputPort;
		this.optional = !mandatory;
	}

	@Override
	public void transformMD() {
		MetaData modified = inputPort.getMetaData();
		if (modified == null) {
			if (!optional) {
				inputPort.addError(new InputMissingMetaDataError(inputPort, null, null));
			}
			outputPort.deliverMD(null);
		} else {
			modified = modified.clone();
			modified.addToHistory(outputPort);
			outputPort.deliverMD(modifyMetaData(modified));
		}
	}

    /**
     * Modifies the received meta data before it is passed to the output. Can be used if the
     * transformation depends on parameters etc. The default implementation just returns the
     * original. Subclasses may safely modify the meta data, since a copy is used for this method.
     *
     * @param unmodifiedMetaData the unmodified meta data
     * @return the meta data
     */
    public MetaData modifyMetaData(MetaData unmodifiedMetaData) {
		return unmodifiedMetaData;
	}

    /**
     * Gets output port.
     *
     * @return the output port
     */
    public OutputPort getOutputPort() {
		return this.outputPort;
	}

    /**
     * Gets input port.
     *
     * @return the input port
     */
    public InputPort getInputPort() {
		return this.inputPort;
	}

    /**
     * Is optional boolean.
     *
     * @return the boolean
     */
    public boolean isOptional() {
		return this.optional;
	}

}
