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
package com.rapidminer.operator.preprocessing.transformation.aggregation;

import com.rapidminer.example.Attribute;
import com.rapidminer.example.ExampleSet;
import com.rapidminer.example.table.AttributeFactory;
import com.rapidminer.example.table.DoubleArrayDataRow;
import com.rapidminer.operator.OperatorException;
import com.rapidminer.operator.OperatorVersion;
import com.rapidminer.operator.ProcessSetupError.Severity;
import com.rapidminer.operator.UserError;
import com.rapidminer.operator.ports.InputPort;
import com.rapidminer.operator.ports.metadata.AttributeMetaData;
import com.rapidminer.operator.ports.metadata.SimpleMetaDataError;
import com.rapidminer.tools.Ontology;

import java.lang.reflect.Constructor;
import java.util.*;


/**
 * This is an abstract class for all {@link AggregationFunction}s, that can be selected to aggregate
 * values of a certain group. Each {@link AggregationFunction} must be able to provide a certain
 * {@link Aggregator}, that will count the examples of one single group and compute the aggregated
 * value. So for example the {@link MeanAggregationFunction} provides an {@link MeanAggregator},
 * that will calculate the mean on all examples delivered to him.
 * <p>
 * The list of the names of all available functions can be queried from the static method
 * {@link #getAvailableAggregationFunctionNames()}. With a name one can call the static method
 * {@link #createAggregationFunction(String, Attribute)} to create a certain aggregator for the
 * actual counting.
 * <p>
 * Additional functions can be registered by calling
 * {@link #registerNewAggregationFunction(String, Class)} from extensions, preferable during their
 * initialization. Please notice that there will be no warning prior process execution if the
 * extension is missing but the usage of it's function is still configured.
 *
 * @author Sebastian Land, Marius Helf
 */
public abstract class AggregationFunction {

    /**
     * The constant FUNCTION_SEPARATOR_OPEN.
     */
    public static final String FUNCTION_SEPARATOR_OPEN = "(";
    /**
     * The constant FUNCTION_SEPARATOR_CLOSE.
     */
    public static final String FUNCTION_SEPARATOR_CLOSE = ")";

    /**
     * The constant FUNCTION_NAME_SUM.
     */
// available functions
	public static final String FUNCTION_NAME_SUM = "sum";
    /**
     * The constant FUNCTION_NAME_SUM_FRACTIONAL.
     */
    public static final String FUNCTION_NAME_SUM_FRACTIONAL = "sum (fractional)";
    /**
     * The constant FUNCTION_NAME_MEDIAN.
     */
    public static final String FUNCTION_NAME_MEDIAN = "median";
    /**
     * The constant FUNCTION_NAME_AVERAGE.
     */
    public static final String FUNCTION_NAME_AVERAGE = "average";
    /**
     * The constant FUNCTION_NAME_VARIANCE.
     */
    public static final String FUNCTION_NAME_VARIANCE = "variance";
    /**
     * The constant FUNCTION_NAME_STANDARD_DEVIATION.
     */
    public static final String FUNCTION_NAME_STANDARD_DEVIATION = "standard_deviation";
    /**
     * The constant FUNCTION_NAME_COUNT_IGNORE_MISSINGS.
     */
    public static final String FUNCTION_NAME_COUNT_IGNORE_MISSINGS = "count (ignoring missings)";
    /**
     * The constant FUNCTION_NAME_COUNT_INCLUDE_MISSINGS.
     */
    public static final String FUNCTION_NAME_COUNT_INCLUDE_MISSINGS = "count (including missings)";
    /**
     * The constant FUNCTION_NAME_COUNT.
     */
    public static final String FUNCTION_NAME_COUNT = "count";
    /**
     * The constant FUNCTION_NAME_COUNT_FRACTIONAL.
     */
    public static final String FUNCTION_NAME_COUNT_FRACTIONAL = "count (fractional)";
    /**
     * The constant FUNCTION_NAME_COUNT_PERCENTAGE.
     */
    public static final String FUNCTION_NAME_COUNT_PERCENTAGE = "count (percentage)";
    /**
     * The constant FUNCTION_NAME_MINIMUM.
     */
    public static final String FUNCTION_NAME_MINIMUM = "minimum";
    /**
     * The constant FUNCTION_NAME_MAXIMUM.
     */
    public static final String FUNCTION_NAME_MAXIMUM = "maximum";
    /**
     * The constant FUNCTION_NAME_LOG_PRODUCT.
     */
    public static final String FUNCTION_NAME_LOG_PRODUCT = "log product";
    /**
     * The constant FUNCTION_NAME_PRODOCT.
     */
    public static final String FUNCTION_NAME_PRODOCT = "product";
    /**
     * The constant FUNCTION_NAME_MODE.
     */
    public static final String FUNCTION_NAME_MODE = "mode";
    /**
     * The constant FUNCTION_NAME_LEAST.
     */
    public static final String FUNCTION_NAME_LEAST = "least";
    /**
     * The constant FUNCTION_NAME_LEAST_ONLY_OCCURRING.
     */
    public static final String FUNCTION_NAME_LEAST_ONLY_OCCURRING = "least (only occurring)";
    /**
     * The constant FUNCTION_NAME_CONCATENATION.
     */
    public static final String FUNCTION_NAME_CONCATENATION = "concatenation";

