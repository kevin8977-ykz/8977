package com.bjpowernode.springboot.mapper;

import com.bjpowernode.springboot.model.Student;
import org.apache.ibatis.annotations.Mapper;

//@Mapper 作用：mybatis 自动扫描数据持久层的映射文件及
//DAO 接口的关系
@Mapper
public interface StudentMapper {

    Student queryStudentById(Integer id);
}
