package com.bjpowernode.springboot.exception;

public class LoginErrorException extends Exception {
    public LoginErrorException(String message) {
        super(message);
    }
}
