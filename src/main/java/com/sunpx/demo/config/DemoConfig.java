package com.sunpx.demo.config;

import com.google.common.collect.Lists;
import com.jfinal.config.*;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.SqlReporter;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;
import com.jfinal.plugin.redis.RedisPlugin;
import com.jfinal.template.Engine;
import com.sunpx.demo.entity.UserInfoEntity;
import com.sunpx.demo.route.AdminRoute;
import com.sunpx.demo.route.HomeRoute;
import com.sunpx.demo.route.LoginRoute;
import com.sunpx.demo.route.UserRoute;
import com.sunpx.demo.webkeys.KeysUtil;
import com.sunpx.interceptor.AuthInterception;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/5/7 0007.
 */
public class DemoConfig extends JFinalConfig{


    @Override
    public void configConstant(Constants me) {
        me.setDevMode(true);
        // 开启日志
        SqlReporter.setLog(true);
    }

    @Override
    public void configRoute(Routes me) {
        me.add(new HomeRoute());
        me.add(new LoginRoute());
        me.add(new AdminRoute());
        me.add(new UserRoute());
    }

    @Override
    public void configEngine(Engine engine) {

    }

    @Override
    public void configPlugin(Plugins me) {

        //配制db数据库
        DruidPlugin dp = new DruidPlugin("jdbc:mysql://localhost/jfinal-pro", "root", "root");
        me.add(dp);

        //配置redis缓存
        // 配置缓存初始化数据
        RedisPlugin bbsRedis = new RedisPlugin(KeysUtil.USER_LIST_NAME, "localhost");
        me.add(bbsRedis);


        ActiveRecordPlugin arp = new ActiveRecordPlugin(dp);
        //建立Entity与数据库表映射关系
        arp.addMapping("user_info" , UserInfoEntity.class);
        me.add(arp);



    }

    @Override
    public void configInterceptor(Interceptors me) {
        me.add(new AuthInterception());
    }

    @Override
    public void configHandler(Handlers handlers) {

    }

    // 系统启动完成后回调
    public void afterJFinalStart() {


    }


}

