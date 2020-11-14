package com.bjpowernode.springboot.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @RequestMapping("/say")
    public Object say(){
        return "hello springboot multi-environment";
    }
}
