package org.inlighting.gentle.helper;

import org.inlighting.gentle.exception.ParamException;

import java.lang.reflect.Method;

public class ParamHelper {
    public Object[] convertToParam(Method method) throws ParamException {
        /*int size = method.getParameterCount();

        if (size==0) {
            return null;
        }

        Object[] params = new Object[size];
        Parameter[] parameters = method.getParameters();
        for (int i=0; i<parameters.length; i++) {
            Parameter parameter = parameters[i];
            if (parameter.isAnnotationPresent(Param.class)) {
                Map<String, String>
                Param paramA = parameter.getAnnotation(Param.class);
                String value = paramA.
            } else {
                throw new ParamException("param annotation is not present");
            }
        }*/
        return null;
    }
}
