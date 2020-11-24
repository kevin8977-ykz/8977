package com.bjpowernode.p2p.service.user.imp;

import com.alibaba.dubbo.config.annotation.Service;

import com.bjpowernode.p2p.mapper.loan.BidInfoMapper;
import com.bjpowernode.p2p.mapper.loan.IncomeRecordMapper;
import com.bjpowernode.p2p.mapper.loan.LoanInfoMapper;
import com.bjpowernode.p2p.mapper.user.FinanceAccountMapper;

import com.bjpowernode.p2p.model.loan.*;
import com.bjpowernode.p2p.model.user.FinanceAccount;
import com.bjpowernode.p2p.model.user.FinanceAccountExample;
import com.bjpowernode.p2p.service.user.IncomeRecordService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
@Service(interfaceClass = IncomeRecordService.class, version = "1.0.0", timeout = 2000)
public class IncomeRecordServiceImpl implements IncomeRecordService {


    @Autowired
    private IncomeRecordMapper incomeRecordMapper;

    @Autowired
    private LoanInfoMapper loanInfoMapper;

    @Autowired
    private BidInfoMapper bidInfoMapper;

    @Autowired
    private FinanceAccountMapper financeAccountMapper;



    @Override
    public List<Map<String, Object>> findIncomeRecordListRecent(Integer uid) {
        //根据用户id查询最近5条收益记录
        return  incomeRecordMapper.selectIncomeRecordListRecent(uid);
    }

    @Override
    public Long findIncomeRecordCount(Integer uid) {
        IncomeRecordExample example = new IncomeRecordExample();
        IncomeRecordExample.Criteria criteria = example.createCriteria();
        criteria.andUidEqualTo(uid);

        return incomeRecordMapper.countByExample(example);
    }

    @Override
    public List<Map<String, Object>> findIncomeRecordListByUid(Integer id, int pageNoIndex, Integer pageSize) {
        return incomeRecordMapper.selectIncomeRecordListByUid(id,pageNoIndex,pageSize);
    }

    /**
     * 产品的状态：0 未满标 1 已满标 2满标且生成收益计划
     * 生成收益计划的业务逻辑：
     * 0.如果剩余可投金额为0时，更新产品状态为1，已满标
     * 1.查询已满标的产品，可能是一个也可能是多个
     * 2.遍历查询出的已满标的集合
     * 3.根据集合中每个已满标的产品，生成对应的收益计划
     * 4.查询出对应的投标集合
     * 5.根据每一条投标记录，生成一条收益计划
     * 6.将收益计划插入到数据库中
     * 计算出本息
     * 计算返还本息的时间
     * 7.更新产品状态为2，满标且生成收益计划
     */
    @Override
    @Transactional
    public void generateIncomeRecordPlan() {
        //剩余可投金额为0时并且产品状态为0，更新产品状态为1
        List<LoanInfo> loanInfoListUpdate = loanInfoMapper.selectLoanInfoListByLeftProductMoneyAndProductStatus(0.0, 0);

        if (ObjectUtils.isNotEmpty(loanInfoListUpdate)) {
            //遍历，将产品的状态修改为1，代表已满标
            for (LoanInfo li : loanInfoListUpdate) {
                LoanInfo updateLoanInfo = new LoanInfo();
                updateLoanInfo.setId(li.getId());
                updateLoanInfo.setProductStatus(1);
                //满标的时间
                updateLoanInfo.setProductFullTime(new Date());
                loanInfoMapper.updateByPrimaryKeySelective(updateLoanInfo);
            }
        }

        LoanInfoExample example = new LoanInfoExample();
        LoanInfoExample.Criteria criteria = example.createCriteria();
        criteria.andProductStatusEqualTo(1);

        //得到已满标的集合
        List<LoanInfo> loanInfoList = loanInfoMapper.selectByExample(example);

        //遍历
        if (ObjectUtils.isNotEmpty(loanInfoList)) {
            //获取每个已满标的产品
            for (LoanInfo loanInfo : loanInfoList) {

                //查询每个产品的投标记录
                Integer loanId = loanInfo.getId();

                BidInfoExample bidInfoExample = new BidInfoExample();

                BidInfoExample.Criteria bidInfoExampleCriteria = bidInfoExample.createCriteria();

                bidInfoExampleCriteria.andLoanIdEqualTo(loanId);

                List<BidInfo> bidInfoList = bidInfoMapper.selectByExample(bidInfoExample);


                //生成收益计划
                //每一个投标记录，都要生成一个收益计划
                if (ObjectUtils.isNotEmpty(bidInfoList)) {
                    for (BidInfo bidInfo : bidInfoList) {

                        IncomeRecord incomeRecord = new IncomeRecord();

                        incomeRecord.setUid(bidInfo.getUid());

                        incomeRecord.setBidId(bidInfo.getId());

                        incomeRecord.setBidMoney(bidInfo.getBidMoney());

                        incomeRecord.setLoanId(bidInfo.getLoanId());

                        //收益返还：0 代表未返还 1 代表已返还
                        incomeRecord.setIncomeStatus(0);

                        //计算收益本息以及收益时间
//                        incomeRecord.setIncomeMoney();
//                        incomeRecord.setIncomeDate();

                        if (loanInfo.getProductType() == 0) {
                            //新手宝，按天计息
                            //日利率 = 年利率 / 100 /365
                            //投资金额 * 日利率 * 周期(天)
                            Double money = bidInfo.getBidMoney() * (loanInfo.getRate() / 100 / 365) * loanInfo.getCycle();
                            //money是有小数点问题，最多保留2位小数
                            Long math = Math.round(money * 100) / 100;
                            incomeRecord.setIncomeMoney(math.doubleValue());


                            //新手宝，返还时间+天
                            //满标时间 + 周期
                            Date productFullTime = loanInfo.getProductFullTime();

                            Date newDate = DateUtils.addDays(productFullTime, 1);

                            incomeRecord.setIncomeDate(DateUtils.addDays(newDate, loanInfo.getCycle()));

                        } else {
                            //优选、散标，按月计息
                            Double money = bidInfo.getBidMoney() * (loanInfo.getRate() / 100 / 12) * loanInfo.getCycle();
                            //money是有小数点问题，最多保留2位小数
                            Long math = Math.round(money * 100) / 100;
                            incomeRecord.setIncomeMoney(math.doubleValue());

                            //优选、散标，返还时间+月
                            //满标时间 + 周期
                            Date productFullTime = loanInfo.getProductFullTime();

                            Date newDate = DateUtils.addDays(productFullTime, 1);

                            incomeRecord.setIncomeDate(DateUtils.addMonths(newDate, loanInfo.getCycle()));

                        }

                        //将收益记录插入到数据库中
                        incomeRecordMapper.insertSelective(incomeRecord);

                    }
                }

                //更新产品状态为2，代表满标且生成收益计划
                LoanInfo updateLoanInfo = new LoanInfo();

                updateLoanInfo.setId(loanId);

                updateLoanInfo.setProductStatus(2);

                loanInfoMapper.updateByPrimaryKeySelective(updateLoanInfo);
            }
        }
    }

