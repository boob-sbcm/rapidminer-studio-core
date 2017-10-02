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
package com.rapidminer.operator.clustering.clusterer;

/**
 * This is a simple data structure for returning an agglomeration of two clusters with a distance
 * within one method return.
 *
 * @author Sebastian Land
 */
public class Agglomeration {

	private int clusterId1;
	private int clusterId2;
	private double distance;

    /**
     * Instantiates a new Agglomeration.
     *
     * @param clusterId1 the cluster id 1
     * @param clusterId2 the cluster id 2
     * @param distance   the distance
     */
    public Agglomeration(int clusterId1, int clusterId2, double distance) {
		this.clusterId1 = clusterId1;
		this.clusterId2 = clusterId2;
		this.distance = distance;
	}

    /**
     * Gets cluster id 1.
     *
     * @return the cluster id 1
     */
    public int getClusterId1() {
		return clusterId1;
	}

    /**
     * Gets cluster id 2.
     *
     * @return the cluster id 2
     */
    public int getClusterId2() {
		return clusterId2;
	}

    /**
     * Gets distance.
     *
     * @return the distance
     */
    public double getDistance() {
		return distance;
	}
}
