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
package com.rapidminer.gui.actions.export;

import java.io.IOException;


/**
 * Exception that will be thrown if an image export via the {@link ImageExporter} fails.
 *
 * @author Nils Woehler
 */
public class ImageExportException extends Exception {

	private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new Image export exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public ImageExportException(String message, Throwable cause) {
		super(message, cause);
	}

    /**
     * Instantiates a new Image export exception.
     *
     * @param message the message
     */
    public ImageExportException(String message) {
		super(message);
	}

    /**
     * Instantiates a new Image export exception.
     *
     * @param e the e
     */
    public ImageExportException(IOException e) {
		super(e);
	}

}
