package org.inlighting.gentle.helper;

import org.inlighting.gentle.annotation.Auth;
import org.inlighting.gentle.proxy.AuthProxy;
import org.inlighting.gentle.proxy.Proxy;
import org.inlighting.gentle.util.ProxyUtil;
import org.inlighting.gentle.util.ReflectionUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Set;

/**
 * Created by Smith on 2017/5/25.
 */
public final class AopHelper {
    private static final Logger LOGGER = LogManager.getLogger();

    // todo 此处待开发，暂时只支持Auth
    // todo 如果URL地址重复则抛出异常
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
