package org.inlighting.gentle.util.sql;

import org.inlighting.gentle.annotation.Db;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Smith on 2017/7/18.
 */
final class SqlExecutor {
    static int insert(Connection connection, final String TABLE_NAME, SqlDataInterface data) throws Exception {
        List<Map.Entry<String, Object>> parsedData = parseData(data);
        PreparedStatement ps = connection.prepareStatement(SqlBuilder.getInsertSql(TABLE_NAME, parsedData));
        for (int i=1; i<=parsedData.size(); i++) {
            ps.setObject(i, parsedData.get(i-1).getValue());
        }
        return ps.executeUpdate();
    }

    static int delete(Connection connection, final String TABLE_NAME, SqlDataInterface data) throws Exception {
        List<Map.Entry<String, Object>> parsedData = parseData(data);
        PreparedStatement ps = connection.prepareStatement(SqlBuilder.getDeleteSql(TABLE_NAME, parsedData));
        for (int i=1; i<=parsedData.size(); i++) {
            ps.setObject(i, parsedData.get(i-1).getValue());
        }
        return ps.executeUpdate();
    }

    static int update(Connection connection, final String TABLE_NAME, SqlDataInterface setData, SqlDataInterface whereData) throws Exception {
        List<Map.Entry<String, Object>> parsedSetData = parseData(setData);
        List<Map.Entry<String, Object>> parsedWhereData = parseData(whereData);
        PreparedStatement ps = connection.prepareStatement(SqlBuilder.getUpdateSql(TABLE_NAME, parsedSetData, parsedWhereData));
        int i=1;
        for (; i<=parsedSetData.size(); i++) {
            ps.setObject(i, parsedSetData.get(i-1).getValue());
        }

        for (int j=1; j<=parsedWhereData.size(); j++) {
            ps.setObject(i, parsedWhereData.get(j-1).getValue());
            i++;
        }
        return ps.executeUpdate();
    }

    static ResultSet select(Connection connection, final String TABLE_NAME,
                            SqlDataInterface data, final String FIELD,
                            final String ORDER, final String LIMIT) throws Exception {
        List<Map.Entry<String, Object>> parsedData = parseData(data);
        PreparedStatement ps = connection.prepareStatement(SqlBuilder.getSelectSql(TABLE_NAME, parsedData, FIELD, ORDER, LIMIT));
        for (int i=1; i<=parsedData.size(); i++) {
            ps.setObject(i, parsedData.get(i-1).getValue());
        }
        return ps.executeQuery();
    }

    static ResultSet executeQuery(Connection connection, final String sql, Object... params) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(sql);
        for (int i=0; i<params.length; i++) {
            ps.setObject(i+1, params[i]);
        }
        return ps.executeQuery();
    }

    static int executeSql(Connection connection, final String sql, Object... params) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(sql);
        for (int i=0; i<params.length; i++) {
            ps.setObject(i+1, params[i]);
        }
        return ps.executeUpdate();
    }

    static void execute(Connection connection, final String sql, Object... params) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(sql);
        for (int i=0; i<params.length; i++) {
            ps.setObject(i+1, params[i]);
        }
        ps.execute();
    }

    private static List<Map.Entry<String, Object>> parseData(SqlDataInterface data) throws Exception {
        Class c = data.getClass();
        Field[] fields = c.getDeclaredFields();
        Map<String, Object> mapped = new HashMap<>();
        for (Field field: fields) {
            field.setAccessible(true);
            if (field.getType().isPrimitive())
                throw new Exception("The field can't be primitive type");

            Object value = field.get(data);
            if (value!=null) {
                Db db = field.getAnnotation(Db.class);
                String DbColumnName = db.column();
                mapped.put(DbColumnName, value);
            }

        }
        List<Map.Entry<String, Object>> finalList = new ArrayList<>(mapped.entrySet());
        if (finalList.size()==0)
            throw new Exception("Actually where condition is empty");
        return finalList;
    }
}
