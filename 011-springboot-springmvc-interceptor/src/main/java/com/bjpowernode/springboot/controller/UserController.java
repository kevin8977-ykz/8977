package com.bjpowernode.springboot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UserController {

    @RequestMapping("/user/account")
    public  Object queryAccount(){

        return "账号可用余额：777元";
    }


    @RequestMapping(value = "/user/verifyRealName")
    public Object verifyRealName(HttpServletRequest request) {
        request.getSession().setAttribute("code",0);
        return "用户实名认证成功";
    }


    @RequestMapping(value = "/user/error")
    public Object error() {
        return "用户没有实名认证";
    }


}
