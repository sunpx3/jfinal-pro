package com.sunpx.demo.controller;

import com.jfinal.core.Controller;
import com.sunpx.demo.entity.UserInfoEntity;
import com.sunpx.demo.services.UserInfoService;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by Administrator on 2018/5/7 0007.
 */
public class LoginController extends Controller {

    public void toLogin(){
        if(isLogin()){
            redirect("/admin/toAdmin");
        }else{
            renderHtml("<script>alert('未登录');location.href='/login.html'</script>");
        }
    }

    public boolean isLogin(){
        UserInfoEntity userInfoEntity = (UserInfoEntity) getSession().getAttribute("user");
        if(userInfoEntity ==null){
            return false;
        }else{
           return true;
        }
    }

    public void login(){
        String username = getPara("username");
        String password = getPara("password");

        try {
            if(!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)){
                if(username.equals("admin") && password.equals("123456")){
                    UserInfoEntity userInfoEntity = new UserInfoEntity();
                    userInfoEntity.set("username",username);
                    userInfoEntity.set("password",password);
                    getSession().setAttribute("user" , userInfoEntity);
                    redirect("/admin/toAdmin");
                }else{
                    String msg = "帐号密码错误，请重试或联系管理员!";
                    msg = URLDecoder.decode(msg , "utf-8");
                    redirect("/login/toLogin?msg=" + msg,true);
                }
            }else{
                String msg = "帐号密码不能为空，请检查！";
                msg = URLDecoder.decode(msg , "utf-8");
                redirect("/login/toLogin?msg=" + msg,true);

            }


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

}
