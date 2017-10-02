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
package com.rapidminer.operator;

import com.rapidminer.core.license.ProductConstraintManager;
import com.rapidminer.gui.tools.SwingTools;
import com.rapidminer.io.process.XMLTools;
import com.rapidminer.license.License;
import com.rapidminer.license.LicenseEvent;
import com.rapidminer.license.LicenseEvent.LicenseEventType;
import com.rapidminer.license.LicenseManagerListener;
import com.rapidminer.tools.*;
import com.rapidminer.tools.documentation.OperatorDocBundle;
import com.rapidminer.tools.documentation.OperatorDocumentation;
import com.rapidminer.tools.plugin.Plugin;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


/**
 * Data container for name, class, short name, path and the (very short) description of an operator.
 * If the corresponding operator is not marked as deprecated the deprecation info string should be
 * null. If the icon string is null, the group icon will be used.
 *
 * @author Ingo Mierswa
 */
public class OperatorDescription implements Comparable<OperatorDescription> {

	private static final ImageIcon[] EMPTY_ICONS = new ImageIcon[3];

	/** the small icon for the constraint violation operators */
	private static final ImageIcon UNSUPPORTED_ICON_SMALL = SwingTools
			.createIcon("16/" + I18N.getMessage(I18N.getGUIBundle(), "gui.constraint.operator.unsupported_datasource.icon"));

	/** the icon for the constraint violation operators */
	private static final ImageIcon UNSUPPORTED_ICON = SwingTools
			.createIcon("24/" + I18N.getMessage(I18N.getGUIBundle(), "gui.constraint.operator.unsupported_datasource.icon"));

	/** the large icon for the constraint violation operators */
	private static final ImageIcon UNSUPPORTED_ICON_LARGE = SwingTools
			.createIcon("48/" + I18N.getMessage(I18N.getGUIBundle(), "gui.constraint.operator.unsupported_datasource.icon"));

	private static final int DEFAULT_PRIORITY = 0;

    /**
     * The constant EXTENSIONS_GROUP_IDENTIFIER.
     */
    public static final String EXTENSIONS_GROUP_IDENTIFIER = "extensions";

	private final String key;
	private final Class<? extends Operator> clazz;
	private List<String> replacesDeprecatedKeys;

	private final OperatorDocumentation documentation;

	private ImageIcon[] icons;

	private String fullyQualifiedGroupKey;

	private int priority = DEFAULT_PRIORITY;;

	/**
	 * @deprecated Only used for Weka
	 */
	@Deprecated
	private final String deprecationInfo = null;

	private String iconName;

	private final Plugin provider;

	private boolean enabled = true;

	/**
	 * Flag that indicates whether the operator is using the extension grouping, i.e. whether the
	 * group tree root is the "extensions.EXTENSION_NAME" group instead of the default group tree
	 * root.
	 */
	private boolean isUsingExtensionTreeRoot = false;

	/**
	 * flag indicating if the operator is supported by the license
	 */
	private Boolean isSupportedByLicense;

