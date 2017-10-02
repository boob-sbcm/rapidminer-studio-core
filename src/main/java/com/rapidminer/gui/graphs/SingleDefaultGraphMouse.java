/**
 * Copyright (C) 2001-2017 RapidMiner GmbH
 */
package com.rapidminer.gui.graphs;

import com.rapidminer.gui.graphs.plugins.ExtendedPickingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.CrossoverScalingControl;
import edu.uci.ics.jung.visualization.control.PluggableGraphMouse;
import edu.uci.ics.jung.visualization.control.ScalingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.TranslatingGraphMousePlugin;

import java.awt.event.InputEvent;


/**
 * This graph mouse does offer no mode selection but instead has all necessary features in its
 * default mode.
 *
 * @param <V> the type parameter
 * @param <E> the type parameter
 * @author Marco Boeck
 * @since 7.5.0
 */
public class SingleDefaultGraphMouse<V, E> extends PluggableGraphMouse {

	/** used by the scaling plugin for zoom in */
	private float in;

	/** used by the scaling plugin for zoom out */
	private float out;

	private ExtendedPickingGraphMousePlugin<V, E> pickingPlugin;
	private TranslatingGraphMousePlugin translatingPlugin;
	private ScalingGraphMousePlugin scalingPlugin;

    /**
     * Instantiates a new Single default graph mouse.
     *
     * @param in  the in
     * @param out the out
     */
    public SingleDefaultGraphMouse(float in, float out) {
		this.in = in;
		this.out = out;

		loadPlugins();
	}

    /**
     * Create and load the plugins to use.
     */
    protected void loadPlugins() {
		pickingPlugin = new ExtendedPickingGraphMousePlugin<V, E>();
		pickingPlugin.setRectangleSelectionEnabled(false);
		translatingPlugin = new TranslatingGraphMousePlugin(InputEvent.BUTTON1_MASK);
		scalingPlugin = new ScalingGraphMousePlugin(new CrossoverScalingControl(), 0, in, out);

		add(pickingPlugin);
		add(translatingPlugin);
		add(scalingPlugin);
	}

}
