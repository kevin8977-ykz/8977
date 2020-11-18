package com.bjpowernode.p2p.service.user;

import org.springframework.stereotype.Component;

public interface RegisterService {

     void saveMessageCodeInRedis(String phone ,Integer messageCode);

    String getMessageCodeInRedis(String phone);
}
