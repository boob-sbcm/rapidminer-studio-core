/*
 * Copyright (C) 2001-2014 RapidMiner GmbH
 */
package com.rapidminer.doc;

import com.sun.javadoc.Tag;
import com.sun.tools.doclets.Taglet;

import java.util.Map;

/**
 * A taglet with name &quot;@rapidminer.ref&quot; can be used in the Javadoc comments of an operator to produce textual
 * references. Example: &quot;@rapidminer.ref figure1|A figure for this&quot;. This will include a LaTeX reference to your
 * documentation.
 *
 * @author Simon Fischer, Ingo Mierswa
 */
public class RefTaglet implements TexTaglet {

	private static final String NAME = "rapidminer.ref";

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
		return NAME;
	}

    /**
     * In field boolean.
     *
     * @return the boolean
     */
    public boolean inField() {
		return true;
	}

    /**
     * In constructor boolean.
     *
     * @return the boolean
     */
    public boolean inConstructor() {
		return true;
	}

    /**
     * In method boolean.
     *
     * @return the boolean
     */
    public boolean inMethod() {
		return true;
	}

    /**
     * In overview boolean.
     *
     * @return the boolean
     */
    public boolean inOverview() {
		return true;
	}

    /**
     * In package boolean.
     *
     * @return the boolean
     */
    public boolean inPackage() {
		return true;
	}

    /**
     * In type boolean.
     *
     * @return the boolean
     */
    public boolean inType() {
		return true;
	}

    /**
     * Is inline tag boolean.
     *
     * @return the boolean
     */
    public boolean isInlineTag() {
		return true;
	}

    /**
     * Register.
     *
     * @param tagletMap the taglet map
     */
    public static void register(Map<String, Taglet> tagletMap) {
		RefTaglet tag = new RefTaglet();
		Taglet t = tagletMap.get(tag.getName());
		if (t != null) {
			tagletMap.remove(tag.getName());
		}
		tagletMap.put(tag.getName(), tag);
	}

	private String[] split(Tag tag) {
		String[] splitted = tag.text().split("\\|");
		if (splitted.length != 2) {
			System.err.println("Usage: {@" + getName() + " latexref|html_human_readable_ref} (" + tag.position() + ")");
			return new String[] { tag.text(), tag.text() };
		} else {
			return splitted;
		}
	}

    /**
     * To string string.
     *
     * @param tag the tag
     * @return the string
     */
    public String toString(Tag tag) {
		return split(tag)[1];
	}

    /**
     * To string string.
     *
     * @param tags the tags
     * @return the string
     */
    public String toString(Tag[] tags) {
		return null;
	}

    /**
     * To tex string.
     *
     * @param tag the tag
     * @return the string
     */
    public String toTex(Tag tag) {
		return "\\ref{" + split(tag)[0] + "}";
	}

    /**
     * To tex string.
     *
     * @param tag the tag
     * @return the string
     */
    public String toTex(Tag[] tag) {
		return null;
	}
}
