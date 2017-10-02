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

import com.rapidminer.gui.new_plotter.configuration.DataTableColumn;
import com.rapidminer.gui.new_plotter.configuration.DimensionConfig;
import com.rapidminer.gui.new_plotter.configuration.DimensionConfig.PlotDimension;
import com.rapidminer.gui.new_plotter.configuration.DomainConfigManager;
import com.rapidminer.gui.new_plotter.configuration.event.AxisParallelLinesConfigurationChangeEvent;
import com.rapidminer.gui.new_plotter.utility.ColorProvider;
import com.rapidminer.gui.new_plotter.utility.ShapeProvider;
import com.rapidminer.gui.new_plotter.utility.SizeProvider;

import java.text.DateFormat;


/**
 * The type Dimension config change event.
 *
 * @author Nils Woehler
 */
public class DimensionConfigChangeEvent implements ConfigurationChangeEvent {

    /**
     * The enum Dimension config change type.
     */
    public enum DimensionConfigChangeType {
        /**
         * Reset dimension config change type.
         */
        RESET, /**
         * About to change grouping dimension config change type.
         */
        ABOUT_TO_CHANGE_GROUPING, 	// informing listernes that grouping will change
        /**
         * Grouping changed dimension config change type.
         */
        GROUPING_CHANGED,			// the grouping has been changed its values
        /**
         * Range dimension config change type.
         */
        RANGE,						// the user range has been changed or exchanged
        /**
         * Column dimension config change type.
         */
        COLUMN,						// the datatable column has been changed or exchange
        /**
         * Scaling dimension config change type.
         */
        SCALING,					// scaling for dimension config has changed (i.e. logarithmic or linear)
        /**
         * Label dimension config change type.
         */
        LABEL,						// the dimensions label has changed
        /**
         * Color provider dimension config change type.
         */
        COLOR_PROVIDER,				// the color provider has been changed or exchanged
        /**
         * Shape provider dimension config change type.
         */
        SHAPE_PROVIDER,				// the shape provider has been changed or exchanged
        /**
         * Size provider dimension config change type.
         */
        SIZE_PROVIDER, 				// the size provider has been changed or exchanged
        /**
         * Sorting dimension config change type.
         */
        SORTING,					// the sorting has been changed or exchanged
        /**
         * Auto naming dimension config change type.
         */
        AUTO_NAMING, /**
         * Color scheme dimension config change type.
         */
        COLOR_SCHEME, /**
         * Crosshair lines changed dimension config change type.
         */
        CROSSHAIR_LINES_CHANGED, /**
         * Date format changed dimension config change type.
         */
        DATE_FORMAT_CHANGED,
	}

	private final DimensionConfigChangeType type;
	private final DimensionConfig source;
	private final PlotDimension dimension;

	private DataTableColumn column = null;
	private ValueGroupingChangeEvent groupingChangeEvent = null;
	private ValueRangeChangeEvent valueRangeChangedEvent = null;

	private Boolean logarithmic = null;
	private String label = null;
	private ColorProvider colorProvider = null;
	private ShapeProvider shapeProvider = null;
	private SizeProvider sizeProvider = null;
	private DomainConfigManager.Sorting sortingMode = null;
	private Boolean includeZero = null;
	private Boolean autoNaming = null;
	private AxisParallelLinesConfigurationChangeEvent crosshairLinesChange;
	private DateFormat dateFormat;

    /**
     * Allowed {@link DimensionConfigChangeType}s are ABOUT_TO_CHANGE_GROUPING or RESET or
     * COLOR_SCHEME
     *
     * @param source    the source
     * @param dimension the dimension
     * @param type      the type
     */
    public DimensionConfigChangeEvent(DimensionConfig source, PlotDimension dimension, DimensionConfigChangeType type) {
		if ((type != DimensionConfigChangeType.ABOUT_TO_CHANGE_GROUPING) && (type != DimensionConfigChangeType.RESET)
				&& (type != DimensionConfigChangeType.COLOR_SCHEME)) {
			throw new RuntimeException(type + " is not allowed calling this constructor.");
		}
		this.source = source;
		this.type = type;
		this.dimension = dimension;
	}

