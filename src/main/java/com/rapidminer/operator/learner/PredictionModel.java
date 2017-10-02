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
package com.rapidminer.operator.learner;

import java.util.Iterator;

import com.rapidminer.example.Attribute;
import com.rapidminer.example.Attributes;
import com.rapidminer.example.ExampleSet;
import com.rapidminer.example.set.ExampleSetUtilities;
import com.rapidminer.example.set.HeaderExampleSet;
import com.rapidminer.example.set.RemappedExampleSet;
import com.rapidminer.example.table.AttributeFactory;
import com.rapidminer.example.table.ExampleTable;
import com.rapidminer.operator.AbstractModel;
import com.rapidminer.operator.OperatorException;
import com.rapidminer.tools.Ontology;


/**
 * PredictionModel is the superclass for all objects generated by learners, i.e. it can be used to
 * create a prediction for a given example set.
 *
 * @author Ingo Mierswa
 */
public abstract class PredictionModel extends AbstractModel {

	/**
	 *
	 */
	private static final long serialVersionUID = 6295359038239089617L;

	/**
	 * This parameter specifies the data types at which the model can be applied on.
	 */
	private ExampleSetUtilities.TypesCompareOption compareDataType;

	/**
	 * This parameter specifies the relation between the training {@link ExampleSet} and the input
	 * {@link ExampleSet} which is needed to apply the model on the input {@link ExampleSet}.
	 */
	private ExampleSetUtilities.SetsCompareOption compareSetSize;

    /**
     * Created a new prediction model which was built on the given example set. Please note that the
     * given example set is automatically transformed into a {@link HeaderExampleSet} which means
     * that no reference to the data itself is kept but only to the header, i.e. to the attribute
     * meta descriptions.
     *
     * @param trainingExampleSet the training example set
     * @deprecated Since RapidMiner Studio 6.0.009. Please use the new Constructor             {@link #PredictionModel(ExampleSet, com.rapidminer.example.set.ExampleSetUtilities.SetsCompareOption, com.rapidminer.example.set.ExampleSetUtilities.TypesCompareOption)}             which offers the possibility to check for AttributeType and kind of ExampleSet             before execution.
     */
    @Deprecated
	protected PredictionModel(ExampleSet trainingExampleSet) {
		super(trainingExampleSet);
		compareDataType = null;
		compareSetSize = null;
	}

    /**
     * Creates a new prediction model which is build based on the given {@link ExampleSet}. Please
     * note that the given ExampleSet is automatically transformed into a {@link HeaderExampleSet}
     * which means that no reference to the data itself is kept but only to the header, i.e., to the
     * attribute meta descriptions.
     *
     * @param trainingExampleSet  the training example set
     * @param sizeCompareOperator describes the allowed relations between the given ExampleSet and future            ExampleSets on which this Model will be applied. If this parameter is null no            error will be thrown.
     * @param typeCompareOperator describes the allowed relations between the types of the attributes of the given            ExampleSet and the types of future attributes of ExampleSet on which this Model            will be applied. If this parameter is null no error will be thrown.
     */
    protected PredictionModel(ExampleSet trainingExampleSet, ExampleSetUtilities.SetsCompareOption sizeCompareOperator,
			ExampleSetUtilities.TypesCompareOption typeCompareOperator) {
		super(trainingExampleSet);
		this.compareDataType = typeCompareOperator;
		this.compareSetSize = sizeCompareOperator;
	}

    /**
     * Subclasses should iterate through the given example set and set the prediction for each
     * example. The given predicted label attribute was already be added to the example set and
     * should be used to set the predicted values.
     *
     * @param exampleSet     the example set
     * @param predictedLabel the predicted label
     * @return the example set
     * @throws OperatorException the operator exception
     */
    public abstract ExampleSet performPrediction(ExampleSet exampleSet, Attribute predictedLabel) throws OperatorException;

	/**
	 * Applies the model by creating a predicted label attribute and setting the predicted label
	 * values.
	 */
	@Override
	public ExampleSet apply(ExampleSet exampleSet) throws OperatorException {
		ExampleSet mappedExampleSet = RemappedExampleSet.create(exampleSet, getTrainingHeader(), false, true);
		checkCompatibility(mappedExampleSet);
		Attribute predictedLabel = createPredictionAttributes(mappedExampleSet, getLabel());
		ExampleSet result = performPrediction(mappedExampleSet, predictedLabel);

		// Copy in order to avoid RemappedExampleSets wrapped around each other accumulating over
		// time
		exampleSet = (ExampleSet) exampleSet.clone();
		copyPredictedLabel(result, exampleSet);

		return exampleSet;
	}

    /**
     * Returns the label attribute.  @return the label
     *
     * @return the label
     */
    public Attribute getLabel() {
		return getTrainingHeader().getAttributes().getLabel();
	}

