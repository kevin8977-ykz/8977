package com.bjpowernode.p2p.service.loan;

import com.bjpowernode.p2p.model.loan.LoanInfo;

import java.util.List;

public interface LoanInfoService {

    //获取历史平均收益率
    Double findHistoryAverageRate();

    //根据产品类型查询产品类别数据
    List<LoanInfo> findLoanInfoListByProductType(int productType, int pageNoIndex, int pageSize);

    //查询产品的总记录数
    long findTotalCount(int productType);


    LoanInfo findById(Integer id);
}
