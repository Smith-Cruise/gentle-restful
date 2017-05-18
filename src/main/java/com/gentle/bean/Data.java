package com.gentle.bean;

/**
 * Created by Smith on 2017/5/16.
 */
public class Data {
    private ResponseStatus status;
    private Object data;

    /*
    * 只传数据默认status为success
    * */
    public Data(Object data) {
        handle(ResponseStatus.SUCCESS, data);
    }

    public Data(ResponseStatus status, Object data) {
        handle(status, data);
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

    private void handle(ResponseStatus status, Object data) {
        this.status = status;
        this.data = data;
    }
}
