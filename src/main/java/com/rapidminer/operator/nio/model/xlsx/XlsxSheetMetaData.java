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
package com.rapidminer.operator.nio.model.xlsx;

/**
 * This class contains meta data about the sheet that should be parsed (e.g. which column/row to
 * begin with, which column/row to end with, etc.).
 *
 * @author Nils Woehler
 * @since 6.3.0
 */
public class XlsxSheetMetaData {

	/**
	 * 0-based index of the first column to start parsing the Excel sheet
	 */
	private final int firstColumnIndex;

	/**
	 * 0-based index of the first row to start parsing the Excel sheet
	 */
	private final int firstRowIndex;

	/**
	 * 0-based index of the last column to parse
	 */
	private final int lastColumnIndex;

	/**
	 * 0-based index of the last row to parse
	 */
	private final int lastRowIndex;

    /**
     * Instantiates a new Xlsx sheet meta data.
     *
     * @param firstColumnIndex the first column index
     * @param firstRowIndex    the first row index
     * @param lastColumnIndex  the last column index
     * @param lastRowIndex     the last row index
     */
    public XlsxSheetMetaData(int firstColumnIndex, int firstRowIndex, int lastColumnIndex, int lastRowIndex) {
		this.firstColumnIndex = firstColumnIndex;
		this.firstRowIndex = firstRowIndex;
		this.lastColumnIndex = lastColumnIndex;
		this.lastRowIndex = lastRowIndex;
	}

    /**
     * Gets first column index.
     *
     * @return {@code 0-based} index of the first column to start parsing the Excel sheet
     */
    public int getFirstColumnIndex() {
		return firstColumnIndex;
	}

    /**
     * Gets first row index.
     *
     * @return {@code 0-based} index of the first row to start parsing the Excel sheet
     */
    public int getFirstRowIndex() {
		return firstRowIndex;
	}

    /**
     * Gets last column index.
     *
     * @return {@code 0-based} index of the last column to parse
     */
    public int getLastColumnIndex() {
		return lastColumnIndex;
	}

    /**
     * Gets last row index.
     *
     * @return {@code 0-based} index of the last row to parse
     */
    public int getLastRowIndex() {
		return lastRowIndex;
	}

    /**
     * Checks whether the current column should be skipped.
     *
     * @param columnIndex the index of the current column
     * @return <code>true</code> if the current column should be skipped because the column index is         too low or too big
     */
    public boolean isSkipColumn(int columnIndex) {
		return columnIndex < 0 || columnIndex + getFirstColumnIndex() > getLastColumnIndex();
	}

    /**
     * Gets number of columns.
     *
     * @return the full number of columns covered by this sheet meta data object
     */
    public int getNumberOfColumns() {
		// we need to add 1 as indices are 0-based
		return getLastColumnIndex() - getFirstColumnIndex() + 1;
	}

    /**
     * Gets number of rows.
     *
     * @return the number of rows covered by this meta data object or -1 if unknown
     */
    public int getNumberOfRows() {
		// Add 1 as row index starts with 0
		int lastRowIndex = getLastRowIndex();
		return lastRowIndex == Integer.MAX_VALUE ? -1 : lastRowIndex + 1;
	}

    /**
     * Maps the given parsed cell index to the actual index of a imported row
     *
     * @param parsedIndex the index that was parsed in the XLSX worksheet
     * @return the index in the imported row
     */
    public int mapColumnIndex(int parsedIndex) {
		return parsedIndex - getFirstColumnIndex();
	}

    /**
     * Get column names string [ ].
     *
     * @param isEmulatingOldNames the is emulating old names
     * @return all default column names for Excel columns
     */
    public String[] getColumnNames(boolean isEmulatingOldNames) {
		int numberOfColumns = getNumberOfColumns();
		String[] columnNames = new String[numberOfColumns];
		for (int i = 0; i < numberOfColumns; i++) {
			if (isEmulatingOldNames) {
				columnNames[i] = "attribute_" + i;
			} else {
				columnNames[i] = XlsxUtilities.convertToColumnName(i + getFirstColumnIndex());
			}
		}
		return columnNames;
	}

}
