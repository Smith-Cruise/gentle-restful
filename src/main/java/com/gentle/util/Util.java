package com.gentle.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gentle.annotation.Db;
import com.gentle.bean.Servlet;
import com.gentle.helper.ServletHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.sql.*;
import java.util.*;

/**
 * 提供一些常用的方法
 * Created by Smith on 2017/5/19.
 */
public final class Util {
    private static final Logger LOGGER = LogManager.getLogger();

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

    /*
    * 同时获取getParameters()和getRequestJsonAsMap()
    * */
    public static Map<String, String> getAllParameters() {
        Map<String, String> map = new HashMap<>();
        HttpServletRequest request = ServletHelper.get().getHttpServletRequest();
        Enumeration<String> params = request.getParameterNames();
        if (params.hasMoreElements()) {
            while (params.hasMoreElements()) {
                String paramName = params.nextElement();
                String paramValue = input(request.getParameter(paramName));
                map.put(paramName, paramValue);
            }
        }

        Map<String, String> jsonMap = getRequestJsonAsMap();
        if (jsonMap!=null) {
            map.putAll(jsonMap);
        }
        return map;
    }

    public static String getRequestJson() {
        HttpServletRequest request = ServletHelper.get().getHttpServletRequest();
        StringBuilder builder = new StringBuilder();
        try {
            BufferedReader reader = request.getReader();
            String temp;
            while ((temp = reader.readLine()) !=null) {
                builder.append(temp);
            }
            return builder.toString();
        } catch (IOException e) {
            return null;
        }
    }

    /*获取以JSON形式提交的数据，字段为空返回null*/
    public static Map<String, String> getRequestJsonAsMap() {
        String json = getRequestJson();
        if (json==null)
            return null;

        try {
            JSONObject object = JSON.parseObject(json);
            Map<String, Object> map = JSON.toJavaObject(object, Map.class);
            Map<String, String> param = new HashMap<>();
            for (Map.Entry<String, Object> entry: map.entrySet()) {
                String value = input(String.valueOf(entry.getValue()));
                param.put(entry.getKey(), value);
            }
            return param;
        } catch (Exception e) {
            return null;
        }
    }

    public static String getAuthorization() {
        return ServletHelper.get().getHttpServletRequest().getHeader("Authorization");
    }

    public static  <T> T resultSetConvertToEntity(ResultSet rs, Class<T> cls) throws Exception {
        Field[] fields = cls.getDeclaredFields();
        T entity = cls.newInstance();
        if (rs.next()) {
            for (Field field: fields) {
                Db db = field.getAnnotation(Db.class);
                String fieldName = db.fieldName();
                Object value = null;
                String type = field.getType().getName();
                if (type.equals(int.class.getName())) {
                    value = rs.getInt(fieldName);
                } else if (type.equals(String.class.getName())) {
                    value = rs.getString(fieldName);
                } else if (type.equals(double.class.getName())) {
                    value = rs.getDouble(fieldName);
                } else if (type.equals(long.class.getName())) {
                    value = rs.getLong(fieldName);
                } else if (type.equals(boolean.class.getName())) {
                    value = rs.getBoolean(fieldName);
                } else if (type.equals(short.class.getName())) {
                    value = rs.getShort(fieldName);
                } else if (type.equals(java.sql.Date.class.getName())) {
                    value = rs.getDate(fieldName);
                } else if (type.equals(Array.class.getName())) {
                    value = rs.getArray(fieldName);
                } else if (type.equals(BigDecimal.class.getName())) {
                    value = rs.getBigDecimal(fieldName);
                } else if (type.equals(Blob.class.getName())) {
                    value = rs.getBlob(fieldName);
                } else if (type.equals(byte.class.getName())) {
                    value = rs.getByte(fieldName);
                } else if (type.equals(byte[].class.getName())) {
                    value = rs.getBytes(fieldName);
                } else {
                    value = rs.getObject(fieldName);
                }

                /* 方法反射 */
                String originFieldName = field.getName();
                String first = originFieldName.substring(0,1).toUpperCase();
                String newFieldName = first+originFieldName.substring(1);
                String methodName = "set"+newFieldName;
                Method method = cls.getMethod(methodName, field.getType());
                method.invoke(entity, value);
            }
        } else {
            throw new SQLException("the ResultSet is end");
        }
        return entity;
    }

    public static  <T> List<T> resultSetConvertToEntityList(ResultSet rs, Class<T> cls) throws Exception {
        List<T> list = new ArrayList<>();
        while (rs.next()) {
            rs.previous();
            T entity = resultSetConvertToEntity(rs, cls);
            list.add(entity);
        }
        return list;
    }

    public static Servlet getServlet() {
        return ServletHelper.get();
    }

    /*
    * 返回32位小写
    * */
    public static String getMD5(String source) {
        StringBuilder sb = new StringBuilder(32);
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] array = md.digest(source.getBytes("utf-8"));
            for (byte b: array) {
                sb.append(Integer.toHexString((b & 0xFF) | 0x100).substring(1, 3));

            }
        } catch (Exception e) {
            return null;
        }
        return sb.toString();
    }

    /*
    * 返回32位大写
    * */
    public static String getMD5(String source, boolean isUppercase) {
        StringBuilder sb = new StringBuilder(32);
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] array = md.digest(source.getBytes("utf-8"));
            for (byte b: array) {
                sb.append(Integer.toHexString((b & 0xFF) | 0x100).toUpperCase().substring(1, 3));
            }
        } catch (Exception e) {
            return null;
        }
        return sb.toString();
    }

    /*
    * 返回HMAC_SHA1 小写
    * */
    public static String getHMAC_SHA1(String secret, String source) {
        try {
            byte[] tempResult = getHMAC_SHA1(secret, source, true);
            StringBuilder hs = new StringBuilder();
            String temp;
            for (int n = 0; tempResult!=null && n < tempResult.length; n++) {
                temp = Integer.toHexString(tempResult[n] & 0XFF);
                if (temp.length() == 1)
                    hs.append('0');
                hs.append(temp);
            }
            return hs.toString();
        } catch (Exception e) {
            return null;
        }
    }

    /*
    * 返回HMAC_SHA1原生二进制数据
    * */
    public static byte[] getHMAC_SHA1(String secret, String source, boolean isHex) {
        try {
            byte[] key = secret.getBytes("utf-8");
            byte[] data = source.getBytes("utf-8");
            SecretKeySpec signingKey = new SecretKeySpec(key, "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);

            return mac.doFinal(data);
        } catch (Exception e) {
            return null;
        }
    }

    public static String getBase64(String s) {
        try {
            return getBase64(s.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public static String getBase64(byte[] s) {
        return Base64.getEncoder().encodeToString(s);
    }
}
