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

import java.util.EventListener;


/**
 * A listener listening to changes of a repository.
 *
 * @author Simon Fischer
 */
public interface RepositoryListener extends EventListener {

    /**
     * Entry added.
     *
     * @param newEntry the new entry
     * @param parent   the parent
     */
    public void entryAdded(Entry newEntry, Folder parent);

    /**
     * Entry changed.
     *
     * @param entry the entry
     */
    public void entryChanged(Entry entry);

    /**
     * Entry removed.
     *
     * @param removedEntry the removed entry
     * @param parent       the parent
     * @param oldIndex     the old index
     */
    public void entryRemoved(Entry removedEntry, Folder parent, int oldIndex);

    /**
     * Folder refreshed.
     *
     * @param folder the folder
     */
    public void folderRefreshed(Folder folder);

}
