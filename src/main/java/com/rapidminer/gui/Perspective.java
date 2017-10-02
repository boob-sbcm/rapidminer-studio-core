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
package com.rapidminer.gui;

import com.rapidminer.tools.FileSystemService;
import com.rapidminer.tools.I18N;
import com.rapidminer.tools.LogService;
import com.vlsolutions.swing.docking.DockingContext;
import com.vlsolutions.swing.docking.ws.Workspace;
import com.vlsolutions.swing.docking.ws.WorkspaceException;

import java.io.*;
import java.util.logging.Level;


/**
 * The type Perspective.
 *
 * @author Simon Fischer
 */
@SuppressWarnings("deprecation")
public class Perspective {

	private final String name;
	private final Workspace workspace = new Workspace();
	private boolean userDefined = false;;
	private final ApplicationPerspectives owner;
	private final PerspectiveModel model;
	private final PerspectiveProperties properties = new PerspectiveProperties();

    /**
     * Instantiates a new Perspective.
     *
     * @param owner the owner
     * @param name  the name
     */
    public Perspective(ApplicationPerspectives owner, String name) {
		this.name = name;
		this.owner = owner;
		this.model = null;
	}

    /**
     * Instantiates a new Perspective.
     *
     * @param model the model
     * @param name  the name
     */
    public Perspective(PerspectiveModel model, String name) {
		this.name = name;
		this.model = model;
		this.owner = null;
	}

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
		return name;
	}

    /**
     * Gets workspace.
     *
     * @return the workspace
     */
    public Workspace getWorkspace() {
		return workspace;
	}

    /**
     * Store.
     *
     * @param dockingContext the docking context
     */
    public void store(DockingContext dockingContext) {
		properties.store();
		try {
			workspace.loadFrom(dockingContext);
		} catch (WorkspaceException e) {
			LogService.getRoot().log(Level.WARNING, I18N.getMessage(LogService.getRoot().getResourceBundle(),
					"com.rapidminer.gui.Perspective.saving_workspace_error", e), e);

		}

	}

    /**
     * Apply.
     *
     * @param dockingContext the docking context
     */
    protected void apply(DockingContext dockingContext) {
		try {
			workspace.apply(dockingContext);
			model.notifyChangeListener();
		} catch (WorkspaceException e) {
			LogService.getRoot().log(Level.WARNING, I18N.getMessage(LogService.getRoot().getResourceBundle(),
					"com.rapidminer.gui.Perspective.applying_workspace_error", e), e);
		}
		properties.apply();
	}

    /**
     * Gets file.
     *
     * @return the file
     */
    File getFile() {
		return FileSystemService
				.getUserConfigFile("vlperspective-" + (isUserDefined() ? "user-" : "predefined-") + name + ".xml");
	}

    /**
     * Save.
     */
    public void save() {
		File file = getFile();
		OutputStream out = null;
		try {
			out = new FileOutputStream(file);
			workspace.writeXML(out);
		} catch (Exception e) {
			LogService.getRoot().log(Level.WARNING, I18N.getMessage(LogService.getRoot().getResourceBundle(),
					"com.rapidminer.gui.Perspective.saving_perspective_error", file, e), e);
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
			}
		}
	}

    /**
     * Load.
     */
    public void load() {
		LogService.getRoot().log(Level.FINE, "com.rapidminer.gui.Perspective.loading_perspective", getName());
		File file = getFile();
		if (!file.exists()) {
			return;
		}
		InputStream in = null;
		try {
			in = new FileInputStream(file);
			workspace.readXML(in);
		} catch (Exception e) {

			if (!userDefined) {
				LogService.getRoot().log(Level.WARNING, I18N.getMessage(LogService.getRoot().getResourceBundle(),
						"com.rapidminer.gui.Perspective.reading_perspective_error_restoring", file, e), e);

				if (owner != null) {
					owner.restoreDefault(getName());
				}
				if (model != null) {
					model.restoreDefault(getName());
				}
			} else {
				LogService.getRoot().log(Level.WARNING, I18N.getMessage(LogService.getRoot().getResourceBundle(),
						"com.rapidminer.gui.Perspective.reading_perspective_error_clearing", file, e), e);
				workspace.clear();
			}
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
			}
		}
	}

    /**
     * Sets user defined.
     *
     * @param b the b
     */
    public void setUserDefined(boolean b) {
		this.userDefined = b;
	}

    /**
     * Is user defined boolean.
     *
     * @return the boolean
     */
    public boolean isUserDefined() {
		return this.userDefined;
	}

    /**
     * Delete.
     */
    public void delete() {
		File file = getFile();
		if (file.exists()) {
			file.delete();
		}
	}

}
