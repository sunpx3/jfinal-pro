package com.sunpx.test;

import com.google.common.base.Preconditions;
import com.jfinal.plugin.activerecord.Table;


import java.util.HashSet;
import java.util.Objects;

/**
 * Created by Administrator on 2018/5/8 0008.
 */
public class MainTest {
    public static void main(String[] args) {

        //System.out.println("19839096-8ece-4692-97fc-6c3fcd186551".replace("-",""));


        try {
            String str = "ssss";
            String s = Preconditions.checkNotNull(str , "参数值为NUll,请重新提交!");
            System.out.println(s);

            int hashNums = Objects.hash(s);

            System.out.println(hashNums);


            //Table


        }catch (NullPointerException e){
            System.out.println(e.getMessage());
        }




    }
}
