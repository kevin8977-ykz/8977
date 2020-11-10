package com.bjpowernode.srping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 在引导类下创建控制层(注解)、服务层（注解）、持久层（注解）
 *      必须在引导类同级或者子包中进行创建
 *      当前引导类包含扫描，扫描的就是同级包及子包
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
