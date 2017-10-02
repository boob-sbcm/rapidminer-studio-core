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
package com.rapidminer.example.set;

import com.rapidminer.example.Attribute;
import com.rapidminer.example.Attributes;
import com.rapidminer.example.Example;
import com.rapidminer.example.ExampleSet;
import com.rapidminer.example.table.ExampleTable;
import com.rapidminer.operator.Annotations;
import com.rapidminer.operator.OperatorProgress;
import com.rapidminer.operator.ProcessStoppedException;
import com.rapidminer.tools.Ontology;

import java.util.*;


/**
 * <p>
 * This example set uses a mapping of indices to access the examples provided by the parent example
 * set. In contrast to the mapped example set, where the sorting would have been disturbed for
 * performance reasons this class simply use the given mapping. A convenience constructor exist to
 * create a view based on the sorting based on a specific attribute.
 * </p>
 *
 * @author Ingo Mierswa, Nils Woehler
 */
public class SortedExampleSet extends AbstractExampleSet {

	private static final long serialVersionUID = 3937175786207007275L;

    /**
     * The constant SORTING_DIRECTIONS.
     */
    public static final String[] SORTING_DIRECTIONS = { "increasing", "decreasing" };

    /**
     * The constant INCREASING.
     */
    public static final int INCREASING = 0;
    /**
     * The constant DECREASING.
     */
    public static final int DECREASING = 1;

	private static class SortingIndex {

		final private Object key;
		final private int index;

        /**
         * Instantiates a new Sorting index.
         *
         * @param key   the key
         * @param index the index
         */
        public SortingIndex(Object key, int index) {
			this.key = key;
			this.index = index;
		}

        /**
         * Gets index.
         *
         * @return the index
         */
        public int getIndex() {
			return index;
		}

        /**
         * Gets key as date.
         *
         * @return the key as date
         */
        public Date getKeyAsDate() {
			return (Date) key;
		}

        /**
         * Gets key as string.
         *
         * @return the key as string
         */
        public String getKeyAsString() {
			return (String) key;
		}

        /**
         * Gets key as double.
         *
         * @return the key as double
         */
        public Double getKeyAsDouble() {
			return (Double) key;
		}

		@Override
		public String toString() {
			return key + " --> " + index;
		}

	}

	/** The parent example set. */
	private ExampleSet parent;

	/** The used mapping. */
	private int[] mapping;

    /**
     * Instantiates a new Sorted example set.
     *
     * @param parent           the parent
     * @param sortingAttribute the sorting attribute
     * @param sortingDirection the sorting direction
     */
    public SortedExampleSet(ExampleSet parent, Attribute sortingAttribute, int sortingDirection) {
		try {
			createSortedExampleSet(parent, sortingAttribute, sortingDirection, null);
		} catch (ProcessStoppedException e) {
			// Cannot happen, OperatorProgress is null
		}
	}

    /**
     * Instantiates a new Sorted example set.
     *
     * @param parent           the parent
     * @param sortingAttribute the sorting attribute
     * @param sortingDirection the sorting direction
     * @param progress         the progress
     * @throws ProcessStoppedException the process stopped exception
     */
    public SortedExampleSet(ExampleSet parent, final Attribute sortingAttribute, int sortingDirection,
			OperatorProgress progress) throws ProcessStoppedException {
		createSortedExampleSet(parent, sortingAttribute, sortingDirection, progress);
	}

