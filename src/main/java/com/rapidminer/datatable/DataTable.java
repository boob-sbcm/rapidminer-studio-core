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

import com.rapidminer.Process;
import com.rapidminer.gui.plotter.charts.AbstractChartPanel.Selection;
import com.rapidminer.report.Tableable;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;


/**
 * A data table that contains Object arrays that record process results, data, etc. Instances of
 * this class are automatically created by {@link Process#getDataTables()} and are used mainly by
 * the {@link com.rapidminer.operator.visualization.ProcessLogOperator}. On the other hand, also
 * {@link com.rapidminer.example.ExampleSet}s can also be used as an data table object.
 *
 * @author Ingo Mierswa
 */
public interface DataTable extends Iterable<DataTableRow>, Tableable {

    /**
     * Indicates if the column with the given index is nominal. For numerical or date columns, the
     * value false should be returned.
     *
     * @param index the index
     * @return the boolean
     */
    public boolean isNominal(int index);

    /**
     * Indicates if the column with the given index is nominal. For numerical or date columns, the
     * value false should be returned.
     *
     * @param index the index
     * @return the boolean
     */
    public boolean isTime(int index);

    /**
     * Indicates if the column with the given index is nominal. For numerical or date columns, the
     * value false should be returned.
     *
     * @param index the index
     * @return the boolean
     */
    public boolean isDate(int index);

    /**
     * Indicates if the column with the given index is nominal. For numerical or date columns, the
     * value false should be returned.
     *
     * @param index the index
     * @return the boolean
     */
    public boolean isDateTime(int index);

    /**
     * Indicates if the column with the given index is nominal. For numerical or date columns, the
     * value false should be returned.
     *
     * @param index the index
     * @return the boolean
     */
    public boolean isNumerical(int index);

    /**
     * If a column is nominal, the index value must be mapped to the nominal value by this method.
     * If the given column is not nominal, this method might throw a {@link NullPointerException}.
     *
     * @param column the column
     * @param index  the index
     * @return the string
     */
    public String mapIndex(int column, int index);

    /**
     * If a column is nominal, the nominal value must be mapped to a (new) index by this method. If
     * the given column is not nominal, this method might throw a {@link NullPointerException}.
     *
     * @param column the column
     * @param value  the value
     * @return the int
     */
    public int mapString(int column, String value);

	/** Returns the name of the i-th column. */
	@Override
	public String getColumnName(int i);

    /**
     * Returns the column index of the column with the given name.  @param name the name
     *
     * @param name the name
     * @return the column index
     */
    public int getColumnIndex(String name);

    /**
     * Returns the weight of the column or Double.NaN if no weight is available.  @param i the
     *
     * @param i the
     * @return the column weight
     */
    public double getColumnWeight(int i);

    /**
     * Returns true if this data table is supporting column weights.  @return the boolean
     *
     * @return the boolean
     */
    public boolean isSupportingColumnWeights();

    /**
     * Returns the total number of columns.  @return the number of columns
     *
     * @return the number of columns
     */
    public int getNumberOfColumns();

    /**
     * Returns the total number of special columns. Please note that these columns do not need to be
     * in an ordered sequence. In order to make sure that a column is a special column the method
     * {@link #isSpecial(int)} should be used.
     *
     * @return the number of special columns
     */
    public int getNumberOfSpecialColumns();

    /**
     * Returns true if this column is a special column which might usually not be used for some
     * plotters, for example weights or labels.
     *
     * @param column the column
     * @return the boolean
     */
    public boolean isSpecial(int column);

    /**
     * Returns an array of all column names.  @return the string [ ]
     *
     * @return the string [ ]
     */
    public String[] getColumnNames();

    /**
     * Returns the name of this data table.  @return the name
     *
     * @return the name
     */
    public String getName();

    /**
     * Sets the name of the data table.  @param name the name
     *
     * @param name the name
     */
    public void setName(String name);

    /**
     * Adds the given {@link DataTableRow} to the table.  @param row the row
     *
     * @param row the row
     */
    public void add(DataTableRow row);

	/** Returns an iterator over all {@link DataTableRow}s. */
	@Override
	public Iterator<DataTableRow> iterator();

    /**
     * Returns the data table row with the given index. Please note that this method is not
     * guaranteed to be efficiently implemented. If you want to scan the complete data table you
     * should use the iterator() method instead.
     *
     * @param index the index
     * @return the row
     */
    public DataTableRow getRow(int index);

    /**
     * Returns the total number of rows.  @return the number of rows
     *
     * @return the number of rows
     */
    public int getNumberOfRows();

    /**
     * Returns the number of different values for the i-th column. Might return -1 or throw an
     * exception if the specific column is not nominal.
     *
     * @param column the column
     * @return the number of values
     */
    public int getNumberOfValues(int column);

    /**
     * Must deliver the proper value as string, i.e. the mapped value for nominal columns.  @param row the row
     *
     * @param row    the row
     * @param column the column
     * @return the value as string
     */
    public String getValueAsString(DataTableRow row, int column);

    /**
     * Adds a table listener listening for data changes.  @param dataTableListener the data table listener
     *
     * @param dataTableListener the data table listener
     */
    public void addDataTableListener(DataTableListener dataTableListener);

    /**
     * Adds a table listener listening for data changes.
     *
     * @param dataTableListener the data table listener
     * @param weakReference     if true, the listener is stored in a weak reference, so that the listener            mechanism does not keep garbage collection from deleting the listener.
     */
    public void addDataTableListener(DataTableListener dataTableListener, boolean weakReference);

    /**
     * Removes the given listener from the list of data change listeners.  @param dataTableListener the data table listener
     *
     * @param dataTableListener the data table listener
     */
    public void removeDataTableListener(DataTableListener dataTableListener);

    /**
     * Writes the table into the given writer.  @param out the out
     *
     * @param out the out
     * @throws IOException the io exception
     */
    public void write(PrintWriter out) throws IOException;

    /**
     * Performs a sampling of this data table. Following operations should only work on the sample.  @param newSize the new size
     *
     * @param newSize the new size
     * @return the data table
     */
    public DataTable sample(int newSize);

    /**
     * Returns true if this data table contains missing values.  @return the boolean
     *
     * @return the boolean
     */
    public boolean containsMissingValues();

    /**
     * Is deselected boolean.
     *
     * @param id the id
     * @return the boolean
     */
// public boolean isDeselected(int index);
	public boolean isDeselected(String id);

    /**
     * Sets selection.
     *
     * @param selection the selection
     */
    public void setSelection(Selection selection);

    /**
     * Gets selection count.
     *
     * @return the selection count
     */
    public int getSelectionCount();
}
