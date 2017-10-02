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
package com.rapidminer;

import com.rapidminer.core.license.LicenseViolationException;
import com.rapidminer.core.license.ProductConstraintManager;
import com.rapidminer.datatable.DataTable;
import com.rapidminer.datatable.SimpleDataTable;
import com.rapidminer.example.table.AttributeFactory;
import com.rapidminer.gui.tools.ProgressThread;
import com.rapidminer.gui.tools.ProgressThreadStoppedException;
import com.rapidminer.io.process.XMLImporter;
import com.rapidminer.license.violation.LicenseViolation;
import com.rapidminer.operator.*;
import com.rapidminer.operator.execution.FlowData;
import com.rapidminer.operator.execution.ProcessFlowFilter;
import com.rapidminer.operator.nio.file.RepositoryBlobObject;
import com.rapidminer.operator.ports.InputPort;
import com.rapidminer.operator.ports.OutputPort;
import com.rapidminer.operator.ports.Port;
import com.rapidminer.report.ReportStream;
import com.rapidminer.repository.*;
import com.rapidminer.studio.internal.ProcessFlowFilterRegistry;
import com.rapidminer.tools.*;
import com.rapidminer.tools.Observable;
import com.rapidminer.tools.Observer;
import com.rapidminer.tools.container.Pair;
import com.rapidminer.tools.usagestats.ActionStatisticsCollector;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.swing.event.EventListenerList;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.*;
import java.util.logging.*;


/**
 * <p>
 * This class was introduced to avoid confusing handling of operator maps and other stuff when a new
 * process definition is created. It is also necessary for file name resolving and breakpoint
 * handling.
 * </p>
 * <p>
 * <p>
 * If you want to use RapidMiner from your own application the best way is often to create a process
 * definition from scratch (by adding the complete operator tree to the process' root operator) or
 * from a file (for example created with the GUI beforehand) and start it by invoking the
 * {@link #run()} method.
 * </p>
 * <p>
 * <p>
 * Observers can listen to changes of the associated file, repository location, and context.
 * </p>
 * TODO: Add reasonable class comment
 *
 * @author Ingo Mierswa
 */
public class Process extends AbstractObservable<Process> implements Cloneable {

    /**
     * The constant PROCESS_STATE_UNKNOWN.
     */
    public static final int PROCESS_STATE_UNKNOWN = -1;
    /**
     * The constant PROCESS_STATE_STOPPED.
     */
    public static final int PROCESS_STATE_STOPPED = 0;
    /**
     * The constant PROCESS_STATE_PAUSED.
     */
    public static final int PROCESS_STATE_PAUSED = 1;
    /**
     * The constant PROCESS_STATE_RUNNING.
     */
    public static final int PROCESS_STATE_RUNNING = 2;

	/** The root operator of the process. */
	private ProcessRootOperator rootOperator = null;

	/** This is the operator which is currently applied. */
	private Operator currentOperator;

	/**
	 * The process might be connected to this file or repository location which is then used to
	 * resolve relative file names which might be defined as parameters.
	 */
	private ProcessLocation processLocation;

	/**
	 * Indicates if the original process file has been changed by import rules. If this happens,
	 * overwriting will destroy the backward compatibility. This flag indicates that this would
	 * happen during saving.
	 */
	private boolean isProcessConverted = false;

	/**
	 * Indicates how deeply nested the current process is. The original process itself has a depth
	 * of {@code 0}. If that process spawns a new one via an Execute Process operator, the depth of
	 * the new one will be {@code 1}. If the new process also contains an Execute Process operator,
	 * the depth will be {@code 2} and so on. Used to prevent {@link StackOverflowError} when
	 * recursion is too deep (mostly to prevent accidents).
	 */
	private int nestingDepth = 0;

	/**
	 * This list contains all unknown parameter information which existed during the loading of the
	 * process.
	 */
	private final List<UnknownParameterInformation> unknownParameterInformation = new LinkedList<>();

	/** The listeners for breakpoints. */
	private final List<BreakpointListener> breakpointListeners = Collections.synchronizedList(new LinkedList<>());

	/** The list of filters called between each operator */
	private final List<ProcessFlowFilter> processFlowFilters = Collections.synchronizedList(new LinkedList<>());

	/** The listeners for logging (data tables). */
	private final List<LoggingListener> loggingListeners = Collections.synchronizedList(new LinkedList<>());

	private final List<ProcessStateListener> processStateListeners = Collections.synchronizedList(new LinkedList<>());

	/** The macro handler can be used to replace (user defined) macro strings. */
	private final MacroHandler macroHandler = new MacroHandler(this);

	/**
	 * This map holds the names of all operators in the process. Operators are automatically
	 * registered during adding and unregistered after removal.
	 */
	private Map<String, Operator> operatorNameMap = new HashMap<>();

	/**
	 * Maps names of ProcessLog operators to Objects, that these Operators use for collecting
	 * statistics (objects of type {@link DataTable}).
	 */
	private final Map<String, DataTable> dataTableMap = new HashMap<>();

	/**
	 * Maps names of report streams to reportStream objects
	 */
	private final Map<String, ReportStream> reportStreamMap = new HashMap<>();

	/**
	 * Stores IOObjects according to a specified name for the runtime of the process.
	 */
	private final Map<String, IOObject> storageMap = new HashMap<>();

	/**
	 * Stores IOObjects according to a specified name for a long-term scope like the session of
	 * RapidMiner or a RapidMiner Server app session
	 */
	private IOObjectMap ioObjectCache = RapidMiner.getGlobalIOObjectCache();

	/** Indicates the current process state. */
	private int processState = PROCESS_STATE_STOPPED;

	/** Indicates whether operators should be executed always or only when dirty. */
	private transient ExecutionMode executionMode = ExecutionMode.ALWAYS;

	/** Indicates whether we are updating meta data. */
	private transient DebugMode debugMode = DebugMode.DEBUG_OFF;

	private transient final Logger logger = makeLogger();

	/** @deprecated Use {@link #getLogger()} */
	@Deprecated
	private transient final LoggingHandler logService = new WrapperLoggingHandler(logger);

	private ProcessContext context = new ProcessContext();

	/** Message generated during import by {@link XMLImporter}. */
	private String importMessage;

	private final Annotations annotations = new Annotations();

	private RepositoryAccessor repositoryAccessor;

	/**
	 * Indicates whether the {@link IOContainer} returned by {@link #run()} might contain
	 * <code>null</code> values for empty results.
	 */
	private boolean omitNullResults = true;

	// -------------------
	// Constructors
	// -------------------

    /**
     * Constructs an process consisting only of a SimpleOperatorChain.
     */
    public Process() {
		try {
			ProcessRootOperator root = OperatorService.createOperator(ProcessRootOperator.class);
			root.rename(root.getOperatorDescription().getName());
			setRootOperator(root);
		} catch (Exception e) {
			throw new RuntimeException("Cannot initialize root operator of the process: " + e.getMessage(), e);
		}
		initContext();
	}

    /**
     * Instantiates a new Process.
     *
     * @param file the file
     * @throws IOException  the io exception
     * @throws XMLException the xml exception
     */
    public Process(final File file) throws IOException, XMLException {
		this(file, null);
	}

    /**
     * Creates a new process from the given process file. This might have been created with the GUI
     * beforehand.
     *
     * @param file             the file
     * @param progressListener the progress listener
     * @throws IOException  the io exception
     * @throws XMLException the xml exception
     */
    public Process(final File file, final ProgressListener progressListener) throws IOException, XMLException {
		this.processLocation = new FileProcessLocation(file);
		initContext();
		try (FileInputStream fis = new FileInputStream(file); Reader in = new InputStreamReader(fis, "UTF-8")) {
			readProcess(in, progressListener);
		}
	}

