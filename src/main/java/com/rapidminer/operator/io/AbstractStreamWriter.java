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
package com.rapidminer.operator.io;

import com.rapidminer.example.ExampleSet;
import com.rapidminer.operator.OperatorDescription;
import com.rapidminer.operator.OperatorException;
import com.rapidminer.operator.UserError;
import com.rapidminer.operator.nio.file.FileObject;
import com.rapidminer.operator.nio.file.FileOutputPortHandler;
import com.rapidminer.operator.ports.OutputPort;
import com.rapidminer.operator.ports.Port;
import com.rapidminer.parameter.ParameterType;
import com.rapidminer.parameter.ParameterTypeFile;
import com.rapidminer.parameter.PortProvider;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


/**
 * Abstract super type of stream writing operators.
 *
 * @author Dominik Halfkann
 */
public abstract class AbstractStreamWriter extends AbstractWriter<ExampleSet> {

    /**
     * The File output port.
     */
    protected OutputPort fileOutputPort = getOutputPorts().createPort("file");
	private FileOutputPortHandler filePortHandler = new FileOutputPortHandler(this, fileOutputPort, getFileParameterName());

    /**
     * Should append boolean.
     *
     * @return the boolean
     */
    protected boolean shouldAppend() {
		return false;
	}

    /**
     * Instantiates a new Abstract stream writer.
     *
     * @param description the description
     */
    public AbstractStreamWriter(OperatorDescription description) {
		super(description, ExampleSet.class);
		getTransformer().addGenerationRule(fileOutputPort, FileObject.class);
	}

	@Override
	public boolean shouldAutoConnect(OutputPort outputPort) {
		if (outputPort == fileOutputPort) {
			return false;
		} else {
			return super.shouldAutoConnect(outputPort);
		}
	}

	@Override
	public ExampleSet write(ExampleSet exampleSet) throws OperatorException {

		OutputStream outputStream = null;
		try {
			if (shouldAppend()) {
				outputStream = filePortHandler.openSelectedFile(true);
			} else {
				outputStream = filePortHandler.openSelectedFile();
			}
			writeStream(exampleSet, outputStream);

		} finally {
			try {
				if (outputStream != null) {
					outputStream.close();
				}
			} catch (IOException e) {
				if (outputStream instanceof FileOutputStream) {
					throw new UserError(this, e, 322, getParameterAsFile(getFileParameterName()), "");
				} else if (outputStream instanceof ByteArrayOutputStream) {
					throw new UserError(this, e, 322, "output stream", "");
				} else {
					throw new UserError(this, e, 322, "unknown file or stream", "");
				}
			}
		}

		return exampleSet;
	}

    /**
     * Creates (but does not add) the file parameter named by {@link #getFileParameterName()} that
     * depends on whether or not {@link #fileOutputPort} is connected.
     *
     * @return the parameter type
     */
    protected ParameterType makeFileParameterType() {
		return FileOutputPortHandler.makeFileParameterType(this, getFileParameterName(), new PortProvider() {

			@Override
			public Port getPort() {
				return fileOutputPort;
			}
		}, getFileExtensions());
	}

    /**
     * Writes data to an OutputStream in a format which is defined in the subclass.
     *
     * @param exampleSet   the example set
     * @param outputStream the output stream
     * @throws OperatorException the operator exception
     */
    protected abstract void writeStream(ExampleSet exampleSet, OutputStream outputStream) throws OperatorException;

    /**
     * Returns the name of the {@link ParameterTypeFile} to be added through which the user can
     * specify the file name.
     *
     * @return the file parameter name
     */
    protected abstract String getFileParameterName();

    /**
     * Returns the allowed file extension.  @return the string [ ]
     *
     * @return the string [ ]
     */
    protected abstract String[] getFileExtensions();

}
