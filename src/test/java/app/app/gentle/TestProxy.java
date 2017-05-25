package app.gentle;

import com.gentle.proxy.AuthProxy;
import com.gentle.util.Util;

import java.util.Map;

/**
 * Created by Smith on 2017/5/25.
 */
public class TestProxy extends AuthProxy {
    @Override
    public void before() {
        System.out.println("before");
    }

    @Override
    public boolean authenticate() {
        try {
            Map<String, String> map = Util.getParameters();
            String user = map.get("user");
            if (user.equals("admin"))
                return true;
            else
                return false;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void after() {
        System.out.println("after");
    }
}
