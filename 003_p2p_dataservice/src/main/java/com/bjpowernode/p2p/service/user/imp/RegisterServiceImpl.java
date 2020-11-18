package com.bjpowernode.p2p.service.user.imp;

import com.alibaba.dubbo.config.annotation.Service;
import com.bjpowernode.p2p.service.user.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@Service(interfaceClass = RegisterService.class,version = "1.0.0",timeout = 2000)
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private RedisTemplate redisTemplate;

    public  void saveMessageCodeInRedis(String phone ,Integer messageCode){

            redisTemplate.boundValueOps(phone).set(messageCode+"");

    }

    public String getMessageCodeInRedis(String phone){

        return  (String) redisTemplate.boundValueOps(phone).get();
    }
}
