package org.inlighting.gentle.util.sql;

import java.sql.Connection;
import java.sql.ResultSet;

/**
 * Created by Smith on 2017/7/17.
 * 暂时只支持最简单的insert和update
 */
public class SqlUtil {

    private Connection connection;

    // 表名
    private String tableName;

    // where的数据
    private SqlDataInterface whereData;

    // limit
    private String limit;

    // order
    private String order;

    // select 的 field
    private String field;

    public SqlUtil(Connection connection) {
        this.connection = connection;
    }

    public SqlUtil table(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public SqlUtil where(SqlDataInterface data) {
        this.whereData = data;
        return this;
    }

    public SqlUtil field(String field) {
        this.field = field;
        return this;
    }

    public SqlUtil order(String order) {
        this.order = order;
        return this;
    }

    public SqlUtil limit(String limit) {
        this.limit = limit;
        return this;
    }

    public int insert(SqlDataInterface data) throws Exception {
        return SqlExecutor.insert(connection, tableName, data);
    }

    public int delete() throws Exception {
        if (tableName==null)
            throw new Exception("table name is not defined");
        if (whereData==null)
            throw new Exception("where field is not defined");
        return SqlExecutor.delete(connection, tableName, whereData);
    }

    public int update(SqlDataInterface data) throws Exception {
        if (tableName==null)
            throw new Exception("table name is not defined");
        if (whereData==null)
            throw new Exception("where field is not defined");
        return SqlExecutor.update(connection, tableName, data, whereData);
    }

    public ResultSet select() throws Exception {
        if (tableName==null)
            throw new Exception("table name is not defined");
        if (whereData==null)
            throw new Exception("where field is not defined");
        if (field==null)
            throw new Exception("select field is not defined");
        return SqlExecutor.select(connection, tableName, whereData, field, order, limit);
    }

    // 自定义sql语句
    public ResultSet sqlQuery(final String sql, Object... params) throws Exception {
        return SqlExecutor.executeQuery(connection, sql, params);
    }

    // 自定义sql语句
    public int sqlExecute(final String sql, Object... params) throws Exception {
        return SqlExecutor.executeSql(connection, sql, params);
    }

    // 自定义sql语句
    public void sql(final String sql, Object... params) throws Exception {
        SqlExecutor.execute(connection, sql, params);
    }

}
