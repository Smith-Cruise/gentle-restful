package com.gentle.util;

import com.gentle.helper.ServletHelper;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 提供一些常用的方法
 * Created by Smith on 2017/5/19.
 */
public final class Util {

    /*
    * 如果字符串为空则返回null
    * */
    public static String input(String s) {
        if (s.equals(""))
            return null;
        else
            return s;
    }

    /*
    * 如果获得的值为空，则会自动转化为null
    * 获得的最终结果就是
    * <"param", null> <"user", "123">
    * 而不会存在以前的样子 <"user", "">
    * */
    public static Map<String, String> getParameters() {
        Map<String, String> map = new HashMap<>();
        HttpServletRequest request = ServletHelper.get().getHttpServletRequest();
        Enumeration<String> params = request.getParameterNames();
        while (params.hasMoreElements()) {
            String paramName = params.nextElement();
            String paramValue = input(request.getParameter(paramName));
            map.put(paramName, paramValue);
        }
        return map;
    }
}
