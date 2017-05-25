package com.gentle.helper;

import com.gentle.annotation.Auth;
import com.gentle.proxy.AuthProxy;
import com.gentle.proxy.Proxy;
import com.gentle.util.ProxyUtil;
import com.gentle.util.ReflectionUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Set;

/**
 * Created by Smith on 2017/5/25.
 */
public final class AopHelper {
    private static final Logger LOGGER = LogManager.getLogger();

    // todo 此处待开发，暂时只支持Auth
    static {
        Set<Class<?>> classSet = ClassHelper.getAuthClassSet();
        for (Class<?> cls: classSet) {
            cls.isAnnotationPresent(Auth.class);
            Auth auth = cls.getAnnotation(Auth.class);
            Class<? extends AuthProxy> authProxyClass = auth.authProxy();
            Proxy proxy = (Proxy) ReflectionUtil.newInstance(authProxyClass);
            Object object = ProxyUtil.getInstance(cls, proxy);
            BeanHelper.updateBean(cls, object);
        }
    }
}
