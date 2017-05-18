package com.app;

import com.gentle.annotation.Action;
import com.gentle.annotation.Controller;
import com.gentle.bean.Data;
import com.gentle.bean.RequestMethod;
import com.gentle.bean.ResponseStatus;

/**
 * Created by Smith on 2017/5/18.
 */
@Controller
public class Test {
    @Action(method = RequestMethod.GET, path = "/success")
    public Data success() {
        return new Data(ResponseStatus.SUCCESS, "this is success request");
    }

    @Action(method = RequestMethod.GET, path = "/error")
    public Data error() {
        return new Data(ResponseStatus.ERROR, "this is error request");
    }

    @Action(method = RequestMethod.GET, path = "/unauthorized")
    public Data unauthorized() {
        return new Data(ResponseStatus.UNAUTHORIZED, "this is unauthorized request");
    }

    @Action(method = RequestMethod.GET, path = "/not_found")
    public Data notfound() {
        return new Data(ResponseStatus.NOTFOUND, "this is not found request");
    }
}
