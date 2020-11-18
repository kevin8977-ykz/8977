package com.bjpowernode.p2p.service.user;

import com.bjpowernode.p2p.model.user.User;

public interface UserService {

    String findById();

    //查询用户总记录数
    long findTotalUserCount();


    //查询用户手机号码是否注册
    boolean checkPhoneNumRegisted(String phone);

    User register(String phone, String loginPassword);


}
