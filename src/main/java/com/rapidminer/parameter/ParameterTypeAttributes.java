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

import com.rapidminer.operator.ports.InputPort;
import com.rapidminer.tools.Ontology;
import com.rapidminer.tools.XMLException;

import org.w3c.dom.Element;


/**
 * A parameter type for selecting several attributes. This is merely a copy of the
 * {@link ParameterTypeAttribute}, since it already comes with all needed functions. But we register
 * a different CellRenderer for this class.
 *
 * @author Tobias Malbrecht, Sebastian Land
 */
public class ParameterTypeAttributes extends ParameterTypeAttribute {

	private static final long serialVersionUID = -4177652183651031337L;

    /**
     * The constant ATTRIBUTE_SEPARATOR_CHARACTER.
     */
    public static final String ATTRIBUTE_SEPARATOR_CHARACTER = "|";

    /**
     * The constant ATTRIBUTE_SEPARATOR_REGEX.
     */
    public static final String ATTRIBUTE_SEPARATOR_REGEX = "\\|";

    /**
     * Instantiates a new Parameter type attributes.
     *
     * @param element the element
     * @throws XMLException the xml exception
     */
    public ParameterTypeAttributes(Element element) throws XMLException {
		super(element);
	}

    /**
     * Instantiates a new Parameter type attributes.
     *
     * @param key         the key
     * @param description the description
     * @param inPort      the in port
     */
    public ParameterTypeAttributes(final String key, String description, InputPort inPort) {
		this(key, description, inPort, true, Ontology.ATTRIBUTE_VALUE);
	}

    /**
     * Instantiates a new Parameter type attributes.
     *
     * @param key         the key
     * @param description the description
     * @param inPort      the in port
     * @param valueTypes  the value types
     */
    public ParameterTypeAttributes(final String key, String description, InputPort inPort, int... valueTypes) {
		this(key, description, inPort, true, valueTypes);
	}

    /**
     * Instantiates a new Parameter type attributes.
     *
     * @param key         the key
     * @param description the description
     * @param inPort      the in port
     * @param optional    the optional
     */
    public ParameterTypeAttributes(final String key, String description, InputPort inPort, boolean optional) {
		this(key, description, inPort, optional, Ontology.ATTRIBUTE_VALUE);
	}

    /**
     * Instantiates a new Parameter type attributes.
     *
     * @param key         the key
     * @param description the description
     * @param inPort      the in port
     * @param optional    the optional
     * @param valueTypes  the value types
     */
    public ParameterTypeAttributes(final String key, String description, InputPort inPort, boolean optional,
			int... valueTypes) {
		super(key, description, inPort, optional, valueTypes);
	}

    /**
     * Instantiates a new Parameter type attributes.
     *
     * @param key         the key
     * @param description the description
     * @param inPort      the in port
     * @param optional    the optional
     * @param expert      the expert
     */
    public ParameterTypeAttributes(final String key, String description, InputPort inPort, boolean optional, boolean expert) {
		this(key, description, inPort, optional);
		setExpert(expert);
	}
}
