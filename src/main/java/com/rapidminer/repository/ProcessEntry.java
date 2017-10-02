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
package com.rapidminer.repository;


/**
 * An entry that can store processes.
 *
 * @author Simon Fischer
 */
public interface ProcessEntry extends DataEntry {

    /**
     * The constant TYPE_NAME.
     */
    public static final String TYPE_NAME = "process";

    /**
     * Retrieve xml string.
     *
     * @return the string
     * @throws RepositoryException the repository exception
     */
    public String retrieveXML() throws RepositoryException;

    /**
     * Store xml.
     *
     * @param xml the xml
     * @throws RepositoryException the repository exception
     */
    public void storeXML(String xml) throws RepositoryException;

}
