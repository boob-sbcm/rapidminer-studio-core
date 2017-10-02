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
package com.rapidminer.gui.tools.components;

import com.rapidminer.gui.actions.ToggleAction;
import com.rapidminer.gui.actions.ToggleAction.ToggleActionListener;
import com.rapidminer.gui.tools.ArrowButton;
import com.rapidminer.gui.tools.ViewToolBar;
import com.vlsolutions.swing.toolbars.VLToolBar;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Action;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;


/**
 * The type Toggle drop down button.
 *
 * @author Tobias Malbrecht
 */
public abstract class ToggleDropDownButton extends JToggleButton implements ToggleActionListener {

	private static final long serialVersionUID = -5987392204641149649L;

	private final PopupMenuListener popupMenuListener = new PopupMenuListener() {

		@Override
		public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
			popupVisible = true;
			mainButton.getModel().setRollover(true);
			arrowButton.getModel().setSelected(true);
		}

		@Override
		public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
			popupVisible = false;

			mainButton.getModel().setRollover(false);
			arrowButton.getModel().setSelected(false);
			((JPopupMenu) e.getSource()).removePopupMenuListener(this); // act as good programmer :)
		}

		@Override
		public void popupMenuCanceled(PopupMenuEvent e) {
			popupVisible = false;
		}
	};

	private final ChangeListener changeListener = new ChangeListener() {

		@Override
		public void stateChanged(ChangeEvent e) {
			if (e.getSource() == mainButton.getModel()) {
				if (popupVisible && !mainButton.getModel().isRollover()) {
					mainButton.getModel().setRollover(true);
					return;
				}
				arrowButton.getModel().setRollover(mainButton.getModel().isRollover());
				arrowButton.setSelected(mainButton.getModel().isArmed() && mainButton.getModel().isPressed());
			} else {
				if (popupVisible && !arrowButton.getModel().isSelected()) {
					arrowButton.getModel().setSelected(true);
					return;
				}
				mainButton.getModel().setRollover(arrowButton.getModel().isRollover());
			}
		}
	};

    /**
     * The Main button.
     */
    protected JToggleButton mainButton = this;

    /**
     * The type Drop down arrow button.
     */
    public static class DropDownArrowButton extends ArrowButton {

		private static final long serialVersionUID = -398619111521186260L;

        /**
         * Instantiates a new Drop down arrow button.
         */
        public DropDownArrowButton() {
			super(SwingConstants.SOUTH);
		}
	}

	private final ArrowButton arrowButton = new DropDownArrowButton();

	private boolean popupVisible = false;

    /**
     * Instantiates a new Toggle drop down button.
     *
     * @param action the action
     */
    public ToggleDropDownButton(Action action) {
		super(action);
		if (action instanceof ToggleAction) {
			((ToggleAction) action).addToggleActionListener(this);
		}
		mainButton.setText(null);
		mainButton.setOpaque(false);
		mainButton.setBorderPainted(false);
		mainButton.setMargin(new Insets(0, 0, 0, 0));
		mainButton.getModel().addChangeListener(changeListener);
		arrowButton.getModel().addChangeListener(changeListener);
		arrowButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				JPopupMenu popup = getPopupMenu();
				popup.addPopupMenuListener(popupMenuListener);
				popup.show(mainButton, 0, mainButton.getHeight());
			}
		});
		arrowButton.setMargin(new Insets(0, 0, 0, 0));
		mainButton.addPropertyChangeListener("enabled", new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				arrowButton.setEnabled(mainButton.isEnabled());
			}
		});
	}

    /**
     * Gets popup menu.
     *
     * @return the popup menu
     */
    protected abstract JPopupMenu getPopupMenu();

    /**
     * Add.
     *
     * @param action the action
     */
    public void add(Action action) {
		getPopupMenu().add(action);
	}

    /**
     * Add.
     *
     * @param item the item
     */
    public void add(JMenuItem item) {
		getPopupMenu().add(item);
	}

    /**
     * Add to tool bar j toggle button.
     *
     * @param toolbar the toolbar
     * @return the j toggle button
     */
    public JToggleButton addToToolBar(JToolBar toolbar) {
		toolbar.add(mainButton);
		toolbar.add(arrowButton);
		return mainButton;
	}

    /**
     * Add to tool bar j toggle button.
     *
     * @param toolbar the toolbar
     * @return the j toggle button
     */
    public JToggleButton addToToolBar(VLToolBar toolbar) {
		toolbar.add(mainButton);
		toolbar.add(arrowButton);
		return mainButton;
	}

    /**
     * Add to tool bar j toggle button.
     *
     * @param toolbar   the toolbar
     * @param alignment the alignment
     * @return the j toggle button
     */
    public JToggleButton addToToolBar(ViewToolBar toolbar, int alignment) {
		toolbar.add(mainButton, alignment);
		toolbar.add(arrowButton, alignment);
		return mainButton;
	}

    /**
     * Add to flow layout panel j toggle button.
     *
     * @param panel the panel
     * @return the j toggle button
     */
    public JToggleButton addToFlowLayoutPanel(JPanel panel) {
		panel.add(mainButton);
		panel.add(arrowButton);
		return mainButton;
	}

    /**
     * Make drop down button toggle drop down button.
     *
     * @param mainAction the main action
     * @param actions    the actions
     * @return the toggle drop down button
     */
// factory methods
	public static ToggleDropDownButton makeDropDownButton(ToggleAction mainAction, Action... actions) {
		final JPopupMenu menu = new JPopupMenu();
		for (Action action : actions) {
			menu.add(action);
		}
		return new ToggleDropDownButton(mainAction) {

			private static final long serialVersionUID = -7359018188605409766L;

			@Override
			protected JPopupMenu getPopupMenu() {
				return menu;
			}
		};
	}

    /**
     * Make drop down button toggle drop down button.
     *
     * @param action the action
     * @return the toggle drop down button
     */
    public static ToggleDropDownButton makeDropDownButton(ToggleAction action) {
		final JPopupMenu menu = new JPopupMenu();
		return new ToggleDropDownButton(action) {

			private static final long serialVersionUID = -7359018188605409766L;

			@Override
			protected JPopupMenu getPopupMenu() {
				return menu;
			}
		};
	}
}
