package com.bjpowernode.springboot.mapper;

import com.bjpowernode.springboot.model.DictionaryType;

public interface DictionaryTypeMapper {
    int deleteByPrimaryKey(String code);

    int insert(DictionaryType record);

    int insertSelective(DictionaryType record);

    DictionaryType selectByPrimaryKey(String code);

    int updateByPrimaryKeySelective(DictionaryType record);

    int updateByPrimaryKey(DictionaryType record);
}