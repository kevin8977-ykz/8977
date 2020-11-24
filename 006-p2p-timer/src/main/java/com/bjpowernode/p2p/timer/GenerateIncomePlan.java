package com.bjpowernode.p2p.timer;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bjpowernode.p2p.service.user.IncomeRecordService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class GenerateIncomePlan {

    @Reference(interfaceClass = IncomeRecordService.class,version = "1.0.0",timeout = 2000,check = false)
    private IncomeRecordService incomeRecordService;

    /**
     * 生成收益计划
     */
//    @Scheduled(cron = "0 0 2 * * ?")
//    @Scheduled(cron = "0/5 * * * * ?")
    public void generate(){
        System.out.println("生成收益计划开始");
        incomeRecordService.generateIncomeRecordPlan();
        System.out.println("生成收益计划结束");
    }

}
