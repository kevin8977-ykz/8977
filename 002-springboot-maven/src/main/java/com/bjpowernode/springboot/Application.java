package com.bjpowernode.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 创建springboot 工程的引导类
 *      通过main方法来启动内嵌式的tomcat服务器
 *      这些过程都是固定的
 */
//该注解修饰的类就是一个Springboot的引导类
@SpringBootApplication
public class Application {


    public static void main(String[] args) {
        //参数1：引导类的字节码 参数2：main方法的args参数
        SpringApplication.run(Application.class,args);
    }
}
