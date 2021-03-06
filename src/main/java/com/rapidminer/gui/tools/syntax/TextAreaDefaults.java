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
package com.rapidminer.gui.tools.syntax;

/*
 * TextAreaDefaults.java - Encapsulates default values for various settings Copyright (C) 1999 Slava
 * Pestov
 * 
 * You may use and modify this package for any purpose. Redistribution is permitted, in both source
 * and binary form, provided that this notice remains intact in all source distributions of this
 * package.
 */

import javax.swing.*;
import java.awt.*;


/**
 * Encapsulates default settings for a text area. This can be passed to the constructor once the
 * necessary fields have been filled out. The advantage of doing this over calling lots of set()
 * methods after creating the text area is that this method is faster.
 * <p>
 * Important bug fix: static defaults with 1 (!) document no longer used!
 *
 * @author Slava Pestov, Tom Bradford, Ingo Mierswa
 */
public class TextAreaDefaults {

    /**
     * The Input handler.
     */
    public InputHandler inputHandler;

    /**
     * The Document.
     */
    public SyntaxDocument document;

    /**
     * The Editable.
     */
    public boolean editable;

    /**
     * The Caret visible.
     */
    public boolean caretVisible;

    /**
     * The Caret blinks.
     */
    public boolean caretBlinks;

    /**
     * The Block caret.
     */
    public boolean blockCaret;

    /**
     * The Electric scroll.
     */
    public int electricScroll;

    /**
     * The Cols.
     */
    public int cols;

    /**
     * The Rows.
     */
    public int rows;

    /**
     * The Styles.
     */
    public SyntaxStyle[] styles;

    /**
     * The Caret color.
     */
    public Color caretColor;

    /**
     * The Selection color.
     */
    public Color selectionColor;

    /**
     * The Line highlight color.
     */
    public Color lineHighlightColor;

    /**
     * The Line highlight.
     */
    public boolean lineHighlight;

    /**
     * The Bracket highlight color.
     */
    public Color bracketHighlightColor;

    /**
     * The Bracket highlight.
     */
    public boolean bracketHighlight;

    /**
     * The Eol marker color.
     */
    public Color eolMarkerColor;

    /**
     * The Eol markers.
     */
    public boolean eolMarkers;

    /**
     * The Paint invalid.
     */
    public boolean paintInvalid;

    /**
     * The Popup.
     */
    public JPopupMenu popup;

    /**
     * Returns a new TextAreaDefaults object with the default values filled in.
     *
     * @return the defaults
     */
    public static TextAreaDefaults getDefaults() {
		TextAreaDefaults defaults = new TextAreaDefaults();

		defaults.inputHandler = new DefaultInputHandler();
		defaults.inputHandler.addDefaultKeyBindings();
		defaults.document = new SyntaxDocument();
		defaults.editable = true;

		defaults.caretVisible = true;
		defaults.caretBlinks = true;
		defaults.electricScroll = 3;

		defaults.cols = 80;
		defaults.rows = 25;
		defaults.styles = SyntaxUtilities.getDefaultSyntaxStyles();
		defaults.caretColor = Color.red;
		defaults.selectionColor = new Color(0xccccff);
		defaults.lineHighlightColor = new Color(0xe0e0e0);
		defaults.lineHighlight = true;
		defaults.bracketHighlightColor = Color.black;
		defaults.bracketHighlight = true;
		defaults.eolMarkerColor = new Color(0x009999);
		defaults.eolMarkers = true;
		defaults.paintInvalid = true;

		return defaults;
	}
}
