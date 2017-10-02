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
package com.rapidminer.operator.learner.functions.kernel;

import java.io.Serializable;


/**
 * Holds all information of a support vector, i.e. the attribute values, the label, and the alpha.
 *
 * @author Ingo Mierswa
 */
public class SupportVector implements Serializable {

	private static final long serialVersionUID = -8544548121343344760L;

	private double[] x;

	private double y;

	private double alpha;

    /**
     * Creates a new support vector.  @param x the x
     *
     * @param x     the x
     * @param y     the y
     * @param alpha the alpha
     */
    public SupportVector(double[] x, double y, double alpha) {
		this.x = x;
		this.y = y;
		this.alpha = alpha;
	}

    /**
     * Get x double [ ].
     *
     * @return the double [ ]
     */
    public double[] getX() {
		return x;
	}

    /**
     * Gets y.
     *
     * @return the y
     */
    public double getY() {
		return y;
	}

    /**
     * Sets alpha.
     *
     * @param alpha the alpha
     */
    public void setAlpha(double alpha) {
		this.alpha = alpha;
	}

    /**
     * Gets alpha.
     *
     * @return the alpha
     */
    public double getAlpha() {
		return alpha;
	}

	@Override
	public String toString() {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < x.length; i++) {
			result.append(x[i] + " ");
		}
		result.append("alpha=" + alpha);
		result.append(" y=" + y);
		return result.toString();
	}
}
