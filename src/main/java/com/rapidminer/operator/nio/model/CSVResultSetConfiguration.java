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

import com.rapidminer.operator.Operator;
import com.rapidminer.operator.OperatorException;
import com.rapidminer.operator.nio.CSVExampleSource;
import com.rapidminer.operator.ports.metadata.ExampleSetMetaData;
import com.rapidminer.tools.ProgressListener;
import com.rapidminer.tools.io.Encoding;

import javax.swing.table.TableModel;
import java.io.File;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A class holding information about syntactical configuration for parsing CSV files
 *
 * @author Simon Fischer
 */
public class CSVResultSetConfiguration implements DataResultSetFactory {

	private String csvFile;

	private boolean skipComments = true;
	private boolean useQuotes = true;
	private boolean skipUTF8BOM = false;
	private boolean trimLines = false;
	private boolean hasHeaderRow = true;

	private String columnSeparators = ";";

	private char quoteCharacter = '"';
	private char escapeCharacter = '\\';
	private char decimalCharacter = '.';
	private String commentCharacters = "#";
	private int startingRow = 0;
	private int headerRow = 0;

	private Charset encoding = Charset.defaultCharset();

	private List<ParsingError> errors;

    /**
     * This will create a completely empty result set configuration
     */
    public CSVResultSetConfiguration() {}

    /**
     * This constructor reads all settings from the parameters of the given operator.
     *
     * @param csvExampleSource the csv example source
     * @throws OperatorException the operator exception
     */
    public CSVResultSetConfiguration(CSVExampleSource csvExampleSource) throws OperatorException {
		// if (csvExampleSource.isParameterSet(CSVExampleSource.PARAMETER_CSV_FILE)) {
		// setCsvFile(csvExampleSource.getParameterAsString(CSVExampleSource.PARAMETER_CSV_FILE));
		// }
		if (csvExampleSource.isFileSpecified()) {
			setCsvFile(csvExampleSource.getSelectedFile().getAbsolutePath());
		}
		setSkipComments(csvExampleSource.getParameterAsBoolean(CSVExampleSource.PARAMETER_SKIP_COMMENTS));
		setUseQuotes(csvExampleSource.getParameterAsBoolean(CSVExampleSource.PARAMETER_USE_QUOTES));
		// setFirstRowAsAttributeNames(csvExampleSource.getParameterAsBoolean(CSVExampleSource.PARAMETER_USE_FIRST_ROW_AS_ATTRIBUTE_NAMES));
		setTrimLines(csvExampleSource.getParameterAsBoolean(CSVExampleSource.PARAMETER_TRIM_LINES));
		if (csvExampleSource.isParameterSet(CSVExampleSource.PARAMETER_COLUMN_SEPARATORS)) {
			setColumnSeparators(csvExampleSource.getParameterAsString(CSVExampleSource.PARAMETER_COLUMN_SEPARATORS));
		}
		if (csvExampleSource.isParameterSet(CSVExampleSource.PARAMETER_ESCAPE_CHARACTER)) {
			setEscapeCharacter(csvExampleSource.getParameterAsChar(CSVExampleSource.PARAMETER_ESCAPE_CHARACTER));
		}
		if (csvExampleSource.isParameterSet(CSVExampleSource.PARAMETER_COMMENT_CHARS)) {
			setCommentCharacters(csvExampleSource.getParameterAsString(CSVExampleSource.PARAMETER_COMMENT_CHARS));
		}
		if (csvExampleSource.isParameterSet(CSVExampleSource.PARAMETER_QUOTES_CHARACTER)) {
			setQuoteCharacter(csvExampleSource.getParameterAsChar(CSVExampleSource.PARAMETER_QUOTES_CHARACTER));
		}
		encoding = Encoding.getEncoding(csvExampleSource);
	}

