package com.bjpowernode.springboot.web;

import com.bjpowernode.springboot.mapper.UserMapper;
import com.bjpowernode.springboot.model.User;
import com.bjpowernode.springboot.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    //查询所有
    @GetMapping
    public List<User> findAll(){
        return userService.findAll();
    }


    //条件查询
    @RequestMapping("/example")
    public List<User>  findByExample (User user){
        return userService.findByExample(user);
    }


    //根据主键查询
    @GetMapping("/{userId}")
    public User findById(@PathVariable("userId") String id){
        return userService.findById(id);
    }


    //根据主键查询
    @GetMapping("/count")
    public Long findCount(){
        return userService.findAllCount();
    }

    //根据主键查询
    @GetMapping("/count/{userId}")
    public Long findCountById(@PathVariable("userId") String id){
        return userService.findCountById(id);
    }


    //新增、修改、删除、选择性新增、修改、删除
    @RequestMapping("/inset")
    public User insert (User user){
        return userService.insert(user);
    }


    @RequestMapping("/insertSelective")
    public User  insertSelective(User user){
        return userService.insertSelective(user);
    }


    @RequestMapping("/update")
    public User update (User user){
        return userService.update(user);
    }

    @RequestMapping("/updateSelective")
    public User updateSelective (User user){
        return userService.updateSelective(user);
    }

    @DeleteMapping("/{userId}")
    public User delete (@PathVariable("userId") String id){
        return userService.delete(id);
    }

    @DeleteMapping("/example/{name}")
    public User deleteSelective (@PathVariable("name") String name){
        return userService.deleteSelective(name);
    }





}
