package com.gentle.util;

import com.gentle.bean.RequestMethod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Smith on 2017/5/17.
 */
public final class FrameworkUtil {
    private static final Logger LOGGER = LogManager.getLogger();

    public static RequestMethod convertToRequestMethod(String s) {
        RequestMethod requestMethod = null;
        try {
            requestMethod = RequestMethod.valueOf(s);
        } catch (Exception e) {
            LOGGER.error("request method convert failure");
        }
        return requestMethod;
    }
}
