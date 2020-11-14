package com.bjpowernode.springboot.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ThymeleafController {


    @RequestMapping("/thymeleaf/index")
    public ModelAndView index(ModelAndView mv){

        mv.addObject("data","SpringBoot集成Thymeleaf模板！");
        mv.setViewName("index");
        return mv;
    }
}