    /**
     * This method is invoked before the model is actually applied. The default implementation
     * performs some basic compatibility checks and writes warnings if the given example set (for
     * applying the model) does not fit the training example set. Subclasses might override this
     * method and might throw exceptions which will prevent the application of the model.
     *
     * @param exampleSet the example set
     * @throws OperatorException the operator exception
     */
    protected void checkCompatibility(ExampleSet exampleSet) throws OperatorException {
		ExampleSet trainingHeaderSet = getTrainingHeader();
		// check given constraints (might throw an UserError)
		ExampleSetUtilities.checkAttributesMatching(getOperator(), trainingHeaderSet.getAttributes(),
				exampleSet.getAttributes(), compareSetSize, compareDataType);
		// check number of attributes
		if (exampleSet.getAttributes().size() != trainingHeaderSet.getAttributes().size()) {
			logWarning("The number of regular attributes of the given example set does not fit the number of attributes of the training example set, training: "
					+ trainingHeaderSet.getAttributes().size() + ", application: " + exampleSet.getAttributes().size());
		} else {
			// check order of attributes
			Iterator<Attribute> trainingIt = trainingHeaderSet.getAttributes().iterator();
			Iterator<Attribute> applyIt = exampleSet.getAttributes().iterator();
			while (trainingIt.hasNext() && applyIt.hasNext()) {
				if (!trainingIt.next().getName().equals(applyIt.next().getName())) {
					logWarning("The order of attributes is not equal for the training and the application example set. This might lead to problems for some models.");
					break;
				}
			}
		}

		// check if all training attributes are part of the example set and have the same value
		// types and values
		for (Attribute trainingAttribute : trainingHeaderSet.getAttributes()) {
			String name = trainingAttribute.getName();
			Attribute attribute = exampleSet.getAttributes().getRegular(name);
			if (attribute == null) {
				logWarning("The given example set does not contain a regular attribute with name '" + name
						+ "'. This might cause problems for some models depending on this particular attribute.");
			} else {
				if (trainingAttribute.getValueType() != attribute.getValueType()) {
					logWarning("The value types between training and application differ for attribute '" + name
							+ "', training: " + Ontology.VALUE_TYPE_NAMES[trainingAttribute.getValueType()]
							+ ", application: " + Ontology.VALUE_TYPE_NAMES[attribute.getValueType()]);
				} else {
					// check nominal values
					if (trainingAttribute.isNominal()) {
						if (trainingAttribute.getMapping().size() != attribute.getMapping().size()) {
							logWarning("The number of nominal values is not the same for training and application for attribute '"
									+ name
									+ "', training: "
									+ trainingAttribute.getMapping().size()
									+ ", application: "
									+ attribute.getMapping().size());
						} else {
							for (String v : trainingAttribute.getMapping().getValues()) {
								int trainingIndex = trainingAttribute.getMapping().getIndex(v);
								int applicationIndex = attribute.getMapping().getIndex(v);
								if (trainingIndex != applicationIndex) {
									logWarning("The internal nominal mappings are not the same between training and application for attribute '"
											+ name + "'. This will probably lead to wrong results during model application.");
									break;
								}
							}
						}
					}
				}
			}
		}
	}

    /**
     * This method creates prediction attributes like the predicted label and confidences if needed.
     *
     * @param exampleSet the example set
     * @param label      the label
     * @return the attribute
     */
    protected Attribute createPredictionAttributes(ExampleSet exampleSet, Attribute label) {
		// create and add prediction attribute
		Attribute predictedLabel = AttributeFactory.createAttribute(label, Attributes.PREDICTION_NAME);
		predictedLabel.clearTransformations();
		ExampleTable table = exampleSet.getExampleTable();
		table.addAttribute(predictedLabel);
		exampleSet.getAttributes().setPredictedLabel(predictedLabel);

		// check whether confidence labels should be constructed
		if (supportsConfidences(label)) {
			for (String value : predictedLabel.getMapping().getValues()) {
				Attribute confidence = AttributeFactory.createAttribute(Attributes.CONFIDENCE_NAME + "(" + value + ")",
						Ontology.REAL);
				table.addAttribute(confidence);
				exampleSet.getAttributes().setSpecialAttribute(confidence, Attributes.CONFIDENCE_NAME + "_" + value);
			}
		}
		return predictedLabel;
	}

    /**
     * This method determines if confidence attributes are created depending on the current label.
     * Usually this depends only on the fact that the label is nominal, but subclasses might
     * override this to avoid attribute construction for confidences.
     *
     * @param label the label
     * @return the boolean
     */
    protected boolean supportsConfidences(Attribute label) {
		return label != null && label.isNominal();
	}

