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
package com.rapidminer.operator.learner.functions.kernel.jmysvm.examples;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.rapidminer.example.Attribute;
import com.rapidminer.tools.Tools;


/**
 * Implementation of a sparse example set which can be used for learning. This data structure is
 * also used as SVM model.
 *
 * @author Stefan Rueping, Ingo Mierswa
 */
public class SVMExamples implements Serializable {

	private static final long serialVersionUID = 7204578592570791663L;

    /**
     * This class holds information aboud the means and variances of an attribute. This is needed to
     * use the same values for the test set if scaling is performed by an SVM operator instead of
     * using a preprocessing step.
     */
    public static class MeanVariance implements Serializable {

		private static final long serialVersionUID = 2700347887530126670L;

		private double mean = 0.0d;

		private double variance = 0.0d;

        /**
         * Instantiates a new Mean variance.
         *
         * @param mean     the mean
         * @param variance the variance
         */
        public MeanVariance(double mean, double variance) {
			this.mean = mean;
			this.variance = variance;
		}

        /**
         * Gets mean.
         *
         * @return the mean
         */
        public double getMean() {
			return mean;
		}

        /**
         * Gets variance.
         *
         * @return the variance
         */
        public double getVariance() {
			return variance;
		}
	}

	/** The dimension of the example set. */
	private int dim;

	/** The number of examples. */
	private int train_size;

	// sparse representation of examples. public for avoiding invocation of a
	// method (slower)
    /**
     * The known attribute values for each example.
     */
    public double[][] atts;

    /**
     * The corresponding indices for the known attribute values for each example.
     */
    public int[][] index;

    /**
     * The ids of all examples.
     */
    public String[] ids;

	/** The SVM alpha values. Will be filled by learning. */
	private double[] alphas;

	/**
	 * The labels of the examples if known. -1 and +1 for classification or the real value for
	 * regression tasks. Will be filled by prediction.
	 */
	private double[] ys;

	/** The hyperplane offset. */
	private double b;

	/**
	 * This example will be once constructed for every thread and delivered with the asked values.
	 */
	private transient ThreadLocal<SVMExample> x = new ThreadLocal<SVMExample>() {

		@Override
		protected SVMExample initialValue() {
			return new SVMExample();
		}
	};

	/**
	 * This map stores the mean-variance informations about all attributes (att index -->
	 * mean-variance). This information is used to scale the data from the test set.
	 */
	private Map<Integer, MeanVariance> meanVarianceMap = new HashMap<Integer, MeanVariance>();

    /**
     * Creates an empty example set of the given size.  @param size the size
     *
     * @param size the size
     * @param b    the b
     */
    public SVMExamples(int size, double b) {
		this.train_size = size;
		this.b = b;

		atts = new double[train_size][];
		index = new int[train_size][];
		ys = new double[train_size];
		alphas = new double[train_size];

		ids = new String[size];

	}

	private static Map<Integer, MeanVariance> createMeanVariances(com.rapidminer.example.ExampleSet exampleSet) {
		double[] sum = new double[exampleSet.getAttributes().size()];
		double[] squaredSum = new double[sum.length];

		Attribute[] regularAttributes = exampleSet.getAttributes().createRegularAttributeArray();
		Iterator<com.rapidminer.example.Example> reader = exampleSet.iterator();
		while (reader.hasNext()) {
			com.rapidminer.example.Example example = reader.next();
			int a = 0;
			for (Attribute attribute : regularAttributes) {
				double value = example.getValue(attribute);
				sum[a] += value;
				squaredSum[a] += value * value;
				a++;
			}
		}

		Map<Integer, MeanVariance> meanVariances = new HashMap<Integer, MeanVariance>();
		for (int a = 0; a < sum.length; a++) {
			sum[a] /= exampleSet.size();
			squaredSum[a] /= exampleSet.size();
			meanVariances.put(a, new MeanVariance(sum[a], squaredSum[a] - (sum[a] * sum[a])));
		}

		return meanVariances;
	}

