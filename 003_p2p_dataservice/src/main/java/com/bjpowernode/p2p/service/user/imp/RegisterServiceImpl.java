package com.bjpowernode.p2p.service.user.imp;

import com.alibaba.dubbo.config.annotation.Service;
import com.bjpowernode.p2p.service.user.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Service(interfaceClass = RegisterService.class,version = "1.0.0",timeout = 2000)
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private RedisTemplate redisTemplate;

    public  void saveMessageCodeInRedis(String phone ,Integer messageCode){

        //key:phone
        //value:messageCode
        //set方法：参数1，value 参数2，超时时间，参数3 超时单位
            redisTemplate.boundValueOps(phone).set(messageCode+"",300, TimeUnit.SECONDS);

    }

    public String getMessageCodeInRedis(String phone){

        return  (String) redisTemplate.boundValueOps(phone).get();
    }
}
