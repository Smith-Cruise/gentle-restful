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
    private static final String DATABASE_POOL_SWITCH_KEY = "database_pool";

    static {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(CONFIG_FILE_NAME);
        config = new Properties();
        try {
            config.load(is);
        } catch (IOException e) {
            LOGGER.error("can't find config configuration");
        }
    }

    public static String getAppPackageName() {
        return getProperty(APP_PACKAGE_KEY);
    }

    public static boolean getDatabasePoolSwitch() {
        String status = config.getProperty(DATABASE_POOL_SWITCH_KEY);
        return status.equals("on");
    }

    public static String getPropertyAsString(String key) {
        return getProperty(key);
    }

    public static int getPropertyAsInt(String key) {
        return Integer.valueOf(getProperty(key));
    }

    public static long getPropertyAsLog(String key) {
        return Long.valueOf(getProperty(key));
    }

    public static double getPropertyAsDouble(String key) {
        return Double.valueOf(getProperty(key));
    }

    public static float getPropertyAsFloat(String key) {
        return Float.valueOf(getProperty(key));
    }

    public static boolean getPropertyAsBoolean(String key) {
        return Boolean.valueOf(getProperty(key));
    }

    private static String getProperty(String key) {
        return config.getProperty(key);
    }
}
