package com.bjpowernode.p2p.service.loan;

import java.util.List;
import java.util.Map;

public interface BidInfoService {

    //查询用户投资总金额
    Double findTotalBidMoney();

    List<Map<String, Object>> findBidInfoListByLoanId(Integer loanId);
}
