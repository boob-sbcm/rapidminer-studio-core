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
package com.rapidminer.gui.wizards;

import com.rapidminer.Process;
import com.rapidminer.parameter.Parameters;


/**
 * Classes implementing this interface should be able to listen to parameter changes indicated by a
 * given XML string.
 *
 * @author Ingo Mierswa
 */
public interface ConfigurationListener {

    /**
     * Gets parameters.
     *
     * @return the parameters
     */
    public Parameters getParameters();

    /**
     * Sets parameters.
     *
     * @param parameters the parameters
     */
    public void setParameters(Parameters parameters);

    /**
     * Gets process.
     *
     * @return the process
     */
    public Process getProcess();

}
