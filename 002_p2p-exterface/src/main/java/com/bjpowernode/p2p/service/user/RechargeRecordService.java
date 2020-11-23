package com.bjpowernode.p2p.service.user;

import com.bjpowernode.p2p.model.loan.RechargeRecord;

import java.util.List;

public interface RechargeRecordService {

    //根据用户id 查询最近5笔充值记录
    List<RechargeRecord> findRechargeRecordListRecent(Integer uid);

    Long findRechargeRecordCount(Integer id);

    List<RechargeRecord> findRechargeRecordListByUid(Integer id, int pageNoIndex, Integer pageSize);

}
