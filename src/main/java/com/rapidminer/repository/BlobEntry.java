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

import java.io.InputStream;
import java.io.OutputStream;


/**
 * A byte blob with no specified contents.
 *
 * @author Simon Fischer
 */
public interface BlobEntry extends DataEntry {

    /**
     * The constant TYPE_NAME.
     */
    public static final String TYPE_NAME = "blob";

    /**
     * Opens a stream to read from this entry.
     *
     * @return the input stream
     * @throws RepositoryException the repository exception
     */
    public InputStream openInputStream() throws RepositoryException;

    /**
     * Opens a stream to this blob, setting its mime type to the given value.
     *
     * @param mimeType the mime type
     * @return TODO output stream
     * @throws RepositoryException the repository exception
     */
    public OutputStream openOutputStream(String mimeType) throws RepositoryException;

    /**
     * Gets mime type.
     *
     * @return the mime type
     */
    public String getMimeType();

}
