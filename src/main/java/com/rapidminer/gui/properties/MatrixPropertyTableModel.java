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
package com.rapidminer.gui.properties;

import javax.swing.table.DefaultTableModel;
import java.util.Vector;


/**
 * A table model model used by {@link MatrixPropertyTable}. This model is necessary to support
 * proper column removal.
 *
 * @author Helge Homburg
 * @see com.rapidminer.gui.properties.MatrixPropertyTable
 */
public class MatrixPropertyTableModel extends DefaultTableModel {

	private static final long serialVersionUID = 0L;

	private String baseName;

	private String columnBaseName;

    /**
     * Instantiates a new Matrix property table model.
     *
     * @param baseName       the base name
     * @param columnBaseName the column base name
     * @param rows           the rows
     * @param columns        the columns
     */
    public MatrixPropertyTableModel(String baseName, String columnBaseName, int rows, int columns) {
		super(rows, columns);
		this.baseName = baseName;
		this.columnBaseName = columnBaseName;
	}

    /**
     * Gets column identifiers.
     *
     * @return the column identifiers
     */
    public Vector<?> getColumnIdentifiers() {
		return columnIdentifiers;
	}

	@Override
	public String getColumnName(int column) {
		if (column > 0) {
			return columnBaseName + " " + column + " ";
		} else {
			return baseName;
		}
	}
}
