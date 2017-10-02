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
package com.rapidminer.operator.ports;

import java.util.List;

import com.rapidminer.operator.IOObject;
import com.rapidminer.operator.ResultObject;
import com.rapidminer.operator.ports.metadata.MDTransformationRule;
import com.rapidminer.operator.ports.metadata.MetaData;
import com.rapidminer.operator.ports.metadata.OneToManyPassThroughRule;


/**
 * Does the same as its superclass but provides also a method to generate a meta data transformation
 * rule that copies from an input to all generated output ports.
 *
 * @author Simon Fischer
 */
public class OutputPortExtender extends SinglePortExtender<OutputPort> {

    /**
     * Instantiates a new Output port extender.
     *
     * @param name  the name
     * @param ports the ports
     */
    public OutputPortExtender(String name, Ports<OutputPort> ports) {
		super(name, ports);
	}

    /**
     * The generated rule copies all meta data from the input port to all generated output ports.  @param inputPort the input port
     *
     * @param inputPort the input port
     * @return the md transformation rule
     */
    public MDTransformationRule makePassThroughRule(InputPort inputPort) {
		return new OneToManyPassThroughRule(inputPort, getManagedPorts());
	}

    /**
     * Deliver to all.
     *
     * @param data  the data
     * @param clone the clone
     */
    public void deliverToAll(IOObject data, boolean clone) {
		for (OutputPort port : getManagedPorts()) {
			if (clone) {
				port.deliver(data.copy());
			} else {
				port.deliver(data);
			}
		}
	}

    /**
     * Deliver.
     *
     * @param inputs the inputs
     */
    public void deliver(List<? extends IOObject> inputs) {
		int i = 0;
		for (OutputPort port : getManagedPorts()) {
			if (port.isConnected()) {
				if (i >= inputs.size()) {
					getPorts().getOwner().getOperator().getLogger().fine("Insufficient input for " + port.getSpec());
				} else {
					IOObject input = inputs.get(i);
					port.deliver(input);
					if (input != null) {
						String name;
						if (input instanceof ResultObject) {
							name = ((ResultObject) input).getName();
						} else {
							name = input.getClass().getName();
						}
						if (input.getSource() != null) {
							name += " (" + input.getSource() + ")";
						}
						getPorts().getOwner().getOperator().getLogger().fine("Delivering " + name + " to " + port.getSpec());
					}
				}
				i++;
			}
		}
	}

    /**
     * Deliver meta data.
     *
     * @param inputMD the input md
     */
    public void deliverMetaData(List<MetaData> inputMD) {
		int i = 0;
		for (OutputPort port : getManagedPorts()) {
			if (port.isConnected()) {
				if (i < inputMD.size()) {
					port.deliverMD(inputMD.get(i));
				}
				i++;
			}
		}
	}

}