    /**
     * Instantiates a new Svm examples.
     *
     * @param exampleSet     the example set
     * @param labelAttribute the label attribute
     * @param scale          the scale
     */
    public SVMExamples(com.rapidminer.example.ExampleSet exampleSet, Attribute labelAttribute, boolean scale) {
		this(exampleSet, labelAttribute, scale ? createMeanVariances(exampleSet) : new HashMap<Integer, MeanVariance>());
	}

    /**
     * Creates a fresh example set of the given size from the RapidMiner example reader. The alpha
     * values and b are zero, the label will be set if it is known.
     *
     * @param exampleSet     the example set
     * @param labelAttribute the label attribute
     * @param meanVariances  the mean variances
     */
    public SVMExamples(com.rapidminer.example.ExampleSet exampleSet, Attribute labelAttribute,
			Map<Integer, MeanVariance> meanVariances) {
		this(exampleSet.size(), 0.0d);
		this.meanVarianceMap = meanVariances;

		Attribute[] regularAttributes = exampleSet.getAttributes().createRegularAttributeArray();
		Iterator<com.rapidminer.example.Example> reader = exampleSet.iterator();
		Attribute idAttribute = exampleSet.getAttributes().getId();
		int exampleCounter = 0;
		while (reader.hasNext()) {
			com.rapidminer.example.Example current = reader.next();
			Map<Integer, Double> attributeMap = new LinkedHashMap<Integer, Double>();
			int a = 0;
			for (Attribute attribute : regularAttributes) {
				double value = current.getValue(attribute);
				if (!com.rapidminer.example.Tools.isDefault(attribute.getDefault(), value)) {
					attributeMap.put(a, value);
				}
				if (a + 1 > dim) {
					dim = a + 1;
				}
				a++;
			}
			atts[exampleCounter] = new double[attributeMap.size()];
			index[exampleCounter] = new int[attributeMap.size()];
			Iterator<Map.Entry<Integer, Double>> i = attributeMap.entrySet().iterator();
			int attributeCounter = 0;
			while (i.hasNext()) {
				Map.Entry<Integer, Double> e = i.next();
				Integer indexValue = e.getKey();
				Double attributeValue = e.getValue();
				index[exampleCounter][attributeCounter] = indexValue.intValue();
				double value = attributeValue.doubleValue();
				MeanVariance meanVariance = meanVarianceMap.get(indexValue);
				if (meanVariance != null) {
					if (meanVariance.getVariance() == 0.0d) {
						value = 0.0d;
					} else {
						value = (value - meanVariance.getMean()) / Math.sqrt(meanVariance.getVariance());
					}
				}
				atts[exampleCounter][attributeCounter] = value;
				attributeCounter++;
			}
			if (labelAttribute != null) {
				double label = current.getValue(labelAttribute);
				if (labelAttribute.isNominal()) {
					ys[exampleCounter] = (label == labelAttribute.getMapping().getPositiveIndex() ? 1 : -1);
				} else {
					ys[exampleCounter] = label;
				}
			}
			if (idAttribute != null) {
				ids[exampleCounter] = current.getValueAsString(idAttribute);
			}
			exampleCounter++;
		}
	}

    /**
     * Reads an example set from the given input stream.  @param in the in
     *
     * @param in the in
     * @throws IOException the io exception
     */
    public SVMExamples(ObjectInputStream in) throws IOException {
		this(in.readInt(), in.readDouble());
		this.dim = in.readInt();
		String scaleString = in.readUTF();
		if (scaleString.equals("scale")) {
			int numberOfAttributes = in.readInt();
			this.meanVarianceMap = new HashMap<Integer, MeanVariance>();
			for (int i = 0; i < numberOfAttributes; i++) {
				int index = in.readInt();
				double mean = in.readDouble();
				double variance = in.readDouble();
				meanVarianceMap.put(Integer.valueOf(index), new MeanVariance(mean, variance));
			}
		}
		for (int e = 0; e < this.train_size; e++) {
			index[e] = new int[in.readInt()];
			atts[e] = new double[index[e].length];
			for (int a = 0; a < index[e].length; a++) {
				index[e][a] = in.readInt();
				atts[e][a] = in.readDouble();
			}
			alphas[e] = in.readDouble();
			ys[e] = in.readDouble();
		}
	}

