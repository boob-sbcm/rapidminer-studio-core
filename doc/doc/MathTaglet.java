/*
 * Copyright (C) 2001-2014 RapidMiner GmbH
 */
package com.rapidminer.doc;

import java.util.Map;

import com.sun.javadoc.Tag;
import com.sun.tools.doclets.Taglet;

/**
 * A taglet with name &quot;@rapidminer.math&quot; can be used in the Javadoc comments of an operator to produce mathematical
 * code. Example: &quot;@rapidminer.math 1/(n+1)&quot;. This will include a LaTeX math environment to you documentation.
 *
 * @author Simon Fischer, Ingo Mierswa
 */
public class MathTaglet implements TexTaglet {

	private static final String NAME = "rapidminer.math";

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
		MathTaglet tag = new MathTaglet();
		Taglet t = tagletMap.get(tag.getName());
		if (t != null) {
			tagletMap.remove(tag.getName());
		}
		tagletMap.put(tag.getName(), tag);
	}

    /**
     * To string string.
     *
     * @param tag the tag
     * @return the string
     */
    public String toString(Tag tag) {
		return "<i>" + tag.text() + "</i>";
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
		return "$" + tag.text() + "$";
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
