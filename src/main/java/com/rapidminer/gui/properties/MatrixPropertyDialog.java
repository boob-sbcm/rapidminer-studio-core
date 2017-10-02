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

import com.rapidminer.gui.tools.ExtendedJScrollPane;
import com.rapidminer.gui.tools.ResourceAction;
import com.rapidminer.operator.Operator;
import com.rapidminer.parameter.ParameterTypeMatrix;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Collection;
import java.util.LinkedList;


/**
 * A Dialog displaying a {@link MatrixPropertyTable}. This can be used to add new values to the
 * parameter matrix or change current values. Removal of values is also supported.
 *
 * @author Helge Homburg, Tobias Malbrecht
 * @see com.rapidminer.gui.properties.MatrixPropertyTable
 */
public class MatrixPropertyDialog extends PropertyDialog {

	private static final long serialVersionUID = 5396725165122306231L;

	private boolean isSquared = false;

	private final MatrixPropertyTable matrixPropertyTable;

    /**
     * Instantiates a new Matrix property dialog.
     *
     * @param type     the type
     * @param matrix   the matrix
     * @param operator the operator
     */
    public MatrixPropertyDialog(final ParameterTypeMatrix type, double[][] matrix, Operator operator) {
		super(type, "matrix");

		this.isSquared = type.isSquared();
		matrixPropertyTable = new MatrixPropertyTable(type.getBaseName(), type.getRowBaseName(), type.getColumnBaseName(),
				matrix, operator);

		Collection<AbstractButton> buttons = new LinkedList<AbstractButton>();
		if (!this.isSquared) {
			buttons.add(new JButton(new ResourceAction("matrix.add_row") {

				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(ActionEvent e) {
					matrixPropertyTable.addRow();
				}
			}));
			buttons.add(new JButton(new ResourceAction("matrix.add_column") {

				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(ActionEvent e) {
					matrixPropertyTable.addColumn();
				}
			}));
			buttons.add(new JButton(new ResourceAction("matrix.remove_row") {

				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(ActionEvent e) {
					matrixPropertyTable.removeSelectedRow();
				}
			}));
			buttons.add(new JButton(new ResourceAction("matrix.remove_column") {

				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(ActionEvent e) {
					matrixPropertyTable.removeSelectedColumn();
				}
			}));
		} else {
			buttons.add(new JButton(new ResourceAction("matrix.increase_size") {

				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(ActionEvent e) {
					matrixPropertyTable.addRow();
					matrixPropertyTable.addColumn();
					matrixPropertyTable.fillNewRowAndColumn();
				}
			}));
			buttons.add(new JButton(new ResourceAction("matrix.decrease_size") {

				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(ActionEvent e) {
					matrixPropertyTable.removeSelectedRowAndColumn();

				}
			}));
		}
		buttons.add(makeOkButton());
		buttons.add(makeCancelButton());
		JScrollPane scrollPane = new ExtendedJScrollPane(matrixPropertyTable);
		scrollPane.setBorder(createBorder());
		layoutDefault(scrollPane, NORMAL, buttons.toArray(new AbstractButton[buttons.size()]));
	}

    /**
     * Get matrix double [ ] [ ].
     *
     * @return the double [ ] [ ]
     */
    public double[][] getMatrix() {
		return matrixPropertyTable.getParameterMatrix();
	}
}
