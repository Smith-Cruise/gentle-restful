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

    private static final String CONFIG_FILE_NAME = "simple_framework.properties";
    private static final String APP_PACKAGE_KEY = "app_package";

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

    private static String getProperty(String key) {
        return config.getProperty(key);
    }
}
