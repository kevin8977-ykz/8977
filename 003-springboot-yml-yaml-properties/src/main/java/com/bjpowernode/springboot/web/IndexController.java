package com.bjpowernode.springboot.web;

import com.bjpowernode.springboot.config.CustomerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
public class IndexController {

    //获取基本类型+string的属性配置
    // 必须要使用${表达式}引用
    @Value("${name}")
    private String username;

    @Value("${age}")
    private Integer age;

    @Value("${address}")
    private String address;

    @Value("${sz2005.class.name}")
    private  String uname;


    @RequestMapping("/get1")
    @ResponseBody
    public String getProperties(){
        return  username + " " + age + " " + address + " " + uname;
    }


    @Value("${uname}")
    private String name;

    @Value("${age}")
    private Integer age2;

    @Value("${hobby}")
    private String hobby;

    @RequestMapping("/get2")
    @ResponseBody
    public String getYaml(){
        return  name + " " + hobby + " " + age2 + " " ;
    }

//    @Value("${sz2005.uname}")
//    private Map<String,Object> sz2005;

    @Resource
    private CustomerConfig customerConfig;


    @RequestMapping("/get3")
    @ResponseBody
    public Object get3(){
        return customerConfig;
    }


}