    /**
     * The constant AGGREATION_FUNCTIONS.
     */
    public static final Map<String, Class<? extends AggregationFunction>> AGGREATION_FUNCTIONS = new TreeMap<>();
	static {
		// numerical/date
		AGGREATION_FUNCTIONS.put(FUNCTION_NAME_SUM, SumAggregationFunction.class);
		AGGREATION_FUNCTIONS.put(FUNCTION_NAME_SUM_FRACTIONAL, SumFractionalAggregationFunction.class);
		AGGREATION_FUNCTIONS.put(FUNCTION_NAME_MEDIAN, MedianAggregationFunction.class);
		AGGREATION_FUNCTIONS.put(FUNCTION_NAME_AVERAGE, MeanAggregationFunction.class);
		AGGREATION_FUNCTIONS.put(FUNCTION_NAME_VARIANCE, VarianceAggregationFunction.class);
		AGGREATION_FUNCTIONS.put(FUNCTION_NAME_STANDARD_DEVIATION, StandardDeviationAggregationFunction.class);
		AGGREATION_FUNCTIONS.put(FUNCTION_NAME_MINIMUM, MinAggregationFunction.class);
		AGGREATION_FUNCTIONS.put(FUNCTION_NAME_MAXIMUM, MaxAggregationFunction.class);

		AGGREATION_FUNCTIONS.put(FUNCTION_NAME_LOG_PRODUCT, LogProductAggregationFunction.class);
		AGGREATION_FUNCTIONS.put(FUNCTION_NAME_PRODOCT, ProductAggregationFunction.class);

		// numerical/date/nominal
		AGGREATION_FUNCTIONS.put(FUNCTION_NAME_COUNT_IGNORE_MISSINGS, CountIgnoringMissingsAggregationFunction.class);
		AGGREATION_FUNCTIONS.put(FUNCTION_NAME_COUNT_INCLUDE_MISSINGS, CountIncludingMissingsAggregationFunction.class);
		AGGREATION_FUNCTIONS.put(FUNCTION_NAME_COUNT, CountAggregationFunction.class);
		AGGREATION_FUNCTIONS.put(FUNCTION_NAME_COUNT_FRACTIONAL, CountFractionalAggregationFunction.class);
		AGGREATION_FUNCTIONS.put(FUNCTION_NAME_COUNT_PERCENTAGE, CountPercentageAggregationFunction.class);

		// Nominal Aggregations
		AGGREATION_FUNCTIONS.put(FUNCTION_NAME_MODE, ModeAggregationFunction.class);
		AGGREATION_FUNCTIONS.put(FUNCTION_NAME_LEAST, LeastAggregationFunction.class);
		AGGREATION_FUNCTIONS.put(FUNCTION_NAME_LEAST_ONLY_OCCURRING, LeastOccurringAggregationFunction.class);
		AGGREATION_FUNCTIONS.put(FUNCTION_NAME_CONCATENATION, ConcatAggregationFunction.class);
	}

