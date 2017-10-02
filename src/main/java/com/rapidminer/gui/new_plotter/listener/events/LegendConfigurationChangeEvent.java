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
package com.rapidminer.gui.new_plotter.listener.events;

import com.rapidminer.gui.new_plotter.configuration.LegendConfiguration;
import com.rapidminer.gui.new_plotter.configuration.LegendConfiguration.LegendPosition;

import java.awt.Color;
import java.awt.Font;


/**
 * The type Legend configuration change event.
 *
 * @author Marius Helf, Nils Woehler
 */
public class LegendConfigurationChangeEvent {

    /**
     * The enum Legend configuration change type.
     */
    public enum LegendConfigurationChangeType {
        /**
         * Positon legend configuration change type.
         */
        POSITON, // the legend position has changed
        /**
         * Font legend configuration change type.
         */
        FONT, // the legend font has changed
        /**
         * Show dimension type legend configuration change type.
         */
        SHOW_DIMENSION_TYPE, // displays the type of a dimension in front of categorical dimension
        /**
         * The Background color.
         */
// legend items
		BACKGROUND_COLOR, /**
         * Frame color legend configuration change type.
         */
        FRAME_COLOR, /**
         * Show legend frame legend configuration change type.
         */
        SHOW_LEGEND_FRAME
	}

	private LegendPosition legendPosition = null;
	private Font legendFont = null;
	private LegendConfiguration source;
	private LegendConfigurationChangeType type;
	private boolean showDimensionType;
	private Color frameColor;
	private Color backgroundColor;
	private boolean showLegendFrame;

    /**
     * Instantiates a new Legend configuration change event.
     *
     * @param source         the source
     * @param legendPosition the legend position
     */
    public LegendConfigurationChangeEvent(LegendConfiguration source, LegendPosition legendPosition) {
		this.source = source;
		this.type = LegendConfigurationChangeType.POSITON;
		this.legendPosition = legendPosition;
	}

    /**
     * Instantiates a new Legend configuration change event.
     *
     * @param source the source
     * @param font   the font
     */
    public LegendConfigurationChangeEvent(LegendConfiguration source, Font font) {
		this.source = source;
		this.type = LegendConfigurationChangeType.FONT;
		this.legendFont = font;
	}

    /**
     * Instantiates a new Legend configuration change event.
     *
     * @param source the source
     * @param show   the show
     * @param type   the type
     */
    public LegendConfigurationChangeEvent(LegendConfiguration source, boolean show, LegendConfigurationChangeType type) {
		if ((type != LegendConfigurationChangeType.SHOW_DIMENSION_TYPE)
				&& (type != LegendConfigurationChangeType.SHOW_LEGEND_FRAME)) {
			throw new RuntimeException(type + " is not allowed calling this constructor.");
		}
		this.source = source;
		this.type = type;
		if (type == LegendConfigurationChangeType.SHOW_DIMENSION_TYPE) {
			this.showDimensionType = show;
		} else {
			this.showLegendFrame = show;
		}
	}

    /**
     * Only FRAME_COLOR or BACKGROUND_COLOR are allowed as type.
     *
     * @param source the source
     * @param color  the color
     * @param type   the type
     */
    public LegendConfigurationChangeEvent(LegendConfiguration source, Color color, LegendConfigurationChangeType type) {
		if ((type != LegendConfigurationChangeType.FRAME_COLOR) && (type != LegendConfigurationChangeType.BACKGROUND_COLOR)) {
			throw new RuntimeException(type + " is not allowed calling this constructor.");
		}
		this.source = source;
		this.type = type;
		if (type == LegendConfigurationChangeType.FRAME_COLOR) {
			frameColor = color;
		} else {
			backgroundColor = color;
		}
	}

    /**
     * Is show dimension type boolean.
     *
     * @return the boolean
     */
    public boolean isShowDimensionType() {
		return showDimensionType;
	}

    /**
     * Gets legend position.
     *
     * @return the legendPosition
     */
    public LegendPosition getLegendPosition() {
		return legendPosition;
	}

    /**
     * Gets legend font.
     *
     * @return the legendFont
     */
    public Font getLegendFont() {
		return legendFont;
	}

    /**
     * Gets source.
     *
     * @return the source
     */
    public LegendConfiguration getSource() {
		return source;
	}

    /**
     * Gets type.
     *
     * @return the type
     */
    public LegendConfigurationChangeType getType() {
		return type;
	}

    /**
     * Gets frame color.
     *
     * @return the frameColor
     */
    public Color getFrameColor() {
		return this.frameColor;
	}

    /**
     * Gets background color.
     *
     * @return the backgroundColor
     */
    public Color getBackgroundColor() {
		return this.backgroundColor;
	}

    /**
     * Is show legend frame boolean.
     *
     * @return the showLegendFrame
     */
    public boolean isShowLegendFrame() {
		return this.showLegendFrame;
	}
}
