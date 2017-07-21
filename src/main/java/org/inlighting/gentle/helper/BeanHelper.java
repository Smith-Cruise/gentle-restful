package org.inlighting.gentle.helper;

import org.inlighting.gentle.util.ReflectionUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Smith on 2017/5/17.
 */
public final class BeanHelper {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Map<Class, Object> BEAN_MAP = new HashMap<>();

    static {
        Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
        for (Class cls: controllerClassSet) {
            Object object = ReflectionUtil.newInstance(cls);
            if (object!=null) {
                BEAN_MAP.put(cls, object);
            }
        }
    }

    public static void updateBean(Class cls, Object obj) {
        BEAN_MAP.put(cls, obj);
    }

    public static Object getBean(Class cls) {
        return BEAN_MAP.get(cls);
    }
}
