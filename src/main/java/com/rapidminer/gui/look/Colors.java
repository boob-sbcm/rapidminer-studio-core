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
package com.rapidminer.gui.look;

import javax.swing.UIDefaults;
import javax.swing.plaf.ColorUIResource;


/**
 * The colors used for the RapidLook look and feel.
 *
 * @author Ingo Mierswa, Marco Boeck
 */
public class Colors {

	private static final ColorUIResource INPUT_BACKGROUND = new ColorUIResource(255, 255, 255);
	private static final ColorUIResource INPUT_BACKGROUND_DARK = new ColorUIResource(240, 240, 240);
	private static final ColorUIResource INPUT_BACKGROUND_DISABLED = new ColorUIResource(254, 254, 254);
	private static final ColorUIResource SELECTION_BOX_FOREGROUND = new ColorUIResource(255, 102, 0);
	private static final ColorUIResource SELECTION_BOX_FOREGROUND_DISABLED = new ColorUIResource(155, 155, 155);

	private static final ColorUIResource INPUT_BORDER = new ColorUIResource(187, 187, 187);
	private static final ColorUIResource INPUT_BORDER_DISABLED = new ColorUIResource(227, 227, 227);
	private static final ColorUIResource INPUT_BORDER_FOCUS = new ColorUIResource(145, 145, 145);

    /**
     * The constant SELECTION_FOREGROUND.
     */
    public static final ColorUIResource SELECTION_FOREGROUND = new ColorUIResource(155, 155, 155);
    /**
     * The constant SELECTION_FOREGROUND_DISABLED.
     */
    public static final ColorUIResource SELECTION_FOREGROUND_DISABLED = new ColorUIResource(209, 208, 208);

    /**
     * The constant SPECIAL_FOREGROUND.
     */
    public static final ColorUIResource SPECIAL_FOREGROUND = SELECTION_BOX_FOREGROUND;

    /**
     * The constant WINDOW_BACKGROUND.
     */
    public static final ColorUIResource WINDOW_BACKGROUND = new ColorUIResource(250, 250, 250);
    /**
     * The constant PANEL_BACKGROUND.
     */
    public static final ColorUIResource PANEL_BACKGROUND = new ColorUIResource(233, 234, 234);
    /**
     * The constant PANEL_BORDER.
     */
    public static final ColorUIResource PANEL_BORDER = INPUT_BORDER_FOCUS;
    /**
     * The constant POPUP_BORDER.
     */
    public static final ColorUIResource POPUP_BORDER = INPUT_BORDER_FOCUS;
    /**
     * The constant PANEL_SEPARATOR.
     */
    public static final ColorUIResource PANEL_SEPARATOR = new ColorUIResource(216, 216, 216);

    /**
     * The constant TEXTFIELD_BACKGROUND.
     */
    public static final ColorUIResource TEXTFIELD_BACKGROUND = INPUT_BACKGROUND;
    /**
     * The constant TEXTFIELD_BACKGROUND_DISABLED.
     */
    public static final ColorUIResource TEXTFIELD_BACKGROUND_DISABLED = INPUT_BACKGROUND_DISABLED;
    /**
     * The constant TEXTFIELD_BORDER.
     */
    public static final ColorUIResource TEXTFIELD_BORDER = INPUT_BORDER;
    /**
     * The constant TEXTFIELD_BORDER_DISABLED.
     */
    public static final ColorUIResource TEXTFIELD_BORDER_DISABLED = INPUT_BORDER_DISABLED;
    /**
     * The constant TEXTFIELD_BORDER_FOCUS.
     */
    public static final ColorUIResource TEXTFIELD_BORDER_FOCUS = INPUT_BORDER_FOCUS;

    /**
     * The constant TEXT_FOREGROUND.
     */
    public static final ColorUIResource TEXT_FOREGROUND = new ColorUIResource(0, 0, 0);
    /**
     * The constant TEXT_HIGHLIGHT_BACKGROUND.
     */
    public static final ColorUIResource TEXT_HIGHLIGHT_BACKGROUND = new ColorUIResource(178, 215, 255);
    /**
     * The constant TEXT_HIGHLIGHT_FOREGROUND.
     */
    public static final ColorUIResource TEXT_HIGHLIGHT_FOREGROUND = new ColorUIResource(0, 0, 0);
    /**
     * The constant TEXT_LIGHT_FOREGROUND.
     */
    public static final ColorUIResource TEXT_LIGHT_FOREGROUND = new ColorUIResource(51, 51, 51);

