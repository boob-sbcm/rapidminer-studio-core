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

import com.rapidminer.operator.IOContainer;
import com.rapidminer.operator.Operator;
import com.rapidminer.tools.Observable;

import java.util.List;
import java.util.Observer;


/**
 * A collection of either input or output {@link Port}s (as defined by generic class T). <br/>
 * Instances of this class serve as a factory and register of ports. Ports can be accessed by names
 * or index. Actual behaviour is defined by specialized interfaces {@link InputPorts} and
 * {@link OutputPorts}. <br/>
 * Implementors of operators are encouraged to generate their ports at construction time and keep
 * private references to them, rather than retrieving them via one of the get methods every time
 * they are needed. The GUI will probably use the getter-methods. <br/>
 * <p>
 * Instances of this class can be observed by the {@link Observer} pattern.
 *
 * @param <T> the type parameter
 * @author Simon Fischer
 */
public interface Ports<T extends Port> extends Observable<Port> {

    /**
     * Returns all input port names registered with these ports.  @return the string [ ]
     *
     * @return the string [ ]
     */
    public String[] getPortNames();

    /**
     * Returns the number of ports.  @return the number of ports
     *
     * @return the number of ports
     */
    public int getNumberOfPorts();

    /**
     * Should be used in apply method to retrieve desired ports. name should be a constant defined
     * in the operator. If a port with the given name does not exist, but a {@link PortExtender}
     * with a suitable prefix is registered, ports will be created accordingly. In this case, the
     * user must call {@link #unlockPortExtenders()} to guarantee that ports can be removed again.
     *
     * @param name the name
     * @return the port by name
     */
    public T getPortByName(String name);

    /**
     * Should only be used by GUI.  @param index the index
     *
     * @param index the index
     * @return the port by index
     */
    public T getPortByIndex(int index);

    /**
     * Returns an immutable view of the ports.  @return the all ports
     *
     * @return the all ports
     */
    public List<T> getAllPorts();

    /**
     * Add a port and notify the {@link Observer}s.
     *
     * @param port the port
     * @throws PortException if the name is already used.
     */
    public void addPort(T port) throws PortException;

    /**
     * Remove a port and notify the {@link Observer}s.
     *
     * @param port the port
     * @throws PortException if port is not registered with this Ports instance.
     */
    public void removePort(T port) throws PortException;

    /**
     * Removes all ports.  @throws PortException the port exception
     *
     * @throws PortException the port exception
     */
    public void removeAll() throws PortException;

    /**
     * Returns true if this port is contained within this Ports object.  @param port the port
     *
     * @param port the port
     * @return the boolean
     */
    public boolean containsPort(T port);

    /**
     * Returns a textual description of the meta data currently assigned to these ports.  @return the meta data description
     *
     * @return the meta data description
     */
    public String getMetaDataDescription();

    /**
     * Returns the operator and process to which these ports are attached.  @return the owner
     *
     * @return the owner
     */
    public PortOwner getOwner();

    /**
     * Creates a new port and adds it to these Ports.  @param name the name
     *
     * @param name the name
     * @return the t
     */
    public T createPort(String name);

    /**
     * Creates (and adds) a new port whose {@link Port#simulatesStack()} returns false.  @param name the name
     *
     * @param name the name
     * @return the t
     */
    public T createPassThroughPort(String name);

    /**
     * Creates a new port and adds it to these Ports if add is true..  @param name the name
     *
     * @param name the name
     * @param add  the add
     * @return the t
     */
    public T createPort(String name, boolean add);

    /**
     * Renames the given port.  @param port the port
     *
     * @param port    the port
     * @param newName the new name
     */
    public void renamePort(T port, String newName);

    /**
     * Clears the input, meta data, and error messages.
     *
     * @param clearFlags the clear flags
     * @see Ports#clear(int) Ports#clear(int)Ports#clear(int)
     */
    public void clear(int clearFlags);

    /**
     * This is a backport method to generate IOContainers containing all output objects of the given
     * ports.
     *
     * @param onlyConnected the only connected
     * @return the io container
     */
    public IOContainer createIOContainer(boolean onlyConnected);

    /**
     * This is a backport method to generate IOContainers containing all output objects of the given
     * ports.
     *
     * @param onlyConnected   the only connected
     * @param omitNullResults the omit null results
     * @return the io container
     */
    public IOContainer createIOContainer(boolean onlyConnected, boolean omitNullResults);

    /**
     * Re-adds this port as the last port in this collection.  @param port the port
     *
     * @param port the port
     */
    public void pushDown(T port);

    /**
     * Disconnects all ports.
     */
    public void disconnectAll();

    /**
     * Disconnects all ports with exception to those connections to operators in the given list.
     *
     * @param exception the exception
     */
    public void disconnectAllBut(List<Operator> exception);

    /**
     * Registers a port extender with this ports.  @param extender the extender
     *
     * @param extender the extender
     */
    public void registerPortExtender(PortExtender extender);

    /**
     * While parsing the process XML file, we may have called loading
     */
    public void unlockPortExtenders();

    /**
     * Frees memory occupied by references to ioobjects.
     */
    public void freeMemory();

    /**
     * Returns the number of ports in these Ports that are actually connected.  @return the number of connected ports
     *
     * @return the number of connected ports
     */
    int getNumberOfConnectedPorts();

}
