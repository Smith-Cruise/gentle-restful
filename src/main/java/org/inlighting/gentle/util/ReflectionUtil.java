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

    public static Object invoke(Object obj, Method method, Object... params) throws Exception {
        method.setAccessible(true);
        return method.invoke(obj, params);
    }
}
