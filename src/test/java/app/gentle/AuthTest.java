package gentle;

import com.gentle.annotation.*;
import com.gentle.bean.Data;
import com.gentle.bean.RequestMethod;
import com.gentle.bean.ResponseStatus;

/**
 * Created by Smith on 2017/5/25.
 */
@Controller
@Auth(authProxy = TestProxy.class)
public class AuthTest {

    @Action(method = RequestMethod.GET, path = "/do_auth")
    public Data doAuth() {
        Data data = new Data();
        data.setStatus(ResponseStatus.SUCCESS);
        return data;
    }
}
