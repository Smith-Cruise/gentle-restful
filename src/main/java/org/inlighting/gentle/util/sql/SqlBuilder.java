package org.inlighting.gentle.util.sql;

import java.util.List;
import java.util.Map;

/**
 * Created by Smith on 2017/7/17.
 */
final class SqlBuilder {
    private final static String INSERT_SQL = "INSERT INTO `%TABLE%` (%FIELD%) VALUES (%DATA%)";
    private final static String DELETE_SQL = "DELETE FROM `%TABLE%` WHERE %WHERE%";
    private final static String UPDATE_SQL = "UPDATE `%TABLE%` SET %SET% WHERE %WHERE%";
    private final static String SELECT_SQL = "SELECT %FIELD% FROM `%TABLE%` WHERE %WHERE% %ORDER% %LIMIT%";
    // private final static String SELECT_SQL = "SELECT %FIELD% FROM `%TABLE%` %WHERE% %ORDER% %LIMIT%";

    static String getInsertSql(final String TABLE_NAME, List<Map.Entry<String, Object>> mapped) {
        StringBuilder sql = new StringBuilder(INSERT_SQL);

        // 生成field和value
        StringBuilder field = new StringBuilder();
        StringBuilder value = new StringBuilder();
        for (Map.Entry<String, Object> entry: mapped) {
            field.append('`');
            field.append(entry.getKey());
            field.append("`,");

            value.append("?,");
        }
        field.deleteCharAt(field.length()-1);
        value.deleteCharAt(value.length()-1);

        int tempOffset;

        // 替换表名
        tempOffset = sql.indexOf("%TABLE%");
        sql.replace(tempOffset, tempOffset+7, TABLE_NAME);

        // 替换字段
        tempOffset = sql.indexOf("%FIELD%");
        sql.replace(tempOffset, tempOffset+7, field.toString());

        // 替换值
        tempOffset = sql.indexOf("%DATA%");
        sql.replace(tempOffset, tempOffset+6, value.toString());
        return sql.toString();
    }

    static String getDeleteSql(final String TABLE_NAME, List<Map.Entry<String, Object>> mapped) {
        StringBuilder sql = new StringBuilder(DELETE_SQL);

        // 生成field和value
        StringBuilder where = new StringBuilder();
        for (Map.Entry<String, Object> entry: mapped) {
            where.append('`');
            where.append(entry.getKey());
            where.append("`=? AND ");
        }
        where.delete(where.length()-5, where.length());

        int tempOffset;

        tempOffset = sql.indexOf("%TABLE%");
        sql.replace(tempOffset, tempOffset+7, TABLE_NAME);

        tempOffset = sql.indexOf("%WHERE%");
        sql.replace(tempOffset, tempOffset+7, where.toString());

        return sql.toString();
    }

    static String getUpdateSql(final String TABLE_NAME, List<Map.Entry<String, Object>> setMapped,
                               List<Map.Entry<String, Object>> whereMapped) {
        StringBuilder sql = new StringBuilder(UPDATE_SQL);

        // 生成set
        StringBuilder set = new StringBuilder();
        for (Map.Entry<String, Object> entry: setMapped) {
            set.append('`');
            set.append(entry.getKey());
            set.append("`=?,");
        }
        set.deleteCharAt(set.length()-1);


        // 生成where
        StringBuilder where = new StringBuilder();
        for (Map.Entry<String, Object> entry: whereMapped) {
            where.append('`');
            where.append(entry.getKey());
            where.append("`=? AND ");
        }
        where.delete(where.length()-5, where.length());

        int tempOffset;

        tempOffset = sql.indexOf("%TABLE%");
        sql.replace(tempOffset, tempOffset+7, TABLE_NAME);

        tempOffset = sql.indexOf("%SET%");
        sql.replace(tempOffset, tempOffset+5, set.toString());

        tempOffset = sql.indexOf("%WHERE%");
        sql.replace(tempOffset, tempOffset+7, where.toString());
        return sql.toString();
    }

    static String getSelectSql(final String TABLE_NAME, List<Map.Entry<String, Object>> mapped,
                               final String FIELD, final String ORDER, final String LIMIT) {
        StringBuilder sql = new StringBuilder(SELECT_SQL);

        // 生成where
        StringBuilder where = new StringBuilder();
        for (Map.Entry<String, Object> entry: mapped) {
            where.append('`');
            where.append(entry.getKey());
            where.append("`=? AND ");
        }
        where.delete(where.length()-5, where.length());

        int tempOffset;

        tempOffset = sql.indexOf("%TABLE%");
        sql.replace(tempOffset, tempOffset+7, TABLE_NAME);

        tempOffset = sql.indexOf("%FIELD%");
        sql.replace(tempOffset, tempOffset+7, FIELD);

        tempOffset = sql.indexOf("%WHERE%");
        sql.replace(tempOffset, tempOffset+7, where.toString());

        tempOffset = sql.indexOf("%ORDER%");
        if (ORDER!=null)
            sql.replace(tempOffset, tempOffset+7, "ORDER BY "+ORDER);
        else
            sql.replace(tempOffset, tempOffset+7, "");

        tempOffset = sql.indexOf("%LIMIT%");
        if (LIMIT!=null)
            sql.replace(tempOffset, tempOffset+7, "LIMIT "+LIMIT);
        else
            sql.replace(tempOffset, tempOffset+7, "");

        return sql.toString();
    }
}