    /**
     * Instantiates a new Dimension config change event.
     *
     * @param source              the source
     * @param dimension           the dimension
     * @param groupingChangeEvent the grouping change event
     */
    public DimensionConfigChangeEvent(DimensionConfig source, PlotDimension dimension,
			ValueGroupingChangeEvent groupingChangeEvent) {
		this.source = source;
		this.type = DimensionConfigChangeType.GROUPING_CHANGED;
		this.groupingChangeEvent = groupingChangeEvent;
		this.dimension = dimension;
	}

    /**
     * Instantiates a new Dimension config change event.
     *
     * @param source                 the source
     * @param dimension              the dimension
     * @param valueRangeChangedEvent the value range changed event
     */
    public DimensionConfigChangeEvent(DimensionConfig source, PlotDimension dimension,
			ValueRangeChangeEvent valueRangeChangedEvent) {
		this.source = source;
		this.type = DimensionConfigChangeType.RANGE;
		this.valueRangeChangedEvent = valueRangeChangedEvent;
		this.dimension = dimension;
	}

    /**
     * Instantiates a new Dimension config change event.
     *
     * @param source    the source
     * @param dimension the dimension
     * @param column    the column
     */
    public DimensionConfigChangeEvent(DimensionConfig source, PlotDimension dimension, DataTableColumn column) {
		this.source = source;
		this.type = DimensionConfigChangeType.COLUMN;
		this.column = column;
		this.dimension = dimension;
	}

    /**
     * Instantiates a new Dimension config change event.
     *
     * @param source    the source
     * @param dimension the dimension
     * @param bool      the bool
     * @param type      the type
     */
    public DimensionConfigChangeEvent(DimensionConfig source, PlotDimension dimension, Boolean bool,
			DimensionConfigChangeType type) {
		if ((type != DimensionConfigChangeType.SCALING) && (type != DimensionConfigChangeType.AUTO_NAMING)) {
			throw new RuntimeException(type + " is not allowed calling this constructor.");
		}
		this.source = source;
		this.type = type;
		if (type == DimensionConfigChangeType.SCALING) {
			this.logarithmic = bool;
		} else if (type == DimensionConfigChangeType.AUTO_NAMING) {
			this.autoNaming = bool;
		} else {
			this.includeZero = bool;
		}
		this.dimension = dimension;
	}

    /**
     * Instantiates a new Dimension config change event.
     *
     * @param source    the source
     * @param dimension the dimension
     * @param label     the label
     */
    public DimensionConfigChangeEvent(DimensionConfig source, PlotDimension dimension, String label) {
		this.source = source;
		this.type = DimensionConfigChangeType.LABEL;
		this.label = label;
		this.dimension = dimension;
	}

    /**
     * Instantiates a new Dimension config change event.
     *
     * @param source        the source
     * @param dimension     the dimension
     * @param colorProvider the color provider
     */
    public DimensionConfigChangeEvent(DimensionConfig source, PlotDimension dimension, ColorProvider colorProvider) {
		this.source = source;
		this.type = DimensionConfigChangeType.COLOR_PROVIDER;
		this.colorProvider = colorProvider;
		this.dimension = dimension;
	}

    /**
     * Instantiates a new Dimension config change event.
     *
     * @param source       the source
     * @param dimension    the dimension
     * @param sizeProvider the size provider
     */
    public DimensionConfigChangeEvent(DimensionConfig source, PlotDimension dimension, SizeProvider sizeProvider) {
		this.source = source;
		this.type = DimensionConfigChangeType.SIZE_PROVIDER;
		this.sizeProvider = sizeProvider;
		this.dimension = dimension;
	}

    /**
     * Instantiates a new Dimension config change event.
     *
     * @param source        the source
     * @param dimension     the dimension
     * @param shapeProvider the shape provider
     */
    public DimensionConfigChangeEvent(DimensionConfig source, PlotDimension dimension, ShapeProvider shapeProvider) {
		this.source = source;
		this.type = DimensionConfigChangeType.SHAPE_PROVIDER;
		this.shapeProvider = shapeProvider;
		this.dimension = dimension;
	}

