package com.bjpowernode.springboot.controller;


import com.bjpowernode.springboot.domain.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/login")
    public Object login(User user , HttpSession session){

        String loginact = user.getLoginact();
        String loginpwd = user.getLoginpwd();

        if(loginact.equals("lb") && loginpwd.equals("123")){

            session.setAttribute("user",user);
            System.out.println("登录成功");
            return "登录成功";
        }
        return "登录失败";
    }

    @RequestMapping("/index")
    public Object index(){


        return "首页面";
    }

}
