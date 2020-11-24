package com.bjpowernode.p2p.timer;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *  cron表达式的使用
 *      *
 *      ?
 *      /
 *      -
 *      ,
 *      每个符号代表着不同的作用
 */
@Component
public class TestTimer {


    /**
     * 定时任务调用的方法，都是没有返回值没有参数的
     *      需要在引导类中添加一个注解，开启定时任务
     */
//    @Scheduled(cron = "* * * * * ?")
    public void demo1(){
        System.out.println("每秒钟触发一次");
    }

//    @Scheduled(cron = "1,3,5,7,9 * * * * ?")
    public void demo2(){
        System.out.println("逗号的使用方式，每次触发时机");
    }

    /**
     * 0秒钟到5秒钟各触发一次
     */
//    @Scheduled(cron = "0-5 * * * * ?")
    public void demo3(){
        System.out.println("横杠的使用方式");
    }

    /**
     * 0秒钟触发，每个5秒触发一次
     */
//    @Scheduled(cron = "0/5 * * * * ?")
    public void demo4(){
        System.out.println("斜杠的使用方式");
    }

    /**
     * 凌晨两点触发一次
     */
//    @Scheduled(cron = "0 0 2 * * ?")
    public void demo5(){
        System.out.println("凌晨两点触发一次");
    }


}
