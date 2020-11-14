package com.bjpowernode.springboot.service.impl;

import com.bjpowernode.springboot.dao.UserDao;
import com.bjpowernode.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserDao userDao;


    @Override
    public Integer queryUserCount() {
        return userDao.queryUserCount();
    }
}
