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
package com.rapidminer.operator.features.construction;

import com.rapidminer.example.set.AttributeWeightedExampleSet;
import com.rapidminer.operator.performance.PerformanceVector;

import java.util.*;


/**
 * A set of individuals. Stores generation number and best individuals.
 *
 * @author Ingo Mierswa
 */
public class ExampleSetBasedPopulation {

    /**
     * The constant PERFORMANCE_COMPARATOR.
     */
    public static final Comparator<ExampleSetBasedIndividual> PERFORMANCE_COMPARATOR = new Comparator<ExampleSetBasedIndividual>() {

		@Override
		public int compare(ExampleSetBasedIndividual i1, ExampleSetBasedIndividual i2) {
			PerformanceVector p1 = i1.getPerformance();
			PerformanceVector p2 = i2.getPerformance();
			return p1.compareTo(p2);
		}
	};

	/** List of ExampleSet */
	private ArrayList<ExampleSetBasedIndividual> individuals = new ArrayList<ExampleSetBasedIndividual>();

	/** Current generation number */
	private int generation = 0;

	/** Generation of the last improval. */
	private int generationOfLastImproval = 0;

	/** All generations' best individual. */
	private ExampleSetBasedIndividual bestEver;

	/** All generations' best performance. */
	private PerformanceVector bestPerformanceEver;

	/** The currently best individual. */
	private ExampleSetBasedIndividual currentBest;

	/** The currently best performance. */
	private PerformanceVector currentBestPerformance;

    /**
     * Construct an empty generation.
     */
    public ExampleSetBasedPopulation() {}

    /**
     * Removes all individuals.
     */
    public void clear() {
		individuals.clear();
	}

    /**
     * Adds a single individual.  @param individual the individual
     *
     * @param individual the individual
     */
    public void add(ExampleSetBasedIndividual individual) {
		individuals.add(individual);
	}

    /**
     * Adds all individuals from the given collection.  @param newIndividuals the new individuals
     *
     * @param newIndividuals the new individuals
     */
    public void addAllIndividuals(Collection<ExampleSetBasedIndividual> newIndividuals) {
		individuals.addAll(newIndividuals);
	}

    /**
     * Removes a single individual.  @param individual the individual
     *
     * @param individual the individual
     */
    public void remove(ExampleSetBasedIndividual individual) {
		individuals.remove(individual);
	}

    /**
     * Removes a single individual.  @param i the
     *
     * @param i the
     */
    public void remove(int i) {
		individuals.remove(i);
	}

    /**
     * Returns a single individual.  @param i the
     *
     * @param i the
     * @return the example set based individual
     */
    public ExampleSetBasedIndividual get(int i) {
		return individuals.get(i);
	}

    /**
     * Returns the number of all individuals.  @return the number of individuals
     *
     * @return the number of individuals
     */
    public int getNumberOfIndividuals() {
		return individuals.size();
	}

    /**
     * Returns true is the population contains no individuals.  @return the boolean
     *
     * @return the boolean
     */
    public boolean empty() {
		return individuals.size() == 0;
	}

    /**
     * Increase the generation number by one.
     */
    public void nextGeneration() {
		generation++;
	}

    /**
     * Returns the current number of the generation.  @return the generation
     *
     * @return the generation
     */
    public int getGeneration() {
		return generation;
	}

    /**
     * Returns the number of generations without improval.
     *
     * @return the generations without improval
     */
    public int getGenerationsWithoutImproval() {
		return generation - generationOfLastImproval;
	}

    /**
     * Remember the current generation's best individual and update the best individual.
     */
    public void updateEvaluation() {
		currentBest = searchBest();
		currentBestPerformance = currentBest == null ? null : currentBest.getPerformance();
		if (bestEver == null || currentBest != null && currentBestPerformance.compareTo(bestPerformanceEver) > 0) {
			bestEver = new ExampleSetBasedIndividual(new AttributeWeightedExampleSet(currentBest.getExampleSet()));
			bestEver.setPerformance(currentBest.getPerformance());
			bestPerformanceEver = bestEver.getPerformance();
			generationOfLastImproval = generation;
		}
	}

	/**
	 * Finds the current generation's best individual. Returns null, if there are unevaluated
	 * individuals. Probably you will want to use <tt>bestEver()</tt> or <tt>lastBest()</tt> because
	 * they don't cause comparisons.
	 */
	private ExampleSetBasedIndividual searchBest() {
		try {
			return Collections.max(individuals, PERFORMANCE_COMPARATOR);
		} catch (NullPointerException e) {
			return null;
		} catch (NoSuchElementException e) {
			return null;
		}
	}

    /**
     * Returns the best performance.  @return the best performance ever
     *
     * @return the best performance ever
     */
    public PerformanceVector getBestPerformanceEver() {
		return bestPerformanceEver;
	}

    /**
     * Returns all generations' best individual.  @return the best individual ever
     *
     * @return the best individual ever
     */
    public ExampleSetBasedIndividual getBestIndividualEver() {
		return bestEver;
	}

    /**
     * Returns the last generation's best individual.  @return the current best
     *
     * @return the current best
     */
    public ExampleSetBasedIndividual getCurrentBest() {
		return currentBest;
	}

    /**
     * Returns the last generation's best performance.  @return the current best performance
     *
     * @return the current best performance
     */
    public PerformanceVector getCurrentBestPerformance() {
		return currentBestPerformance;
	}

    /**
     * Sorts the individuals in ascending order according to their performance, thus the best one
     * will be in last position.
     */
    public void sort() {
		Collections.sort(individuals, PERFORMANCE_COMPARATOR);
	}

	@Override
	public String toString() {
		StringBuffer s = new StringBuffer("Generation: " + generation + ",size: " + getNumberOfIndividuals()
				+ ", individual sizes: [");
		for (int i = 0; i < getNumberOfIndividuals(); i++) {
			if (i > 0) {
				s.append(",");
			}
			s.append(get(i).getExampleSet().getNumberOfUsedAttributes());
		}
		s.append("]");
		return s.toString();
	}
}
