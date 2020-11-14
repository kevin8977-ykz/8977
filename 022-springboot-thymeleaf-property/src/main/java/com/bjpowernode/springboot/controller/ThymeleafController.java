package com.bjpowernode.springboot.controller;

import com.bjpowernode.springboot.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class ThymeleafController {


    @RequestMapping("/index")
    public String index(Model model){
        model.addAttribute("data","Hello,SpringBoot 集成 Thymeleaf模版");

        User user = new User();
        user.setId(10001);
        user.setName("王五");
        user.setPhone("13888880000");
        user.setAddress("天津市");
        model.addAttribute("user",user);
        model.addAttribute("hello","Hello");

        return "index";
    }



    @RequestMapping("/if-unless")
    public String toBase(Model model, HttpSession session){

        User user = new User();

        user.setId(7777);
        user.setName("坤坤");
        user.setPhone("1353489787");
        user.setAddress("浙江省温州市");

        User user1 = new User();
        user1.setId(6666);
        user1.setName("蔡蔡");
        user1.setPhone("123456789");
        user1.setAddress("浙江省温州市111");

        session.setAttribute("user1",user1);
        model.addAttribute("user",user);
        return "base";
    }
}
