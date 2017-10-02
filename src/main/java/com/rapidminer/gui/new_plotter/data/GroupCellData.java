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
package com.rapidminer.gui.new_plotter.data;

import com.rapidminer.gui.new_plotter.configuration.DimensionConfig;
import com.rapidminer.gui.new_plotter.configuration.DimensionConfig.PlotDimension;
import com.rapidminer.gui.new_plotter.configuration.ValueSource;
import com.rapidminer.gui.new_plotter.configuration.ValueSource.SeriesUsageType;

import java.util.HashMap;
import java.util.Map;


/**
 * The type Group cell data.
 *
 * @author Marius Helf
 */
public class GroupCellData {

    /**
     * The Dimension to data map.
     */
    Map<SeriesUsageType, Map<PlotDimension, double[]>> dimensionToDataMap = new HashMap<ValueSource.SeriesUsageType, Map<PlotDimension, double[]>>();

    /**
     * Gets data for usage type.
     *
     * @param usageType the usage type
     * @return the data for usage type
     */
    public Map<PlotDimension, double[]> getDataForUsageType(SeriesUsageType usageType) {
		return dimensionToDataMap.get(usageType);
	}

    /**
     * Sets data for usage type.
     *
     * @param usageType the usage type
     * @param data      the data
     */
    public void setDataForUsageType(SeriesUsageType usageType, Map<PlotDimension, double[]> data) {
		if (data != null) {
			dimensionToDataMap.put(usageType, data);
		} else {
			dimensionToDataMap.remove(usageType);
		}
	}

    /**
     * Returns the number of data points in the DOMAIN dimension of the MAIN_SERIES series of this
     * GroupCellData. All other usage types should have the same number of values.
     *
     * @return the size
     */
    public int getSize() {
		Map<PlotDimension, double[]> mainData = getDataForUsageType(SeriesUsageType.MAIN_SERIES);
		if (mainData == null) {
			return 0;
		}

		double[] domainData = mainData.get(PlotDimension.DOMAIN);
		if (domainData == null) {
			return 0;
		}

		return domainData.length;
	}

    /**
     * Initializes a map from Dimension to double[] for the given SeriesUsageType. Each double array
     * is initialized with size valueCount.
     * <p>
     * The created map is added to this GroupCellData and then returned to the caller.
     *
     * @param usageType  the usage type
     * @param dimensions the dimensions
     * @param valueCount the value count
     * @return the map
     */
    public Map<PlotDimension, double[]> initDataForUsageType(SeriesUsageType usageType, Iterable<PlotDimension> dimensions,
			int valueCount) {
		Map<PlotDimension, double[]> data = new HashMap<DimensionConfig.PlotDimension, double[]>();
		for (PlotDimension dimension : dimensions) {
			data.put(dimension, new double[valueCount]);
		}
		setDataForUsageType(usageType, data);
		return data;
	}
}