	/**
	 * This map contains legacy aggregation function names and the class, which contains the legacy
	 * functionality. Each of the map elements has to be represented in the
	 * LEGACY_AGGREATION_FUNCTIONS_VERSIONS map, too.
	 */
	private static final Map<String, Class<? extends AggregationFunction>> LEGACY_AGGREATION_FUNCTIONS = new TreeMap<>();
	static {
		// median has been replaced after version 7.4.1
		LEGACY_AGGREATION_FUNCTIONS.put(FUNCTION_NAME_MEDIAN, MedianAggregationFunctionLegacy.class);
	}

	/**
	 * This map contains legacy aggregation function names and the {@link OperatorVersion} until the
	 * legacy function should be used. Each of the map elements has to be represented in the
	 * LEGACY_AGGREATION_FUNCTIONS map, too.
	 */
	private static final Map<String, OperatorVersion> LEGACY_AGGREATION_FUNCTIONS_VERSIONS = new TreeMap<>();
	static {
		LEGACY_AGGREATION_FUNCTIONS_VERSIONS.put(FUNCTION_NAME_MEDIAN, AggregationOperator.VERSION_7_4_0);
	}

    /**
     * The constant AGGREGATION_FUNCTIONS_META_DATA_PROVIDER.
     */
    public static final Map<String, AggregationFunctionMetaDataProvider> AGGREGATION_FUNCTIONS_META_DATA_PROVIDER = new HashMap<>();
	static {
		HashMap<Integer, Integer> transformationRules = new HashMap<Integer, Integer>() {

			private static final long serialVersionUID = 8941596913239332241L;

			{
				put(Ontology.DATE_TIME, Ontology.DATE_TIME);
				put(Ontology.NUMERICAL, Ontology.REAL);
			}
		};
		AGGREGATION_FUNCTIONS_META_DATA_PROVIDER.put(FUNCTION_NAME_SUM,
				new DefaultAggregationFunctionMetaDataProvider(FUNCTION_NAME_SUM, SumAggregationFunction.FUNCTION_SUM,
						FUNCTION_SEPARATOR_OPEN, FUNCTION_SEPARATOR_CLOSE, new int[] { Ontology.NUMERICAL }));
		AGGREGATION_FUNCTIONS_META_DATA_PROVIDER.put(FUNCTION_NAME_SUM_FRACTIONAL,
				new DefaultAggregationFunctionMetaDataProvider("fractionalSum",
						SumFractionalAggregationFunction.FUNCTION_SUM_FRACTIONAL, FUNCTION_SEPARATOR_OPEN,
						FUNCTION_SEPARATOR_CLOSE, new int[] { Ontology.NUMERICAL }));
		AGGREGATION_FUNCTIONS_META_DATA_PROVIDER.put(FUNCTION_NAME_MEDIAN,
				new MappingAggregationFunctionMetaDataProvider(FUNCTION_NAME_MEDIAN,
						MedianAggregationFunction.FUNCTION_MEDIAN, FUNCTION_SEPARATOR_OPEN, FUNCTION_SEPARATOR_CLOSE,
						transformationRules));
		AGGREGATION_FUNCTIONS_META_DATA_PROVIDER.put(FUNCTION_NAME_AVERAGE,
				new MappingAggregationFunctionMetaDataProvider(FUNCTION_NAME_AVERAGE,
						MeanAggregationFunction.FUNCTION_AVERAGE, FUNCTION_SEPARATOR_OPEN, FUNCTION_SEPARATOR_CLOSE,
						transformationRules));
		AGGREGATION_FUNCTIONS_META_DATA_PROVIDER.put(FUNCTION_NAME_VARIANCE,
				new DefaultAggregationFunctionMetaDataProvider(FUNCTION_NAME_VARIANCE,
						VarianceAggregationFunction.FUNCTION_VARIANCE, FUNCTION_SEPARATOR_OPEN, FUNCTION_SEPARATOR_CLOSE,
						new int[] { Ontology.NUMERICAL }, Ontology.REAL));
		AGGREGATION_FUNCTIONS_META_DATA_PROVIDER.put(FUNCTION_NAME_STANDARD_DEVIATION,
				new DefaultAggregationFunctionMetaDataProvider(FUNCTION_NAME_STANDARD_DEVIATION,
						StandardDeviationAggregationFunction.FUNCTION_STANDARD_DEVIATION, FUNCTION_SEPARATOR_OPEN,
						FUNCTION_SEPARATOR_CLOSE, new int[] { Ontology.NUMERICAL }, Ontology.REAL));
		AGGREGATION_FUNCTIONS_META_DATA_PROVIDER.put(FUNCTION_NAME_COUNT_IGNORE_MISSINGS,
				new DefaultAggregationFunctionMetaDataProvider(FUNCTION_NAME_COUNT_IGNORE_MISSINGS,
						CountIgnoringMissingsAggregationFunction.FUNCTION_COUNT, FUNCTION_SEPARATOR_OPEN,
						FUNCTION_SEPARATOR_CLOSE, new int[] { Ontology.ATTRIBUTE_VALUE }, Ontology.INTEGER));
		AGGREGATION_FUNCTIONS_META_DATA_PROVIDER.put(FUNCTION_NAME_COUNT_INCLUDE_MISSINGS,
				new DefaultAggregationFunctionMetaDataProvider(FUNCTION_NAME_COUNT_INCLUDE_MISSINGS,
						CountIncludingMissingsAggregationFunction.FUNCTION_COUNT, FUNCTION_SEPARATOR_OPEN,
						FUNCTION_SEPARATOR_CLOSE, new int[] { Ontology.ATTRIBUTE_VALUE }, Ontology.INTEGER));
		AGGREGATION_FUNCTIONS_META_DATA_PROVIDER.put(FUNCTION_NAME_COUNT,
				new DefaultAggregationFunctionMetaDataProvider(FUNCTION_NAME_COUNT, CountAggregationFunction.FUNCTION_COUNT,
						FUNCTION_SEPARATOR_OPEN, FUNCTION_SEPARATOR_CLOSE, new int[] { Ontology.ATTRIBUTE_VALUE },
						Ontology.INTEGER));
		AGGREGATION_FUNCTIONS_META_DATA_PROVIDER.put(FUNCTION_NAME_COUNT_FRACTIONAL,
				new DefaultAggregationFunctionMetaDataProvider("fractionalCount",
						CountFractionalAggregationFunction.FUNCTION_COUNT_FRACTIONAL, FUNCTION_SEPARATOR_OPEN,
						FUNCTION_SEPARATOR_CLOSE, new int[] { Ontology.ATTRIBUTE_VALUE }, Ontology.REAL));
		AGGREGATION_FUNCTIONS_META_DATA_PROVIDER.put(FUNCTION_NAME_COUNT_PERCENTAGE,
				new DefaultAggregationFunctionMetaDataProvider("percentageCount",
						CountPercentageAggregationFunction.FUNCTION_COUNT_PERCENTAGE, FUNCTION_SEPARATOR_OPEN,
						FUNCTION_SEPARATOR_CLOSE, new int[] { Ontology.ATTRIBUTE_VALUE }, Ontology.REAL));
		AGGREGATION_FUNCTIONS_META_DATA_PROVIDER.put(FUNCTION_NAME_MINIMUM,
				new MappingAggregationFunctionMetaDataProvider(FUNCTION_NAME_MINIMUM, MinAggregationFunction.FUNCTION_MIN,
						FUNCTION_SEPARATOR_OPEN, FUNCTION_SEPARATOR_CLOSE, transformationRules));
		AGGREGATION_FUNCTIONS_META_DATA_PROVIDER.put(FUNCTION_NAME_MAXIMUM,
				new MappingAggregationFunctionMetaDataProvider(FUNCTION_NAME_MAXIMUM, MaxAggregationFunction.FUNCTION_MAX,
						FUNCTION_SEPARATOR_OPEN, FUNCTION_SEPARATOR_CLOSE, transformationRules));
		AGGREGATION_FUNCTIONS_META_DATA_PROVIDER.put(FUNCTION_NAME_LOG_PRODUCT,
				new DefaultAggregationFunctionMetaDataProvider("log product",
						LogProductAggregationFunction.FUNCTION_LOG_PRODUCT, FUNCTION_SEPARATOR_OPEN,
						FUNCTION_SEPARATOR_CLOSE, new int[] { Ontology.NUMERICAL }, Ontology.REAL));
		AGGREGATION_FUNCTIONS_META_DATA_PROVIDER.put(FUNCTION_NAME_PRODOCT,
				new DefaultAggregationFunctionMetaDataProvider(FUNCTION_NAME_PRODOCT,
						ProductAggregationFunction.FUNCTION_PRODUCT, FUNCTION_SEPARATOR_OPEN, FUNCTION_SEPARATOR_CLOSE,
						new int[] { Ontology.NUMERICAL }, Ontology.REAL));

		// Nominal Aggregations
		AGGREGATION_FUNCTIONS_META_DATA_PROVIDER.put(FUNCTION_NAME_MODE,
				new DefaultAggregationFunctionMetaDataProvider(FUNCTION_NAME_MODE, ModeAggregationFunction.FUNCTION_MODE,
						FUNCTION_SEPARATOR_OPEN, FUNCTION_SEPARATOR_CLOSE, new int[] { Ontology.ATTRIBUTE_VALUE }));
		AGGREGATION_FUNCTIONS_META_DATA_PROVIDER.put(FUNCTION_NAME_LEAST,
				new DefaultAggregationFunctionMetaDataProvider(FUNCTION_NAME_LEAST, LeastAggregationFunction.FUNCTION_LEAST,
						FUNCTION_SEPARATOR_OPEN, FUNCTION_SEPARATOR_CLOSE, new int[] { Ontology.NOMINAL },
						Ontology.POLYNOMINAL));
		AGGREGATION_FUNCTIONS_META_DATA_PROVIDER.put(FUNCTION_NAME_LEAST_ONLY_OCCURRING,
				new DefaultAggregationFunctionMetaDataProvider(FUNCTION_NAME_LEAST_ONLY_OCCURRING,
						LeastOccurringAggregationFunction.FUNCTION_LEAST_OCCURRING, FUNCTION_SEPARATOR_OPEN,
						FUNCTION_SEPARATOR_CLOSE, new int[] { Ontology.NOMINAL }, Ontology.POLYNOMINAL));
		AGGREGATION_FUNCTIONS_META_DATA_PROVIDER.put(FUNCTION_NAME_CONCATENATION,
				new DefaultAggregationFunctionMetaDataProvider(FUNCTION_NAME_CONCATENATION,
						ConcatAggregationFunction.FUNCTION_CONCAT, FUNCTION_SEPARATOR_OPEN, FUNCTION_SEPARATOR_CLOSE,
						new int[] { Ontology.NOMINAL }, Ontology.POLYNOMINAL));
	}

