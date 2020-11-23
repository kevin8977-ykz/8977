package com.bjpowernode.p2p.service.user;

import com.bjpowernode.p2p.model.user.FinanceAccount;

public interface FinanceAccountService {

    FinanceAccount findByUid(Integer id);
}
