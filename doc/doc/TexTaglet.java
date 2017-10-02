/*
 * Copyright (C) 2001-2014 RapidMiner GmbH
 */
package com.rapidminer.doc;

import com.sun.javadoc.Tag;
import com.sun.tools.doclets.Taglet;

/**
 * Creates the LaTeX code from a html taglet.
 *
 * @author Simon Fischer
 */
public interface TexTaglet extends Taglet {

    /**
     * To tex string.
     *
     * @param tag the tag
     * @return the string
     */
    public String toTex(Tag tag);

    /**
     * To tex string.
     *
     * @param tag the tag
     * @return the string
     */
    public String toTex(Tag[] tag);

}
