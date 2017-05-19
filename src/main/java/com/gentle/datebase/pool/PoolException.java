package com.gentle.datebase.pool;

/**
 * Created by Smith on 2017/5/18.
 */
public class PoolException extends Exception {
    public PoolException(String msg) {
        super(msg);
    }

    public PoolException() {
        super("database pool has error occurred");
    }
}
