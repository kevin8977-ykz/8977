package com.bjpowernode.springboot.config;

import com.bjpowernode.springboot.UserInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//用于定义配置类，可替换 xml 文件；定义一个拦截器，相当于之前的 mvc 里的配置
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//定义需要拦截的路径
        String[] addPathPatterns = {
                "/user/**",
        };
        //定义不需要拦截的路径
        String[] excludePathPatterns = {
                "/user/error",
                "/user/verifyRealName"
        };
        registry.addInterceptor(new UserInterceptor()) //添加要注册的拦截 器对象
                .addPathPatterns(addPathPatterns) //添加需要拦截的路径
                .excludePathPatterns(excludePathPatterns); //添加不需要拦截的路径
    }
}
