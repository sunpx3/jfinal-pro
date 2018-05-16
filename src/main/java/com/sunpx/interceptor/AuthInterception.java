package com.sunpx.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.sunpx.demo.entity.UserInfoEntity;


/**
 * auth login interception
 * Created by Administrator on 2018/5/7 0007.
 */
public class AuthInterception implements Interceptor {

    @Override
    public void intercept(Invocation inv) {

        Controller c = inv.getController();
        UserInfoEntity userInfoEntity = (UserInfoEntity) c.getSession().getAttribute("user");

        try{
            if(inv.getControllerKey().contains("/login")) {
                inv.invoke();
            }else if(inv.getControllerKey().contains("/api")){
                inv.invoke();
            }
            else {
                if (userInfoEntity == null){
                    c.renderHtml("<script>alert('未登录');location.href='/login.html'</script>");
                }else {
                    inv.invoke();
                }
            }
        }catch(Exception e) {
            e.printStackTrace();
            inv.invoke();
        }


    }
}