    /**
     * Instantiates a new Dimension config change event.
     *
     * @param source      the source
     * @param dimension   the dimension
     * @param sortingMode the sorting mode
     */
    public DimensionConfigChangeEvent(DimensionConfig source, PlotDimension dimension,
			DomainConfigManager.Sorting sortingMode) {
		this.source = source;
		this.type = DimensionConfigChangeType.SORTING;
		this.sortingMode = sortingMode;
		this.dimension = dimension;
	}

    /**
     * Instantiates a new Dimension config change event.
     *
     * @param source    the source
     * @param dimension the dimension
     * @param e         the e
     */
    public DimensionConfigChangeEvent(DomainConfigManager source, PlotDimension dimension,
			AxisParallelLinesConfigurationChangeEvent e) {
		this.type = DimensionConfigChangeType.CROSSHAIR_LINES_CHANGED;
		this.dimension = dimension;
		this.source = source;
		this.crosshairLinesChange = e;
	}

    /**
     * Instantiates a new Dimension config change event.
     *
     * @param source     the source
     * @param dateFormat the date format
     */
    public DimensionConfigChangeEvent(DimensionConfig source, DateFormat dateFormat) {
		this.source = source;
		this.dimension = source.getDimension();
		this.type = DimensionConfigChangeType.DATE_FORMAT_CHANGED;
		this.dateFormat = dateFormat;
	}

    /**
     * Gets data table column.
     *
     * @return the column
     */
    public DataTableColumn getDataTableColumn() {
		return column;
	}

    /**
     * Gets grouping change event.
     *
     * @return the groupingChangeEvent
     */
    public ValueGroupingChangeEvent getGroupingChangeEvent() {
		return groupingChangeEvent;
	}

    /**
     * Gets value range changed event.
     *
     * @return the valueRangeChangedEvent
     */
    public ValueRangeChangeEvent getValueRangeChangedEvent() {
		return valueRangeChangedEvent;
	}

    /**
     * Gets logarithmic.
     *
     * @return the logarithmic
     */
    public Boolean getLogarithmic() {
		return logarithmic;
	}

    /**
     * Gets label.
     *
     * @return the label
     */
    public String getLabel() {
		return label;
	}

    /**
     * Gets color provider.
     *
     * @return the colorProvider
     */
    public ColorProvider getColorProvider() {
		return colorProvider;
	}

    /**
     * Gets size provider.
     *
     * @return the sizeProvider
     */
    public SizeProvider getSizeProvider() {
		return sizeProvider;
	}

    /**
     * Gets shape provider.
     *
     * @return the shapeProvider
     */
    public ShapeProvider getShapeProvider() {
		return shapeProvider;
	}

    /**
     * Gets type.
     *
     * @return the type
     */
    public DimensionConfigChangeType getType() {
		return type;
	}

    /**
     * Gets source.
     *
     * @return the source
     */
    public DimensionConfig getSource() {
		return source;
	}

    /**
     * Gets sorting mode.
     *
     * @return the sortProvider
     */
    public DomainConfigManager.Sorting getSortingMode() {
		return sortingMode;
	}

    /**
     * Gets dimension.
     *
     * @return dimension dimension
     */
    public PlotDimension getDimension() {
		return dimension;
	}

	@Override
	public ConfigurationChangeType getConfigurationChangeType() {
		return ConfigurationChangeType.DIMENSION_CONFIG_CHANGE;
	}

    /**
     * Gets include zero.
     *
     * @return the includeZero
     */
    public Boolean getIncludeZero() {
		return includeZero;
	}

    /**
     * Gets auto naming.
     *
     * @return the autoNaming
     */
    public Boolean getAutoNaming() {
		return autoNaming;
	}

	@Override
	public String toString() {
		return getType().toString();
	}

    /**
     * Gets crosshair lines change.
     *
     * @return the crosshair lines change
     */
    public AxisParallelLinesConfigurationChangeEvent getCrosshairLinesChange() {
		return crosshairLinesChange;
	}

    /**
     * Gets date format.
     *
     * @return the date format
     */
    public DateFormat getDateFormat() {
		return dateFormat;
	}
}
