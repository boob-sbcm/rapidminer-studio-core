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
package com.rapidminer.tools;

import com.rapidminer.parameter.ParameterHandler;
import com.rapidminer.parameter.ParameterType;
import com.rapidminer.parameter.ParameterTypeString;
import com.rapidminer.parameter.UndefinedParameterError;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;


/**
 * A parser class for dates to be utilized inside date handling operators.
 *
 * @author Sebastian Land
 */
public class DateParser extends SimpleDateFormat {

	private static final long serialVersionUID = -950183600865410299L;

    /**
     * The constant PARAMETER_DATE_FORMAT.
     */
    public static final String PARAMETER_DATE_FORMAT = "date_format";

    /**
     * The constant DEFAULT_DATE_FORMAT.
     */
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    /**
     * The constant DEFAULT_DATE_TIME_FORMAT.
     */
    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd hh:mm:ss";

    /**
     * Instantiates a new Date parser.
     *
     * @param dateFormat the date format
     */
    public DateParser(String dateFormat) {
		super(dateFormat);
	}

    /**
     * Gets instance.
     *
     * @param handler the handler
     * @return the instance
     * @throws UndefinedParameterError the undefined parameter error
     */
    public static DateParser getInstance(ParameterHandler handler) throws UndefinedParameterError {
		String dateFormat = handler.getParameterAsString(PARAMETER_DATE_FORMAT);
		return new DateParser(dateFormat);
	}

    /**
     * Gets parameter types.
     *
     * @param handler the handler
     * @return the parameter types
     */
// TODO add ParameterTypeDate and corresponding ValueCellEditor
	// TODO integrate this parser into Nominal2Date, etc
	public static List<ParameterType> getParameterTypes(ParameterHandler handler) {
		List<ParameterType> types = new LinkedList<ParameterType>();
		types.add(new ParameterTypeString(PARAMETER_DATE_FORMAT, "The format pattern of date values.", DEFAULT_DATE_FORMAT));
		return types;
	}
}
