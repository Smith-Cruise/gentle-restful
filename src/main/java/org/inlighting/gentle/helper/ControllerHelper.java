package org.inlighting.gentle.helper;

import org.inlighting.gentle.annotation.Action;
import org.inlighting.gentle.bean.Handler;
import org.inlighting.gentle.bean.Request;
import org.inlighting.gentle.bean.RequestMethod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Smith on 2017/5/17.
 */
public final class ControllerHelper {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Map<Request, Handler> ACTION_MAP = new HashMap<>();

    static {
        Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
        final String BASE_PATH = ConfigHelper.getAppBasePath();
        for (Class controllerClass: controllerClassSet) {
            /* 获取次controller的所有方法，包含private等等 */
            Method[] methods = controllerClass.getDeclaredMethods();
            for (Method method: methods) {
                if (method.isAnnotationPresent(Action.class)) {
                    Action action = method.getAnnotation(Action.class);
                    String requestPath = action.path();
                    RequestMethod requestMethod = action.method();
                    Request request = new Request(requestMethod, BASE_PATH+requestPath);

                    Handler handler = new Handler(controllerClass, method);
                    if (ACTION_MAP.containsKey(request))
                        LOGGER.warn("this url with method is repeated");
                    else
                        ACTION_MAP.put(request, handler);
                }
            }
        }
    }

    public static Handler getHandler(String requestPath, RequestMethod method) {
        Request request = new Request(method, requestPath);
        return ACTION_MAP.get(request);
    }
}
