package com.bjpowernode.springboot.service;

import org.apache.catalina.User;

import java.util.List;

public interface UserService {

    List<User> findAll();
}