	private Attribute sourceAttribute;
	private boolean isIgnoringMissings;
	private boolean isCountingOnlyDistinct;

    /**
     * Instantiates a new Aggregation function.
     *
     * @param sourceAttribute   the source attribute
     * @param ignoreMissings    the ignore missings
     * @param countOnlyDistinct the count only distinct
     */
    public AggregationFunction(Attribute sourceAttribute, boolean ignoreMissings, boolean countOnlyDistinct) {
		this.sourceAttribute = sourceAttribute;
		this.isIgnoringMissings = ignoreMissings;
		this.isCountingOnlyDistinct = countOnlyDistinct;
	}

    /**
     * This returns the attribute this aggregation function will derive the data from.
     *
     * @return the source attribute
     */
    public Attribute getSourceAttribute() {
		return sourceAttribute;
	}

    /**
     * This returns the attribute that will be created in the resulting {@link ExampleSet} to get
     * the aggregated values for each group.
     *
     * @return the target attribute
     */
    public abstract Attribute getTargetAttribute();

    /**
     * This will return the {@link Aggregator} object that computes the value of this particular
     * {@link AggregationFunction} for a specific group.
     *
     * @return the aggregator
     */
    public abstract Aggregator createAggregator();

    /**
     * This determines, if any missing values will be just ignored or counted with the respective
     * aggregation function. Some functions might cope with that, others will just turn to be NaN.
     *
     * @return the boolean
     */
    public boolean isIgnoringMissings() {
		return isIgnoringMissings;
	}

