package test;

import org.inlighting.gentle.annotation.Action;
import org.inlighting.gentle.annotation.Controller;
import org.inlighting.gentle.bean.Data;
import org.inlighting.gentle.bean.RequestMethod;
import org.inlighting.gentle.bean.ResponseStatus;

@Controller
public class MainController {

    @Action(method = RequestMethod.GET, path = "/success")
    public Data success() {
        Data data = new Data();
        data.setStatus(ResponseStatus.SUCCESS);
        return data;
    }
}
