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

import org.fife.ui.rsyntaxtextarea.SyntaxConstants;

import java.io.Serializable;


/**
 * The possible text types for the {@link ParameterTypeText} type.
 *
 * @author Ingo Mierswa
 */
public enum TextType implements Serializable {

    /**
     * Plain text type.
     */
    PLAIN(SyntaxConstants.SYNTAX_STYLE_NONE, false, false), /**
     * Xml text type.
     */
    XML(SyntaxConstants.SYNTAX_STYLE_XML, true, false), /**
     * Html text type.
     */
    HTML(
			SyntaxConstants.SYNTAX_STYLE_HTML, true, false), /**
     * Sql text type.
     */
    SQL(SyntaxConstants.SYNTAX_STYLE_SQL, false, true), /**
     * Java text type.
     */
    JAVA(
					SyntaxConstants.SYNTAX_STYLE_JAVA, true, true), /**
     * Groovy text type.
     */
    GROOVY(SyntaxConstants.SYNTAX_STYLE_GROOVY, true, true), /**
     * Python text type.
     */
    PYTHON(
			SyntaxConstants.SYNTAX_STYLE_PYTHON, true, true), /**
     * R text type.
     */
    R("text/r", true, true);

	private String syntaxIdentifier;
	private boolean isAutoIntending;
	private boolean isBracketMatching;

	TextType(String syntaxIdentifier, boolean isAutoIntending, boolean isBracketMatching) {
		this.syntaxIdentifier = syntaxIdentifier;
		this.isAutoIntending = isAutoIntending;
		this.isBracketMatching = isBracketMatching;
	}

    /**
     * Gets syntax identifier.
     *
     * @return the syntax identifier
     */
    public String getSyntaxIdentifier() {
		return syntaxIdentifier;
	}

    /**
     * Is auto intending boolean.
     *
     * @return the boolean
     */
    public boolean isAutoIntending() {
		return isAutoIntending;
	}

    /**
     * Is bracket matching boolean.
     *
     * @return the boolean
     */
    public boolean isBracketMatching() {
		return isBracketMatching;
	}
}
