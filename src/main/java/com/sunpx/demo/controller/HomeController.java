package com.sunpx.demo.controller;

import com.google.common.collect.Lists;
import com.jfinal.core.Controller;
import com.sunpx.demo.entity.UserInfoEntity;
import com.sunpx.demo.services.UserInfoService;

import java.util.List;

/**
 * Created by Administrator on 2018/5/7 0007.
 */
public class HomeController extends Controller {

    public void index(){
        //renderText("The msg is index method.");


        List<String> nameList  = Lists.newArrayList();
        nameList.add("张三");
        nameList.add("李四");
        nameList.add("王五");
        nameList.add("朱六");
        nameList.add("沈七");


        UserInfoEntity userInfoEntity = new UserInfoEntity();
        //userInfoEntity.setFirstName("绝");
       // userInfoEntity.setLastName("影");

        setAttr("AppName" , "Hello Sir ,The app name is aiguozhe.");
        setAttr("userInfoEntity" , userInfoEntity);
        setAttr("nameList" , nameList);
        setAttr("isChecked",true);

        UserInfoService userInfoService = UserInfoService.userService;
        List<UserInfoEntity> userInfoList = userInfoService.getUserInfoList();


        setAttr("userInfoList" , userInfoList);

        render("/index.html");
    }



    public void hello(){
        String firstName = getPara("firstName");
        String lastName = getPara("lastName");



        renderText(firstName + " " + lastName);
    }


}
