package com.bjpowernode.springboot.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.bjpowernode.springboot.mapper.UserMapper;
import com.bjpowernode.springboot.model.User;
import com.bjpowernode.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;


import java.util.List;


@Component
@Service(interfaceClass = UserService.class,version = "1.0.0",timeout = 2000)
public class UserServiceImpl implements UserService {


    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> getUserAll() {

        //首先先从redis中查询
        List<User> allUsers = redisTemplate.boundListOps("allUsers").range(0, -1);

        //redis中获取数据的时候很可能不是null，很可能是"[]"
        //如果获取string的时候"[]","{}"
        System.out.println(allUsers);

        if(allUsers == null || allUsers.size() <= 0){
            //证明没有数据，则去mysql中查询
            //根据条件查询，example 传入一个null，mapper映射配置文件中 根据 select * from tbl_user
            // * 通过sql标签引入，所以数据库中user表的字段信息
            allUsers = userMapper.getUserAll();
            //查询完成后，将数据存入到redis中
            redisTemplate.boundListOps("allUsers").leftPush(allUsers);
            System.out.println("从数据库中获取userList数据");
            return allUsers;
        }

        System.out.println("从Redis中获取userList数据");
        return allUsers;
    }
}
