package com.bjpowernode.springboot.controller;

import com.alibaba.dubbo.config.annotation.Reference;

import com.bjpowernode.springboot.domain.User;
import com.bjpowernode.springboot.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {


    @Reference(interfaceClass = UserService.class,version = "1.0.0",timeout = 2000,check = false)
    private UserService userService;

    @RequestMapping("/findAll")
    public List<User> findAll(){
       return userService.findAll();
    }

}