    /**
     * Creates a new process from the given XML copying state information not covered by the XML
     * from the parameter process.
     *
     * @param xml     the xml
     * @param process the process
     * @throws IOException  the io exception
     * @throws XMLException the xml exception
     */
    public Process(final String xml, final Process process) throws IOException, XMLException {
		this(xml);
		this.processLocation = process.processLocation;
	}

    /**
     * Reads an process configuration from an XML String.  @param xmlString the xml string
     *
     * @param xmlString the xml string
     * @throws IOException  the io exception
     * @throws XMLException the xml exception
     */
    public Process(final String xmlString) throws IOException, XMLException {
		initContext();
		StringReader in = new StringReader(xmlString);
		readProcess(in);
		in.close();
	}

    /**
     * Reads an process configuration from the given reader.  @param in the in
     *
     * @param in the in
     * @throws IOException  the io exception
     * @throws XMLException the xml exception
     */
    public Process(final Reader in) throws IOException, XMLException {
		initContext();
		readProcess(in);
	}

    /**
     * Reads an process configuration from the given stream.  @param in the in
     *
     * @param in the in
     * @throws IOException  the io exception
     * @throws XMLException the xml exception
     */
    public Process(final InputStream in) throws IOException, XMLException {
		initContext();
		readProcess(new InputStreamReader(in, XMLImporter.PROCESS_FILE_CHARSET));
	}

    /**
     * Reads an process configuration from the given URL.  @param url the url
     *
     * @param url the url
     * @throws IOException  the io exception
     * @throws XMLException the xml exception
     */
    public Process(final URL url) throws IOException, XMLException {
		initContext();
		Reader in = new InputStreamReader(WebServiceTools.openStreamFromURL(url), getEncoding(null));
		readProcess(in);
		in.close();
	}

    /**
     * Make logger logger.
     *
     * @return the logger
     */
    protected Logger makeLogger() {
		return Logger.getLogger(Process.class.getName());
	}

	private void initContext() {
		getContext().addObserver(delegatingContextObserver, false);
	}

	/**
	 * Clone constructor. Makes a deep clone of the operator tree and the process file. The same
	 * applies for the operatorNameMap. The breakpoint listeners are copied by reference and all
	 * other fields are initialized like for a fresh process.
	 */
	private Process(final Process other) {
		this();
		setRootOperator((ProcessRootOperator) other.rootOperator.cloneOperator(other.rootOperator.getName(), false));
		this.currentOperator = null;
		if (other.processLocation != null) {
			this.processLocation = other.processLocation;
		} else {
			this.processLocation = null;
		}
	}

	private void initLogging(final int logVerbosity) {
		if (logVerbosity >= 0) {
			logger.setLevel(WrapperLoggingHandler.LEVELS[logVerbosity]);
		} else {
			logger.setLevel(Level.INFO);
		}
	}

	@Override
	public Object clone() {
		return new Process(this);
	}

    /**
     * Sets experiment state.
     *
     * @param state the state
     * @deprecated Use {@link #setProcessState(int)} instead
     */
    @Deprecated
	public synchronized void setExperimentState(final int state) {
		setProcessState(state);
	}

	private void setProcessState(final int state) {
		int oldState = this.processState;
		this.processState = state;
		fireProcessStateChanged(oldState, state);
	}

    /**
     * Gets experiment state.
     *
     * @return the experiment state
     * @deprecated Use {@link #getProcessState()} instead
     */
    @Deprecated
	public synchronized int getExperimentState() {
		return getProcessState();
	}

    /**
     * Gets process state.
     *
     * @return the process state
     */
    public int getProcessState() {
		return this.processState;
	}

	// -------------------------
	// User initiated state changes
	// ---------------------------

    /**
     * Adds the given process state listener.  @param processStateListener the process state listener
     *
     * @param processStateListener the process state listener
     */
    public void addProcessStateListener(ProcessStateListener processStateListener) {
		this.processStateListeners.add(processStateListener);
	}

    /**
     * Removes the given process state listener.  @param processStateListener the process state listener
     *
     * @param processStateListener the process state listener
     */
    public void removeProcessStateListener(ProcessStateListener processStateListener) {
		this.processStateListeners.remove(processStateListener);
	}

	private void fireProcessStateChanged(int stateBefore, int newState) {
		// sanity check
		if (stateBefore == newState) {
			return;
		}
		List<ProcessStateListener> listeners;
		synchronized (processStateListeners) {
			listeners = new LinkedList<>(processStateListeners);
		}
		for (ProcessStateListener listener : listeners) {
			switch (newState) {
				case PROCESS_STATE_PAUSED:
					listener.paused(this);
					break;
				case PROCESS_STATE_STOPPED:
					listener.stopped(this);
					break;
				default:
					if (stateBefore == PROCESS_STATE_STOPPED) {
						listener.started(this);
					} else {
						listener.resumed(this);
					}
					break;
			}
		}

	}

	// -------------------------
	// Logging
	// -------------------------

    /**
     * Gets log.
     *
     * @return the log
     * @deprecated use {@link #getLogger()} instead
     */
    @Deprecated
	public LoggingHandler getLog() {
		return this.logService;
	}

    /**
     * Gets logger.
     *
     * @return the logger
     */
    public Logger getLogger() {
		return this.logger;
	}

	// -------------------------
	// Macro Handler
	// -------------------------

    /**
     * Returns the macro handler.  @return the macro handler
     *
     * @return the macro handler
     */
    public MacroHandler getMacroHandler() {
		return this.macroHandler;
	}

    /**
     * Clears all macros.
     */
    public void clearMacros() {
		this.getMacroHandler().clear();
	}

	// -------------------------
	// IOObject Storage
	// -------------------------

    /**
     * Stores the object with the given name.  @param name the name
     *
     * @param name   the name
     * @param object the object
     */
    public void store(final String name, final IOObject object) {
		this.storageMap.put(name, object);
	}

    /**
     * Retrieves the stored object.  @param name the name
     *
     * @param name   the name
     * @param remove the remove
     * @return the io object
     */
    public IOObject retrieve(final String name, final boolean remove) {
		if (remove) {
			return this.storageMap.remove(name);
		} else {
			return this.storageMap.get(name);
		}
	}

    /**
     * Clears all macros.
     */
    public void clearStorage() {
		this.storageMap.clear();
	}

	// -------------------------
	// State storage
	// -------------------------

    /**
     * Injects another {@link IOObject} cache (to remember and recall {@link IOObject}s during a
     * long-term scope)
     * <p>
     * If {@link #ioObjectCache} is null, the setter does not have any effect
     *
     * @param ioObjectCache the io object cache
     */
    public void setIOObjectCache(IOObjectMap ioObjectCache) {
		if (ioObjectCache != null) {
			this.ioObjectCache = ioObjectCache;
		}
	}

    /**
     * Returns the {@link IOObject} cache (to remember and recall {@link IOObject}s during a
     * long-term scope), designed to be manipulated by operators in the process
     *
     * @return the IOObjectCache of the process
     */
    public IOObjectMap getIOObjectCache() {
		return ioObjectCache;
	}

	// -------------------------
	// Data Tables (Logging)
	// -------------------------

    /**
     * Adds the given logging listener.  @param loggingListener the logging listener
     *
     * @param loggingListener the logging listener
     */
    public void addLoggingListener(final LoggingListener loggingListener) {
		this.loggingListeners.add(loggingListener);
	}

    /**
     * Removes the given logging listener.  @param loggingListener the logging listener
     *
     * @param loggingListener the logging listener
     */
    public void removeLoggingListener(final LoggingListener loggingListener) {
		this.loggingListeners.remove(loggingListener);
	}

    /**
     * Returns true if a data table object with the given name exists.  @param name the name
     *
     * @param name the name
     * @return the boolean
     */
    public boolean dataTableExists(final String name) {
		return dataTableMap.get(name) != null;
	}

