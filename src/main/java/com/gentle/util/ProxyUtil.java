package com.gentle.util;

import com.gentle.proxy.Proxy;
import net.sf.cglib.proxy.Enhancer;

/**
 * Created by Smith on 2017/5/25.
 */
public final class ProxyUtil {
    private static Enhancer enhancer = new Enhancer();

    public static Object getInstance(Class targetClass, Proxy proxy) {
        enhancer.setSuperclass(targetClass);
        enhancer.setCallback(proxy);
        return enhancer.create();
    }
}
