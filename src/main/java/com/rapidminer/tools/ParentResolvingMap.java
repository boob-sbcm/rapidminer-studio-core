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
package com.rapidminer.tools;

import java.io.IOException;
import java.net.URL;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;

import com.rapidminer.tools.plugin.Plugin;


/**
 * The type Parent resolving map.
 *
 * @param <K> the type parameter
 * @param <V> the type parameter
 * @author Tobias Malbrecht
 */
public abstract class ParentResolvingMap<K, V> extends AbstractMap<K, V> implements Map<K, V> {

	private final Map<K, V> delegate = new HashMap<K, V>();

    /**
     * Instantiates a new Parent resolving map.
     */
    public ParentResolvingMap() {}

    /**
     * Parse properties.
     *
     * @param resourceName the resource name
     * @param prefix       the prefix
     * @param suffix       the suffix
     * @param classLoader  the class loader
     * @param provider     the provider
     * @throws IOException the io exception
     */
    public void parseProperties(String resourceName, String prefix, String suffix, ClassLoader classLoader, Plugin provider)
			throws IOException {
		Properties groupProps = new Properties();
		URL resource = classLoader.getResource(resourceName);
		if (resource == null) {
			// LogService.getRoot().warning("Group properties resource '"+resourceName +
			// "' not found.");
			LogService.getRoot().log(Level.WARNING,
					"com.rapidminer.tools.ParentResolvingMap.group_properties_resource_not_found", resourceName);
		} else {
			groupProps.load(resource.openStream());
			for (String propKey : groupProps.stringPropertyNames()) {
				if (propKey.startsWith(prefix) && propKey.endsWith(suffix)) {
					String keyString = propKey.substring(prefix.length());
					keyString = keyString.substring(0, keyString.length() - suffix.length());
					K mapKey = parseKey(keyString, classLoader, provider);
					V value = parseValue(groupProps.getProperty(propKey), classLoader, provider);
					delegate.put(mapKey, value);
				}
			}
		}
	}

    /**
     * Parse value v.
     *
     * @param value       the value
     * @param classLoader the class loader
     * @param provider    the provider
     * @return the v
     */
    protected abstract V parseValue(String value, ClassLoader classLoader, Plugin provider);

    /**
     * Parse key k.
     *
     * @param key         the key
     * @param classLoader the class loader
     * @param provider    the provider
     * @return the k
     */
    protected abstract K parseKey(String key, ClassLoader classLoader, Plugin provider);

    /**
     * Gets parent.
     *
     * @param child    the child
     * @param provider the provider
     * @return the parent
     */
    protected abstract K getParent(K child, Plugin provider);

    /**
     * Gets default.
     *
     * @return the default
     */
    protected abstract V getDefault();

    /**
     * Get v.
     *
     * @param key      the key
     * @param provider the provider
     * @return the v
     */
    public V get(K key, Plugin provider) {
		return getValue(key, provider);
	}

	@SuppressWarnings("unchecked")
	@Override
	public V get(Object key) {
		return getValue((K) key, null);
	}

	private V getValue(K key, Plugin provider) {
		while (key != null) {
			V value = delegate.get(key);
			if (value != null) {
				return value;
			} else {
				key = getParent(key, provider);
			}
		}
		return getDefault();
	}

	@Override
	public V put(K key, V value) {
		return delegate.put(key, value);
	}

	@Override
	public Set<Map.Entry<K, V>> entrySet() {
		return delegate.entrySet();
	}
}
