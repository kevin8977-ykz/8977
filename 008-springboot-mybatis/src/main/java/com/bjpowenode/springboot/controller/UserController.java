package com.bjpowenode.springboot.controller;

import com.bjpowenode.springboot.domain.User;
import com.bjpowenode.springboot.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {


    @Resource
    private UserService userService;


    @RequestMapping("/getAll")
    public List<User> getAll(){
        return  userService.findAll();
    }



}
