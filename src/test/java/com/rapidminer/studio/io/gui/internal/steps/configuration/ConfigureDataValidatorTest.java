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
package com.rapidminer.studio.io.gui.internal.steps.configuration;

import com.rapidminer.core.io.data.ColumnMetaData;
import com.rapidminer.core.io.data.ColumnMetaData.ColumnType;
import com.rapidminer.core.io.data.DataSetException;
import com.rapidminer.studio.io.data.DefaultColumnMetaData;
import com.rapidminer.tools.Observable;
import com.rapidminer.tools.Observer;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.*;


/**
 * Tests the {@link ConfigureDataValidator}.
 *
 * @author Gisa Schaefer
 */
public class ConfigureDataValidatorTest {

	private static List<ColumnMetaData> columnMetaData;
	private static ConfigureDataValidator validator;
	private static List<ParsingError> parsingErrors;

    /**
     * Sets up for all.
     */
    @BeforeClass
	public static void setUpForAll() {
		parsingErrors = new ArrayList<>(4);
		parsingErrors.add(new ParsingError(0, 2, "xXX0", ""));
		parsingErrors.add(new ParsingError(1, 1, "xXX1", ""));
		parsingErrors.add(new ParsingError(2, 5, "xXX2", ""));
		parsingErrors.add(new ParsingError(3, 0, "xXX3", ""));
	}

    /**
     * Sets up.
     */
    @Before
	public void setUp() {
		columnMetaData = new ArrayList<ColumnMetaData>(
				Arrays.asList(new ColumnMetaData[] { new DefaultColumnMetaData("att1", ColumnType.REAL),
						new DefaultColumnMetaData("att2", ColumnType.CATEGORICAL),
						new DefaultColumnMetaData("att3", ColumnType.DATE),
						new DefaultColumnMetaData("att4", ColumnType.INTEGER) }));
		validator = new ConfigureDataValidator();
		validator.init(columnMetaData);
	}

    /**
     * No errors.
     *
     * @throws DataSetException the data set exception
     */
    @Test
	public void noErrors() throws DataSetException {
		assertTrue(validator.getParsingErrors().isEmpty());
		assertTrue(validator.getColumnErrors().isEmpty());
		for (int i = 0; i < 4; i++) {
			assertFalse(validator.isDuplicateNameColumn(i));
			assertFalse(validator.isDuplicateRoleColumn(i));
		}
	}

    /**
     * All parsing errors.
     */
    @Test
	public void allParsingErrors() {
		validator.setParsingErrors(parsingErrors);
		assertEquals(4, validator.getParsingErrors().size());
	}

    /**
     * Parsing errors column removed.
     */
    @Test
	public void parsingErrorsColumnRemoved() {
		validator.setParsingErrors(parsingErrors);
		columnMetaData.get(1).setRemoved(true);
		assertEquals(3, validator.getParsingErrors().size());
		for (ParsingError error : validator.getParsingErrors()) {
			assertTrue(error.getColumn() != 1);
		}
	}

    /**
     * Column errors same name.
     */
    @Test
	public void columnErrorsSameName() {
		columnMetaData.get(1).setName("att1");
		final AtomicBoolean wasCalled = new AtomicBoolean();
		Observer<Set<Integer>> observer = new Observer<Set<Integer>>() {

			@Override
			public void update(Observable<Set<Integer>> observable, Set<Integer> arg) {
				assertTrue(arg.contains(1));
				assertTrue(arg.contains(0));
				wasCalled.set(true);
			}
		};
		validator.addObserver(observer, false);
		validator.validate(1);
		for (int i = 0; i < 4; i++) {
			assertEquals(i == 0 || i == 1, validator.isDuplicateNameColumn(i));
			assertFalse(validator.isDuplicateRoleColumn(i));
		}
		List<ColumnError> errors = validator.getColumnErrors();
		assertEquals(1, errors.size());
		List<Integer> intList = new ArrayList<>(2);
		intList.add(0);
		intList.add(1);
		assertEquals(intList, errors.get(0).getAffectedColumns());
		assertTrue(wasCalled.get());
	}

