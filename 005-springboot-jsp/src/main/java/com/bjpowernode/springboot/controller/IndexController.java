package com.bjpowernode.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

    @RequestMapping("/index")
    public ModelAndView toindex(ModelAndView model){
        model.addObject("message","hello springboot");
        model.setViewName("index");
        // 如果返回index，会识别不了这个/index的url
        //因为springboot官方推荐使用的thymeleaf模板引擎
        //由于集成jsp：
        //1.导入解析jap的插件，pom.xml导入坐标
        //2.将当前jsp文件，编译后，指定到/META-INF/resources目录中
        //3.修改配置文件，配置springmvc的前缀和后缀

        return model;
    }
}
