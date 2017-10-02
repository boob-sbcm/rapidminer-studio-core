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
package com.rapidminer.operator;

import com.rapidminer.example.Attribute;
import com.rapidminer.gui.viewer.MetaDataViewerTableModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 * Instances of this class can be used to annotate {@link IOObject}s, {@link Attribute}s, etc.
 *
 * @author Simon Fischer, Marius Helf
 */
public class Annotations implements Serializable, Map<String, String>, Cloneable {

	private static final long serialVersionUID = 1L;

    /**
     * The constant ANNOTATIONS_TAG_NAME.
     */
    public static final String ANNOTATIONS_TAG_NAME = "annotations";

	// for IOObjects

    /**
     * Source, e.g. URI, or SQL query of data.
     */
    public static final String KEY_SOURCE = "Source";

    /**
     * The constant KEY_FILENAME.
     */
    public static final String KEY_FILENAME = "Filename";

    /**
     * User defined comment.
     */
    public static final String KEY_COMMENT = "Comment";

	// for Attribtues

    /**
     * Physical unit of attributes.
     */
    public static final String KEY_UNIT = "Unit";

    /**
     * Colors for attribute values.
     */
    public static final String KEY_COLOR_MAP = "Colors";

	// Dublin Core

    /**
     * The constant KEY_DC_AUTHOR.
     */
    public static final String KEY_DC_AUTHOR = "dc.author";
    /**
     * The constant KEY_DC_TITLE.
     */
    public static final String KEY_DC_TITLE = "dc.title";
    /**
     * The constant KEY_DC_SUBJECT.
     */
    public static final String KEY_DC_SUBJECT = "dc.subject";
    /**
     * The constant KEY_DC_COVERAGE.
     */
    public static final String KEY_DC_COVERAGE = "dc.coverage";
    /**
     * The constant KEY_DC_DESCRIPTION.
     */
    public static final String KEY_DC_DESCRIPTION = "dc.description";
    /**
     * The constant KEY_DC_CREATOR.
     */
    public static final String KEY_DC_CREATOR = "dc.creator";
    /**
     * The constant KEY_DC_PUBLISHER.
     */
    public static final String KEY_DC_PUBLISHER = "dc.publisher";
    /**
     * The constant KEY_DC_CONTRIBUTOR.
     */
    public static final String KEY_DC_CONTRIBUTOR = "dc.contributor";
    /**
     * The constant KEY_DC_RIGHTS_HOLDER.
     */
    public static final String KEY_DC_RIGHTS_HOLDER = "dc.rightsHolder";
    /**
     * The constant KEY_DC_RIGHTS.
     */
    public static final String KEY_DC_RIGHTS = "dc.rights";
    /**
     * The constant KEY_DC_PROVENANCE.
     */
    public static final String KEY_DC_PROVENANCE = "dc.provenance";
    /**
     * The constant KEY_DC_SOURCE.
     */
    public static final String KEY_DC_SOURCE = "dc.source";
    /**
     * The constant KEY_DC_RELATION.
     */
    public static final String KEY_DC_RELATION = "dc.relation";
    /**
     * The constant KEY_DC_AUDIENCE.
     */
    public static final String KEY_DC_AUDIENCE = "dc.audience";
    /**
     * The constant KEY_DC_INSTRUCTIONAL_METHOD.
     */
    public static final String KEY_DC_INSTRUCTIONAL_METHOD = "dc.description";

    /**
     * Custom keys defined by RapidMiner
     */
    public static final String[] KEYS_RAPIDMINER_IOOBJECT = { KEY_SOURCE, KEY_COMMENT };

    /**
     * Custom keys defined by the Dublin Core standard.
     */
    public static final String[] KEYS_DUBLIN_CORE = { KEY_DC_AUTHOR, KEY_DC_TITLE, KEY_DC_SUBJECT, KEY_DC_COVERAGE,
			KEY_DC_DESCRIPTION, KEY_DC_CREATOR, KEY_DC_PUBLISHER, KEY_DC_CONTRIBUTOR, KEY_DC_RIGHTS_HOLDER, KEY_DC_RIGHTS,
			KEY_DC_PROVENANCE, KEY_DC_SOURCE, KEY_DC_RELATION, KEY_DC_AUDIENCE, KEY_DC_INSTRUCTIONAL_METHOD, };

    /**
     * All keys that are supposed to be used with {@link IOObject}s.
     */
    public static final String[] ALL_KEYS_IOOBJECT = { KEY_SOURCE, KEY_COMMENT, KEY_FILENAME,

	KEY_DC_AUTHOR, KEY_DC_TITLE, KEY_DC_SUBJECT, KEY_DC_COVERAGE, KEY_DC_DESCRIPTION, KEY_DC_CREATOR, KEY_DC_PUBLISHER,
			KEY_DC_CONTRIBUTOR, KEY_DC_RIGHTS_HOLDER, KEY_DC_RIGHTS, KEY_DC_PROVENANCE, KEY_DC_SOURCE, KEY_DC_RELATION,
			KEY_DC_AUDIENCE, KEY_DC_INSTRUCTIONAL_METHOD,

	};

