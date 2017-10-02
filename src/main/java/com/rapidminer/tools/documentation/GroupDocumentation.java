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
package com.rapidminer.tools.documentation;

import com.rapidminer.io.process.XMLTools;
import com.rapidminer.tools.GroupTree;
import org.w3c.dom.Element;


/**
 * Documentation for a {@link GroupTree}.
 *
 * @author Simon Fischer
 */
public class GroupDocumentation {

	private final String key;
	private final String name;
	private final String help;

    /**
     * Instantiates a new Group documentation.
     *
     * @param key the key
     */
    public GroupDocumentation(String key) {
		this.key = key;
		this.name = keyToUpperCase(key);
		this.help = "The group '" + name + "'.";
	}

    /**
     * Instantiates a new Group documentation.
     *
     * @param key  the key
     * @param name the name
     * @param help the help
     */
    public GroupDocumentation(String key, String name, String help) {
		this.key = key;
		this.name = name;
		this.help = help;
	}

    /**
     * Instantiates a new Group documentation.
     *
     * @param element the element
     */
    GroupDocumentation(Element element) {
		this.key = XMLTools.getTagContents(element, "key");
		this.name = XMLTools.getTagContents(element, "name");
		this.help = XMLTools.getTagContents(element, "help");
	}

    /**
     * Gets key.
     *
     * @return the key
     */
    public String getKey() {
		return key;
	}

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
		return name;
	}

    /**
     * Gets help.
     *
     * @return the help
     */
    public String getHelp() {
		return help != null ? help : "";
	}

	@Override
	public String toString() {
		return key + ": " + name;
	}

    /**
     * Key to upper case string.
     *
     * @param key the key
     * @return the string
     */
    public static String keyToUpperCase(String key) {
		String name = key;
		if (name.indexOf('.') >= 0) {
			name = name.substring(name.lastIndexOf('.') + 1);
		}
		name = name.replace('_', ' ');
		char[] chars = name.toCharArray();
		boolean makeUppercase = true;
		for (int i = 0; i < chars.length; i++) {
			if (makeUppercase) {
				chars[i] = Character.toUpperCase(chars[i]);
			}
			makeUppercase = Character.isWhitespace(chars[i]);
		}
		return new String(chars);
	}
}
