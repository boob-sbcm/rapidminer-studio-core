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
package com.rapidminer.gui.renderer.models;

import com.rapidminer.gui.renderer.AbstractTableModelTableRenderer;
import com.rapidminer.operator.IOContainer;
import com.rapidminer.operator.features.transformation.AbstractEigenvectorModel;
import com.rapidminer.operator.features.transformation.ComponentVector;
import com.rapidminer.tools.Tools;

import java.util.List;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;


/**
 * The type Eigenvector model eigenvector renderer.
 *
 * @author Sebastian Land
 */
public class EigenvectorModelEigenvectorRenderer extends AbstractTableModelTableRenderer {

    /**
     * The type Eigenvector table model.
     */
    public static class EigenvectorTableModel extends AbstractTableModel {

		private static final long serialVersionUID = -9026248524043239399L;

		private String[] attributeNames;

		private List<? extends ComponentVector> eigenVectors;

		private int numberOfComponents;

        /**
         * Instantiates a new Eigenvector table model.
         *
         * @param eigenVectors       the eigen vectors
         * @param attributeNames     the attribute names
         * @param numberOfComponents the number of components
         */
        public EigenvectorTableModel(List<? extends ComponentVector> eigenVectors, String[] attributeNames,
				int numberOfComponents) {
			this.eigenVectors = eigenVectors;
			this.attributeNames = attributeNames;
			this.numberOfComponents = numberOfComponents;
		}

		@Override
		public int getColumnCount() {
			return eigenVectors.size() + 1;
		}

		@Override
		public int getRowCount() {
			return numberOfComponents;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			if (columnIndex == 0) {
				return attributeNames[rowIndex];
			} else {
				return Tools.formatNumber(eigenVectors.get(columnIndex - 1).getVector()[rowIndex]);
			}
		}

		@Override
		public String getColumnName(int column) {
			if (column == 0) {
				return "Attribute";
			} else {
				return "PC " + column;
			}
		}
	}

	@Override
	public String getName() {
		return "Eigenvectors";
	}

	@Override
	public TableModel getTableModel(Object renderable, IOContainer ioContainer, boolean isReporting) {
		AbstractEigenvectorModel model = (AbstractEigenvectorModel) renderable;
		return model.getEigenvectorTableModel();
	}
}
