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
package com.rapidminer.operator.nio.model;

import java.util.List;


/**
 * An error that occurred during parsing.
 *
 * @author Simon Fischer
 */
public class ParsingError {

    /**
     * The enum Error code.
     */
    public static enum ErrorCode {
        /**
         * The Unparseable date.
         */
        UNPARSEABLE_DATE("unparseable date"), /**
         * The Unparseable integer.
         */
        UNPARSEABLE_INTEGER("unparseable integer"), /**
         * The Unparseable real.
         */
        UNPARSEABLE_REAL(
				"unparseable real number"), /**
         * The More than two values.
         */
        MORE_THAN_TWO_VALUES("more than two values for binominal attribute"), /**
         * The Row too long.
         */
        ROW_TOO_LONG(
				"row too long"), /**
         * The File syntax error.
         */
        FILE_SYNTAX_ERROR("file syntax error"), /**
         * The Same role for multiple columns.
         */
        SAME_ROLE_FOR_MULTIPLE_COLUMNS("duplicate role"), /**
         * The Same name for multiple columns.
         */
        SAME_NAME_FOR_MULTIPLE_COLUMNS(
				"duplicate attribute name");

		private final String message;

		private ErrorCode(String message) {
			this.message = message;
		}

        /**
         * Gets message.
         *
         * @return the message
         */
        public String getMessage() {
			return message;
		}
	}

	/** The row number in which this error occurred. */
	private final int row;

	/**
	 * The example to which this {@link #row} is mapped. E.g., if rows are used as annotations,
	 * example index and row do not match.
	 */
	private int exampleIndex;

	/** The column (cell index) in which this error occurred. */
	private final int column;

	private List<Integer> columns = null;

	/** The original value that was unparseable. Most of the time, this will be a string. */
	private final Object originalValue;

	private final ErrorCode errorCode;

	private final Throwable cause;

    /**
     * Instantiates a new Parsing error.
     *
     * @param columns       the columns
     * @param errorCode     the error code
     * @param originalValue the original value
     */
    public ParsingError(List<Integer> columns, ErrorCode errorCode, Object originalValue) {
		this(-1, -1, errorCode, originalValue, null);
		this.columns = columns;
	}

    /**
     * Instantiates a new Parsing error.
     *
     * @param row           the row
     * @param column        the column
     * @param errorCode     the error code
     * @param originalValue the original value
     */
    public ParsingError(int row, int column, ErrorCode errorCode, Object originalValue) {
		this(row, column, errorCode, originalValue, null);
	}

    /**
     * Instantiates a new Parsing error.
     *
     * @param row           the row
     * @param column        the column
     * @param errorCode     the error code
     * @param originalValue the original value
     * @param cause         the cause
     */
    public ParsingError(int row, int column, ErrorCode errorCode, Object originalValue, Throwable cause) {
		super();
		this.row = row;
		this.column = column;
		this.originalValue = originalValue;
		this.errorCode = errorCode;
		this.setExampleIndex(row);
		this.cause = cause;
	}

    /**
     * Gets row.
     *
     * @return the row
     */
    public int getRow() {
		return row;
	}

    /**
     * Gets column.
     *
     * @return the column
     */
    public int getColumn() {
		return column;
	}

    /**
     * Gets columns.
     *
     * @return the columns
     */
    public List<Integer> getColumns() {
		return columns;
	}

    /**
     * Gets original value.
     *
     * @return the original value
     */
    public Object getOriginalValue() {
		return originalValue;
	}

    /**
     * Gets error code.
     *
     * @return the error code
     */
    public ErrorCode getErrorCode() {
		return errorCode;
	}

    /**
     * Sets example index.
     *
     * @param exampleIndex the example index
     */
    public void setExampleIndex(int exampleIndex) {
		this.exampleIndex = exampleIndex;
	}

    /**
     * Gets example index.
     *
     * @return the example index
     */
    public int getExampleIndex() {
		return exampleIndex;
	}

    /**
     * Gets cause.
     *
     * @return the cause
     */
    public Throwable getCause() {
		return cause;
	}

    /**
     * Gets message.
     *
     * @return the error message without location reference
     */
    public String getMessage() {
		return getErrorCode().getMessage() + ": \"" + getOriginalValue() + "\"";
	}

	@Override
	public String toString() {
		return "line " + getRow() + ", column " + getColumn() + ": " + getErrorCode().getMessage() + "(" + getOriginalValue()
				+ ")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result = prime * result + ((columns == null) ? 0 : columns.hashCode());
		result = prime * result + ((errorCode == null) ? 0 : errorCode.hashCode());
		result = prime * result + exampleIndex;
		result = prime * result + ((originalValue == null) ? 0 : originalValue.hashCode());
		result = prime * result + row;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof ParsingError)) {
			return false;
		}
		ParsingError other = (ParsingError) obj;
		if (column != other.column) {
			return false;
		}
		if (columns == null) {
			if (other.columns != null) {
				return false;
			}
		} else if (!columns.equals(other.columns)) {
			return false;
		}
		if (errorCode != other.errorCode) {
			return false;
		}
		if (exampleIndex != other.exampleIndex) {
			return false;
		}
		if (originalValue == null) {
			if (other.originalValue != null) {
				return false;
			}
		} else if (!originalValue.equals(other.originalValue)) {
			return false;
		}
		if (row != other.row) {
			return false;
		}
		return true;
	}
}
