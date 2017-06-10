package gentle;

import com.alibaba.fastjson.JSON;
import com.gentle.helper.DbHelper;
import com.gentle.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

/**
 * Created by Smith on 2017/6/3.
 */
public class TestDatabase {
    public static void main(String[] args) {
        try {
            Connection connection = DbHelper.getConnection();
            String sql1 = "SELECT * FROM `data`";
            PreparedStatement ps = connection.prepareStatement(sql1);
            ResultSet rs = ps.executeQuery();
            List<TestEntity> entities = Util.resultSetConvertToEntityList(rs, TestEntity.class);
            System.out.println(JSON.toJSONString(entities));
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        select();
    }

    public static void select() {
        try {
            Connection connection = DbHelper.getConnection();
            String sql2 = "SELECT * FROM `data` WHERE `id`=1";
            PreparedStatement ps = connection.prepareStatement(sql2);
            ResultSet rs = ps.executeQuery();
            TestEntity entity = Util.resultSetConvertToEntity(rs, TestEntity.class);
            System.out.println(JSON.toJSONString(entity));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
