package com.bjpowernode.p2p.service.user;

import com.bjpowernode.p2p.model.user.FinanceAccount;

public interface FinanceAccountService {

    FinanceAccount findByUid(Integer id);

    //充值成功,根据充值金额更新账户可用余额
    void updateAvailableMoneyByUid(Integer uid,Double total_amount);
}
