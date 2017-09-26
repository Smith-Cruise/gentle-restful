package org.inlighting.gentle;

import org.inlighting.gentle.helper.ConfigHelper;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;

/**
 * Created by Smith on 2017/5/17.
 */
@WebFilter(urlPatterns = "/*")
public class GlobalFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String baseUrl = ConfigHelper.getAppBasePath();
        HttpServletRequest req = (HttpServletRequest)servletRequest;
        String url = req.getRequestURI();

        servletRequest.setCharacterEncoding("utf-8");
        servletResponse.setCharacterEncoding("utf-8");
        servletResponse.setContentType("application/json;charset=utf-8");
        if (url.startsWith(baseUrl)) {
            servletRequest.getRequestDispatcher("/gentle-restful"+url).forward(servletRequest, servletResponse);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 初始化类
        Loader.load();
    }

    @Override
    public void destroy() {

    }
}