    /**
     * Not same name anymore update.
     */
    @Test
	public void notSameNameAnymoreUpdate() {
		columnMetaData.get(1).setName("att1");
		validator.validate(1);
		columnMetaData.get(1).setName("att2");
		final AtomicBoolean wasCalled = new AtomicBoolean();
		Observer<Set<Integer>> observer = new Observer<Set<Integer>>() {

			@Override
			public void update(Observable<Set<Integer>> observable, Set<Integer> arg) {
				assertTrue(arg.contains(1));
				assertTrue(arg.contains(0));
				wasCalled.set(true);
			}
		};
		validator.addObserver(observer, false);
		validator.validate(1);
		assertTrue(wasCalled.get());
	}

    /**
     * Column errors same name removed.
     */
    @Test
	public void columnErrorsSameNameRemoved() {
		columnMetaData.get(1).setName("att1");
		columnMetaData.get(1).setRemoved(true);
		validator.validate(1);
		for (int i = 0; i < 4; i++) {
			assertFalse(validator.isDuplicateNameColumn(i));
			assertFalse(validator.isDuplicateRoleColumn(i));
		}
		assertTrue(validator.getColumnErrors().isEmpty());
	}

    /**
     * Column errors same role.
     */
    @Test
	public void columnErrorsSameRole() {
		columnMetaData.get(0).setRole("label");
		columnMetaData.get(1).setRole("label");
		validator.validate(0);
		final AtomicBoolean wasCalled = new AtomicBoolean();
		Observer<Set<Integer>> observer = new Observer<Set<Integer>>() {

			@Override
			public void update(Observable<Set<Integer>> observable, Set<Integer> arg) {
				assertTrue(arg.contains(1));
				assertTrue(arg.contains(0));
				wasCalled.set(true);
			}
		};
		validator.addObserver(observer, false);
		validator.validate(1);
		for (int i = 0; i < 4; i++) {
			assertEquals(i == 0 || i == 1, validator.isDuplicateRoleColumn(i));
			assertFalse(validator.isDuplicateNameColumn(i));
		}
		List<ColumnError> errors = validator.getColumnErrors();
		assertEquals(1, errors.size());
		List<Integer> intList = new ArrayList<>(2);
		intList.add(0);
		intList.add(1);
		assertEquals(intList, errors.get(0).getAffectedColumns());
		assertTrue(wasCalled.get());
	}

    /**
     * Column errors same role removed.
     */
    @Test
	public void columnErrorsSameRoleRemoved() {
		columnMetaData.get(0).setRole("label");
		validator.validate(0);
		columnMetaData.get(1).setRole("label");
		columnMetaData.get(1).setRemoved(true);
		validator.validate(1);
		for (int i = 0; i < 4; i++) {
			assertFalse(validator.isDuplicateNameColumn(i));
			assertFalse(validator.isDuplicateRoleColumn(i));
		}
		assertTrue(validator.getColumnErrors().isEmpty());
	}

    /**
     * Not same role anymore update.
     */
    @Test
	public void notSameRoleAnymoreUpdate() {
		columnMetaData.get(0).setRole("label");
		columnMetaData.get(1).setRole("label");
		validator.validate(0);
		validator.validate(1);
		final AtomicBoolean wasCalled = new AtomicBoolean();
		Observer<Set<Integer>> observer = new Observer<Set<Integer>>() {

			@Override
			public void update(Observable<Set<Integer>> observable, Set<Integer> arg) {
				assertTrue(arg.contains(1));
				assertTrue(arg.contains(0));
				wasCalled.set(true);
			}
		};
		validator.addObserver(observer, false);
		columnMetaData.get(1).setRole("label2");
		validator.validate(1);
		assertTrue(wasCalled.get());
	}

    /**
     * No updated indices for parsing errors.
     */
    @Test
	public void noUpdatedIndicesForParsingErrors() {
		final AtomicBoolean wasCalled = new AtomicBoolean();
		Observer<Set<Integer>> observer = new Observer<Set<Integer>>() {

			@Override
			public void update(Observable<Set<Integer>> observable, Set<Integer> arg) {
				assertTrue(arg == null);
				wasCalled.set(true);
			}
		};
		validator.addObserver(observer, false);
		validator.setParsingErrors(parsingErrors);
		assertTrue(wasCalled.get());
	}

