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
package com.rapidminer.operator.visualization.dependencies;

import Jama.Matrix;
import com.rapidminer.datatable.DataTable;
import com.rapidminer.datatable.DataTablePairwiseMatrixExtractionAdapter;
import com.rapidminer.datatable.DataTableSymmetricalMatrixAdapter;
import com.rapidminer.example.Attribute;
import com.rapidminer.example.ExampleSet;
import com.rapidminer.operator.ResultObjectAdapter;
import com.rapidminer.tools.Tools;

import java.text.NumberFormat;


/**
 * A simple (symmetrical) matrix which can be used for correlation or covariance matrices. A special
 * constructor for the attributes of an example set is provided.
 *
 * @author Ingo Mierswa
 */
public class NumericalMatrix extends ResultObjectAdapter {

	private static final long serialVersionUID = -5498982791125720765L;

	private static final int MAX_NUMBER_OF_RESULT_STRING_ATTRIBUTES = 20;

	private Matrix matrix;

	private String[] columnNames;

	private String[] rowNames;

	private NumberFormat formatter;

	private String name;

	private boolean symmetrical = false;

	private String firstAttributeName = "First Attribute";

	private String secondAttributeName = "Second Attribute";

    /**
     * Instantiates a new Numerical matrix.
     *
     * @param name        the name
     * @param columnNames the column names
     * @param symmetrical the symmetrical
     */
    public NumericalMatrix(String name, String[] columnNames, boolean symmetrical) {
		this(name, columnNames, new Matrix(columnNames.length, columnNames.length), symmetrical);
	}

    /**
     * Instantiates a new Numerical matrix.
     *
     * @param name        the name
     * @param columnNames the column names
     * @param matrix      the matrix
     * @param symmetrical the symmetrical
     */
    public NumericalMatrix(String name, String[] columnNames, Matrix matrix, boolean symmetrical) {
		this.name = name;
		formatter = NumberFormat.getInstance();
		formatter.setMaximumFractionDigits(3);
		formatter.setMinimumFractionDigits(3);
		this.columnNames = columnNames;
		this.rowNames = columnNames;
		this.matrix = matrix;
		this.symmetrical = symmetrical;
	}

    /**
     * Instantiates a new Numerical matrix.
     *
     * @param name        the name
     * @param rowNames    the row names
     * @param columnNames the column names
     * @param matrix      the matrix
     */
    public NumericalMatrix(String name, String[] rowNames, String[] columnNames, Matrix matrix) {
		this.name = name;
		formatter = NumberFormat.getInstance();
		formatter.setMaximumFractionDigits(3);
		formatter.setMinimumFractionDigits(3);
		this.rowNames = rowNames;
		this.columnNames = columnNames;
		this.matrix = matrix;
		this.symmetrical = false;
	}

    /**
     * Instantiates a new Numerical matrix.
     *
     * @param name        the name
     * @param exampleSet  the example set
     * @param symmetrical the symmetrical
     */
    public NumericalMatrix(String name, ExampleSet exampleSet, boolean symmetrical) {
		this(name, getColumnNames(exampleSet), symmetrical);
	}

    /**
     * Sets first attribute name.
     *
     * @param firstAttributeName the first attribute name
     */
    public void setFirstAttributeName(String firstAttributeName) {
		this.firstAttributeName = firstAttributeName;
	}

    /**
     * Sets second attribute name.
     *
     * @param secondAttributeName the second attribute name
     */
    public void setSecondAttributeName(String secondAttributeName) {
		this.secondAttributeName = secondAttributeName;
	}

    /**
     * Gets column name.
     *
     * @param index the index
     * @return the column name
     */
    public String getColumnName(int index) {
		return this.columnNames[index];
	}

    /**
     * Gets row name.
     *
     * @param index the index
     * @return the row name
     */
    public String getRowName(int index) {
		return this.rowNames[index];
	}

	private static String[] getColumnNames(ExampleSet exampleSet) {
		String[] attributeNames = new String[exampleSet.getAttributes().size()];
		int counter = 0;
		for (Attribute attribute : exampleSet.getAttributes()) {
			attributeNames[counter++] = attribute.getName();
		}
		return attributeNames;
	}

    /**
     * Sets value.
     *
     * @param i     the
     * @param j     the j
     * @param value the value
     */
    public void setValue(int i, int j, double value) {
		this.matrix.set(i, j, value);
		if (symmetrical) {
			this.matrix.set(j, i, value);
		}
	}

    /**
     * Gets value.
     *
     * @param i the
     * @param j the j
     * @return the value
     */
    public double getValue(int i, int j) {
		return matrix.get(i, j);
	}

    /**
     * Is symmetrical boolean.
     *
     * @return the boolean
     */
    public boolean isSymmetrical() {
		return this.symmetrical;
	}

    /**
     * Gets number of columns.
     *
     * @return the number of columns
     */
    public int getNumberOfColumns() {
		return this.columnNames.length;
	}

    /**
     * Gets number of rows.
     *
     * @return the number of rows
     */
    public int getNumberOfRows() {
		return this.rowNames.length;
	}

	@Override
	public String getName() {
		return name + " Matrix";
	}

    /**
     * Create matrix data table data table.
     *
     * @return the data table
     */
    public DataTable createMatrixDataTable() {
		return new DataTableSymmetricalMatrixAdapter(this, this.name, this.columnNames);
	}

    /**
     * This creates a pairwise data table where each combination is only included once. So only one
     * triangle is returned of the matrix. This is only a good idea if the matrix is symetrically
     *
     * @return the data table
     */
    public DataTable createPairwiseDataTable() {
		return new DataTablePairwiseMatrixExtractionAdapter(this, this.rowNames, this.columnNames, new String[] {
				firstAttributeName, secondAttributeName, name });
	}

    /**
     * This creates a pairwise data table. If isSymetrical is true, only the pairs of one triangle
     * of the matrix are returned.
     *
     * @param showSymetrical the show symetrical
     * @return the data table
     */
    public DataTable createPairwiseDataTable(boolean showSymetrical) {
		return new DataTablePairwiseMatrixExtractionAdapter(this, this.rowNames, this.columnNames, new String[] {
				firstAttributeName, secondAttributeName, name }, showSymetrical);
	}

    /**
     * Gets extension.
     *
     * @return the extension
     */
    public String getExtension() {
		return "mat";
	}

    /**
     * Gets file description.
     *
     * @return the file description
     */
    public String getFileDescription() {
		return name.toLowerCase() + " matrix";
	}

	@Override
	public String toString() {
		StringBuffer result = new StringBuffer(name + " Matrix (" + matrix.getRowDimension() + " rows, "
				+ matrix.getColumnDimension() + " columns):" + Tools.getLineSeparator());
		for (int i = 0; i < columnNames.length; i++) {
			if (i < MAX_NUMBER_OF_RESULT_STRING_ATTRIBUTES) {
				result.append("\t" + columnNames[i]);
			} else {
				result.append("...");
				break;
			}
		}

		for (int i = 0; i < matrix.getRowDimension(); i++) {
			if (i < MAX_NUMBER_OF_RESULT_STRING_ATTRIBUTES) {
				result.append(Tools.getLineSeparator() + columnNames[i]);
				for (int j = 0; j < matrix.getColumnDimension(); j++) {
					if (j < MAX_NUMBER_OF_RESULT_STRING_ATTRIBUTES) {
						result.append("\t" + formatter.format(matrix.get(i, j)));
					} else {
						result.append("...");
						break;
					}
				}
			} else {
				result.append(Tools.getLineSeparator() + "...");
				break;
			}
		}
		return result.toString();
	}
}
