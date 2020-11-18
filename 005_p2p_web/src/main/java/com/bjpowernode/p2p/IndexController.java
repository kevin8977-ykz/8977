package com.bjpowernode.p2p;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bjpowernode.p2p.model.loan.LoanInfo;
import com.bjpowernode.p2p.service.loan.LoanInfoService;
import com.bjpowernode.p2p.service.user.UserService;
import com.bjpowernode.p2p.service.loan.BidInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Controller
public class IndexController {

    @Reference(interfaceClass = UserService.class,version = "1.0.0",check = false)
    private UserService userService;

    @Reference(interfaceClass = BidInfoService.class,version = "1.0.0",check = false)
    private BidInfoService bidInfoService;

    @Reference(interfaceClass = LoanInfoService.class,version = "1.0.0",check = false)
    private LoanInfoService loanInfoService;

    @RequestMapping("/")
    public String toIndex(Model model){

        //命名规则：
        //非持久层:
        //查询：find/get
        //修改: modify/update/edit
        //新增: add/save
        //删除：remove/del

        //持久层：
        //查询：select
        //修改：update
        //新增：insert
        //删除：delete

        //模拟数据的并发操作
        //当前线程池设置100个线程数
       /* ExecutorService  service = Executors.newFixedThreadPool(100);

        for (int i = 0; i < 10000; i++) {
            service.submit(() -> {
               // long totalUserCount = userService.findTotalUserCount();
                //Double totalBidMoney = bidInfoService.findTotalBidMoney();
                Double historyAverageRate = loanInfoService.findHistoryAverageRate();
            });

        }*/
        //获取首页面所需要的数据
        //用户总人数 , u_user
        long totalUserCount = userService.findTotalUserCount();
        System.out.println("totalUserCount:"+totalUserCount);
        model.addAttribute("totalUserCount",totalUserCount);

        //投资总金额, b_bid_info
        Double totalBidMoney = bidInfoService.findTotalBidMoney();
        System.out.println("totalBidMoney:"+totalBidMoney);
        model.addAttribute("totalBidMoney",totalBidMoney);

        //历史年化收益率,b_loan_info
        Double historyAverageRate = loanInfoService.findHistoryAverageRate();
        System.out.println("historyAverageRate:"+historyAverageRate);
        model.addAttribute("historyAverageRate",historyAverageRate);

        //从redis中读取数据，但是什么时候去更新redis中的数据呢？
        //通过定时任务框架，凌晨两点钟，更新redis中的数据。


        //产品列表
        //查询 新手宝、优选、散标数据
        //新手宝默认显示1条数据
        //优选默认显示4条数据
        //散标默认显示8条数据
         int productType = 0;
         int pageNoIndex = 0;
         int pageSize = 1;
         List<LoanInfo> xloanInfoList = loanInfoService.findLoanInfoListByProductType(productType,pageNoIndex,pageSize);
        System.out.println("xloanInfoList:"+xloanInfoList);
         model.addAttribute("xloanInfoList",xloanInfoList);

         productType = 1;
         pageSize = 4;
         List<LoanInfo> yloanInfoList = loanInfoService.findLoanInfoListByProductType(productType,pageNoIndex,pageSize);
        System.out.println("yloanInfoList:"+yloanInfoList);
         model.addAttribute("yloanInfoList",yloanInfoList);

         productType = 2;
         pageSize = 8;
         List<LoanInfo> sloanInfoList = loanInfoService.findLoanInfoListByProductType(productType,pageNoIndex,pageSize);
        System.out.println("sloanInfoList:"+sloanInfoList);
        model.addAttribute("sloanInfoList",sloanInfoList);

        return "index";
    }


}
