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

import com.rapidminer.gui.tools.ArrowButton;
import com.rapidminer.gui.tools.ViewToolBar;
import com.vlsolutions.swing.toolbars.VLToolBar;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * The type Plain arrow drop down button.
 *
 * @author Dominik Halfkann
 */
public abstract class PlainArrowDropDownButton extends JButton {

	private static final long serialVersionUID = -5987392204641149649L;

	private final PopupMenuListener popupMenuListener = new PopupMenuListener() {

		@Override
		public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
			popupVisible = true;
			arrowButton.getModel().setSelected(true);
		}

		@Override
		public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
			popupVisible = false;

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

			if (popupVisible && !arrowButton.getModel().isSelected()) {
				arrowButton.getModel().setSelected(true);
				return;
			}

		}
	};

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
     * Instantiates a new Plain arrow drop down button.
     *
     * @param action the action
     */
    public PlainArrowDropDownButton(Action action) {
		super(action);
		arrowButton.getModel().addChangeListener(changeListener);
		arrowButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				JPopupMenu popup = getPopupMenu();
				popup.addPopupMenuListener(popupMenuListener);
				popup.show(arrowButton, 0, arrowButton.getHeight());
			}
		});
		arrowButton.setMargin(new Insets(0, 0, 0, 0));
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
     * Add to tool bar j button.
     *
     * @param toolbar the toolbar
     * @return the j button
     */
    public JButton addToToolBar(JToolBar toolbar) {
		toolbar.add(arrowButton);
		return arrowButton;
	}

    /**
     * Add to tool bar j button.
     *
     * @param toolbar the toolbar
     * @return the j button
     */
    public JButton addToToolBar(VLToolBar toolbar) {
		toolbar.add(arrowButton);
		return arrowButton;
	}

    /**
     * Add to tool bar j button.
     *
     * @param toolbar   the toolbar
     * @param alignment the alignment
     * @return the j button
     */
    public JButton addToToolBar(ViewToolBar toolbar, int alignment) {
		toolbar.add(arrowButton, alignment);
		return arrowButton;
	}

    /**
     * Gets drop down arrow button.
     *
     * @return the drop down arrow button
     */
    public JButton getDropDownArrowButton() {
		return arrowButton;
	}

    /**
     * Make drop down button plain arrow drop down button.
     *
     * @param mainAction the main action
     * @param actions    the actions
     * @return the plain arrow drop down button
     */
// factory methods
	public static PlainArrowDropDownButton makeDropDownButton(Action mainAction, Action... actions) {
		final JPopupMenu menu = new JPopupMenu();
		for (Action action : actions) {
			menu.add(action);
		}
		return new PlainArrowDropDownButton(mainAction) {

			private static final long serialVersionUID = -7359018188605409766L;

			@Override
			protected JPopupMenu getPopupMenu() {
				return menu;
			}
		};
	}

    /**
     * Make drop down button plain arrow drop down button.
     *
     * @param action the action
     * @return the plain arrow drop down button
     */
    public static PlainArrowDropDownButton makeDropDownButton(Action action) {
		final JPopupMenu menu = new JPopupMenu();
		return new PlainArrowDropDownButton(action) {

			private static final long serialVersionUID = -7359018188605409766L;

			@Override
			protected JPopupMenu getPopupMenu() {
				return menu;
			}
		};
	}
}
