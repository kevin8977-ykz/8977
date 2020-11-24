package com.bjpowernode.p2p.service.user.imp;

import com.alibaba.dubbo.config.annotation.Service;
import com.bjpowernode.p2p.mapper.loan.RechargeRecordMapper;
import com.bjpowernode.p2p.model.loan.RechargeRecord;
import com.bjpowernode.p2p.model.loan.RechargeRecordExample;
import com.bjpowernode.p2p.service.user.RechargeRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Component
@Service(interfaceClass = RechargeRecordService.class,version = "1.0.0",timeout = 2000)
public class RechargeRecordServiceImpl implements RechargeRecordService {


    @Autowired
    private RechargeRecordMapper rechargeRecordMapper;

    @Autowired
    private RedisTemplate redisTemplate;

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

    /**
     * 生成唯一订单号
     * 规则：四位随机数+"M"+格式化到秒的时间+"R"+六位随机数
     */
    @Override
    public String getRechargeNo() {
        Random rd = new Random(); // 创建随机对象
        String n = "";            //保存随机数
        int rdGet;                // 取得随机数
        do {
            if (rd.nextInt() % 2 == 1) {
                rdGet = Math.abs(rd.nextInt()) % 10 + 48;  // 产生48到57的随机数(0-9的键位值)
            }else{
                rdGet = Math.abs(rd.nextInt()) % 26 + 97;  // 产生97到122的随机数(a-z的键位值)
            }
            char num1 = (char) rdGet;                      //int转换char
            String dd = Character.toString(num1);
            n += dd;
        } while (n.length() < 8);// 设定长度，此处假定长度小于8
        String r1= (((Math.random()*9+1)*100000)+"").substring(0, 6);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
        String SNDate = sdf.format(new Date());
        String orderCode = r1 + "M" + SNDate + "R" + n.toUpperCase();
        return orderCode;

    }

    @Override
    public RechargeRecord recharge(Double rechargeMoney, Integer uid) {
        //将充值记录插入到数据库中
        RechargeRecord rechargeRecord = new RechargeRecord();
        rechargeRecord.setUid(uid);
        rechargeRecord.setRechargeTime(new Date());
        //0 充值中  1 充值成功 2 充值失败
        rechargeRecord.setRechargeStatus("0");
        //充值订单号,全局唯一不能重复
        String rechargeNo = getRechargeNo();
        rechargeRecord.setRechargeNo(rechargeNo);
        rechargeRecord.setRechargeMoney(rechargeMoney);
        rechargeRecord.setRechargeDesc("支付宝充值支付");
        //插入到数据库中
        int i = rechargeRecordMapper.insertSelective(rechargeRecord);
        if (i <= 0){
            throw new RuntimeException("充值失败,请稍后再试");
        }
        return rechargeRecord;

    }
}