    /**
     * Gets mean variances.
     *
     * @return the mean variances
     */
    public Map<Integer, MeanVariance> getMeanVariances() {
		return meanVarianceMap;
	}

    /**
     * Gets number of support vectors.
     *
     * @return the number of support vectors
     */
    public int getNumberOfSupportVectors() {
		int result = 0;
		for (int i = 0; i < alphas.length; i++) {
			if (alphas[i] != 0.0d) {
				result++;
			}
		}
		return result;
	}

    /**
     * Writes the example set into the given output stream.  @param out the out
     *
     * @param out the out
     * @throws IOException the io exception
     */
    public void writeSupportVectors(ObjectOutputStream out) throws IOException {
		out.writeInt(getNumberOfSupportVectors());
		out.writeDouble(b);
		out.writeInt(dim);
		if (meanVarianceMap == null || meanVarianceMap.size() == 0) {
			out.writeUTF("noscale");
		} else {
			out.writeUTF("scale");
			out.writeInt(meanVarianceMap.size());
			for (Entry<Integer, MeanVariance> meanVar : meanVarianceMap.entrySet()) {
				MeanVariance meanVariance = meanVar.getValue();
				out.writeInt(meanVar.getKey());
				out.writeDouble(meanVariance.getMean());
				out.writeDouble(meanVariance.getVariance());
			}
		}
		for (int e = 0; e < train_size; e++) {
			if (alphas[e] != 0.0d) {
				out.writeInt(atts[e].length);
				for (int a = 0; a < atts[e].length; a++) {
					out.writeInt(index[e][a]);
					out.writeDouble(atts[e][a]);
				}
				out.writeDouble(alphas[e]);
				out.writeDouble(ys[e]);
			}
		}
	}

    /**
     * Counts the training examples.
     *
     * @return Number of examples
     */
    public int count_examples() {
		return train_size;
	}

    /**
     * Counts the positive training examples
     *
     * @return Number of positive examples
     */
    public int count_pos_examples() {
		int result = 0;
		for (int i = 0; i < train_size; i++) {
			if (ys[i] > 0) {
				result++;
			}
		}
		return result;
	}

    /**
     * Gets the dimension of the examples
     *
     * @return dim dim
     */
    public int get_dim() {
		return dim;
	}

    /**
     * Sets dim.
     *
     * @param d the d
     */
    public void set_dim(int d) {
		dim = d;
	}

    /**
     * Gets an example. Returns a thread-local instance with value set according to the desired
     * position.
     * <p>
     * It always returns the same thread-local instance, so calling this method again in the same
     * thread will change the already acquired instance.
     *
     * @param pos Number of example
     * @return Array of example attributes in their default order
     */
    public SVMExample get_example(int pos) {
		SVMExample result = x.get();
		result.att = atts[pos];
		result.index = index[pos];
		return result;
	}

    /**
     * Gets an y-value.
     *
     * @param pos Number of example
     * @return y y
     */
    public double get_y(int pos) {
		return ys[pos];
	}

    /**
     * Sets the label value for the specified example.  @param pos the pos
     *
     * @param pos the pos
     * @param y   the y
     */
    public void set_y(int pos, double y) {
		ys[pos] = y;
	}

    /**
     * Gets the y array
     *
     * @return y double [ ]
     */
    public double[] get_ys() {
		return ys;
	}

    /**
     * Gets an alpha-value. Please note that the alpha values are already multiplied by the
     * corresponding y-value.
     *
     * @param pos Number of example
     * @return alpha alpha
     */
    public double get_alpha(int pos) {
		return alphas[pos];
	}

