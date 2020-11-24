package com.bjpowernode.p2p.timer;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bjpowernode.p2p.service.user.IncomeRecordService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class GenerateIncomePlanBack {


    @Reference(interfaceClass = IncomeRecordService.class,version = "1.0.0",timeout = 2000,check = false)
    private IncomeRecordService incomeRecordService;

    /**
     * 收益计划返还
     */
    @Scheduled(cron = "0/5 * * * * ?")
    public void incomePlanBack(){
        System.out.println("返还收益计划开始");
        incomeRecordService.incomePlanBack();
        System.out.println("返还收益计划结束");
    }

}
