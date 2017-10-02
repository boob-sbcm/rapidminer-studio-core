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
package com.rapidminer.gui.flow;

import com.rapidminer.gui.flow.processrendering.view.ProcessRendererView;
import com.rapidminer.operator.Operator;
import com.rapidminer.operator.OperatorChain;
import com.rapidminer.operator.ports.Port;

import javax.swing.*;


/**
 * Interface for callbacks received from the {@link ProcessRendererView} when the user opens a context
 * menu.
 *
 * @author Simon Fischer
 */
public interface ProcessInteractionListener {

    /**
     * Called on right-clicking an operator.  @param menu the menu
     *
     * @param menu     the menu
     * @param operator the operator
     */
    public void operatorContextMenuWillOpen(JPopupMenu menu, Operator operator);

    /**
     * Called on right-clicking a port.  @param menu the menu
     *
     * @param menu the menu
     * @param port the port
     */
    public void portContextMenuWillOpen(JPopupMenu menu, Port port);

    /**
     * Called when an operator is moved.  @param op the op
     *
     * @param op the op
     */
    public void operatorMoved(Operator op);

    /**
     * Called when the currently displayed {@link OperatorChain} changed.  @param displayedChain the displayed chain
     *
     * @param displayedChain the displayed chain
     */
    public void displayedChainChanged(OperatorChain displayedChain);
}
