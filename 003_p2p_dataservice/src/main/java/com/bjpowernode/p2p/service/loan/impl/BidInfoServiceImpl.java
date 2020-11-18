package com.bjpowernode.p2p.service.loan.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.bjpowernode.p2p.mapper.loan.BidInfoMapper;
import com.bjpowernode.p2p.service.loan.BidInfoService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;


@Component
@Service(interfaceClass = BidInfoService.class,version = "1.0.0",timeout = 2000)
public class BidInfoServiceImpl implements BidInfoService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private BidInfoMapper bidInfoMapper;

    @Override
    public Double findTotalBidMoney() {

        Double totalBidMoney = (Double) redisTemplate.boundValueOps("totalBidMoney").get();

        if(ObjectUtils.isEmpty(totalBidMoney)){

            synchronized (this){
                totalBidMoney = (Double) redisTemplate.boundValueOps("totalBidMoney").get();
                if (ObjectUtils.isEmpty(totalBidMoney)){
                    totalBidMoney = bidInfoMapper.selectTotalBidMoney();
                    redisTemplate.boundValueOps("totalBidMoney").set(totalBidMoney);
                    System.out.println("从数据库中获取数据 totalBidMoney");
                }
            }
        }else {
            System.out.println("从redis中获取数据 totalBidMoney");
        }


        return totalBidMoney;
    }

    @Override
    public List<Map<String, Object>> findBidInfoListByLoanId(Integer loanId) {

        //sql:根据产品ID查询，投资记录列表，再根据投资记录的uid，查询用户的记录
        List<Map<String, Object>> listByLoanId= bidInfoMapper.selectBidInfoListByLoanId(loanId);

        return listByLoanId;
    }
}
