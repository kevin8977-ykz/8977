package com.bjpowenode.springboot.service.impl;

import com.bjpowenode.springboot.dao.UserDao;
import com.bjpowenode.springboot.domain.User;
import com.bjpowenode.springboot.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {


    @Resource
    private UserDao userDao;

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public User findById(String id) {
        return userDao.findById(id);
    }

    @Override
    public Object updateById(User user) {
         userDao.updateById(user);
        return  user;

    }

    @Override
    public void save(User user) {
        user.setId("111");
        userDao.save(user);
    }

    @Override
    //如果添加了@Transactional注解，没有控制住事务，需要在引导类上添加一个开启事务控制的注解
    @Transactional
    public void deleteById(String id) {
        userDao.deleteById(id);

        int i = 1/0;
    }
}
