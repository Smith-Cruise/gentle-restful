package com.gentle.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by Smith on 2017/5/16.
 */
public final class ClassUtil {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String rootPath;

    static {
        StringBuilder stringBuilder = new StringBuilder(getClassLoader().getResource("").getFile());
        rootPath = stringBuilder.toString().replace("\\", "/");
    }

    public static Set<Class<?>> getClassSet(String packageName) {
        Set<Class<?>> classSet = new HashSet<>();
        try {
            Enumeration<URL> urls = getClassLoader().getResources(packageName.replace(".","/"));
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                if (url!=null) {
                    String protocol = url.getProtocol();
                    if (protocol.equals("file")) {
                        String packagePath = url.getPath().replaceAll("%20", "");
                        addFile(classSet, new File(packagePath));
                    } else if (protocol.equals("jar")) {
                        JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
                        if (jarURLConnection!=null) {
                            JarFile jarFile = jarURLConnection.getJarFile();
                            if (jarFile!=null) {
                                Enumeration<JarEntry> jarEntries = jarFile.entries();
                                while (jarEntries.hasMoreElements()) {
                                    JarEntry jarEntry = jarEntries.nextElement();
                                    String jarEntryName = jarEntry.getName();
                                    if (jarEntryName.endsWith(".class")) {
                                        String jarEntryClassName = jarEntryName.replace("/", ".");
                                        addClassToSet(classSet, jarEntryClassName);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            LOGGER.error("can't read file:"+e.getMessage());
        } catch (ClassNotFoundException e) {
            LOGGER.error("class not found:"+e.getMessage(), e);
        }
        return classSet;
    }

    private static void addFile(Set<Class<?>> classSet, File file) throws IOException, ClassNotFoundException {
        if (file.isFile()) {
            String tempFilePath = file.getPath().replace("\\", "/");
            StringBuilder tempFilePathBuilder = new StringBuilder(tempFilePath);
            if (!tempFilePath.startsWith("/")) {
                tempFilePathBuilder.insert(0, "/");
            }
            String finalFilePath = tempFilePathBuilder.toString();
            String className = finalFilePath.replace(rootPath, "").replace("/", ".");
            addClassToSet(classSet, className);
        } else if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files!=null) {
                for (File subFile: files) {
                    addFile(classSet, subFile);
                }
            }
        }
    }

    private static void addClassToSet(Set<Class<?>> classSet, String className) throws ClassNotFoundException {
        className = className.replace(".class", "");
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