    /**
     * Adds the given data table.
     *
     * @param table the table
     */
    public void addDataTable(final DataTable table) {
		dataTableMap.put(table.getName(), table);
		synchronized (loggingListeners) {
			for (LoggingListener listener : loggingListeners) {
				listener.addDataTable(table);
			}
		}
	}

    /**
     * Clears a single data table, i.e. removes all entries.  @param name the name
     *
     * @param name the name
     */
    public void clearDataTable(final String name) {
		DataTable table = getDataTable(name);
		if (table != null) {
			if (table instanceof SimpleDataTable) {
				((SimpleDataTable) table).clear();
			}
		}
	}

    /**
     * Deletes a single data table.  @param name the name
     *
     * @param name the name
     */
    public void deleteDataTable(final String name) {
		if (dataTableExists(name)) {
			DataTable table = dataTableMap.remove(name);
			synchronized (loggingListeners) {
				for (LoggingListener listener : loggingListeners) {
					listener.removeDataTable(table);
				}
			}
		}
	}

    /**
     * Returns the data table associated with the given name. If the name was not used yet, an empty
     * DataTable object is created with the given columnNames.
     *
     * @param name the name
     * @return the data table
     */
    public DataTable getDataTable(final String name) {
		return dataTableMap.get(name);
	}

    /**
     * Returns all data tables.  @return the data tables
     *
     * @return the data tables
     */
    public Collection<DataTable> getDataTables() {
		return dataTableMap.values();
	}

	/** Removes all data tables before running a new process. */
	private void clearDataTables() {
		dataTableMap.clear();
	}

	// ------------------------------
	// Report Streams
	// ------------------------------

    /**
     * This method adds a new report stream with the given name
     *
     * @param stream the stream
     */
    public void addReportStream(final ReportStream stream) {
		reportStreamMap.put(stream.getName(), stream);
	}

    /**
     * Returns the reportStream with given name
     *
     * @param name the name
     * @return the report stream
     */
    public ReportStream getReportStream(final String name) {
		if (name == null || name.length() == 0) {
			if (reportStreamMap.size() == 1) {
				return reportStreamMap.values().iterator().next();
			} else {
				return null;
			}
		} else {
			return reportStreamMap.get(name);
		}
	}

    /**
     * Removes this reportStream from process. This report Stream will not be notified about new
     * report items.
     *
     * @param name of the report stream given in the ReportGenerator operator
     */
    public void removeReportStream(final String name) {
		reportStreamMap.remove(name);
	}

    /**
     * Clear report streams.
     */
    public void clearReportStreams() {
		reportStreamMap.clear();
	}

	// ----------------------
	// Operator Handling
	// ----------------------

    /**
     * Sets the current root operator. This might lead to a new registering of operator names.  @param root the root
     *
     * @param root the root
     */
    public void setRootOperator(final ProcessRootOperator root) {
		if (this.rootOperator != null) {
			this.rootOperator.removeObserver(delegatingOperatorObserver);
		}
		this.rootOperator = root;
		this.rootOperator.addObserver(delegatingOperatorObserver, false);
		this.operatorNameMap.clear();
		this.rootOperator.setProcess(this);
	}

    /**
     * Delivers the current root operator.  @return the root operator
     *
     * @return the root operator
     */
    public ProcessRootOperator getRootOperator() {
		return rootOperator;
	}

    /**
     * Returns the operator with the given name.  @param name the name
     *
     * @param name the name
     * @return the operator
     */
    public Operator getOperator(final String name) {
		return operatorNameMap.get(name);
	}

    /**
     * Returns the operator that is currently being executed.  @return the current operator
     *
     * @return the current operator
     */
    public Operator getCurrentOperator() {
		return currentOperator;
	}

    /**
     * Returns a Collection view of all operators.  @return the all operators
     *
     * @return the all operators
     */
    public Collection<Operator> getAllOperators() {
		List<Operator> result = rootOperator.getAllInnerOperators();
		result.add(0, rootOperator);
		return result;
	}

    /**
     * Returns a Set view of all operator names (i.e. Strings).  @return the all operator names
     *
     * @return the all operator names
     */
    public Collection<String> getAllOperatorNames() {
		Collection<String> allNames = new LinkedList<>();
		for (Operator o : getAllOperators()) {
			allNames.add(o.getName());
		}
		return allNames;
	}

    /**
     * Sets the operator that is currently being executed.  @param operator the operator
     *
     * @param operator the operator
     */
    public void setCurrentOperator(final Operator operator) {
		this.currentOperator = operator;
	}

	// -------------------------------------
	// start, stop, resume, breakpoints
	// -------------------------------------

	/** We synchronize on this object to wait and resume operation. */
	private final Object breakpointLock = new Object();

    /**
     * Pauses the process at a breakpoint.  @param operator the operator
     *
     * @param operator       the operator
     * @param iocontainer    the iocontainer
     * @param breakpointType the breakpoint type
     */
    public void pause(final Operator operator, final IOContainer iocontainer, final int breakpointType) {
		setProcessState(PROCESS_STATE_PAUSED);
		fireBreakpointEvent(operator, iocontainer, breakpointType);
		while (getProcessState() == Process.PROCESS_STATE_PAUSED) {
			synchronized (breakpointLock) {
				try {
					breakpointLock.wait();
				} catch (InterruptedException e) {
				}
			}
		}
	}

    /**
     * Resumes the process after it has been paused.
     */
    public void resume() {
		setProcessState(PROCESS_STATE_RUNNING);
		synchronized (breakpointLock) {
			breakpointLock.notifyAll();
		}
		fireResumeEvent();
	}

    /**
     * Stops the process as soon as possible.
     */
    public void stop() {
		this.setProcessState(PROCESS_STATE_STOPPED);
		synchronized (breakpointLock) {
			breakpointLock.notifyAll();
		}
	}

    /**
     * Stops the process as soon as possible.
     */
    public void pause() {
		this.setProcessState(PROCESS_STATE_PAUSED);
	}

    /**
     * Returns true iff the process should be stopped.  @return the boolean
     *
     * @return the boolean
     */
    public boolean shouldStop() {
		return getProcessState() == PROCESS_STATE_STOPPED;
	}

    /**
     * Returns true iff the process should be stopped.  @return the boolean
     *
     * @return the boolean
     */
    public boolean shouldPause() {
		return getProcessState() == PROCESS_STATE_PAUSED;
	}

	// --------------------
	// Filters between operators handling
	// --------------------

    /**
     * Add a new {@link ProcessFlowFilter} to this process. The filter will be called directly
     * before and after each operator. Refer to {@link ProcessFlowFilter} for more information.
     * <p>
     * If the given filter instance is already registered, it will not be added a second time.
     * </p>
     *
     * @param filter the filter instance to add
     */
    public void addProcessFlowFilter(ProcessFlowFilter filter) {
		if (filter == null) {
			throw new IllegalArgumentException("filter must not be null!");
		}
		if (!processFlowFilters.contains(filter)) {
			processFlowFilters.add(filter);
		}
	}

    /**
     * Remove a {@link ProcessFlowFilter} from this process. Does nothing if the filter is unknown.
     *
     * @param filter the filter instance to remove
     */
    public void removeProcessFlowFilter(ProcessFlowFilter filter) {
		if (filter == null) {
			throw new IllegalArgumentException("filter must not be null!");
		}
		processFlowFilters.remove(filter);
	}

    /**
     * Notifies all registered {@link ProcessFlowFilter}s that the next operator in the process is
     * about to be executed.
     *
     * @param previousOperator the previous operator; may be {@code null} for the first operator in a subprocess
     * @param nextOperator     the next operator to be called, never {@code null}
     * @param input            the list of all input data for the next operator. If {@code null}, an empty list            will be used
     * @throws OperatorException the operator exception
     */
    public void fireProcessFlowBeforeOperator(Operator previousOperator, Operator nextOperator, List<FlowData> input)
			throws OperatorException {
		if (nextOperator == null) {
			throw new IllegalArgumentException("nextOperator must not be null!");
		}
		if (input == null) {
			input = Collections.emptyList();
		}
		synchronized (processFlowFilters) {
			for (ProcessFlowFilter filter : processFlowFilters) {
				filter.preOperator(previousOperator, nextOperator, input);
			}
		}
	}

