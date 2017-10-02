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

import com.rapidminer.datatable.DataTable;
import com.rapidminer.gui.new_plotter.configuration.DimensionConfig;
import com.rapidminer.gui.new_plotter.configuration.DimensionConfig.PlotDimension;
import com.rapidminer.gui.new_plotter.configuration.PlotConfiguration;
import com.rapidminer.gui.new_plotter.configuration.RangeAxisConfig;
import com.rapidminer.gui.new_plotter.engine.jfreechart.link_and_brush.listener.LinkAndBrushSelection;
import com.rapidminer.gui.new_plotter.templates.style.ColorScheme;
import org.jfree.chart.plot.PlotOrientation;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;


/**
 * A {@link PlotConfigurationChangeEvent} represents a change of the {@link PlotConfiguration}.
 * Every {@link PlotConfigurationChangeEvent} has a cloned source of its starting
 * {@link PlotConfiguration}.
 *
 * @author Nils Woehler
 */
public class PlotConfigurationChangeEvent implements ConfigurationChangeEvent {

    /**
     * The enum Plot configuration change type.
     */
    public enum PlotConfigurationChangeType {
        /**
         * Range axis config added plot configuration change type.
         */
        RANGE_AXIS_CONFIG_ADDED, // a range axis config was added
        /**
         * Range axis config removed plot configuration change type.
         */
        RANGE_AXIS_CONFIG_REMOVED, // a range axis config was removed
        /**
         * Range axis config moved plot configuration change type.
         */
        RANGE_AXIS_CONFIG_MOVED, // a range axis config was moved to another
        /**
         * Dimension config added plot configuration change type.
         */
// index
		DIMENSION_CONFIG_ADDED, // a dimension config was added
        /**
         * Dimension config removed plot configuration change type.
         */
        DIMENSION_CONFIG_REMOVED, // a dimension config was removed
        /**
         * Chart title plot configuration change type.
         */
        CHART_TITLE, // the chart title has changed
        /**
         * Axes font plot configuration change type.
         */
        AXES_FONT, // the axes font has changed
        /**
         * Frame background color plot configuration change type.
         */
        FRAME_BACKGROUND_COLOR, // the chart background has changed
        /**
         * Plot background color plot configuration change type.
         */
        PLOT_BACKGROUND_COLOR, // the plot background has changed
        /**
         * Plot orientation plot configuration change type.
         */
        PLOT_ORIENTATION, // the domain axis orientation has changed
        /**
         * Data table exchanged plot configuration change type.
         */
        DATA_TABLE_EXCHANGED, // the data table has been exchanged

        /**
         * The Range axis config changed.
         */
// Refer to other events
		RANGE_AXIS_CONFIG_CHANGED, // a range axis configuration has changed
        /**
         * Dimension config changed plot configuration change type.
         */
        DIMENSION_CONFIG_CHANGED, // the configuration for a dimension has been changed or exchanged
        /**
         * Axis line color plot configuration change type.
         */
        AXIS_LINE_COLOR, /**
         * Axis line width plot configuration change type.
         */
        AXIS_LINE_WIDTH, /**
         * Color scheme plot configuration change type.
         */
        COLOR_SCHEME, /**
         * Link and brush selection plot configuration change type.
         */
        LINK_AND_BRUSH_SELECTION, /**
         * Legend changed plot configuration change type.
         */
        LEGEND_CHANGED, /**
         * Meta change plot configuration change type.
         */
        META_CHANGE,  // this
        /**
         * The Trigger replot.
         */
// means
																												// the
																												// a
																												// list
																												// of
																												// plot
																												// config
																												// change
																												// events
																												// is
																												// about
																												// to
																												// happen
		TRIGGER_REPLOT // this event is used to trigger a replot
	}

	private final PlotConfigurationChangeType type;
	private PlotConfiguration source;

	private final List<PlotConfigurationChangeEvent> plotConfigChangeEvents = new LinkedList<PlotConfigurationChangeEvent>();

	// Range Axis Config added, removed, moved
	private RangeAxisConfig rangeAxisConfig = null;
	private Integer index = null;

	private PlotDimension dimension = null;
	private DimensionConfig dimensionConfig = null;

	private String chartTitle = null;

	private Font axesFont = null;

	private Color plotBackgroundColor = null;
	private Color frameBackgroundColor = null;
	private Color axisLineColor = null;

