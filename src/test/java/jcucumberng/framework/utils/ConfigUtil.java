package jcucumberng.framework.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

/**
 * {@code ConfigUtil} handles actions for reading/loading configuration files.
 * 
 * @author Kat Rollo &lt;rollo.katherine@gmail.com&gt;
 */
public final class ConfigUtil {

	private static final String FRAMEWORK_CONF_DIR = "/src/test/resources/jcucumberng/framework/";
	private static final String PROJECT_CONF_DIR = "/src/test/resources/jcucumberng/project/";

	private static final String NO_SUCH_KEY = "Key not found in ";

	private ConfigUtil() {
		// Prevent instantiation
	}

	/**
	 * Reads framework config from {@code framework.properties}.
	 * 
	 * @param key the config key (Example: {@code browser=CHROME32}, key =
	 *            {@code browser})
	 * @return String - the value corresponding to the given key (Example:
	 *         {@code browser=CHROME32}, value = {@code CHROME32})
	 * @throws IOException
	 */
	public static String framework(String key) throws IOException {
		String propsFileName = "framework.properties";
		StringBuilder builder = new StringBuilder();
		builder.append(StringUtils.replace(System.getProperty("user.dir"), "\\", "/"));
		builder.append(FRAMEWORK_CONF_DIR);
		builder.append(propsFileName);

		InputStream inputStream = new FileInputStream(builder.toString());
		Properties props = new Properties();
		props.load(inputStream);

		String value = props.getProperty(key);
		if (StringUtils.isBlank(value)) {
			builder.setLength(0);
			builder.append(propsFileName + ": " + key);
			throw new NoSuchKeyException(NO_SUCH_KEY + builder.toString());
		}

		return StringUtils.trim(value);
	}

	/**
	 * Reads project config from {@code project.properties}.
	 * 
	 * @param key the config key (Example: {@code base.url=www.google.com}, key =
	 *            {@code base.url})
	 * @return String - the value corresponding to the given key (Example:
	 *         {@code base.url=www.google.com}, value = {@code www.google.com})
	 * @throws IOException
	 */
	public static String project(String key) throws IOException {
		String propsFileName = "project.properties";
		StringBuilder builder = new StringBuilder();
		builder.append(StringUtils.replace(System.getProperty("user.dir"), "\\", "/"));
		builder.append(PROJECT_CONF_DIR);
		builder.append(propsFileName);

		InputStream inputStream = new FileInputStream(builder.toString());
		Properties props = new Properties();
		props.load(inputStream);

		String value = props.getProperty(key);
		if (StringUtils.isBlank(value)) {
			builder.setLength(0);
			builder.append(propsFileName + ": " + key);
			throw new NoSuchKeyException(NO_SUCH_KEY + builder.toString());
		}

		return StringUtils.trim(value);
	}

	/**
	 * Loads {@code log4j2.conf.file} from {@code framework.properties}.
	 */
	public static void logger() {
		String cfgFile = null;
		try {
			cfgFile = ConfigUtil.framework("log4j2.conf.file");
		} catch (IOException ioe) {
			throw new NoLoggerConfigException("Cannot find logger config file: " + cfgFile);
		}

		StringBuilder builder = new StringBuilder();
		builder.append(StringUtils.replace(System.getProperty("user.dir"), "\\", "/"));
		builder.append(FRAMEWORK_CONF_DIR);
		builder.append(cfgFile);

		File log4j2File = new File(builder.toString());
		System.setProperty("log4j2.configurationFile", log4j2File.toURI().toString());
	}

	/**
	 * Reads web elements from {@code ui-map.properties}.
	 * 
	 * @param key the element key (Example: {@code first.name=id:firstName}, key =
	 *            {@code first.name})
	 * @return String - the value corresponding to the given key (Example:
	 *         {@code first.name=id:firstName}, value = {@code id:firstName})
	 * @throws IOException
	 */
	public static String uiMap(String key) throws IOException {
		String propsFileName = "ui-map.properties";
		StringBuilder builder = new StringBuilder();
		builder.append(StringUtils.replace(System.getProperty("user.dir"), "\\", "/"));
		builder.append(PROJECT_CONF_DIR);
		builder.append(propsFileName);

		InputStream inputStream = new FileInputStream(builder.toString());
		Properties props = new Properties();
		props.load(inputStream);

		String value = props.getProperty(key);
		if (StringUtils.isBlank(value)) {
			builder.setLength(0);
			builder.append(propsFileName + ": " + key);
			throw new NoSuchKeyException(NO_SUCH_KEY + builder.toString());
		}

		return StringUtils.trim(value);
	}

}