    /**
     * Notifies all registered {@link ProcessFlowFilter}s that an operator in the process has just
     * finished execution.
     *
     * @param previousOperator the operator which just finished, never {@code null}
     * @param nextOperator     the next operator to be called; may be {@code null} if this was the last operator            in a subprocess
     * @param output           the list of all output data from the previous operator. If {@code null}, an empty            list will be used
     * @throws OperatorException the operator exception
     */
    public void fireProcessFlowAfterOperator(Operator previousOperator, Operator nextOperator, List<FlowData> output)
			throws OperatorException {
		if (previousOperator == null) {
			throw new IllegalArgumentException("previousOperator must not be null!");
		}
		if (output == null) {
			output = Collections.emptyList();
		}
		synchronized (processFlowFilters) {
			for (ProcessFlowFilter filter : processFlowFilters) {
				filter.postOperator(previousOperator, nextOperator, output);
			}
		}
	}

    /**
     * Copies the registered {@link ProcessFlowFilter}s of this process to the given process
     * instance.
     *
     * @param otherProcess the process who should get all process flow listeners which are registered to this            process instance
     */
    public void copyProcessFlowListenersToOtherProcess(Process otherProcess) {
		if (otherProcess == null) {
			throw new IllegalArgumentException("otherProcess must not be null!");
		}

		synchronized (processFlowFilters) {
			for (ProcessFlowFilter filter : processFlowFilters) {
				otherProcess.addProcessFlowFilter(filter);
			}
		}
	}

	// --------------------
	// Breakpoint Handling
	// --------------------

    /**
     * Adds a breakpoint listener.  @param listener the listener
     *
     * @param listener the listener
     */
    public void addBreakpointListener(final BreakpointListener listener) {
		breakpointListeners.add(listener);
	}

    /**
     * Removes a breakpoint listener.  @param listener the listener
     *
     * @param listener the listener
     */
    public void removeBreakpointListener(final BreakpointListener listener) {
		breakpointListeners.remove(listener);
	}

	/** Fires the event that the process was paused. */
	private void fireBreakpointEvent(final Operator operator, final IOContainer ioContainer, final int location) {
		synchronized (breakpointListeners) {
			for (BreakpointListener l : breakpointListeners) {
				l.breakpointReached(this, operator, ioContainer, location);
			}
		}
	}

    /**
     * Fires the event that the process was resumed.
     */
    public void fireResumeEvent() {
		LinkedList<BreakpointListener> l;
		synchronized (breakpointListeners) {
			l = new LinkedList<>(breakpointListeners);
		}
		for (BreakpointListener listener : l) {
			listener.resume();
		}
	}

	// -----------------
	// Checks
	// -----------------

    /**
     * Delivers the information about unknown parameter types which occurred during process creation
     * (from streams or files).
     *
     * @return the unknown parameters
     */
    public List<UnknownParameterInformation> getUnknownParameters() {
		return this.unknownParameterInformation;
	}

    /**
     * Clears the information about unknown parameter types which occurred during process creation
     * (from streams or files).
     */
    public void clearUnknownParameters() {
		this.unknownParameterInformation.clear();
	}

    /**
     * Checks for correct number of inner operators, properties, and io.
     *
     * @param inputContainer the input container
     * @return the boolean
     * @deprecated Use {@link #checkProcess(IOContainer)} instead
     */
    @Deprecated
	public boolean checkExperiment(final IOContainer inputContainer) {
		return checkProcess(inputContainer);
	}

    /**
     * Checks for correct number of inner operators, properties, and io.  @param inputContainer the input container
     *
     * @param inputContainer the input container
     * @return the boolean
     */
    public boolean checkProcess(final IOContainer inputContainer) {
		rootOperator.checkAll();
		return true;
	}

	// ------------------
	// Running
	// ------------------

	/**
	 * This method initializes the process, the operators, and the services and must be invoked at
	 * the beginning of run. It also resets all apply counts.
	 */
	private final void prepareRun(final int logVerbosity) throws OperatorException {
		initLogging(logVerbosity);

		setProcessState(PROCESS_STATE_RUNNING);
		getLogger().fine("Initialising process setup.");

		RandomGenerator.init(this);
		ResultService.init(this);

		clearDataTables();
		clearReportStreams();
		clearMacros();
		clearStorage();
		if (getExecutionMode() != ExecutionMode.ONLY_DIRTY) {
			getRootOperator().clear(Port.CLEAR_DATA);
		}
		AttributeFactory.resetNameCounters();

		getLogger().fine("Process initialised.");

		// add process start macro value here already to have it available for root parameters
		// can be overwritten if it is passed to the run() method via the macro map
		getMacroHandler().addMacro(MacroHandler.PROCESS_START,
				MacroHandler.DATE_FORMAT.get().format(new Date(System.currentTimeMillis())));
	}

	/**
	 * Checks whether input data was specified in the process context. Will return true if at least
	 * one input port has specified data.
	 *
	 * @param firstPort
	 *            The first port to check
	 * @return true if at least one input port has specified data.
	 */
	private boolean checkForInitialData(int firstPort) {
		for (int i = firstPort; i < context.getInputRepositoryLocations().size(); i++) {
			String location = context.getInputRepositoryLocations().get(i);
			if (location == null || location.length() == 0) {
				continue;
			}
			if (i >= rootOperator.getSubprocess(0).getInnerSources().getNumberOfPorts()) {
				break;
			}
			return true;
		}
		return false;
	}

    /**
     * Loads results from the repository if specified in the {@link ProcessContext}.
     *
     * @param firstPort Specifies the first port which is read from the ProcessContext. This enables the            possibility to skip ports for which input is already specified via the input            parameter of the run() method.
     * @throws UserError the user error
     */
    protected void loadInitialData(final int firstPort) throws UserError {
		loadInitialData(firstPort, null);
	}

