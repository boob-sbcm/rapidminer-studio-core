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
package com.rapidminer.operator.clustering;

import com.rapidminer.example.ExampleSet;
import com.rapidminer.tools.Tools;

import java.util.HashMap;
import java.util.Map;


/**
 * This class represents a stadard implementation of a flat, fuzzy clustering.
 *
 * @author Regina Fritsch
 */
public class FlatFuzzyClusterModel extends ClusterModel {

	private static final long serialVersionUID = -1408826564261080217L;

	private final Map<Integer, Double> clusterStandardDeviations = new HashMap<Integer, Double>();
	private final Map<Integer, Double> clusterProbabilities = new HashMap<Integer, Double>();
	private final Map<Integer, double[]> clusterMeans = new HashMap<Integer, double[]>();
	private final Map<Integer, double[][]> clusterCovarianceMatrix = new HashMap<Integer, double[][]>();
	private double[][] exampleInClusterProbability;

    /**
     * Instantiates a new Flat fuzzy cluster model.
     *
     * @param exampleSet        the example set
     * @param k                 the k
     * @param addClusterAsLabel the add cluster as label
     * @param removeUnknown     the remove unknown
     */
    public FlatFuzzyClusterModel(ExampleSet exampleSet, int k, boolean addClusterAsLabel, boolean removeUnknown) {
		super(exampleSet, k, addClusterAsLabel, removeUnknown);
	}

    /**
     * Gets example in cluster probability.
     *
     * @param example the example
     * @param cluster the cluster
     * @return the example in cluster probability
     */
    public double getExampleInClusterProbability(int example, int cluster) {
		return exampleInClusterProbability[example][cluster];
	}

    /**
     * Sets example in cluster probability.
     *
     * @param exampleInClusterProbability the example in cluster probability
     */
    public void setExampleInClusterProbability(double[][] exampleInClusterProbability) {
		this.exampleInClusterProbability = new double[exampleInClusterProbability.length][exampleInClusterProbability[0].length];
		for (int i = 0; i < exampleInClusterProbability.length; i++) {
			for (int j = 0; j < exampleInClusterProbability[i].length; j++) {
				this.exampleInClusterProbability[i][j] = exampleInClusterProbability[i][j];
			}
		}
	}

    /**
     * Get cluster mean double [ ].
     *
     * @param cluster the cluster
     * @return the double [ ]
     */
    public double[] getClusterMean(int cluster) {
		return clusterMeans.get(cluster);
	}

    /**
     * Sets cluster mean.
     *
     * @param cluster the cluster
     * @param value   the value
     */
    public void setClusterMean(int cluster, double[] value) {
		clusterMeans.put(cluster, value);
	}

    /**
     * Gets cluster probability.
     *
     * @param cluster the cluster
     * @return the cluster probability
     */
    public double getClusterProbability(int cluster) {
		return clusterProbabilities.get(cluster);
	}

    /**
     * Sets cluster probability.
     *
     * @param cluster the cluster
     * @param value   the value
     */
    public void setClusterProbability(int cluster, double value) {
		clusterProbabilities.put(cluster, value);
	}

    /**
     * Gets cluster standard deviation.
     *
     * @param cluster the cluster
     * @return the cluster standard deviation
     */
    public double getClusterStandardDeviation(int cluster) {
		return clusterStandardDeviations.get(cluster);
	}

    /**
     * Sets cluster standard deviation.
     *
     * @param cluster the cluster
     * @param value   the value
     */
    public void setClusterStandardDeviation(int cluster, double value) {
		clusterStandardDeviations.put(cluster, value);
	}

    /**
     * Clear cluster standard deviations.
     */
    public void clearClusterStandardDeviations() {
		clusterStandardDeviations.clear();
	}

    /**
     * Get cluster covariance matrix double [ ] [ ].
     *
     * @param cluster the cluster
     * @return the double [ ] [ ]
     */
    public double[][] getClusterCovarianceMatrix(int cluster) {
		return clusterCovarianceMatrix.get(cluster);
	}

    /**
     * Sets cluster covariance matrix.
     *
     * @param cluster the cluster
     * @param matrix  the matrix
     */
    public void setClusterCovarianceMatrix(int cluster, double[][] matrix) {
		clusterCovarianceMatrix.put(cluster, matrix);
	}

	@Override
	public String toString() {
		StringBuffer result = new StringBuffer(super.toString() + Tools.getLineSeparator());

		result.append("------------------------------------------------------" + Tools.getLineSeparators(2));

		if (!clusterProbabilities.isEmpty()) {
			result.append("cluster probabilities:" + Tools.getLineSeparator());
			for (int i = 0; i < clusterProbabilities.size(); i++) {
				result.append("Cluster " + i + ": " + clusterProbabilities.get(i) + Tools.getLineSeparator());
			}
			result.append(Tools.getLineSeparator());
		}

		if (!clusterMeans.isEmpty()) {
			result.append("cluster means:" + Tools.getLineSeparator());
			for (int i = 0; i < clusterMeans.size(); i++) {
				result.append("Cluster " + i + ": ");
				for (int j = 0; j < clusterMeans.get(i).length; j++) {
					result.append(clusterMeans.get(i)[j]);
					if (j < clusterMeans.get(i).length - 1) {
						result.append("; ");
					}
				}
				result.append(Tools.getLineSeparator());
			}
			result.append(Tools.getLineSeparator());
		}

		if (!clusterStandardDeviations.isEmpty()) {
			result.append("cluster standard deviations:" + Tools.getLineSeparator());
			for (int i = 0; i < clusterStandardDeviations.size(); i++) {
				result.append("Cluster " + i + ": " + clusterStandardDeviations.get(i) + Tools.getLineSeparator());
			}
			result.append(Tools.getLineSeparator());
		}

		if (!clusterCovarianceMatrix.isEmpty()) {
			result.append("cluster covariance matrices:" + Tools.getLineSeparator());
			for (int i = 0; i < clusterCovarianceMatrix.size(); i++) {
				result.append("Cluster " + i + ": " + Tools.getLineSeparator());
				for (int j = 0; j < clusterMeans.get(i).length; j++) {
					for (int k = 0; k < clusterCovarianceMatrix.get(i)[j].length; k++) {
						result.append(clusterCovarianceMatrix.get(i)[j][k] + "\t");
					}
					result.append(Tools.getLineSeparator());
				}
			}
			result.append(Tools.getLineSeparator());
		}

		return result.toString();
	}

}
