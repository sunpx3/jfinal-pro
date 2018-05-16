package com.sunpx;

import com.jfinal.core.JFinal;

/**
 * Created by Administrator on 2018/5/7 0007.
 */
public class RunnerStart {
    public static void main(String[] args) {

        JFinal.start("src/main/webapp", 80, "/");

    }
}
