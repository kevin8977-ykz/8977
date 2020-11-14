package com.bjpowernode.springboot.filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import java.io.IOException;

//@WebFilter(urlPatterns = "/springboot/myFilter2")
public class MyFilter2 extends HttpFilter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("---------------通过配置类注册过滤器-------------------");

        super.doFilter(request, response, chain);
    }
}