	private PlotOrientation orientation = null;

	// refer to other events
	private DimensionConfigChangeEvent dimensionChange = null;
	private RangeAxisConfigChangeEvent rangeAxisConfigChange = null;
	private Float domainAxisLineWidth = null;
	private ColorScheme colorScheme = null;
	private DataTable dataTable = null;
	private LinkAndBrushSelection linkAndBrushSelection = null;
	private LegendConfigurationChangeEvent legendConfigurationChangeEvent = null;

    /**
     * Gets data table.
     *
     * @return the data table
     */
    public DataTable getDataTable() {
		return dataTable;
	}

    /**
     * Allowed {@link PlotConfigurationChangeType}s are RANGE_AXIS_CONFIG_ADDED,
     * RANGE_AXIS_CONFIG_REMOVED and RANGE_AXIS_CONFIG_MOVED
     *
     * @param source    the source
     * @param type      the type
     * @param rangeAxis the range axis
     * @param index     the index
     */
    public PlotConfigurationChangeEvent(PlotConfiguration source, PlotConfigurationChangeType type,
			RangeAxisConfig rangeAxis, Integer index) {
		setSource(source);
		if ((type != PlotConfigurationChangeType.RANGE_AXIS_CONFIG_ADDED)
				&& (type != PlotConfigurationChangeType.RANGE_AXIS_CONFIG_REMOVED)
				&& (type != PlotConfigurationChangeType.RANGE_AXIS_CONFIG_MOVED)) {
			throw new RuntimeException(type + " is not allowed calling this constructor.");
		}
		this.type = type;
		this.rangeAxisConfig = rangeAxis;
		this.index = index;
	}

    /**
     * Instantiates a new Plot configuration change event.
     *
     * @param source the source
     */
    public PlotConfigurationChangeEvent(PlotConfiguration source) {
		setSource(source);
		this.type = PlotConfigurationChangeType.TRIGGER_REPLOT;
	}

    /**
     * Allowed {@link PlotConfigurationChangeType}s are DIMENSION_CONFIG_ADDED or DIMENSION_REMOVED
     *
     * @param source          the source
     * @param type            the type
     * @param dimension       the dimension
     * @param dimensionConfig the dimension config
     */
    public PlotConfigurationChangeEvent(PlotConfiguration source, PlotConfigurationChangeType type, PlotDimension dimension,
			DimensionConfig dimensionConfig) {
		if ((type != PlotConfigurationChangeType.DIMENSION_CONFIG_ADDED)
				&& (type != PlotConfigurationChangeType.DIMENSION_CONFIG_REMOVED)) {
			throw new RuntimeException(type + " is not allowed calling this constructor.");
		}

		setSource(source);
		this.type = type;
		this.dimension = dimension;
		this.dimensionConfig = dimensionConfig;
	}

    /**
     * Instantiates a new Plot configuration change event.
     *
     * @param source     the source
     * @param chartTitle the chart title
     */
    public PlotConfigurationChangeEvent(PlotConfiguration source, String chartTitle) {
		setSource(source);
		this.type = PlotConfigurationChangeType.CHART_TITLE;
		this.chartTitle = chartTitle;
	}

    /**
     * Instantiates a new Plot configuration change event.
     *
     * @param source                 the source
     * @param plotConfigChangeEvents the plot config change events
     */
    public PlotConfigurationChangeEvent(PlotConfiguration source, List<PlotConfigurationChangeEvent> plotConfigChangeEvents) {
		setSource(source);
		this.type = PlotConfigurationChangeType.META_CHANGE;
		this.plotConfigChangeEvents.addAll(plotConfigChangeEvents);
	}

    /**
     * Instantiates a new Plot configuration change event.
     *
     * @param source   the source
     * @param axesFont the axes font
     */
    public PlotConfigurationChangeEvent(PlotConfiguration source, Font axesFont) {
		setSource(source);
		this.type = PlotConfigurationChangeType.AXES_FONT;
		this.axesFont = axesFont;
	}

