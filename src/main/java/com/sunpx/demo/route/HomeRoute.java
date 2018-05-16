package com.sunpx.demo.route;

import com.jfinal.config.Routes;
import com.sunpx.demo.controller.HomeController;

/**
 * Created by Administrator on 2018/5/7 0007.
 */
public class HomeRoute extends Routes{

    @Override
    public void config() {
        //根目录下方法映射
        super.add("/",HomeController.class);
    }
}
