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
package com.rapidminer.operator.visualization.dependencies;

import com.rapidminer.operator.ResultObjectAdapter;

import java.util.List;


/**
 * Displays the result of an ANOVA matrix calculation.
 *
 * @author Ingo Mierswa
 */
public class ANOVAMatrix extends ResultObjectAdapter {

	private static final long serialVersionUID = 6245314851143584397L;

	private double[][] probabilities;

	private List<String> anovaAttributeNames;

	private List<String> groupNames;

	private double significanceLevel;

    /**
     * Instantiates a new Anova matrix.
     *
     * @param probabilities       the probabilities
     * @param anovaAttributeNames the anova attribute names
     * @param groupNames          the group names
     * @param significanceLevel   the significance level
     */
    public ANOVAMatrix(double[][] probabilities, List<String> anovaAttributeNames, List<String> groupNames,
			double significanceLevel) {
		this.probabilities = probabilities;
		this.anovaAttributeNames = anovaAttributeNames;
		this.groupNames = groupNames;
		this.significanceLevel = significanceLevel;
	}

    /**
     * Get probabilities double [ ] [ ].
     *
     * @return the double [ ] [ ]
     */
    public double[][] getProbabilities() {
		return probabilities;
	}

    /**
     * Gets anova attribute names.
     *
     * @return the anova attribute names
     */
    public List<String> getAnovaAttributeNames() {
		return anovaAttributeNames;
	}

    /**
     * Gets grouping attribute names.
     *
     * @return the grouping attribute names
     */
    public List<String> getGroupingAttributeNames() {
		return groupNames;
	}

    /**
     * Gets significance level.
     *
     * @return the significance level
     */
    public double getSignificanceLevel() {
		return significanceLevel;
	}

	@Override
	public String toResultString() {
		StringBuffer result = new StringBuffer();

		return result.toString();
	}

	@Override
	public String toString() {
		return "ANOVA matrix indicating which attributes provide significant differences "
				+ "between groups defined by other (nominal) attributes.";
	}

    /**
     * Gets extension.
     *
     * @return the extension
     */
    public String getExtension() {
		return "ano";
	}

    /**
     * Gets file description.
     *
     * @return the file description
     */
    public String getFileDescription() {
		return "anova matrix";
	}
}
