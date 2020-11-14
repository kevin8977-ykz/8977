package com.bjpowernode.springboot.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.bjpowernode.springboot.model.User;
import com.bjpowernode.springboot.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Reference(interfaceClass = UserService.class,version = "1.0.0",timeout = 2000,check = false)
    private UserService userService;


    @RequestMapping("/getAll")
    public String getAll(Model model){

        List<User> userAll = userService.getUserAll();

        model.addAttribute("user",userAll);


        return "user";

    }

}
