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
package com.rapidminer.gui.new_plotter.templates.style;

import com.rapidminer.io.process.XMLTools;
import com.rapidminer.tools.AbstractObservable;
import com.rapidminer.tools.XMLException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.awt.*;
import java.util.UUID;


/**
 * Describes a color in RGB scheme and contains an alpha value for the color.
 *
 * @author Marco Boeck, Nils Woehler
 */
public class ColorRGB extends AbstractObservable<ColorRGB> {

    /**
     * The constant XML_TAG_NAME.
     */
    public static final String XML_TAG_NAME = "color";
	private static final String R_XML_TAG = "red";
	private static final String G_XML_TAG = "green";
	private static final String B_XML_TAG = "blue";
	private static final String ALPHA_XML_TAG = "alpha";

	/** r portion of the RGB color */
	private int r;

	/** g portion of the RGB color */
	private int g;

	/** b portion of the RGB color */
	private int b;

	/** alpha portion of the RGB color */
	private int alpha;

	private UUID id = UUID.randomUUID();

    /**
     * Creates a new {@link ColorRGB} object with the specified r g b components and an alpha value
     * of 255.
     *
     * @param r the r
     * @param g the g
     * @param b the b
     */
    public ColorRGB(int r, int g, int b) {
		this(r, g, b, 255);
	}

    /**
     * Creates a new {@link ColorRGB} object with the specified r g b and alpha components.
     *
     * @param r     the r
     * @param g     the g
     * @param b     the b
     * @param alpha the alpha
     */
    public ColorRGB(int r, int g, int b, int alpha) {
		// check for illegal values
		if (r > 255 || r < 0) {
			throw new IllegalArgumentException("r must be between 0 and 255, but was '" + r + "'!");
		}
		if (g > 255 || g < 0) {
			throw new IllegalArgumentException("g must be between 0 and 255, but was '" + g + "'!");
		}
		if (b > 255 || b < 0) {
			throw new IllegalArgumentException("b must be between 0 and 255, but was '" + b + "'!");
		}
		if (alpha > 255 || alpha < 0) {
			throw new IllegalArgumentException("alpha must be between 0 and 255, but was '" + alpha + "'!");
		}

		this.r = r;
		this.g = g;
		this.b = b;
		this.alpha = alpha;
	}

    /**
     * Instantiates a new Color rgb.
     *
     * @param element the element
     * @throws XMLException the xml exception
     */
    public ColorRGB(Element element) throws XMLException {
		if (!XML_TAG_NAME.equals(element.getTagName())) {
			throw new XMLException("<" + XML_TAG_NAME + "> expected.");
		}
		this.r = XMLTools.getTagContentsAsInt(element, R_XML_TAG);
		this.g = XMLTools.getTagContentsAsInt(element, G_XML_TAG);
		this.b = XMLTools.getTagContentsAsInt(element, B_XML_TAG);
		this.alpha = XMLTools.getTagContentsAsInt(element, ALPHA_XML_TAG);
	}

    /**
     * Returns the R component of this color.
     *
     * @return r r
     */
    public int getR() {
		return r;
	}

    /**
     * Returns the G component of this color.
     *
     * @return g g
     */
    public int getG() {
		return g;
	}

    /**
     * Returns the B component of this color.
     *
     * @return b b
     */
    public int getB() {
		return b;
	}

    /**
     * Returns the Alpha component of this color.
     *
     * @return alpha alpha
     */
    public int getAlpha() {
		return alpha;
	}

    /**
     * Gets hex value.
     *
     * @return the color as hex value with preceding '#'.
     */
    public String getHexValue() {
		return "#" + ColorRGB.convertColorRGBToHex(this);
	}

    /**
     * Sets hex value.
     *
     * @param hexValue the new color as hex value with preceding '#'.
     */
    public void setHexValue(String hexValue) {
		ColorRGB convertHexToColorRGB = ColorRGB.convertHexToColorRGB(hexValue.substring(1));
		this.r = convertHexToColorRGB.getR();
		this.g = convertHexToColorRGB.getG();
		this.b = convertHexToColorRGB.getB();
		this.alpha = convertHexToColorRGB.getAlpha();
		fireUpdate(this);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.alpha;
		result = prime * result + this.b;
		result = prime * result + this.g;
		result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
		result = prime * result + this.r;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ColorRGB other = (ColorRGB) obj;
		if (this.alpha != other.alpha) {
			return false;
		}
		if (this.b != other.b) {
			return false;
		}
		if (this.g != other.g) {
			return false;
		}
		if (this.id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!this.id.equals(other.id)) {
			return false;
		}
		if (this.r != other.r) {
			return false;
		}
		return true;
	}

    /**
     * Converts a {@link ColorRGB} input to a {@link Color} object.
     *
     * @param colorRGB the color rgb
     * @return color color
     */
    public static Color convertToColor(ColorRGB colorRGB) {
		return new Color(colorRGB.getR(), colorRGB.getG(), colorRGB.getB());
	}

    /**
     * Converts a {@link ColorRGB} input to a {@link Color} object including the alpha value.
     *
     * @param colorRGB the color rgb
     * @return color color
     */
    public static Color convertToColorWithAlpha(ColorRGB colorRGB) {
		return new Color(colorRGB.getR(), colorRGB.getG(), colorRGB.getB(), colorRGB.getAlpha());
	}

    /**
     * Converts a {@link Color} input to a {@link ColorRGB} object.
     *
     * @param color the color
     * @return color rgb
     */
    public static ColorRGB convertColorToColorRGB(Color color) {
		return new ColorRGB(color.getRed(), color.getGreen(), color.getBlue());
	}

    /**
     * Converts a {@link Color} input to a {@link ColorRGB} object including the alpha value.
     *
     * @param color the color
     * @return color rgb
     */
    public static ColorRGB convertColorWithAlphaToColorRGB(Color color) {
		return new ColorRGB(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
	}

	@Override
	public ColorRGB clone() {
		return new ColorRGB(r, g, b, alpha);
	}

    /**
     * Convert color rgb to hex string.
     *
     * @param color the color that should be converted to a hex string representation
     * @return the color as a hex string without the preceding '#'
     */
    public static String convertColorRGBToHex(ColorRGB color) {
		return String.format("%02x%02x%02x", color.getR(), color.getG(), color.getB());
	}

    /**
     * Convert hex to color rgb color rgb.
     *
     * @param hexString a color represented as a hex string without a preceding '#'.
     * @return the {@link ColorRGB} object
     */
    public static ColorRGB convertHexToColorRGB(String hexString) {
		return ColorRGB.convertColorToColorRGB(Color.decode("#" + hexString));
	}

    /**
     * To xml node.
     *
     * @param doc the doc
     * @return node node
     */
    public Node toXML(Document doc) {
		Element root = doc.createElement(XML_TAG_NAME);
		XMLTools.setTagContents(root, R_XML_TAG, String.valueOf(getR()));
		XMLTools.setTagContents(root, G_XML_TAG, String.valueOf(getG()));
		XMLTools.setTagContents(root, B_XML_TAG, String.valueOf(getB()));
		XMLTools.setTagContents(root, ALPHA_XML_TAG, String.valueOf(getAlpha()));
		return root;
	}
}
