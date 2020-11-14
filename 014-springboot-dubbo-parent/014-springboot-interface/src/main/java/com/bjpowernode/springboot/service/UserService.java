package com.bjpowernode.springboot.service;

import com.bjpowernode.springboot.domain.User;

import java.util.List;


public interface UserService {

     List<User> findAll();
}
