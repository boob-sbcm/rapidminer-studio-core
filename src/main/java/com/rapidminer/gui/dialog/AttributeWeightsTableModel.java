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
package com.rapidminer.gui.dialog;

import com.rapidminer.example.AttributeWeights;
import com.rapidminer.gui.properties.celleditors.value.PropertyValueCellEditor;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;


/**
 * This table model is used by an {@link AttributeWeightsDialog}. It is used to show attribute
 * weights created by the process, by the user, or were loaded from a file. Several view modes and
 * sorting are supported.
 *
 * @author Ingo Mierswa ingomierswa Exp $
 */
public class AttributeWeightsTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 6151252627227324898L;

    /**
     * The constant VIEW_ALL.
     */
    public static final int VIEW_ALL = 0;

    /**
     * The constant VIEW_FILE.
     */
    public static final int VIEW_FILE = 1;

    /**
     * The constant VIEW_PROCESS.
     */
    public static final int VIEW_PROCESS = 2;

    /**
     * The constant VIEW_UPDATED.
     */
    public static final int VIEW_UPDATED = 3;

    /**
     * The constant VIEW_SELECTED.
     */
    public static final int VIEW_SELECTED = 4;

    /**
     * The constant VIEW_MODES.
     */
    public static final String[] VIEW_MODES = { "Show all", "Show from file", "Show from process", "Show updated",
			"Show selected" };

	private static class State {

        /**
         * The constant SOURCE_PROCESS.
         */
        public static final int SOURCE_PROCESS = 1;

        /**
         * The constant SOURCE_FILE.
         */
        public static final int SOURCE_FILE = 2;

        /**
         * The constant SOURCE_BOTH.
         */
        public static final int SOURCE_BOTH = 3;

		private int source = SOURCE_PROCESS;

		private double oldWeight = 0.0d;

		private boolean updated = false;

        /**
         * Instantiates a new State.
         *
         * @param source    the source
         * @param oldWeight the old weight
         */
        public State(int source, double oldWeight) {
			this.source = source;
			this.oldWeight = oldWeight;
		}

        /**
         * Gets source.
         *
         * @return the source
         */
        public int getSource() {
			return source;
		}

        /**
         * Sets source.
         *
         * @param source the source
         */
        public void setSource(int source) {
			this.source = source;
		}

        /**
         * Is updated boolean.
         *
         * @return the boolean
         */
        public boolean isUpdated() {
			return updated;
		}

        /**
         * Sets updated.
         *
         * @param updated the updated
         */
        public void setUpdated(boolean updated) {
			this.updated = updated;
		}

        /**
         * Gets old weight.
         *
         * @return the old weight
         */
        public double getOldWeight() {
			return oldWeight;
		}
	}

	private transient AttributeWeights weights;

	private String[] attributeNames;

	private PropertyValueCellEditor[] editors;

	private Map<String, State> updateMap = new HashMap<String, State>();

	private int viewMode = VIEW_ALL;

	private boolean overwrite = false;

	private double minWeight = Double.NEGATIVE_INFINITY;

	private int selectionCount = 0;

    /**
     * Instantiates a new Attribute weights table model.
     *
     * @param weights the weights
     */
    public AttributeWeightsTableModel(AttributeWeights weights) {
		if (weights != null) {
			this.weights = (AttributeWeights) weights.clone();
		} else {
			this.weights = new AttributeWeights();
		}
		for (String attributeName : this.weights.getAttributeNames()) {
			double oldWeight = this.weights.getWeight(attributeName);
			updateMap.put(attributeName, new State(State.SOURCE_PROCESS, oldWeight));
		}
		updateTable();
	}

    /**
     * Read resolve object.
     *
     * @return the object
     */
    protected Object readResolve() {
		this.weights = new AttributeWeights();
		return this;
	}

	@Override
	public Object getValueAt(int row, int column) {
		if (column == 0) {
			return attributeNames[row];
		} else if (column == 1) {
			return Double.valueOf(weights.getWeight(attributeNames[row]));
		} else {
			return null;
		}
	}

	@Override
	public void setValueAt(Object value, int row, int column) {
		double weight = weights.getWeight(attributeNames[row]);
		try {
			weight = Double.parseDouble((String) value);
		} catch (NumberFormatException e) {
		}
		weights.setWeight(attributeNames[row], weight);
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		if (column == 0) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public int getRowCount() {
		return attributeNames.length;
	}

	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public String getColumnName(int column) {
		switch (column) {
			case 0:
				return "Attribute";
			case 1:
				return "Weight";
			default:
				return "?";
		}
	}

	// ================================================================================

    /**
     * Gets attribute weights.
     *
     * @return the attribute weights
     */
    public AttributeWeights getAttributeWeights() {
		return weights;
	}

    /**
     * Gets weight editor.
     *
     * @param row the row
     * @return the weight editor
     */
    public PropertyValueCellEditor getWeightEditor(int row) {
		return editors[row];
	}

    /**
     * Sets view mode.
     *
     * @param mode the mode
     */
    public void setViewMode(int mode) {
		this.viewMode = mode;
	}

    /**
     * Indicates if values which are merged with the current weights should overwrite them. Please
     * note that a value of zero always overwrites the current weight!
     *
     * @param overwrite the overwrite
     */
    public void setOverwriteMode(boolean overwrite) {
		this.overwrite = overwrite;
	}

    /**
     * Sets min weight.
     *
     * @param minWeight the min weight
     */
    public void setMinWeight(double minWeight) {
		this.minWeight = minWeight;
	}

    /**
     * Gets min weight.
     *
     * @return the min weight
     */
    public double getMinWeight() {
		return minWeight;
	}

    /**
     * Merge weights.
     *
     * @param fileWeights the file weights
     */
    public void mergeWeights(AttributeWeights fileWeights) {
		for (String attributeName : fileWeights.getAttributeNames()) {
			double fileWeight = fileWeights.getWeight(attributeName);
			double processWeight = weights.getWeight(attributeName);

			if (fileWeight == 0.0d) {
				weights.setWeight(attributeName, 0.0d);
			} else if (overwrite) {
				weights.setWeight(attributeName, fileWeight);
			} else {
				if (Double.isNaN(weights.getWeight(attributeName))) { // overwrite
																		// only
																		// if
																		// not
																		// set
																		// by
																		// process
					weights.setWeight(attributeName, fileWeight);
				}
			}

			// update existing state
			State state = updateMap.get(attributeName);
			if (state != null) {
				if (state.getSource() == State.SOURCE_PROCESS) {
					state.setSource(State.SOURCE_BOTH);
				}
				if (fileWeight != processWeight) {
					state.setUpdated(true);
				}
			} else { // add new state
				updateMap.put(attributeName, new State(State.SOURCE_FILE, weights.getWeight(attributeName)));
			}
		}
	}

    /**
     * Gets number of selected.
     *
     * @return the number of selected
     */
    public int getNumberOfSelected() {
		return selectionCount;
	}

    /**
     * Gets total number.
     *
     * @return the total number
     */
    public int getTotalNumber() {
		return this.weights.size();
	}

	// ================================================================================

    /**
     * Update table.
     */
    public void updateTable() {
		// attribute names
		Iterator<String> i = this.weights.getAttributeNames().iterator();
		List<String> names = new LinkedList<String>();
		this.selectionCount = 0;
		while (i.hasNext()) {
			String attributeName = i.next();
			double weight = weights.getWeight(attributeName);
			if (weight != 0.0d) {
				selectionCount++;
			}
			if (weight >= minWeight) {
				State state = updateMap.get(attributeName);
				switch (viewMode) {
					case VIEW_FILE:
						if (state.getSource() == State.SOURCE_FILE || state.getSource() == State.SOURCE_BOTH) {
							names.add(attributeName);
						}
						break;
					case VIEW_PROCESS:
						if (state.getSource() == State.SOURCE_PROCESS || state.getSource() == State.SOURCE_BOTH) {
							names.add(attributeName);
						}
						break;
					case VIEW_UPDATED:
						if (state.isUpdated()) {
							names.add(attributeName);
						}
						break;
					case VIEW_SELECTED:
						if (weights.getWeight(attributeName) != 0.0d) {
							names.add(attributeName);
						}
						break;
					default:
						names.add(attributeName);
						break;
				}
			}
		}
		attributeNames = new String[names.size()];
		names.toArray(attributeNames);

		// weight editors
		editors = new PropertyValueCellEditor[attributeNames.length];
		for (int k = 0; k < editors.length; k++) {
			editors[k] = new AttributeWeightCellEditor(updateMap.get(attributeNames[k]).getOldWeight());
		}

		// repaint
		super.fireTableChanged(new TableModelEvent(this));
	}
}
