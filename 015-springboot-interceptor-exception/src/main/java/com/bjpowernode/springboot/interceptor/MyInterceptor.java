package com.bjpowernode.springboot.interceptor;

import com.bjpowernode.springboot.domain.User;
import com.bjpowernode.springboot.exception.LoginErrorException;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器实现步骤：
 *      1.自定义一个类实现HandlerInterceptor接口
 *      2.重写3个方法
 *          preHandle
 *              在控制器方法调用之前执行
 *          postHandle
 *              在控制器方法调用之后执行
 *          afterCompletion
 *              页面加载完成后执行
 */

public class MyInterceptor  implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        User user = (User) request.getSession().getAttribute("user");

        if(user == null){

            System.out.println("用户未登录！");
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write("用户未登录！");
            throw new LoginErrorException("登录失败");
        }
        System.out.println("用户已登录，允许访问");

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }
}
