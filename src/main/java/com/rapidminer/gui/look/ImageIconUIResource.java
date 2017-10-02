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
package com.rapidminer.gui.look;

import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.plaf.UIResource;


/**
 * The UI for image icons.
 *
 * @author Ingo Mierswa
 */
public class ImageIconUIResource extends ImageIcon implements UIResource {

	private static final long serialVersionUID = 705603654836477091L;

    /**
     * Instantiates a new Image icon ui resource.
     */
    public ImageIconUIResource() {}

    /**
     * Instantiates a new Image icon ui resource.
     *
     * @param imageData the image data
     */
    public ImageIconUIResource(byte imageData[]) {
		super(imageData);
	}

    /**
     * Instantiates a new Image icon ui resource.
     *
     * @param image the image
     */
    public ImageIconUIResource(Image image) {
		super(image);
	}

    /**
     * Instantiates a new Image icon ui resource.
     *
     * @param location the location
     */
    public ImageIconUIResource(URL location) {
		super(location, location.toExternalForm());
	}

    /**
     * Instantiates a new Image icon ui resource.
     *
     * @param filename the filename
     */
    public ImageIconUIResource(String filename) {
		super(filename, filename);
	}
}