    /**
     * 返还收益计划：
     *      查询收益记录中，收益状态为0的数据，代表未返还收益计划的数据
     *      返还完成后，将收益返还状态更新为1，代表已返还
     *
     */
    @Override
    public void incomePlanBack() {

        //查询收益当天未返还的记录
        List<IncomeRecord> incomeRecordList = incomeRecordMapper.selectIncomeRecordListByIncomeStatus();

        if(ObjectUtils.isNotEmpty(incomeRecordList)){
            //查询有数据
            for (IncomeRecord incomeRecord : incomeRecordList) {
                //开始返还收益

                //根据userId查询出账户信息
                FinanceAccountExample example = new FinanceAccountExample();

                FinanceAccountExample.Criteria criteria = example.createCriteria();

                criteria.andUidEqualTo(incomeRecord.getUid());

                //查询出的账户信息，只有一条
                List<FinanceAccount> financeAccountList = financeAccountMapper.selectByExample(example);

                if(ObjectUtils.isNotEmpty(financeAccountList)){
                    //获取账户信息
                    FinanceAccount financeAccount = financeAccountList.get(0);

                    //现有的余额
                    Double availableMoney = financeAccount.getAvailableMoney();

                    //利息
                    Double incomeMoney = incomeRecord.getIncomeMoney();

                    //本金
                    Double bidMoney = incomeRecord.getBidMoney();

                    Double money = availableMoney + incomeMoney + bidMoney;

                    FinanceAccount updateFinanceAccount = new FinanceAccount();

                    updateFinanceAccount.setId(financeAccount.getId());

                    updateFinanceAccount.setAvailableMoney(money);

                    //返还本金和利息
                    financeAccountMapper.updateByPrimaryKeySelective(updateFinanceAccount);

                    //将收益返还的状态更新为1
                    IncomeRecord updateIncomeRecord = new IncomeRecord();

                    updateIncomeRecord.setId(incomeRecord.getId());

                    updateIncomeRecord.setIncomeStatus(1);

                    incomeRecordMapper.updateByPrimaryKeySelective(updateIncomeRecord);
                }

            }

        }

    }

}
