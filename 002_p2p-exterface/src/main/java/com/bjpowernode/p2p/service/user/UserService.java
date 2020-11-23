package com.bjpowernode.p2p.service.user;

import com.bjpowernode.p2p.model.user.User;

public interface UserService {

    String findById();

    //查询用户总记录数
    long findTotalUserCount();


    //查询用户手机号码是否注册
    boolean checkPhoneNumRegisted(String phone);

    User register(String phone, String loginPassword);


    User updateRegisteData(Integer id, String realName, String idCard);

    /**
     * 根据手机号和密码查询用户
     * @param phone
     * @param loginPassword
     * @return
     */
    User login(String phone, String loginPassword);
}
