package com.bjpowenode.springboot.controller;


import com.bjpowenode.springboot.domain.User;
import com.bjpowenode.springboot.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/user")
public class RestFulController {


    @Resource
    private UserService userService;


    @GetMapping("/{userId}")
    public User findById(@PathVariable(name = "userId") String id){
        return userService.findById(id);
    }


    @PutMapping("/{userId}")
    public Object putById(@PathVariable(name = "userId") String userId,String name,String email ){
        User user = userService.findById(userId);
        user.setName(name);
        user.setEmail(email);
        return userService.updateById(user);
    }


    @PostMapping
    public User postById( User user){
         userService.save(user);
         return user;
    }



    @DeleteMapping("/{userId}")
    public Object deleteById(@PathVariable(name = "userId") String id){
         userService.deleteById(id);
        System.out.println(1/0);
        return  "删除成功";
    }

}
