package com.bjpowernode.springboot.config;

import com.bjpowernode.springboot.servlet.MyServlet3;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;

@Configuration
public class SystemConfig {


    @Bean
    public ServletRegistrationBean myServletRegistration(){
        ServletRegistrationBean servletRegistrationBean =
                new ServletRegistrationBean(new MyServlet3(),"/springboot/myServlet");

        return servletRegistrationBean;

    }

    @Bean
    public FilterRegistrationBean characterFilterRegistration(){
        //设置字符编码过滤器
        //CharacterEncoding 是由 Spring 提供的一个字符编码过滤器，之前是配置在web.xml 文件中
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();

        //强制使用指定字符编码
        characterEncodingFilter.setForceEncoding(true);

        //设置指定字符编码
        characterEncodingFilter.setEncoding("UTF-8");

        //创建过滤器注册bean
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();

        //设置字符编码过滤器
        filterRegistrationBean.setFilter(characterEncodingFilter);

        //设置字符编码过滤器路径
        filterRegistrationBean.addUrlPatterns("/*");

        return filterRegistrationBean;

    }
}
