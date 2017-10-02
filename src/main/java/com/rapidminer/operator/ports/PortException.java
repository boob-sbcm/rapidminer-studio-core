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

import java.awt.Component;
import java.awt.Point;

import javax.swing.JOptionPane;


/**
 * The type Port exception.
 *
 * @author Simon Fischer
 */
public class PortException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3144885811799953716L;

    /**
     * Instantiates a new Port exception.
     *
     * @param message the message
     */
    public PortException(String message) {
		super(message);
	}

    /**
     * Instantiates a new Port exception.
     *
     * @param port    the port
     * @param message the message
     */
    public PortException(Port port, String message) {
		super("Exception at " + port.getSpec() + ": " + message);
	}

    /**
     * Has repair options boolean.
     *
     * @return the boolean
     */
    public boolean hasRepairOptions() {
		return false;
	}

    /**
     * Show repair popup.
     *
     * @param parent        the parent
     * @param popupLocation the popup location
     */
    public void showRepairPopup(Component parent, Point popupLocation) {
		JOptionPane.showMessageDialog(parent, getMessage(), "Port exception", JOptionPane.ERROR_MESSAGE);
	}
}