    /**
     * Parses an operator in the RM 5.0 xml standard for operator definitions. In contrast to
     * earlier versions, the {@link OperatorDescription} does not register themselves on the
     * OperatorTree. This is now handled centralized by the {@link OperatorService}.
     *
     * @param fullyQualifiedGroupKey the fully qualified group key
     * @param element                the element
     * @param classLoader            the class loader
     * @param provider               the provider
     * @param bundle                 the bundle
     * @throws ClassNotFoundException the class not found exception
     * @throws XMLException           the xml exception
     */
    @SuppressWarnings("unchecked")
	public OperatorDescription(final String fullyQualifiedGroupKey, final Element element, final ClassLoader classLoader,
			final Plugin provider, final OperatorDocBundle bundle) throws ClassNotFoundException, XMLException {
		this.provider = provider;
		this.fullyQualifiedGroupKey = fullyQualifiedGroupKey;
		this.isSupportedByLicense = null;

		key = XMLTools.getTagContents(element, "key", true);

		String priorityString = XMLTools.getTagContents(element, "priority");
		if (priorityString != null) {
			try {
				this.priority = Integer.valueOf(priorityString);
			} catch (NumberFormatException e) {
				this.priority = DEFAULT_PRIORITY;
			}
		}

		Class<?> generatedClass = Class.forName(XMLTools.getTagContents(element, "class", true).trim(), true, classLoader);
		this.clazz = (Class<? extends Operator>) generatedClass;

		this.documentation = (OperatorDocumentation) bundle.getObject("operator." + key);
		if (documentation.getName().equals("")) {
			documentation.setName(key);
			documentation.setDocumentation("Operator's description is missing in referenced OperatorDoc.");
		}

		NodeList children = element.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			if (child instanceof Element && ((Element) child).getTagName().equals("replaces")) {
				setIsReplacementFor(((Element) child).getTextContent());
			}
		}

