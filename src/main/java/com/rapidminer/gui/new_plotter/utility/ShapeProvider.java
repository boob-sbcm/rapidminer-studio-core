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
package com.rapidminer.gui.new_plotter.utility;

import com.rapidminer.gui.new_plotter.configuration.SeriesFormat;
import com.rapidminer.gui.new_plotter.configuration.SeriesFormat.ItemShape;

import java.awt.Shape;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * The type Shape provider.
 *
 * @author Marius Helf
 */
public class ShapeProvider {

	private Map<Double, Shape> shapeMap;

    /**
     * Instantiates a new Shape provider.
     *
     * @param shapeMap the shape map
     */
    public ShapeProvider(Map<Double, Shape> shapeMap) {
		super();
		this.shapeMap = shapeMap;
	}

    /**
     * Instantiates a new Shape provider.
     *
     * @param categoryList the category list
     */
    public ShapeProvider(List<Double> categoryList) {
		super();
		this.shapeMap = createShapeMapping(categoryList);
	}

    /**
     * Gets shape for category.
     *
     * @param category the category
     * @return the shape for category
     */
    public Shape getShapeForCategory(double category) {
		return shapeMap.get(category);
	}

    /**
     * Create shape mapping map.
     *
     * @param categoryList the category list
     * @return the map
     */
    public static Map<Double, Shape> createShapeMapping(List<Double> categoryList) {
		List<ItemShape> itemShapeValues = Arrays.asList(SeriesFormat.ItemShape.values());
		Map<Double, Shape> shapeMapping = new HashMap<Double, Shape>();
		int idx = 1;
		for (Double category : categoryList) {
			if (idx < itemShapeValues.size()) {
				ItemShape itemShape = itemShapeValues.get(idx);
				if (itemShape != ItemShape.NONE) {
					shapeMapping.put(category, itemShape.getShape());
				}
			}
			++idx;
		}
		return shapeMapping;
	}

    /**
     * Sets shape for category.
     *
     * @param category the category
     * @param shape    the shape
     */
    public void setShapeForCategory(double category, Shape shape) {
		if (shape == null) {
			shapeMap.remove(category);
		} else {
			shapeMap.put(category, shape);
		}
	}

    /**
     * Max category count int.
     *
     * @return the int
     */
    public int maxCategoryCount() {
		return SeriesFormat.ItemShape.values().length - 1;
	}

    /**
     * Supports numerical values boolean.
     *
     * @return the boolean
     */
    public boolean supportsNumericalValues() {
		return false;
	}
}
