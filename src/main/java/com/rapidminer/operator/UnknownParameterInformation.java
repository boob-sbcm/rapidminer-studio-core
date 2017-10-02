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
 * This is a helper class storing information about unknown parameters.
 *
 * @author Ingo Mierswa
 */
public class UnknownParameterInformation implements Comparable<UnknownParameterInformation> {

	private String operatorName;

	private String operatorClass;

	private String parameterName;

	private String parameterValue;

    /**
     * Instantiates a new Unknown parameter information.
     *
     * @param operatorName   the operator name
     * @param operatorClass  the operator class
     * @param parameterName  the parameter name
     * @param parameterValue the parameter value
     */
    public UnknownParameterInformation(String operatorName, String operatorClass, String parameterName, String parameterValue) {
		this.operatorName = operatorName;
		this.operatorClass = operatorClass;
		this.parameterName = parameterName;
		this.parameterValue = parameterValue;
	}

    /**
     * Gets operator name.
     *
     * @return the operator name
     */
    public String getOperatorName() {
		return operatorName;
	}

    /**
     * Gets operator class.
     *
     * @return the operator class
     */
    public String getOperatorClass() {
		return operatorClass;
	}

    /**
     * Gets parameter name.
     *
     * @return the parameter name
     */
    public String getParameterName() {
		return parameterName;
	}

    /**
     * Gets parameter value.
     *
     * @return the parameter value
     */
    public String getParameterValue() {
		return parameterValue;
	}

	@Override
	public int compareTo(UnknownParameterInformation o) {
		return this.operatorClass.compareTo(o.operatorClass);
	}
}
