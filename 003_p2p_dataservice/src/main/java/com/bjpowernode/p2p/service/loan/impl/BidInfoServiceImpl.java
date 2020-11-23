package com.bjpowernode.p2p.service.loan.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.bjpowernode.p2p.mapper.loan.BidInfoMapper;
import com.bjpowernode.p2p.mapper.loan.LoanInfoMapper;
import com.bjpowernode.p2p.mapper.user.FinanceAccountMapper;
import com.bjpowernode.p2p.model.loan.BidInfo;
import com.bjpowernode.p2p.model.loan.BidInfoExample;
import com.bjpowernode.p2p.model.loan.LoanInfo;
import com.bjpowernode.p2p.model.user.FinanceAccount;
import com.bjpowernode.p2p.model.user.FinanceAccountExample;
import com.bjpowernode.p2p.service.loan.BidInfoService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Component
@Service(interfaceClass = BidInfoService.class,version = "1.0.0",timeout = 2000)
public class BidInfoServiceImpl implements BidInfoService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private BidInfoMapper bidInfoMapper;

    @Autowired
    private LoanInfoMapper loanInfoMapper;

    @Autowired
    private FinanceAccountMapper financeAccountMapper;

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
    public List<Map<String, Object>> findBidInfoListByLoanId(Integer loanId,Integer pageIndex,Integer pageSize) {

        //sql:根据产品ID查询，投资记录列表，再根据投资记录的uid，查询用户的记录
        List<Map<String, Object>> listByLoanId= bidInfoMapper.selectBidInfoListByLoanId(loanId,pageIndex,pageSize);

        return listByLoanId;
    }

    @Override
    public List<Map<String, Object>> findBidInfoListRecent(Integer uid) {

        return bidInfoMapper.selectBidInfoListRecent(uid);
    }


    //查询投资记录的中记录数
    @Override
    public Long findBidInfoCount(Integer uid) {
        BidInfoExample example = new BidInfoExample();
        BidInfoExample.Criteria criteria = example.createCriteria();
        criteria.andUidEqualTo(uid);

        return bidInfoMapper.countByExample(example);
    }

    @Override
    public List<Map<String,Object>> findBidInfoListByUid(Integer id, int pageNoIndex, Integer pageSize) {

        return bidInfoMapper.selectBidInfoListByUid(id,pageNoIndex,pageSize);
    }

    @Override
    public long findBidInfoCountByLoanId(Integer id) {
        BidInfoExample example = new BidInfoExample();
        BidInfoExample.Criteria criteria = example.createCriteria();
        criteria.andLoanIdEqualTo(id);
        return bidInfoMapper.countByExample(example);
    }

    @Override
    @Transactional
    public void invest(Integer loanId, Double bidMoney, Integer id) {
        //生成投资记录
        BidInfo bidInfo = new BidInfo();
        bidInfo.setUid(id);
        bidInfo.setLoanId(loanId);
        bidInfo.setBidMoney(bidMoney);
        bidInfo.setBidTime(new Date());
        bidInfo.setBidStatus(1);//1为投资成功

        //插入这条记录
        int bidInfoCount = bidInfoMapper.insertSelective(bidInfo);

        if(bidInfoCount <= 0 ){
             throw new RuntimeException("插入投资记录失败");
        }

        //查询剩余可投金额
        LoanInfo loanInfo = loanInfoMapper.selectByPrimaryKey(loanId);

        if (ObjectUtils.isEmpty(loanInfo)){
            throw new RuntimeException("未查询到该产品");
        }
        //产品表中剩余可投资金额减去投资金额
     /*   LoanInfo updateLoanInfo = new LoanInfo();
        updateLoanInfo.setLeftProductMoney(loanInfo.getLeftProductMoney() - bidMoney);
        updateLoanInfo.setId(loanId);
        loanInfoMapper.updateByPrimaryKeySelective(updateLoanInfo);*/
        int loanInfoCount = loanInfoMapper.update(loanId,bidMoney,loanInfo.getVersion());

        if ( loanInfoCount <= 0){
            throw new RuntimeException("剩余可投金额更新失败");
        }
        //查询账户信息
        FinanceAccountExample example = new FinanceAccountExample();
        FinanceAccountExample.Criteria criteria = example.createCriteria();
        criteria.andUidEqualTo(id);
        List<FinanceAccount> financeAccounts = financeAccountMapper.selectByExample(example);
        if (ObjectUtils.isEmpty(financeAccounts)){
            throw new RuntimeException("查询账户信息失败");
        }
        //账户可用余额减去投资金额
        FinanceAccount updateFinanceAccount = new FinanceAccount();
        updateFinanceAccount.setId(financeAccounts.get(0).getId());
        Double availableMoney = financeAccounts.get(0).getAvailableMoney() - bidMoney;
        updateFinanceAccount.setAvailableMoney(availableMoney);
        int financeAccountCount = financeAccountMapper.updateByPrimaryKeySelective(updateFinanceAccount);

        if (financeAccountCount <= 0){
            throw new RuntimeException("更新账户余额失败");
        }
        //处理投资排行榜'


    }


}
