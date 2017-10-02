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

import java.util.logging.Level;

import javax.swing.JMenu;
import javax.swing.JToolBar;

import com.rapidminer.tools.LogService;
import com.vlsolutions.swing.docking.Dockable;
import com.vlsolutions.swing.docking.DockingContext;


/**
 * Collection of {@link Perspective}s that can be applied, saved, created.
 *
 * @author Simon Fischer
 * @deprecated Since 7.0.0. Use {@link PerspectiveController} instead.
 */
@Deprecated
public abstract class ApplicationPerspectives {

    /**
     * The Perspective controller.
     */
    protected final PerspectiveController perspectiveController;

    /**
     * Instantiates a new Application perspectives.
     *
     * @param perspectiveController the perspective controller
     */
    public ApplicationPerspectives(final PerspectiveController perspectiveController) {
		this.perspectiveController = perspectiveController;
	}

    /**
     * Instantiates a new Application perspectives.
     *
     * @param context the context
     */
    public ApplicationPerspectives(final DockingContext context) {
		perspectiveController = new PerspectiveController(context);
	}

    /**
     * Show perspective.
     *
     * @param perspective the perspective
     */
    public void showPerspective(final Perspective perspective) {
		perspectiveController.showPerspective(perspective);
	}

    /**
     * Gets workspace menu.
     *
     * @return the workspace menu
     */
    public JMenu getWorkspaceMenu() {
		LogService.getRoot().log(Level.WARNING,
				"The access of the WorkspaceMenu deprecated. Use the PerspectiveController instead.");
		return new JMenu();
	}

    /**
     * Gets workspace tool bar.
     *
     * @return the workspace tool bar
     */
    public JToolBar getWorkspaceToolBar() {
		LogService.getRoot().log(Level.WARNING,
				"The access of the WorkspaceToolBar is deprecated. Use the PerspectiveController instead.");
		return new JToolBar();
	}

    /**
     * Checks if the given string is valid as name of a new perspective.
     *
     * @param name the name
     * @return validity boolean
     */
    public boolean isValidName(final String name) {
		return perspectiveController.getModel().isValidName(name);
	}

    /**
     * Add perspective perspective.
     *
     * @param name        the name
     * @param userDefined the user defined
     * @return the perspective
     * @throws IllegalArgumentException if name is already used
     */
    public Perspective addPerspective(final String name, final boolean userDefined) {
		return perspectiveController.getModel().addPerspective(name, userDefined);
	}

    /**
     * Saves all perspectives to the users config directory.
     */
    public void saveAll() {
		perspectiveController.saveAll();
	}

    /**
     * Loads all perspectives from the users config directory.
     */
    public void loadAll() {
		perspectiveController.loadAll();
	}

    /**
     * Gets current perspective.
     *
     * @return the current perspective
     */
    public Perspective getCurrentPerspective() {
		return perspectiveController.getModel().getSelectedPerspective();
	}

    /**
     * Switches to the given perspective, storing the current one.  @param name the name
     *
     * @param name the name
     */
    public void showPerspective(final String name) {
		perspectiveController.showPerspective(name);
	}

    /**
     * Creates a user-defined perspectives, and possibly switches to this new perspective
     * immediately. The new perspective will be a copy of the current one.
     *
     * @param name the name
     * @param show the show
     * @return the perspective
     */
    public Perspective createUserPerspective(final String name, final boolean show) {
		return perspectiveController.createUserPerspective(name, show);
	}

    /**
     * Shows the tab as a child of the given dockable in all perspectives.  @param dockable the dockable
     *
     * @param dockable the dockable
     * @param parent   the parent
     */
    public void showTabInAllPerspectives(final Dockable dockable, final Dockable parent) {
		perspectiveController.showTabInAllPerspectives(dockable, parent);
	}

    /**
     * Remove from all perspectives.
     *
     * @param dockable the dockable
     */
    public void removeFromAllPerspectives(final Dockable dockable) {
		perspectiveController.removeFromAllPerspectives(dockable);
	}

    /**
     * Make predefined.
     */
    protected abstract void makePredefined();

    /**
     * Restore default.
     *
     * @param perspectiveName the perspective name
     */
    protected abstract void restoreDefault(String perspectiveName);

    /**
     * Gets perspective.
     *
     * @param name the name
     * @return the perspective
     */
    protected Perspective getPerspective(final String name) {
		return perspectiveController.getModel().getPerspective(name);
	}

    /**
     * Add perspective change listener.
     *
     * @param listener the listener
     */
    public void addPerspectiveChangeListener(final PerspectiveChangeListener listener) {
		perspectiveController.getModel().addPerspectiveChangeListener(listener);
	}

    /**
     * Remove perspective change listener boolean.
     *
     * @param listener the listener
     * @return the boolean
     */
    public boolean removePerspectiveChangeListener(final PerspectiveChangeListener listener) {
		return perspectiveController.getModel().removePerspectiveChangeListener(listener);
	}

    /**
     * Notify change listener.
     */
    public void notifyChangeListener() {
		perspectiveController.getModel().notifyChangeListener();
	}

}
