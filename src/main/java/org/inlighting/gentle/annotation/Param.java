package org.inlighting.gentle.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * if required=true, and param didn't exist, framework will
 * auto return 400
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Param {
    String value() default "";

    String name() default "";

    boolean required() default true;
}