    /**
     * Loads results from the repository if specified in the {@link ProcessContext}. Will also show
     * the progress of loading if a {@link ProgressListener} is specified.
     *
     * @param firstPort        Specifies the first port which is read from the ProcessContext. This enables the            possibility to skip ports for which input is already specified via the input            parameter of the run() method.
     * @param progressListener The progress listener for loading the data. Can be null.
     * @throws UserError the user error
     */
    protected void loadInitialData(final int firstPort, ProgressListener progressListener) throws UserError {
		ProcessContext context = getContext();
		if (context.getInputRepositoryLocations().isEmpty()) {
			if (progressListener != null) {
				progressListener.complete();
			}
			return;
		}
		if (progressListener != null) {
			progressListener.setTotal(context.getInputRepositoryLocations().size() - firstPort - 1);
			progressListener.setCompleted(0);
		}
		getLogger()
				.info("Loading initial data" + (firstPort > 0 ? " (starting at port " + (firstPort + 1) + ")" : "") + ".");
		for (int i = firstPort; i < context.getInputRepositoryLocations().size(); i++) {
			if (shouldStop()) {
				return;
			}
			String location = context.getInputRepositoryLocations().get(i);
			if (location == null || location.length() == 0) {
				getLogger().fine("Input #" + (i + 1) + " not specified.");
				if (progressListener != null) {
					progressListener.setCompleted(i - firstPort + 1);
				}
				continue;
			}
			if (i >= rootOperator.getSubprocess(0).getInnerSources().getNumberOfPorts()) {
				getLogger().warning("No input port available for process input #" + (i + 1) + ": " + location);
				int rest = context.getInputRepositoryLocations().size() - i - 1;
				if (rest != 0) {
					getLogger().warning("Aborting loading " + rest + " more process input(s)");
				}
				if (progressListener != null) {
					progressListener.complete();
				}
				break;
			}
			OutputPort port = rootOperator.getSubprocess(0).getInnerSources().getPortByIndex(i);
			RepositoryLocation loc;
			try {
				loc = resolveRepositoryLocation(location);
			} catch (MalformedRepositoryLocationException | UserError e1) {
				if (progressListener != null) {
					progressListener.complete();
				}
				throw new PortUserError(port, 325, e1.getMessage());
			}
			try {
				Entry entry = loc.locateEntry();
				if (entry == null) {
					if (progressListener != null) {
						progressListener.complete();
					}
					throw new PortUserError(port, 312, loc, "Entry does not exist.");
				}
				if (entry instanceof IOObjectEntry) {
					getLogger().info("Assigning " + loc + " to input port " + port.getSpec() + ".");
					// only deliver the data if the port is really connected
					if (port.isConnected()) {
						port.deliver(((IOObjectEntry) entry).retrieveData(null));
					}
				} else if (entry instanceof BlobEntry) {
					getLogger().info("Assigning " + loc + " to input port " + port.getSpec() + ".");
					// only deliver the data if the port is really connected
					if (port.isConnected()) {
						port.deliver(new RepositoryBlobObject(loc));
					}
				} else {
					getLogger().info("Cannot assigning " + loc + " to input port " + port.getSpec()
							+ ": Repository location does not reference an IOObject entry.");
					if (progressListener != null) {
						progressListener.complete();
					}
					throw new PortUserError(port, 312, loc, "Not an IOObject entry.");
				}
				if (progressListener != null) {
					progressListener.setCompleted(i - firstPort + 1);
				}
			} catch (RepositoryException e) {
				if (progressListener != null) {
					progressListener.complete();
				}
				throw new PortUserError(port, 312, loc, e.getMessage());
			}
		}
	}

    /**
     * Stores the results in the repository if specified in the {@link ProcessContext}.  @throws UserError the user error
     *
     * @throws UserError the user error
     */
    protected void saveResults() throws UserError {
		ProcessContext context = getContext();
		if (context.getOutputRepositoryLocations().isEmpty()) {
			return;
		}
		getLogger().info("Saving results.");
		for (int i = 0; i < context.getOutputRepositoryLocations().size(); i++) {
			String locationStr = context.getOutputRepositoryLocations().get(i);
			if (locationStr == null || locationStr.length() == 0) {
				getLogger().fine("Output #" + (i + 1) + " not specified.");
			} else {
				if (i >= rootOperator.getSubprocess(0).getInnerSinks().getNumberOfPorts()) {
					getLogger().warning("No output port corresponding to process output #" + (i + 1) + ": " + locationStr);
				} else {
					InputPort port = rootOperator.getSubprocess(0).getInnerSinks().getPortByIndex(i);
					RepositoryLocation location;
					try {
						location = rootOperator.getProcess().resolveRepositoryLocation(locationStr);
					} catch (MalformedRepositoryLocationException e1) {
						throw new PortUserError(port, 325, e1.getMessage());
					} catch (UserError e1) {
						throw new PortUserError(port, 325, e1.getMessage());
					}
					IOObject data = port.getDataOrNull(IOObject.class);
					if (data == null) {
						getLogger().warning(
								"Nothing to store at " + location + ": No results produced at " + port.getSpec() + ".");
					} else {
						try {
							RepositoryAccessor repositoryAccessor = getRepositoryAccessor();
							location.setAccessor(repositoryAccessor);
							RepositoryManager.getInstance(repositoryAccessor).store(data, location, rootOperator);

						} catch (RepositoryException e) {
							throw new PortUserError(port, 315, location, e.getMessage());
						}
					}
				}
			}
		}
	}

    /**
     * Apply context macros.
     */
    public void applyContextMacros() {
		for (Pair<String, String> macro : context.getMacros()) {
			getLogger().fine("Defining context macro: " + macro.getFirst() + " = " + macro.getSecond() + ".");
			getMacroHandler().addMacro(macro.getFirst(), macro.getSecond());
		}
	}

    /**
     * Starts the process with no input.  @return the io container
     *
     * @return the io container
     * @throws OperatorException the operator exception
     */
    public final IOContainer run() throws OperatorException {
		return run(new IOContainer());
	}

    /**
     * Starts the process with the given log verbosity.  @param logVerbosity the log verbosity
     *
     * @param logVerbosity the log verbosity
     * @return the io container
     * @throws OperatorException the operator exception
     */
    public final IOContainer run(final int logVerbosity) throws OperatorException {
		return run(new IOContainer(), logVerbosity);
	}

    /**
     * Starts the process with the given input.  @param input the input
     *
     * @param input the input
     * @return the io container
     * @throws OperatorException the operator exception
     */
    public final IOContainer run(final IOContainer input) throws OperatorException {
		return run(input, LogService.UNKNOWN_LEVEL);
	}

    /**
     * Starts the process with the given input. The process uses the given log verbosity.  @param input the input
     *
     * @param input        the input
     * @param logVerbosity the log verbosity
     * @return the io container
     * @throws OperatorException the operator exception
     */
    public final IOContainer run(final IOContainer input, final int logVerbosity) throws OperatorException {
		return run(input, logVerbosity, null);
	}

    /**
     * Starts the process with the given input. The process uses a default log verbosity. The
     * boolean flag indicates if some static initializations should be cleaned before the process is
     * started. This should usually be true but it might be useful to set this to false if, for
     * example, several process runs uses the same object visualizer which would have been cleaned
     * otherwise.
     *
     * @param input  the input
     * @param unused the unused
     * @return the io container
     * @throws OperatorException the operator exception
     */
    @Deprecated
	public final IOContainer run(final IOContainer input, final boolean unused) throws OperatorException {
		return run(input, LogService.UNKNOWN_LEVEL);
	}

    /**
     * Starts the process with the given input. The process uses the given log verbosity. The
     * boolean flag indicates if some static initializations should be cleaned before the process is
     * started. This should usually be true but it might be useful to set this to false if, for
     * example, several process runs uses the same object visualizer which would have been cleaned
     * otherwise.
     *
     * @param input        the input
     * @param logVerbosity the log verbosity
     * @param cleanUp      the clean up
     * @return the io container
     * @throws OperatorException the operator exception
     */
    @Deprecated
	public final IOContainer run(final IOContainer input, final int logVerbosity, final boolean cleanUp)
			throws OperatorException {
		return run(input, logVerbosity, null);
	}

    /**
     * Starts the process with the given input. The process uses the given log verbosity. The
     * boolean flag indicates if some static initializations should be cleaned before the process is
     * started. This should usually be true but it might be useful to set this to false if, for
     * example, several process runs uses the same object visualizer which would have been cleaned
     * otherwise.
     * <p>
     * Since the macros are cleaned then as well it is not possible to set macros to a process but
     * with the given macroMap of this method.
     *
     * @param input        the input
     * @param logVerbosity the log verbosity
     * @param cleanUp      the clean up
     * @param macroMap     the macro map
     * @return the io container
     * @throws OperatorException the operator exception
     */
    @Deprecated
	public final IOContainer run(final IOContainer input, final int logVerbosity, final boolean cleanUp,
			final Map<String, String> macroMap) throws OperatorException {
		return run(input, logVerbosity, macroMap);

	}

    /**
     * Run io container.
     *
     * @param input        the input
     * @param logVerbosity the log verbosity
     * @param macroMap     the macro map
     * @return the io container
     * @throws OperatorException the operator exception
     */
    public final IOContainer run(final IOContainer input, final int logVerbosity, final Map<String, String> macroMap)
			throws OperatorException {
		return run(input, logVerbosity, macroMap, true);
	}

