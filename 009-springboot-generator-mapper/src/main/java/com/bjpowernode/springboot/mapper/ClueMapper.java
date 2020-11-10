package com.bjpowernode.springboot.mapper;

import com.bjpowernode.springboot.model.Clue;

public interface ClueMapper {

    //根据主键删除
    int deleteByPrimaryKey(String id);

    //插入
    int insert(Clue record);

    //选择性插入
    int insertSelective(Clue record);

    //根据主键查询
    Clue selectByPrimaryKey(String id);

    //根据主键选择更新
    int updateByPrimaryKeySelective(Clue record);

    //根据主键更新
    int updateByPrimaryKey(Clue record);
}