    /**
     * The constant BUTTON_BORDER.
     */
    public static final ColorUIResource BUTTON_BORDER = new ColorUIResource(177, 177, 177);
    /**
     * The constant BUTTON_BORDER_DISABLED.
     */
    public static final ColorUIResource BUTTON_BORDER_DISABLED = new ColorUIResource(200, 200, 200);
    /**
     * The constant BUTTON_BORDER_FOCUS.
     */
    public static final ColorUIResource BUTTON_BORDER_FOCUS = INPUT_BORDER_FOCUS;
    /**
     * The constant BUTTON_BACKGROUND_GRADIENT_START.
     */
    public static final ColorUIResource BUTTON_BACKGROUND_GRADIENT_START = new ColorUIResource(240, 240, 240);
    /**
     * The constant BUTTON_BACKGROUND_GRADIENT_END.
     */
    public static final ColorUIResource BUTTON_BACKGROUND_GRADIENT_END = new ColorUIResource(218, 218, 218);
    /**
     * The constant BUTTON_BACKGROUND_ROLLOVER_GRADIENT_START.
     */
    public static final ColorUIResource BUTTON_BACKGROUND_ROLLOVER_GRADIENT_START = new ColorUIResource(225, 225, 225);
    /**
     * The constant BUTTON_BACKGROUND_ROLLOVER_GRADIENT_END.
     */
    public static final ColorUIResource BUTTON_BACKGROUND_ROLLOVER_GRADIENT_END = new ColorUIResource(213, 213, 213);
    /**
     * The constant BUTTON_BACKGROUND_PRESSED_GRADIENT_START.
     */
    public static final ColorUIResource BUTTON_BACKGROUND_PRESSED_GRADIENT_START = new ColorUIResource(198, 198, 198);
    /**
     * The constant BUTTON_BACKGROUND_PRESSED_GRADIENT_END.
     */
    public static final ColorUIResource BUTTON_BACKGROUND_PRESSED_GRADIENT_END = new ColorUIResource(230, 230, 230);
    /**
     * The constant BUTTON_BACKGROUND_DISABLED_GRADIENT_START.
     */
    public static final ColorUIResource BUTTON_BACKGROUND_DISABLED_GRADIENT_START = new ColorUIResource(210, 210, 210);
    /**
     * The constant BUTTON_BACKGROUND_DISABLED_GRADIENT_END.
     */
    public static final ColorUIResource BUTTON_BACKGROUND_DISABLED_GRADIENT_END = new ColorUIResource(210, 210, 210);

    /**
     * The constant COMBOBOX_BACKGROUND.
     */
    public static final ColorUIResource COMBOBOX_BACKGROUND = INPUT_BACKGROUND;
    /**
     * The constant COMBOBOX_BACKGROUND_DARK.
     */
    public static final ColorUIResource COMBOBOX_BACKGROUND_DARK = INPUT_BACKGROUND_DARK;
    /**
     * The constant COMBOBOX_BACKGROUND_DISABLED.
     */
    public static final ColorUIResource COMBOBOX_BACKGROUND_DISABLED = INPUT_BACKGROUND_DISABLED;
    /**
     * The constant COMBOBOX_ARROW.
     */
    public static final ColorUIResource COMBOBOX_ARROW = new ColorUIResource(75, 75, 75);
    /**
     * The constant COMBOBOX_ARROW_DISABLED.
     */
    public static final ColorUIResource COMBOBOX_ARROW_DISABLED = new ColorUIResource(175, 175, 175);
    /**
     * The constant COMBOBOX_BORDER.
     */
    public static final ColorUIResource COMBOBOX_BORDER = INPUT_BORDER;
    /**
     * The constant COMBOBOX_BORDER_DISABLED.
     */
    public static final ColorUIResource COMBOBOX_BORDER_DISABLED = INPUT_BORDER_DISABLED;
    /**
     * The constant COMBOBOX_BORDER_FOCUS.
     */
    public static final ColorUIResource COMBOBOX_BORDER_FOCUS = INPUT_BORDER_FOCUS;

