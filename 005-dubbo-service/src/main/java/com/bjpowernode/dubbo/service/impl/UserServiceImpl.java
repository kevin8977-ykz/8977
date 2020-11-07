package com.bjpowernode.dubbo.service.impl;

import com.bjpowernode.dubbo.domain.User;
import com.bjpowernode.dubbo.service.UserService;

import java.util.ArrayList;
import java.util.List;



public class UserServiceImpl implements UserService {


    public List<User> findAll() {

        List<User> users = new ArrayList<User>();

        for (int i = 0; i < 10; i++) {
            User user = new User();

            user.setId(i +"");
            user.setName("刘备"+i);
            users.add(user);
        }
        return users;
    }
}
