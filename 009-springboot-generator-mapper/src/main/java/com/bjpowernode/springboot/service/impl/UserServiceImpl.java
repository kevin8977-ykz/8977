package com.bjpowernode.springboot.service.impl;

import com.bjpowernode.springboot.mapper.UserMapper;
import com.bjpowernode.springboot.model.User;
import com.bjpowernode.springboot.model.UserExample;
import com.bjpowernode.springboot.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    //逆向工程执行一次就好了
    //逆向工程生成mapper接口
    @Resource
    private UserMapper userMapper;

    @Override
    public List<User> findAll() {
        //根据条件查询，example
        //传入一个null，mapper映射配置文件中
        //根据 select * from tbl_user
        // * 通过sql标签引入，所以数据库中user表的字段信息
        return userMapper.selectByExample(null);
    }

    @Override
    public List<User> findByExample(User user) {
        //创建查询条件对象
        UserExample example = new UserExample();

        //封装查询条件
        UserExample.Criteria criteria = example.createCriteria();

        //条件拼接有固定的格式(方法名称)：and 字段名称 查询条件结尾

        if(user.getName() != null && user.getName() != ""){
            criteria.andNameLike(user.getName());
        }


        if(user.getEmail() != null && user.getEmail() != ""){
            //等值查询
            criteria.andEmailEqualTo(user.getEmail());
        }

        List<User> users = userMapper.selectByExample(example);

        return users;

    }

    @Override
    public User findById(String id) {
        return userMapper.selectByPrimaryKey(id);

    }

    @Override
    public Long findAllCount() {

        return userMapper.countByExample(null);
    }

    @Override
    public Long findCountById(String id) {

        UserExample example = new UserExample();

        UserExample.Criteria criteria = example.createCriteria();

        //根据id等值查询
        criteria.andIdEqualTo(id);

        return userMapper.countByExample(example);
    }

    @Override
    public User insert(User user) {
        userMapper.insert(user);
        return user;
    }

    @Override
    public User insertSelective(User user) {
        userMapper.insertSelective(user);
        return user;
    }

    @Override
    public User update(User user) {
        userMapper.updateByPrimaryKey(user);
        return user;
    }

    @Override
    public User updateSelective(User user) {
        userMapper.updateByPrimaryKeySelective(user);
        return user;
    }

    @Override
    public User delete(String id) {
        userMapper.deleteByPrimaryKey(id);
        return new User();
    }

    @Override
    public User deleteSelective(String name) {

        UserExample example = new UserExample();

        UserExample.Criteria criteria = example.createCriteria();

        criteria.andNameEqualTo(name);

        userMapper.deleteByExample(example);

        return null;
    }
}
