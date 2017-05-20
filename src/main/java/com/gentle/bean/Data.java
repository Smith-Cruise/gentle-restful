package com.gentle.bean;

/**
 * Created by Smith on 2017/5/16.
 */
public class Data {
    private ResponseStatus status;
    private String msg;
    private Object data;

    public Data() {

    }

    /*
    * 只传数据默认status为success
    * */
    public Data(Object data) {
        this(ResponseStatus.SUCCESS, null, data);
    }

    /*
    * 默认为success
    * */
    public Data(String msg, Object data) {
        this(ResponseStatus.SUCCESS, msg, data);
    }

    public Data(ResponseStatus status, String msg) {
        this(status, msg, null);
    }

    public Data(ResponseStatus status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
