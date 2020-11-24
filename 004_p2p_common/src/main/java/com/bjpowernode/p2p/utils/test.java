package com.bjpowernode.p2p.utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class test {

    public static void fun1() throws Exception, IOException {
        RandomCode verifyCode = new RandomCode();
        verifyCode.output(verifyCode.getImage(), new FileOutputStream("test.jpg"));
        String str = verifyCode.getText();
        System.out.println(str);
    }

    public static void main(String[] args) throws Exception {
        fun1();
    }
}