    /**
     * This determines, if values are counted only once, if occurring more than once. Please note
     * that will increase the memory load drastically on numerical attributes.
     *
     * @return the boolean
     */
    public boolean isCountingOnlyDistinct() {
		return isCountingOnlyDistinct;
	}

    /**
     * This will return whether this {@link AggregationFunction} is compatible with the given
     * sourceAttribute.
     *
     * @return the boolean
     */
    public abstract boolean isCompatible();

    /**
     * This method will fill in the default value of this aggregation function. It has to maintain
     * the mapping, if the function is nominal. The default value will be a NaN. Every subclass that
     * wants to change this, has to override this method.
     *
     * @param attribute the attribute
     * @param row       the row
     */
    public void setDefault(Attribute attribute, DoubleArrayDataRow row) {
		row.set(attribute, Double.NaN);
	}

    /**
     * This will create the {@link AggregationFunction} with the given name for the given source
     * Attribute.
     *
     * @param name              please use one of the FUNCTION_NAME_* constants to prevent unnecessary errors
     * @param sourceAttribute   the source attribute
     * @param ignoreMissings    the ignore missings
     * @param countOnlyDistinct the count only distinct
     * @return the aggregation function
     * @throws OperatorException the operator exception
     */
    public static final AggregationFunction createAggregationFunction(String name, Attribute sourceAttribute,
			boolean ignoreMissings, boolean countOnlyDistinct) throws OperatorException {
		Class<? extends AggregationFunction> aggregationFunctionClass = AGGREATION_FUNCTIONS.get(name);
		if (aggregationFunctionClass == null) {
			throw new UserError(null, "aggregation.illegal_function_name", name);
		}
		try {
			Constructor<? extends AggregationFunction> constructor = aggregationFunctionClass.getConstructor(Attribute.class,
					boolean.class, boolean.class);
			return constructor.newInstance(sourceAttribute, ignoreMissings, countOnlyDistinct);
		} catch (Exception e) {
			throw new RuntimeException(
					"All implementations of AggregationFunction need to have a constructor accepting an Attribute and boolean. Other reasons for this error may be class loader problems.",
					e);
		}
	}

