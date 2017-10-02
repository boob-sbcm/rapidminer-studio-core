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
package com.rapidminer.studio.io.data.internal.file.excel;

import com.rapidminer.operator.nio.model.ExcelResultSetConfiguration;


/**
 * Simple POJO class to store the sheet and cell selection within the
 * {@link ExcelSheetSelectionPanel}.
 *
 * @author Nils Woehler
 * @since 7.0.0
 */
class CellRangeSelection {

	private int columnIndexStart;
	private int rowIndexStart;
	private int columnIndexEnd;
	private int rowIndexEnd;

    /**
     * Instantiates a new Cell range selection.
     *
     * @param config the config
     */
    CellRangeSelection(ExcelResultSetConfiguration config) {
		this(config.getColumnOffset(), config.getRowOffset(), config.getColumnLast(), config.getRowLast());
	}

    /**
     * Instantiates a new Cell range selection.
     *
     * @param columnIndexStart the column index start
     * @param rowIndexStart    the row index start
     * @param columnIndexEnd   the column index end
     * @param rowIndexEnd      the row index end
     */
    CellRangeSelection(int columnIndexStart, int rowIndexStart, int columnIndexEnd, int rowIndexEnd) {
		this.columnIndexStart = columnIndexStart;
		this.rowIndexStart = rowIndexStart;
		this.columnIndexEnd = columnIndexEnd;
		this.rowIndexEnd = rowIndexEnd;
	}

    /**
     * Copy constructor.
     *
     * @param other the other instance to copy
     */
    CellRangeSelection(CellRangeSelection other) {
		this(other.columnIndexStart, other.rowIndexStart, other.columnIndexEnd, other.rowIndexEnd);
	}

    /**
     * Gets column index end.
     *
     * @return the column index end
     */
    public int getColumnIndexEnd() {
		return columnIndexEnd;
	}

    /**
     * Gets column index start.
     *
     * @return the column index start
     */
    public int getColumnIndexStart() {
		return columnIndexStart;
	}

    /**
     * Gets row index end.
     *
     * @return the row index end
     */
    public int getRowIndexEnd() {
		return rowIndexEnd;
	}

    /**
     * Gets row index start.
     *
     * @return the row index start
     */
    public int getRowIndexStart() {
		return rowIndexStart;
	}

    /**
     * Sets column index start.
     *
     * @param columnIndexStart the column index start
     */
    public void setColumnIndexStart(int columnIndexStart) {
		this.columnIndexStart = columnIndexStart;
	}

    /**
     * Sets row index start.
     *
     * @param rowIndexStart the row index start
     */
    public void setRowIndexStart(int rowIndexStart) {
		this.rowIndexStart = rowIndexStart;
	}

    /**
     * Sets column index end.
     *
     * @param columnIndexEnd the column index end
     */
    public void setColumnIndexEnd(int columnIndexEnd) {
		this.columnIndexEnd = columnIndexEnd;
	}

    /**
     * Sets row index end.
     *
     * @param rowIndexEnd the row index end
     */
    public void setRowIndexEnd(int rowIndexEnd) {
		this.rowIndexEnd = rowIndexEnd;
	}

}
