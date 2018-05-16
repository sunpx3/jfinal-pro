package com.sunpx.demo.route;

import com.jfinal.config.Routes;
import com.sunpx.demo.controller.LoginController;

/**
 * Created by Administrator on 2018/5/7 0007.
 */
public class LoginRoute extends Routes {
    @Override
    public void config() {
        super.add("/login" , LoginController.class);
    }
}
