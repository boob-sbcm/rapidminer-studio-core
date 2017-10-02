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
package com.rapidminer.gui.plotter;

import com.rapidminer.operator.IOObject;

import java.util.HashMap;


/**
 * This is the data holding class for plotter settings. It is used by the PlotterConfigurationModel
 * to store the actual parameters and their values.
 *
 * @author Sebastian Land
 */
public class PlotterConfigurationSettings {

    /**
     * Key to use in {@link IOObject#setUserData(String, Object)} when attaching a
     * Map<String,String> with the plotter settings as user data.
     */
    public static final String IOOBJECT_USER_DATA_SETTINGS_KEY = PlotterConfigurationSettings.class.getName() + ".settings";
    /**
     * The constant IOOBJECT_USER_DATA_PLOTTER_KEY.
     */
    public static final String IOOBJECT_USER_DATA_PLOTTER_KEY = PlotterConfigurationSettings.class.getName() + ".plotter";

    /**
     * The constant AXIS_Z.
     */
// common settings
	public static final String AXIS_Z = "_axis_z_axis";
    /**
     * The constant AXIS_Y.
     */
    public static final String AXIS_Y = "_axis_y_axis";
    /**
     * The constant AXIS_X.
     */
    public static final String AXIS_X = "_axis_x_axis";
    /**
     * The constant AXIS_STACK_COLUMN.
     */
    public static final String AXIS_STACK_COLUMN = "_axis_stack_column";
    /**
     * The constant INDEX_DIMENSION.
     */
    public static final String INDEX_DIMENSION = "_axis_index_dimension";
    /**
     * The constant DIMENSION.
     */
    public static final String DIMENSION = "_axis_dimension";
    /**
     * The constant AXIS_HISTOGRAM.
     */
    public static final String AXIS_HISTOGRAM = "_axis_histogram";
    /**
     * The constant POINT_COLOR.
     */
    public static final String POINT_COLOR = "_axis_point_color";
    /**
     * The constant GROUP_BY_COLUMN.
     */
    public static final String GROUP_BY_COLUMN = "_axis_group_by_column";
    /**
     * The constant AGGREGATION.
     */
    public static final String AGGREGATION = "_aggregation";
    /**
     * The constant AXIS_PLOT_COLUMNS.
     */
    public static final String AXIS_PLOT_COLUMNS = "_plot_columns";
    /**
     * The constant CLASS_COLUMN.
     */
    public static final String CLASS_COLUMN = "_axis_class_column";
    /**
     * The constant AXIS_PLOT_COLUMN.
     */
    public static final String AXIS_PLOT_COLUMN = "_plot_column";
    /**
     * The constant BUBBLE_SIZE.
     */
    public static final String BUBBLE_SIZE = "_axis_bubble_size";
    /**
     * The constant NUMBER_OF_BINS.
     */
    public static final String NUMBER_OF_BINS = "_number_of_bins";

	private String plotterName;

	// TODO change to Map<String, Map<String, String>>
	private HashMap<String, String> parameterSettings = new HashMap<>();
	private HashMap<String, Class<? extends Plotter>> availablePlotters;

    /**
     * Instantiates a new Plotter configuration settings.
     */
    public PlotterConfigurationSettings() {}

	/**
	 * This is the clone constructor.
	 */
	private PlotterConfigurationSettings(PlotterConfigurationSettings other) {
		plotterName = other.plotterName;
		parameterSettings.putAll(other.parameterSettings);
		availablePlotters = other.availablePlotters;
	}

	@Override
	public PlotterConfigurationSettings clone() {
		return new PlotterConfigurationSettings(this);
	}

    /**
     * Gets plotter name.
     *
     * @return the plotter name
     */
    public String getPlotterName() {
		return plotterName;
	}

    /**
     * Sets plotter name.
     *
     * @param plotterName the plotter name
     */
    public void setPlotterName(String plotterName) {
		this.plotterName = plotterName;
	}

    /**
     * Gets parameter settings.
     *
     * @return the parameter settings
     */
    public HashMap<String, String> getParameterSettings() {
		return parameterSettings;
	}

    /**
     * Gets available plotters.
     *
     * @return the available plotters
     */
    public HashMap<String, Class<? extends Plotter>> getAvailablePlotters() {
		return availablePlotters;
	}

    /**
     * Sets available plotters.
     *
     * @param availablePlotters the available plotters
     */
    public void setAvailablePlotters(HashMap<String, Class<? extends Plotter>> availablePlotters) {
		this.availablePlotters = availablePlotters;
	}

	/* Parameter methods */

    /**
     * This method sets a parameter specified by the key to the given value. Calling this method
     * will not inform any listener since it's only a data storage. Please use setParameterValue of
     * PlotterConfigurationModel instead.
     *
     * @param key   the key
     * @param value the value
     */
    public void setParameterValue(String key, String value) {
		parameterSettings.put(key, value);
	}

    /**
     * This method will return the parameter value of the given generalized key. Generalized keys
     * will be used internally by the PlotterSettings.
     *
     * @param generalizedKey the generalized key
     * @return the parameter value
     */
    public String getParameterValue(String generalizedKey) {
		return parameterSettings.get(generalizedKey);
	}

    /**
     * This method checks whether the parameter with this generalized key is stored.
     *
     * @param generalizedKeyName the generalized key name
     * @return the boolean
     */
    public boolean isParameterSet(String generalizedKeyName) {
		return parameterSettings.containsKey(generalizedKeyName);
	}

    /**
     * Sets paramter settings.
     *
     * @param settings the settings
     */
    public void setParamterSettings(HashMap<String, String> settings) {
		this.parameterSettings = settings;

	}
}
