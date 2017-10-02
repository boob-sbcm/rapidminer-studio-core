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

import com.rapidminer.datatable.DataTable;
import com.rapidminer.gui.new_plotter.PlotConfigurationError;
import com.rapidminer.gui.new_plotter.configuration.DataTableColumn.ValueType;
import com.rapidminer.gui.new_plotter.configuration.ValueGrouping.GroupingType;
import com.rapidminer.gui.new_plotter.listener.DimensionConfigListener;
import com.rapidminer.gui.new_plotter.utility.NumericalValueRange;
import com.rapidminer.gui.new_plotter.utility.ValueRange;
import com.rapidminer.tools.I18N;

import java.text.DateFormat;
import java.util.List;
import java.util.Set;
import java.util.Vector;


/**
 * Defines where a dimension gets its values from. Could be: the values of an attribute, all
 * attributes, etc.
 * <p>
 * Also defines the sort order and the value range.
 *
 * @author Marius Helf, Nils Woehler
 */
public interface DimensionConfig extends Cloneable {

    /**
     * The constant DEFAULT_DATE_FORMAT_STRING.
     */
    public static final String DEFAULT_DATE_FORMAT_STRING = "dd.MM.yyyy HH:mm";
    /**
     * The constant DEFAULT_AXIS_LABEL.
     */
    public static final String DEFAULT_AXIS_LABEL = "";
    /**
     * The constant DEFAULT_USE_USER_DEFINED_DATE_FORMAT.
     */
    public static boolean DEFAULT_USE_USER_DEFINED_DATE_FORMAT = false;
    /**
     * The constant DEFAULT_USER_DEFINED_LOWER_BOUND.
     */
    public static double DEFAULT_USER_DEFINED_LOWER_BOUND = 0;
    /**
     * The constant DEFAULT_USER_DEFINED_UPPER_BOUND.
     */
    public static double DEFAULT_USER_DEFINED_UPPER_BOUND = 1;

    /**
     * The enum Plot dimension.
     */
    public enum PlotDimension {
        /**
         * Value plot dimension.
         */
        VALUE(null, null), /**
         * Domain plot dimension.
         */
        DOMAIN(I18N.getGUILabel("plotter.configuration_dialog.plot_dimension.domain.label"), I18N
				.getGUILabel("plotter.configuration_dialog.plot_dimension.domain.short_label")), /**
         * Color plot dimension.
         */
        COLOR(I18N
				.getGUILabel("plotter.configuration_dialog.plot_dimension.color.label"), I18N
				.getGUILabel("plotter.configuration_dialog.plot_dimension.color.short_label")), /**
         * Shape plot dimension.
         */
        SHAPE(I18N
				.getGUILabel("plotter.configuration_dialog.plot_dimension.shape.label"), I18N
				.getGUILabel("plotter.configuration_dialog.plot_dimension.shape.short_label")), /**
         * Size plot dimension.
         */
        SIZE(I18N
				.getGUILabel("plotter.configuration_dialog.plot_dimension.size.label"), I18N
				.getGUILabel("plotter.configuration_dialog.plot_dimension.size.short_label")), /**
         * Selected plot dimension.
         */
        SELECTED(I18N
				.getGUILabel("plotter.configuration_dialog.plot_dimension.selected.label"), I18N
				.getGUILabel("plotter.configuration_dialog.plot_dimension.selected.short_label"));

		// LINESTYLE

		private final String name;
		private String shortName;

		private PlotDimension(String name, String shortName) {
			this.name = name;
			this.shortName = shortName;
		}

        /**
         * Gets name.
         *
         * @return The display name of the enum value.
         */
        public String getName() {
			return name;
		}

        /**
         * Gets short name.
         *
         * @return A shorter display name of the enum value.
         */
        public String getShortName() {
			return shortName;
		}
	}

    /**
     * Gets user defined upper bound.
     *
     * @return the user defined upper bound
     */
    public Double getUserDefinedUpperBound();

    /**
     * Gets user defined lower bound.
     *
     * @return the user defined lower bound
     */
    public Double getUserDefinedLowerBound();

    /**
     * Gets dimension.
     *
     * @return the dimension
     */
    public PlotDimension getDimension();

    /**
     * Returns the {@link DataTableColumn} from which this DimensionConfig gets its raw values.
     *
     * @return the data table column
     */
    public DataTableColumn getDataTableColumn();

    /**
     * Gets grouping.
     *
     * @return the grouping
     */
    public ValueGrouping getGrouping();

    /**
     * Returns the range of data which is used to create the diagram. Note that this is not
     * necessarily the data the user sees, because he might apply further filtering by zooming.
     * <p>
     * Might return null, which indicates that all values should be used.
     * <p>
     * Returns a clone of the actual range, so changing the returned object does not actually change
     * the range of this {@link DimensionConfig}.
     *
     * @param dataTable the data table
     * @return the user defined range clone
     */
    public ValueRange getUserDefinedRangeClone(DataTable dataTable);

