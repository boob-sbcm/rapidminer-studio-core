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

import com.rapidminer.RapidMiner;
import com.rapidminer.operator.ExecutionMode;
import com.rapidminer.parameter.ParameterType;
import com.rapidminer.security.PluginSandboxPolicy;
import com.rapidminer.tools.parameter.*;

import java.io.*;
import java.security.AccessControlException;
import java.security.AccessController;
import java.util.*;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * This class loads the RapidMiner property files and provides methods to access them. It also
 * stores the values of the properties. They are still mirrored in the System properties for keeping
 * compatibility but it is strongly recommended to use this class to get access.
 * <p>
 * During init this class will try to load settings from various sources. Sources with a higher
 * specificy will overwrite settings with a lower. The sequence is as follows while only the first
 * step is executed if the {@link ExecutionMode} forbidds file access:
 * <ol>
 * <li>if the system property <code>rapidminer.config.dir</code> is set, the file rapidminerrc
 * inside this directory will be loaded.</li>
 * <li>if the property is not set, the environment variable <code>RAPIDMINER_CONFIG_DIR</code> will
 * be evaluated in the same way.</li>
 * <li>the file rapidminer-studio-settings.cfg in the user's .Rapidminer directory will be
 * loaded.</li>
 * <li>the file rapidminer-studio-settings.cfg in the user's home directory will be loaded.</li>
 * <li>the file denoted by the System property <code>rapidminer.rcfile</code> will be loaded if
 * defined.</li>
 * </ol>
 * It also provides methods to create files relative to the RapidMiner home directory. The way to
 * access the properties via System.getProperty is deprecated and should be replaced by
 * #getProperty(String).
 *
 * @author Simon Fischer, Ingo Mierswa, Sebastian Land, Marco Boeck
 */
public class ParameterService {

	private static final Logger LOGGER = Logger.getLogger(ParameterService.class.getSimpleName());

    /**
     * The constant RAPIDMINER_CONFIG_FILE_NAME.
     */
    public static final String RAPIDMINER_CONFIG_FILE_NAME = "rapidminer-studio-settings.cfg";

    /**
     * Property specifying the root directory of the RapidMiner project sources.
     */
    public static final String PROPERTY_RAPIDMINER_SRC_ROOT = "rapidminer.src.root";
    /**
     * The constant PROPERTY_RAPIDMINER_CONFIG_DIR.
     */
    public static final String PROPERTY_RAPIDMINER_CONFIG_DIR = "rapidminer.config.dir";

    /**
     * The constant ENVIRONMENT_RAPIDMINER_CONFIG_DIR.
     */
    public static final String ENVIRONMENT_RAPIDMINER_CONFIG_DIR = "RAPIDMINER_CONFIG_DIR";

	private static boolean intialized = false;
	private static final List<ParameterChangeListener> PARAMETER_LISTENERS = new LinkedList<>();
	private static final Map<String, Parameter> PARAMETER_MAP = new TreeMap<>();
	private static final List<ParameterWriter> PARAMETER_WRITERS = new LinkedList<>();

	static {
		PARAMETER_WRITERS.add(new WindowsExeParameterWriter());
		PARAMETER_WRITERS.add(new WindowsBatParameterWriter());
	}

    /**
     * Reads the configuration file if allowed by the {@link com.rapidminer.operator.ExecutionMode}.
     */
    public static void init() {
		if (!intialized) {
			// then try to read configuration from file system if allowed to do so.
			if (RapidMiner.getExecutionMode().canAccessFilesystem()) {
				List<File> configFilesList = new LinkedList<>();

				// adding global config file defined by parameter or environment variable
				File globalConfigFile = getGlobalConfigFile(RAPIDMINER_CONFIG_FILE_NAME);
				if (globalConfigFile != null) {
					configFilesList.add(globalConfigFile);
					configFilesList.add(getOSDependentFile(globalConfigFile));
				}

				// add user specific config file from .RapidMiner directory
				File userConfigFile = FileSystemService.getUserConfigFile(RAPIDMINER_CONFIG_FILE_NAME);
				if (userConfigFile != null) {
					configFilesList.add(userConfigFile);
					configFilesList.add(getOSDependentFile(userConfigFile));
				}

				// finally if user has given a parameter to specify the RC file, this one is used
				String parameterConfigFilePath = System.getProperty(RapidMiner.PROPERTY_RAPIDMINER_RC_FILE);
				if (parameterConfigFilePath != null) {
					configFilesList.add(new File(parameterConfigFilePath));
				}

				// finally read all collected files if existing
				for (File configFile : configFilesList) {
					if (configFile.exists()) {
						try {
							setParameters(configFile);
							LOGGER.config("Trying rcfile '" + configFile.getAbsolutePath() + "'...success");
						} catch (FileNotFoundException e) {
							LOGGER.config("Trying rcfile '" + configFile.getAbsolutePath() + "'...skipped");
						} catch (IOException e) {
							LOGGER.config("Trying rcfile '" + configFile.getAbsolutePath() + "'...skipped");
						}
					}
				}
			} else {
				LOGGER.config("Execution mode " + RapidMiner.getExecutionMode()
						+ " does not permit file access. Ignoring all rcfiles.");
			}

			// set flag to avoid second call
			intialized = true;
		}
	}

