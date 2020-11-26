package com.bjpowernode.p2p.exeception;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class CustomHandlerException {

    @ExceptionHandler(Exception.class)
    public String exception1(Exception e,Model model){
        e.printStackTrace();

        System.out.println("捕获了Exception的异常");

        //提示信息
        if (StringUtils.isEmpty(e.getMessage())){
            model.addAttribute("trade_msg","网络异常,请稍后再试");
        }else {
            model.addAttribute("trade_msg",e.getMessage());
        }


        return "toError";
    }

}
