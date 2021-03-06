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
package com.rapidminer.gui.metadata;

import com.rapidminer.operator.ports.metadata.MetaData;
import com.rapidminer.repository.gui.ToolTipProviderHelper;

import java.awt.*;


/**
 * Provides a custom renderer added by the {@link ToolTipProviderHelper} for subclasses of
 * {@link MetaData}.
 *
 * @author Simon Fischer, Gabor Makrai
 */
public interface MetaDataRendererFactory {

    /**
     * Gets supported class.
     *
     * @return the supported class
     */
    public Class<? extends MetaData> getSupportedClass();

    /**
     * Create renderer component.
     *
     * @param metaData the meta data
     * @return the component
     */
    public Component createRenderer(MetaData metaData);
}
