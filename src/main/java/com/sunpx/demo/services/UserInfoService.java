package com.sunpx.demo.services;

import com.sunpx.demo.entity.UserInfoEntity;

import java.util.List;

/**
 * Created by Administrator on 2018/5/8 0008.
 */
public class UserInfoService {

    public static final UserInfoService userService = new UserInfoService();

    public static final UserInfoEntity userDao = new UserInfoEntity().dao();


    public List<UserInfoEntity> getUserInfoList() {

        List<UserInfoEntity> userInfoList = userDao.find("select id,username,password from user_info");


        return userInfoList;
    }

    public UserInfoEntity getUserInfoByUserName(String username){
        UserInfoEntity userInfoEntity = userDao.findFirst("select id , username , password  from user_info");
        return userInfoEntity;
    }



}
