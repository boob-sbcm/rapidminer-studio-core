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
package com.rapidminer.gui.tools;

import javax.swing.*;
import java.awt.*;


/**
 * This toolbar extension is not floatable and activate the hover effect.
 *
 * @author Ingo Mierswa
 */
public class ViewToolBar extends JToolBar {

	private static final long serialVersionUID = -9219638829666999431L;

    /**
     * The constant LEFT.
     */
    public static final int LEFT = 0;

    /**
     * The constant TOP.
     */
    public static final int TOP = 0;

    /**
     * The constant RIGHT.
     */
    public static final int RIGHT = 1;

    /**
     * The constant BOTTOM.
     */
    public static final int BOTTOM = 1;

	private JToolBar leftToolBar = new JToolBar();

	private JToolBar rightToolBar = new JToolBar();

    /**
     * Instantiates a new View tool bar.
     */
    public ViewToolBar() {
		this(HORIZONTAL);
	}

    /**
     * Instantiates a new View tool bar.
     *
     * @param orientation the orientation
     */
    public ViewToolBar(int orientation) {
		super();
		setFloatable(false);
		setRollover(true);
		setBorder(null);
		setLayout(new BorderLayout());

		leftToolBar.setFloatable(false);
		leftToolBar.setRollover(true);
		leftToolBar.setBorder(null);
		rightToolBar.setFloatable(false);
		rightToolBar.setRollover(true);
		rightToolBar.setBorder(null);

		leftToolBar.setOrientation(orientation);
		rightToolBar.setOrientation(orientation);
		if (orientation == HORIZONTAL) {
			add(leftToolBar, BorderLayout.WEST);
			add(rightToolBar, BorderLayout.EAST);
		} else {
			add(leftToolBar, BorderLayout.NORTH);
			add(rightToolBar, BorderLayout.SOUTH);
			setBorder(null);
		}
	}

	@Override
	public Component add(Component c) {
		return add(c, LEFT);
	}

	@Override
	public Component add(Component c, int alignment) {
		switch (alignment) {
			case LEFT:
				leftToolBar.add(c);
				break;
			case RIGHT:
				rightToolBar.add(c);
				break;
			default:
				leftToolBar.add(c);
				break;
		}
		return c;
	}

    /**
     * Add separator.
     *
     * @param alignment the alignment
     */
    public void addSeparator(int alignment) {
		switch (alignment) {
			case LEFT:
				leftToolBar.addSeparator();
				break;
			case RIGHT:
				rightToolBar.addSeparator();
				break;
			default:
				leftToolBar.addSeparator();
				break;
		}
	}

	@Override
	public JButton add(Action a) {
		return add(a, LEFT);
	}

    /**
     * Add j button.
     *
     * @param a         the a
     * @param alignment the alignment
     * @return the j button
     */
    public JButton add(Action a, int alignment) {
		switch (alignment) {
			case LEFT:
				return leftToolBar.add(a);
			case RIGHT:
				return rightToolBar.add(a);
			default:
				return leftToolBar.add(a);
		}
	}

	@Override
	public void setBackground(Color bg) {
		super.setBackground(bg);
		if (leftToolBar != null) {
			leftToolBar.setBackground(bg);
		}
		if (rightToolBar != null) {
			rightToolBar.setBackground(bg);
		}
	}
}
