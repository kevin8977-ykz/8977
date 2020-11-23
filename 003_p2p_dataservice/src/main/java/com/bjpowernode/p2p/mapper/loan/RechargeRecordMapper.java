package com.bjpowernode.p2p.mapper.loan;

import com.bjpowernode.p2p.model.loan.RechargeRecord;
import com.bjpowernode.p2p.model.loan.RechargeRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RechargeRecordMapper {
    long countByExample(RechargeRecordExample example);

    int deleteByExample(RechargeRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(RechargeRecord record);

    int insertSelective(RechargeRecord record);

    List<RechargeRecord> selectByExample(RechargeRecordExample example);

    RechargeRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") RechargeRecord record, @Param("example") RechargeRecordExample example);

    int updateByExample(@Param("record") RechargeRecord record, @Param("example") RechargeRecordExample example);

    int updateByPrimaryKeySelective(RechargeRecord record);

    int updateByPrimaryKey(RechargeRecord record);

    //根据用户id查询最近5条充值记录
    List<RechargeRecord> selectRechargeRecordListRecent(Integer uid);

    List<RechargeRecord> selectRechargeRecordListByUid(@Param("uid") Integer uid,
                                                       @Param("pageNo") int pageNoIndex,
                                                       @Param("pageSize") Integer pageSize);
}