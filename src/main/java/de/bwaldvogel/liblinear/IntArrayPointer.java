/**
 * Copyright (c) 2007-2014 The LIBLINEAR Project. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted
 * provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions
 * and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of
 * conditions and the following disclaimer in the documentation and/or other materials provided with
 * the distribution.
 *
 * 3. Neither name of copyright holders nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE REGENTS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF
 * THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package de.bwaldvogel.liblinear;

/**
 * The type Int array pointer.
 */
final class IntArrayPointer {

	private final int[] _array;
	private int _offset;

    /**
     * Sets offset.
     *
     * @param offset the offset
     */
    public void setOffset(int offset) {
		if (offset < 0 || offset >= _array.length) {
			throw new IllegalArgumentException("offset must be between 0 and the length of the array");
		}
		_offset = offset;
	}

    /**
     * Instantiates a new Int array pointer.
     *
     * @param array  the array
     * @param offset the offset
     */
    public IntArrayPointer(final int[] array, final int offset) {
		_array = array;
		setOffset(offset);
	}

    /**
     * Get int.
     *
     * @param index the index
     * @return the int
     */
    public int get(final int index) {
		return _array[_offset + index];
	}

    /**
     * Set.
     *
     * @param index the index
     * @param value the value
     */
    public void set(final int index, final int value) {
		_array[_offset + index] = value;
	}
}