    /**
     * Keys that can be assigned to {@link Attribute}s. If you extend this list, also extend
     * {@link MetaDataViewerTableModel#COLUMN_NAMES}.
     */
    public static final String[] ALL_KEYS_ATTRIBUTE = { KEY_COMMENT, KEY_UNIT };

	private LinkedHashMap<String, String> keyValueMap = new LinkedHashMap<String, String>();

    /**
     * Pseudo-annotation to be used for attribute names.
     */
    public static final String ANNOTATION_NAME = "Name";

    /**
     * Instantiates a new Annotations.
     */
    public Annotations() {}

    /**
     * Clone constructor.
     *
     * @param annotations the annotations
     */
    public Annotations(Annotations annotations) {
		this.keyValueMap = new LinkedHashMap<String, String>(annotations.keyValueMap);
	}

    /**
     * Sets annotation.
     *
     * @param key   the key
     * @param value the value
     */
    public void setAnnotation(String key, String value) {
		keyValueMap.put(key, value);
	}

    /**
     * Gets annotation.
     *
     * @param key the key
     * @return the annotation
     */
    public String getAnnotation(String key) {
		return keyValueMap.get(key);
	}

    /**
     * Gets keys.
     *
     * @return the keys
     */
    public List<String> getKeys() {
		return new ArrayList<String>(keyValueMap.keySet());
	}

    /**
     * Remove annotation.
     *
     * @param key the key
     */
    public void removeAnnotation(String key) {
		keyValueMap.remove(key);
	}

	@Override
	public int size() {
		return keyValueMap.size();
	}

	@Override
	public void clear() {
		keyValueMap.clear();
	}

	@Override
	public boolean containsKey(Object key) {
		return keyValueMap.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return keyValueMap.containsValue(value);
	}

	@Override
	public Set<java.util.Map.Entry<String, String>> entrySet() {
		return keyValueMap.entrySet();
	}

	@Override
	public String get(Object key) {
		if (key instanceof String) {
			return getAnnotation((String) key);
		} else {
			return null;
		}
	}

	@Override
	public boolean isEmpty() {
		return keyValueMap.isEmpty();
	}

	@Override
	public Set<String> keySet() {
		return keyValueMap.keySet();
	}

	@Override
	public String put(String key, String value) {
		setAnnotation(key, value);
		return value;
	}

	@Override
	public void putAll(Map<? extends String, ? extends String> m) {
		keyValueMap.putAll(m);
	}

	@Override
	public String remove(Object key) {
		return keyValueMap.remove(key);
	}

	@Override
	public Collection<String> values() {
		return keyValueMap.values();
	}

	@Override
	public String toString() {
		return keyValueMap.toString();
	}

    /**
     * To xml element.
     *
     * @param doc the doc
     * @return the element
     */
    public Element toXML(Document doc) {
		Element elem = doc.createElement(ANNOTATIONS_TAG_NAME);
		for (Map.Entry<String, String> entry : keyValueMap.entrySet()) {
			addAnnotationToXML(elem, entry.getKey(), entry.getValue());
		}
		return elem;
	}

    /**
     * Updates the XML representation to contain this annotation.  @param annotationsElement the annotations element
     *
     * @param annotationsElement the annotations element
     * @param name               the name
     * @param value              the value
     */
    public static void addAnnotationToXML(Element annotationsElement, String name, String value) {
		if (value == null) {
			deleteAnnotationFromXML(annotationsElement, name);
		} else {
			// XMLTools.setTagContents(annotationsElement, name, value);
			final Document doc = annotationsElement.getOwnerDocument();
			Element elem = doc.createElement("annotation");
			annotationsElement.appendChild(elem);
			elem.setAttribute("key", name);
			elem.setTextContent(value);
		}
	}

    /**
     * Updates the XML representation removing this annotation.  @param annotationsElement the annotations element
     *
     * @param annotationsElement the annotations element
     * @param name               the name
     */
    public static void deleteAnnotationFromXML(Element annotationsElement, String name) {
		// XMLTools.deleteTagContents(annotationsElement, name);
		NodeList children = annotationsElement.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			if ((child instanceof Element) && name.equals(((Element) child).getAttribute("annotation"))) {
				annotationsElement.removeChild(child);
			}
		}
	}

    /**
     * Parse xml.
     *
     * @param annotationsElem the annotations elem
     */
    public void parseXML(Element annotationsElem) {
		NodeList children = annotationsElem.getElementsByTagName("annotation");
		for (int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			if (child instanceof Element) {
				String name = ((Element) child).getAttribute("key");
				setAnnotation(name, child.getTextContent());
			}
		}
	}

    /**
     * Gets defined annotation names.
     *
     * @return the defined annotation names
     */
    public List<String> getDefinedAnnotationNames() {
		List<String> result = new LinkedList<String>();
		result.addAll(keySet());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	protected Annotations clone() {
		return new Annotations(this);
	}

    /**
     * Copies all annotations from the input argument to this Annotations object. Existing entries
     * will be overwritten.
     *
     * @param annotations the annotations
     */
    public void addAll(Annotations annotations) {
		if (annotations != null) {
			this.keyValueMap.putAll(annotations);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Annotations)) {
			return false;
		}
		return keyValueMap.equals(((Annotations) obj).keyValueMap);
	}
}
