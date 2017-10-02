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
package com.rapidminer.operator;

/**
 * The possible capabilities for all learners.
 *
 * @author Julien Nioche, Ingo Mierswa
 * @rapidminer.todo replace by Enumeration after change to Java 1.5
 */
public enum OperatorCapability {

    /**
     * The Polynominal attributes.
     */
    POLYNOMINAL_ATTRIBUTES("polynominal attributes"), /**
     * The Binominal attributes.
     */
    BINOMINAL_ATTRIBUTES("binominal attributes"), /**
     * The Numerical attributes.
     */
    NUMERICAL_ATTRIBUTES(
			"numerical attributes"), /**
     * The Polynominal label.
     */
    POLYNOMINAL_LABEL("polynominal label"), /**
     * The Binominal label.
     */
    BINOMINAL_LABEL("binominal label"), /**
     * The Numerical label.
     */
    NUMERICAL_LABEL(
			"numerical label"), /**
     * The One class label.
     */
    ONE_CLASS_LABEL("one class label"), /**
     * No label operator capability.
     */
    NO_LABEL("unlabeled"), /**
     * Updatable operator capability.
     */
    UPDATABLE("updatable"), /**
     * The Weighted examples.
     */
    WEIGHTED_EXAMPLES(
			"weighted examples"), /**
     * The Formula provider.
     */
    FORMULA_PROVIDER("formula provider"), /**
     * The Missing values.
     */
    MISSING_VALUES("missing values");

	private String description;

	private OperatorCapability(String description) {
		this.description = description;
	}

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return description;
	}

	// public static LearnerCapability getCapability(int index) {
	// return ALL_CAPABILITIES.get(index);
	// }
	//
	// public static List<LearnerCapability> getAllCapabilities() {
	// return ALL_CAPABILITIES;
	// }
}
