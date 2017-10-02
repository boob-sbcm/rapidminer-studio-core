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
package com.rapidminer.gui.tools.table;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;


/**
 * This is an component of the {@link EditableHeaderJTable}. It was retrieved from
 * http://www.java2s.com/Code/Java/Swing-Components/EditableHeaderTableExample2.htm
 *
 * @author Sebastian Land
 */
public class EditableTableHeaderColumn extends TableColumn {

	private static final long serialVersionUID = 1L;

    /**
     * The Header editor.
     */
    protected TableCellEditor headerEditor;

    /**
     * The Is header editable.
     */
    protected boolean isHeaderEditable;

    /**
     * Instantiates a new Editable table header column.
     *
     * @param column the column
     */
    public EditableTableHeaderColumn(int column) {
		super(column);
		setHeaderEditor(createDefaultHeaderEditor());
		isHeaderEditable = true;
	}

    /**
     * Sets header editor.
     *
     * @param headerEditor the header editor
     */
    public void setHeaderEditor(TableCellEditor headerEditor) {
		this.headerEditor = headerEditor;
	}

    /**
     * Gets header editor.
     *
     * @return the header editor
     */
    public TableCellEditor getHeaderEditor() {
		return headerEditor;
	}

    /**
     * Sets header editable.
     *
     * @param isEditable the is editable
     */
    public void setHeaderEditable(boolean isEditable) {
		isHeaderEditable = isEditable;
	}

    /**
     * Is header editable boolean.
     *
     * @return the boolean
     */
    public boolean isHeaderEditable() {
		return isHeaderEditable;
	}

    /**
     * Copy values.
     *
     * @param base the base
     */
    public void copyValues(TableColumn base) {
		modelIndex = base.getModelIndex();
		identifier = base.getIdentifier();
		width = base.getWidth();
		minWidth = base.getMinWidth();
		setPreferredWidth(base.getPreferredWidth());
		maxWidth = base.getMaxWidth();
		headerRenderer = base.getHeaderRenderer();
		headerValue = base.getHeaderValue();
		cellRenderer = base.getCellRenderer();
		cellEditor = base.getCellEditor();
		isResizable = base.getResizable();
	}

    /**
     * Create default header editor table cell editor.
     *
     * @return the table cell editor
     */
    protected TableCellEditor createDefaultHeaderEditor() {
		return new DefaultCellEditor(new JTextField());
	}

}
