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
package com.rapidminer.datatable;

/**
 * The type Value mapping data table row.
 *
 * @author Marius Helf
 */
public class ValueMappingDataTableRow implements DataTableRow {

	private ValueMappingDataTableView dataTable;
	private DataTableRow row;

    /**
     * Instantiates a new Value mapping data table row.
     *
     * @param row                   the row
     * @param valueMappingDataTable the value mapping data table
     */
    public ValueMappingDataTableRow(DataTableRow row, ValueMappingDataTableView valueMappingDataTable) {
		this.dataTable = valueMappingDataTable;
		this.row = row;
	}

	@Override
	public String getId() {
		return row.getId();
	}

	@Override
	public double getValue(int index) {
		double value = row.getValue(index);
		return dataTable.mapValue(value, index);
	}

	@Override
	public int getNumberOfValues() {
		return row.getNumberOfValues();
	}

}
