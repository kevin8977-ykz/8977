package com.bjpowernode.p2p.mapper.loan;

import com.bjpowernode.p2p.model.loan.IncomeRecord;
import com.bjpowernode.p2p.model.loan.IncomeRecordExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface IncomeRecordMapper {
    long countByExample(IncomeRecordExample example);

    int deleteByExample(IncomeRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(IncomeRecord record);

    int insertSelective(IncomeRecord record);

    List<IncomeRecord> selectByExample(IncomeRecordExample example);

    IncomeRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") IncomeRecord record, @Param("example") IncomeRecordExample example);

    int updateByExample(@Param("record") IncomeRecord record, @Param("example") IncomeRecordExample example);

    int updateByPrimaryKeySelective(IncomeRecord record);

    int updateByPrimaryKey(IncomeRecord record);

    /**
     * 根据用户id，查询最近5条收益记录
     * @param uid
     * @return
     */
    List<Map<String, Object>> selectIncomeRecordListRecent(Integer uid);

    List<Map<String, Object>> selectIncomeRecordListByUid(@Param("uid") Integer uid,
                                                          @Param("pageNo") int pageNo,
                                                          @Param("pageSize") Integer pageSize);
}