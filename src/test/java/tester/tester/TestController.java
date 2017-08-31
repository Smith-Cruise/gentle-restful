package tester;

import org.inlighting.gentle.annotation.Action;
import org.inlighting.gentle.annotation.Controller;
import org.inlighting.gentle.annotation.Param;
import org.inlighting.gentle.bean.Data;
import org.inlighting.gentle.bean.RequestMethod;
import org.inlighting.gentle.bean.ResponseStatus;

@Controller
public class TestController {

    @Action(method = RequestMethod.GET, path = "/success")
    public Data test() {
        Data data = new Data();
        data.setStatus(ResponseStatus.SUCCESS);
        return data;
    }

    @Action(method = RequestMethod.GET, path = "/param")
    public Data testParam(
            @Param(name = "a") String a,
            @Param("b") String b,
            @Param(name = "c", required = false) String c,
            @Param(name = "d") Integer d) {
        Data data = new Data();
        data.setStatus(ResponseStatus.SUCCESS);
        data.setData(a+";"+b+";"+c+";"+d);
        return data;
    }
}
