package com.bjpowernode.p2p.mapper.loan;

import com.bjpowernode.p2p.model.loan.LoanInfo;
import com.bjpowernode.p2p.model.loan.LoanInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LoanInfoMapper {
    long countByExample(LoanInfoExample example);

    int deleteByExample(LoanInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LoanInfo record);

    int insertSelective(LoanInfo record);

    List<LoanInfo> selectByExample(LoanInfoExample example);

    LoanInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") LoanInfo record, @Param("example") LoanInfoExample example);

    int updateByExample(@Param("record") LoanInfo record, @Param("example") LoanInfoExample example);

    int updateByPrimaryKeySelective(LoanInfo record);

    int updateByPrimaryKey(LoanInfo record);

    Double selectHistoryAverageRate();

    //根据产品类型查询产品列表数据
    List<LoanInfo> selectLoanInfoListByProductType(@Param("productType") int productType,
                                                   @Param("pageNo") int pageNoIndex,
                                                   @Param("pageSize") int pageSize);

}