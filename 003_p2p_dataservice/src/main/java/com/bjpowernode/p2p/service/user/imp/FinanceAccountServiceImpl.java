package com.bjpowernode.p2p.service.user.imp;

import com.alibaba.dubbo.config.annotation.Service;
import com.bjpowernode.p2p.mapper.user.FinanceAccountMapper;
import com.bjpowernode.p2p.model.user.FinanceAccount;
import com.bjpowernode.p2p.model.user.FinanceAccountExample;
import com.bjpowernode.p2p.service.user.FinanceAccountService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Service(interfaceClass = FinanceAccountService.class,version = "1.0.0",timeout = 2000)
public class FinanceAccountServiceImpl implements FinanceAccountService {

    @Autowired
    private FinanceAccountMapper financeAccountMapper;

    @Override
    public FinanceAccount findByUid(Integer id) {

        FinanceAccount financeAccount =financeAccountMapper.selectByUid(id);
        if (financeAccount.getAvailableMoney() == null){
            financeAccount.setAvailableMoney(0.0);
            financeAccountMapper.updateByPrimaryKeySelective(financeAccount);
        }
        return  financeAccount;
    }


    /**
     * 充值成功,根据充值金额更新账户可用余额
     * @param total_amount
     */
    @Override
    @Transactional
    public void updateAvailableMoneyByUid(Integer uid,Double total_amount) {
        FinanceAccountExample example = new FinanceAccountExample();
        FinanceAccountExample.Criteria criteria = example.createCriteria();
        criteria.andUidEqualTo(uid);
        List<FinanceAccount> financeAccountList = financeAccountMapper.selectByExample(example);
        if (ObjectUtils.isNotEmpty(financeAccountList)){
            FinanceAccount financeAccount = financeAccountList.get(0);
            FinanceAccount  updateFinanceAccount = new FinanceAccount();
            updateFinanceAccount.setId(financeAccount.getId());
            updateFinanceAccount.setUid(uid);
            updateFinanceAccount.setAvailableMoney(financeAccount.getAvailableMoney()+total_amount);
            financeAccountMapper.updateByPrimaryKeySelective(updateFinanceAccount);
        }
    }
}
