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
package com.rapidminer.gui.actions;

import com.rapidminer.gui.tools.ResourceAction;
import com.rapidminer.gui.tools.components.ToggleDropDownButton;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Collection;
import java.util.LinkedList;


/**
 * An action that handles a boolean state which is toggled by the actionPerformed method.
 *
 * @author Tobias Malbrecht
 */
public abstract class ToggleAction extends ResourceAction {

	private static final long serialVersionUID = -4465114837957358373L;

    /**
     * The interface Toggle action listener.
     */
    public interface ToggleActionListener {

        /**
         * Sets selected.
         *
         * @param selected the selected
         */
        public void setSelected(boolean selected);
	}

	private class ToggleJCheckBoxMenuItem extends JCheckBoxMenuItem implements ToggleActionListener {

		private static final long serialVersionUID = 8604924475187496354L;

		private ToggleJCheckBoxMenuItem(Action action) {
			super(action);
			setSelected(ToggleAction.this.isSelected());
		}
	}

	private class ToggleJToggleButton extends JToggleButton implements ToggleActionListener {

		private static final long serialVersionUID = 8939204437291275737L;

		private ToggleJToggleButton(Action action) {
			super(action);
			setSelected(ToggleAction.this.isSelected());
		}
	}

	private abstract class ToggleJToggleDropDownButton extends ToggleDropDownButton implements ToggleActionListener {

		private static final long serialVersionUID = 1534764344656638939L;

		private ToggleJToggleDropDownButton(ToggleAction action) {
			super(action);
			setSelected(ToggleAction.this.isSelected());
		}
	}

	private boolean selected = false;

	private Collection<ToggleActionListener> listeners = new LinkedList<ToggleActionListener>();

    /**
     * Instantiates a new Toggle action.
     *
     * @param smallIcon the small icon
     * @param key       the key
     * @param args      the args
     */
    public ToggleAction(boolean smallIcon, String key, Object... args) {
		this(smallIcon, key, IconType.NORMAL, args);
	}

    /**
     * Instantiates a new Toggle action.
     *
     * @param smallIcon the small icon
     * @param key       the key
     * @param iconType  the icon type
     * @param args      the args
     */
    public ToggleAction(boolean smallIcon, String key, IconType iconType, Object... args) {
		super(smallIcon, key, iconType, args);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		setSelected(!isSelected());
		actionToggled(null);
	}

    /**
     * Action toggled.
     *
     * @param e the e
     */
    public abstract void actionToggled(ActionEvent e);

    /**
     * Reset action.
     *
     * @param selected the selected
     */
    public void resetAction(boolean selected) {
		if (selected != isSelected()) {
			setSelected(!isSelected());
			actionToggled(null);
		}
	}

    /**
     * Is selected boolean.
     *
     * @return the boolean
     */
    public boolean isSelected() {
		return selected;
	}

    /**
     * Sets selected.
     *
     * @param selected the selected
     */
    public void setSelected(boolean selected) {
		this.selected = selected;
		for (ToggleActionListener l : listeners) {
			l.setSelected(selected);
		}
	}

    /**
     * Create menu item j check box menu item.
     *
     * @return the j check box menu item
     */
    public JCheckBoxMenuItem createMenuItem() {
		ToggleJCheckBoxMenuItem item = new ToggleJCheckBoxMenuItem(this);
		listeners.add(item);
		return item;
	}

    /**
     * Create toggle button j toggle button.
     *
     * @return the j toggle button
     */
    public JToggleButton createToggleButton() {
		ToggleJToggleButton button = new ToggleJToggleButton(this);
		listeners.add(button);
		return button;
	}

    /**
     * Create drop down toggle button toggle drop down button.
     *
     * @param popupMenu the popup menu
     * @return the toggle drop down button
     */
    public ToggleDropDownButton createDropDownToggleButton(final JPopupMenu popupMenu) {
		ToggleJToggleDropDownButton button = new ToggleJToggleDropDownButton(this) {

			private static final long serialVersionUID = 619422148555974973L;

			@Override
			protected JPopupMenu getPopupMenu() {
				return popupMenu;
			}
		};
		listeners.add(button);
		return button;
	}

    /**
     * Add toggle action listener.
     *
     * @param l the l
     */
    public void addToggleActionListener(ToggleActionListener l) {
		listeners.add(l);
	}

    /**
     * Remove toggle action listener.
     *
     * @param l the l
     */
    public void removeToggleActionListener(ToggleActionListener l) {
		listeners.remove(l);
	}
}
