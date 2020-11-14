package com.bjpowernode.springboot.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.bjpowernode.springboot.domain.User;
import com.bjpowernode.springboot.service.UserService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
@Service(interfaceClass = UserService.class,version = "1.0.0",timeout = 2000)
public class UserServiceImpl implements UserService {

    @Override
    public List<User> findAll() {

        List<User> list = new ArrayList<>();

        list.add(new User("lb","123"));
        list.add(new User("zjl","456"));
        list.add(new User("cyx","789"));
        list.add(new User("zbh","457"));
        list.add(new User("zgr","785"));

        return list;
    }
}
