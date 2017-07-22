package org.inlighting.gentle;

import org.inlighting.gentle.bean.*;
import org.inlighting.gentle.helper.BeanHelper;
import org.inlighting.gentle.helper.ControllerHelper;
import org.inlighting.gentle.helper.ServletHelper;
import org.inlighting.gentle.util.FrameworkUtil;
import org.inlighting.gentle.util.ReflectionUtil;
import org.inlighting.gentle.util.Util;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * Created by Smith on 2017/5/17.
 */
public class DispatchServlet extends HttpServlet {
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
            Object result = ReflectionUtil.invoke(controllerObject, method);
            if (result instanceof Data) {
                Data data = (Data)result;
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
}
