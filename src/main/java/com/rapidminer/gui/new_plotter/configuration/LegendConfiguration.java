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
package com.rapidminer.gui.new_plotter.configuration;

import java.awt.Color;
import java.awt.Font;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.jfree.ui.RectangleEdge;

import com.rapidminer.gui.new_plotter.listener.LegendConfigurationListener;
import com.rapidminer.gui.new_plotter.listener.events.LegendConfigurationChangeEvent;
import com.rapidminer.gui.new_plotter.listener.events.LegendConfigurationChangeEvent.LegendConfigurationChangeType;
import com.rapidminer.tools.FontTools;
import com.rapidminer.tools.I18N;


/**
 * The type Legend configuration.
 *
 * @author Marius Helf, Nils Woehler
 */
public class LegendConfiguration implements Cloneable {

    /**
     * The enum Legend position.
     */
    public enum LegendPosition {
        /**
         * Top legend position.
         */
        TOP(I18N.getGUILabel("plotter.legendposition.TOP.label"), RectangleEdge.TOP), /**
         * Bottom legend position.
         */
        BOTTOM(
				I18N.getGUILabel("plotter.legendposition.BOTTOM.label"),
				RectangleEdge.BOTTOM), /**
         * Left legend position.
         */
        LEFT(I18N.getGUILabel("plotter.legendposition.LEFT.label"),
						RectangleEdge.LEFT), /**
         * Right legend position.
         */
        RIGHT(I18N.getGUILabel("plotter.legendposition.RIGHT.label"),
								RectangleEdge.RIGHT), /**
         * None legend position.
         */
        NONE(I18N.getGUILabel("plotter.legendposition.NONE.label"), null);

		private String name;
		private RectangleEdge position;

		LegendPosition(String name, RectangleEdge position) {
			this.name = name;
			this.position = position;
		}

        /**
         * Gets name.
         *
         * @return the name
         */
        public String getName() {
			return name;
		}

        /**
         * Caution may be <code>null</code>!
         *
         * @return position position
         */
        public RectangleEdge getPosition() {
			return position;
		}
	}

    /**
     * The constant DEFAULT_LEGEND_FONT.
     */
    public static final Font DEFAULT_LEGEND_FONT = FontTools.getFont(Font.DIALOG, Font.PLAIN, 12);
	private static final boolean DEFAULT_SHOW_DIMENSION_TYPE = true;
	private static final LegendPosition DEFAULT_LEGEND_POSITION = LegendPosition.RIGHT;
    /**
     * The constant DEFAULT_LEGEND_BACKGROUND_COLOR.
     */
    public static final Color DEFAULT_LEGEND_BACKGROUND_COLOR = Color.white;
    /**
     * The constant DEFAULT_LEGEND_FRAME_COLOR.
     */
    public static final Color DEFAULT_LEGEND_FRAME_COLOR = Color.black;
	private static final boolean DEFAULT_SHOW_LEGEND_FRAME = true;
    /**
     * The constant DEFAULT_LEGEND_FONT_COLOR.
     */
    public static final Color DEFAULT_LEGEND_FONT_COLOR = Color.black;

	private transient List<WeakReference<LegendConfigurationListener>> listeners = new LinkedList<WeakReference<LegendConfigurationListener>>();
	private Font legendFont = DEFAULT_LEGEND_FONT;
	private LegendPosition legendPosition = DEFAULT_LEGEND_POSITION;

	private LegendConfigurationChangeEvent currentEvent = null;

	private boolean showDimensionType = DEFAULT_SHOW_DIMENSION_TYPE;

	private Color legendBackgroundColor = DEFAULT_LEGEND_BACKGROUND_COLOR;

	private Color legendFrameColor = DEFAULT_LEGEND_FRAME_COLOR;
	private boolean showLegendFrame = DEFAULT_SHOW_LEGEND_FRAME;
	private Color legendFontColor = DEFAULT_LEGEND_FONT_COLOR;

    /**
     * Is show dimension type boolean.
     *
     * @return the boolean
     */
    public boolean isShowDimensionType() {
		return showDimensionType;
	}

    /**
     * Sets show dimension type.
     *
     * @param showDimensionType the show dimension type
     */
    public void setShowDimensionType(boolean showDimensionType) {
		if (showDimensionType != this.showDimensionType) {
			this.showDimensionType = showDimensionType;
			fireShowDimensionTypeChanged();
		}
	}

	private void fireShowDimensionTypeChanged() {
		fireLegendConfigurationChanged(new LegendConfigurationChangeEvent(this, showDimensionType,
				LegendConfigurationChangeType.SHOW_DIMENSION_TYPE));
	}

    /**
     * Gets current event.
     *
     * @return the current event
     */
    public LegendConfigurationChangeEvent getCurrentEvent() {
		return currentEvent;
	}

    /**
     * Gets legend font.
     *
     * @return the legend font
     */
    public Font getLegendFont() {
		return legendFont;
	}

    /**
     * Sets legend font.
     *
     * @param legendFont the legend font
     */
    public void setLegendFont(Font legendFont) {
		if (legendFont != this.legendFont) {
			this.legendFont = legendFont;
			fireLegendFontChanged();
		}
	}

