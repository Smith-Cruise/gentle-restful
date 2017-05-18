package com.gentle.bean;

import java.lang.reflect.Method;

/**
 * Created by Smith on 2017/5/17.
 */
public class Handler {
    private Class controllerClass;
    private Method method;

    public Handler() {
    }

    public Handler(Class controllerClass, Method method) {
        this.controllerClass = controllerClass;
        this.method = method;
    }

    public Class getControllerClass() {
        return controllerClass;
    }

    public void setControllerClass(Class controllerClass) {
        this.controllerClass = controllerClass;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
