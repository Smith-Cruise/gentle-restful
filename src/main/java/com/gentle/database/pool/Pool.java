package com.gentle.database.pool;

import com.gentle.database.driver.Mysql;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Vector;

/**
 * Created by Smith on 2017/5/19.
 */
public class Pool {
    private final static Logger LOGGER = LogManager.getLogger();

    private PoolConfig config;

    private Vector<PooledConnection> connections;

    public Pool(PoolConfig poolConfig) throws Exception {
        config = poolConfig;
        Class.forName(config.getDriver());
    }

    public synchronized Connection getConnection() throws InterruptedException {
        if (connections == null) {
            connections = new Vector<>();
            initializePool();
        }

        PooledConnection connection = null;
        while (connection == null) {
            connection = getFreeConnection();
        }
        return connection.getConnection();
    }

    public void backConnection(Connection connection) {
        for (PooledConnection pooledConnection: connections) {
            if (pooledConnection.getConnection() == connection) {
                pooledConnection.setBusy(false);
            }
        }
    }

    private void initializePool() {
        int min = config.getMinConnection();
        for (int i=0; i<min; i++) {
            try {
                connections.addElement(createPooledConnection());
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
            }
        }
    }

    private PooledConnection getFreeConnection() {
        if (connections.size() >= config.getMaxConnection()) {
            Iterator<PooledConnection> connectionIterator = connections.iterator();
            while (connectionIterator.hasNext()) {
                PooledConnection connection = connectionIterator.next();
                // 连接不忙
                if (!connection.isBusy()) {
                    // 测试连接
                    if (testConnection(connection)) {
                        connection.setBusy(true);
                        return connection;
                    } else {
                        System.out.println("shanchu");
                        connectionIterator.remove();
                    }
                }
            }
            return null;
        } else {
            try {
                PooledConnection connection = createPooledConnection();
                connections.add(connection);
                return connection;
            } catch (SQLException e) {
                return null;
            }
        }
    }

    private boolean testConnection(PooledConnection connection) {
        boolean status;
        try {
            status = connection.getConnection().isValid(0);
        } catch (SQLException e) {
            status = false;
        }
        return status;
    }

    private PooledConnection createPooledConnection() throws SQLException {
        // todo 暂时只支持mysql
        Connection connection = Mysql.getConnection(config.getUrl(), config.getUsername(), config.getPassword());
        return new PooledConnection(connection);
    }
}