	/**
	 * Helper method for constructor
	 */
	private void createSortedExampleSet(ExampleSet parent, final Attribute sortingAttribute, int sortingDirection,
			OperatorProgress progress) throws ProcessStoppedException {
		this.parent = (ExampleSet) parent.clone();
		List<SortingIndex> sortingIndex = new ArrayList<SortingIndex>(parent.size());
		if (progress != null) {
			progress.setTotal(100);
		}

		// create sort index
		int exampleCounter = 0;
		int progressTriggerCounter = 0;
		Iterator<Example> i = parent.iterator();
		while (i.hasNext()) {
			Example example = i.next();
			if (Ontology.ATTRIBUTE_VALUE_TYPE.isA(sortingAttribute.getValueType(), Ontology.DATE_TIME)) {
				sortingIndex.add(new SortingIndex(example.getDateValue(sortingAttribute), exampleCounter));
			} else if (sortingAttribute.isNumerical()) {
				sortingIndex.add(new SortingIndex(example.getNumericalValue(sortingAttribute), exampleCounter));

			} else {
				sortingIndex.add(new SortingIndex(example.getNominalValue(sortingAttribute), exampleCounter));

			}
			exampleCounter++;
			progressTriggerCounter++;
			if (progress != null && progressTriggerCounter > 2_000_000) {
				progressTriggerCounter = 0;
				progress.setCompleted((int) ((long) exampleCounter * 40 / parent.size()));
			}
		}
		if (progress != null) {
			progress.setCompleted(40);
		}

		// create comparator
		Comparator<SortingIndex> sortComparator = new Comparator<SortingIndex>() {

			@Override
			public int compare(SortingIndex o1, SortingIndex o2) {
				if (Ontology.ATTRIBUTE_VALUE_TYPE.isA(sortingAttribute.getValueType(), Ontology.DATE_TIME)) {
					Date firstDate = o1.getKeyAsDate();
					Date secondDate = o2.getKeyAsDate();
					if (firstDate == secondDate || firstDate == null) {
						return 0;
					} else {
						return firstDate.compareTo(secondDate);
					}
				} else if (sortingAttribute.isNumerical()) {
					Double firstDouble = o1.getKeyAsDouble();
					Double secondDouble = o2.getKeyAsDouble();
					if (firstDouble == null || firstDouble.equals(secondDouble)) {
						return 0;
					} else {
						return firstDouble.compareTo(secondDouble);
					}
				} else if (sortingAttribute.isNominal()) {
					String firstString = o1.getKeyAsString();
					String secondString = o2.getKeyAsString();
					if (firstString == null || firstString.equals(secondString)) {
						return 0;
					} else {
						return firstString.compareTo(secondString);
					}
				}
				return 0;
			}
		};

		// sort
		if (sortingDirection == INCREASING) {
			Collections.sort(sortingIndex, sortComparator);
		} else {
			Collections.sort(sortingIndex, Collections.reverseOrder(sortComparator));
		}
		if (progress != null) {
			progress.setCompleted(60);
		}

		// change mapping
		int[] mapping = new int[parent.size()];
		exampleCounter = 0;
		progressTriggerCounter = 0;
		Iterator<SortingIndex> k = sortingIndex.iterator();
		while (k.hasNext()) {
			Integer index = k.next().getIndex();
			mapping[exampleCounter++] = index;
			progressTriggerCounter++;
			if (progress != null && progressTriggerCounter > 2_000_000) {
				progressTriggerCounter = 0;
				progress.setCompleted((int) (60 + (long) exampleCounter * 40 / sortingIndex.size()));
			}
		}

		this.mapping = mapping;
	}

    /**
     * Constructs an example set based on the given sort mapping.  @param parent the parent
     *
     * @param parent  the parent
     * @param mapping the mapping
     */
    public SortedExampleSet(ExampleSet parent, int[] mapping) {
		this.parent = (ExampleSet) parent.clone();
		this.mapping = mapping;
	}

    /**
     * Clone constructor.  @param exampleSet the example set
     *
     * @param exampleSet the example set
     */
    public SortedExampleSet(SortedExampleSet exampleSet) {
		this.parent = (ExampleSet) exampleSet.parent.clone();
		this.mapping = new int[exampleSet.mapping.length];
		System.arraycopy(exampleSet.mapping, 0, this.mapping, 0, exampleSet.mapping.length);
	}

	@Override
	public boolean equals(Object o) {
		if (!super.equals(o)) {
			return false;
		}
		if (!(o instanceof SortedExampleSet)) {
			return false;
		}

		SortedExampleSet other = (SortedExampleSet) o;
		if (this.mapping.length != other.mapping.length) {
			return false;
		}
		for (int i = 0; i < this.mapping.length; i++) {
			if (this.mapping[i] != other.mapping[i]) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		return super.hashCode() ^ Arrays.hashCode(this.mapping);
	}

	/** Returns a {@link SortedExampleReader}. */
	@Override
	public Iterator<Example> iterator() {
		return new SortedExampleReader(this);
	}

	/** Returns the i-th example in the mapping. */
	@Override
	public Example getExample(int index) {
		if (index < 0 || index >= this.mapping.length) {
			throw new RuntimeException("Given index '" + index + "' does not fit the mapped ExampleSet!");
		} else {
			return this.parent.getExample(this.mapping[index]);
		}
	}

	/** Counts the number of examples. */
	@Override
	public int size() {
		return mapping.length;
	}

	@Override
	public Attributes getAttributes() {
		return this.parent.getAttributes();
	}

	@Override
	public Annotations getAnnotations() {
		return parent.getAnnotations();
	}

	@Override
	public ExampleTable getExampleTable() {
		return this.parent.getExampleTable();
	}

	@Override
	public void cleanup() {
		parent.cleanup();
	}
}
