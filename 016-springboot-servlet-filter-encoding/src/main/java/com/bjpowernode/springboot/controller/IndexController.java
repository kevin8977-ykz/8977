package com.bjpowernode.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Controller
public class IndexController {


    @RequestMapping("/abc")
    public String abc(Model model) {
        model.addAttribute("data","SpringBoot 框架打 jar 运行");
        return "abc";
    }

    @RequestMapping(value = "/abc/json")
    public  Object json() {

        Map<String,Object> paramMap = new HashMap<String, Object>();
        paramMap.put("code","10000");
        return paramMap;
    }
}



