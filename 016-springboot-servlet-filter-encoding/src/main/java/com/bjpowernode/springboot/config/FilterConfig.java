package com.bjpowernode.springboot.config;

import com.bjpowernode.springboot.filter.MyFilter;
import com.bjpowernode.springboot.filter.MyFilter2;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration //定义为配置类
public class FilterConfig {

    @Bean
    public FilterRegistrationBean myFilterRegistration() {
        //注册过滤器
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new MyFilter2());
        //添加过滤路径
        filterRegistrationBean.addUrlPatterns("/springboot/*","/user/*");

        return filterRegistrationBean;
    }
}

