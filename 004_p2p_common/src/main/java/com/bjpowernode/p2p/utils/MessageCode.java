package com.bjpowernode.p2p.utils;

import org.apache.commons.lang3.RandomUtils;

public class MessageCode {

    public static Integer generateMessageCode(){
       return RandomUtils.nextInt(100000,999999);
    }



}
