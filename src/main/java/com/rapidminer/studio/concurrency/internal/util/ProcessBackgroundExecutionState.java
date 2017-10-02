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
package com.rapidminer.studio.concurrency.internal.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.rapidminer.LoggingListener;
import com.rapidminer.Process;
import com.rapidminer.ProcessListener;
import com.rapidminer.datatable.DataTable;
import com.rapidminer.operator.IOContainer;
import com.rapidminer.operator.IOObject;
import com.rapidminer.operator.Operator;
import com.rapidminer.operator.OperatorException;
import com.rapidminer.tools.SimpleObservable;
import com.rapidminer.tools.Tools;


/**
 * This class implements listener interfaces to keep track of the execution of a process in the
 * background and to allow the gui to access and display the state.
 * <p>
 * Note that this part of the API is only temporary and might be removed in future versions again.
 * </p>
 *
 * @author Sebastian Land
 * @since 7.4
 */
public class ProcessBackgroundExecutionState extends SimpleObservable<ProcessBackgroundExecutionState.State> {

    /**
     * The enum State.
     */
    public enum State {
        /**
         * Failed state.
         */
        FAILED, /**
         * Canceled state.
         */
        CANCELED, /**
         * Pending state.
         */
        PENDING, /**
         * Running state.
         */
        RUNNING, /**
         * Finished state.
         */
        FINISHED;
	}

	private LinkedList<ProcessExecutionStackEntry> operatorStack = new LinkedList<>();
	private LinkedList<DataTable> processLogs = new LinkedList<>();

	private ProcessListener processListener;
	private LoggingListener loggingListener;

	private State state = State.PENDING;
	private Future<IOContainer> futureResults;
	private List<IOObject> results;
	private Path logFilePath;
	private Process process;

    /**
     * Instantiates a new Process background execution state.
     *
     * @param process the process
     */
    public ProcessBackgroundExecutionState(Process process) {
		this.process = process;
		// adding listeners

		processListener = new ProcessListener() {

			@Override
			public void processStarts(Process process) {
				ProcessBackgroundExecutionState.this.setState(State.RUNNING);
			}

			@Override
			public void processStartedOperator(Process process, Operator op) {
				operatorStack.push(new ProcessExecutionStackEntry(op));
			}

			@Override
			public void processFinishedOperator(Process process, Operator op) {
				operatorStack.pop();
			}

			@Override
			public void processEnded(Process process) {
				operatorStack.clear();
				if (state != State.CANCELED) {
					ProcessBackgroundExecutionState.this.setState(State.FINISHED);
				}
			}
		};
		loggingListener = new LoggingListener() {

			@Override
			public void addDataTable(DataTable dataTable) {
				processLogs.add(dataTable);
			}

			@Override
			public void removeDataTable(DataTable dataTable) {
				processLogs.remove(dataTable);
			}
		};
		this.process.getRootOperator().addProcessListener(processListener);
		this.process.addLoggingListener(loggingListener);
	}

    /**
     * Is started boolean.
     *
     * @return the boolean
     */
    public boolean isStarted() {
		return state != State.PENDING;
	}

    /**
     * Is running boolean.
     *
     * @return the boolean
     */
    public boolean isRunning() {
		return state == State.RUNNING;
	}

    /**
     * Is ended boolean.
     *
     * @return the boolean
     */
    public boolean isEnded() {
		// Hack to move to finish state, even if listener was not informed
		if (operatorStack.isEmpty() && getResults() != null) {
			this.setState(State.FINISHED);
		}
		return state == State.FINISHED;
	}

    /**
     * This returns the reference on the current operator stack. Do not modify!
     *
     * @return the stack
     */
    public LinkedList<ProcessExecutionStackEntry> getStack() {
		return operatorStack;
	}

    /**
     * Sets results.
     *
     * @param futureResults the future results
     */
    public void setResults(Future<IOContainer> futureResults) {
		this.futureResults = futureResults;
	}

    /**
     * Sets log file path.
     *
     * @param logFile the log file
     */
    public void setLogFilePath(Path logFile) {
		this.logFilePath = logFile;
	}

    /**
     * Returns the process console log contents.
     *
     * @return the log as a string or {@code null} if the log cannot be read from its temp file.
     */
    public String getLogContent() {
		try (InputStream is = Files.newInputStream(logFilePath)) {
			return Tools.readTextFile(is);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

    /**
     * This returns the results or null if they are still to be computed.
     *
     * @return results results
     * @throws OperatorException
     */
    public List<IOObject> getResults() {
		if (futureResults != null && futureResults.isDone()) {
			try {
				if (results == null) {
					results = futureResults.get().asList();
				}
				return results;
			} catch (InterruptedException e) {
			} catch (ExecutionException e) {
			}
		}
		return null;
	}

    /**
     * This returns the exception that has been thrown during runtime or null if not present or
     * still executing.
     *
     * @return exception exception
     */
    public Throwable getException() {
		try {
			if (futureResults != null && futureResults.isDone()) {
				futureResults.get();
			}
		} catch (ExecutionException e) {
			return e.getCause();
		} catch (InterruptedException e) {
		}
		return null;
	}

    /**
     * Is failed boolean.
     *
     * @return the boolean
     */
    public boolean isFailed() {
		if (state != State.FAILED && state != State.CANCELED) {
			if (getException() != null) {
				this.setState(State.FAILED);
			}
		}
		return state == State.FAILED;
	}

    /**
     * Sets failed.
     */
    public void setFailed() {
		this.setState(State.FAILED);
	}

    /**
     * Is stopped boolean.
     *
     * @return the boolean
     */
    public boolean isStopped() {
		return state == State.CANCELED;
	}

    /**
     * Sets stopped.
     */
    public void setStopped() {
		this.setState(State.CANCELED);
	}

    /**
     * Gets process logs.
     *
     * @return the process logs
     */
    public List<DataTable> getProcessLogs() {
		return processLogs;
	}

    /**
     * Gets state.
     *
     * @return the state
     */
    public State getState() {
		isFailed();
		isEnded();
		return state;
	}

    /**
     * Sets the current state
     *
     * @param newState the new state
     * @return true if the state has changed
     */
    public boolean setState(State newState) {
		boolean changed = false;
		if (state != newState) {
			state = newState;
			if (state == State.FINISHED || state == State.CANCELED || state == State.FAILED) {
				cleanup();
			}
			changed = true;
			this.fireUpdate(newState);
		}
		return changed;
	}

	/**
	 * Clean up by removing listeners and reference to process. Call when the state is in any final
	 * state.
	 */
	private void cleanup() {
		this.process.getRootOperator().removeProcessListener(processListener);
		this.process.removeLoggingListener(loggingListener);
		this.process = null;
	}
}
