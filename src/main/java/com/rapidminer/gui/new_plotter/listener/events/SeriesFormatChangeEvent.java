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

import com.rapidminer.gui.new_plotter.configuration.LineFormat.LineStyle;
import com.rapidminer.gui.new_plotter.configuration.SeriesFormat;
import com.rapidminer.gui.new_plotter.configuration.SeriesFormat.FillStyle;
import com.rapidminer.gui.new_plotter.configuration.SeriesFormat.IndicatorType;
import com.rapidminer.gui.new_plotter.configuration.SeriesFormat.ItemShape;
import com.rapidminer.gui.new_plotter.configuration.SeriesFormat.StackingMode;
import com.rapidminer.gui.new_plotter.configuration.SeriesFormat.VisualizationType;

import java.awt.Color;


/**
 * The type Series format change event.
 *
 * @author Nils Woehler
 */
public class SeriesFormatChangeEvent implements ConfigurationChangeEvent {

    /**
     * The enum Series format change type.
     */
    public enum SeriesFormatChangeType {
        /**
         * Item shape series format change type.
         */
        ITEM_SHAPE, /**
         * Item size series format change type.
         */
        ITEM_SIZE, /**
         * Item color series format change type.
         */
        ITEM_COLOR, /**
         * Line width series format change type.
         */
        LINE_WIDTH, /**
         * Line style series format change type.
         */
        LINE_STYLE, /**
         * Line color series format change type.
         */
        LINE_COLOR, /**
         * Opacity series format change type.
         */
        OPACITY, /**
         * Area fill style series format change type.
         */
        AREA_FILL_STYLE, /**
         * Series type series format change type.
         */
        SERIES_TYPE, /**
         * Stacking mode series format change type.
         */
        STACKING_MODE, /**
         * Utility indicator series format change type.
         */
        UTILITY_INDICATOR
	}

	private final SeriesFormat source;
	private final SeriesFormatChangeType type;

	private VisualizationType seriesType = null;
	private StackingMode stackingMode = null;
	private IndicatorType errorIndicator = null;
	private LineStyle lineStyle = null;
	private ItemShape itemShape = null;

	private Double itemSize = null;
	private Float lineWidth = null;
	private Color itemColor = null;

	private Color lineColor = null;
	private FillStyle areaFillStyle = null;
	private Integer opacity = null;

    /**
     * Instantiates a new Series format change event.
     *
     * @param source     the source
     * @param seriesType the series type
     */
    public SeriesFormatChangeEvent(SeriesFormat source, VisualizationType seriesType) {
		this.type = SeriesFormatChangeType.SERIES_TYPE;
		this.seriesType = seriesType;
		this.source = source;
	}

    /**
     * Instantiates a new Series format change event.
     *
     * @param source       the source
     * @param stackingMode the stacking mode
     */
    public SeriesFormatChangeEvent(SeriesFormat source, StackingMode stackingMode) {
		this.type = SeriesFormatChangeType.STACKING_MODE;
		this.stackingMode = stackingMode;
		this.source = source;
	}

    /**
     * Instantiates a new Series format change event.
     *
     * @param source         the source
     * @param errorIndicator the error indicator
     */
    public SeriesFormatChangeEvent(SeriesFormat source, IndicatorType errorIndicator) {
		this.type = SeriesFormatChangeType.UTILITY_INDICATOR;
		this.errorIndicator = errorIndicator;
		this.source = source;
	}

    /**
     * Instantiates a new Series format change event.
     *
     * @param source    the source
     * @param lineStyle the line style
     */
    public SeriesFormatChangeEvent(SeriesFormat source, LineStyle lineStyle) {
		this.type = SeriesFormatChangeType.LINE_STYLE;
		this.lineStyle = lineStyle;
		this.source = source;
	}

    /**
     * Instantiates a new Series format change event.
     *
     * @param source    the source
     * @param itemShape the item shape
     */
    public SeriesFormatChangeEvent(SeriesFormat source, ItemShape itemShape) {
		this.type = SeriesFormatChangeType.ITEM_SHAPE;
		this.itemShape = itemShape;
		this.source = source;
	}

    /**
     * Instantiates a new Series format change event.
     *
     * @param source   the source
     * @param itemSize the item size
     */
    public SeriesFormatChangeEvent(SeriesFormat source, Double itemSize) {
		super();
		this.type = SeriesFormatChangeType.ITEM_SIZE;
		this.itemSize = itemSize;
		this.source = source;
	}

