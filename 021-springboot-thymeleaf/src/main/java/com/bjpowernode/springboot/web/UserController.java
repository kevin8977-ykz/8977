package com.bjpowernode.springboot.web;

import com.bjpowernode.springboot.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {


    @RequestMapping("/user/detail")
    public ModelAndView userDetail(){
        ModelAndView mv = new ModelAndView();
        User user = new User();
        user.setId(10086);
        user.setUsername("李白");
        user.setAge(17);

        mv.addObject("user",user);
        mv.setViewName("user");

        return mv;
    }

    @RequestMapping("/user/url")
    public ModelAndView userUrl(){
        ModelAndView mv = new ModelAndView();
        User user = new User();
        user.setId(1007);
        user.setUsername("周杰伦");
        user.setAge(27);

        mv.addObject("user",user);
        mv.setViewName("url");

        return mv;
    }
}
