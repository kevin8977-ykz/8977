package com.bjpowernode.p2p.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bjpowernode.p2p.entity.BaseResult;
import com.bjpowernode.p2p.model.user.User;
import com.bjpowernode.p2p.service.loan.BidInfoService;
import com.bjpowernode.p2p.service.loan.LoanInfoService;
import com.bjpowernode.p2p.service.user.RegisterService;
import com.bjpowernode.p2p.service.user.UserService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/loan")
public class LoginController {

    @Reference(interfaceClass = LoanInfoService.class,version = "1.0.0",check = false)
    private  LoanInfoService loanInfoService;

    @Reference(interfaceClass = BidInfoService.class,version = "1.0.0",check = false)
    private BidInfoService bidInfoService;

    @Reference(interfaceClass = UserService.class,version = "1.0.0",check = false)
    private UserService userService;

    @Reference(interfaceClass = RegisterService.class,version = "1.0.0",check = false)
    private RegisterService registerService;

    @RequestMapping("/login")
    @ResponseBody
    public BaseResult login(@RequestParam(required = true,name = "phone") String phone,
                            @RequestParam(required = true,name = "loginPassword") String loginPassword,
                            @RequestParam(required = true,name = "messageCode")String messageCode,
                            HttpSession session){
        //1.校验验证码的合法性
        String messageCodeInRedis = registerService.getMessageCodeInRedis(phone);

        //2.校验失败，返回错误的提示信息
        if(!StringUtils.equals(messageCode,messageCodeInRedis)){
            return BaseResult.error("验证码不匹配,请重新输入！");
        }
        //3.校验通过，进行登录操作
        //4.使用手机号码和密码，匹配数据库中的信息
        User user = userService.login(phone,loginPassword);

        if(ObjectUtils.isEmpty(user)){
            //用户名密码查询失败
            //5.登录失败，返回错误的提示信息
            return BaseResult.error("用户名或密码错误，请重新输入!");
        }
        System.out.println(user);
        //6.将用户存入到session中
        session.setAttribute("user",user);

        //7.登录成功，返回BaseResult.success
        return BaseResult.success();


    }



    @RequestMapping("/page/login")
    public String toLogin(Model model, String returnUrl){

        //将returnUrl，返回到login.html中
        model.addAttribute("returnUrl",returnUrl);

        //获取历史平均年化收益率 b_loan_info
        Double historyAverageRate = loanInfoService.findHistoryAverageRate();
        model.addAttribute("historyAverageRate",historyAverageRate);

        //获取平台注册总人数 u_user
        long totalUserCount = userService.findTotalUserCount();
        model.addAttribute("totalUserCount",totalUserCount);

        //获取累计收益总金额 b_bid_info
        Double totalBidMoney = bidInfoService.findTotalBidMoney();
        model.addAttribute("totalBidMoney",totalBidMoney);



        return "login";
    }


    @RequestMapping("/logout")
    public String logout(HttpSession session){

        //退出操作
        //销毁session
        //session.invalidate();
        session.removeAttribute("user");

        return "redirect:/";
    }


}
