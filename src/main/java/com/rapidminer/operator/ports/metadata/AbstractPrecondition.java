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
package com.rapidminer.operator.ports.metadata;

import com.rapidminer.operator.ProcessSetupError.Severity;
import com.rapidminer.operator.ports.InputPort;
import com.rapidminer.operator.ports.quickfix.QuickFix;

import java.util.Collections;
import java.util.List;


/**
 * The type Abstract precondition.
 *
 * @author Simon Fischer
 */
public abstract class AbstractPrecondition implements Precondition {

	private final InputPort inputPort;

    /**
     * Instantiates a new Abstract precondition.
     *
     * @param inputPort the input port
     */
    public AbstractPrecondition(InputPort inputPort) {
		this.inputPort = inputPort;
	}

    /**
     * Gets input port.
     *
     * @return the input port
     */
    protected InputPort getInputPort() {
		return inputPort;
	}

    /**
     * Create error.
     *
     * @param severity the severity
     * @param i18nKey  the 18 n key
     * @param i18nArgs the 18 n args
     */
    protected void createError(Severity severity, String i18nKey, Object... i18nArgs) {
		createError(severity, Collections.<QuickFix> emptyList(), i18nKey, i18nArgs);
	}

    /**
     * Create error.
     *
     * @param severity the severity
     * @param fixes    the fixes
     * @param i18nKey  the 18 n key
     * @param i18nArgs the 18 n args
     */
    protected void createError(Severity severity, final List<? extends QuickFix> fixes, String i18nKey, Object... i18nArgs) {
		getInputPort().addError(new SimpleMetaDataError(severity, getInputPort(), fixes, i18nKey, i18nArgs));
	}

	@Override
	public String toString() {
		return getDescription();
	}
}