    /**
     * Returns the label of the dimension config that will be shown in the GUI.
     *
     * @return the label
     */
    public String getLabel();

    /**
     * Gets errors.
     *
     * @return the errors
     */
    public List<PlotConfigurationError> getErrors();

    /**
     * Gets warnings.
     *
     * @return the warnings
     */
    public List<PlotConfigurationError> getWarnings();

    /**
     * Gets valid grouping types.
     *
     * @return the valid grouping types
     */
    public Vector<GroupingType> getValidGroupingTypes();

    /**
     * Gets value type.
     *
     * @return the value type
     */
    public ValueType getValueType();

    /**
     * Gets supported value types.
     *
     * @return the supported value types
     */
    public Set<DataTableColumn.ValueType> getSupportedValueTypes();

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId();

    /**
     * Is valid boolean.
     *
     * @return the boolean
     */
    public boolean isValid();

    /**
     * Is auto range required boolean.
     *
     * @return the boolean
     */
    public boolean isAutoRangeRequired();

    /**
     * Is logarithmic boolean.
     *
     * @return the boolean
     */
    public boolean isLogarithmic();

    /**
     * Is auto naming boolean.
     *
     * @return the boolean
     */
    public boolean isAutoNaming();

    /**
     * Is grouping boolean.
     *
     * @return the boolean
     */
    public boolean isGrouping();

    /**
     * Is nominal boolean.
     *
     * @return the boolean
     */
    public boolean isNominal();

    /**
     * Is numerical boolean.
     *
     * @return the boolean
     */
    public boolean isNumerical();

    /**
     * Is date boolean.
     *
     * @return the boolean
     */
    public boolean isDate();

    /**
     * Is using user defined lower bound boolean.
     *
     * @return the boolean
     */
    public boolean isUsingUserDefinedLowerBound();

    /**
     * Is using user defined upper bound boolean.
     *
     * @return the boolean
     */
    public boolean isUsingUserDefinedUpperBound();

    /**
     * Sets grouping.
     *
     * @param grouping the grouping
     */
    public void setGrouping(ValueGrouping grouping);

    /**
     * Sets data table column.
     *
     * @param column the column
     */
// public void setSortProvider(SortProvider sortProvider);
	public void setDataTableColumn(DataTableColumn column);

    /**
     * Sets user defined range.
     *
     * @param range the range
     */
    public void setUserDefinedRange(NumericalValueRange range);

    /**
     * Sets logarithmic.
     *
     * @param logarithmic the logarithmic
     */
    public void setLogarithmic(boolean logarithmic);

    /**
     * Sets auto naming.
     *
     * @param autoNaming the auto naming
     */
    public void setAutoNaming(boolean autoNaming);

    /**
     * Sets label.
     *
     * @param label the label
     */
    public void setLabel(String label);

    /**
     * Sets upper bound.
     *
     * @param upperBound the upper bound
     */
    public void setUpperBound(Double upperBound);

    /**
     * Sets lower bound.
     *
     * @param lowerBound the lower bound
     */
    public void setLowerBound(Double lowerBound);

    /**
     * Sets use user defined upper bound.
     *
     * @param useUpperBound the use upper bound
     */
    public void setUseUserDefinedUpperBound(boolean useUpperBound);

    /**
     * Sets use user defined lower bound.
     *
     * @param useLowerBound the use lower bound
     */
    public void setUseUserDefinedLowerBound(boolean useLowerBound);

    /**
     * Remove dimension config listener.
     *
     * @param l the l
     */
    public void removeDimensionConfigListener(DimensionConfigListener l);

    /**
     * Add dimension config listener.
     *
     * @param l the l
     */
    public void addDimensionConfigListener(DimensionConfigListener l);

    /**
     * Color scheme changed.
     */
    public void colorSchemeChanged();

    /**
     * Returns a {@link DateFormat} to be used for formatting dates on this axis.
     *
     * @return the date format used to format dates on this axis.
     */
    public DateFormat getDateFormat();

    /**
     * Sets user defined date format string.
     *
     * @param formatString the format string
     */
    public void setUserDefinedDateFormatString(String formatString);

    /**
     * Gets user defined date format string.
     *
     * @return the user defined date format string
     */
    public String getUserDefinedDateFormatString();

    /**
     * Sets use user defined date format.
     *
     * @param yes the yes
     */
    public void setUseUserDefinedDateFormat(boolean yes);

    /**
     * Is using user defined date format boolean.
     *
     * @return the boolean
     */
    boolean isUsingUserDefinedDateFormat();
}
