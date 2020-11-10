package com.bjpowernode.springboot.service;

import com.bjpowernode.springboot.model.User;

import java.util.List;

public interface UserService {
    List<User> findAll();

    List<User> findByExample(User user);

    User findById(String id);

    Long findAllCount();

    Long findCountById(String id);

    User insert(User user);


    User insertSelective(User user);

    User update(User user);

    User updateSelective(User user);

    User delete(String id);


    User deleteSelective(String name);
}
