package org.inlighting.gentle.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Method;

/**
 * Created by Smith on 2017/5/17.
 */
public final class ReflectionUtil {
    private static final Logger LOGGER = LogManager.getLogger();

    public static Object newInstance(Class cls) {
        Object obj = null;
        try {
            obj = cls.newInstance();
        } catch (Exception e) {
            LOGGER.error("instance object failure");
        }
        return obj;
    }

    public static Object invoke(Object obj, Method method, Object... params) {
        Object result = null;
        try {
            method.setAccessible(true);
            result = method.invoke(obj, params);
        } catch (Exception e) {
            LOGGER.error("method invoke failure:", e);
        }
        return result;
    }
}