    /**
     * This method sets the given parameter to the given value. If the parameter is not known, yet,
     * it will be added as a defined parameter with a default scope.
     *
     * @param type  the type
     * @param value the value
     */
    public static void setParameterValue(ParameterType type, String value) {
		Parameter parameter = PARAMETER_MAP.get(type.getKey());
		if (parameter == null) {
			parameter = new Parameter(type, value);
			PARAMETER_MAP.put(type.getKey(), parameter);
		}
		setParameterValue(type.getKey(), value);
	}

    /**
     * This method sets the parameter with the given key to the given value. If the parameter does
     * not yet exist a new non defined parameter will be created. The value can then be retrieved
     * but it won't be saved in any configuration file and will be lost after restarting RapidMiner.
     * <p>
     * For compatibility reasons this will set the parameter also in the System properties. This
     * might be removed in further versions.
     *
     * @param key   the key
     * @param value the value
     */
    public static void setParameterValue(String key, String value) {
		// this might be removed later. It remains only for compatibility
		if (System.getProperty(key) == null) {
			System.setProperty(key, value);
		}

		// Before changing, check if the Parameter is protected
		if (RapidMiner.isParameterProtected(key)) {
			// If yes, only extensions with enough permissions should be allowed to change it
			try {
				if (System.getSecurityManager() != null) {
					AccessController
							.checkPermission(new RuntimePermission(PluginSandboxPolicy.RAPIDMINER_INTERNAL_PERMISSION));
				}
			} catch (AccessControlException e) {
				return;
			}
		}

		// setting parameter
		Parameter parameter = PARAMETER_MAP.get(key);
		if (parameter == null) {
			parameter = new Parameter(value);
			PARAMETER_MAP.put(key, parameter);
		}
		parameter.setValue(value);

		try {
			informListenerOfChange(key, value);
		} catch (Throwable e) {
			LogService.getRoot().log(Level.WARNING, I18N.getMessage(LogService.getRoot().getResourceBundle(),
					"com.rapidminer.tools.ParameterService.listener_error", e));
		}
	}

    /**
     * This method returns the value of the given parameter or null if this parameter is unknown.
     * For compatibility reasons this will return defined parameters as well as undefined.
     *
     * @param key the key
     * @return the parameter value
     */
    public static String getParameterValue(String key) {
		Parameter parameter = PARAMETER_MAP.get(key);
		if (parameter != null) {
			return parameter.getValue();
		}
		return null;
	}

    /**
     * This will return the group of the parameter with the given key. If the key is unknown, null
     * will be returned.
     *
     * @param key the key
     * @return the group key
     */
    public static String getGroupKey(String key) {
		Parameter parameter = PARAMETER_MAP.get(key);
		if (parameter != null) {
			return parameter.getGroup();
		}
		return null;
	}

    /**
     * This method returns the type of the defined parameter identified by key or null if this key
     * is unknown.
     *
     * @param key the key
     * @return the parameter type
     */
    public static ParameterType getParameterType(String key) {
		Parameter parameter = PARAMETER_MAP.get(key);
		if (parameter != null) {
			return parameter.getType();
		}
		return null;
	}

    /**
     * This method returns all keys of all parameters, implicit as well as defined ones.
     *
     * @return the parameter keys
     */
    public static Collection<String> getParameterKeys() {
		return PARAMETER_MAP.keySet();
	}

    /**
     * This method will return a Collection of all keys of defined parameter types. Undefined types
     * will not be returned.
     *
     * @return the defined parameter keys
     */
    public static Collection<String> getDefinedParameterKeys() {
		LinkedList<String> keys = new LinkedList<>();
		for (Entry<String, Parameter> entry : PARAMETER_MAP.entrySet()) {
			if (entry.getValue().isDefined()) {
				keys.add(entry.getKey());
			}
		}
		return keys;
	}

