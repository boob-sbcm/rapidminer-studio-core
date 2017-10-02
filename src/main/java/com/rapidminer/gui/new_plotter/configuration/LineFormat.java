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

import com.rapidminer.gui.new_plotter.listener.events.LineFormatChangeEvent;
import com.rapidminer.gui.new_plotter.utility.DataStructureUtils;
import com.rapidminer.tools.I18N;

import java.awt.*;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


/**
 * The type Line format.
 *
 * @author Marius Helf
 */
public class LineFormat implements Cloneable {

	private static class StrokeFactory {

        /**
         * Gets solid stroke.
         *
         * @return the solid stroke
         */
        static public BasicStroke getSolidStroke() {
			return new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
		}

        /**
         * Gets dotted stroke.
         *
         * @return the dotted stroke
         */
        static public BasicStroke getDottedStroke() {
			return new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 10.0f, new float[] { 1f, 1f }, 0.0f);
		}

        /**
         * Gets short dashed stroke.
         *
         * @return the short dashed stroke
         */
        static public BasicStroke getShortDashedStroke() {
			return new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 10.0f, new float[] { 4f, 2f }, 0.0f);
		}

        /**
         * Gets long dashed stroke.
         *
         * @return the long dashed stroke
         */
        static public BasicStroke getLongDashedStroke() {
			return new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 10.0f, new float[] { 7f, 3f }, 0.0f);
		}

        /**
         * Gets dash dot stroke.
         *
         * @return the dash dot stroke
         */
        static public BasicStroke getDashDotStroke() {
			return new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 10.0f, new float[] { 6f, 2f, 1f, 2f },
					0.0f);
		}

        /**
         * Gets striped stroke.
         *
         * @return the striped stroke
         */
        static public BasicStroke getStripedStroke() {
			return new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 10.0f, new float[] { 0.2f, 0.2f }, 0.0f);
		}
	}

    /**
     * The enum Line style.
     */
    public enum LineStyle {
        /**
         * None line style.
         */
        NONE(null, I18N.getGUILabel("plotter.linestyle.NONE.label")), /**
         * Solid line style.
         */
        SOLID(StrokeFactory.getSolidStroke(), I18N
				.getGUILabel("plotter.linestyle.SOLID.label")), /**
         * Dots line style.
         */
        DOTS(StrokeFactory.getDottedStroke(), I18N
				.getGUILabel("plotter.linestyle.DOTS.label")), /**
         * Short dashes line style.
         */
        SHORT_DASHES(StrokeFactory.getShortDashedStroke(), I18N
				.getGUILabel("plotter.linestyle.SHORT_DASHES.label")), /**
         * Long dashes line style.
         */
        LONG_DASHES(StrokeFactory.getLongDashedStroke(), I18N
				.getGUILabel("plotter.linestyle.LONG_DASHES.label")), /**
         * Dash dot line style.
         */
        DASH_DOT(StrokeFactory.getDashDotStroke(), I18N
				.getGUILabel("plotter.linestyle.DASH_DOT.label")), /**
         * Stripes line style.
         */
        STRIPES(StrokeFactory.getStripedStroke(), I18N
				.getGUILabel("plotter.linestyle.STRIPES.label"));

		private final BasicStroke stroke;
		private final String name;

        /**
         * Gets stroke.
         *
         * @return the stroke
         */
        public BasicStroke getStroke() {
			return stroke;
		}

        /**
         * Gets name.
         *
         * @return the name
         */
        public String getName() {
			return name;
		}

		private LineStyle(BasicStroke stroke, String name) {
			this.stroke = stroke;
			this.name = name;
		}
	}

	private List<WeakReference<LineFormatListener>> listeners = new LinkedList<WeakReference<LineFormatListener>>();

	private LineStyle style = LineStyle.NONE;	// dashed, solid...
	private Color color = Color.GRAY;
	private float width = 1.0f;

    /**
     * Gets style.
     *
     * @return the style
     */
    public LineStyle getStyle() {
		return style;
	}

    /**
     * Sets style.
     *
     * @param style the style
     */
    public void setStyle(LineStyle style) {
		if (style != this.style) {
			this.style = style;
			fireStyleChanged();
		}
	}

    /**
     * Gets color.
     *
     * @return the color
     */
    public Color getColor() {
		return color;
	}

    /**
     * Sets color.
     *
     * @param color the color
     */
    public void setColor(Color color) {
		if (color == null ? this.color != null : !color.equals(this.color)) {
			this.color = color;
			fireColorChanged();
		}
	}

    /**
     * Gets width.
     *
     * @return the width
     */
    public float getWidth() {
		return width;
	}

    /**
     * Sets width.
     *
     * @param width the width
     */
    public void setWidth(float width) {
		if (width != this.width) {
			this.width = width;
			fireWidthChanged();
		}
	}

	private void fireWidthChanged() {
		fireLineFormatChanged(new LineFormatChangeEvent(this, width));
	}

	private void fireColorChanged() {
		fireLineFormatChanged(new LineFormatChangeEvent(this, color));
	}

	private void fireStyleChanged() {
		fireLineFormatChanged(new LineFormatChangeEvent(this, style));
	}

	private void fireLineFormatChanged(LineFormatChangeEvent e) {
		Iterator<WeakReference<LineFormatListener>> it = listeners.iterator();
		while (it.hasNext()) {
			LineFormatListener l = it.next().get();
			if (l != null) {
				l.lineFormatChanged(e);
			} else {
				it.remove();
			}
		}
	}

	@Override
	public LineFormat clone() {
		LineFormat clone = new LineFormat();
		clone.color = new Color(color.getRGB(), true);
		clone.style = style;
		clone.width = width;
		return clone;
	}

    /**
     * Gets stroke.
     *
     * @return the stroke
     */
    public BasicStroke getStroke() {
		BasicStroke stroke = style.getStroke();

		if (stroke != null) {
			float[] scaledDashArray = getScaledDashArray();
			BasicStroke scaledStroke = new BasicStroke(this.getWidth(), stroke.getEndCap(), stroke.getLineJoin(),
					stroke.getMiterLimit(), scaledDashArray, stroke.getDashPhase());
			return scaledStroke;
		} else {
			return null;
		}
	}

    /**
     * Get scaled dash array float [ ].
     *
     * @return the float [ ]
     */
    float[] getScaledDashArray() {
		BasicStroke stroke = getStyle().getStroke();

		if (stroke == null) {
			return null;
		}
		float[] dashArray = stroke.getDashArray();
		float[] scaledDashArray;
		if (dashArray != null) {
			float scalingFactor = getWidth();
			if (scalingFactor <= 0) {
				scalingFactor = 1;
			}
			if (scalingFactor != 1) {
				scaledDashArray = DataStructureUtils.cloneAndMultiplyArray(dashArray, scalingFactor);
			} else {
				scaledDashArray = dashArray;
			}
		} else {
			scaledDashArray = dashArray;
		}
		return scaledDashArray;
	}

    /**
     * Add line format listener.
     *
     * @param l the l
     */
    public void addLineFormatListener(LineFormatListener l) {
		listeners.add(new WeakReference<LineFormatListener>(l));
	}

    /**
     * Remove line format listener.
     *
     * @param l the l
     */
    public void removeLineFormatListener(LineFormatListener l) {
		Iterator<WeakReference<LineFormatListener>> it = listeners.iterator();
		while (it.hasNext()) {
			LineFormatListener listener = it.next().get();
			if (l != null) {
				if (listener != null && listener.equals(l)) {
					it.remove();
				}
			} else {
				it.remove();
			}
		}
	}
}
