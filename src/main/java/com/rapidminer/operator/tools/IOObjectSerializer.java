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
package com.rapidminer.operator.tools;

import com.rapidminer.example.ExampleSet;

import java.io.*;
import java.util.Arrays;


/**
 * The type Io object serializer.
 *
 * @author Simon Fischer
 */
public class IOObjectSerializer {

    /**
     * The constant MAGIC_NUMBER.
     */
    public static final byte[] MAGIC_NUMBER = { (byte) 0x2A, (byte) 0x71, (byte) 0xD1 };

	private static final IOObjectSerializer INSTANCE = new IOObjectSerializer();

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static IOObjectSerializer getInstance() {
		return INSTANCE;
	}

    /**
     * Serializes the object with a default type appropriate for the given object.  @param out the out
     *
     * @param out    the out
     * @param object the object
     * @throws IOException the io exception
     */
    public void serialize(OutputStream out, Object object) throws IOException {
		SerializationType type;
		if (object instanceof ExampleSet) {
			type = SerializationType.STREAMED_EXAMPLE_SET_DENSE_CURRENT_VERSION;
		} else {
			type = SerializationType.JAVA_BINARY;
		}
		serialize(out, object, type);
	}

    /**
     * Write header.
     *
     * @param out               the out
     * @param serializationType the serialization type
     * @throws IOException the io exception
     */
    public void writeHeader(OutputStream out, SerializationType serializationType) throws IOException {
		out.write(MAGIC_NUMBER);
		out.flush();
		DataOutputStream dout = new DataOutputStream(out);
		dout.writeInt(serializationType.ordinal());
		dout.flush();
	}

    /**
     * Serializes the object to the stream, using the given serialization type.  @param out the out
     *
     * @param out               the out
     * @param object            the object
     * @param serializationType the serialization type
     * @throws IOException the io exception
     */
    public void serialize(OutputStream out, Object object, SerializationType serializationType) throws IOException {
		writeHeader(out, serializationType);
		serializationType.getBodySerializer().serialize(object, out);
		out.flush();
	}

    /**
     * Deserialize header serialization type.
     *
     * @param in the in
     * @return the serialization type
     * @throws IOException the io exception
     */
    public SerializationType deserializeHeader(InputStream in) throws IOException {
		byte[] magicRead = new byte[MAGIC_NUMBER.length];
		int offset = 0;
		int length;
		do {
			length = in.read(magicRead, offset, MAGIC_NUMBER.length - offset);
			if (length == -1) {
				throw new IOException("EOF while reading magic number.");
			}
			offset += length;
		} while (offset < MAGIC_NUMBER.length);
		if (!Arrays.equals(magicRead, MAGIC_NUMBER)) {
			throw new IOException("No magic number found. Make sure you are using RapidMiner 5.0 compatible files.");
		}
		DataInputStream din = new DataInputStream(in);
		int typeIndex = din.readInt();
		if ((typeIndex < 0) || (typeIndex > SerializationType.values().length)) {
			throw new IOException("Illegal serialization type: " + typeIndex);
		}
		return SerializationType.values()[typeIndex];
	}

    /**
     * Deserializes an object serialized by
     * {@link #serialize(OutputStream, Object, SerializationType)}.
     *
     * @param in the in
     * @return the object
     * @throws IOException the io exception
     */
    public Object deserialize(InputStream in) throws IOException {
		SerializationType type = deserializeHeader(in);
		return type.getBodySerializer().deserialize(in);
	}

    /**
     * Serializes the object into a byte buffer.  @param o the o
     *
     * @param o the o
     * @return the byte [ ]
     * @throws IOException the io exception
     */
    public byte[] serializeToBuffer(Object o) throws IOException {
		ByteArrayOutputStream bufOut = new ByteArrayOutputStream();
		try {
			serialize(bufOut, o);
			bufOut.flush();
			return bufOut.toByteArray();
		} finally {
			bufOut.close();
		}
	}

    /**
     * Deserializes the object from a byte buffer created by {@link #serializeToBuffer(Object)}.  @param buffer the buffer
     *
     * @param buffer the buffer
     * @return the object
     * @throws IOException            the io exception
     * @throws ClassNotFoundException the class not found exception
     */
    public Object deserializeFromBuffer(byte[] buffer) throws IOException, ClassNotFoundException {
		ByteArrayInputStream bufIn = new ByteArrayInputStream(buffer);
		try {
			return deserialize(bufIn);
		} finally {
			bufIn.close();
		}
	}
}
