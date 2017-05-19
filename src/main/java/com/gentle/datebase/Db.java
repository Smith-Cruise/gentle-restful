package com.gentle.datebase;

import com.gentle.datebase.pool.Pool;
import com.gentle.datebase.pool.PoolConfig;
import com.gentle.datebase.pool.PoolException;
import com.gentle.helper.ConfigHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;

/**
 * Created by Smith on 2017/5/16.
 */
public final class Db {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final PoolConfig POOL_CONFIG = new PoolConfig();

    private static Pool pool;

    /* 数据库连接池配置文件的key */
    private static final String DATABASE_KEY = "pool.database";
    private static final String DRIVER_KEY = "pool.driver";
    private static final String URL_KEY = "pool.url";
    private static final String USERNAME_KEY = "pool.username";
    private static final String PASSWORD_KEY = "pool.password";
    private static final String MIN_CONNECTION_KEY = "pool.minConnection";
    private static final String MAX_CONNECTION_KEY = "pool.maxConnection";
    private static final String WAIT_TIME_KEY = "pool.waitTime";

    static {
        // 判断是否开启数据库连接池
        if (ConfigHelper.getDatabasePoolSwitch()) {
            // 装载配置
            loadConfig();

            if (pool == null) {
                try {
                    pool = new Pool(POOL_CONFIG);
                } catch (Exception e) {
                    LOGGER.error("database pool initialize failed, system had to stop");
                    System.exit(-1);
                }
            }
        }
        System.out.println("pool init ok");
    }

    public static Connection getConnection() throws Exception {
        if (pool == null) {
            throw new PoolException("database pool had not been instanced");
        }

        return pool.getConnection();
    }

    public static void backConnection(Connection connection) {
        if (pool != null) {
            System.out.println("开始归还");
            pool.backConnection(connection);
        }
    }

    private static void loadConfig() {
        POOL_CONFIG.setDatabase(ConfigHelper.getPropertyAsString(DATABASE_KEY));
        POOL_CONFIG.setDriver(ConfigHelper.getPropertyAsString(DRIVER_KEY));
        POOL_CONFIG.setUrl(ConfigHelper.getPropertyAsString(URL_KEY));
        POOL_CONFIG.setUsername(ConfigHelper.getPropertyAsString(USERNAME_KEY));
        POOL_CONFIG.setPassword(ConfigHelper.getPropertyAsString(PASSWORD_KEY));
        POOL_CONFIG.setMinConnection(ConfigHelper.getPropertyAsInt(MIN_CONNECTION_KEY));
        POOL_CONFIG.setMaxConnection(ConfigHelper.getPropertyAsInt(MAX_CONNECTION_KEY));
        POOL_CONFIG.setWaitTime(ConfigHelper.getPropertyAsLog(WAIT_TIME_KEY));
    }
}
