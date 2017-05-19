package app;

import com.gentle.database.Db;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * Created by Smith on 2017/5/19.
 */
public class Mysql {
    public static void main(String[] args) {
        try {
            Class.forName("com.gentle.database.Db");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i=0; i<100; i++) {
            new Robot().start();
        }
    }
}

class Robot extends Thread {
    @Override
    public void run() {
        try {
            Connection connection = Db.getConnection();
            String sql = "INSERT INTO `sdb` (`thread`) VALUE (?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, Thread.currentThread().getName());
            ps.execute();
            //connection.close();
            Db.backConnection(connection);
            System.out.println("success");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