    /**
     * No update for not duplicated name change.
     */
    @Test
	public void noUpdateForNotDuplicatedNameChange() {
		final AtomicBoolean wasCalled = new AtomicBoolean();
		Observer<Set<Integer>> observer = new Observer<Set<Integer>>() {

			@Override
			public void update(Observable<Set<Integer>> observable, Set<Integer> arg) {
				wasCalled.set(true);
			}
		};
		validator.addObserver(observer, false);
		columnMetaData.get(1).setName("att");
		validator.validate(1);
		assertFalse(wasCalled.get());
	}

    /**
     * Updated indices for duplicated name change.
     */
    @Test
	public void updatedIndicesForDuplicatedNameChange() {
		final AtomicBoolean wasCalled = new AtomicBoolean();
		final Set<Integer> expected = new HashSet<>();
		expected.add(0);
		expected.add(1);
		Observer<Set<Integer>> observer = new Observer<Set<Integer>>() {

			@Override
			public void update(Observable<Set<Integer>> observable, Set<Integer> arg) {
				assertEquals(expected, arg);
				wasCalled.set(true);
			}
		};
		validator.addObserver(observer, false);
		columnMetaData.get(1).setName("att1");
		validator.validate(1);
		assertTrue(wasCalled.get());
	}

    /**
     * Change between different duplicates no update indices.
     */
    @Test
	public void changeBetweenDifferentDuplicatesNoUpdateIndices() {
		final AtomicBoolean wasCalled = new AtomicBoolean();
		columnMetaData.get(1).setName("att1");
		columnMetaData.get(2).setName("att1");
		columnMetaData.get(3).setName("att2");
		columnMetaData.add(new DefaultColumnMetaData("att2", ColumnType.BINARY));
		validator.init(columnMetaData);
		Observer<Set<Integer>> observer = new Observer<Set<Integer>>() {

			@Override
			public void update(Observable<Set<Integer>> observable, Set<Integer> arg) {
				assertTrue(arg == null);
				wasCalled.set(true);
			}
		};
		validator.addObserver(observer, false);
		columnMetaData.get(2).setName("att2");
		validator.validate(2);
		assertTrue(wasCalled.get());
	}

    /**
     * Change between different duplicates no update indices 2.
     */
    @Test
	public void changeBetweenDifferentDuplicatesNoUpdateIndices2() {
		final AtomicBoolean wasCalled = new AtomicBoolean();
		columnMetaData.get(1).setName("att1");
		columnMetaData.get(2).setName("att1");
		columnMetaData.get(3).setName("att2");
		columnMetaData.add(new DefaultColumnMetaData("att2", ColumnType.BINARY));
		validator.init(columnMetaData);
		Observer<Set<Integer>> observer = new Observer<Set<Integer>>() {

			@Override
			public void update(Observable<Set<Integer>> observable, Set<Integer> arg) {
				assertTrue(arg == null);
				wasCalled.set(true);
			}
		};
		validator.addObserver(observer, false);
		columnMetaData.get(0).setName("att2");
		validator.validate(0);
		assertTrue(wasCalled.get());
	}

    /**
     * Duplicates no change.
     */
    @Test
	public void duplicatesNoChange() {
		final AtomicBoolean wasCalled = new AtomicBoolean();
		columnMetaData.get(1).setName("att1");
		columnMetaData.get(2).setName("att1");
		columnMetaData.get(3).setName("att2");
		columnMetaData.add(new DefaultColumnMetaData("att2", ColumnType.BINARY));
		validator.init(columnMetaData);
		Observer<Set<Integer>> observer = new Observer<Set<Integer>>() {

			@Override
			public void update(Observable<Set<Integer>> observable, Set<Integer> arg) {
				wasCalled.set(true);
			}
		};
		validator.addObserver(observer, false);
		validator.validate(0);
		assertFalse(wasCalled.get());
	}

    /**
     * Parsing error affected column changed.
     */
    @Test
	public void parsingErrorAffectedColumnChanged() {
		validator.setParsingErrors(parsingErrors);
		final AtomicBoolean wasCalled = new AtomicBoolean();
		Observer<Set<Integer>> observer = new Observer<Set<Integer>>() {

			@Override
			public void update(Observable<Set<Integer>> observable, Set<Integer> arg) {
				assertTrue(arg == null);
				wasCalled.set(true);
			}
		};
		validator.addObserver(observer, false);
		columnMetaData.get(0).setName("att");
		validator.validate(0);
		assertTrue(wasCalled.get());
	}
}
