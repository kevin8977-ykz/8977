package com.bjpowernode.p2p.service.user.imp;

import com.alibaba.dubbo.config.annotation.Service;
import com.bjpowernode.p2p.mapper.loan.IncomeRecordMapper;
import com.bjpowernode.p2p.model.loan.IncomeRecordExample;
import com.bjpowernode.p2p.service.user.IncomeRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Service(interfaceClass = IncomeRecordService.class,version = "1.0.0",timeout = 2000)
public class IncomeRecordServiceImpl implements IncomeRecordService {


    @Autowired
    private IncomeRecordMapper incomeRecordMapper;

    @Override
    public List<Map<String, Object>> findIncomeRecordListRecent(Integer uid) {
        //根据用户id查询最近5条收益记录
        return  incomeRecordMapper.selectIncomeRecordListRecent(uid);
    }

    @Override
    public Long findIncomeRecordCount(Integer uid) {
        IncomeRecordExample example = new IncomeRecordExample();
        IncomeRecordExample.Criteria criteria = example.createCriteria();
        criteria.andUidEqualTo(uid);

        return incomeRecordMapper.countByExample(example);
    }

    @Override
    public List<Map<String, Object>> findIncomeRecordListByUid(Integer uid, int pageNo, Integer pageSize) {
        return incomeRecordMapper.selectIncomeRecordListByUid(uid,pageNo,pageSize);
    }


}
