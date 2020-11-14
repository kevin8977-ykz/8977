package com.bjpowernode.springboot.handler;

import com.bjpowernode.springboot.exception.LoginErrorException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MyHandlerException {

    @ExceptionHandler(LoginErrorException.class)
    public Object loginError(Exception e){

        e.printStackTrace();

        System.out.println("LoginErrorException异常处理器进行处理");

        return "登录失败";

    }

    @ExceptionHandler(Exception.class)
    public Object Exception(Exception e){

        e.printStackTrace();

        System.out.println("Exception异常处理器进行处理");

        return "登录失败";

    }
}
