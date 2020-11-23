package com.bjpowernode.p2p.service.user.imp;

import com.alibaba.dubbo.config.annotation.Service;
import com.bjpowernode.p2p.mapper.loan.RechargeRecordMapper;
import com.bjpowernode.p2p.model.loan.RechargeRecord;
import com.bjpowernode.p2p.model.loan.RechargeRecordExample;
import com.bjpowernode.p2p.service.user.RechargeRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Service(interfaceClass = RechargeRecordService.class,version = "1.0.0",timeout = 2000)
public class RechargeRecordServiceImpl implements RechargeRecordService {


    @Autowired
    private RechargeRecordMapper rechargeRecordMapper;

    @Override
    public List<RechargeRecord> findRechargeRecordListRecent(Integer uid) {
        return rechargeRecordMapper.selectRechargeRecordListRecent(uid);
    }

    @Override
    public Long findRechargeRecordCount(Integer id) {
        RechargeRecordExample example = new RechargeRecordExample();
        RechargeRecordExample.Criteria criteria = example.createCriteria();

        criteria.andUidEqualTo(id);

        return rechargeRecordMapper.countByExample(example);
    }

    @Override
    public List<RechargeRecord> findRechargeRecordListByUid(Integer uid, int pageNoIndex, Integer pageSize) {
        return rechargeRecordMapper.selectRechargeRecordListByUid(uid,pageNoIndex,pageSize);
    }
}
