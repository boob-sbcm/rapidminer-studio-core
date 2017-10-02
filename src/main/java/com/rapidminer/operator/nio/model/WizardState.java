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
package com.rapidminer.operator.nio.model;

import com.rapidminer.example.ExampleSet;
import com.rapidminer.operator.OperatorException;
import com.rapidminer.operator.nio.ImportWizardUtils;
import com.rapidminer.repository.RepositoryLocation;
import com.rapidminer.tools.LogService;
import com.rapidminer.tools.ProgressListener;

import java.util.logging.Level;


/**
 * The complete state of a data import wizard. Steps of the wizard communicate through this
 * interface.
 *
 * @author Simon Fischer
 */
public class WizardState {

	private final DataResultSetTranslator translator;
	private final DataResultSetTranslationConfiguration config;
	private final DataResultSetFactory dataResultSetFactory;
	private final AbstractDataResultSetReader operator;
	private final int maxRows = ImportWizardUtils.getPreviewLength();

	private RepositoryLocation selectedLocation;

    /**
     * Instantiates a new Wizard state.
     *
     * @param operator             the operator
     * @param dataResultSetFactory the data result set factory
     */
    public WizardState(AbstractDataResultSetReader operator, DataResultSetFactory dataResultSetFactory) {
		super();
		this.config = new DataResultSetTranslationConfiguration(operator);
		this.translator = new DataResultSetTranslator(operator);
		this.operator = operator;
		this.dataResultSetFactory = dataResultSetFactory;
	}

    /**
     * Gets translator.
     *
     * @return the translator
     */
    public DataResultSetTranslator getTranslator() {
		return translator;
	}

    /**
     * Gets translation configuration.
     *
     * @return the translation configuration
     */
    public DataResultSetTranslationConfiguration getTranslationConfiguration() {
		return config;
	}

    /**
     * Gets data result set factory.
     *
     * @return the data result set factory
     */
    public DataResultSetFactory getDataResultSetFactory() {
		return dataResultSetFactory;
	}

    /**
     * Read now example set.
     *
     * @param dataResultSet    the data result set
     * @param previewOnly      the preview only
     * @param progressListener the progress listener
     * @return the example set
     * @throws OperatorException the operator exception
     */
    public ExampleSet readNow(DataResultSet dataResultSet, boolean previewOnly, ProgressListener progressListener)
			throws OperatorException {
		// LogService.getRoot().info("Reading example set...");
		LogService.getRoot().log(Level.INFO, "com.rapidminer.operator.nio.model.WizardState.reading_example_set");
		final DataResultSetTranslator translator = getTranslator();
		try {
			ExampleSet cachedExampleSet = translator.read(dataResultSet, getTranslationConfiguration(), previewOnly,
					progressListener);
			return cachedExampleSet;
		} finally {
			translator.close();
		}
	}

    /**
     * Gets number of preview rows.
     *
     * @return the number of preview rows
     */
    public int getNumberOfPreviewRows() {
		return maxRows;
	}

    /**
     * Gets operator.
     *
     * @return the operator
     */
    public AbstractDataResultSetReader getOperator() {
		return operator;
	}

    /**
     * Sets selected location.
     *
     * @param selectedLocation the selected location
     */
    public void setSelectedLocation(RepositoryLocation selectedLocation) {
		this.selectedLocation = selectedLocation;
	}

    /**
     * Gets selected location.
     *
     * @return the selected location
     */
    public RepositoryLocation getSelectedLocation() {
		return selectedLocation;
	}
}
