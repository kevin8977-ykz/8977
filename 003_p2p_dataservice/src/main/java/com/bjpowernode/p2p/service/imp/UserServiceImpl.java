package com.bjpowernode.p2p.service.imp;

import com.alibaba.dubbo.config.annotation.Service;
import com.bjpowernode.p2p.model.service.UserService;
import org.springframework.stereotype.Component;

@Component
@Service(interfaceClass = UserService.class,version = "1.0.0",timeout = 2000)
public class UserServiceImpl implements UserService {



    public String findById(){
        System.out.println("findById");
        return "findById";
    }

}
