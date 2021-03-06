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
package com.rapidminer.gui.look.icons;

import javax.swing.*;
import java.awt.*;


/**
 * The factory used for creating and holding icon objects. All icons are singletons delivered by the
 * methods of this class.
 *
 * @author Ingo Mierswa
 */
public class IconFactory {

    /**
     * The constant MENU_ICON_SIZE.
     */
    public static final Dimension MENU_ICON_SIZE = new Dimension(10, 10);

	private final static Icon RADIO_BUTTON_ICON = new RadioButtonIcon();

	private final static Icon CHECK_BOX_ICON = new CheckBoxIcon();

	private final static Icon CHECK_BOX_MENU_ITEM_ICON = new CheckBoxMenuItemIcon();

	private final static Icon RADIO_BUTTON_MENU_ITEM_ICON = new RadioButtonMenuItemIcon();

    /**
     * Gets radio button icon.
     *
     * @return the radio button icon
     */
    public static Icon getRadioButtonIcon() {
		return RADIO_BUTTON_ICON;
	}

    /**
     * Gets check box icon.
     *
     * @return the check box icon
     */
    public static Icon getCheckBoxIcon() {
		return CHECK_BOX_ICON;
	}

    /**
     * Gets check box menu item icon.
     *
     * @return the check box menu item icon
     */
    public static Icon getCheckBoxMenuItemIcon() {
		return CHECK_BOX_MENU_ITEM_ICON;
	}

    /**
     * Gets radio button menu item icon.
     *
     * @return the radio button menu item icon
     */
    public static Icon getRadioButtonMenuItemIcon() {
		return RADIO_BUTTON_MENU_ITEM_ICON;
	}

}