    /**
     * Starts the process with the given input. The process uses the given log verbosity.
     * <p>
     * If input is not null, it is delivered to the input ports of the process. If it is null or
     * empty, the input is read instead from the locations specified in the {@link ProcessContext}.
     * <p>
     * If input contains less IOObjects than are specified in the context, the remaining ones are
     * read according to the context.
     *
     * @param input        the input
     * @param logVerbosity the log verbosity
     * @param macroMap     the macro map
     * @param storeOutput  Specifies if the output of the process should be saved. This is useful, if you            embed a process using the Execute Process operator, and do not want to store the            output as specified by the process context.
     * @return the io container
     * @throws OperatorException the operator exception
     */
    public final IOContainer run(final IOContainer input, int logVerbosity, final Map<String, String> macroMap,
			final boolean storeOutput) throws OperatorException {
		// make sure the process flow filter is registered
		ProcessFlowFilter filter = ProcessFlowFilterRegistry.INSTANCE.getProcessFlowFilter();
		if (filter != null && !processFlowFilters.contains(filter)) {
			addProcessFlowFilter(filter);
		}

		// make sure licensing constraints are not violated
		// iterate over all operators in the process
		for (Operator op : rootOperator.getAllInnerOperators()) {
			// we only care about enabled operators
			if (op.isEnabled()) {

				// Check for annotations that constrain access to the current operator
				List<LicenseViolation> licenseViolations = ProductConstraintManager.INSTANCE.checkAnnotationViolations(op,
						true);
				if (!licenseViolations.isEmpty()) {
					throw new LicenseViolationException(op, licenseViolations);
				}

				// as a side effect mark all enabled operators as dirty
				// so it is clear which ones have already been executed
				op.makeDirty();
			}
		}
		// fetching process name for logging
		String name = null;
		if (getProcessLocation() != null) {
			name = getProcessLocation().toString();
		}

		int myVerbosity = rootOperator.getParameterAsInt(ProcessRootOperator.PARAMETER_LOGVERBOSITY);
		if (logVerbosity == LogService.UNKNOWN_LEVEL) {
			logVerbosity = LogService.OFF;
		}
		logVerbosity = Math.min(logVerbosity, myVerbosity);

		prepareRun(logVerbosity);

		// apply macros
		applyContextMacros();
		if (macroMap != null) {
			for (Map.Entry<String, String> entry : macroMap.entrySet()) {
				getMacroHandler().addMacro(entry.getKey(), entry.getValue());
			}
		}

		String logFilename = rootOperator.getParameter(ProcessRootOperator.PARAMETER_LOGFILE);
		Handler logHandler = null;
		if (logFilename != null) {
			try {
				logHandler = new FileHandler(logFilename);
				logHandler.setFormatter(new SimpleFormatter());
				logHandler.setLevel(Level.ALL);
				getLogger().config("Logging process to file " + logFilename);
			} catch (Exception e) {
				getLogger().warning("Cannot create log file '" + logFilename + "': " + e);
			}
		}
		if (logHandler != null) {
			getLogger().addHandler(logHandler);
		}

		long start = System.currentTimeMillis();

		rootOperator.processStarts();

		final int firstInput = input != null ? input.getIOObjects().length : 0;
		if (checkForInitialData(firstInput)) {
			// load data as specified in process context
			ProgressThread pt = new ProgressThread("load_context_data", false) {

				@Override
				public void run() {
					try {
						loadInitialData(firstInput, getProgressListener());
						setLastInitException(null);
					} catch (ProgressThreadStoppedException ptse) {
						// do nothing, it's checked below (pt.isCancelled)
					} catch (Exception e) {
						setLastInitException(e);
					}
				}
			};
			pt.setShowDialogTimerDelay(5000);
			pt.setStartDialogShowTimer(true);

			pt.startAndWait();
			if (lastInitException != null) {
				Throwable e = lastInitException;
				lastInitException = null;
				finishProcess(logHandler);
				OperatorException oe;
				if (e instanceof OperatorException) {
					oe = (OperatorException) e;
				} else {
					oe = new OperatorException("context_problem_other", e, e.getMessage());
				}
				throw oe;
			}
			if (pt.isCancelled() || shouldStop()) {
				finishProcess(logHandler);
				throw new ProcessStoppedException();
			}
		}

		if (name != null) {
			getLogger().info("Process " + name + " starts");
		} else {
			getLogger().info("Process starts");
		}
		getLogger().fine("Process:" + Tools.getLineSeparator() + getRootOperator().createProcessTree(3));

		try {
			ActionStatisticsCollector.getInstance().logExecution(this);
			if (input != null) {
				rootOperator.deliverInput(Arrays.asList(input.getIOObjects()));
			}
			rootOperator.execute();
			rootOperator.checkForStop();
			if (storeOutput) {
				saveResults();
			}
			IOContainer result = rootOperator.getResults(isOmittingNullResults());
			long end = System.currentTimeMillis();

			getLogger().fine("Process:" + Tools.getLineSeparator() + getRootOperator().createProcessTree(3));
			if (name != null) {
				getLogger().info("Process " + name + " finished successfully after " + Tools.formatDuration(end - start));
			} else {
				getLogger().info("Process finished successfully after " + Tools.formatDuration(end - start));
			}

			return result;
		} catch (OperatorException e) {
			if (e instanceof ProcessStoppedException) {
				Operator op = getOperator(((ProcessStoppedException) e).getOperatorName());
				ActionStatisticsCollector.getInstance().log(op, ActionStatisticsCollector.OPERATOR_EVENT_STOPPED);
			} else {
				ActionStatisticsCollector.getInstance().log(getCurrentOperator(),
						ActionStatisticsCollector.OPERATOR_EVENT_FAILURE);
				if (e instanceof UserError) {
					ActionStatisticsCollector.getInstance().log(((UserError) e).getOperator(),
							ActionStatisticsCollector.OPERATOR_EVENT_USER_ERROR);
				} else {
					ActionStatisticsCollector.getInstance().log(getCurrentOperator(),
							ActionStatisticsCollector.OPERATOR_EVENT_OPERATOR_EXCEPTION);
				}
			}
			throw e;
		} finally {
			finishProcess(logHandler);
		}
	}

	/** The last thrown exception during context loading */
	private Exception lastInitException;

	/**
	 * Sets the last thrown exception that occurred during context data loading. This is necessary,
	 * since the {@link ProgressThread#startAndWait()} method does not throw exceptions from the run
	 * method of the {@link ProgressThread}.
	 *
	 * @param e
	 *            the exception that was thrown
	 */
	private void setLastInitException(Exception e) {
		this.lastInitException = e;
	}

	/**
	 * Finishes the process and cleans up everything, including GUI.
	 *
	 * @since 7.4
	 */
	private void finishProcess(Handler logHandler) {
		stop();
		tearDown();
		if (logHandler != null) {
			getLogger().removeHandler(logHandler);
			logHandler.close();
		}
		ActionStatisticsCollector.getInstance().logExecutionFinished(this);
	}

	/** This method is invoked after a process has finished. */
	private void tearDown() {
		try {
			rootOperator.processFinished();
		} catch (OperatorException e) {
			getLogger().log(Level.WARNING, "Problem during finishing the process: " + e.getMessage(), e);
		}

		// clean up
		// clearMacros();
		clearReportStreams();
		clearStorage();
		clearUnknownParameters();
		ResultService.close();
	}

	// ----------------------
	// Process IO
	// ----------------------

