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
package com.rapidminer.operator.libraries;

import com.rapidminer.OperatorLibraryService;
import com.rapidminer.gui.tools.VersionNumber;
import com.rapidminer.operator.Operator;
import com.rapidminer.operator.OperatorDescription;
import com.rapidminer.operator.ports.metadata.Precondition;
import com.rapidminer.tools.documentation.OperatorDocBundle;
import com.rapidminer.tools.plugin.Plugin;
import org.w3c.dom.Element;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;


/**
 * This is the abstract superclass for all {@link OperatorDescription}s that are stored inside an
 * {@link OperatorLibrary}. These provide additional functionality for accessing different versions
 * of these operators.
 *
 * @author Sebastian Land
 */
public abstract class LibraryOperatorDescription extends OperatorDescription {

	private static final String ATTRIBUTE_KEY = "key";
	private static final String ATTRIBUTE_GROUP_KEY = "group-key";
	private static final String ATTRIBUTE_ICON = "icon";

	private List<VersionNumber> operatorVersions = new LinkedList<VersionNumber>();
	private OperatorLibrary containingLibrary;

    /**
     * Instantiates a new Library operator description.
     *
     * @param containingLibrary      the containing library
     * @param fullyQualifiedGroupKey the fully qualified group key
     * @param key                    the key
     * @param clazz                  the clazz
     * @param classLoader            the class loader
     * @param iconName               the icon name
     * @param provider               the provider
     * @param bundle                 the bundle
     * @param availableVersions      the available versions
     */
    public LibraryOperatorDescription(OperatorLibrary containingLibrary, String fullyQualifiedGroupKey, String key,
			Class<? extends Operator> clazz, ClassLoader classLoader, String iconName, Plugin provider,
			OperatorDocBundle bundle, List<VersionNumber> availableVersions) {
		super(fullyQualifiedGroupKey, key, clazz, classLoader, iconName, provider, bundle);
		this.containingLibrary = containingLibrary;
		this.operatorVersions.addAll(availableVersions);
	}

    /**
     * Operator for constructing these descriptions from xml that has been generated with the
     * {@link #writeXML(Element)} method.
     * <p>
     * Please notice that you have to regularly add all defined operator versions using the
     * {@link #addVersion(VersionNumber)} method.
     *
     * @param operatorLibrary the operator library
     * @param element         the element
     * @param clazz           the clazz
     */
    public LibraryOperatorDescription(OperatorLibrary operatorLibrary, Element element, Class<? extends Operator> clazz) {
		super(element.getAttribute(ATTRIBUTE_GROUP_KEY), element.getAttribute(ATTRIBUTE_KEY), clazz, clazz.getClassLoader(),
				element.getAttribute(ATTRIBUTE_ICON), null, operatorLibrary.getDocumentationBundle());
		this.containingLibrary = operatorLibrary;
	}

    /**
     * This returns a list of all defined version numbers.
     *
     * @return the operator versions
     */
    public final List<VersionNumber> getOperatorVersions() {
		return operatorVersions;
	}

    /**
     * This adds the given version to the existing numbers of versions. This has to be called by
     * subclasses in order to publish a new version of an operator! This ensures that the listeners
     * of the {@link OperatorLibraryService} will be informed.
     *
     * @param versionNumber the version number
     */
    protected final void addVersion(VersionNumber versionNumber) {
		operatorVersions.add(versionNumber);
		OperatorLibraryService.getService().notifiyOperatorChanged(this);
	}

    /**
     * This returns the collection of all Preconditions that exist for the i-th port. It may return
     * an empty collection if no preconditions have been specified.
     *
     * @param i the
     * @return the port preconditions
     */
    public abstract Collection<? extends Precondition> getPortPreconditions(int i);

    /**
     * This method returns the library this description is contained in.
     *
     * @return the library
     */
    public OperatorLibrary getLibrary() {
		return containingLibrary;
	}

    /**
     * This method saves all settings into the given element.
     *
     * @param operatorElement the operator element
     */
    public void writeXML(Element operatorElement) {
		operatorElement.setAttribute(ATTRIBUTE_KEY, getKey());
		operatorElement.setAttribute(ATTRIBUTE_GROUP_KEY, getGroup());
		operatorElement.setAttribute(ATTRIBUTE_ICON, getIconName());
	}
}
