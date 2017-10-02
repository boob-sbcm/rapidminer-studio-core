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
package com.rapid_i.repository.wsimport.mgt;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for createDBConnection complex type.
 * <p>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;complexType name="createDBConnection">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="host" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="port" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="user" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pwd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="schema" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="system" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="permittedGroups" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "createDBConnection", propOrder = {
    "name",
    "host",
    "port",
    "user",
    "pwd",
    "schema",
    "system",
    "permittedGroups"
})
public class CreateDBConnection {

    /**
     * The Name.
     */
    protected String name;
    /**
     * The Host.
     */
    protected String host;
    /**
     * The Port.
     */
    protected String port;
    /**
     * The User.
     */
    protected String user;
    /**
     * The Pwd.
     */
    protected String pwd;
    /**
     * The Schema.
     */
    protected String schema;
    /**
     * The System.
     */
    protected String system;
    /**
     * The Permitted groups.
     */
    protected List<String> permittedGroups;

    /**
     * Gets the value of the name property.
     *
     * @return possible object is     {@link String }
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     *
     * @param value allowed object is     {@link String }
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the host property.
     *
     * @return possible object is     {@link String }
     */
    public String getHost() {
        return host;
    }

    /**
     * Sets the value of the host property.
     *
     * @param value allowed object is     {@link String }
     */
    public void setHost(String value) {
        this.host = value;
    }

    /**
     * Gets the value of the port property.
     *
     * @return possible object is     {@link String }
     */
    public String getPort() {
        return port;
    }

    /**
     * Sets the value of the port property.
     *
     * @param value allowed object is     {@link String }
     */
    public void setPort(String value) {
        this.port = value;
    }

    /**
     * Gets the value of the user property.
     *
     * @return possible object is     {@link String }
     */
    public String getUser() {
        return user;
    }

    /**
     * Sets the value of the user property.
     *
     * @param value allowed object is     {@link String }
     */
    public void setUser(String value) {
        this.user = value;
    }

    /**
     * Gets the value of the pwd property.
     *
     * @return possible object is     {@link String }
     */
    public String getPwd() {
        return pwd;
    }

    /**
     * Sets the value of the pwd property.
     *
     * @param value allowed object is     {@link String }
     */
    public void setPwd(String value) {
        this.pwd = value;
    }

    /**
     * Gets the value of the schema property.
     *
     * @return possible object is     {@link String }
     */
    public String getSchema() {
        return schema;
    }

    /**
     * Sets the value of the schema property.
     *
     * @param value allowed object is     {@link String }
     */
    public void setSchema(String value) {
        this.schema = value;
    }

    /**
     * Gets the value of the system property.
     *
     * @return possible object is     {@link String }
     */
    public String getSystem() {
        return system;
    }

    /**
     * Sets the value of the system property.
     *
     * @param value allowed object is     {@link String }
     */
    public void setSystem(String value) {
        this.system = value;
    }

    /**
     * Gets the value of the permittedGroups property.
     * <p>
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the permittedGroups property.
     * <p>
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPermittedGroups().add(newItem);
     * </pre>
     * <p>
     * <p>
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     *
     * @return the permitted groups
     */
    public List<String> getPermittedGroups() {
        if (permittedGroups == null) {
            permittedGroups = new ArrayList<String>();
        }
        return this.permittedGroups;
    }

}
