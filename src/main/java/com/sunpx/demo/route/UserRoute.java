package com.sunpx.demo.route;

import com.jfinal.config.Routes;
import com.sunpx.demo.controller.UserController;

/**
 * Created by Administrator on 2018/5/15 0015.
 */
public class UserRoute extends Routes {
    @Override
    public void config() {
        add("/user", UserController.class);
    }
}
