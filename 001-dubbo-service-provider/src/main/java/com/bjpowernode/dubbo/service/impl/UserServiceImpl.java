package com.bjpowernode.dubbo.service.impl;

import com.bjpowernode.dubbo.service.UserService;

public class UserServiceImpl implements UserService {

    @Override
    public Object findAll() {

        return "dubbo rpc service execute ...";
    }
}
