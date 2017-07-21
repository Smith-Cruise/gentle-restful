package org.inlighting.gentle.annotation;

import org.inlighting.gentle.proxy.AuthProxy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Smith on 2017/5/25.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Auth {
    Class<? extends AuthProxy> authProxy();
}