    /**
     * This method will return a Collection of all keys of defined parameter types. Undefined types
     * will not be returned.
     *
     * @return the defined parameter types
     */
    public static Set<ParameterType> getDefinedParameterTypes() {
		HashSet<ParameterType> types = new HashSet<>();
		for (Entry<String, Parameter> entry : PARAMETER_MAP.entrySet()) {
			if (entry.getValue().isDefined()) {
				types.add(entry.getValue().getType());
			}
		}
		return types;
	}

	/**
	 * This method returns the operating system dependent file of the given file.
	 */
	private static File getOSDependentFile(File file) {
		return new File(file.getAbsoluteFile() + "." + System.getProperty("os.name"));
	}

    /**
     * This sets the parameters to the values given by a properties file denoted by the given file
     * object.
     *
     * @param file the file
     * @throws FileNotFoundException the file not found exception
     * @throws IOException           the io exception
     */
    public static void setParameters(File file) throws FileNotFoundException, IOException {
		setParameters(new FileInputStream(file));
	}

    /**
     * This method reads the input stream that streams in a properties file and sets the parameter
     * values accordingly. If the stream cannot be accessed an exception will be thrown.
     *
     * @param in the in
     * @throws IOException the io exception
     */
    public static void setParameters(InputStream in) throws IOException {
		Properties properties = new Properties();
		properties.load(in);

		for (Map.Entry<Object, Object> entry : properties.entrySet()) {
			setParameterValue((String) entry.getKey(), (String) entry.getValue());
		}

		try {
			in.close();
		} catch (IOException e) {
			// can't help it
		}
	}

