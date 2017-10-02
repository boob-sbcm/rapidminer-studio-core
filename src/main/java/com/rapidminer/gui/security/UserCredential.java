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
package com.rapidminer.gui.security;

import java.net.PasswordAuthentication;


/**
 * The user credentials stored in a {@link Wallet}. Each username belongs to one URL.
 *
 * @author Miguel Buescher
 */
public class UserCredential {

	private String url;
	private String user;
	private char[] password;

    /**
     * Instantiates a new User credential.
     *
     * @param url      the url
     * @param user     the user
     * @param password the password
     */
    public UserCredential(String url, String user, char[] password) {
		super();
		this.url = url;
		this.user = user;
		this.password = password;
	}

    /**
     * Sets user.
     *
     * @param user the user
     */
    public void setUser(String user) {
		this.user = user;
	}

    /**
     * Sets password.
     *
     * @param password the password
     */
    public void setPassword(char[] password) {
		this.password = password;
	}

    /**
     * Gets url.
     *
     * @return the url
     */
    public String getURL() {
		return url;
	}

    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUsername() {
		return user;
	}

    /**
     * Get password char [ ].
     *
     * @return the char [ ]
     */
    public char[] getPassword() {
		return password;
	}

    /**
     * Make password authentication password authentication.
     *
     * @return the password authentication
     */
    public PasswordAuthentication makePasswordAuthentication() {
		return new PasswordAuthentication(getUsername(), getPassword());
	}

	@Override
	public UserCredential clone() {
		return new UserCredential(getURL(), getUsername(), getPassword());
	}
}
