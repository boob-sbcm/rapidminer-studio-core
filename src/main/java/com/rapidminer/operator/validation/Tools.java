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
package com.rapidminer.operator.validation;

import com.rapidminer.operator.IOContainer;
import com.rapidminer.operator.IOObject;
import com.rapidminer.operator.MissingIOObjectException;
import com.rapidminer.operator.OperatorException;
import com.rapidminer.operator.performance.PerformanceVector;
import com.rapidminer.operator.ports.InputPort;
import com.rapidminer.operator.ports.OutputPort;
import com.rapidminer.operator.ports.PortPairExtender;
import com.rapidminer.tools.math.AverageVector;

import java.util.List;


/**
 * Tools class for validation operators. This class provides methods for average building of
 * performance vectors and other average vectors.
 *
 * @author Ingo Mierswa
 */
public class Tools {

    /**
     * Searches for the average vectors in the given IOContainer and fills the list if it is empty
     * or build the averages. Only performance vectors are averaged.
     *
     * @param evalOutput     the eval output
     * @param averageVectors the average vectors
     * @throws OperatorException the operator exception
     * @deprecated This method is no longer needed.
     */
    @Deprecated
	public static void handleAverages(IOContainer evalOutput, List<AverageVector> averageVectors) throws OperatorException {
		handleAverages(evalOutput, averageVectors, true);
	}

    /**
     * Searches for the average vectors in the given IOContainer and fills the list if it is empty
     * or build the averages. The boolean flag onlyPerformanceVectors indicates if the average
     * should be built from PerformanceVectors only or from other AverageVectors too. Throws a
     * NullPointerException if averageVectors is null.
     *
     * @param evalOutput             the eval output
     * @param averageVectors         the average vectors
     * @param onlyPerformanceVectors the only performance vectors
     * @throws OperatorException the operator exception
     * @deprecated This method is no longer needed.
     */
    @Deprecated
	public static void handleAverages(IOContainer evalOutput, List<AverageVector> averageVectors,
			boolean onlyPerformanceVectors) throws OperatorException {
		Class<? extends IOObject> requestClass = AverageVector.class;
		if (onlyPerformanceVectors) {
			requestClass = PerformanceVector.class;
		}
		if (averageVectors.size() == 0) {
			// first run --> do not calculate average values but fill the vector list
			boolean inputOk = true;
			while (inputOk) {
				try {
					AverageVector currentAverage = (AverageVector) evalOutput.remove(requestClass);
					// since this averages might be averages of averages and averagecount could be
					// greater 0: reset for equal weighting
					averageVectors.add(currentAverage);
					for (int i = 0; i < currentAverage.getSize(); i++) {
						// Note: 0 is correct here (and not 1). averageCount will be set to 1
						// (and immediately after this to 2) in the first call to
						// Averageble.buildAverage()
						currentAverage.getAveragable(i).setAverageCount(0);
					}
				} catch (MissingIOObjectException e) {
					inputOk = false;
				}
			}
		} else {
			// later runs --> build the average with corresponding average vectors
			for (int n = 0; n < averageVectors.size(); n++) {
				AverageVector currentAverage = (AverageVector) evalOutput.remove(requestClass);
				AverageVector oldVector = averageVectors.get(n); // get last averaged average vector
				if (!oldVector.getClass().isInstance(currentAverage)) {
					throw new OperatorException("ValidationChain: Average vector mismatch! Fatal error.");
				}
				for (int i = 0; i < oldVector.size(); i++) {
					oldVector.getAveragable(i).buildAverage(currentAverage.getAveragable(i)); // build
																								// average
																								// for
																								// all
																								// criteria
				}
			}
		}
	}

    /**
     * Returns the first performance vector in the given list or null if no performance vectors
     * exist.
     *
     * @param averageVectors the average vectors
     * @return the performance vector
     * @deprecated This method is no longer needed.
     */
    @Deprecated
	public static PerformanceVector getPerformanceVector(List<AverageVector> averageVectors) {
		java.util.Iterator<AverageVector> i = averageVectors.iterator();
		while (i.hasNext()) {
			AverageVector currentAverage = i.next();
			if (currentAverage instanceof PerformanceVector) {
				return (PerformanceVector) currentAverage;
			}
		}
		return null;
	}

    /**
     * Iterates {@link #buildAverages(InputPort, OutputPort)} over pairs generated by this extender.
     *
     * @param portExtender the port extender
     * @throws OperatorException the operator exception
     */
    public static void buildAverages(PortPairExtender portExtender) throws OperatorException {
		for (PortPairExtender.PortPair pair : portExtender.getManagedPairs()) {
			buildAverages(pair.getInputPort(), pair.getOutputPort());
		}
	}

    /**
     * Build averages.
     *
     * @param inputPort  the input port
     * @param outputPort the output port
     * @throws OperatorException the operator exception
     */
/*
     * Takes AverageVector from the input port, and, if no output is still null (i.e. we are in the
	 * first iteration), copies the vector to the output. If the output is not null (i.e. we are in
	 * the second or later iteration) builds the average. Null inputs are ignored.
	 */
	public static void buildAverages(InputPort inputPort, OutputPort outputPort) throws OperatorException {
		AverageVector performance = inputPort.getDataOrNull(AverageVector.class);
		if (performance == null) {
			return;
		}
		if (outputPort.getDataOrNull(IOObject.class) == null) {
			// we don't have data yet, so copy to output
			// since this averages might be averages of averages and averagecount could be greater
			// 0: reset for equal weighting
			for (int i = 0; i < performance.size(); i++) {
				performance.getAveragable(i).setAverageCount(0);
			}
			outputPort.deliver(performance);
		} else {
			AverageVector average = outputPort.getData(AverageVector.class);
			if (!average.getClass().isInstance(performance)) {
				// this cannot happen
				throw new RuntimeException("Average vector mismatch!");
			}
			// build average for all criteria
			for (int i = 0; i < average.size(); i++) {
				average.getAveragable(i).buildAverage(performance.getAveragable(i));
			}
			outputPort.deliver(average);
		}
	}

}
