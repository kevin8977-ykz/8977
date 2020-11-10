package com.bjpowenode.springboot.service;

import com.bjpowenode.springboot.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


public interface UserService {

    List<User> findAll();

    User findById(String id);

    Object updateById(User user);

    void save(User user);

    void deleteById(String id);
}
