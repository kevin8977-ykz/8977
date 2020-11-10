package com.bjpowernode.springboot.service.impl;

import com.bjpowernode.springboot.service.UserService;
import org.apache.catalina.User;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {


    //RedisTemplate对象
    //spring封装的模板对象 JdbcTemplate
    @Resource
    private RedisTemplate redisTemplate;


    public List<User> findAll(){
        //redis操作的数据类型都是键值对
        //方法格式：
        //以bound或ops开头
        //bound类型ops
        //opsFor类型
        //redis的数据类型:List、Set、ZSet、Hash、String
        redisTemplate.boundValueOps("username").set("wangwu");

        System.out.println(redisTemplate.opsForValue().get("username").toString());

        return null;
    }

}
