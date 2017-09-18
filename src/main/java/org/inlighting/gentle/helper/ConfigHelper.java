package org.inlighting.gentle.helper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Smith on 2017/5/16.
 */
public final class ConfigHelper {
    private static final Logger LOGGER = LogManager.getLogger();
    private static Properties CONFIG;

    private static final String CONFIG_FILE_NAME = "gentle_restful.properties";

    private static final String APP_PACKAGE_KEY = "app_package";
    private static final String APP_BASE_PATH_KEY = "app_base_path";
    private static final String DRUID_SWITCH_KEY = "druid_switch";
    private static final String DRUID_CONFIG_FILE_NAME_KEY = "druid_config_file_name";

    static {
        CONFIG = getProperties(CONFIG_FILE_NAME);
    }

    public static String getAppPackageName() {
        return getProperty(APP_PACKAGE_KEY);
    }

    public static String getAppBasePath() {
        return getProperty(APP_BASE_PATH_KEY);
    }

    public static boolean getDruidSwitch() {
        String status = getProperty(DRUID_SWITCH_KEY);
        return status.equals("on");
    }

    public static String getDruidConfigFileName() {
        return getProperty(DRUID_CONFIG_FILE_NAME_KEY);
    }

    public static Properties getDruidProperties() {
        return getProperties(getDruidConfigFileName());
    }

    private static Properties getProperties(String fileName) {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
        try {
            Properties properties = new Properties();
            properties.load(is);
            return properties;
        } catch (IOException e) {
            LOGGER.error("can't find config configuration");
            return null;
        }
    }

    private static String getProperty(String key) {
        return CONFIG.getProperty(key);
    }
}
