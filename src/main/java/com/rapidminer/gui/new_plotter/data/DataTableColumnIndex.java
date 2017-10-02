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
package com.rapidminer.gui.new_plotter.data;

import com.rapidminer.datatable.DataTable;
import com.rapidminer.datatable.DataTableListener;
import com.rapidminer.gui.new_plotter.configuration.DataTableColumn;


/**
 * This class stores the column index of a {@link DataTableColumn}.
 *
 * @author Marius Helf
 */
public class DataTableColumnIndex implements DataTableListener {

	private int index = -1;
	private DataTableColumn dataTableColumn;
	private boolean upToDate = false;
	private DataTable dataTable;

    /**
     * Instantiates a new Data table column index.
     *
     * @param dataTableColumn the data table column
     * @param dataTable       the data table
     */
    public DataTableColumnIndex(DataTableColumn dataTableColumn, DataTable dataTable) {
		this.dataTableColumn = dataTableColumn;
		this.dataTable = dataTable;
	}

    /**
     * Gets data table column.
     *
     * @return the data table column
     */
    public DataTableColumn getDataTableColumn() {
		return dataTableColumn;
	}

    /**
     * Sets data table column.
     *
     * @param dataTableColumn the data table column
     */
    public void setDataTableColumn(DataTableColumn dataTableColumn) {
		if (dataTableColumn != this.dataTableColumn) {
			this.dataTableColumn = dataTableColumn;
			index = -1;
			upToDate = false;
		}
	}

    /**
     * Gets data table.
     *
     * @return the data table
     */
    public DataTable getDataTable() {
		return dataTable;
	}

    /**
     * Sets data table.
     *
     * @param dataTable the data table
     */
    public void setDataTable(DataTable dataTable) {
		if (dataTable != this.dataTable) {
			if (this.dataTable != null) {
				dataTable.removeDataTableListener(this);
			}
			this.dataTable = dataTable;
			if (dataTable != null) {
				dataTable.addDataTableListener(this, true);
			}

			invalidate();
		}
	}

    /**
     * Gets index.
     *
     * @return the index
     */
    public int getIndex() {
		if (!upToDate) {
			update();
		}
		return index;
	}

    /**
     * Invalidate.
     */
    public void invalidate() {
		upToDate = false;
	}

	private void update() {
		index = DataTableColumn.getColumnIndex(dataTable, dataTableColumn);
		upToDate = true;
	}

	@Override
	public void dataTableUpdated(DataTable source) {
		invalidate();
	}
}
