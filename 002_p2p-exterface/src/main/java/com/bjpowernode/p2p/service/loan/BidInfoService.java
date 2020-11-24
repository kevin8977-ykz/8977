package com.bjpowernode.p2p.service.loan;

import com.bjpowernode.p2p.model.loan.BidInfo;

import java.util.List;
import java.util.Map;

public interface BidInfoService {

    //查询用户投资总金额
    Double findTotalBidMoney();

    List<Map<String, Object>> findBidInfoListByLoanId(Integer loanId,Integer pageIndex,Integer pageSize);

    List<Map<String, Object>> findBidInfoListRecent(Integer uid);

    Long findBidInfoCount(Integer uid);

    List<Map<String,Object>> findBidInfoListByUid(Integer id, int pageNoIndex, Integer pageSize);

    long findBidInfoCountByLoanId(Integer id);


    void invest(Integer loanId, Double bidMoney, Integer id);

    List<Map<String, Object>> findInvestTop();
}
