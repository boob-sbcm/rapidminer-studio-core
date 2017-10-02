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
package com.rapidminer.gui.look.ui;

import com.rapidminer.gui.look.RapidLookTools;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicMenuItemUI;
import java.awt.*;


/**
 * The UI for menu items.
 *
 * @author Ingo Mierswa
 */
public class MenuItemUI extends BasicMenuItemUI {

    /**
     * Create ui component ui.
     *
     * @param c the c
     * @return the component ui
     */
    public static ComponentUI createUI(JComponent c) {
		return new MenuItemUI();
	}

	@Override
	protected void installDefaults() {
		super.installDefaults();
	}

	@Override
	public void installUI(JComponent c) {
		super.installUI(c);
	}

	@Override
	public void uninstallUI(JComponent c) {
		super.uninstallUI(c);
	}

	@Override
	protected void paintText(Graphics g, JMenuItem menuItem, Rectangle textRect, String text) {
		super.paintText(g, menuItem, textRect, text);
	}

	@Override
	protected void paintBackground(Graphics g, JMenuItem m, Color c) {
		Color oldColor = g.getColor();
		if (!m.isArmed()) {
			RapidLookTools.drawMenuItemFading(m, g);
		} else {
			RapidLookTools.drawMenuItemBackground(g, m);
		}
		g.setColor(oldColor);
	}
}
