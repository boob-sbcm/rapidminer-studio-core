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
package com.rapidminer.gui.renderer;

import com.rapidminer.gui.graphs.GraphCreator;
import com.rapidminer.gui.graphs.GraphViewer;
import com.rapidminer.gui.graphs.LayoutSelection;
import com.rapidminer.gui.processeditor.results.ResultDisplayTools;
import com.rapidminer.operator.IOContainer;
import com.rapidminer.operator.ports.InputPort;
import com.rapidminer.parameter.ParameterType;
import com.rapidminer.parameter.ParameterTypeBoolean;
import com.rapidminer.parameter.ParameterTypeStringCategory;
import com.rapidminer.parameter.UndefinedParameterError;
import com.rapidminer.report.Reportable;

import java.awt.*;
import java.util.List;


/**
 * This is the abstract renderer superclass for all renderers which should be a graph based on a
 * given {@link GraphCreator}.
 *
 * @author Ingo Mierswa
 */
public abstract class AbstractGraphRenderer extends AbstractRenderer {

    /**
     * The constant LAYOUT_BALLOON.
     */
    public static final String LAYOUT_BALLOON = "Balloon";

    /**
     * The constant LAYOUT_ISOM.
     */
    public static final String LAYOUT_ISOM = "ISOM";

    /**
     * The constant LAYOUT_KK_LAYOUT.
     */
    public static final String LAYOUT_KK_LAYOUT = "KKLayout";

    /**
     * The constant LAYOUT_FR_LAYOUT.
     */
    public static final String LAYOUT_FR_LAYOUT = "FRLayout";

    /**
     * The constant LAYOUT_CIRCLE.
     */
    public static final String LAYOUT_CIRCLE = "Circle";

    /**
     * The constant LAYOUT_SPRING.
     */
    public static final String LAYOUT_SPRING = "Spring";

    /**
     * The constant LAYOUT_TREE.
     */
    public static final String LAYOUT_TREE = "Tree";

    /**
     * The constant PARAMETER_LAYOUT.
     */
    public static final String PARAMETER_LAYOUT = "layout";

    /**
     * The constant PARAMETER_SHOW_NODE_LABELS.
     */
    public static final String PARAMETER_SHOW_NODE_LABELS = "show_node_labels";

    /**
     * The constant PARAMETER_SHOW_EDGE_LABELS.
     */
    public static final String PARAMETER_SHOW_EDGE_LABELS = "show_edge_labels";

    /**
     * The constant RENDERER_NAME.
     */
    public static final String RENDERER_NAME = "Graph View";

    /**
     * The constant LAYOUTS.
     */
    public static final String[] LAYOUTS = { LAYOUT_ISOM, LAYOUT_KK_LAYOUT, LAYOUT_FR_LAYOUT, LAYOUT_CIRCLE, LAYOUT_SPRING,
			LAYOUT_TREE, LAYOUT_BALLOON };

    /**
     * Gets graph creator.
     *
     * @param renderable  the renderable
     * @param ioContainer the io container
     * @return the graph creator
     */
    public abstract GraphCreator<String, String> getGraphCreator(Object renderable, IOContainer ioContainer);

    /**
     * Gets default layout.
     *
     * @return the default layout
     */
    public String getDefaultLayout() {
		return LAYOUT_FR_LAYOUT;
	}

	@Override
	public String getName() {
		return RENDERER_NAME;
	}

	@Override
	public Component getVisualizationComponent(Object renderable, IOContainer ioContainer) {
		GraphCreator<String, String> graphCreator = getGraphCreator(renderable, ioContainer);
		if (graphCreator != null) {
			return new GraphViewer<>(graphCreator);
		} else {
			return ResultDisplayTools.createErrorComponent("No data for graph creation.");
		}
	}

	@Override
	public Reportable createReportable(Object renderable, IOContainer ioContainer, int width, int height) {
		GraphCreator<String, String> graphCreator = getGraphCreator(renderable, ioContainer);
		if (graphCreator != null) {
			GraphViewer<String, String> viewer = new GraphViewer<>(graphCreator);
			viewer.setSize(width, height);

			LayoutSelection<String, String> layoutSelection = viewer.getLayoutSelection();
			try {
				layoutSelection.setSelectedItem(getParameter(PARAMETER_LAYOUT));
			} catch (UndefinedParameterError e) {
				// do nothing
			}

			viewer.setPaintEdgeLabels(getParameterAsBoolean(PARAMETER_SHOW_EDGE_LABELS));
			viewer.setPaintVertexLabels(getParameterAsBoolean(PARAMETER_SHOW_NODE_LABELS));

			return viewer;
		} else {
			return new DefaultReadable("No data for graph creation.");
		}
	}

	@Override
	public List<ParameterType> getParameterTypes(InputPort inputPort) {
		List<ParameterType> types = super.getParameterTypes(inputPort);

		ParameterTypeStringCategory layoutType = new ParameterTypeStringCategory(PARAMETER_LAYOUT,
				"Indicates which layout should be used for graph rendering.", LAYOUTS, getDefaultLayout());
		layoutType.setEditable(false);
		types.add(layoutType);

		types.add(new ParameterTypeBoolean(PARAMETER_SHOW_NODE_LABELS,
				"Indicates if the labels of the node should be visualized.", true));
		types.add(new ParameterTypeBoolean(PARAMETER_SHOW_EDGE_LABELS,
				"Indicates if the labels of the edges should be visualized.", true));

		return types;
	}
}
