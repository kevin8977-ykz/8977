package com.bjpowenode.springboot.dao;

import com.bjpowenode.springboot.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface UserDao {
    List<User> findAll();
    User findById(String id);

    void updateById(@Param("user") User user);

    void save(User user);

    void deleteById(String id);
}
