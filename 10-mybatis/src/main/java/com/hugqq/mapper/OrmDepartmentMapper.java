package com.hugqq.mapper;

import com.hugqq.entity.OrmDepartment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrmDepartmentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrmDepartment record);

    int insertSelective(OrmDepartment record);

    OrmDepartment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrmDepartment record);

    int updateByPrimaryKey(OrmDepartment record);
}