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

import com.rapidminer.gui.new_plotter.configuration.DimensionConfig.PlotDimension;
import com.rapidminer.gui.new_plotter.configuration.ValueSource.SeriesUsageType;

import java.util.*;


/**
 * Contains a list of {@link GroupCellKeyAndData}.
 *
 * @author Marius Helf
 */
public class GroupCellSeriesData implements Iterable<GroupCellKeyAndData> {

	private List<GroupCellKeyAndData> groupCellSeriesData = new LinkedList<GroupCellKeyAndData>();

    /**
     * Add group cell.
     *
     * @param groupCellKeyAndData the group cell key and data
     */
    public void addGroupCell(GroupCellKeyAndData groupCellKeyAndData) {
		groupCellSeriesData.add(groupCellKeyAndData);
	}

    /**
     * Clear.
     */
    public void clear() {
		groupCellSeriesData.clear();
	}

    /**
     * Group cell count int.
     *
     * @return the int
     */
    public int groupCellCount() {
		return groupCellSeriesData.size();
	}

    /**
     * Is empty boolean.
     *
     * @return the boolean
     */
    public boolean isEmpty() {
		return groupCellSeriesData.isEmpty();
	}

    /**
     * Gets distinct values.
     *
     * @param usageType the usage type
     * @param dimension the dimension
     * @return the distinct values
     */
    public Set<Double> getDistinctValues(SeriesUsageType usageType, PlotDimension dimension) {
		Set<Double> distinctValuesSet = new HashSet<Double>();
		for (GroupCellKeyAndData groupCellKeyAndData : groupCellSeriesData) {
			GroupCellData groupCellData = groupCellKeyAndData.getData();
			for (double value : groupCellData.getDataForUsageType(usageType).get(dimension)) {
				distinctValuesSet.add(value);
			}
		}
		return distinctValuesSet;
	}

	@Override
	public synchronized Iterator<GroupCellKeyAndData> iterator() {
		Iterator<GroupCellKeyAndData> i = null;
		synchronized (groupCellSeriesData) {
			i = groupCellSeriesData.iterator();
		}
		return i;
	}

    /**
     * Gets group cell key and data.
     *
     * @param seriesIdx the series idx
     * @return the group cell key and data
     */
    public GroupCellKeyAndData getGroupCellKeyAndData(int seriesIdx) {
		return groupCellSeriesData.get(seriesIdx);
	}
}
