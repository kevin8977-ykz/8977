package com.bjpowernode.dubbo.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bjpowernode.dubbo.domain.User;
import com.bjpowernode.dubbo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class UserController {

    //使用xml方式进行服务的消费/订阅
    //<dubbo:reference check="false" id="userService" version="0.0.1" interface="com.bjpowernode.dubbo.service.UserService"/>
    //通过dubbo:reference标签，根据动态代理，将接口创建实现类对象，交给spring容器管理
    //@Autowired注解，通过类型从容器获取接口对象
    //@Autowired
    //private UserService userService;

    //使用注解方式进行服务的消费/订阅
    //属性配置和xml的方式一样
    @Reference(interfaceClass = UserService.class,version = "1.0.0",timeout = 2000,check = false)
    private UserService userService;


    @RequestMapping("/findAll")
    @ResponseBody
    public List<User> findAll(){

        return userService.findAll();
    }
}