	@Override
	public void setParameters(AbstractDataResultSetReader source) {
		source.setParameter(CSVExampleSource.PARAMETER_CSV_FILE, getCsvFile());
		source.setParameter(CSVExampleSource.PARAMETER_SKIP_COMMENTS, String.valueOf(isSkipComments()));
		source.setParameter(CSVExampleSource.PARAMETER_USE_QUOTES, String.valueOf(isUseQuotes()));
		// source.setParameter(CSVExampleSource.PARAMETER_USE_FIRST_ROW_AS_ATTRIBUTE_NAMES,
		// String.valueOf(isFirstRowAsAttributeNames()));
		source.setParameter(CSVExampleSource.PARAMETER_COLUMN_SEPARATORS, getColumnSeparators());
		source.setParameter(CSVExampleSource.PARAMETER_TRIM_LINES, String.valueOf(isTrimLines()));
		source.setParameter(CSVExampleSource.PARAMETER_QUOTES_CHARACTER, String.valueOf(getQuoteCharacter()));
		source.setParameter(CSVExampleSource.PARAMETER_ESCAPE_CHARACTER, String.valueOf(getEscapeCharacter()));
		source.setParameter(CSVExampleSource.PARAMETER_COMMENT_CHARS, getCommentCharacters());

		source.setParameter(Encoding.PARAMETER_ENCODING, encoding.name());
	}

	@Override
	public DataResultSet makeDataResultSet(Operator operator) throws OperatorException {
		return new CSVResultSet(this, operator);
	}

	@Override
	public TableModel makePreviewTableModel(ProgressListener listener) throws OperatorException, ParseException {
		final DataResultSet resultSet = makeDataResultSet(null);
		DefaultPreview preview = null;
		try {
			this.errors = ((CSVResultSet) resultSet).getErrors();
			preview = new DefaultPreview(resultSet, listener);
		} finally {
			resultSet.close();
		}
		return preview;
	}

    /**
     * Sets csv file.
     *
     * @param csvFile the csv file
     */
    public void setCsvFile(String csvFile) {
		this.csvFile = csvFile;
	}

    /**
     * Gets csv file.
     *
     * @return the csv file
     */
    public String getCsvFile() {
		return csvFile;
	}

    /**
     * Gets csv file as file.
     *
     * @return the csv file as file
     */
    public File getCsvFileAsFile() {
		return csvFile == null ? null : new File(csvFile);
	}

    /**
     * Sets use quotes.
     *
     * @param useQuotes the use quotes
     */
    public void setUseQuotes(boolean useQuotes) {
		this.useQuotes = useQuotes;
	}

    /**
     * Is use quotes boolean.
     *
     * @return the boolean
     */
    public boolean isUseQuotes() {
		return useQuotes;
	}

    /**
     * Has header row boolean.
     *
     * @return the boolean
     */
    public boolean hasHeaderRow() {
		return hasHeaderRow;
	}

    /**
     * Sets has header row.
     *
     * @param hasHeaderRow the has header row
     */
    public void setHasHeaderRow(boolean hasHeaderRow) {
		this.hasHeaderRow = hasHeaderRow;
	}

    /**
     * Sets skip comments.
     *
     * @param skipComments the skip comments
     */
    public void setSkipComments(boolean skipComments) {
		this.skipComments = skipComments;
	}

    /**
     * Is skip comments boolean.
     *
     * @return the boolean
     */
    public boolean isSkipComments() {
		return skipComments;
	}

    /**
     * Sets column separators.
     *
     * @param columnSeparators the column separators
     */
    public void setColumnSeparators(String columnSeparators) {
		this.columnSeparators = columnSeparators;
	}

    /**
     * Gets column separators.
     *
     * @return the column separators
     */
    public String getColumnSeparators() {
		return columnSeparators;
	}

    /**
     * Sets comment characters.
     *
     * @param commentCharacters the comment characters
     */
    public void setCommentCharacters(String commentCharacters) {
		this.commentCharacters = commentCharacters;
	}

    /**
     * Gets comment characters.
     *
     * @return the comment characters
     */
    public String getCommentCharacters() {
		return commentCharacters;
	}

    /**
     * Sets escape character.
     *
     * @param escapeCharacter the escape character
     */
    public void setEscapeCharacter(char escapeCharacter) {
		this.escapeCharacter = escapeCharacter;
	}

    /**
     * Gets escape character.
     *
     * @return the escape character
     */
    public char getEscapeCharacter() {
		return escapeCharacter;
	}

