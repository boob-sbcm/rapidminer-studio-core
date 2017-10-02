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
package com.rapidminer.parameter;

import com.rapidminer.io.process.XMLTools;
import com.rapidminer.tools.XMLException;
import org.w3c.dom.Element;


/**
 * A parameter type for parameter matrices. Operators ask for the matrix of the specified values
 * with {@link com.rapidminer.operator.Operator#getParameterAsMatrix(String)}.
 *
 * @author Helge Homburg, Ingo Mierswa
 */
public class ParameterTypeMatrix extends ParameterTypeString {

	private static final long serialVersionUID = 0L;

	private static final String ATTRIBUTE_IS_SQUARED = null;

	private static final String ELEMENT_NAME = null;

	private static final String ELEMENT_COLUMN_NAME = null;

	private static final String ELEMENT_ROW_NAME = null;

	private boolean isSquared = false;

	private String baseName;

	private String rowBaseName;

	private String columnBaseName;

    /**
     * Instantiates a new Parameter type matrix.
     *
     * @param element the element
     * @throws XMLException the xml exception
     */
    public ParameterTypeMatrix(Element element) throws XMLException {
		super(element);

		isSquared = Boolean.valueOf(element.getAttribute(ATTRIBUTE_IS_SQUARED));
		baseName = XMLTools.getTagContents(element, ELEMENT_NAME, true);
		rowBaseName = XMLTools.getTagContents(element, ELEMENT_ROW_NAME, true);
		columnBaseName = XMLTools.getTagContents(element, ELEMENT_COLUMN_NAME, true);
	}

    /**
     * Instantiates a new Parameter type matrix.
     *
     * @param key            the key
     * @param description    the description
     * @param baseName       the base name
     * @param rowBaseName    the row base name
     * @param columnBaseName the column base name
     * @param isSquared      the is squared
     */
    public ParameterTypeMatrix(String key, String description, String baseName, String rowBaseName, String columnBaseName,
			boolean isSquared) {
		this(key, description, baseName, rowBaseName, columnBaseName, isSquared, true);
	}

    /**
     * Instantiates a new Parameter type matrix.
     *
     * @param key            the key
     * @param description    the description
     * @param baseName       the base name
     * @param rowBaseName    the row base name
     * @param columnBaseName the column base name
     * @param isSquared      the is squared
     * @param isOptional     the is optional
     */
    public ParameterTypeMatrix(String key, String description, String baseName, String rowBaseName, String columnBaseName,
			boolean isSquared, boolean isOptional) {
		super(key, description, isOptional);
		this.isSquared = isSquared;
		this.baseName = baseName;
		this.rowBaseName = rowBaseName;
		this.columnBaseName = columnBaseName;
	}

    /**
     * Is squared boolean.
     *
     * @return the boolean
     */
    public boolean isSquared() {
		return isSquared;
	}

    /**
     * Sets squared.
     *
     * @param isSquared the is squared
     */
    public void setSquared(boolean isSquared) {
		this.isSquared = isSquared;
	}

    /**
     * Gets base name.
     *
     * @return the base name
     */
    public String getBaseName() {
		return baseName;
	}

    /**
     * Sets base name.
     *
     * @param baseName the base name
     */
    public void setBaseName(String baseName) {
		this.baseName = baseName;
	}

    /**
     * Gets row base name.
     *
     * @return the row base name
     */
    public String getRowBaseName() {
		return rowBaseName;
	}

    /**
     * Sets row base name.
     *
     * @param rowBaseName the row base name
     */
    public void setRowBaseName(String rowBaseName) {
		this.rowBaseName = rowBaseName;
	}

    /**
     * Gets column base name.
     *
     * @return the column base name
     */
    public String getColumnBaseName() {
		return columnBaseName;
	}

    /**
     * Sets column base name.
     *
     * @param columnBaseName the column base name
     */
    public void setColumnBaseName(String columnBaseName) {
		this.columnBaseName = columnBaseName;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @return always {@code false}
	 */
	@Override
	public boolean isSensitive() {
		return false;
	}

	@Override
	protected void writeDefinitionToXML(Element typeElement) {
		super.writeDefinitionToXML(typeElement);

		typeElement.setAttribute(ATTRIBUTE_IS_SQUARED, isSquared + "");
		XMLTools.setTagContents(typeElement, ELEMENT_NAME, baseName);
		XMLTools.setTagContents(typeElement, ELEMENT_ROW_NAME, rowBaseName);
		XMLTools.setTagContents(typeElement, ELEMENT_COLUMN_NAME, columnBaseName);
	}
}
