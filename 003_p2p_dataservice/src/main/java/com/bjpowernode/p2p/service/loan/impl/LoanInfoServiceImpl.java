package com.bjpowernode.p2p.service.loan.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.bjpowernode.p2p.mapper.loan.LoanInfoMapper;
import com.bjpowernode.p2p.model.loan.LoanInfo;
import com.bjpowernode.p2p.model.loan.LoanInfoExample;
import com.bjpowernode.p2p.service.loan.LoanInfoService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@Service(interfaceClass = LoanInfoService.class,version = "1.0.0",timeout = 2000)
public class LoanInfoServiceImpl implements LoanInfoService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private LoanInfoMapper loanInfoMapper;

    @Override
    public Double findHistoryAverageRate() {
        Double historyAverageRate = (Double) redisTemplate.boundValueOps("historyAverageRate").get();
        if(ObjectUtils.isEmpty(historyAverageRate)){
            synchronized (this){
                historyAverageRate = (Double) redisTemplate.boundValueOps("historyAverageRate").get();
                if(ObjectUtils.isEmpty(historyAverageRate)){
                    historyAverageRate = loanInfoMapper.selectHistoryAverageRate();
                    redisTemplate.boundValueOps("historyAverageRate").set(historyAverageRate);
                    System.out.println("从数据库中获取数据 historyAverageRate");
                }
            }
        }else {
            System.out.println("从redis中获取数据 historyAverageRate");
        }

        return historyAverageRate;
    }

    @Override
    public List<LoanInfo> findLoanInfoListByProductType(int productType, int pageNoIndex, int pageSize) {
        return loanInfoMapper.selectLoanInfoListByProductType(productType, pageNoIndex, pageSize);
    }

    @Override
    public long findTotalCount(int productType) {
        LoanInfoExample example = new LoanInfoExample();

        LoanInfoExample.Criteria criteria = example.createCriteria();

        criteria.andProductTypeEqualTo(productType);

        return loanInfoMapper.countByExample(example);
    }

    @Override
    public LoanInfo findById(Integer id) {
        return  loanInfoMapper.selectByPrimaryKey(id);
    }
}
