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

import com.rapidminer.operator.IOObject;
import com.rapidminer.operator.Operator;
import com.rapidminer.tools.ProgressListener;

import java.util.List;


/**
 * An entry containing sub-entries.
 *
 * @author Simon Fischer
 */
public interface Folder extends Entry {

    /**
     * The constant TYPE_NAME.
     */
    public static final String TYPE_NAME = "folder";

    /**
     * Gets data entries.
     *
     * @return the data entries
     * @throws RepositoryException the repository exception
     */
    public List<DataEntry> getDataEntries() throws RepositoryException;

    /**
     * Gets subfolders.
     *
     * @return the subfolders
     * @throws RepositoryException the repository exception
     */
    public List<Folder> getSubfolders() throws RepositoryException;

    /**
     * Refresh.
     *
     * @throws RepositoryException the repository exception
     */
    public void refresh() throws RepositoryException;

    /**
     * Contains entry boolean.
     *
     * @param name the name
     * @return the boolean
     * @throws RepositoryException the repository exception
     */
    public boolean containsEntry(String name) throws RepositoryException;

    /**
     * Create folder folder.
     *
     * @param name the name
     * @return the folder
     * @throws RepositoryException the repository exception
     */
    public Folder createFolder(String name) throws RepositoryException;

    /**
     * Create io object entry io object entry.
     *
     * @param name             the name
     * @param ioobject         the ioobject
     * @param callingOperator  the calling operator
     * @param progressListener the progress listener
     * @return the io object entry
     * @throws RepositoryException the repository exception
     */
    public IOObjectEntry createIOObjectEntry(String name, IOObject ioobject, Operator callingOperator,
			ProgressListener progressListener) throws RepositoryException;

    /**
     * Create process entry process entry.
     *
     * @param name       the name
     * @param processXML the process xml
     * @return the process entry
     * @throws RepositoryException the repository exception
     */
    public ProcessEntry createProcessEntry(String name, String processXML) throws RepositoryException;

    /**
     * Create blob entry blob entry.
     *
     * @param name the name
     * @return the blob entry
     * @throws RepositoryException the repository exception
     */
    public BlobEntry createBlobEntry(String name) throws RepositoryException;

    /**
     * Returns true iff a child with the given name exists and a {@link #refresh()} would find this
     * entry (or it is already loaded).
     *
     * @param childName the child name
     * @return the boolean
     * @throws RepositoryException the repository exception
     */
    public boolean canRefreshChild(String childName) throws RepositoryException;
}
