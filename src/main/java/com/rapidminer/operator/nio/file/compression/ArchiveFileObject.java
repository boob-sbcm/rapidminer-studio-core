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
package com.rapidminer.operator.nio.file.compression;

import com.rapidminer.operator.OperatorException;
import com.rapidminer.operator.nio.file.FileObject;
import com.rapidminer.tools.Tools;

import java.io.*;
import java.util.Set;


/**
 * The type Archive file object.
 *
 * @author Marius Helf
 */
public abstract class ArchiveFileObject extends FileObject {

    /**
     * The enum Fuzzy compression level.
     */
    public enum FuzzyCompressionLevel {
        /**
         * use compression level as configured in the archive file object
         */
        DEFAULT,
        /**
         * use highest compression level
         */
        BEST,
        /**
         * compress as fast as possible
         */
        FASTEST,
        /**
         * don't compress at all
         */
        NONE
	}

    /**
     * The enum Buffer type.
     */
    public enum BufferType {
        /**
         * Memory buffer type.
         */
        MEMORY, /**
         * File buffer type.
         */
        FILE
	};

	private static final long serialVersionUID = 1L;
	private static final String TEMP_FILE_PREFIX = "rm_archivefile_";
	private static final String TEMP_FILE_SUFFIX = ".dump";

	/**
	 * The data stream to which the archived data is written.
	 */
	private OutputStream archiveDataStream;

	private final BufferType bufferType;
	private File tmpFile;
	private FuzzyCompressionLevel compressionLevel = FuzzyCompressionLevel.DEFAULT;

    /**
     * Instantiates a new Archive file object.
     *
     * @throws OperatorException the operator exception
     */
    public ArchiveFileObject() throws OperatorException {
		bufferType = BufferType.FILE;
		init();
	}

    /**
     * Instantiates a new Archive file object.
     *
     * @param bufferType the buffer type
     * @throws OperatorException the operator exception
     */
    public ArchiveFileObject(BufferType bufferType) throws OperatorException {
		super();
		this.bufferType = bufferType;
		init();
	}

	private void init() throws OperatorException {
		switch (bufferType) {
			case FILE:
				try {
					tmpFile = File.createTempFile("rm_archivefile_", ".dump");
					tmpFile.deleteOnExit();
					archiveDataStream = new FileOutputStream(tmpFile);
				} catch (IOException e) {
					throw new OperatorException("303", e, tmpFile, e.getMessage());
				}
				break;
			case MEMORY:
				archiveDataStream = new ByteArrayOutputStream();
				break;
			default:
				throw new RuntimeException("Unknown buffer type: " + bufferType);
		}
	}

    /**
     * Gets archive data stream.
     *
     * @return the archive data stream
     */
    public OutputStream getArchiveDataStream() {
		return archiveDataStream;
	}

    /**
     * Flush.
     *
     * @throws IOException the io exception
     */
    protected void flush() throws IOException {
		archiveDataStream.flush();
	}

	@Override
	public InputStream openStream() throws OperatorException {

		try {
			flush();
		} catch (IOException e) {
			throw new OperatorException("archive_file.stream_error", e, new Object[0]);
		}

		switch (bufferType) {
			case FILE:
				try {
					return new FileInputStream(tmpFile);
				} catch (FileNotFoundException e) {
					throw new OperatorException("301", e, tmpFile);
				}
			case MEMORY:
				return new ByteArrayInputStream(((ByteArrayOutputStream) archiveDataStream).toByteArray());
			default:
				throw new RuntimeException("bufferType should never be null");
		}
	}

	@Override
	public File getFile() throws OperatorException {
		if (tmpFile == null) {
			try {
				tmpFile = File.createTempFile(TEMP_FILE_PREFIX, TEMP_FILE_SUFFIX);
				tmpFile.deleteOnExit();
			} catch (IOException e) {
				throw new OperatorException("303", e, "File in " + System.getProperty("java.io.tmpdir"), e.getMessage());
			}
			try (FileOutputStream fos = new FileOutputStream(tmpFile); InputStream in = openStream();) {
				Tools.copyStreamSynchronously(in, fos, true);
			} catch (IOException e) {
				throw new OperatorException("303", e, tmpFile, e.getMessage());
			}
		} else {
			try {
				flush();
			} catch (IOException e) {
				throw new OperatorException("303", e, tmpFile, e.getMessage());
			}
		}
		return tmpFile;
	}

    /**
     * Add entry.
     *
     * @param fileObject the file object
     * @param directory  the directory
     * @throws OperatorException the operator exception
     */
    public void addEntry(FileObject fileObject, String directory) throws OperatorException {
		addEntry(fileObject, directory, getCompressionLevel());
	}

    /**
     * Add entry.
     *
     * @param fileObject            the file object
     * @param directory             the directory
     * @param localCompressionLevel the local compression level
     * @throws OperatorException the operator exception
     */
    public abstract void addEntry(FileObject fileObject, String directory, FuzzyCompressionLevel localCompressionLevel)
			throws OperatorException;

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		switch (bufferType) {
			case FILE:
				builder.append("File");
				break;
			case MEMORY:
				builder.append("Memory");
				break;
			default:
				throw new RuntimeException("Unknown buffer type: " + bufferType);
		}
		builder.append(" buffered ");
		builder.append(getName());

		return builder.toString();
	}

	@Override
	protected void finalize() throws Throwable {
		if (tmpFile != null) {
			tmpFile.delete();
		}
		super.finalize();
	}

    /**
     * Gets compression level.
     *
     * @return the compression level
     */
    public FuzzyCompressionLevel getCompressionLevel() {
		return compressionLevel;
	}

    /**
     * Sets compression level.
     *
     * @param compressionLevel the compression level
     */
    public void setCompressionLevel(FuzzyCompressionLevel compressionLevel) {
		// TODO throw exception if compressionLevel is not supported
		this.compressionLevel = compressionLevel;
	}

    /**
     * Gets supported compression levels.
     *
     * @return the supported compression levels
     */
    public abstract Set<FuzzyCompressionLevel> getSupportedCompressionLevels();
}