    /**
     * Gets the alpha array. Please note that the alpha values are already multiplied by the
     * corresponding y-value.
     *
     * @return alpha double [ ]
     */
    public double[] get_alphas() {
		return alphas;
	}

    /**
     * swap two training examples
     *
     * @param pos1 the pos 1
     * @param pos2 the pos 2
     */
    public void swap(int pos1, int pos2) {
		double[] dummyA = atts[pos1];
		atts[pos1] = atts[pos2];
		atts[pos2] = dummyA;
		int[] dummyI = index[pos1];
		index[pos1] = index[pos2];
		index[pos2] = dummyI;
		double dummyd = alphas[pos1];
		alphas[pos1] = alphas[pos2];
		alphas[pos2] = dummyd;
		dummyd = ys[pos1];
		ys[pos1] = ys[pos2];
		ys[pos2] = dummyd;
	}

    /**
     * get b
     *
     * @return b b
     */
    public double get_b() {
		return b;
	}

    /**
     * set b
     *
     * @param new_b the new b
     */
    public void set_b(double new_b) {
		b = new_b;
	}

    /**
     * sets an alpha value.
     *
     * @param pos   Number of example
     * @param alpha New value
     */
    public void set_alpha(int pos, double alpha) {
		alphas[pos] = alpha;
	}

    /**
     * Clear alphas.
     */
    public void clearAlphas() {
		for (int i = 0; i < alphas.length; i++) {
			alphas[i] = 0.0d;
		}
	}

    /**
     * Sets mean variance map.
     *
     * @param meanVarianceMap the mean variance map
     */
    public void setMeanVarianceMap(Map<Integer, MeanVariance> meanVarianceMap) {
		this.meanVarianceMap = meanVarianceMap;
	}

    /**
     * Get ys double [ ].
     *
     * @return the double [ ]
     */
    public double[] getYs() {
		return ys;
	}

    /**
     * Sets ys.
     *
     * @param ys the ys
     */
    public void setYs(double[] ys) {
		this.ys = ys;
	}

    /**
     * Gets train size.
     *
     * @return the train size
     */
    public int getTrain_size() {
		return train_size;
	}

    /**
     * Sets train size.
     *
     * @param train_size the train size
     */
    public void setTrain_size(int train_size) {
		this.train_size = train_size;
	}

    /**
     * Gets b.
     *
     * @return the b
     */
    public double getB() {
		return b;
	}

    /**
     * Sets b.
     *
     * @param b the b
     */
    public void setB(double b) {
		this.b = b;
	}

	// ================================================================================

    /**
     * Gets id.
     *
     * @param index the index
     * @return the id
     */
    public String getId(int index) {
		return ids[index];
	}

	@Override
	public String toString() {
		return toString(atts.length, false);
	}

    /**
     * To string string.
     *
     * @param onlySV the only sv
     * @return the string
     */
    public String toString(boolean onlySV) {
		return toString(atts.length, onlySV);
	}

    /**
     * To string string.
     *
     * @param numberOfExamples the number of examples
     * @param onlySV           the only sv
     * @return the string
     */
    public String toString(int numberOfExamples, boolean onlySV) {
		StringBuffer result = new StringBuffer("SVM Example Set ("
				+ (onlySV ? (getNumberOfSupportVectors() + " support vectors") : (train_size + " examples")) + "):"
				+ Tools.getLineSeparator() + "b: " + b + Tools.getLineSeparator());
		for (int e = 0; e < numberOfExamples; e++) {
			if (!onlySV || (alphas[e] != 0.0d)) {
				for (int a = 0; a < atts[e].length; a++) {
					result.append(index[e][a] + ":");
					result.append(atts[e][a] + " ");
				}
				result.append(", alpha: " + alphas[e]);
				result.append(", y: " + ys[e] + Tools.getLineSeparator());
			}
		}
		return result.toString();
	}

	/**
	 * Do default deserialization and initialize transient field x.
	 */
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		x = new ThreadLocal<SVMExample>() {

			@Override
			protected SVMExample initialValue() {
				return new SVMExample();
			}

		};
	}

}
