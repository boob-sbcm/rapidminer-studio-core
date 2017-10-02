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
package com.rapid_i.repository.wsimport;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for entryResponse complex type.
 * <p>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;complexType name="entryResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://service.web.rapidanalytics.de/}response">
 *       &lt;sequence>
 *         &lt;element name="date" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="ioObjectClassName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="latestRevision" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="location" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="size" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="user" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "entryResponse", propOrder = {
    "date",
    "ioObjectClassName",
    "latestRevision",
    "location",
    "size",
    "type",
    "user"
})
public class EntryResponse
    extends Response
{

    /**
     * The Date.
     */
    protected long date;
    /**
     * The Io object class name.
     */
    protected String ioObjectClassName;
    /**
     * The Latest revision.
     */
    protected int latestRevision;
    /**
     * The Location.
     */
    protected String location;
    /**
     * The Size.
     */
    protected int size;
    /**
     * The Type.
     */
    protected String type;
    /**
     * The User.
     */
    protected String user;

    /**
     * Gets the value of the date property.
     *
     * @return the date
     */
    public long getDate() {
        return date;
    }

    /**
     * Sets the value of the date property.
     *
     * @param value the value
     */
    public void setDate(long value) {
        this.date = value;
    }

    /**
     * Gets the value of the ioObjectClassName property.
     *
     * @return possible object is     {@link String }
     */
    public String getIoObjectClassName() {
        return ioObjectClassName;
    }

    /**
     * Sets the value of the ioObjectClassName property.
     *
     * @param value allowed object is     {@link String }
     */
    public void setIoObjectClassName(String value) {
        this.ioObjectClassName = value;
    }

    /**
     * Gets the value of the latestRevision property.
     *
     * @return the latest revision
     */
    public int getLatestRevision() {
        return latestRevision;
    }

    /**
     * Sets the value of the latestRevision property.
     *
     * @param value the value
     */
    public void setLatestRevision(int value) {
        this.latestRevision = value;
    }

    /**
     * Gets the value of the location property.
     *
     * @return possible object is     {@link String }
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the value of the location property.
     *
     * @param value allowed object is     {@link String }
     */
    public void setLocation(String value) {
        this.location = value;
    }

    /**
     * Gets the value of the size property.
     *
     * @return the size
     */
    public int getSize() {
        return size;
    }

    /**
     * Sets the value of the size property.
     *
     * @param value the value
     */
    public void setSize(int value) {
        this.size = value;
    }

    /**
     * Gets the value of the type property.
     *
     * @return possible object is     {@link String }
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     *
     * @param value allowed object is     {@link String }
     */
    public void setType(String value) {
        this.type = value;
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

}
