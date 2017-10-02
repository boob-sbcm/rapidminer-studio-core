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
package com.rapidminer.repository;

/**
 * Contains error codes for exceptions etc.
 *
 * @author Simon Fischer
 */
public interface RepositoryConstants {

    /**
     * The constant OK.
     */
    public static final int OK = 0;

    /**
     * The constant GENERAL_ERROR.
     */
    public static final int GENERAL_ERROR = -1;

    /**
     * The constant NO_SUCH_ENTRY.
     */
    public static final int NO_SUCH_ENTRY = -2;

    /**
     * The constant NO_SUCH_REVISION.
     */
    public static final int NO_SUCH_REVISION = -3;

    /**
     * The constant DUPLICATE_ENTRY.
     */
    public static final int DUPLICATE_ENTRY = -4;

    /**
     * The constant WRONG_TYPE.
     */
    public static final int WRONG_TYPE = -5;

    /**
     * The constant ILLEGAL_CHARACTER.
     */
    public static final int ILLEGAL_CHARACTER = -6;

    /**
     * The constant ILLEGAL_TYPE.
     */
    public static final int ILLEGAL_TYPE = -7;

    /**
     * The constant ILLEGAL_DATA_FORMAT.
     */
    public static final int ILLEGAL_DATA_FORMAT = -8;

    /**
     * The constant MALFORMED_CRON_EXPRESSION.
     */
    public static final int MALFORMED_CRON_EXPRESSION = -9;

    /**
     * The constant NO_SUCH_TRIGGER.
     */
    public static final int NO_SUCH_TRIGGER = -10;

    /**
     * The constant MALFORMED_PROCESS.
     */
    public static final int MALFORMED_PROCESS = -11;

    /**
     * The constant ACCESS_DENIED.
     */
    public static final int ACCESS_DENIED = -12;

    /**
     * The constant NO_SUCH_GROUP.
     */
    public static final int NO_SUCH_GROUP = -13;

    /**
     * The constant DUPLICATE_USERNAME.
     */
    public static final int DUPLICATE_USERNAME = -14;

    /**
     * The constant DUPLICATE_GROUPNAME.
     */
    public static final int DUPLICATE_GROUPNAME = -15;

    /**
     * The constant NO_SUCH_USER_IN_GROUP.
     */
    public static final int NO_SUCH_USER_IN_GROUP = -16;

    /**
     * The constant FORBIDDEN.
     */
    public static final int FORBIDDEN = -17;

    /**
     * The constant NO_SUCH_PROCESS.
     */
    public static final int NO_SUCH_PROCESS = -18;

    /**
     * The constant MISSING_PARAMETER.
     */
    public static final int MISSING_PARAMETER = -19;

    /**
     * The constant VERSION_OUT_OF_DATE.
     */
    public static final int VERSION_OUT_OF_DATE = -20;

    /**
     * The constant NO_SUCH_USER.
     */
    public static final int NO_SUCH_USER = -21;

    /**
     * The constant PROCESS_FAILED.
     */
    public static final int PROCESS_FAILED = -22;

    /**
     * The constant NO_SUCH_QUEUE.
     */
    public static final int NO_SUCH_QUEUE = -23;

}
