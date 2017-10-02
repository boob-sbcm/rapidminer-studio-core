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
package com.rapidminer.parameter;

/**
 * A parameter type for specifying a repository location.
 *
 * @author Simon Fischer, Sebastian Land
 */
public class ParameterTypeRepositoryLocation extends ParameterTypeString {

	private static final long serialVersionUID = 1L;

	private boolean allowFolders, allowEntries, allowAbsoluteEntries, enforceValidRepositoryEntryName,
			onlyWriteableLocations;

    /**
     * Creates a new parameter type for files with the given extension. If the extension is null no
     * file filters will be used.
     *
     * @param key         the key
     * @param description the description
     * @param optional    the optional
     */
    public ParameterTypeRepositoryLocation(String key, String description, boolean optional) {
		this(key, description, true, false, optional);
	}

    /**
     * Creates a new parameter type for files with the given extension. If the extension is null no
     * file filters will be used.
     *
     * @param key              the key
     * @param description      the description
     * @param allowEntries     the allow entries
     * @param allowDirectories the allow directories
     * @param optional         the optional
     */
    public ParameterTypeRepositoryLocation(String key, String description, boolean allowEntries, boolean allowDirectories,
			boolean optional) {
		this(key, description, allowEntries, allowDirectories, false, optional, false, false);
	}

    /**
     * Instantiates a new Parameter type repository location.
     *
     * @param key                             the key
     * @param description                     the description
     * @param allowEntries                    the allow entries
     * @param allowDirectories                the allow directories
     * @param allowAbsoluteEntries            the allow absolute entries
     * @param optional                        the optional
     * @param enforceValidRepositoryEntryName the enforce valid repository entry name
     */
    public ParameterTypeRepositoryLocation(String key, String description, boolean allowEntries, boolean allowDirectories,
			boolean allowAbsoluteEntries, boolean optional, boolean enforceValidRepositoryEntryName) {
		this(key, description, allowEntries, allowDirectories, allowAbsoluteEntries, optional,
				enforceValidRepositoryEntryName, false);
	}

    /**
     * Creates a new parameter type for files with the given extension. If the extension is null no
     * file filters will be used. If {@link #enforceValidRepositoryEntryName} is set to
     * <code>true</code>, will enforce valid repository entry names.
     *
     * @param key                             the key
     * @param description                     the description
     * @param allowEntries                    the allow entries
     * @param allowDirectories                the allow directories
     * @param allowAbsoluteEntries            the allow absolute entries
     * @param optional                        the optional
     * @param enforceValidRepositoryEntryName the enforce valid repository entry name
     * @param onlyWriteableLocations          the only writeable locations
     */
    public ParameterTypeRepositoryLocation(String key, String description, boolean allowEntries, boolean allowDirectories,
			boolean allowAbsoluteEntries, boolean optional, boolean enforceValidRepositoryEntryName,
			boolean onlyWriteableLocations) {
		super(key, description, null);

		setOptional(optional);
		setAllowEntries(allowEntries);
		setAllowFolders(allowDirectories);
		setAllowAbsoluteEntries(allowAbsoluteEntries);
		setEnforceValidRepositoryEntryName(enforceValidRepositoryEntryName);
		setOnlyWriteableLocations(onlyWriteableLocations);
	}

    /**
     * Creates a new parameter type for files with the given extension. If the extension is null no
     * file filters will be used.
     *
     * @param key                  the key
     * @param description          the description
     * @param allowEntries         the allow entries
     * @param allowDirectories     the allow directories
     * @param allowAbsoluteEntries the allow absolute entries
     * @param optional             the optional
     */
    public ParameterTypeRepositoryLocation(String key, String description, boolean allowEntries, boolean allowDirectories,
			boolean allowAbsoluteEntries, boolean optional) {
		this(key, description, allowEntries, allowDirectories, allowAbsoluteEntries, optional, false, false);
	}

    /**
     * Is only writeable locations boolean.
     *
     * @return the boolean
     */
    public boolean isOnlyWriteableLocations() {
		return onlyWriteableLocations;
	}

    /**
     * Sets only writeable locations.
     *
     * @param onlyWriteableLocations the only writeable locations
     */
    public void setOnlyWriteableLocations(boolean onlyWriteableLocations) {
		this.onlyWriteableLocations = onlyWriteableLocations;
	}

    /**
     * Is allow folders boolean.
     *
     * @return the boolean
     */
    public boolean isAllowFolders() {
		return allowFolders;
	}

    /**
     * Sets allow folders.
     *
     * @param allowFolders the allow folders
     */
    public void setAllowFolders(boolean allowFolders) {
		this.allowFolders = allowFolders;
	}

    /**
     * Is allow entries boolean.
     *
     * @return the boolean
     */
    public boolean isAllowEntries() {
		return allowEntries;
	}

    /**
     * Sets allow entries.
     *
     * @param allowEntries the allow entries
     */
    public void setAllowEntries(boolean allowEntries) {
		this.allowEntries = allowEntries;
	}

    /**
     * Sets allow absolute entries.
     *
     * @param allowAbsoluteEntries the allow absolute entries
     */
    public void setAllowAbsoluteEntries(boolean allowAbsoluteEntries) {
		this.allowAbsoluteEntries = allowAbsoluteEntries;
	}

    /**
     * Is allow absolute entries boolean.
     *
     * @return the boolean
     */
    public boolean isAllowAbsoluteEntries() {
		return this.allowAbsoluteEntries;
	}

    /**
     * Is enforce valid repository entry name boolean.
     *
     * @return the boolean
     */
    public boolean isEnforceValidRepositoryEntryName() {
		return enforceValidRepositoryEntryName;
	}

    /**
     * Sets enforce valid repository entry name.
     *
     * @param enforceValidRepositoryEntryName the enforce valid repository entry name
     */
    public void setEnforceValidRepositoryEntryName(boolean enforceValidRepositoryEntryName) {
		this.enforceValidRepositoryEntryName = enforceValidRepositoryEntryName;
	}
}
