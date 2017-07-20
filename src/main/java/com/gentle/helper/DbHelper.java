package com.gentle.helper;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by Smith on 2017/5/20.
 */
public final class DbHelper {
    private static final Logger LOGGER = LogManager.getLogger();

    private static DataSource dataSource = null;

    static {
        if (ConfigHelper.getDruidSwitch()) {
            Properties properties = ConfigHelper.getDruidProperties();
            try {
                dataSource = DruidDataSourceFactory.createDataSource(properties);
            } catch (Exception e) {
                LOGGER.error("druid initialized failed");
            }
        }
    }

    public static Connection get() throws SQLException {
        if (dataSource == null)
            throw new SQLException("druid had not been initialized");

        return dataSource.getConnection();
    }

    public static void close(Connection connection) {
        try {
            if (connection!=null) {
                connection.close();
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
