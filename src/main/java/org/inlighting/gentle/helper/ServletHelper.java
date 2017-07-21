package org.inlighting.gentle.helper;

import org.inlighting.gentle.bean.Servlet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用于存储当前线程的Request和Response
 */
public final class ServletHelper {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final ThreadLocal<Servlet> SERVLET_THREAD_LOCAL = new ThreadLocal<>();

    public static void init(HttpServletRequest request, HttpServletResponse response) {
        Servlet servlet = new Servlet(request, response);
        SERVLET_THREAD_LOCAL.set(servlet);
    }

    public static Servlet get() {
        return SERVLET_THREAD_LOCAL.get();
    }
}
