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
package com.rapidminer.gui.tools.actions;

import com.rapidminer.gui.tools.ExtendedJTable;
import com.rapidminer.gui.tools.IconSize;
import com.rapidminer.gui.tools.SwingTools;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;


/**
 * Start the corresponding action.
 *
 * @author Ingo Mierswa
 */
public class SelectRowAction extends AbstractAction {

	private static final long serialVersionUID = 8723250647594667876L;

	private static final String ICON_NAME = "table_selection_row.png";

	private static final Icon[] ICONS = new Icon[IconSize.values().length];

	static {
		int counter = 0;
		for (IconSize size : IconSize.values()) {
			ICONS[counter++] = SwingTools.createIcon(size.getSize() + "/" + ICON_NAME);
		}
	}

	private ExtendedJTable table;

    /**
     * Instantiates a new Select row action.
     *
     * @param table the table
     * @param size  the size
     */
    public SelectRowAction(ExtendedJTable table, IconSize size) {
		super("Select Row", ICONS[size.ordinal()]);
		this.table = table;
		putValue(SHORT_DESCRIPTION, "Select the complete row");
		putValue(MNEMONIC_KEY, Integer.valueOf(KeyEvent.VK_R));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.table.selectCompleteRow();
	}
}