		// we need to know when the license changes because operators may become
		// supported/unsupported, which results in a changed icon */
		ProductConstraintManager.INSTANCE.registerLicenseManagerListener(new LicenseManagerListener() {

			@Override
			public <S, C> void handleLicenseEvent(final LicenseEvent<S, C> event) {
				if (event.getType() == LicenseEventType.ACTIVE_LICENSE_CHANGED) {
					isSupportedByLicense = null;
					updateIcons();
				}
			}
		});
		setIconName(XMLTools.getTagContents(element, "icon"));
		updateIcons();
	}

    /**
     * Constructor for programmatic (non-parsed) creation of OperatorDescriptions, e.g. by a
     * {@link GenericOperatorFactory}.
     *
     * @param fullyQualifiedGroupKey the fully qualified group key
     * @param key                    the key
     * @param clazz                  the clazz
     * @param classLoader            the class loader
     * @param iconName               the icon name
     * @param provider               the provider
     */
    public OperatorDescription(final String fullyQualifiedGroupKey, final String key, final Class<? extends Operator> clazz,
			final ClassLoader classLoader, final String iconName, final Plugin provider) {
		this(fullyQualifiedGroupKey, key, clazz, classLoader, iconName, provider, null);
	}

    /**
     * Constructor for programmatic (non-parsed) creation of OperatorDescriptions, e.g. by a
     * {@link GenericOperatorFactory}. Additionally this allows to specify an operator documentation
     * bundle where the docu is retrieved from.
     *
     * @param fullyQualifiedGroupKey the fully qualified group key
     * @param key                    the key
     * @param clazz                  the clazz
     * @param classLoader            the class loader
     * @param iconName               the icon name
     * @param provider               the provider
     * @param bundle                 the bundle
     */
    public OperatorDescription(final String fullyQualifiedGroupKey, final String key, final Class<? extends Operator> clazz,
			final ClassLoader classLoader, final String iconName, final Plugin provider, final OperatorDocBundle bundle) {
		this.isSupportedByLicense = null;
		this.key = key;
		this.clazz = clazz;
		this.fullyQualifiedGroupKey = fullyQualifiedGroupKey;
		this.provider = provider;
		if (bundle == null) {
			this.documentation = new OperatorDocumentation(key);
		} else {
			this.documentation = (OperatorDocumentation) bundle.getObject("operator." + key);
			if (documentation.getName().equals("")) {
				documentation.setName(key);
				documentation.setDocumentation("Operator's description is missing in referenced OperatorDoc.");
			}
		}

		// we need to know when the license changes because operators may become
		// supported/unsupported, which results in a changed icon */
		ProductConstraintManager.INSTANCE.registerLicenseManagerListener(new LicenseManagerListener() {

			@Override
			public <S, C> void handleLicenseEvent(final LicenseEvent<S, C> event) {
				if (event.getType() == LicenseEventType.ACTIVE_LICENSE_CHANGED) {
					refresh();
				}
			}
		});

		setIconName(iconName);
		updateIcons();
	}

    /**
     * Creates a new operator description object. If the corresponding operator is not marked as
     * deprecated the deprecation info string should be null. If the icon string is null, the group
     * icon will be used.
     *
     * @param classLoader     the class loader
     * @param key             the key
     * @param name            the name
     * @param className       the class name
     * @param group           the group
     * @param iconName        the icon name
     * @param deprecationInfo the deprecation info
     * @param provider        the provider
     * @throws ClassNotFoundException the class not found exception
     * @deprecated No I18N support.
     */
    @Deprecated
	public OperatorDescription(final ClassLoader classLoader, final String key, final String name, final String className,
			final String group, final String iconName, final String deprecationInfo, final Plugin provider)
					throws ClassNotFoundException {
		this(classLoader, key, name, className, null, null, group, iconName, deprecationInfo, provider);
	}

    /**
     * Creates an operator description with the given fields.
     *
     * @param classLoader      the class loader
     * @param key              the key
     * @param name             the name
     * @param className        the class name
     * @param shortDescription the short description
     * @param longDescription  the long description
     * @param groupName        the group name
     * @param iconName         the icon name
     * @param deprecationInfo  the deprecation info
     * @param provider         the provider
     * @throws ClassNotFoundException the class not found exception
     * @deprecated This constructor cannot provide an internationalization mechanism since             description is not taken from operator documentation bundle.
     */
    @SuppressWarnings("unchecked")
	@Deprecated
	public OperatorDescription(final ClassLoader classLoader, final String key, final String name, final String className,
			final String shortDescription, final String longDescription, final String groupName, final String iconName,
			final String deprecationInfo, final Plugin provider) throws ClassNotFoundException {
		this.key = key;

		this.clazz = (Class<? extends Operator>) Class.forName(className, true, classLoader);
		this.documentation = new OperatorDocumentation(name);
		this.documentation.setSynopsis(shortDescription);
		this.documentation.setDocumentation(longDescription);
		this.documentation.setDeprecation(deprecationInfo);

		this.fullyQualifiedGroupKey = groupName;

		this.provider = provider;

		setIconName(iconName);
		updateIcons();
	}

    /**
     * This constructor remains for compatibility reasons. Please use one of the non deprecated
     * alternatives.
     *
     * @param key         the key
     * @param clazz       the clazz
     * @param group       the group
     * @param classLoader the class loader
     * @param iconName    the icon name
     * @param provider    the provider
     */
    @Deprecated
	public OperatorDescription(final String key, final Class<? extends Operator> clazz, final GroupTree group,
			final ClassLoader classLoader, final String iconName, final Plugin provider) {
		this(group.getFullyQualifiedKey(), key, clazz, classLoader, iconName, provider);
	}

    /**
     * This constructor remains for compatibility reasons. Please use one of the non deprecated
     * alternatives.
     *
     * @param key         the key
     * @param clazz       the clazz
     * @param groupTree   the group tree
     * @param classLoader the class loader
     * @param iconName    the icon name
     * @param provider    the provider
     * @param bundle      the bundle
     */
    @Deprecated
	public OperatorDescription(final String key, final Class<? extends Operator> clazz, final GroupTree groupTree,
			final ClassLoader classLoader, final String iconName, final Plugin provider, final OperatorDocBundle bundle) {
		this(groupTree.getFullyQualifiedKey(), key, clazz, classLoader, iconName, provider);
	}

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
		return getOperatorDocumentation().getName();
	}

    /**
     * Gets short name.
     *
     * @return the short name
     */
    public String getShortName() {
		return getOperatorDocumentation().getShortName();
	}

    /**
     * Gets tags.
     *
     * @return the tags
     */
    public List<String> getTags() {
		return getOperatorDocumentation().getTags();
	}

    /**
     * Gets operator class.
     *
     * @return the operator class
     */
    public Class<? extends Operator> getOperatorClass() {
		return clazz;
	}

    /**
     * Gets short description.
     *
     * @return the short description
     */
    public String getShortDescription() {
		return getOperatorDocumentation().getSynopsis();
	}

    /**
     * Gets long description html.
     *
     * @return the long description html
     */
    public String getLongDescriptionHTML() {
		OperatorDocumentation operatorDocumentation = getOperatorDocumentation();
		if (operatorDocumentation.getDocumentation() != null) {
			return operatorDocumentation.getDocumentation();
		}
		if (operatorDocumentation.getSynopsis() != null) {
			return operatorDocumentation.getSynopsis();
		}
		return "";
	}

    /**
     * Gets operator documentation.
     *
     * @return the operator documentation
     */
    public OperatorDocumentation getOperatorDocumentation() {
		return documentation;
	}

    /**
     * This returns the qualified, dot separated key of the containing group.
     *
     * @return the group
     */
    public String getGroup() {
		return fullyQualifiedGroupKey;
	}

    /**
     * This returns the actual group name as displayed in RapidMiner.
     *
     * @return the group name
     */
    public String getGroupName() {
		int pos = fullyQualifiedGroupKey.lastIndexOf(".");
		if (pos == -1) {
			return fullyQualifiedGroupKey;
		} else {
			return fullyQualifiedGroupKey.substring(pos + 1);
		}
	}

    /**
     * Gets icon.
     *
     * @return the icon
     */
    public ImageIcon getIcon() {
		return getIcons()[1];
	}

    /**
     * The priority of the operator, defined in the operatorsXYZ.xml via the {@code <priority>} tag.
     * A higher value means the operator is sorted higher in the operator tree.<br/>
     * If no priority is defined, it will default to {@code 0}.
     *
     * @return priority priority
     */
    public int getPriority() {
		return priority;
	}

    /**
     * Gets small icon.
     *
     * @return the small icon
     */
    public ImageIcon getSmallIcon() {
		ImageIcon[] icons2 = this.getIcons();
		if (icons2[0] != null) {
			return icons2[0];
		} else {
			return icons2[1];
		}
	}

    /**
     * Gets large icon.
     *
     * @return the large icon
     */
    public ImageIcon getLargeIcon() {
		ImageIcon[] icons2 = this.getIcons();
		if (icons2[2] != null) {
			return icons2[2];
		} else {
			return icons2[1];
		}
	}

    /**
     * Gets abbreviated class name.
     *
     * @return the abbreviated class name
     */
    public String getAbbreviatedClassName() {
		return getOperatorClass().getName().replace("com.rapidminer.operator.", "c.r.o.");
	}

    /**
     * Gets deprecation info.
     *
     * @return the deprecation info
     */
    public String getDeprecationInfo() {
		if (deprecationInfo != null) {
			return deprecationInfo;
		} else {
			return getOperatorDocumentation().getDeprecation();
		}
	}

    /**
     * Is deprecated boolean.
     *
     * @return the boolean
     */
    public boolean isDeprecated() {
		return getDeprecationInfo() != null;
	}

    /**
     * Gets provider name.
     *
     * @return the provider name
     */
    public String getProviderName() {
		return provider != null ? provider.getName() : OperatorService.RAPID_MINER_CORE_PREFIX;
	}

    /**
     * This defines the namespace of the provider. If is core,
     * OperatorService.RAPID_MINER_CORE_NAMESPACE is returned. Otherwise the namespace of the
     * extension is returned as defined by the manifest.xml
     *
     * @return provider namespace
     */
    public String getProviderNamespace() {
		return provider != null ? provider.getExtensionId() : OperatorService.RAPID_MINER_CORE_NAMESPACE;
	}

    /**
     * Gets key.
     *
     * @return the key
     */
    public String getKey() {
		if (provider != null) {
			return provider.getPrefix() + ":" + this.key;
		} else {
			return this.key;
		}
	}

    /**
     * Returns the key of this operator without any prefix.
     *
     * @return the key without prefix
     */
    public String getKeyWithoutPrefix() {
		return this.key;
	}

    /**
     * Disable.
     */
    public void disable() {
		this.enabled = false;
	}

    /**
     * Some operators may be disabled, e.g. because they cannot be applied inside an application
     * server (file access etc.)
     *
     * @return the boolean
     */
    public boolean isEnabled() {
		return enabled;
	}

	@Override
	public String toString() {
		return "key='" + key + "'; name='" + getName() + "'; "
				+ (replacesDeprecatedKeys != null ? "replaces: " + replacesDeprecatedKeys : "") + "; implemented by "
				+ clazz.getName() + "; group: " + fullyQualifiedGroupKey + "; icon: " + iconName;
	}

	@Override
	public int compareTo(final OperatorDescription d) {
		String myName = this.getName();
		String otherName = d.getName();
		return myName.compareTo(otherName);
	}

	@Override
	public boolean equals(final Object o) {
		if (!(o instanceof OperatorDescription)) {
			return false;
		} else {
			OperatorDescription other = (OperatorDescription) o;
			return this.getKey().equals(other.getKey());
		}
	}

	@Override
	public int hashCode() {
		return this.getKey().hashCode();
	}

    /**
     * Creates a new operator based on the description. Subclasses that want to overwrite the
     * creation behavior should override
     *
     * @return the operator
     * @throws OperatorCreationException the operator creation exception
     */
    public final Operator createOperatorInstance() throws OperatorCreationException {
		if (!isEnabled()) {
			throw new OperatorCreationException(OperatorCreationException.OPERATOR_DISABLED_ERROR,
					key + "(" + clazz.getName() + ")", null);
		}
		Operator operator = null;
		try {
			operator = createOperatorInstanceByDescription(this);
		} catch (InstantiationException e) {
			throw new OperatorCreationException(OperatorCreationException.INSTANTIATION_ERROR,
					key + "(" + clazz.getName() + ")", e);
		} catch (IllegalAccessException e) {
			throw new OperatorCreationException(OperatorCreationException.ILLEGAL_ACCESS_ERROR,
					key + "(" + clazz.getName() + ")", e);
		} catch (NoSuchMethodException e) {
			throw new OperatorCreationException(OperatorCreationException.NO_CONSTRUCTOR_ERROR,
					key + "(" + clazz.getName() + ")", e);
		} catch (java.lang.reflect.InvocationTargetException e) {
			throw new OperatorCreationException(OperatorCreationException.CONSTRUCTION_ERROR,
					key + "(" + clazz.getName() + ")", e);
		} catch (Throwable t) {
			throw new OperatorCreationException(OperatorCreationException.INSTANTIATION_ERROR, "(" + clazz.getName() + ")",
					t);
		}
		OperatorService.invokeCreationHooks(operator);
		return operator;
	}

    /**
     * This method creates the actual instance of the {@link Operator} defined by the given
     * {@link OperatorDescription}. Subclasses might overwrite this method in order to change the
     * creation behavior or way.
     *
     * @param description the description
     * @return the operator
     * @throws IllegalArgumentException  the illegal argument exception
     * @throws InstantiationException    the instantiation exception
     * @throws IllegalAccessException    the illegal access exception
     * @throws InvocationTargetException the invocation target exception
     * @throws SecurityException         the security exception
     * @throws NoSuchMethodException     the no such method exception
     */
    protected Operator createOperatorInstanceByDescription(final OperatorDescription description)
			throws IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException,
			SecurityException, NoSuchMethodException {
		java.lang.reflect.Constructor<? extends Operator> constructor = clazz
				.getConstructor(new Class[] { OperatorDescription.class });
		return constructor.newInstance(new Object[] { description });
	}

    /**
     * Sets is replacement for.
     *
     * @param opName the op name
     */
    public void setIsReplacementFor(final String opName) {
		if (replacesDeprecatedKeys == null) {
			replacesDeprecatedKeys = new LinkedList<>();
		}
		replacesDeprecatedKeys.add(opName);
	}

    /**
     * Returns keys of deprecated operators replaced by this operator.  @return the replaced keys
     *
     * @return the replaced keys
     */
    public List<String> getReplacedKeys() {
		if (replacesDeprecatedKeys != null) {
			return replacesDeprecatedKeys;
		} else {
			return Collections.emptyList();
		}
	}

    /**
     * Gets icon name.
     *
     * @return the icon name
     */
    public String getIconName() {
		if (iconName != null) {
			return iconName;
		}
		return null;
	}

    /**
     * Sets icon name.
     *
     * @param iconName the icon name
     */
    public void setIconName(final String iconName) {
		this.iconName = iconName;
		updateIcons();
	}

	/**
	 * Update the icons. Icons may change when a license changes.
	 */
	private void updateIcons() {
		if (iconName != null) {
			icons = new ImageIcon[3];
			icons[0] = SwingTools.createIcon("16/" + iconName);
			icons[1] = SwingTools.createIcon("24/" + iconName);
			icons[2] = SwingTools.createIcon("48/" + iconName);
			if (!isSupportedByLicense()) {
				icons[0] = SwingTools.createOverlayIcon(icons[0], UNSUPPORTED_ICON_SMALL);
				icons[1] = SwingTools.createOverlayIcon(icons[1], UNSUPPORTED_ICON);
				icons[2] = SwingTools.createOverlayIcon(icons[2], UNSUPPORTED_ICON_LARGE);
			}
		} else {
			icons = EMPTY_ICONS;
		}
	}

    /**
     * This returns true if a specific icon already has been defined in this
     * {@link OperatorDescription}. It might be possible, that there's no such icon name defined.
     * Since each operator should have an icon, it will be read from the containing Group when the
     * {@link OperatorDescription} is registered using
     * {@link OperatorService#registerOperator(OperatorDescription)}.
     *
     * @return the boolean
     */
    public boolean isIconDefined() {
		return iconName != null;
	}

	private ImageIcon[] getIcons() {
		if (icons != null) {
			return icons;
		}
		return null;
	}

    /**
     * Gets provider.
     *
     * @return the provider
     */
    public Plugin getProvider() {
		return provider;
	}

    /**
     * Sets fully qualified group key.
     *
     * @param key the key
     */
    protected void setFullyQualifiedGroupKey(final String key) {
		this.fullyQualifiedGroupKey = key;
	}

	/**
	 * Checks if the {@link Operator} represented by this description is supported by the currently
	 * active {@link License}.
	 *
	 * @return
	 */
	private Boolean isSupportedByLicense() {
		if (isSupportedByLicense == null) {
			try {
				// check for operator annotations
				isSupportedByLicense = Boolean
						.valueOf(ProductConstraintManager.INSTANCE.isAllowedByAnnotations(createOperatorInstance()));
			} catch (OperatorCreationException e) {
			}      // does not really matter
		}
		if (isSupportedByLicense == null) {
			isSupportedByLicense = Boolean.TRUE;
		}
		return isSupportedByLicense;
	}

    /**
     * Refreshes the {@link OperatorDescription}. Therefore the supported by license cache is
     * invalidated and the icons are updated.
     */
    public void refresh() {
		isSupportedByLicense = null;
		updateIcons();
	}

    /**
     * Is using extension tree root boolean.
     *
     * @return whether the operator is using the "extensions.EXTENSION_NAME group" group as tree         root
     */
    public boolean isUsingExtensionTreeRoot() {
		return isUsingExtensionTreeRoot;
	}

    /**
     * Sets use extension tree root.
     *
     * @param isUsingExtensionGrouping defines whether the operator using the "extensions.EXTENSION_NAME group" group as            tree root
     */
    public void setUseExtensionTreeRoot(boolean isUsingExtensionGrouping) {
		this.isUsingExtensionTreeRoot = isUsingExtensionGrouping;
	}
}
