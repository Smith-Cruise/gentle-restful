package com.gentle.bean;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by Smith on 2017/5/18.
 */
public class HttpBody {
    @JSONField(ordinal = 1)
    private int code;

    @JSONField(ordinal = 2)
    private String msg;

    @JSONField(ordinal = 3)
    private Object data;

    public HttpBody() {
    }

    public HttpBody(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