    /**
     * Allowed {@link PlotConfigurationChangeType}s are FRAME_BACKGROUND_COLOR or
     * PLOT_BACKGROUND_COLOR or AXIS_LINE_COLOR
     *
     * @param source the source
     * @param type   the type
     * @param color  the color
     */
    public PlotConfigurationChangeEvent(PlotConfiguration source, PlotConfigurationChangeType type, Color color) {
		setSource(source);
		this.type = type;
		if ((type != PlotConfigurationChangeType.FRAME_BACKGROUND_COLOR)
				&& (type != PlotConfigurationChangeType.PLOT_BACKGROUND_COLOR)
				&& (type != PlotConfigurationChangeType.AXIS_LINE_COLOR)) {
			throw new RuntimeException(type + " is not allowed calling this constructor.");
		}

		if (type == PlotConfigurationChangeType.FRAME_BACKGROUND_COLOR) {
			frameBackgroundColor = color;
		} else if (type == PlotConfigurationChangeType.AXIS_LINE_COLOR) {
			axisLineColor = color;
		} else if (type == PlotConfigurationChangeType.PLOT_BACKGROUND_COLOR) {
			plotBackgroundColor = color;
		} else {
			throw new RuntimeException("Unknown type for color assignment");
		}
	}

    /**
     * Instantiates a new Plot configuration change event.
     *
     * @param source          the source
     * @param dimensionChange the dimension change
     */
    public PlotConfigurationChangeEvent(PlotConfiguration source, DimensionConfigChangeEvent dimensionChange) {
		setSource(source);
		this.type = PlotConfigurationChangeType.DIMENSION_CONFIG_CHANGED;
		this.dimensionChange = dimensionChange;
	}

    /**
     * Instantiates a new Plot configuration change event.
     *
     * @param source    the source
     * @param selection the selection
     */
    public PlotConfigurationChangeEvent(PlotConfiguration source, LinkAndBrushSelection selection) {
		setSource(source);
		this.type = PlotConfigurationChangeType.LINK_AND_BRUSH_SELECTION;
		this.linkAndBrushSelection = selection;
	}

    /**
     * Instantiates a new Plot configuration change event.
     *
     * @param source          the source
     * @param rangeAxisChange the range axis change
     */
    public PlotConfigurationChangeEvent(PlotConfiguration source, RangeAxisConfigChangeEvent rangeAxisChange) {
		setSource(source);
		this.type = PlotConfigurationChangeType.RANGE_AXIS_CONFIG_CHANGED;
		this.rangeAxisConfigChange = rangeAxisChange;
	}

    /**
     * Instantiates a new Plot configuration change event.
     *
     * @param plotConfiguration the plot configuration
     * @param orientation       the orientation
     */
    public PlotConfigurationChangeEvent(PlotConfiguration plotConfiguration, PlotOrientation orientation) {
		setSource(plotConfiguration);
		this.type = PlotConfigurationChangeType.PLOT_ORIENTATION;
		this.orientation = orientation;
	}

    /**
     * Instantiates a new Plot configuration change event.
     *
     * @param plotConfiguration   the plot configuration
     * @param domainAxisLineWidth the domain axis line width
     */
    public PlotConfigurationChangeEvent(PlotConfiguration plotConfiguration, float domainAxisLineWidth) {
		setSource(plotConfiguration);
		this.type = PlotConfigurationChangeType.AXIS_LINE_WIDTH;
		this.domainAxisLineWidth = domainAxisLineWidth;
	}

    /**
     * Instantiates a new Plot configuration change event.
     *
     * @param plotConfiguration the plot configuration
     * @param colorScheme       the color scheme
     */
    public PlotConfigurationChangeEvent(PlotConfiguration plotConfiguration, ColorScheme colorScheme) {
		setSource(plotConfiguration);
		this.type = PlotConfigurationChangeType.COLOR_SCHEME;
		this.colorScheme = colorScheme;
	}

    /**
     * Instantiates a new Plot configuration change event.
     *
     * @param plotConfiguration the plot configuration
     * @param dataTable         the data table
     */
    public PlotConfigurationChangeEvent(PlotConfiguration plotConfiguration, DataTable dataTable) {
		setSource(plotConfiguration);
		this.type = PlotConfigurationChangeType.DATA_TABLE_EXCHANGED;
		this.dataTable = dataTable;
	}

    /**
     * Instantiates a new Plot configuration change event.
     *
     * @param plotConfiguration the plot configuration
     * @param change            the change
     */
    public PlotConfigurationChangeEvent(PlotConfiguration plotConfiguration, LegendConfigurationChangeEvent change) {
		setSource(plotConfiguration);
		this.type = PlotConfigurationChangeType.LEGEND_CHANGED;
		this.legendConfigurationChangeEvent = change;
	}

