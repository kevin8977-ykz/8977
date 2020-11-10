package com.bjpowernode.springboot.mapper;

import com.bjpowernode.springboot.model.DictionaryValue;

public interface DictionaryValueMapper {
    int deleteByPrimaryKey(String id);

    int insert(DictionaryValue record);

    int insertSelective(DictionaryValue record);

    DictionaryValue selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DictionaryValue record);

    int updateByPrimaryKey(DictionaryValue record);
}