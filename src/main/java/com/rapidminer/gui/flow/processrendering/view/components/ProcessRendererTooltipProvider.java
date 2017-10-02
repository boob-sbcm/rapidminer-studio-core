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
package com.rapidminer.gui.flow.processrendering.view.components;

import com.rapidminer.gui.flow.processrendering.model.ProcessRendererModel;
import com.rapidminer.gui.flow.processrendering.view.ProcessRendererView;
import com.rapidminer.gui.metadata.MetaDataRendererFactoryRegistry;
import com.rapidminer.gui.tools.components.ToolTipWindow.TipProvider;
import com.rapidminer.operator.IOObject;
import com.rapidminer.operator.ProcessRootOperator;
import com.rapidminer.operator.ProcessSetupError.Severity;
import com.rapidminer.operator.ports.Port;
import com.rapidminer.operator.ports.metadata.ExampleSetMetaData;
import com.rapidminer.operator.ports.metadata.MetaData;
import com.rapidminer.operator.ports.metadata.MetaDataError;

import java.awt.*;
import java.util.List;


/**
 * Provides tooltips for the {@link ProcessRendererView}.
 *
 * @author Marco Boeck
 * @since 6.4.0
 */
public class ProcessRendererTooltipProvider implements TipProvider {

	private ProcessRendererModel model;

    /**
     * Instantiates a new Process renderer tooltip provider.
     *
     * @param model the model
     */
    public ProcessRendererTooltipProvider(ProcessRendererModel model) {
		this.model = model;
	}

	@Override
	public Object getIdUnder(final Point point) {
		if (model.getConnectingPortSource() == null) {
			return model.getHoveringPort();
		} else {
			return null;
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public String getTip(final Object o) {
		Port port = (Port) o;
		StringBuilder tip = new StringBuilder();

		if (model.getDisplayedChain() instanceof ProcessRootOperator) {
			if (port.getPorts() == model.getDisplayedChain().getSubprocess(0).getInnerSources()) {
				int index = model.getDisplayedChain().getSubprocess(0).getInnerSources().getAllPorts().indexOf(port);
				List<String> locations = model.getDisplayedChain().getProcess().getContext().getInputRepositoryLocations();
				if (index >= 0 && index < locations.size()) {
					String dest = locations.get(index);
					tip.append("Loaded from: ").append(dest).append("<br/>");
				}
			} else if (port.getPorts() == model.getDisplayedChain().getSubprocess(0).getInnerSinks()) {
				int index = model.getDisplayedChain().getSubprocess(0).getInnerSinks().getAllPorts().indexOf(port);
				List<String> locations = model.getDisplayedChain().getProcess().getContext().getOutputRepositoryLocations();
				if (index >= 0 && index < locations.size()) {
					String dest = locations.get(index);
					tip.append("Stored at: ").append(dest).append("<br/>");
				}
			}
		}

		tip.append("<strong>");
		tip.append(port.getSpec());
		tip.append("</strong> (");
		tip.append(port.getName());
		tip.append(")<br/>");
		tip.append("<em>Meta data:</em> ");
		MetaData metaData = port.getMetaData();
		if (metaData != null) {
			if (metaData instanceof ExampleSetMetaData) {
				tip.append(((ExampleSetMetaData) metaData).getShortDescription());
			} else {
				tip.append(metaData.getDescription());
			}
			tip.append("<br/><em>Generated by:</em> ");
			tip.append(metaData.getGenerationHistoryAsHTML());
			tip.append("<br>");
		} else {
			tip.append("-<br/>");
		}
		IOObject data = port.getAnyDataOrNull();
		if (data != null) {
			tip.append("</br><em>Data:</em> ");
			tip.append(data.toString());
			tip.append("<br/>");
		}
		tip.append(port.getDescription());
		if (!port.getErrors().isEmpty()) {
			boolean hasErrors = false;
			boolean hasWarnings = false;
			for (MetaDataError error : port.getErrors()) {
				if (error.getSeverity() == Severity.ERROR) {
					hasErrors = true;
				}
				if (error.getSeverity() == Severity.WARNING) {
					hasWarnings = true;
				}
			}
			if (hasErrors) {
				tip.append("<br/><strong style=\"color:red\">");
				tip.append(port.getErrors().size());
				tip.append(" error(s):</strong>");
				for (MetaDataError error : port.getErrors()) {
					if (error.getSeverity() == Severity.ERROR) {
						tip.append("<br/> ");
						tip.append(error.getMessage());
					}
				}
			}
			if (hasWarnings) {
				tip.append("<br/><strong style=\"color:#FFA500\">");
				tip.append(port.getErrors().size());
				tip.append(" warnings(s):</strong>");
				for (MetaDataError error : port.getErrors()) {
					if (error.getSeverity() == Severity.WARNING) {
						tip.append("<br/> ");
						tip.append(error.getMessage());
					}
				}
			}
		}
		return tip.toString();
	}

	@SuppressWarnings("deprecation")
	@Override
	public Component getCustomComponent(final Object o) {
		Port hoveringPort = (Port) o;
		MetaData metaData = hoveringPort.getMetaData();
		return MetaDataRendererFactoryRegistry.getInstance().createRenderer(metaData);
	}
}
