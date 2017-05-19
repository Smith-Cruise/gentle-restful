package com.gentle;

import com.gentle.bean.*;
import com.gentle.helper.BeanHelper;
import com.gentle.helper.ControllerHelper;
import com.gentle.helper.ServletHelper;
import com.gentle.util.FrameworkUtil;
import com.gentle.util.JsonUtil;
import com.gentle.util.ReflectionUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * Created by Smith on 2017/5/17.
 */
@WebServlet(urlPatterns = "/*")
public class DispatchServlet extends HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException {
        // 初始化类
        Loader.load();


    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /* 保存当前线程的request 和 response */
        ServletHelper.init(req, resp);

        RequestMethod requestMethod = FrameworkUtil.convertToRequestMethod(req.getMethod().toUpperCase());
        String requestPath = req.getPathInfo();

        /* 获取指定Handler */
        Handler handler = ControllerHelper.getHandler(requestPath, requestMethod);
        if (handler!=null) {
            Class controllerClass = handler.getControllerClass();
            Object controllerObject = BeanHelper.getBean(controllerClass);
            Method method = handler.getMethod();
            Object result = null;
            result = ReflectionUtil.invoke(controllerObject, method);
            if (result instanceof Data) {
                Data data = (Data)result;
                if (data.getStatus() == ResponseStatus.SUCCESS) {
                    do200(resp, data.getData());
                } else if (data.getStatus() == ResponseStatus.ERROR) {
                    do400(resp, data.getData());
                } else if (data.getStatus() == ResponseStatus.UNAUTHORIZED) {
                    do401(resp, data.getData());
                } else {
                    do404(resp, data.getData());
                }
            }
        } else {
            do404(resp, null);
        }
    }

    /*
    * 200 SUCCESS
    * 400 ERROR
    * 401 UNAUTHORIZED
    * 404 NOT FOUND
    * */

    private void do200(HttpServletResponse response, Object data) throws IOException {
        response.setStatus(200);
        response.getWriter().write(getHttpBody(200, "success", data));
    }

    private void do400(HttpServletResponse response, Object data) throws IOException {
        response.setStatus(400);
        response.getWriter().write(getHttpBody(400, "invalid request", data));
    }

    private void do401(HttpServletResponse response, Object data) throws IOException {
        response.setStatus(401);
        response.getWriter().write(getHttpBody(401, "unauthorized", data));
    }

    private void do404(HttpServletResponse response, Object data) throws IOException {
        response.setStatus(404);
        response.getWriter().write(getHttpBody(404, "not found", data));
    }

    private String getHttpBody(int code, String msg, Object data) {
        HttpBody httpBody = new HttpBody(code, msg, data);
        return JsonUtil.toJson(httpBody);
    }
}
