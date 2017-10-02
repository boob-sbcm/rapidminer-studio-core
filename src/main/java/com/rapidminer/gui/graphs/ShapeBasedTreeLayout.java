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
package com.rapidminer.gui.graphs;

import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Forest;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.Tree;
import org.apache.commons.collections15.Transformer;
import org.apache.commons.collections15.map.LazyMap;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.*;
import java.util.List;


/**
 * This layout algorithm takes the shapes of the trees into account and performs a non-overlapping
 * layout.
 *
 * @param <V> the type parameter
 * @param <E> the type parameter
 * @author Ingo Mierswa
 */
public class ShapeBasedTreeLayout<V, E> implements Layout<V, E> {

	private static final int DEFAULT_WIDTH = 70;

	private static final int DEFAULT_HEIGHT = 70;

	private static final int MARGIN = 15;

	private Dimension size = new Dimension(600, 600);

	private Forest<V, E> graph;

    /**
     * The Locations.
     */
    protected Map<V, Point2D> locations = LazyMap.decorate(new HashMap<V, Point2D>(), new Transformer<V, Point2D>() {

		@Override
		public Point2D transform(V arg0) {
			return new Point2D.Double();
		}
	});

    /**
     * Gets atomics.
     *
     * @param p the p
     * @return the atomics
     */
    public List<V> getAtomics(V p) {
		List<V> v = new ArrayList<V>();
		getAtomics(p, v);
		return v;
	}

	private void getAtomics(V p, List<V> v) {
		for (V c : graph.getSuccessors(p)) {
			if (graph.getSuccessors(c).size() == 0) {
				v.add(c);
			} else {
				getAtomics(c, v);
			}
		}
	}

	private Collection<V> roots;

	private Transformer<V, Shape> shapeTransformer;

    /**
     * Instantiates a new Shape based tree layout.
     *
     * @param g                the g
     * @param shapeTransformer the shape transformer
     */
    public ShapeBasedTreeLayout(Forest<V, E> g, Transformer<V, Shape> shapeTransformer) {
		this.graph = g;
		this.roots = getRoots(g);
		this.shapeTransformer = shapeTransformer;
		calculateLocations();
	}

	private Collection<V> getRoots(Forest<V, E> forest) {
		Set<V> roots = new HashSet<V>();
		for (Tree<V, E> tree : forest.getTrees()) {
			roots.add(tree.getRoot());
		}
		return roots;
	}

    /**
     * Gets current size.
     *
     * @return the current size
     */
    public Dimension getCurrentSize() {
		return size;
	}

	private void calculateLocations() {
		double xOffset = 20;
		double yOffset = 30;

		if (roots.size() > 0 && graph != null) {
			for (V v : roots) {
				calculateLocations(v, xOffset, yOffset);
				double currentWidth = calculateWidth(v);
				xOffset += currentWidth;
				xOffset += MARGIN;
			}
		}
	}

    /**
     * Calculate locations.
     *
     * @param v       the v
     * @param xOffset the x offset
     * @param yOffset the y offset
     */
    void calculateLocations(V v, double xOffset, double yOffset) {
		double currentWidth = calculateWidth(v);
		double currentHeight = calculateHeight(v);

		setPosition(v, xOffset + currentWidth / 2, yOffset + currentHeight / 2);

		// handle children
		yOffset += DEFAULT_HEIGHT;
		if (currentHeight > DEFAULT_HEIGHT) {
			yOffset += currentHeight;
		}

		int childrenNum = graph.getSuccessors(v).size();
		if (childrenNum != 0) {
			boolean first = true;
			for (V element : graph.getSuccessors(v)) {
				if (!first) {
					xOffset += MARGIN;
				}

				calculateLocations(element, xOffset, yOffset);

				double totalChildrenWidth = calculateWidth(element);
				xOffset += totalChildrenWidth;

				first = false;
			}
		}
	}

	private double calculateWidth(V v) {
		double childrenWidthSum = 0;
		int childrenNum = graph.getSuccessors(v).size();
		if (childrenNum != 0) {
			boolean first = true;
			for (V element : graph.getSuccessors(v)) {
				if (!first) {
					childrenWidthSum += MARGIN;
				}
				childrenWidthSum += calculateWidth(element);
				first = false;
			}
		}

		double width = DEFAULT_WIDTH;
		if (this.shapeTransformer != null) {
			Shape shape = this.shapeTransformer.transform(v);
			if (shape != null) {
				width = shape.getBounds().getWidth();
			}
		}
		double size = Math.max(width, childrenWidthSum);
		size = Math.max(0, size);
		return size;
	}

	private double calculateHeight(V v) {
		double height = DEFAULT_HEIGHT;
		if (this.shapeTransformer != null) {
			Shape shape = this.shapeTransformer.transform(v);
			if (shape != null) {
				height = shape.getBounds().getHeight();
			}
		}
		return height;
	}

	@Override
	public void setSize(Dimension size) {
		this.size = size;
		calculateLocations();
	}

	private void setPosition(V vertex, double x, double y) {
		locations.get(vertex).setLocation(new Point2D.Double(x, y));
	}

	@Override
	public Graph<V, E> getGraph() {
		return graph;
	}

	@Override
	public Dimension getSize() {
		return size;
	}

	@Override
	public void initialize() {}

	@Override
	public boolean isLocked(V v) {
		return false;
	}

	@Override
	public void lock(V v, boolean state) {}

	@Override
	public void reset() {}

	@Override
	public void setGraph(Graph<V, E> graph) {
		if (graph instanceof Forest) {
			this.graph = (Forest<V, E>) graph;
			calculateLocations();
		} else {
			throw new IllegalArgumentException("graph must be a forest");
		}
	}

	@Override
	public void setInitializer(Transformer<V, Point2D> initializer) {}

    /**
     * Gets center.
     *
     * @return the center
     */
    public Point2D getCenter() {
		return new Point2D.Double(size.getWidth() / 2, size.getHeight() / 2);
	}

	@Override
	public void setLocation(V v, Point2D location) {
		locations.get(v).setLocation(location);
	}

	@Override
	public Point2D transform(V v) {
		return locations.get(v);
	}
}
