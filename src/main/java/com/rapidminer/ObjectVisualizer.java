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
package com.rapidminer;

/**
 * Interface managing the visualization of objects. This might be a dialog showing the feature
 * values ({@link com.rapidminer.gui.ExampleVisualizer}) or more sophisticated methods like
 * displaying a text or playing a piece of music. Please note that GUI components should not be
 * constructed in the contstructor but in the method {@link #startVisualization(Object)} in order to
 * ensure that the visualizer can be constructed also in environments where graphical user
 * interfaces are not allowed.
 *
 * @author Michael Wurst, Ingo Mierswa
 */
public interface ObjectVisualizer {

    /**
     * Start visualization.
     *
     * @param objId the obj id
     */
    public void startVisualization(Object objId);

    /**
     * Stop visualization.
     *
     * @param objId the obj id
     */
    public void stopVisualization(Object objId);

    /**
     * Gets title.
     *
     * @param objId the obj id
     * @return the title
     */
    public String getTitle(Object objId);

    /**
     * Is capable to visualize boolean.
     *
     * @param objId the obj id
     * @return the boolean
     */
    public boolean isCapableToVisualize(Object objId);

    /**
     * Gets detail data.
     *
     * @param objId     the obj id
     * @param fieldName the field name
     * @return the detail data
     */
    public String getDetailData(Object objId, String fieldName);

    /**
     * Get field names string [ ].
     *
     * @param objId the obj id
     * @return the string [ ]
     */
    public String[] getFieldNames(Object objId);

}
