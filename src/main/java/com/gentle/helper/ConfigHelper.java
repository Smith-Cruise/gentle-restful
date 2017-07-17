package com.gentle.helper;

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
    private static final Properties config;

    private static final String CONFIG_FILE_NAME = "gentle_restful.properties";
    private static final String APP_PACKAGE_KEY = "app_package";
    private static final String DRUID_SWITCH_KEY = "druid_switch";
    private static final String DRUID_CONFIG_FILE_NAME_KEY = "druid_config_file_name";

    static {
        config = getProperties(CONFIG_FILE_NAME);
    }

    public static String getAppPackageName() {
        return getProperty(APP_PACKAGE_KEY);
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
        Properties properties = new Properties();
        try {
            properties.load(is);
        } catch (IOException e) {
            LOGGER.error("can't find config configuration");
        }
        return properties;
    }

    private static String getPropertyAsString(String key) {
        return getProperty(key);
    }

    private static int getPropertyAsInt(String key) {
        return Integer.valueOf(getProperty(key));
    }

    private static long getPropertyAsLog(String key) {
        return Long.valueOf(getProperty(key));
    }

    private static double getPropertyAsDouble(String key) {
        return Double.valueOf(getProperty(key));
    }

    private static float getPropertyAsFloat(String key) {
        return Float.valueOf(getProperty(key));
    }

    private static boolean getPropertyAsBoolean(String key) {
        return Boolean.valueOf(getProperty(key));
    }

    private static String getProperty(String key) {
        return config.getProperty(key);
    }
}
