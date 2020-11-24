package com.bjpowernode.p2p.service.user;

import java.util.List;
import java.util.Map;

public interface IncomeRecordService {

    /**
     *  根据用户id查询最近5条收益记录
     * @param uid
     * @return
     */
    List<Map<String, Object>> findIncomeRecordListRecent(Integer uid);

    Long findIncomeRecordCount(Integer uid);


    /**
     * 根据用户id 分页查询收益记录
     * @param id
     * @param pageNoIndex
     * @param pageSize
     * @return
     */
    List<Map<String, Object>> findIncomeRecordListByUid(Integer id, int pageNoIndex, Integer pageSize);


    void generateIncomeRecordPlan();

    void incomePlanBack();

}
