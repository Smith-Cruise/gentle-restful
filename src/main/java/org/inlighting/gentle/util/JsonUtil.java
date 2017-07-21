package org.inlighting.gentle.util;

import com.alibaba.fastjson.JSON;

/**
 * Created by Smith on 2017/5/18.
 */
public final class JsonUtil {
    public static String toJson(Object object) {
        return JSON.toJSONString(object);
    }
}
