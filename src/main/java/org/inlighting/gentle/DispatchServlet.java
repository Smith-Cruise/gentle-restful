package org.inlighting.gentle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.inlighting.gentle.annotation.Param;
import org.inlighting.gentle.bean.*;
import org.inlighting.gentle.exception.ParamException;
import org.inlighting.gentle.helper.BeanHelper;
import org.inlighting.gentle.helper.ControllerHelper;
import org.inlighting.gentle.helper.ServletHelper;
import org.inlighting.gentle.util.FrameworkUtil;
import org.inlighting.gentle.util.ReflectionUtil;
import org.inlighting.gentle.util.Util;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;

/**
 * Created by Smith on 2017/5/17.
 */
@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatchServlet extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void init(ServletConfig config) throws ServletException {
        // 初始化类
        Loader.load();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestPath = req.getPathInfo();
        /* 保存当前线程的request 和 response */
        ServletHelper.init(req, resp);

        RequestMethod requestMethod = FrameworkUtil.convertToRequestMethod(req.getMethod().toUpperCase());

        /* 获取指定Handler */
        Handler handler = ControllerHelper.getHandler(requestPath, requestMethod);
        if (handler!=null) {
            Class controllerClass = handler.getControllerClass();
            Object controllerObject = BeanHelper.getBean(controllerClass);
            Method method = handler.getMethod();

            try {
                int parameterSize = method.getParameterCount();
                Object[] paramObj = null;
                if (parameterSize>0) {
                    paramObj = new Object[parameterSize];
                    Parameter[] parameters = method.getParameters();
                    for (int i=0 ;i<parameterSize; i++) {
                        Parameter parameter = parameters[i];

                        // check parameter isn't primitive type
                        Class parameterType = parameter.getType();
                        if (parameterType.isPrimitive())
                            LOGGER.warn("Param annotation's type can't be primitive type");
                        if (parameter.isAnnotationPresent(Param.class)) {
                            Param paramAnnotation = parameter.getAnnotation(Param.class);
                            String value = paramAnnotation.value();
                            String name = paramAnnotation.name();
                            boolean required = paramAnnotation.required();
                            String paramKey;
                            if (value.length()==0)
                                paramKey = name;
                            else
                                paramKey = value;

                            Map<String, String> params = Util.getParameters();
                            String paramValue = params.get(paramKey);
                            if (required) {
                                if (paramValue==null || paramValue.length()==0)
                                    throw new ParamException();
                                else
                                    paramObj[i] = autoConvert(paramValue, parameterType);
                            } else {
                                if (paramValue==null || paramValue.length()==0)
                                    paramObj[i] = null;
                                else
                                    paramObj[i] = autoConvert(paramValue, parameterType);
                            }
                        } else {
                            throw new ParamException();
                        }
                    }
                }

                Object result;

                if (parameterSize==0)
                    result = ReflectionUtil.invoke(controllerObject, method);
                else
                    result = ReflectionUtil.invoke(controllerObject, method, paramObj);

                if (result instanceof Data) {
                    Data data = (Data)result;
                    returnHttp(resp, data);
                }
            } catch (ParamException e) {
                Data data = new Data(ResponseStatus.ERROR, "parameters didn't meet requirements");
                returnHttp(resp, data);
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
                Data data = new Data(ResponseStatus.ERROR, "some error occurred");
                returnHttp(resp, data);
            }
        } else {
            Data data = new Data(ResponseStatus.NOTFOUND, null, null);
            returnHttp(resp, data);
        }
    }

    /*
    * 200 SUCCESS
    * 400 ERROR
    * 401 UNAUTHORIZED
    * 404 NOT FOUND
    * */
    private void returnHttp(HttpServletResponse response, Data data) throws IOException {
        ResponseStatus status = data.getStatus();
        int code;
        String msg = data.getMsg();
        Object datum = data.getData();
        switch (status) {
            case SUCCESS:
                code = 200;
                if (msg==null)
                    msg = "success";
                break;
            case ERROR:
                code = 400;
                if (msg==null)
                    msg = "invalid request";
                break;
            case UNAUTHORIZED:
                code = 401;
                if (msg==null)
                    msg = "unauthorized";
                break;
            default:
                code = 404;
                if (msg==null)
                    msg = "not found";
        }
        HttpBody body = new HttpBody(code, msg, datum);
        response.setStatus(code);
        response.getWriter().write(Util.getJson(body));
    }

    private Object autoConvert(String s, Class type) throws Exception {
        String name = type.getName();
        if (name.equals(String.class.getName())) {
            return s;
        } else if (name.equals(Integer.class.getName())) {
            return Integer.valueOf(s);
        } else if (name.equals(Double.class.getName())) {
            return Double.valueOf(s);
        } else if (name.equals(Boolean.class.getName())) {
            return Boolean.valueOf(s);
        } else if (name.equals(Long.class.getName())) {
            return Long.valueOf(s);
        } else if (name.equals(Float.class.getName())) {
            return Float.valueOf(s);
        } else if (name.equals(Short.class.getName())) {
            return Short.valueOf(s);
        } else {
            return s;
        }
    }
}