    /**
     * The constant SPINNER_BACKGROUND.
     */
    public static final ColorUIResource SPINNER_BACKGROUND = INPUT_BACKGROUND;
    /**
     * The constant SPINNER_ARROW.
     */
    public static final ColorUIResource SPINNER_ARROW = new ColorUIResource(150, 150, 150);
    /**
     * The constant SPINNER_BUTTON_ROLLOVER.
     */
    public static final ColorUIResource SPINNER_BUTTON_ROLLOVER = new ColorUIResource(225, 225, 225);
    /**
     * The constant SPINNER_BUTTON_PRESSED.
     */
    public static final ColorUIResource SPINNER_BUTTON_PRESSED = new ColorUIResource(205, 205, 205);
    /**
     * The constant SPINNER_BUTTON_BACKGROUND.
     */
    public static final ColorUIResource SPINNER_BUTTON_BACKGROUND = new ColorUIResource(245, 245, 245);
    /**
     * The constant SPINNER_BUTTON_DISABLED.
     */
    public static final ColorUIResource SPINNER_BUTTON_DISABLED = new ColorUIResource(240, 240, 240);
    /**
     * The constant SPINNER_BORDER.
     */
    public static final ColorUIResource SPINNER_BORDER = TEXTFIELD_BORDER;

    /**
     * The constant SLIDER_TRACK_BACKGROUND.
     */
    public static final ColorUIResource SLIDER_TRACK_BACKGROUND = INPUT_BACKGROUND;
    /**
     * The constant SLIDER_TRACK_BACKGROUND_DISABLED.
     */
    public static final ColorUIResource SLIDER_TRACK_BACKGROUND_DISABLED = INPUT_BACKGROUND_DISABLED;
    /**
     * The constant SLIDER_TRACK_FOREGROUND.
     */
    public static final ColorUIResource SLIDER_TRACK_FOREGROUND = SELECTION_BOX_FOREGROUND;
    /**
     * The constant SLIDER_TRACK_BORDER.
     */
    public static final ColorUIResource SLIDER_TRACK_BORDER = TEXTFIELD_BORDER;
    /**
     * The constant SLIDER_THUMB_BACKGROUND.
     */
    public static final ColorUIResource SLIDER_THUMB_BACKGROUND = INPUT_BACKGROUND;
    /**
     * The constant SLIDER_THUMB_BORDER.
     */
    public static final ColorUIResource SLIDER_THUMB_BORDER = TEXTFIELD_BORDER;
    /**
     * The constant SLIDER_THUMB_BORDER_FOCUS.
     */
    public static final ColorUIResource SLIDER_THUMB_BORDER_FOCUS = TEXTFIELD_BORDER_FOCUS;

    /**
     * The constant CHECKBOX_BACKGROUND.
     */
    public static final ColorUIResource CHECKBOX_BACKGROUND = INPUT_BACKGROUND;
    /**
     * The constant CHECKBOX_BACKGROUND_DISABLED.
     */
    public static final ColorUIResource CHECKBOX_BACKGROUND_DISABLED = INPUT_BACKGROUND_DISABLED;
    /**
     * The constant CHECKBOX_BORDER.
     */
    public static final ColorUIResource CHECKBOX_BORDER = INPUT_BORDER;
    /**
     * The constant CHECKBOX_BORDER_DISABLED.
     */
    public static final ColorUIResource CHECKBOX_BORDER_DISABLED = INPUT_BORDER_DISABLED;
    /**
     * The constant CHECKBOX_BORDER_FOCUS.
     */
    public static final ColorUIResource CHECKBOX_BORDER_FOCUS = INPUT_BORDER_FOCUS;
    /**
     * The constant CHECKBOX_CHECKED.
     */
    public static final ColorUIResource CHECKBOX_CHECKED = SELECTION_BOX_FOREGROUND;
    /**
     * The constant CHECKBOX_CHECKED_DISABLED.
     */
    public static final ColorUIResource CHECKBOX_CHECKED_DISABLED = SELECTION_BOX_FOREGROUND_DISABLED;

    /**
     * The constant RADIOBUTTON_BACKGROUND.
     */
    public static final ColorUIResource RADIOBUTTON_BACKGROUND = CHECKBOX_BACKGROUND;
    /**
     * The constant RADIOBUTTON_BACKGROUND_DISABLED.
     */
    public static final ColorUIResource RADIOBUTTON_BACKGROUND_DISABLED = CHECKBOX_BACKGROUND_DISABLED;
    /**
     * The constant RADIOBUTTON_BORDER.
     */
    public static final ColorUIResource RADIOBUTTON_BORDER = CHECKBOX_BORDER;
    /**
     * The constant RADIOBUTTON_BORDER_DISABLED.
     */
    public static final ColorUIResource RADIOBUTTON_BORDER_DISABLED = CHECKBOX_BORDER_DISABLED;
    /**
     * The constant RADIOBUTTON_BORDER_FOCUS.
     */
    public static final ColorUIResource RADIOBUTTON_BORDER_FOCUS = CHECKBOX_BORDER_FOCUS;
    /**
     * The constant RADIOBUTTON_CHECKED.
     */
    public static final ColorUIResource RADIOBUTTON_CHECKED = CHECKBOX_CHECKED;
    /**
     * The constant RADIOBUTTON_CHECKED_DISABLED.
     */
    public static final ColorUIResource RADIOBUTTON_CHECKED_DISABLED = CHECKBOX_CHECKED_DISABLED;

