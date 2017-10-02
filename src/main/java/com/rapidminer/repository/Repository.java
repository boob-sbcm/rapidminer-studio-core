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

import com.rapidminer.repository.gui.RepositoryConfigurationPanel;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


/**
 * The interface Repository.
 *
 * @author Simon Fischer
 */
public interface Repository extends Folder {

    /**
     * Add repository listener.
     *
     * @param l the l
     */
    public void addRepositoryListener(RepositoryListener l);

    /**
     * Remove repository listener.
     *
     * @param l the l
     */
    public void removeRepositoryListener(RepositoryListener l);

    /**
     * This will return the entry if existing or null if it can't be found.
     *
     * @param string the string
     * @return the entry
     * @throws RepositoryException the repository exception
     */
    public Entry locate(String string) throws RepositoryException;

    /**
     * Returns some user readable information about the state of this repository.  @return the state
     *
     * @return the state
     */
    public String getState();

    /**
     * Returns the icon name for the repository.  @return the icon name
     *
     * @return the icon name
     */
    public String getIconName();

    /**
     * Returns a piece of XML to store the repository in a configuration file.  @param doc the doc
     *
     * @param doc the doc
     * @return the element
     */
    public Element createXML(Document doc);

    /**
     * Should save boolean.
     *
     * @return the boolean
     */
    public abstract boolean shouldSave();

    /**
     * Called after the repository is added. Currently unused, but may be useful. Was once used to
     * fetch JDBC connection entries from remote server.
     */
    public void postInstall();

    /**
     * Pre remove.
     */
    public void preRemove();

    /**
     * Returns true if the repository is configurable. In that case,  @return the boolean
     *
     * @return the boolean
     */
    public boolean isConfigurable();

    /**
     * Creates a configuration panel.  @return the repository configuration panel
     *
     * @return the repository configuration panel
     */
    public RepositoryConfigurationPanel makeConfigurationPanel();
}