    /**
     * This returns the file with the given fileName from the directory denoted by first the
     * Parameter named {@value #PROPERTY_RAPIDMINER_CONFIG_DIR} and if this one is not defined by
     * the environment variable {@value #ENVIRONMENT_RAPIDMINER_CONFIG_DIR}. If neither one is
     * defined, null is returned.
     *
     * @param fileName the file name
     * @return the global config file
     */
    public static File getGlobalConfigFile(String fileName) {
		File dir = getGlobalConfigDir();
		if (dir != null) {
			File result = new File(dir, fileName);
			if (result.exists()) {
				if (result.canRead()) {
					return result;
				} else {
					LOGGER.warning("Config file " + result.getAbsolutePath() + " is not readable.");
					return null;
				}
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	private static File getGlobalConfigDir() {
		String configDir = System.getProperty(PROPERTY_RAPIDMINER_CONFIG_DIR);
		if (configDir == null) {
			configDir = System.getenv(ENVIRONMENT_RAPIDMINER_CONFIG_DIR);
		}
		if (configDir != null) {
			File dir = new File(configDir);
			if (dir.exists()) {
				if (dir.canRead()) {
					return dir;
				} else {
					LOGGER.warning("Directory " + dir.getAbsolutePath() + " specified by environment variable "
							+ ENVIRONMENT_RAPIDMINER_CONFIG_DIR + " is not readable.");
					return null;
				}
			} else {
				LOGGER.warning("Directory " + dir.getAbsolutePath() + " specified by environment variable "
						+ ENVIRONMENT_RAPIDMINER_CONFIG_DIR + " does not exist.");
				return null;
			}
		} else {
			return null;
		}
	}

    /**
     * This method will save all currently known defined parameter types into the version and os
     * dependent config file in the user's RapidMiner directory. This method will also generate
     * files that are needed for preStartParameter that affect as environment variables the staring
     * JVM. If file access isn't allowed nothing is done at all.
     */
    public static void saveParameters() {
		saveParameters(FileSystemService.getMainUserConfigFile());

		// now export properties using all additional registered writers
		for (ParameterWriter writer : PARAMETER_WRITERS) {
			writer.writeParameters(PARAMETER_MAP);
		}

		informListenerOfSave();
	}

    /**
     * This method will save all currently known defined parameters into the given file. If file
     * access isn't allowed by the execution mode, nothing is done.
     * <p>
     * Please notice that in contrast to {@link #saveParameters()}, no preStartParameter files are
     * written.
     *
     * @param configFile the config file
     */
    public static void saveParameters(File configFile) {
		if (!RapidMiner.getExecutionMode().canAccessFilesystem()) {
			LogService.getRoot().log(Level.CONFIG,
					"com.rapidminer.tools.ParameterService.ignoring_request_to_save_properties",
					RapidMiner.getExecutionMode());
			return;
		}

		// building properties object to save to file
		Properties properties = new Properties();
		for (Entry<String, Parameter> entry : PARAMETER_MAP.entrySet()) {
			Parameter parameter = entry.getValue();
			if (parameter.getValue() != null) {
				properties.put(entry.getKey(), parameter.getValue());
			}
		}
		BufferedOutputStream out = null;
		try (FileOutputStream fos = new FileOutputStream(configFile)) {
			out = new BufferedOutputStream(fos);
			properties.store(out, "");
		} catch (FileNotFoundException e) {
			LogService.getRoot().log(Level.WARNING, I18N.getMessage(LogService.getRoot().getResourceBundle(),
					"com.rapidminer.tools.ParameterService.writing_user_properties_error", e.getMessage()), e);
		} catch (IOException e) {
			LogService.getRoot().log(Level.WARNING, I18N.getMessage(LogService.getRoot().getResourceBundle(),
					"com.rapidminer.tools.ParameterService.writing_user_properties_error", e.getMessage()), e);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					LogService.getRoot().log(Level.WARNING, I18N.getMessage(LogService.getRoot().getResourceBundle(),
							"com.rapidminer.tools.ParameterService.closing_user_properties_file_error", e.getMessage()), e);
				}
			}
		}
	}

    /**
     * This method lets register the given {@link ParameterType} with defaults settings. To have
     * more control over the scope and group name refer to any other registerParameter method.
     * <p>
     * If a implicite Parameter is already defined with the key, it will be converted to an explict
     * without loosing the data.
     *
     * @param type the type
     */
    public static void registerParameter(ParameterType type) {
		Parameter parameter = PARAMETER_MAP.get(type.getKey());
		if (parameter == null) {
			parameter = new Parameter(type);
			PARAMETER_MAP.put(type.getKey(), parameter);
		} else {
			parameter.setType(type);
		}
	}

    /**
     * This method allows to set the group explicitly rather than deriving it from the key.
     *
     * @param type  the type
     * @param group the group
     */
    public static void registerParameter(ParameterType type, String group) {
		Parameter parameter = PARAMETER_MAP.get(type.getKey());
		if (parameter == null) {
			parameter = new Parameter(type, group);
			PARAMETER_MAP.put(type.getKey(), parameter);
		} else {
			parameter.setType(type);
			parameter.setGroup(group);
		}
	}

    /**
     * This method can be used to register the given {@link ParameterType} on the given
     * {@link ParameterScope}. This method can be used to define for example preStartParameters like
     * memory size...
     *
     * @param type  the type
     * @param group the group
     * @param scope the scope
     */
    public static void registerParameter(ParameterType type, String group, ParameterScope scope) {
		Parameter parameter = PARAMETER_MAP.get(type.getKey());
		if (parameter == null) {
			parameter = new Parameter(type, group);
			PARAMETER_MAP.put(type.getKey(), parameter);
		} else {
			parameter.setType(type);
			parameter.setGroup(group);
		}
		parameter.setScope(scope);
	}

	private static void informListenerOfChange(String key, String value) {
		for (ParameterChangeListener listener : PARAMETER_LISTENERS) {
			listener.informParameterChanged(key, value);
		}
	}

	private static void informListenerOfSave() {
		for (ParameterChangeListener listener : PARAMETER_LISTENERS) {
			listener.informParameterSaved();
		}
	}

    /**
     * This will add the given listener to the list of listers. It will be informed whenever a
     * setting is changed.
     *
     * @param listener the listener
     */
    public static void registerParameterChangeListener(ParameterChangeListener listener) {
		PARAMETER_LISTENERS.add(listener);
	}

    /**
     * This method will remove the given listener from the list. It won't be informed anymore.
     *
     * @param listener the listener
     */
    public static void removeParameterChangeListener(ParameterChangeListener listener) {
		PARAMETER_LISTENERS.remove(listener);
	}

	/*
	 * Deprecated methods remaining for compatibility only.
	 */

    /**
     * This method is deprecated and shouldn't be used anymore. To save RapidMiner's Parameters, use
     * method {@link #saveProperties(File))}. To save the given properties you can simply call
     * {@link Properties#store(java.io.OutputStream, String)} yourself.
     *
     * @param properties the properties
     * @param file       the file
     */
    @Deprecated
	public static void writeProperties(Properties properties, File file) {
		if (!RapidMiner.getExecutionMode().canAccessFilesystem()) {
			LogService.getRoot().log(Level.CONFIG,
					"com.rapidminer.tools.ParameterService.ignoring_request_to_save_properties",
					RapidMiner.getExecutionMode());
			return;
		}

		try (FileOutputStream fos = new FileOutputStream(file); BufferedOutputStream out = new BufferedOutputStream(fos)) {
			properties.store(out, "");
		} catch (IOException e) {
			LogService.getRoot().log(Level.WARNING, I18N.getMessage(LogService.getRoot().getResourceBundle(),
					"com.rapidminer.tools.ParameterService.writing_user_properties_error", e.getMessage()), e);
		}
	}

    /**
     * This method shouldn't be used anymore since it does two actions at one time: Setting and
     * saving. You can easily replace it by subsequent calls to
     * {@link #setParameterValue(String, String)} and {@link #saveParameters()}.
     *
     * @param key   the key
     * @param value the value
     */
    @Deprecated
	public static void writePropertyIntoMainUserConfigFile(String key, String value) {
		setParameterValue(key, value);
		saveParameters();
	}

    /**
     * Deprecated method. Remains only for compatibility. Please use
     * {@link FileSystemService#getMainUserConfigFile()} instead.
     *
     * @return the main user config file
     */
    @Deprecated
	public static File getMainUserConfigFile() {
		return FileSystemService.getMainUserConfigFile();
	}

    /**
     * Deprecated method. Remains only for compatibility. Please use
     * {@link FileSystemService#getUserConfigFile(String)} instead.
     *
     * @param name the name
     * @return the user config file
     */
    @Deprecated
	public static File getUserConfigFile(String name) {
		return FileSystemService.getUserConfigFile(name);
	}

    /**
     * Deprecated method. Remains only for compatibility. Please use
     * {@link FileSystemService#getUserRapidMinerDir()} instead.
     *
     * @return the user rapid miner dir
     */
    @Deprecated
	public static File getUserRapidMinerDir() {
		return FileSystemService.getUserRapidMinerDir();
	}

    /**
     * Deprecated method. Remains only for compatibility. Please use
     * {@link FileSystemService#getRapidMinerHome()} instead.
     *
     * @return the rapid miner home
     * @throws IOException the io exception
     */
    @Deprecated
	public static File getRapidMinerHome() throws IOException {
		return FileSystemService.getRapidMinerHome();
	}

    /**
     * Deprecated method. Remains only for compatibility. Please use
     * {@link FileSystemService#getLibraryFile(String)} instead.
     *
     * @param name the name
     * @return the library file
     * @throws IOException the io exception
     */
    @Deprecated
	public static File getLibraryFile(String name) throws IOException {
		return FileSystemService.getLibraryFile(name);
	}

    /**
     * Deprecated method. Remains only for compatibility. Please use
     * {@link FileSystemService#getSourceRoot()} instead.
     *
     * @return the source root
     */
    @Deprecated
	public static File getSourceRoot() {
		return FileSystemService.getSourceRoot();
	}

    /**
     * Deprecated method. Remains only for compatibility. Please use
     * {@link FileSystemService#getSourceFile(String)} instead.
     *
     * @param name the name
     * @return the source file
     */
    @Deprecated
	public static File getSourceFile(String name) {
		return FileSystemService.getSourceFile(name);
	}

    /**
     * Deprecated method. Remains only for compatibility. Please use
     * {@link FileSystemService#getSourceResourceFile(String)} instead.
     *
     * @param name the name
     * @return the source resource file
     */
    @Deprecated
	public static File getSourceResourceFile(String name) {
		return FileSystemService.getSourceResourceFile(name);
	}

    /**
     * This method is deprecated and remains only for compatibility. Please use {@link #init()}
     * instead.
     *
     * @param operatorsXMLStream the operators xml stream
     */
    @Deprecated
	public static void init(InputStream operatorsXMLStream) {
		init();
	}

    /**
     * Returns true if value is "true", "yes", "y" or "on". Returns false if value is "false", "no",
     * "n" or "off". Otherwise returns <tt>deflt</tt>.
     *
     * @param value the value
     * @param deflt the deflt
     * @return the boolean
     */
    @Deprecated
	public static boolean booleanValue(String value, boolean deflt) {
		if (value == null) {
			return deflt;
		}
		if (value.equals("true")) {
			return true;
		} else if (value.equals("yes")) {
			return true;
		} else if (value.equals("y")) {
			return true;
		} else if (value.equals("on")) {
			return true;
		} else if (value.equals("false")) {
			return false;
		} else if (value.equals("no")) {
			return false;
		} else if (value.equals("n")) {
			return false;
		} else if (value.equals("off")) {
			return false;
		}

		return deflt;
	}

}