    /**
     * The constant LINKBUTTON_LOCAL.
     */
    public static final ColorUIResource LINKBUTTON_LOCAL = new ColorUIResource(0, 0, 238);
    /**
     * The constant LINKBUTTON_REMOTE.
     */
    public static final ColorUIResource LINKBUTTON_REMOTE = LINKBUTTON_LOCAL;

    /**
     * The constant TOOLTIP_BACKGROUND.
     */
    public static final ColorUIResource TOOLTIP_BACKGROUND = new ColorUIResource(252, 252, 252);
    /**
     * The constant TOOLTIP_FOREGROUND.
     */
    public static final ColorUIResource TOOLTIP_FOREGROUND = new ColorUIResource(0, 0, 0);
    /**
     * The constant TOOLTIP_BORDER.
     */
    public static final ColorUIResource TOOLTIP_BORDER = new ColorUIResource(0, 0, 0);

    /**
     * The constant SPLITPANE_BORDER.
     */
    public static final ColorUIResource SPLITPANE_BORDER = INPUT_BORDER;
    /**
     * The constant SPLITPANE_BORDER_FOCUS.
     */
    public static final ColorUIResource SPLITPANE_BORDER_FOCUS = INPUT_BORDER_FOCUS;
    /**
     * The constant SPLITPANE_DOTS.
     */
    public static final ColorUIResource SPLITPANE_DOTS = new ColorUIResource(255, 255, 255);

    /**
     * The constant TAB_BORDER.
     */
    public static final ColorUIResource TAB_BORDER = new ColorUIResource(187, 187, 187);
    /**
     * The constant TAB_BACKGROUND.
     */
    public static final ColorUIResource TAB_BACKGROUND = new ColorUIResource(213, 213, 213);
    /**
     * The constant TAB_BACKGROUND_HIGHLIGHT.
     */
    public static final ColorUIResource TAB_BACKGROUND_HIGHLIGHT = new ColorUIResource(233, 233, 234);
    /**
     * The constant TAB_BACKGROUND_SELECTED.
     */
    public static final ColorUIResource TAB_BACKGROUND_SELECTED = TAB_BACKGROUND_HIGHLIGHT;
    /**
     * The constant TAB_CONTENT_BORDER.
     */
    public static final ColorUIResource TAB_CONTENT_BORDER = TAB_BORDER;

    /**
     * The constant CARD_PANEL_BACKGROUND.
     */
    public static final ColorUIResource CARD_PANEL_BACKGROUND = PANEL_BACKGROUND;
    /**
     * The constant CARD_PANEL_BACKGROUND_HIGHLIGHT.
     */
    public static final ColorUIResource CARD_PANEL_BACKGROUND_HIGHLIGHT = new ColorUIResource(220, 220, 220);
    /**
     * The constant CARD_PANEL_BACKGROUND_SELECTED.
     */
    public static final ColorUIResource CARD_PANEL_BACKGROUND_SELECTED = new ColorUIResource(208, 209, 209);

