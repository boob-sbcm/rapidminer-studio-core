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
import javax.swing.event.*;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.EventObject;


/**
 * This is an component of the {@link EditableHeaderJTable}. It was retrieved from
 * http://www.java2s.com/Code/Java/Swing-Components/EditableHeaderTableExample2.htm
 *
 * @author Sebastian Land
 */
public class EditableTableHeader extends JTableHeader implements CellEditorListener {

	private static final long serialVersionUID = 1L;

    /**
     * The Header row.
     */
    public final int HEADER_ROW = -10;

    /**
     * The Editing column.
     */
    transient protected int editingColumn;

    /**
     * The Cell editor.
     */
    transient protected TableCellEditor cellEditor;

    /**
     * The Editor comp.
     */
    transient protected Component editorComp;

    /**
     * Instantiates a new Editable table header.
     *
     * @param columnModel the column model
     */
    public EditableTableHeader(TableColumnModel columnModel) {
		super(columnModel);
		setReorderingAllowed(false);
		cellEditor = null;
		recreateTableColumn(columnModel);

		// this listener is just for the case when someone opens an editor and then changes the size
		// of the left-handed
		// column without losing focus of the editor. Without this listener the editor would remain
		// in the same position
		// and look very weird.
		this.getColumnModel().addColumnModelListener(new TableColumnModelListener() {

			@Override
			public void columnSelectionChanged(ListSelectionEvent e) {}

			@Override
			public void columnRemoved(TableColumnModelEvent e) {}

			@Override
			public void columnMoved(TableColumnModelEvent e) {}

			@Override
			public void columnMarginChanged(ChangeEvent e) {
				// calling it each time because after the first time the editor is set to null
				// anyway
				removeEditor();
			}

			@Override
			public void columnAdded(TableColumnModelEvent e) {}
		});
	}

	@Override
	public void updateUI() {
		setUI(new EditableTableHeaderUI());
		resizeAndRepaint();
		invalidate();
	}

    /**
     * Recreate table column.
     *
     * @param columnModel the column model
     */
    protected void recreateTableColumn(TableColumnModel columnModel) {
		int n = columnModel.getColumnCount();
		EditableTableHeaderColumn[] newCols = new EditableTableHeaderColumn[n];
		TableColumn[] oldCols = new TableColumn[n];
		for (int i = 0; i < n; i++) {
			oldCols[i] = columnModel.getColumn(i);
			newCols[i] = new EditableTableHeaderColumn(i);
			newCols[i].copyValues(oldCols[i]);
		}
		for (int i = 0; i < n; i++) {
			columnModel.removeColumn(oldCols[i]);
		}
		for (int i = 0; i < n; i++) {
			columnModel.addColumn(newCols[i]);
		}
	}

    /**
     * Edit cell at boolean.
     *
     * @param index the index
     * @param e     the e
     * @return the boolean
     */
    public boolean editCellAt(int index, EventObject e) {
		if (cellEditor != null && !cellEditor.stopCellEditing()) {
			return false;
		}
		if (!isCellEditable(index)) {
			return false;
		}
		TableCellEditor editor = getCellEditor(index);

		if (editor != null && editor.isCellEditable(e)) {
			editorComp = prepareEditor(editor, index);
			editorComp.setBounds(getHeaderRect(index));
			add(editorComp);
			editorComp.validate();
			setCellEditor(editor);
			setEditingColumn(index);
			editor.addCellEditorListener(this);

			return true;
		}
		return false;
	}

    /**
     * Is cell editable boolean.
     *
     * @param index the index
     * @return the boolean
     */
    public boolean isCellEditable(int index) {
		if (getReorderingAllowed()) {
			return false;
		}
		int columnIndex = columnModel.getColumn(index).getModelIndex();
		EditableTableHeaderColumn col = (EditableTableHeaderColumn) columnModel.getColumn(columnIndex);
		return col.isHeaderEditable();
	}

    /**
     * Gets cell editor.
     *
     * @param index the index
     * @return the cell editor
     */
    public TableCellEditor getCellEditor(int index) {
		int columnIndex = columnModel.getColumn(index).getModelIndex();
		EditableTableHeaderColumn col = (EditableTableHeaderColumn) columnModel.getColumn(columnIndex);
		return col.getHeaderEditor();
	}

    /**
     * Sets cell editor.
     *
     * @param newEditor the new editor
     */
    public void setCellEditor(TableCellEditor newEditor) {
		TableCellEditor oldEditor = cellEditor;
		cellEditor = newEditor;

		// firePropertyChange

		if (oldEditor != null) {
			oldEditor.removeCellEditorListener(this);
		}
		if (newEditor != null) {
			newEditor.addCellEditorListener(this);
		}
	}

    /**
     * Prepare editor component.
     *
     * @param editor the editor
     * @param index  the index
     * @return the component
     */
    @SuppressWarnings("deprecation")
	public Component prepareEditor(TableCellEditor editor, int index) {
		Object value = columnModel.getColumn(index).getHeaderValue();
		boolean isSelected = true;
		int row = HEADER_ROW;
		JTable table = getTable();
		Component comp = editor.getTableCellEditorComponent(table, value, isSelected, row, index);
		if (comp instanceof JComponent) {
			((JComponent) comp).setNextFocusableComponent(this);
		}
		return comp;
	}

    /**
     * Gets cell editor.
     *
     * @return the cell editor
     */
    public TableCellEditor getCellEditor() {
		return cellEditor;
	}

    /**
     * Gets editor component.
     *
     * @return the editor component
     */
    public Component getEditorComponent() {
		return editorComp;
	}

    /**
     * Sets editing column.
     *
     * @param aColumn the a column
     */
    public void setEditingColumn(int aColumn) {
		editingColumn = aColumn;
	}

    /**
     * Gets editing column.
     *
     * @return the editing column
     */
    public int getEditingColumn() {
		return editingColumn;
	}

    /**
     * Remove editor.
     */
    public void removeEditor() {
		TableCellEditor editor = getCellEditor();
		if (editor != null) {
			editor.removeCellEditorListener(this);

			requestFocus();
			remove(editorComp);

			int index = getEditingColumn();
			Rectangle cellRect = getHeaderRect(index);

			setCellEditor(null);
			setEditingColumn(-1);
			editorComp = null;

			repaint(cellRect);
		}
	}

    /**
     * Is editing boolean.
     *
     * @return the boolean
     */
    public boolean isEditing() {
		return (cellEditor == null) ? false : true;
	}

	//
	// CellEditorListener
	//
	@Override
	public void editingStopped(ChangeEvent e) {
		TableCellEditor editor = getCellEditor();
		if (editor != null) {
			Object value = editor.getCellEditorValue();
			int index = getEditingColumn();
			columnModel.getColumn(index).setHeaderValue(value);
			removeEditor();
		}
	}

	@Override
	public void editingCanceled(ChangeEvent e) {
		removeEditor();
	}

	// @Override
	// public void setReorderingAllowed(boolean b) {
	// super.setReorderingAllowed(b);
	// reorderingAllowed = b;
	// }
}
