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
package com.rapidminer.gui.viewer;

import com.rapidminer.datatable.DataTable;
import com.rapidminer.datatable.DataTableListener;
import com.rapidminer.gui.look.Colors;
import com.rapidminer.gui.look.RapidLookTools;
import com.rapidminer.gui.tools.ExtendedJTable;

import javax.swing.table.JTableHeader;
import java.awt.event.MouseEvent;


/**
 * Can be used to display (parts of) a DataTable by means of a JTable.
 *
 * @author Ingo Mierswa
 */
public class DataTableViewerTable extends ExtendedJTable implements DataTableListener {

	private static final long serialVersionUID = 3206734427933036268L;

	private DataTableViewerTableModel model;

    /**
     * Instantiates a new Data table viewer table.
     *
     * @param autoResize the auto resize
     */
    public DataTableViewerTable(boolean autoResize) {
		this(null, true, false, autoResize);
	}

    /**
     * Instantiates a new Data table viewer table.
     *
     * @param dataTable     the data table
     * @param sortable      the sortable
     * @param columnMovable the column movable
     * @param autoResize    the auto resize
     */
    public DataTableViewerTable(DataTable dataTable, boolean sortable, boolean columnMovable, boolean autoResize) {
		super(sortable, columnMovable, autoResize);
		if (dataTable != null) {
			setDataTable(dataTable);
		}

		setAutoResizeMode(AUTO_RESIZE_OFF);
		setRowHeight(getRowHeight() + 5);

		// handles the highlighting of the currently hovered row
		setRowHighlighting(true);
	}

	@Override
	public void dataTableUpdated(DataTable source) {
		if (this.model != null) {
			this.model.fireTableDataChanged();
		}
	}

    /**
     * Sets data table.
     *
     * @param dataTable the data table
     */
    public void setDataTable(DataTable dataTable) {
		this.model = new DataTableViewerTableModel(dataTable);
		setModel(model);

		dataTable.addDataTableListener(this);
	}

	/** This method ensures that the correct tool tip for the current column is delivered. */
	@Override
	protected JTableHeader createDefaultTableHeader() {
		JTableHeader header = new JTableHeader(columnModel) {

			private static final long serialVersionUID = 1L;

			@Override
			public String getToolTipText(MouseEvent e) {
				java.awt.Point p = e.getPoint();
				int index = columnModel.getColumnIndexAtX(p.x);
				int realColumnIndex = convertColumnIndexToModel(index);
				if (realColumnIndex >= 0 && realColumnIndex < getModel().getColumnCount()) {
					return "The column " + getModel().getColumnName(realColumnIndex);
				} else {
					return "";
				}
			}
		};
		header.putClientProperty(RapidLookTools.PROPERTY_TABLE_HEADER_BACKGROUND, Colors.WHITE);
		return header;
	}
}
