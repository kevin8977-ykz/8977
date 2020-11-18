package com.bjpowernode.p2p.service.user.imp;

import com.alibaba.dubbo.config.annotation.Service;
import com.bjpowernode.p2p.mapper.user.UserMapper;
import com.bjpowernode.p2p.model.user.User;
import com.bjpowernode.p2p.model.user.UserExample;
import com.bjpowernode.p2p.service.user.UserService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@Service(interfaceClass = UserService.class,version = "1.0.0",timeout = 2000)
public class UserServiceImpl implements UserService {

    @Autowired
    private RedisTemplate redisTemplate;


    @Autowired
    private UserMapper userMapper;

    public String findById(){
        System.out.println("findById");
        return "findById";
    }

    @Override
    //public synchronized long findTotalUserCount() {
    public  long findTotalUserCount() {

        //从redis中获取用户总数
        Long  totalUserCount= (Long) redisTemplate.boundValueOps("totalUserCount").get();

       // if( totalUserCount == null || totalUserCount.equals("")){}
        //if(StringUtils.isNotEmpty(totalUserCount))
        //if(StringUtils.isEmpty(totalUserCount)){}

        //第一次进行业务逻辑判断
        if (ObjectUtils.isEmpty(totalUserCount)){

            synchronized (this){
                //第二次进行查询，从redis
                totalUserCount = (Long) redisTemplate.boundValueOps("totalUserCount").get();
                //第二次进行业务逻辑判断
                if (ObjectUtils.isEmpty(totalUserCount)){
                    //如果redis中没有查询出数据，去数据库查询，然后赋值
                    totalUserCount = userMapper.countByExample(null);
                    //存入到redis中
                    redisTemplate.boundValueOps("totalUserCount").set(totalUserCount);
                    System.out.println("从数据库中获取数据 totalUserCount");
                }
            }
        }else {
            System.out.println("从redis中获取数据 totalUserCount");
        }
        return totalUserCount;
    }

    @Override
    public boolean checkPhoneNumRegisted(String phone) {
        //根据手机号码查询总记录数

        UserExample example = new UserExample();

        UserExample.Criteria criteria = example.createCriteria();

        criteria.andPhoneEqualTo(phone);

        Long count = userMapper.countByExample(example);

        //Integer count = userMapper.checkPhoneNumRegisted(phone);

        if (count  >  0){
            return false;
        }

        return true;
    }



    @Override
    public User register(String phone, String loginPassword) {

        //将用户名和密码插入到数据库中
        //将用户返回
        User user = new User();
        user.setPhone(phone);
        user.setLoginPassword(loginPassword);
        user.setAddTime(new Date());
        user.setLastLoginTime(new Date());
        //要在映射配置文件中配置，主键返回
        System.out.println("保存前："+user);
        userMapper.insertSelective(user);
        System.out.println("保存后："+user);
        return user;



    }


}
