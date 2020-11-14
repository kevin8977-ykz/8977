package com.bjpowernode.springboot.config;

import com.bjpowernode.springboot.interceptor.MyInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class MyConfig implements WebMvcConfigurer {

    //添加自定义的springmvc的拦截器

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
            //添加注册的拦截器
            registry.addInterceptor(new MyInterceptor())
                    .addPathPatterns("/**")
            //放行登录的请求
            .excludePathPatterns("/user/login");
    }
}
