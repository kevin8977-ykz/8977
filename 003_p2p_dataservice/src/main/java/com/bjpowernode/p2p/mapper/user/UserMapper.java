package com.bjpowernode.p2p.mapper.user;

import com.bjpowernode.p2p.model.user.User;
import com.bjpowernode.p2p.model.user.UserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    long countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    Integer checkPhoneNumRegisted(String phone);

    int updateRegisteData(@Param("id") Integer id,
                           @Param("realName") String realName,
                           @Param("idCard") String idCard);
}