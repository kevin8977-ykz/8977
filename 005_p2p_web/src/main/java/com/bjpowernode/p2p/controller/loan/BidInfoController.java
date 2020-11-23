package com.bjpowernode.p2p.controller.loan;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bjpowernode.p2p.entity.BaseResult;
import com.bjpowernode.p2p.model.user.User;
import com.bjpowernode.p2p.service.loan.BidInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Controller
@RequestMapping("/loan")
public class BidInfoController {

    @Reference(interfaceClass = BidInfoService.class,version = "1.0.0",timeout = 2000,check = false)
    private BidInfoService bidInfoService;


    @RequestMapping("/invest")
    @ResponseBody
    public BaseResult invest(HttpSession session,Integer loanId, Double bidMoney){

        //生成投资记录
        //产品表中剩余可投资金额减去投资金额
        //账户可用余额减去投资金额
        //处理投资排行榜'
        User user = (User) session.getAttribute("user");
      /*  ExecutorService executorService = Executors.newFixedThreadPool(1000);
        for (int i = 0; i < 1000; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    bidInfoService.invest(loanId,bidMoney,user.getId());

                }
            });
        }*/
        bidInfoService.invest(loanId,bidMoney,user.getId());


        return BaseResult.success();
    }


}
