package com.gentle.helper;

import com.gentle.annotation.Controller;
import com.gentle.util.ClassUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Smith on 2017/5/16.
 */
public final class ClassHelper {
    private static final Logger LOGGER = LogManager.getLogger();

    // 框架class set集合
    private static final Set<Class<?>> BASE_CLASS_SET;

    // 应用class set集合
    private static final Set<Class<?>> APP_CLASS_SET;

    static {
        BASE_CLASS_SET = ClassUtil.getClassSet("com.gentle");
        APP_CLASS_SET = ClassUtil.getClassSet(ConfigHelper.getAppPackageName());
    }

    public static Set<Class<?>> getBaseClassSet() {
        return BASE_CLASS_SET;
    }

    public static Set<Class<?>> getControllerClassSet() {
        Set<Class<?>> controllerSet = new HashSet<>();
        for (Class cls: APP_CLASS_SET) {
            if (cls.isAnnotationPresent(Controller.class)) {
                controllerSet.add(cls);
            }
        }

        return controllerSet;
    }
}