    /**
     * Creates a predicted label for the given example set based on the label attribute defined for
     * this prediction model. Subclasses which override this method should first invoke
     * super.createPredictedLabel(exampleSet) and should then replace the attribute with a new
     * predicted label attribute via a method call like
     * <code>exampleSet.replaceAttribute(predictedLabel, AttributeFactory.changeValueType(predictedLabel, Ontology.REAL)); </code>
     * . This might be useful in cases where a crisp nominal prediction should be replaced by
     * confidence predictions.
     *
     * @param exampleSet the example set
     * @param label      the label
     * @return the attribute
     */
    public static Attribute createPredictedLabel(ExampleSet exampleSet, Attribute label) {
		// create and add prediction attribute
		Attribute predictedLabel = AttributeFactory.createAttribute(label, Attributes.PREDICTION_NAME);
		predictedLabel.clearTransformations();
		ExampleTable table = exampleSet.getExampleTable();
		table.addAttribute(predictedLabel);
		exampleSet.getAttributes().setPredictedLabel(predictedLabel);

		// create and add confidence attributes for nominal labels
		if (label.isNominal()) {
			for (String value : predictedLabel.getMapping().getValues()) {
				Attribute confidence = AttributeFactory.createAttribute(Attributes.CONFIDENCE_NAME + "(" + value + ")",
						Ontology.REAL);
				table.addAttribute(confidence);
				exampleSet.getAttributes().setSpecialAttribute(confidence, Attributes.CONFIDENCE_NAME + "_" + value);
			}
		}
		return predictedLabel;
	}

	@Override
	public String toString() {
		return getName() + " (prediction model for label " + getTrainingHeader().getAttributes().getLabel().getName() + ")";
	}

    /**
     * Helper method in order to reduce memory consumption. This method should be invoked after a
     * predicted label and confidence are not longer needed, e.g. after each iteration of a
     * crossvalidation or after a meta learning iteration.
     *
     * @param exampleSet the example set
     */
    public static void removePredictedLabel(ExampleSet exampleSet) {
		removePredictedLabel(exampleSet, true, true);
	}

    /**
     * Helper method in order to lower memory consumption. This method should be invoked after a
     * predicted label and confidence are not longer needed, e.g. after each crossvalidation run or
     * after a meta learning iteration.
     *
     * @param exampleSet                 the example set
     * @param removePredictionFromTable  the remove prediction from table
     * @param removeConfidencesFromTable the remove confidences from table
     */
    public static void removePredictedLabel(ExampleSet exampleSet, boolean removePredictionFromTable,
			boolean removeConfidencesFromTable) {
		Attribute predictedLabel = exampleSet.getAttributes().getPredictedLabel();
		if (predictedLabel != null) { // remove old predicted label
			if (predictedLabel.isNominal()) {
				for (String value : predictedLabel.getMapping().getValues()) {
					Attribute currentConfidenceAttribute = exampleSet.getAttributes().getSpecial(
							Attributes.CONFIDENCE_NAME + "_" + value);
					if (currentConfidenceAttribute != null) {
						exampleSet.getAttributes().remove(currentConfidenceAttribute);
						if (removeConfidencesFromTable) {
							exampleSet.getExampleTable().removeAttribute(currentConfidenceAttribute);
						}
					}
				}
			}
			exampleSet.getAttributes().remove(predictedLabel);
			if (removePredictionFromTable) {
				exampleSet.getExampleTable().removeAttribute(predictedLabel);
			}
		}
	}

    /**
     * Copies the predicted label from the source example set to the destination example set. Does
     * nothing if the source does not contain a predicted label.
     *
     * @param source      the source
     * @param destination the destination
     */
    public static void copyPredictedLabel(ExampleSet source, ExampleSet destination) {
		Attribute predictedLabel = source.getAttributes().getPredictedLabel();
		if (predictedLabel != null) {
			// remove attributes but do not delete the columns from the table, otherwise copying is
			// not possible
			removePredictedLabel(destination, false, false);
			if (predictedLabel.isNominal()) {
				for (String value : predictedLabel.getMapping().getValues()) {
					Attribute currentConfidenceAttribute = source.getAttributes()
							.getSpecial(Attributes.CONFIDENCE_NAME + "_" + value);

					// it's possible that the model does not create confidences for all label
					// values, so check for null (e.g. OneClass-SVM)
					if (currentConfidenceAttribute != null) {
						Attribute copyOfCurrentConfidenceAttribute = AttributeFactory
								.createAttribute(currentConfidenceAttribute);
						destination.getAttributes().setSpecialAttribute(copyOfCurrentConfidenceAttribute,
								Attributes.CONFIDENCE_NAME + "_" + value);
					}
				}
			}
			Attribute copyOfPredictedLabel = AttributeFactory.createAttribute(predictedLabel);
			destination.getAttributes().setPredictedLabel(copyOfPredictedLabel);
		}

		Attribute costs = source.getAttributes().getCost();
		if (costs != null) {
			destination.getAttributes().setSpecialAttribute(costs, Attributes.CLASSIFICATION_COST);
		}
	}
}
