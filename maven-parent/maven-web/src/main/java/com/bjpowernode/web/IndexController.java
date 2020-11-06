package com.bjpowernode.web;

import com.bjpowernode.domain.User;
import com.bjpowernode.service.UserService;
import com.sun.deploy.net.HttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class IndexController {

    @Resource
    private UserService userService;

    @RequestMapping("/index")
    @ResponseBody
    public Object getIndex( ){
        List<User> userList = userService.findAll();

        return "index execute ...."+userList;
    }
}