    /**
     * Instantiates a new Series format change event.
     *
     * @param source    the source
     * @param lineWidth the line width
     */
    public SeriesFormatChangeEvent(SeriesFormat source, Float lineWidth) {
		this.type = SeriesFormatChangeType.LINE_WIDTH;
		this.lineWidth = lineWidth;
		this.source = source;
	}

    /**
     * Instantiates a new Series format change event.
     *
     * @param source        the source
     * @param areaFillStyle the area fill style
     */
    public SeriesFormatChangeEvent(SeriesFormat source, FillStyle areaFillStyle) {
		this.type = SeriesFormatChangeType.AREA_FILL_STYLE;
		this.areaFillStyle = areaFillStyle;
		this.source = source;
	}

    /**
     * Instantiates a new Series format change event.
     *
     * @param source  the source
     * @param opacity the opacity
     */
    public SeriesFormatChangeEvent(SeriesFormat source, Integer opacity) {
		this.type = SeriesFormatChangeType.OPACITY;
		this.opacity = opacity;
		this.source = source;
	}

    /**
     * Allowed {@link SeriesFormatChangeType}s are LINE_COLOR or ITEM_COLOR.
     *
     * @param source the source
     * @param type   the type
     * @param color  the color
     */
    public SeriesFormatChangeEvent(SeriesFormat source, SeriesFormatChangeType type, Color color) {
		this.type = type;
		if ((type != SeriesFormatChangeType.ITEM_COLOR) && (type != SeriesFormatChangeType.LINE_COLOR)) {
			throw new RuntimeException(type + " is not allowed calling this constructor.");
		}
		if (type == SeriesFormatChangeType.LINE_COLOR) {
			this.lineColor = color;
		} else {
			this.itemColor = color;
		}
		this.source = source;
	}

    /**
     * Gets type.
     *
     * @return the type
     */
    public SeriesFormatChangeType getType() {
		return type;
	}

    /**
     * Gets series type.
     *
     * @return the series type
     */
    public VisualizationType getSeriesType() {
		return seriesType;
	}

    /**
     * Gets error indicator.
     *
     * @return the error indicator
     */
    public IndicatorType getErrorIndicator() {
		return errorIndicator;
	}

    /**
     * Gets stacking mode.
     *
     * @return the stacking mode
     */
    public StackingMode getStackingMode() {
		return stackingMode;
	}

    /**
     * Gets source.
     *
     * @return the source
     */
    public SeriesFormat getSource() {
		return source;
	}

    /**
     * Gets line style.
     *
     * @return the lineStyle
     */
    public LineStyle getLineStyle() {
		return lineStyle;
	}

    /**
     * Gets item shape.
     *
     * @return the itemShape
     */
    public ItemShape getItemShape() {
		return itemShape;
	}

    /**
     * Gets item size.
     *
     * @return the itemSize
     */
    public Double getItemSize() {
		return itemSize;
	}

    /**
     * Gets line width.
     *
     * @return the lineWidth
     */
    public Float getLineWidth() {
		return lineWidth;
	}

    /**
     * Gets line color.
     *
     * @return the lineColor
     */
    public Color getLineColor() {
		return lineColor;
	}

    /**
     * Gets opacity.
     *
     * @return the opacity
     */
    public Integer getOpacity() {
		return opacity;
	}

    /**
     * Sets series type.
     *
     * @param seriesType the seriesType to set
     */
    public void setSeriesType(VisualizationType seriesType) {
		this.seriesType = seriesType;
	}

    /**
     * Sets stacking mode.
     *
     * @param stackingMode the stackingMode to set
     */
    public void setStackingMode(StackingMode stackingMode) {
		this.stackingMode = stackingMode;
	}

    /**
     * Gets item color.
     *
     * @return the itemColor
     */
    public Color getItemColor() {
		return itemColor;
	}

    /**
     * Gets area fill style.
     *
     * @return the areaFillStyle
     */
    public FillStyle getAreaFillStyle() {
		return areaFillStyle;
	}

	@Override
	public ConfigurationChangeType getConfigurationChangeType() {
		return ConfigurationChangeType.SERIES_FORMAT_CHANGE;
	}

	@Override
	public String toString() {
		return getType().toString();
	}

}
