package app.database;

import com.gentle.helper.DbHelper;
import com.gentle.util.sql.SqlUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;

/**
 * Created by Smith on 2017/7/19.
 */
class DatabaseTest {
    private final String TABLE_NAME = "test";

    @Test
    void testInsert() {
        Assertions.assertEquals(1,doInsert());
    }

    @Test
    void testDelete() {
        Assertions.assertEquals(1, doDelete());
    }

    @Test
    void testUpdate() {
        Assertions.assertEquals(1, doUpdate());
    }

    @Test
    void testSelect() {
        Assertions.assertEquals(1, doSelect());
    }

    private int doInsert() {
        TestEntity data = new TestEntity();
        data.setName("smith");
        data.setDateline(10000L);
        Connection connection = null;
        try {
            connection = DbHelper.get();
            SqlUtil sqlUtil = new SqlUtil(connection);
            return sqlUtil.table(TABLE_NAME).insert(data);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            DbHelper.close(connection);
            doDropSql();
        }
    }

    // must with where condition
    private int doDelete() {
        doInsertTestData();

        TestEntity data = new TestEntity();
        data.setName("smith");
        Connection connection = null;
        try {
            connection = DbHelper.get();
            SqlUtil sqlUtil = new SqlUtil(connection);
            return sqlUtil.table(TABLE_NAME).where(data).delete();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            DbHelper.close(connection);
            doDropSql();
        }
    }

    // must with where condition
    private int doUpdate() {
        // insert data before update
        doInsertTestData();

        TestEntity whereCondition = new TestEntity();
        whereCondition.setName("smith");
        TestEntity updatedCondition = new TestEntity();
        updatedCondition.setDateline(System.currentTimeMillis());

        Connection connection = null;
        try {
            connection = DbHelper.get();
            SqlUtil sqlUtil = new SqlUtil(connection);
            return sqlUtil.table("test").where(whereCondition).update(updatedCondition);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            DbHelper.close(connection);
            doDropSql();
        }
    }

    // must with field and where condition
    private int doSelect() {
        doInsertTestData();

        Connection connection = null;
        try {
            connection = DbHelper.get();
            SqlUtil sqlUtil = new SqlUtil(connection);
            TestEntity whereCondition = new TestEntity();
            whereCondition.setName("smith");
            ResultSet resultSet = sqlUtil.table("test").where(whereCondition).field("*").select();
            int i=0;
            while (resultSet.next())
                i++;
            return i;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        } finally {
            DbHelper.close(connection);
            doDropSql();
        }
    }

    private void doDropSql() {
        Connection connection = null;
        try {
            connection = DbHelper.get();
            SqlUtil sqlUtil = new SqlUtil(connection);
            sqlUtil.sql("TRUNCATE `test`");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbHelper.close(connection);
        }
    }

    private int doInsertTestData() {
        TestEntity data = new TestEntity();
        data.setName("smith");
        data.setDateline(10000L);
        Connection connection = null;
        try {
            connection = DbHelper.get();
            SqlUtil sqlUtil = new SqlUtil(connection);
            return sqlUtil.table(TABLE_NAME).insert(data);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            DbHelper.close(connection);
        }
    }
}
