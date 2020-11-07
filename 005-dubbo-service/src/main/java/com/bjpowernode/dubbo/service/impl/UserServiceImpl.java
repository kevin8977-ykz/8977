package com.bjpowernode.dubbo.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.bjpowernode.dubbo.domain.User;
import com.bjpowernode.dubbo.mapper.UserMapper;
import com.bjpowernode.dubbo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


//使用xml方式注解服务到注册中心:
// <dubbo:service interface="com.bjpowernode.dubbo.service.UserService"
//                       version="0.0.1"
//                       ref="userService" />
//使用注解方式将服务注册到注册中心
//不仅能够通过@Service将服务注册到注册中心之外，还可以将对象交给spring容器进行管理
@Service(interfaceClass = UserService.class,version = "1.0.0",timeout = 2000)
public class UserServiceImpl implements UserService {


    @Autowired
    private UserMapper userMapper;

    public List<User> findAll() {

        return userMapper.findAll();
    }
}