    /**
     * The constant SCROLLBAR_TRACK_BACKGROUND.
     */
    public static final ColorUIResource SCROLLBAR_TRACK_BACKGROUND = PANEL_BACKGROUND;
    /**
     * The constant SCROLLBAR_TRACK_BORDER.
     */
    public static final ColorUIResource SCROLLBAR_TRACK_BORDER = TAB_BORDER;
    /**
     * The constant SCROLLBAR_THUMB_BACKGROUND.
     */
    public static final ColorUIResource SCROLLBAR_THUMB_BACKGROUND = new ColorUIResource(198, 199, 199);
    /**
     * The constant SCROLLBAR_THUMB_BACKGROUND_ROLLOVER.
     */
    public static final ColorUIResource SCROLLBAR_THUMB_BACKGROUND_ROLLOVER = new ColorUIResource(188, 188, 186);
    /**
     * The constant SCROLLBAR_THUMB_BACKGROUND_PRESSED.
     */
    public static final ColorUIResource SCROLLBAR_THUMB_BACKGROUND_PRESSED = new ColorUIResource(175, 175, 175);
    /**
     * The constant SCROLLBAR_THUMB_FOREGROUND.
     */
    public static final ColorUIResource SCROLLBAR_THUMB_FOREGROUND = new ColorUIResource(151, 152, 152);
    /**
     * The constant SCROLLBAR_THUMB_BORDER.
     */
    public static final ColorUIResource SCROLLBAR_THUMB_BORDER = SCROLLBAR_TRACK_BORDER;
    /**
     * The constant SCROLLBAR_ARROW.
     */
    public static final ColorUIResource SCROLLBAR_ARROW = new ColorUIResource(98, 98, 98);
    /**
     * The constant SCROLLBAR_ARROW_PRESSED.
     */
    public static final ColorUIResource SCROLLBAR_ARROW_PRESSED = SCROLLBAR_THUMB_BACKGROUND_PRESSED;
    /**
     * The constant SCROLLBAR_ARROW_ROLLOVER.
     */
    public static final ColorUIResource SCROLLBAR_ARROW_ROLLOVER = new ColorUIResource(120, 120, 120);
    /**
     * The constant SCROLLBAR_ARROW_BORDER.
     */
    public static final ColorUIResource SCROLLBAR_ARROW_BORDER = SCROLLBAR_TRACK_BORDER;
    /**
     * The constant SCROLLBAR_ARROW_BACKGROUND.
     */
    public static final ColorUIResource SCROLLBAR_ARROW_BACKGROUND = SCROLLBAR_TRACK_BACKGROUND;

    /**
     * The constant TABLE_HEADER_BACKGROUND_GRADIENT_START.
     */
    public static final ColorUIResource TABLE_HEADER_BACKGROUND_GRADIENT_START = new ColorUIResource(231, 232, 232);
    /**
     * The constant TABLE_HEADER_BACKGROUND_GRADIENT_END.
     */
    public static final ColorUIResource TABLE_HEADER_BACKGROUND_GRADIENT_END = new ColorUIResource(225, 225, 225);
    /**
     * The constant TABLE_HEADER_BACKGROUND_FOCUS.
     */
    public static final ColorUIResource TABLE_HEADER_BACKGROUND_FOCUS = new ColorUIResource(208, 208, 208);
    /**
     * The constant TABLE_HEADER_BACKGROUND_PRESSED.
     */
    public static final ColorUIResource TABLE_HEADER_BACKGROUND_PRESSED = new ColorUIResource(193, 193, 193);
    /**
     * The constant TABLE_HEADER_BORDER.
     */
    public static final ColorUIResource TABLE_HEADER_BORDER = INPUT_BORDER;
    /**
     * The constant TABLE_CELL_BORDER.
     */
    public static final ColorUIResource TABLE_CELL_BORDER = new ColorUIResource(228, 228, 228);
    /**
     * The constant TABLE_CELL_BORDER_HIGHLIGHT.
     */
    public static final ColorUIResource TABLE_CELL_BORDER_HIGHLIGHT = new ColorUIResource(212, 212, 212);

    /**
     * The constant MENU_ITEM_SEPARATOR.
     */
    public static final ColorUIResource MENU_ITEM_SEPARATOR = new ColorUIResource(223, 224, 224);
    /**
     * The constant MENU_ITEM_BACKGROUND.
     */
    public static final ColorUIResource MENU_ITEM_BACKGROUND = new ColorUIResource(254, 254, 254);
    /**
     * The constant MENU_ITEM_BACKGROUND_SELECTED.
     */
    public static final ColorUIResource MENU_ITEM_BACKGROUND_SELECTED = TEXT_HIGHLIGHT_BACKGROUND;
    /**
     * The constant MENUBAR_BORDER.
     */
    public static final ColorUIResource MENUBAR_BORDER = INPUT_BORDER;
    /**
     * The constant MENUBAR_BORDER_HIGHLIGHT.
     */
    public static final ColorUIResource MENUBAR_BORDER_HIGHLIGHT = INPUT_BORDER_FOCUS;
    /**
     * The constant MENUBAR_BACKGROUND_HIGHLIGHT.
     */
    public static final ColorUIResource MENUBAR_BACKGROUND_HIGHLIGHT = PANEL_BACKGROUND;

