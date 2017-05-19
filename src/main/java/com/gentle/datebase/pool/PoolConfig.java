package com.gentle.datebase.pool;

/**
 * Created by Smith on 2017/5/18.
 */
public class PoolConfig {
    // todo 连接最大空闲时间

    private String database; // 数据库类型
    private String driver; // 数据库连接驱动
    private String url; // 数据库连接URL
    private String username; // 数据库连接user
    private String password; // 数据库连接password
    private int minConnection; // 数据库连接池最小连接数
    private int maxConnection; // 数据库连接池最大连接数
    private long waitTime; // 取得连接的最大等待时间

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getMinConnection() {
        return minConnection;
    }

    public void setMinConnection(int minConnection) {
        this.minConnection = minConnection;
    }

    public int getMaxConnection() {
        return maxConnection;
    }

    public void setMaxConnection(int maxConnection) {
        this.maxConnection = maxConnection;
    }

    public long getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(long waitTime) {
        this.waitTime = waitTime;
    }
}
