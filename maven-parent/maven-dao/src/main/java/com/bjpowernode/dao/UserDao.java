package com.bjpowernode.dao;

import com.bjpowernode.domain.User;

import java.util.List;

public interface UserDao {

    List<User> findAll();
}
