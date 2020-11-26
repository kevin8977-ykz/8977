package com.bjpowernode.p2p.interceptor;

import com.bjpowernode.p2p.model.user.User;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 实现拦截器操作
 *  实现一个接口
 *  重写3个方法
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    /**
     * 在控制器执行之前执行
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("拦截器执行了");

        //判断用户是否登录
        HttpSession session = request.getSession();
        //获取登录的用户对象
        User user = (User) session.getAttribute("user");

        //获取请求url
        String requestURI = request.getRequestURI();


        if (ObjectUtils.isEmpty(user)){
            //要放行的请求;
            //登录页面  首页  注册页面
            if(StringUtils.contains(requestURI,"login")){
                return true;
            }else if (StringUtils.contains(requestURI,"realName")){
                //实名注册页面,如果该用户未登录跳转
                throw new Exception("请先去注册,登录,再进行实名制操作!");
            }else if (StringUtils.contains(requestURI,"/")){
                System.out.println("这拦截器估计没用了,都是/下的...... ");
                return true;
            }else if (StringUtils.contains(requestURI,"register")){
                return true;
            }

            System.out.println("用户未登录,跳转到登录页面");
            //未登录
            return false;
        }

        return true;
    }

    /**
     * 在控制器执行之后执行
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 在页面加载完成后执行
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