    /**
     * Sets quote character.
     *
     * @param quoteCharacter the quote character
     */
    public void setQuoteCharacter(char quoteCharacter) {
		this.quoteCharacter = quoteCharacter;
	}

    /**
     * Gets quote character.
     *
     * @return the quote character
     */
    public char getQuoteCharacter() {
		return quoteCharacter;
	}

    /**
     * Sets decimal character.
     *
     * @param decimalCharacter the decimal character
     */
    public void setDecimalCharacter(char decimalCharacter) {
		this.decimalCharacter = decimalCharacter;
	}

    /**
     * Gets decimal character.
     *
     * @return the decimal character
     */
    public char getDecimalCharacter() {
		return decimalCharacter;
	}

    /**
     * Sets trim lines.
     *
     * @param trimLines the trim lines
     */
    public void setTrimLines(boolean trimLines) {
		this.trimLines = trimLines;
	}

    /**
     * Is trim lines boolean.
     *
     * @return the boolean
     */
    public boolean isTrimLines() {
		return trimLines;
	}

    /**
     * Sets encoding.
     *
     * @param encoding the encoding
     */
    public void setEncoding(Charset encoding) {
		this.encoding = encoding;
	}

    /**
     * Gets encoding.
     *
     * @return the encoding
     */
    public Charset getEncoding() {
		return encoding;
	}

    /**
     * Is skipping utf 8 bom boolean.
     *
     * @return the boolean
     */
    public boolean isSkippingUTF8BOM() {
		return skipUTF8BOM;
	}

    /**
     * Sets skip utf 8 bom.
     *
     * @param skipUTF8BOM the skip utf 8 bom
     */
    public void setSkipUTF8BOM(boolean skipUTF8BOM) {
		this.skipUTF8BOM = skipUTF8BOM;
	}

    /**
     * Gets starting row.
     *
     * @return the starting row
     */
    public int getStartingRow() {
		return startingRow;
	}

    /**
     * Sets starting row.
     *
     * @param startingRow the starting row
     */
    public void setStartingRow(int startingRow) {
		this.startingRow = startingRow;
	}

    /**
     * Gets header row.
     *
     * @return the header row
     */
    public int getHeaderRow() {
		return headerRow;
	}

    /**
     * Sets header row.
     *
     * @param headerRow the header row
     */
    public void setHeaderRow(int headerRow) {
		this.headerRow = headerRow;
	}

	@Override
	public String getResourceName() {
		return getCsvFile();
	}

	@Override
	public ExampleSetMetaData makeMetaData() {
		return new ExampleSetMetaData();
	}

    /**
     * Gets errors.
     *
     * @return the errors
     */
    public List<ParsingError> getErrors() {
		return errors;
	}

	@Override
	public void close() {}

    /**
     * Gets parameter map.
     *
     * @return a map containing all fieldNames and their values
     */
    public Map<String, String> getParameterMap() {
		Map<String, String> parameterMap = new HashMap<>();
		parameterMap.put("csvFile", getCsvFile());
		parameterMap.put("useQuotes", String.valueOf(isUseQuotes()));
		parameterMap.put("hasHeaderRow", String.valueOf(hasHeaderRow()));
		parameterMap.put("headerRow", String.valueOf(getHeaderRow()));
		parameterMap.put("decimalCharacter", String.valueOf(getDecimalCharacter()));
		parameterMap.put("startingRow", String.valueOf(getStartingRow()));
		parameterMap.put("skipComments", String.valueOf(isSkipComments()));
		parameterMap.put("columnSeparators", getColumnSeparators());
		parameterMap.put("commentCharacters", getCommentCharacters());
		parameterMap.put("escapeCharacter", String.valueOf(getEscapeCharacter()));
		parameterMap.put("quoteCharacter", String.valueOf(getQuoteCharacter()));
		parameterMap.put("trimLines", String.valueOf(isTrimLines()));
		parameterMap.put("encoding", String.valueOf(getEncoding()));
		parameterMap.put("skipUTF8BOM", String.valueOf(isSkippingUTF8BOM()));
		return parameterMap;
	}
}