    /**
     * The constant PROGRESSBAR_BACKGROUND.
     */
    public static final ColorUIResource PROGRESSBAR_BACKGROUND = new ColorUIResource(247, 247, 247);
    /**
     * The constant PROGRESSBAR_DETERMINATE_FOREGROUND_GRADIENT_START.
     */
    public static final ColorUIResource PROGRESSBAR_DETERMINATE_FOREGROUND_GRADIENT_START = new ColorUIResource(255, 153, 2);
    /**
     * The constant PROGRESSBAR_DETERMINATE_FOREGROUND_GRADIENT_END.
     */
    public static final ColorUIResource PROGRESSBAR_DETERMINATE_FOREGROUND_GRADIENT_END = new ColorUIResource(245, 131, 23);
    /**
     * The constant PROGRESSBAR_INDETERMINATE_FOREGROUND_1.
     */
    public static final ColorUIResource PROGRESSBAR_INDETERMINATE_FOREGROUND_1 = new ColorUIResource(255, 153, 2);
    /**
     * The constant PROGRESSBAR_INDETERMINATE_FOREGROUND_2.
     */
    public static final ColorUIResource PROGRESSBAR_INDETERMINATE_FOREGROUND_2 = new ColorUIResource(255, 181, 58);
    /**
     * The constant PROGRESSBAR_BORDER.
     */
    public static final ColorUIResource PROGRESSBAR_BORDER = new ColorUIResource(221, 221, 221);

    /**
     * The constant WARNING_COLOR.
     */
    public static final ColorUIResource WARNING_COLOR = new ColorUIResource(255, 230, 152);

    /**
     * The constant WHITE.
     */
    public static final ColorUIResource WHITE = new ColorUIResource(255, 255, 255);
    /**
     * The constant BLACK.
     */
    public static final ColorUIResource BLACK = new ColorUIResource(0, 0, 0);

	private ColorUIResource[] tabbedPaneColors = new ColorUIResource[] { new ColorUIResource(200, 205, 210),
			new ColorUIResource(215, 220, 225), new ColorUIResource(170, 170, 190), new ColorUIResource(200, 200, 220),
			new ColorUIResource(190, 200, 220), new ColorUIResource(250, 250, 250), new ColorUIResource(255, 255, 255),
			new ColorUIResource(210, 210, 230), new ColorUIResource(180, 190, 210), new ColorUIResource(200, 200, 220),
			new ColorUIResource(210, 210, 230), new ColorUIResource(220, 220, 240), new ColorUIResource(230, 230, 250),
			new ColorUIResource(235, 235, 255), new ColorUIResource(240, 240, 255), new ColorUIResource(245, 245, 255),
			new ColorUIResource(250, 250, 255), new ColorUIResource(255, 255, 255), new ColorUIResource(255, 255, 255),
			new ColorUIResource(210, 210, 230), new ColorUIResource(240, 240, 255), new ColorUIResource(245, 145, 0), };

	private ColorUIResource[] fileChooserColors = new ColorUIResource[] { new ColorUIResource(255, 200, 200),
			new ColorUIResource(230, 170, 170) };

	private ColorUIResource desktopBackgroundColor = new ColorUIResource(180, 195, 220);

    /**
     * Add custom entries to table.
     *
     * @param table the table
     */
    public void addCustomEntriesToTable(UIDefaults table) {
		Object[] values = new Object[] { "ToolTip.background", TOOLTIP_BACKGROUND, "ToolTip.foreground", TOOLTIP_FOREGROUND,
				"ToolTip.borderColor", TOOLTIP_BORDER };
		table.putDefaults(values);
	}

    /**
     * Gets common background.
     *
     * @return the common background
     */
    public ColorUIResource getCommonBackground() {
		return PANEL_BACKGROUND;
	}

    /**
     * Get tabbed pane colors color ui resource [ ].
     *
     * @return the color ui resource [ ]
     */
    public ColorUIResource[] getTabbedPaneColors() {
		return this.tabbedPaneColors;
	}

    /**
     * Get file chooser colors color ui resource [ ].
     *
     * @return the color ui resource [ ]
     */
    public ColorUIResource[] getFileChooserColors() {
		return this.fileChooserColors;
	}

    /**
     * Gets desktop background color.
     *
     * @return the desktop background color
     */
    public ColorUIResource getDesktopBackgroundColor() {
		return this.desktopBackgroundColor;
	}
}
