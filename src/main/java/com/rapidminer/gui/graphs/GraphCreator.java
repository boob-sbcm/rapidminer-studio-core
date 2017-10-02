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

import java.awt.Paint;
import java.util.Set;

import javax.swing.JComponent;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.renderers.Renderer;


/**
 * Creates a graph which can be displayed by the graph viewer. This class serves as a sort of graph
 * model combined with some methods tuning the displaying of nodes and edges in the graph.
 *
 * @param <V> the type parameter
 * @param <E> the type parameter
 * @author Ingo Mierswa, Marco Boeck
 */
public interface GraphCreator<V, E> {

    /**
     * The constant EDGE_SHAPE_LINE.
     */
    public static final int EDGE_SHAPE_LINE = 0;

    /**
     * The constant EDGE_SHAPE_QUAD_CURVE.
     */
    public static final int EDGE_SHAPE_QUAD_CURVE = 1;

    /**
     * The constant EDGE_SHAPE_CUBIC_CURVE.
     */
    public static final int EDGE_SHAPE_CUBIC_CURVE = 2;

    /**
     * The constant EDGE_SHAPE_BENT_LINE.
     */
    public static final int EDGE_SHAPE_BENT_LINE = 3;

    /**
     * The constant EDGE_SHAPE_WEDGE.
     */
    public static final int EDGE_SHAPE_WEDGE = 4;

    /**
     * Creates the graph.  @return the graph
     *
     * @return the graph
     */
    public Graph<V, E> createGraph();

    /**
     * Returns the name that should be displayed in each vertex. May return null (no name will be
     * shown for this node).
     *
     * @param id the id
     * @return the vertex name
     */
    public String getVertexName(V id);

    /**
     * Returns the text that should be displayed as tool tip for each vertex. May return null (no
     * tool tip will be shown for this node).
     *
     * @param id the id
     * @return the vertex tool tip
     */
    public String getVertexToolTip(V id);

    /**
     * Returns the text that should be displayed at the edge with the given id. May return null (no
     * text will be shown for this edge).
     *
     * @param id the id
     * @return the edge name
     */
    public String getEdgeName(E id);

    /**
     * Returns the strength of the edge. Should be normalized between 0 and 1.  @param id the id
     *
     * @param id the id
     * @return the edge strength
     */
    public double getEdgeStrength(E id);

    /**
     * Returns the shape of the edges. Must be one out of the constants {@link #EDGE_SHAPE_LINE},
     * {@link #EDGE_SHAPE_QUAD_CURVE}, {@link #EDGE_SHAPE_CUBIC_CURVE},
     * {@link #EDGE_SHAPE_BENT_LINE}, or {@link #EDGE_SHAPE_WEDGE}.
     *
     * @return the edge shape
     */
    public int getEdgeShape();

    /**
     * Returns the minimal height for leafs. Might be important for graphs where the leafs should be
     * rendered in some specialized way. May return -1 (no minimal height).
     *
     * @return the min leaf height
     */
    public int getMinLeafHeight();

    /**
     * Returns the minimal width for leafs. Might be important for graphs where the leafs should be
     * rendered in some specialized way. May return -1 (no minimal width).
     *
     * @return the min leaf width
     */
    public int getMinLeafWidth();

    /**
     * Returns true if the vertex with the given id should use a bold font.  @param id the id
     *
     * @param id the id
     * @return the boolean
     */
    public boolean isBold(V id);

    /**
     * Returns true if the edge labels should be rotated.  @return the boolean
     *
     * @return the boolean
     */
    public boolean isRotatingEdgeLabels();

    /**
     * Returns the transformer which maps vertices to the paint (color) used for drawing. May return
     * null.
     *
     * @param viewer the viewer
     * @return the vertex paint transformer
     */
    public Transformer<V, Paint> getVertexPaintTransformer(VisualizationViewer<V, E> viewer);

    /**
     * Returns the renderer used for the nodes. May return null (use default renderer).  @return the vertex renderer
     *
     * @return the vertex renderer
     */
    public Renderer.Vertex<V, E> getVertexRenderer();

    /**
     * Returns the renderer used for node labels. May return null (use default renderer).  @return the vertex label renderer
     *
     * @return the vertex label renderer
     */
    public Renderer.VertexLabel<V, E> getVertexLabelRenderer();

    /**
     * Returns the renderer for edge labels. May return null (use default renderer).  @return the edge label renderer
     *
     * @return the edge label renderer
     */
    public Renderer.EdgeLabel<V, E> getEdgeLabelRenderer();

    /**
     * Returns true if the edge label should be decorated.  @return the boolean
     *
     * @return the boolean
     */
    public boolean isEdgeLabelDecorating();

    /**
     * Returns true if the node with the given id is a leaf.  @param id the id
     *
     * @param id the id
     * @return the boolean
     */
    public boolean isLeaf(V id);

    /**
     * Returns the viewer for objects. Will be notified after clicks on nodes. May return null (no
     * object viewer is used).
     *
     * @return the object viewer
     */
    public GraphObjectViewer getObjectViewer();

    /**
     * Returns the object for the given id, e.g. a cluster node. The result of this method will be
     * given to the object viewer after a mouse click.
     *
     * @param id the id
     * @return the object
     */
    public Object getObject(V id);

    /**
     * Returns the number of option componenents which will be added to the control panel of the
     * {@link GraphViewer}.
     *
     * @return the number of option components
     */
    public int getNumberOfOptionComponents();

    /**
     * Returns the desired option componenents which will be added to the control panel of the
     * {@link GraphViewer}.
     *
     * @param viewer the viewer
     * @param index  the index
     * @return the option component
     */
    public JComponent getOptionComponent(GraphViewer<?, ?> viewer, int index);

    /**
     * Returns the label offset. In most case, using -1 is just fine (default offset). Some tree
     * like graphs might prefer to use 0 since they manage the offset themself.
     *
     * @return the label offset
     */
    public int getLabelOffset();

    /**
     * Indicates if the edge labels should be initially shown.  @return the boolean
     *
     * @return the boolean
     */
    public boolean showEdgeLabelsDefault();

    /**
     * Indicates if the vertex labels should be initially shown.  @return the boolean
     *
     * @return the boolean
     */
    public boolean showVertexLabelsDefault();

    /**
     * For directed graphs, the edges on the path back to the root will return true.  @param selectedVertexes the selected vertexes
     *
     * @param selectedVertexes the selected vertexes
     * @param id               the id
     * @return the boolean
     */
    public default boolean isEdgeOnSelectedPath(Set<V> selectedVertexes, E id) {
		return false;
	}

    /**
     * For directed graphs, the vertices on the path back to the root will return true.  @param selectedVertexes the selected vertexes
     *
     * @param selectedVertexes the selected vertexes
     * @param id               the id
     * @return the boolean
     */
    public default boolean isVertexOnSelectedPath(Set<V> selectedVertexes, V id) {
		return false;
	}

    /**
     * Returns the scale of the vertex. Should be normalized between 0 and 1. Only used if
     * {@link #isVertexCircle()} returns {@code true}.
     *
     * @param id the id
     * @return the vertex scale
     */
    public default double getVertexScale(V id) {
		return 1d;
	}

    /**
     * Returns if the given vertex should be displayed as a perfect circle. Otherwise it will be
     * displayed as ellipsis. Note that circle vertexes will NOT care about their vertex label size
     * and assume a default size, scaled by {@link #getVertexScale(Object)}. Default: {@code false}
     *
     * @param id the id
     * @return the boolean
     */
    public default boolean isVertexCircle(V id) {
		return false;
	}
}
