package com.bjpowernode.springboot.web;

import com.bjpowernode.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @RequestMapping("/user/count")
    public String UserCount(){

        Integer userCount = userService.queryUserCount();



        return "学生总人数为："+userCount;
    }

}
