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
package com.rapidminer.repository.local;

import com.rapidminer.io.process.XMLTools;
import com.rapidminer.repository.*;
import com.rapidminer.repository.gui.LocalRepositoryPanel;
import com.rapidminer.repository.gui.RepositoryConfigurationPanel;
import com.rapidminer.tools.FileSystemService;
import com.rapidminer.tools.I18N;
import com.rapidminer.tools.XMLException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.swing.event.EventListenerList;
import java.io.File;


/**
 * A repository backed by the local file system. Each entry is backed by one or more files.
 *
 * @author Simon Fischer
 */
public class LocalRepository extends SimpleFolder implements Repository {

	private final EventListenerList listeners = new EventListenerList();

	private File root;

    /**
     * The enum Local state.
     */
    public enum LocalState {
        /**
         * Accessible local state.
         */
        ACCESSIBLE(null), /**
         * Not accessible local state.
         */
        NOT_ACCESSIBLE(I18N.getMessage(I18N.getGUIBundle(), "gui.repository.not_accessible.message"));

		private String state;

		private LocalState(String state) {
			this.state = state;
		}

		@Override
		public String toString() {
			return state;
		}

        /**
         * Value of string.
         *
         * @param state the state
         * @return the string
         */
        public static String valueOf(LocalState state) {
			return state.toString();
		}
	}

    /**
     * Creates a file-based repository in a default location.
     *
     * @param name the name
     * @throws RepositoryException the repository exception
     * @see #getDefaultRepositoryFolder(String) #getDefaultRepositoryFolder(String)#getDefaultRepositoryFolder(String)
     */
    public LocalRepository(String name) throws RepositoryException {
		this(name, getDefaultRepositoryFolder(name));
	}

    /**
     * Creates a file-based repository in the given location.  @param name the name
     *
     * @param name the name
     * @param root the root
     * @throws RepositoryException the repository exception
     */
    public LocalRepository(String name, File root) throws RepositoryException {
		super(name, null, null);
		this.root = root;
		mkdir();
		if (!root.isDirectory()) {
			throw new RepositoryException("Folder '" + root + "' is not a directory.");
		}
		if (!root.canWrite()) {
			throw new RepositoryException("Folder '" + root + "' is not writable.");
		}
		setRepository(this);
	}

    /**
     * Gets root.
     *
     * @return the root
     */
    public File getRoot() {
		return this.root;
	}

	@Override
	public boolean rename(String newName) {
		setName(newName);
		fireEntryRenamed(this);
		return true;
	}

	@Override
	public File getFile() {
		return getRoot();
	}

    /**
     * Sets root.
     *
     * @param root the root
     */
    public void setRoot(File root) {
		this.root = root;
	}

	@Override
	public void addRepositoryListener(RepositoryListener l) {
		listeners.add(RepositoryListener.class, l);
	}

	@Override
	public void removeRepositoryListener(RepositoryListener l) {
		listeners.remove(RepositoryListener.class, l);
	}

    /**
     * Fire entry renamed.
     *
     * @param entry the entry
     */
    protected void fireEntryRenamed(final Entry entry) {
		for (RepositoryListener l : listeners.getListeners(RepositoryListener.class)) {
			l.entryChanged(entry);
		}

	}

    /**
     * Fire entry added.
     *
     * @param newEntry the new entry
     * @param parent   the parent
     */
    protected void fireEntryAdded(final Entry newEntry, final Folder parent) {
		for (RepositoryListener l : listeners.getListeners(RepositoryListener.class)) {
			l.entryAdded(newEntry, parent);
		}
	}

    /**
     * Fire refreshed.
     *
     * @param folder the folder
     */
    public void fireRefreshed(final Folder folder) {
		for (RepositoryListener l : listeners.getListeners(RepositoryListener.class)) {
			l.folderRefreshed(folder);
		}
	}

    /**
     * Fire entry removed.
     *
     * @param removedEntry the removed entry
     * @param parent       the parent
     * @param index        the index
     */
    protected void fireEntryRemoved(final Entry removedEntry, final Folder parent, final int index) {
		for (RepositoryListener l : listeners.getListeners(RepositoryListener.class)) {
			l.entryRemoved(removedEntry, parent, index);
		}
	}

	@Override
	public String getDescription() {
		return "This is a local repository stored on your local computer at " + getFile() + ".";
	}

	@Override
	public Entry locate(String entry) throws RepositoryException {
		return RepositoryManager.getInstance(null).locate(this, entry, false);
	}

	@Override
	public RepositoryLocation getLocation() {
		try {
			return new RepositoryLocation(getName(), new String[0]);
		} catch (MalformedRepositoryLocationException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String getState() {
		if (getRoot() == null) {
			return null;
		}
		return getRoot().exists() ? LocalState.valueOf(LocalState.ACCESSIBLE) : LocalState
				.valueOf(LocalState.NOT_ACCESSIBLE);
	}

	@Override
	public String getIconName() {
		return I18N.getMessage(I18N.getGUIBundle(), "gui.repository.local.icon");
	}

	@Override
	public Element createXML(Document doc) {
		Element repositoryElement = doc.createElement("localRepository");

		Element file = doc.createElement("file");
		file.appendChild(doc.createTextNode(this.root.getAbsolutePath()));
		repositoryElement.appendChild(file);

		Element name = doc.createElement("alias");
		name.appendChild(doc.createTextNode(this.getName()));
		repositoryElement.appendChild(name);

		return repositoryElement;
	}

    /**
     * From xml local repository.
     *
     * @param element the element
     * @return the local repository
     * @throws XMLException        the xml exception
     * @throws RepositoryException the repository exception
     */
    public static LocalRepository fromXML(Element element) throws XMLException, RepositoryException {
		return new LocalRepository(XMLTools.getTagContents(element, "alias", true), new File(XMLTools.getTagContents(
				element, "file", true)));
	}

	@Override
	public void delete() {
		RepositoryManager.getInstance(null).removeRepository(this);
	}

	@Override
	public boolean shouldSave() {
		return true;
	}

	@Override
	public void postInstall() {}

	@Override
	public void preRemove() {}

	@Override
	public boolean isConfigurable() {
		return true;
	}

	@Override
	public RepositoryConfigurationPanel makeConfigurationPanel() {
		return new LocalRepositoryPanel(null, false);
	}

	/**
	 * Returns the folder which, by default, contains RM repositories, e.g. .RapidMiner/repositories
	 */
	private static File getDefaultRepositoryContainerFolder() {
		File dir = FileSystemService.getUserConfigFile("repositories");
		dir.mkdir();
		return dir;
	}

    /**
     * Returns the default folder in which a repository with this alias would be stored.  @param forAlias the for alias
     *
     * @param forAlias the for alias
     * @return the default repository folder
     */
    public static final File getDefaultRepositoryFolder(String forAlias) {
		return new File(getDefaultRepositoryContainerFolder(), forAlias);
	}

}
