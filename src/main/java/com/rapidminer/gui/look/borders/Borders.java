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
package com.rapidminer.gui.look.borders;

import javax.swing.border.Border;


/**
 * The border creation and maintaining class used for all components. This class creates all borders
 * once and use these singletons for painting. Therefore, this class mainly serves as access point
 * for the border singletons.
 *
 * @author Ingo Mierswa
 */
public class Borders {

    /**
     * The constant TOOL_TIP_BORDER.
     */
    public static Border TOOL_TIP_BORDER = new ToolTipBorder();
    /**
     * The constant DUMMY_BORDER.
     */
    public static Border DUMMY_BORDER = new DummyBorder();
    /**
     * The constant POPUP_BORDER.
     */
    public static Border POPUP_BORDER = new PopupBorder();
    /**
     * The constant SHADOWED_POPUP_MENU_BORDER.
     */
    public static Border SHADOWED_POPUP_MENU_BORDER = new ShadowedPopupMenuBorder();
    /**
     * The constant TABLE_HEADER_BORDER.
     */
    public static Border TABLE_HEADER_BORDER = new TableHeaderBorder();
    /**
     * The constant SPLIT_PANE_BORDER.
     */
    public static Border SPLIT_PANE_BORDER = new SplitPaneBorder();
    /**
     * The constant INTERNAL_FRAME_BORDER.
     */
    public static Border INTERNAL_FRAME_BORDER = new InternalFrameBorder();
    /**
     * The constant COMBO_BOX_BORDER.
     */
    public static Border COMBO_BOX_BORDER = new ComboBoxBorder();
    /**
     * The constant TOOL_BAR_BORDER.
     */
    public static Border TOOL_BAR_BORDER = new ToolBarBorder();
    /**
     * The constant PROGRESS_BAR_BORDER.
     */
    public static Border PROGRESS_BAR_BORDER = new ProgressBarBorder();
    /**
     * The constant EMPTY_BORDER.
     */
    public static Border EMPTY_BORDER = new EmptyBorder();
    /**
     * The constant EMPTY_BUTTON_BORDER.
     */
    public static Border EMPTY_BUTTON_BORDER = new EmptyButtonBorder();
    /**
     * The constant POPUP_MENU_BORDER.
     */
    public static Border POPUP_MENU_BORDER = new PopupMenuBorder();
    /**
     * The constant TEXT_FIELD_BORDER.
     */
    public static Border TEXT_FIELD_BORDER = new TextFieldBorder();
    /**
     * The constant SCROLL_PANE_BORDER.
     */
    public static Border SCROLL_PANE_BORDER = new ScrollPaneBorder();
    /**
     * The constant SPINNER_BORDER.
     */
    public static Border SPINNER_BORDER = new SpinnerBorder();
    /**
     * The constant EMPTY_COMBO_BOX_BORDER.
     */
    public static Border EMPTY_COMBO_BOX_BORDER = new EmptyComboBoxBorder();
    /**
     * The constant CHECK_BOX_BORDER.
     */
    public static Border CHECK_BOX_BORDER = new CheckBoxBorder();
    /**
     * The constant COMBO_BOX_LIST_CELL_RENDERER_FOCUS_BORDER.
     */
    public static Border COMBO_BOX_LIST_CELL_RENDERER_FOCUS_BORDER = new ComboBoxListCellRendererFocusBorder();
    /**
     * The constant MENU_BAR_BORDER.
     */
    public static Border MENU_BAR_BORDER = new MenuBarBorder();

    /**
     * Gets check box border.
     *
     * @return the check box border
     */
    public static Border getCheckBoxBorder() {
		return CHECK_BOX_BORDER;
	}

    /**
     * Gets tool tip border.
     *
     * @return the tool tip border
     */
    public static Border getToolTipBorder() {
		return TOOL_TIP_BORDER;
	}

    /**
     * Gets dummy border.
     *
     * @return the dummy border
     */
    public static Border getDummyBorder() {
		return DUMMY_BORDER;
	}

    /**
     * Gets popup border.
     *
     * @return the popup border
     */
    public static Border getPopupBorder() {
		return POPUP_BORDER;
	}

    /**
     * Gets shadowed popup menu border.
     *
     * @return the shadowed popup menu border
     */
    public static Border getShadowedPopupMenuBorder() {
		return SHADOWED_POPUP_MENU_BORDER;
	}

    /**
     * Gets table header border.
     *
     * @return the table header border
     */
    public static Border getTableHeaderBorder() {
		return TABLE_HEADER_BORDER;
	}

    /**
     * Gets split pane border.
     *
     * @return the split pane border
     */
    public static Border getSplitPaneBorder() {
		return SPLIT_PANE_BORDER;
	}

    /**
     * Gets internal frame border.
     *
     * @return the internal frame border
     */
    public static Border getInternalFrameBorder() {
		return INTERNAL_FRAME_BORDER;
	}

    /**
     * Gets menu bar border.
     *
     * @return the menu bar border
     */
    public static Border getMenuBarBorder() {
		return MENU_BAR_BORDER;
	}

    /**
     * Gets combo box border.
     *
     * @return the combo box border
     */
    public static Border getComboBoxBorder() {
		return COMBO_BOX_BORDER;
	}

    /**
     * Gets tool bar border.
     *
     * @return the tool bar border
     */
    public static Border getToolBarBorder() {
		return TOOL_BAR_BORDER;
	}

    /**
     * Gets progress bar border.
     *
     * @return the progress bar border
     */
    public static Border getProgressBarBorder() {
		return PROGRESS_BAR_BORDER;
	}

    /**
     * Gets empty border.
     *
     * @return the empty border
     */
    public static Border getEmptyBorder() {
		return EMPTY_BORDER;
	}

    /**
     * Gets empty button border.
     *
     * @return the empty button border
     */
    public static Border getEmptyButtonBorder() {
		return EMPTY_BUTTON_BORDER;
	}

    /**
     * Gets popup menu border.
     *
     * @return the popup menu border
     */
    public static Border getPopupMenuBorder() {
		return POPUP_MENU_BORDER;
	}

    /**
     * Gets text field border.
     *
     * @return the text field border
     */
    public static Border getTextFieldBorder() {
		return TEXT_FIELD_BORDER;
	}

    /**
     * Gets scroll pane border.
     *
     * @return the scroll pane border
     */
    public static Border getScrollPaneBorder() {
		return SCROLL_PANE_BORDER;
	}

    /**
     * Gets spinner border.
     *
     * @return the spinner border
     */
    public static Border getSpinnerBorder() {
		return SPINNER_BORDER;
	}

    /**
     * Gets combo box list cell renderer focus border.
     *
     * @return the combo box list cell renderer focus border
     */
    public static Border getComboBoxListCellRendererFocusBorder() {
		return COMBO_BOX_LIST_CELL_RENDERER_FOCUS_BORDER;
	}

    /**
     * Gets empty combo box border.
     *
     * @return the empty combo box border
     */
    public static Border getEmptyComboBoxBorder() {
		return EMPTY_COMBO_BOX_BORDER;
	}
}