    /**
     * Gets legend background color.
     *
     * @return the legendBackgroundColor
     */
    public Color getLegendBackgroundColor() {
		return this.legendBackgroundColor;
	}

    /**
     * Gets legend frame color.
     *
     * @return the legendFrameColor
     */
    public Color getLegendFrameColor() {
		return this.legendFrameColor;
	}

    /**
     * Sets legend background color.
     *
     * @param legendBackgroundColor the legendBackgroundColor to set
     */
    public void setLegendBackgroundColor(Color legendBackgroundColor) {
		this.legendBackgroundColor = legendBackgroundColor;
		fireLegendConfigurationChanged(new LegendConfigurationChangeEvent(this, legendBackgroundColor,
				LegendConfigurationChangeType.BACKGROUND_COLOR));
	}

    /**
     * Sets legend frame color.
     *
     * @param legendFrameColor the legendFrameColor to set
     */
    public void setLegendFrameColor(Color legendFrameColor) {
		this.legendFrameColor = legendFrameColor;
		fireLegendConfigurationChanged(
				new LegendConfigurationChangeEvent(this, legendFrameColor, LegendConfigurationChangeType.FRAME_COLOR));
	}

    /**
     * Gets legend position.
     *
     * @return the legend position
     */
    public LegendPosition getLegendPosition() {
		return legendPosition;
	}

    /**
     * Sets legend position.
     *
     * @param legendPosition the legend position
     */
    public void setLegendPosition(LegendPosition legendPosition) {
		if (legendPosition != this.legendPosition) {
			this.legendPosition = legendPosition;
			fireLegendPositionChanged();
		}
	}

	@Override
	public LegendConfiguration clone() {
		LegendConfiguration clone = new LegendConfiguration();
		clone.legendFont = this.legendFont;
		clone.legendPosition = this.legendPosition;
		clone.showDimensionType = this.showDimensionType;
		clone.legendBackgroundColor = this.legendBackgroundColor;
		clone.legendFrameColor = this.legendFrameColor;
		clone.showLegendFrame = this.showLegendFrame;
		clone.legendFontColor = this.legendFontColor;
		return clone;
	}

	private void fireLegendFontChanged() {
		fireLegendConfigurationChanged(new LegendConfigurationChangeEvent(this, legendFont));
	}

	private void fireLegendPositionChanged() {
		fireLegendConfigurationChanged(new LegendConfigurationChangeEvent(this, legendPosition));
	}

	private void fireLegendConfigurationChanged(LegendConfigurationChangeEvent e) {
		currentEvent = e;
		Iterator<WeakReference<LegendConfigurationListener>> it = listeners.iterator();
		while (it.hasNext()) {
			LegendConfigurationListener l = it.next().get();
			if (l != null) {
				l.legendConfigurationChanged(e);
			} else {
				it.remove();
			}
		}
		currentEvent = null;
	}

    /**
     * Add listener.
     *
     * @param l the l
     */
    public void addListener(LegendConfigurationListener l) {
		listeners.add(new WeakReference<LegendConfigurationListener>(l));
	}

    /**
     * Remove listener.
     *
     * @param l the l
     */
    public void removeListener(LegendConfigurationListener l) {
		Iterator<WeakReference<LegendConfigurationListener>> it = listeners.iterator();
		while (it.hasNext()) {
			LegendConfigurationListener listener = it.next().get();
			if (listener == null || listener == l) {
				it.remove();
			}
		}
	}

    /**
     * Reset to defaults.
     */
    public void resetToDefaults() {
		setLegendFont(DEFAULT_LEGEND_FONT);
		setLegendPosition(DEFAULT_LEGEND_POSITION);
		setShowDimensionType(DEFAULT_SHOW_DIMENSION_TYPE);
		setLegendFrameColor(DEFAULT_LEGEND_FRAME_COLOR);
		setLegendBackgroundColor(DEFAULT_LEGEND_BACKGROUND_COLOR);
		setShowLegendFrame(DEFAULT_SHOW_LEGEND_FRAME);
	}

    /**
     * Is show legend frame boolean.
     *
     * @return boolean boolean
     */
    public boolean isShowLegendFrame() {
		return showLegendFrame;
	}

    /**
     * Sets show legend frame.
     *
     * @param showLegendFrame the showLegendFrame to set
     */
    public void setShowLegendFrame(boolean showLegendFrame) {
		this.showLegendFrame = showLegendFrame;
		fireLegendConfigurationChanged(
				new LegendConfigurationChangeEvent(this, showLegendFrame, LegendConfigurationChangeType.SHOW_LEGEND_FRAME));
	}

    /**
     * Gets legend font color.
     *
     * @return the legend font color
     */
    public Color getLegendFontColor() {
		return legendFontColor;
	}

    /**
     * Sets legend font color.
     *
     * @param newLegendFontColor the new legend font color
     */
    public void setLegendFontColor(Color newLegendFontColor) {
		this.legendFontColor = newLegendFontColor;
		fireLegendConfigurationChanged(new LegendConfigurationChangeEvent(this, legendFont));
	}
}