    /**
     * This will create the {@link AggregationFunction} with the given name for the given source
     * Attribute with a fallback to a legacy {@link AggregationFunction} if necessary.
     *
     * @param name              please use one of the FUNCTION_NAME_* constants to prevent unnecessary errors
     * @param sourceAttribute   the source attribute
     * @param ignoreMissings    the ignore missings
     * @param countOnlyDistinct the count only distinct
     * @param version           The {@link OperatorVersion} of the executing operator to ensure that a legacy            function will be used for old versions
     * @return the aggregation function
     * @throws OperatorException the operator exception
     */
    public static final AggregationFunction createAggregationFunction(String name, Attribute sourceAttribute,
			boolean ignoreMissings, boolean countOnlyDistinct, OperatorVersion version) throws OperatorException {
		Class<? extends AggregationFunction> aggregationFunctionClass = null;
		// check if the legacy version should be used
		Iterator<String> iterator = LEGACY_AGGREATION_FUNCTIONS.keySet().iterator();
		while (iterator.hasNext()) {
			String current = iterator.next();
			if (name.equals(current) && version.isAtMost(LEGACY_AGGREATION_FUNCTIONS_VERSIONS.get(current))) {
				aggregationFunctionClass = LEGACY_AGGREATION_FUNCTIONS.get(current);
				break;
			}
		}
		if (aggregationFunctionClass == null) {
			aggregationFunctionClass = AGGREATION_FUNCTIONS.get(name);
		}
		if (aggregationFunctionClass == null) {
			throw new UserError(null, "aggregation.illegal_function_name", name);
		}
		try {
			Constructor<? extends AggregationFunction> constructor = aggregationFunctionClass.getConstructor(Attribute.class,
					boolean.class, boolean.class);
			return constructor.newInstance(sourceAttribute, ignoreMissings, countOnlyDistinct);
		} catch (Exception e) {
			throw new RuntimeException(
					"All implementations of AggregationFunction need to have a constructor accepting an Attribute and boolean. Other reasons for this error may be class loader problems.",
					e);
		}
	}

