package com.gentle.proxy;

import com.gentle.bean.Data;
import com.gentle.bean.ResponseStatus;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by Smith on 2017/5/25.
 */
public abstract class AuthProxy extends Proxy {

    @Override
    public void before() {

    }

    public boolean authenticate() {
        return true;
    }

    @Override
    public void after() {

    }

    @Override
    public final Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        before();
        Object result;
        if (authenticate()) {
            result = methodProxy.invokeSuper(o, objects);
        } else {
            result = new Data(ResponseStatus.UNAUTHORIZED);
        }

        after();
        return result;
    }
}
