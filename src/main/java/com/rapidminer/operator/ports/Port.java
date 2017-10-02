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
package com.rapidminer.operator.ports;

import com.rapidminer.operator.*;
import com.rapidminer.operator.ports.metadata.MetaData;
import com.rapidminer.operator.ports.metadata.MetaDataError;
import com.rapidminer.operator.ports.quickfix.QuickFix;
import com.rapidminer.tools.Observable;

import java.util.Collection;
import java.util.List;


/**
 * Operators in a process are connected via input and output ports. Whenever an operator generates
 * output (or meta data about output), it is delivered to the connected input port. <br/>
 * This interface defines all behavior and properies common to input and output ports. This is
 * basically names, description etc., as well as adding messages about problems in the process setup
 * and quick fixes.
 *
 * @author Simon Fischer
 * @see Ports
 */
public interface Port extends Observable<Port> {

    /**
     * The constant CLEAR_META_DATA_ERRORS.
     */
    public static final int CLEAR_META_DATA_ERRORS = 1 << 0;
    /**
     * The constant CLEAR_METADATA.
     */
    public static final int CLEAR_METADATA = 1 << 1;
    /**
     * The constant CLEAR_DATA.
     */
    public static final int CLEAR_DATA = 1 << 2;
    /**
     * The constant CLEAR_SIMPLE_ERRORS.
     */
    public static final int CLEAR_SIMPLE_ERRORS = 1 << 3;
    /**
     * The constant CLEAR_REAL_METADATA.
     */
    public static final int CLEAR_REAL_METADATA = 1 << 4;
    /**
     * The constant CLEAR_ALL.
     */
    public static final int CLEAR_ALL = CLEAR_META_DATA_ERRORS | CLEAR_METADATA | CLEAR_DATA | CLEAR_SIMPLE_ERRORS
			| CLEAR_REAL_METADATA;

    /**
     * Clears all error types.
     */
    public static final int CLEAR_ALL_ERRORS = CLEAR_META_DATA_ERRORS | CLEAR_SIMPLE_ERRORS;

    /**
     * Clears all meta data, real and inferred.
     */
    public static final int CLEAR_ALL_METADATA = CLEAR_METADATA | CLEAR_REAL_METADATA;

    /**
     * A human readable, unique (operator scope) name for the port.  @return the name
     *
     * @return the name
     */
    public String getName();

    /**
     * Returns the meta data currently assigned to this port.
     *
     * @return the meta data
     * @deprecated use {@link #getMetaData(Class)} instead
     */
    @Deprecated
	public MetaData getMetaData();

    /**
     * Returns the meta data of the desired class or throws an UserError if available meta data
     * cannot be cast to the desired class. If no meta data is present at all, <code>null</code> is
     * returned.
     *
     * @param <T>          the type parameter
     * @param desiredClass the desired class
     * @return the meta data
     * @throws IncompatibleMDClassException the incompatible md class exception
     */
    public <T extends MetaData> T getMetaData(Class<T> desiredClass) throws IncompatibleMDClassException;

    /**
     * This method returns the object of the desired class or throws an UserError if no object is
     * present or cannot be casted to the desiredClass. * @throws UserError if data is missing or of
     * wrong class.
     *
     * @param <T>          the type parameter
     * @param desiredClass the desired class
     * @return the data
     * @throws UserError the user error
     */
    public <T extends IOObject> T getData(Class<T> desiredClass) throws UserError;

    /**
     * Returns the last object delivered to the connected {@link InputPort} or received from the
     * connected {@link OutputPort}
     *
     * @param <T>          the type parameter
     * @param desiredClass the desired class
     * @return the data or null
     * @throws UserError If data is not of the requested type.
     */
    public <T extends IOObject> T getDataOrNull(Class<T> desiredClass) throws UserError;

    /**
     * Same as {@link #getData(true)}.
     *
     * @param <T> the type parameter
     * @return the data
     * @throws OperatorException the operator exception
     * @deprecated use {@link #getData(Class)}
     */
    @Deprecated
	public <T extends IOObject> T getData() throws OperatorException;

    /**
     * Returns the last object delivered to the connected {@link InputPort} or received from the
     * connected {@link OutputPort}
     *
     * @param <T> the type parameter
     * @return the data or null
     * @throws UserError If data is not of the requested type.
     * @deprecated call {@link #getDataOrNull(Class)}
     */
    @Deprecated
	public <T extends IOObject> T getDataOrNull() throws UserError;

    /**
     * Returns the last object delivered to the connected {@link InputPort} or received from the
     * connected {@link OutputPort}. Never throws an exception.
     *
     * @return the any data or null
     */
    public IOObject getAnyDataOrNull();

    /**
     * Returns the set of ports to which this port belongs.  @return the ports
     *
     * @return the ports
     */
    public Ports<? extends Port> getPorts();

    /**
     * Gets a three letter abbreviation of the port's name.  @return the short name
     *
     * @return the short name
     */
    public String getShortName();

    /**
     * Returns a human readable description of the ports pre/ and postconditions.  @return the description
     *
     * @return the description
     */
    public String getDescription();

    /**
     * Returns true if connected to another Port.  @return the boolean
     *
     * @return the boolean
     */
    public boolean isConnected();

    /**
     * Report an error in the current process setup.  @param metaDataError the meta data error
     *
     * @param metaDataError the meta data error
     */
    public void addError(MetaDataError metaDataError);

    /**
     * Returns the set of errors added since the last clear errors.  @return the errors
     *
     * @return the errors
     */
    public Collection<MetaDataError> getErrors();

    /**
     * Clears data, meta data and errors at this port.
     *
     * @param clearFlags disjunction of the CLEAR_XX constants.
     */
    public void clear(int clearFlags);

    /**
     * Returns a sorted list of all quick fixes applicable for this port.  @return the list
     *
     * @return the list
     */
    public List<QuickFix> collectQuickFixes();

    /**
     * Returns the string "OperatorName.PortName".  @return the spec
     *
     * @return the spec
     */
    public String getSpec();

    /**
     * Indicates whether {@link ExecutionUnit#autoWire()} should simulate the pre RM 5.0 stack
     * behaviour of {@link IOContainer}. Normally, ports should return true here. However, ports
     * created by {@link PortPairExtender}s should return false here, since (most of the time) they
     * only pass data through rather adding new IOObjects to the IOContainer. TODO: delete
     *
     * @return the boolean
     * @deprecated The above reasoning turned out to be unnecessary if implementations in other             places are correct. We always simulate the stack now. This method is only called             from within {@link ExecutionUnit#autoWire(CompatibilityLevel, InputPorts,
     * LinkedList<OutputPort>)} to keep a reference, but it has no effect on the             auto-wiring process. We keep this method until the end of the alpha test phase of             Vega.
     */
    @Deprecated
	public boolean simulatesStack();

    /**
     * Locks the port so port extenders do not remove the port if disconnected. unlocks it.
     */
    public void lock();

    /**
     * Unlock.
     *
     * @see #lock() #lock()#lock()
     */
    public void unlock();

    /**
     * Is locked boolean.
     *
     * @return the boolean
     * @see #lock() #lock()#lock()
     */
    public boolean isLocked();

    /**
     * Releases of any hard reference to IOObjects held by this class.
     */
    void freeMemory();

}
