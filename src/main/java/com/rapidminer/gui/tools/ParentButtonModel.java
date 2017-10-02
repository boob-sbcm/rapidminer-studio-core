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
package com.rapidminer.gui.tools;

import javax.swing.*;


/**
 * Model for a {@link ParentButtonPanel}.
 *
 * @param <T> the type parameter
 * @author Simon Fischer
 */
public interface ParentButtonModel<T> {

    /**
     * Gets root.
     *
     * @return the root
     */
    public T getRoot();

    /**
     * Gets parent.
     *
     * @param child the child
     * @return the parent
     */
    public T getParent(T child);

    /**
     * Gets number of children.
     *
     * @param node the node
     * @return the number of children
     */
    public int getNumberOfChildren(T node);

    /**
     * Gets child.
     *
     * @param node  the node
     * @param index the index
     * @return the child
     */
    public T getChild(T node, int index);

    /**
     * To string string.
     *
     * @param node the node
     * @return the string
     */
    public String toString(T node);

    /**
     * Gets icon.
     *
     * @param node the node
     * @return the icon
     */
    public Icon getIcon(T node);
}
