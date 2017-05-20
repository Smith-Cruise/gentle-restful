package com.gentle.util;

import com.gentle.helper.ConfigHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Smith on 2017/5/16.
 */
public final class ClassUtil {
    private static final Logger LOGGER = LogManager.getLogger();
    private static Set<Class> classSet = new HashSet<>();

    public static Set<Class> getClassSet(String packageName) {
        StringBuilder stringBuilder = new StringBuilder(getClassLoader().getResource("").getFile());
        stringBuilder.deleteCharAt(0);
        String originalPath = stringBuilder.toString().replace("/", "\\");
        try {
            URL url = getClassLoader().getResource(packageName.replace(".", "/"));
            File[] files = new File(url.getPath()).listFiles();
            for (File file: files) {
                String className = file.getAbsolutePath().replace(originalPath, "").replace("\\", ".").replace(".class", "");
                if (file.getName().endsWith(".class")) {
                    //System.out.println(file.getAbsolutePath());
                    addClassToSet(className);
                } else if (file.isDirectory()) {
                    getClassSet(packageName+"."+file.getName());
                }
            }
        } catch (NullPointerException e) {
            LOGGER.error("get class set in specific package error:"+e.getMessage());
        } catch (ClassNotFoundException e) {
            LOGGER.error("load class error:"+e.getMessage());
        }
        return classSet;
    }

    private static void addClassToSet(String className) throws ClassNotFoundException {
        Class cls = loadClass(className);
        classSet.add(cls);
    }

    private static Class loadClass(String className) throws ClassNotFoundException {
        return Class.forName(className, false, getClassLoader());
    }

    private static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }
}
