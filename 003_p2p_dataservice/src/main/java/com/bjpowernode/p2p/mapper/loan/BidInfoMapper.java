package com.bjpowernode.p2p.mapper.loan;

import com.bjpowernode.p2p.model.loan.BidInfo;
import com.bjpowernode.p2p.model.loan.BidInfoExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface BidInfoMapper {
    long countByExample(BidInfoExample example);

    int deleteByExample(BidInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BidInfo record);

    int insertSelective(BidInfo record);

    List<BidInfo> selectByExample(BidInfoExample example);

    BidInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BidInfo record, @Param("example") BidInfoExample example);

    int updateByExample(@Param("record") BidInfo record, @Param("example") BidInfoExample example);

    int updateByPrimaryKeySelective(BidInfo record);

    int updateByPrimaryKey(BidInfo record);

    Double selectTotalBidMoney();

    //根据产品ID,查询给产品的投资金额、投资时间、用户的手机号码
    List<Map<String, Object>> selectBidInfoListByLoanId(@Param("loanId") Integer loanId,
                                                        @Param("pageIndex") Integer pageIndex,
                                                        @Param("pageSize") Integer pageSize);

    //根据用户id，查询最近5条投资记录
    List<Map<String, Object>> selectBidInfoListRecent(Integer uid);

    List<Map<String,Object>> selectBidInfoListByUid(@Param("id") Integer id,@Param("pageNoIndex") int pageNoIndex,@Param("pageSize") Integer pageSize);

    Long findBidInfoCount(Integer uid);
}