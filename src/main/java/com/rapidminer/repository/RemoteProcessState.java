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
 * The enum Remote process state.
 *
 * @author Simon Fischer
 */
public enum RemoteProcessState {
    /**
     * Pending remote process state.
     */
    PENDING(false, false, "clock_run.png"), /**
     * Running remote process state.
     */
    RUNNING(false, false, "media_play.png"), /**
     * Completed remote process state.
     */
    COMPLETED(true, true, "check.png"), /**
     * Failed remote process state.
     */
    FAILED(
			true, false, "error.png"), /**
     * Stopped remote process state.
     */
    STOPPED(true, false, "media_stop.png"), /**
     * Zombie remote process state.
     */
    ZOMBIE(true, false, "skull.png");

	// STOP_REQUESTED(false, "media_stop.png");

	private String iconName;
	private boolean terminated;
	private boolean successful;

	private RemoteProcessState(boolean terminated, boolean successful, String iconName) {
		this.terminated = terminated;
		this.successful = successful;
		this.iconName = iconName;
	}

    /**
     * Is terminated boolean.
     *
     * @return the boolean
     */
    public boolean isTerminated() {
		return terminated;
	}

    /**
     * Gets icon name.
     *
     * @return the icon name
     */
    public String getIconName() {
		return iconName;
	}

    /**
     * Is successful boolean.
     *
     * @return the boolean
     */
    public boolean isSuccessful() {
		return successful;
	}
}
