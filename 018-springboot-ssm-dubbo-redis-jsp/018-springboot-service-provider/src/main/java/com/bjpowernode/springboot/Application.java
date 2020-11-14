package com.bjpowernode.springboot;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableDubboConfiguration
////mybatis的接口扫描
//@MapperScan("com.bjpowernode.springboot.ssm.mapper")
////开启事务控制
@EnableDubboConfiguration
@MapperScan(basePackages = "com.bjpowernode.springboot.mapper")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
