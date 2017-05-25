package app.gentle;

import com.gentle.helper.DbHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Smith on 2017/5/20.
 */
public class Main {
    public static void main(String[] args) {
        try {
            Class.forName("com.gentle.helper.DbHelper", true, Thread.currentThread().getContextClassLoader());
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i=0; i<1000; i++) {
            new Robot().start();
        }
    }


}
class Robot extends Thread {
    @Override
    public void run() {
        try {
            Connection connection = DbHelper.getConnection();
            String sql = "ISNERT INTO `sdb` (`thread`) VALUE (?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, Thread.currentThread().getName());
            System.out.println("success");
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}