    /**
     * Sets source.
     *
     * @param plotConfiguration the plot configuration
     */
    public void setSource(PlotConfiguration plotConfiguration) {
		this.source = plotConfiguration;
	}

    /**
     * Gets dimension change.
     *
     * @return the dimension change
     */
    public DimensionConfigChangeEvent getDimensionChange() {
		return dimensionChange;
	}

    /**
     * This function can only be called if type of change event is META_CHANGE
     *
     * @param newSource   the new source
     * @param changeEvent the change event
     */
    public void addPlotConfigChangeEvent(PlotConfiguration newSource, PlotConfigurationChangeEvent changeEvent) {
		if (type != PlotConfigurationChangeType.META_CHANGE) {
			throw new IllegalArgumentException("Wrong type. Only META_CHANGE is allowed!");
		}
		source = newSource;
		plotConfigChangeEvents.add(changeEvent);
	}

    /**
     * Gets plot config change events.
     *
     * @return the list of plot configuration events, if this event is a META_CHANGE. The returned         list must NOT be changed!
     */
    public List<PlotConfigurationChangeEvent> getPlotConfigChangeEvents() {
		return plotConfigChangeEvents;
	}

    /**
     * Gets type.
     *
     * @return the type
     */
    public PlotConfigurationChangeType getType() {
		return type;
	}

    /**
     * Gets range axis config.
     *
     * @return the rangeAxis
     */
    public RangeAxisConfig getRangeAxisConfig() {
		return rangeAxisConfig;
	}

    /**
     * Gets index.
     *
     * @return the index
     */
    public Integer getIndex() {
		return index;
	}

    /**
     * Gets color scheme.
     *
     * @return the colorScheme
     */
    public ColorScheme getColorScheme() {
		return colorScheme;
	}

    /**
     * Gets link and brush selection.
     *
     * @return the linkAndBrushSelection
     */
    public LinkAndBrushSelection getLinkAndBrushSelection() {
		return linkAndBrushSelection;
	}

    /**
     * Gets dimension.
     *
     * @return the dimension
     */
    public PlotDimension getDimension() {
		return dimension;
	}

    /**
     * Gets dimension config.
     *
     * @return the dimensionConfig
     */
    public DimensionConfig getDimensionConfig() {
		return dimensionConfig;
	}

    /**
     * Gets domain axis line color.
     *
     * @return the domainAxisLineColor
     */
    public Color getDomainAxisLineColor() {
		return axisLineColor;
	}

    /**
     * Gets domain axis line width.
     *
     * @return the domainAxisLineWidth
     */
    public float getDomainAxisLineWidth() {
		return domainAxisLineWidth;
	}

    /**
     * Gets range axis config change.
     *
     * @return the rangeAxisChange
     */
    public RangeAxisConfigChangeEvent getRangeAxisConfigChange() {
		return rangeAxisConfigChange;
	}

    /**
     * Gets source.
     *
     * @return the source
     */
    public PlotConfiguration getSource() {
		return source;
	}

    /**
     * Gets chart title.
     *
     * @return the chartTitle
     */
    public String getChartTitle() {
		return chartTitle;
	}

    /**
     * Gets axes font.
     *
     * @return the axesFont
     */
    public Font getAxesFont() {
		return axesFont;
	}

    /**
     * Gets plot background color.
     *
     * @return the plotBackgroundColor
     */
    public Color getPlotBackgroundColor() {
		return plotBackgroundColor;
	}

    /**
     * Gets frame background color.
     *
     * @return the chartBackgroundColor
     */
    public Color getFrameBackgroundColor() {
		return frameBackgroundColor;
	}

	@Override
	public ConfigurationChangeType getConfigurationChangeType() {
		return ConfigurationChangeType.PLOT_CONFIGURATION_CHANGE;
	}

    /**
     * Gets orientation.
     *
     * @return the orientation
     */
    public PlotOrientation getOrientation() {
		return orientation;
	}

	@Override
	public String toString() {
		return getType().toString();
	}

    /**
     * Gets legend configuration change event.
     *
     * @return the legend configuration change event
     */
    public LegendConfigurationChangeEvent getLegendConfigurationChangeEvent() {
		return legendConfigurationChangeEvent;
	}

}