    /**
     * Gets encoding.
     *
     * @param encoding the encoding
     * @return the encoding
     */
    public static Charset getEncoding(String encoding) {
		if (encoding == null) {
			encoding = ParameterService.getParameterValue(RapidMiner.PROPERTY_RAPIDMINER_GENERAL_DEFAULT_ENCODING);
			if (encoding == null || encoding.trim().length() == 0) {
				encoding = RapidMiner.SYSTEM_ENCODING_NAME;
			}
		}

		Charset result = null;
		if (RapidMiner.SYSTEM_ENCODING_NAME.equals(encoding)) {
			result = Charset.defaultCharset();
		} else {
			try {
				result = Charset.forName(encoding);
			} catch (IllegalCharsetNameException e) {
				result = Charset.defaultCharset();
			} catch (UnsupportedCharsetException e) {
				result = Charset.defaultCharset();
			} catch (IllegalArgumentException e) {
				result = Charset.defaultCharset();
			}
		}
		return result;
	}

    /**
     * Saves the process to the process file.  @throws IOException the io exception
     *
     * @throws IOException the io exception
     */
    public void save() throws IOException {
		try {
			Process.checkIfSavable(this);
		} catch (Exception e) {
			throw new IOException(e.getMessage());
		}
		if (processLocation != null) {
			this.isProcessConverted = false;
			processLocation.store(this, null);
		} else {
			throw new IOException("No process location is specified.");
		}
	}

    /**
     * Saves the process to the given process file.  @param file the file
     *
     * @param file the file
     * @throws IOException the io exception
     */
    public void save(final File file) throws IOException {
		try {
			Process.checkIfSavable(this);
		} catch (Exception e) {
			throw new IOException(e.getMessage());
		}
		new FileProcessLocation(file).store(this, null);
	}

    /**
     * Resolves the given filename against the directory containing the process file.
     *
     * @param name the name
     * @return the file
     */
    public File resolveFileName(final String name) {
		File absolute = new File(name);
		if (absolute.isAbsolute()) {
			return absolute;
		}
		if (processLocation instanceof FileProcessLocation) {
			File processFile = ((FileProcessLocation) processLocation).getFile();
			return Tools.getFile(processFile.getParentFile(), name);
		} else {
			String homeName = System.getProperty("user.home");
			if (homeName != null) {
				File file = new File(new File(homeName), name);
				getLogger().warning("Process not attached to a file. Resolving against user directory: '" + file + "'.");
				return file;
			} else {
				getLogger().warning("Process not attached to a file. Trying abolute filename '" + name + "'.");
				return new File(name);
			}
		}

	}

    /**
     * Reads the process setup from the given input stream.  @param in the in
     *
     * @param in the in
     * @throws XMLException the xml exception
     * @throws IOException  the io exception
     */
    public void readProcess(final Reader in) throws XMLException, IOException {
		readProcess(in, null);
	}

