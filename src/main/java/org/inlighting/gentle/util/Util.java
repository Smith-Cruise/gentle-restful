package org.inlighting.gentle.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.inlighting.gentle.annotation.Db;
import org.inlighting.gentle.bean.Servlet;
import org.inlighting.gentle.helper.ServletHelper;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.sql.Date;
import java.sql.*;
import java.util.*;

/**
 * 提供一些常用的方法
 * @author smith
 */
public final class Util {
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * 检验一个字符串是否为空白，如果为空白则返回null
     * @param s 字符串
     * @return 返回字符串
     */
    public static String input(String s) {
        if (s.equals(""))
            return null;
        else
            return s;
    }

    /**
     * 类似于getParameter，返回Map格式.
     * 如果获得的值为空，则会自动转化为null.<br>
     * 例如URL 传值param: "", name: "smith",
     * 则返回 {"data", null} {"name", "smith"}
     * @return 返回Map
     */
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

    /**
     * 同时获取getParameters()和getRequestJsonAsMap()
     * @return 返回Map数据
     */
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

    public void setSession(String key, Object value) {
        HttpServletRequest request = ServletHelper.get().getHttpServletRequest();
        request.getSession().setAttribute(key, value);
    }

    public Object getSession(String key) {
        HttpServletRequest request = ServletHelper.get().getHttpServletRequest();
        return request.getSession().getAttribute(key);
    }

    /**
     * 获取表单以body格式提交的数据
     * @return 得到的数据
     */
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

    /**
     * 获取表单以body格式提交的数据.
     * 并且转化为Map格式
     * @return 返回Map
     */
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

    /**
     * 获取http字段中的Authorization.
     * 通常用于前后端通信的鉴权.
     * @return 返回Authorization
     */
    public static String getAuthorization() {
        return ServletHelper.get().getHttpServletRequest().getHeader("Authorization");
    }

    /**
     * 将ResultSet转化为实体
     * @param rs ResultSet
     * @param cls 实体的类
     * @param <T> 泛型
     * @return 返回实体
     * @throws Exception 异常
     */
    public static  <T> T resultSetConvertToEntity(ResultSet rs, Class<T> cls) throws Exception {
        Field[] fields = cls.getDeclaredFields();
        T entity = cls.newInstance();
        if (rs.next()) {
            for (Field field: fields) {
                // check field type
                if (field.getType().isPrimitive())
                    throw new Exception("The field type can't be primitive type");

                field.setAccessible(true);
                Db db = field.getAnnotation(Db.class);
                String fieldName = db.column();
                Object value = null;
                String typeName = field.getType().getName();
                if (typeName.equals(Integer.class.getName())) {
                    value = rs.getInt(fieldName);
                } else if (typeName.equals(String.class.getName())) {
                    value = rs.getString(fieldName);
                } else if (typeName.equals(Long.class.getName())) {
                    value = rs.getLong(fieldName);
                } else if (typeName.equals(Double.class.getName())) {
                    value = rs.getDouble(fieldName);
                } else if (typeName.equals(Float.class.getName())) {
                    value = rs.getFloat(fieldName);
                } else if (typeName.equals(Date.class.getName())) {
                    value = rs.getDate(fieldName);
                } else if (typeName.equals(Time.class.getName())) {
                    value = rs.getTime(fieldName);
                } else if (typeName.equals(Timestamp.class.getName())) {
                    value = rs.getTimestamp(fieldName);
                } else if (typeName.equals(Boolean.class.getName())) {
                    value = rs.getBoolean(fieldName);
                } else if (typeName.equals(Byte.class.getName())) {
                    value = rs.getByte(fieldName);
                } else if (typeName.equals(Short.class.getName())) {
                    value = rs.getShort(fieldName);
                } else if (typeName.equals(Byte[].class.getName())) {
                    value = rs.getBytes(fieldName);
                } else {
                    value = rs.getObject(fieldName);
                }
                field.set(entity, value);
            }
        } else {
            throw new SQLException("the ResultSet is end");
        }
        return entity;
    }

    /**
     * 将ResultSet转化为多个实体,并且以List存储
     * @param rs ResultSet
     * @param cls 实体的类
     * @param <T> 泛型
     * @return 返回List
     * @throws Exception 抛出异常
     */
    public static <T> List<T> resultSetConvertToEntityList(ResultSet rs, Class<T> cls) throws Exception {
        List<T> list = new ArrayList<>();
        while (rs.next()) {
            rs.previous();
            T entity = resultSetConvertToEntity(rs, cls);
            list.add(entity);
        }
        return list;
    }

    /**
     * @return 返回当前线程的Servlet
     */
    public static Servlet getServlet() {
        return ServletHelper.get();
    }

    /**
     * 返回md5
     * @param source 待加密字符串
     * @param isUppercase 是否大小写
     * @return 返回加密后的字符串
     */
    public static String getMD5(String source, boolean isUppercase) {
        StringBuilder sb = new StringBuilder(32);
        if (isUppercase) {
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
        } else {
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
    }

    /**
     * 返回HMAC_SHA1小写
     * @param secret 密钥
     * @param source 待加密
     * @return 加密后字符串
     */
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

    /**
     * 二进制返回HMAC_SHA1
     * @param secret 密钥
     * @param source 待加密
     * @param isHex 返回二进制
     * @return 返回加密后字符串
     */
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

    /**
     * 计算base64
     * @param s 字符串
     * @return 得到base64
     */
    public static String getBase64(String s) {
        try {
            return getBase64(s.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    /**
     * 计算base64
     * @param s 字符串
     * @return 得到base64
     */
    public static String getBase64(byte[] s) {
        return Base64.getEncoder().encodeToString(s);
    }

    /**
     * 讲Object转化为json，内部调用 fastjson
     * @param obj 变量
     * @return json
     */
    public static String getJson(Object obj) {
        return JSON.toJSONString(obj);
    }
}