    /**
     * This method can be called in order to get the target attribute meta data after the
     * aggregation functions have been applied. This method can register errors on the given
     * InputPort (if not null), if there's an illegal state. If the state makes applying an
     * {@link AggregationFunction} impossible, this method will return null!
     *
     * @param aggregationFunctionName please use one of the FUNCTION_NAME_* constants to prevent unnecessary errors
     * @param sourceAttributeMetaData the source attribute meta data
     * @param inputPort               the input port
     * @return the attribute meta data
     */
    public static final AttributeMetaData getAttributeMetaData(String aggregationFunctionName,
			AttributeMetaData sourceAttributeMetaData, InputPort inputPort) {
		AggregationFunctionMetaDataProvider metaDataProvider = AGGREGATION_FUNCTIONS_META_DATA_PROVIDER
				.get(aggregationFunctionName);
		if (metaDataProvider != null) {
			return metaDataProvider.getTargetAttributeMetaData(sourceAttributeMetaData, inputPort);
		} else {
			// register error about unknown aggregation function
			if (inputPort != null) {
				inputPort.addError(new SimpleMetaDataError(Severity.ERROR, inputPort,
						"aggregation.unknown_aggregation_function", aggregationFunctionName));
			}
			return null;
		}
	}

    /**
     * This method will return the array containing the names of all available aggregation
     * functions. The names are sorted according to natural ordering.
     *
     * @return the string [ ]
     */
    public static String[] getAvailableAggregationFunctionNames() {
		String[] names = new String[AGGREATION_FUNCTIONS.size()];
		int i = 0;
		for (String name : AGGREATION_FUNCTIONS.keySet()) {
			names[i] = name;
			i++;
		}

		return names;
	}

    /**
     * This method will return a list of aggregate functions that are compatible with the provided
     * valueType.
     *
     * @param valueType a valueType found in {@link Ontology}.
     * @return the compatible aggregation function names
     */
    public static List<String> getCompatibleAggregationFunctionNames(int valueType) {
		List<String> compatibleAggregationFunctions = new LinkedList<>();

		Attribute sampleAttribute = AttributeFactory.createAttribute(valueType);

		for (String name : getAvailableAggregationFunctionNames()) {
			try {
				if (createAggregationFunction(name, sampleAttribute, true, true).isCompatible()) {
					compatibleAggregationFunctions.add(name);
				}
			} catch (OperatorException e) {
				// do nothing
			}
		}

		return compatibleAggregationFunctions;
	}

    /**
     * With this method extensions might register additional aggregation functions if needed.
     *
     * @param name             the name
     * @param clazz            the clazz
     * @param metaDataProvider the meta data provider
     */
    public static void registerNewAggregationFunction(String name, Class<? extends AggregationFunction> clazz,
			AggregationFunctionMetaDataProvider metaDataProvider) {
		AGGREATION_FUNCTIONS.put(name, clazz);
		AGGREGATION_FUNCTIONS_META_DATA_PROVIDER.put(name, metaDataProvider);
	}

    /**
     * This function is called once during the aggregation process, when all {@link Aggregator}s are
     * known. In this step post-processing like normalization etc. can be done.
     * <p>
     * The default implementation does nothing.
     *
     * @param allAggregators the all aggregators
     */
    public void postProcessing(List<Aggregator> allAggregators) {
		// do nothing
	}

}