    /**
     * Read process.
     *
     * @param in               the in
     * @param progressListener the progress listener
     * @throws XMLException the xml exception
     * @throws IOException  the io exception
     */
    public void readProcess(final Reader in, final ProgressListener progressListener) throws XMLException, IOException {
		Map<String, Operator> nameMapBackup = operatorNameMap;
		operatorNameMap = new HashMap<>(); // no invocation of clear (see below)

		if (progressListener != null) {
			progressListener.setTotal(120);
			progressListener.setCompleted(0);
		}
		try {
			Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(in));
			if (progressListener != null) {
				progressListener.setCompleted(20);
			}
			unknownParameterInformation.clear();
			XMLImporter xmlImporter = new XMLImporter(progressListener);
			xmlImporter.parse(document, this, unknownParameterInformation);

			nameMapBackup = operatorNameMap;
			rootOperator.clear(Port.CLEAR_ALL);
		} catch (javax.xml.parsers.ParserConfigurationException e) {
			throw new XMLException(e.toString(), e);
		} catch (SAXException e) {
			throw new XMLException("Cannot parse document: " + e.getMessage(), e);
		} finally {
			operatorNameMap = nameMapBackup; // if everything went fine -->
			// map = new map, if not -->
			// map = old map (backup)
			if (progressListener != null) {
				progressListener.complete();
			}
		}
	}

    /**
     * Returns a &quot;name (i)&quot; if name is already in use. This new name should then be used
     * as operator name.
     *
     * @param name     the name
     * @param operator the operator
     * @return the string
     */
    public String registerName(final String name, final Operator operator) {
		if (operatorNameMap.get(name) != null) {
			String baseName = name;
			int index = baseName.indexOf(" (");
			if (index >= 0) {
				baseName = baseName.substring(0, index);
			}
			int i = 2;
			while (operatorNameMap.get(baseName + " (" + i + ")") != null) {
				i++;
			}
			String newName = baseName + " (" + i + ")";
			operatorNameMap.put(newName, operator);
			return newName;
		} else {
			operatorNameMap.put(name, operator);
			return name;
		}
	}

    /**
     * This method is used for unregistering a name from the operator name map.  @param name the name
     *
     * @param name the name
     */
    public void unregisterName(final String name) {
		operatorNameMap.remove(name);
	}

    /**
     * Notify renaming.
     *
     * @param oldName the old name
     * @param newName the new name
     */
    public void notifyRenaming(final String oldName, final String newName) {
		rootOperator.notifyRenaming(oldName, newName);
	}

	@Override
	public String toString() {
		if (rootOperator == null) {
			return "empty process";
		} else {
			return "Process:" + Tools.getLineSeparator() + rootOperator.getXML(true);
		}
	}

	private final EventListenerList processSetupListeners = new EventListenerList();

	/** Delegates any changes in the ProcessContext to the root operator. */
	private final Observer<ProcessContext> delegatingContextObserver = new Observer<ProcessContext>() {

		@Override
		public void update(final Observable<ProcessContext> observable, final ProcessContext arg) {
			fireUpdate();
		}
	};
	private final Observer<Operator> delegatingOperatorObserver = new Observer<Operator>() {

		@Override
		public void update(final Observable<Operator> observable, final Operator arg) {
			fireUpdate();
		}
	};

    /**
     * Add process setup listener.
     *
     * @param listener the listener
     */
    public void addProcessSetupListener(final ProcessSetupListener listener) {
		processSetupListeners.add(ProcessSetupListener.class, listener);
	}

    /**
     * Remove process setup listener.
     *
     * @param listener the listener
     */
    public void removeProcessSetupListener(final ProcessSetupListener listener) {
		processSetupListeners.remove(ProcessSetupListener.class, listener);
	}

    /**
     * Fire operator added.
     *
     * @param operator the operator
     */
    public void fireOperatorAdded(final Operator operator) {
		for (ProcessSetupListener l : processSetupListeners.getListeners(ProcessSetupListener.class)) {
			l.operatorAdded(operator);
		}
	}

    /**
     * Fire operator changed.
     *
     * @param operator the operator
     */
    public void fireOperatorChanged(final Operator operator) {
		for (ProcessSetupListener l : processSetupListeners.getListeners(ProcessSetupListener.class)) {
			l.operatorChanged(operator);
		}
	}

    /**
     * Fire operator removed.
     *
     * @param operator             the operator
     * @param oldIndex             the old index
     * @param oldIndexAmongEnabled the old index among enabled
     */
    public void fireOperatorRemoved(final Operator operator, final int oldIndex, final int oldIndexAmongEnabled) {
		for (ProcessSetupListener l : processSetupListeners.getListeners(ProcessSetupListener.class)) {
			l.operatorRemoved(operator, oldIndex, oldIndexAmongEnabled);
		}
	}

    /**
     * Fire execution order changed.
     *
     * @param unit the unit
     */
    public void fireExecutionOrderChanged(final ExecutionUnit unit) {
		for (ProcessSetupListener l : processSetupListeners.getListeners(ProcessSetupListener.class)) {
			l.executionOrderChanged(unit);
		}
	}

    /**
     * Gets execution mode.
     *
     * @return the execution mode
     */
    public ExecutionMode getExecutionMode() {
		return executionMode;
	}

    /**
     * Sets execution mode.
     *
     * @param mode the mode
     */
    public void setExecutionMode(final ExecutionMode mode) {
		this.executionMode = mode;
	}

    /**
     * Gets debug mode.
     *
     * @return the debug mode
     */
    public DebugMode getDebugMode() {
		return debugMode;
	}

    /**
     * Sets debug mode.
     *
     * @param mode the mode
     */
    public void setDebugMode(final DebugMode mode) {
		this.debugMode = mode;
		if (mode == DebugMode.DEBUG_OFF) {
			getRootOperator().clear(Port.CLEAR_REAL_METADATA);
		}
	}

    /**
     * Resolves a repository location relative to {@link #getRepositoryLocation()}.  @param loc the loc
     *
     * @param loc the loc
     * @return the repository location
     * @throws UserError                            the user error
     * @throws MalformedRepositoryLocationException the malformed repository location exception
     */
    public RepositoryLocation resolveRepositoryLocation(final String loc)
			throws UserError, MalformedRepositoryLocationException {
		if (RepositoryLocation.isAbsolute(loc)) {
			RepositoryLocation repositoryLocation = new RepositoryLocation(loc);
			repositoryLocation.setAccessor(getRepositoryAccessor());
			return repositoryLocation;
		}
		RepositoryLocation repositoryLocation = getRepositoryLocation();
		if (repositoryLocation != null) {
			RepositoryLocation repositoryLocation2 = new RepositoryLocation(repositoryLocation.parent(), loc);
			repositoryLocation2.setAccessor(getRepositoryAccessor());
			return repositoryLocation2;
		} else {
			throw new UserError(null, 317, loc);
		}
	}

    /**
     * Turns loc into a repository location relative to {@link #getRepositoryLocation()}.  @param loc the loc
     *
     * @param loc the loc
     * @return the string
     */
    public String makeRelativeRepositoryLocation(final RepositoryLocation loc) {
		RepositoryLocation repositoryLocation = getRepositoryLocation();
		if (repositoryLocation != null) {
			return loc.makeRelative(repositoryLocation.parent());
		} else {
			return loc.getAbsoluteLocation();
		}
	}

    /**
     * Sets context.
     *
     * @param context the context
     */
    public void setContext(final ProcessContext context) {
		if (this.context != null) {
			this.context.removeObserver(delegatingContextObserver);
		}
		this.context = context;
		this.context.addObserver(delegatingContextObserver, false);
		fireUpdate();
	}

    /**
     * Gets context.
     *
     * @return the context
     */
    public ProcessContext getContext() {
		return context;
	}

    /**
     * Sets import message.
     *
     * @param importMessage the import message
     */
    public void setImportMessage(final String importMessage) {
		this.importMessage = importMessage;
	}

    /**
     * This returns true if the process has been imported and ImportRules have been applied during
     * importing. Since the backward compatibility is lost on save, one can warn by retrieving this
     * value.
     *
     * @return the boolean
     */
    public boolean isProcessConverted() {
		return isProcessConverted;
	}

    /**
     * This sets whether the process is converted.
     *
     * @param isProcessConverted the is process converted
     */
    public void setProcessConverted(final boolean isProcessConverted) {
		this.isProcessConverted = isProcessConverted;
	}

    /**
     * Returns some user readable messages generated during import by {@link XMLImporter}.
     *
     * @return the import message
     */
    public String getImportMessage() {
		return importMessage;
	}

	// process location (file/repository)

    /**
     * Returns true iff either a file or a repository location is defined.  @return the boolean
     *
     * @return the boolean
     */
    public boolean hasSaveDestination() {
		return processLocation != null;
	}

    /**
     * Returns the current process file.
     *
     * @return the experiment file
     * @deprecated Use {@link #getProcessFile()} instead
     */
    @Deprecated
	public File getExperimentFile() {
		return getProcessFile();
	}

    /**
     * Returns the current process file.
     *
     * @return the process file
     * @deprecated Use {@link #getProcessLocation()}
     */
    @Deprecated
	public File getProcessFile() {
		if (processLocation instanceof FileProcessLocation) {
			return ((FileProcessLocation) processLocation).getFile();
		} else {
			return null;
		}
	}

    /**
     * Sets the process file. This file might be used for resolving relative filenames.
     *
     * @param file the file
     * @deprecated Please use {@link #setProcessFile(File)} instead.
     */
    @Deprecated
	public void setExperimentFile(final File file) {
		setProcessLocation(new FileProcessLocation(file));
	}

    /**
     * Sets the process file. This file might be used for resolving relative filenames.  @param file the file
     *
     * @param file the file
     */
    public void setProcessFile(final File file) {
		setProcessLocation(new FileProcessLocation(file));
	}

    /**
     * Sets process location.
     *
     * @param processLocation the process location
     */
    public void setProcessLocation(final ProcessLocation processLocation) {
		// keep process file version if same file, otherwise overwrite
		if (this.processLocation != null && !this.processLocation.equals(processLocation)) {
			this.isProcessConverted = false;
			getLogger().info("Decoupling process from location " + this.processLocation
					+ ". Process is now associated with file " + processLocation + ".");
		}
		this.processLocation = processLocation;
		fireUpdate();
	}

    /**
     * Gets process location.
     *
     * @return the process location
     */
    public ProcessLocation getProcessLocation() {
		return this.processLocation;
	}

    /**
     * Gets repository location.
     *
     * @return the repository location
     */
    public RepositoryLocation getRepositoryLocation() {
		if (processLocation instanceof RepositoryProcessLocation) {
			return ((RepositoryProcessLocation) processLocation).getRepositoryLocation();
		} else {
			return null;
		}
	}

    /**
     * Is omitting null results boolean.
     *
     * @return if <code>false</code> is returned, the {@link IOContainer} returned by {@link #run()}         will contain <code>null</code> results instead of just omitting them.
     */
    public boolean isOmittingNullResults() {
		return omitNullResults;
	}

    /**
     * If set to <code>false</code> the {@link IOContainer} returned by {@link #run()} will contain
     * <code>null</code> results instead of omitting them. By default this is <code>true</code>.
     *
     * @param omitNullResults the omit null results
     */
    public void setOmitNullResults(boolean omitNullResults) {
		this.omitNullResults = omitNullResults;
	}

    /**
     * Can be called by GUI components if visual representation or any other state not known to the
     * process itself has changed.
     */
    public void updateNotify() {
		fireUpdate(this);
	}

    /**
     * Gets repository accessor.
     *
     * @return the repository accessor
     */
    public RepositoryAccessor getRepositoryAccessor() {
		return repositoryAccessor;
	}

    /**
     * Sets repository accessor.
     *
     * @param repositoryAccessor the repository accessor
     */
    public void setRepositoryAccessor(final RepositoryAccessor repositoryAccessor) {
		this.repositoryAccessor = repositoryAccessor;
	}

    /**
     * Gets annotations.
     *
     * @return the annotations
     */
    public Annotations getAnnotations() {
		return annotations;
	}

    /**
     * Indicates how deeply nested the current process is. The original process itself has a depth
     * of {@code 0}. If that process spawns a new one via an Execute Process operator, the depth of
     * the new one will be {@code 1}. If the new process also contains an Execute Process operator,
     * the depth will be {@code 2} and so on.
     *
     * @return the nesting depth of the current process
     */
    public int getDepth() {
		return nestingDepth;
	}

    /**
     * Sets the nesting depth of this process. See {@link #getDepth()} for details.
     *
     * @param depth the new nesting depth
     */
    public void setDepth(int depth) {
		this.nestingDepth = depth;
	}

    /**
     * Checks weather the process can be saved as is. Throws an Exception if the Process should not
     * be saved.
     *
     * @param process the process
     * @throws Exception the exception
     */
    public static void checkIfSavable(final Process process) throws Exception {
		for (Operator operator : process.getAllOperators()) {
			if (operator instanceof DummyOperator) {
				throw new Exception(
						"The process contains dummy operators. Remove all dummy operators or install all missing extensions in order to save the process.");
			}
		}
	}

}
