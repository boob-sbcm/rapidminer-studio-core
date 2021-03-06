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
package com.rapidminer.operator.nio;

import com.rapidminer.operator.nio.model.ColumnMetaData;
import com.rapidminer.operator.nio.model.ParsingError;

import java.util.*;
import java.util.Map.Entry;


/**
 * Validates the MetaData set by the user in the MetaDataDeclarationWizardStep which is a part of
 * importing e.g. an Excel file.
 *
 * @author Dominik Halfkann
 */
public class MetaDataValidator extends Observable {

	private List<ParsingError> errorList = new ArrayList<ParsingError>();

    /**
     * The Meta data to col num map.
     */
    Map<ColumnMetaData, Integer> metaDataToColNumMap = new HashMap<ColumnMetaData, Integer>();

    /**
     * The Column roles.
     */
    Map<String, List<Integer>> columnRoles = new HashMap<String, List<Integer>>();
    /**
     * The Column names.
     */
    Map<String, List<Integer>> columnNames = new HashMap<String, List<Integer>>();

    /**
     * The Old duplicate name column.
     */
    List<Integer> oldDuplicateNameColumn = new ArrayList<Integer>();
    /**
     * The Duplicate name column.
     */
    List<Integer> duplicateNameColumn = new ArrayList<Integer>();

    /**
     * The Old duplicate role column.
     */
    List<Integer> oldDuplicateRoleColumn = new ArrayList<Integer>();
    /**
     * The Duplicate role column.
     */
    List<Integer> duplicateRoleColumn = new ArrayList<Integer>();

	private void updateColumnMaps(ColumnMetaData cmd) {
		int updatedColumnNum = metaDataToColNumMap.get(cmd);
		deleteColumnNumFromMaps(updatedColumnNum);

		if (cmd.isSelected()) {
			// name
			List<Integer> columnsForName = columnNames.get(cmd.getUserDefinedAttributeName());
			if (columnsForName == null) {
				// if there isn't a list for that name, create one
				List<Integer> columns = new ArrayList<Integer>();
				columns.add(updatedColumnNum);
				columnNames.put(cmd.getUserDefinedAttributeName(), columns);
			} else {
				// if there is already a list for that name, add column
				columnsForName.add(updatedColumnNum);
			}

			// role
			List<Integer> columnsForRole = columnRoles.get(cmd.getRole());
			if (columnsForRole == null) {
				// if there isn't a list for that role, create one
				List<Integer> columns = new ArrayList<Integer>();
				columns.add(updatedColumnNum);
				columnRoles.put(cmd.getRole(), columns);
			} else {
				// if there is already a list for that role, add column
				columnsForRole.add(updatedColumnNum);
			}
		}
	}

	private void validate(ColumnMetaData cmd) {
		updateColumnMaps(cmd);
		checkForDuplicates();
	}

    /**
     * Check for duplicates.
     */
    public void checkForDuplicates() {
		oldDuplicateNameColumn.clear();
		oldDuplicateNameColumn.addAll(duplicateNameColumn);
		duplicateNameColumn.clear();

		oldDuplicateRoleColumn.clear();
		oldDuplicateRoleColumn.addAll(duplicateRoleColumn);
		duplicateRoleColumn.clear();

		errorList = new ArrayList<ParsingError>();

		for (Entry<String, List<Integer>> roleEntry : columnRoles.entrySet()) {
			if (roleEntry.getValue().size() > 1 && !roleEntry.getKey().equals("attribute")) {
				errorList.add(new ParsingError(roleEntry.getValue(), ParsingError.ErrorCode.SAME_ROLE_FOR_MULTIPLE_COLUMNS,
						roleEntry.getKey()));
				duplicateRoleColumn.addAll(roleEntry.getValue());
			}
		}

		for (Entry<String, List<Integer>> nameEntry : columnNames.entrySet()) {
			if (nameEntry.getValue().size() > 1) {
				errorList.add(new ParsingError(nameEntry.getValue(), ParsingError.ErrorCode.SAME_NAME_FOR_MULTIPLE_COLUMNS,
						nameEntry.getKey()));
				duplicateNameColumn.addAll(nameEntry.getValue());
			}
		}
		checkIfUpdate();
	}

	private void checkIfUpdate() {
		if (!oldDuplicateNameColumn.equals(duplicateNameColumn) || !oldDuplicateRoleColumn.equals(duplicateRoleColumn)) {
			// if one of the duplicates lists aren't equal, fire update
			Set<Integer> columnsUpdate = new HashSet<Integer>();
			columnsUpdate.addAll(oldDuplicateNameColumn);
			columnsUpdate.addAll(duplicateNameColumn);
			columnsUpdate.addAll(oldDuplicateRoleColumn);
			columnsUpdate.addAll(duplicateRoleColumn);

			this.setChanged();
			this.notifyObservers(columnsUpdate);
		}
	}

    /**
     * Gets errors.
     *
     * @return the errors
     */
    public List<ParsingError> getErrors() {
		return errorList;
	}

    /**
     * Add column meta data.
     *
     * @param cmd    the cmd
     * @param column the column
     */
    public void addColumnMetaData(ColumnMetaData cmd, int column) {
		metaDataToColNumMap.put(cmd, column);
		cmd.addObserver(new Observer() {

			@Override
			public void update(Observable o, Object arg) {
				if (o instanceof ColumnMetaData) {
					ColumnMetaData cmd = (ColumnMetaData) o;
					validate(cmd);
				}
			}

		});
		updateColumnMaps(cmd);
	}

	private void deleteColumnNumFromMaps(int columnNumber) {
		for (Entry<String, List<Integer>> nameEntry : columnNames.entrySet()) {
			if (nameEntry.getValue() != null) {
				nameEntry.getValue().remove((Integer) columnNumber);
			}
		}
		for (Entry<String, List<Integer>> roleEntry : columnRoles.entrySet()) {
			if (roleEntry.getValue() != null) {
				roleEntry.getValue().remove((Integer) columnNumber);
			}
		}
	}

    /**
     * Is duplicate name column boolean.
     *
     * @param column the column
     * @return the boolean
     */
    public boolean isDuplicateNameColumn(int column) {
		return duplicateNameColumn.contains(column);
	}

    /**
     * Is duplicate role column boolean.
     *
     * @param column the column
     * @return the boolean
     */
    public boolean isDuplicateRoleColumn(int column) {
		return duplicateRoleColumn.contains(column);
	}

}
