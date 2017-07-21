package org.inlighting.gentle.helper;

import org.inlighting.gentle.annotation.Auth;
import org.inlighting.gentle.annotation.Controller;
import org.inlighting.gentle.util.ClassUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.annotation.Annotation;
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
        return getSpecificClassSet(Controller.class);
    }

    public static Set<Class<?>> getAuthClassSet() {
        return getSpecificClassSet(Auth.class);
    }

    private static Set<Class<?>> getSpecificClassSet(Class<? extends Annotation> annotation) {
        Set<Class<?>> classSet = new HashSet<>();
        for (Class cls: APP_CLASS_SET) {
            if (cls.isAnnotationPresent(annotation)) {
                classSet.add(cls);
            }
        }
        return classSet;
    }
}
