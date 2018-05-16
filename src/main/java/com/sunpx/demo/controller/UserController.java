package com.sunpx.demo.controller;

import com.google.common.collect.Lists;
import com.jfinal.core.Controller;
import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;
import com.sunpx.demo.entity.UserInfoEntity;
import com.sunpx.demo.services.UserInfoService;
import com.sunpx.demo.webkeys.KeysUtil;
import org.springframework.util.StringUtils;

import java.util.List;

    /**
     * Created by Administrator on 2018/5/15 0015.
     */
    public class UserController extends Controller {


        public void getUserList(){

            List<UserInfoEntity> userList = Lists.newArrayList();

            Cache cache = Redis.use(KeysUtil.USER_LIST_NAME);

            if(cache.exists("userList")){
                System.out.println("into cache data.");
                userList = cache.get("userList");
            }else{
                System.out.println("into database data.");
                userList = UserInfoService.userService.getUserInfoList();

                //更新缓存数据
                cache.set("userList" , userList);

            };

            setAttr("userList" , userList);

        }


        public void updateUserByUserName(){
            String username = getPara("username");
            String password = getPara("password");
            if(!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)){
                UserInfoEntity userInfoEntity = UserInfoService.userService.getUserInfoByUserName(username);
                userInfoEntity.set("password" , password);
                UserInfoService.userDao.update();


                //同步缓存数据
                //Redis.use(KeysUtil.USER_LIST_NAME).

            }
        }


}
