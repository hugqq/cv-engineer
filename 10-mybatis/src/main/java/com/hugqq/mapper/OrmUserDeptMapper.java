package com.hugqq.mapper;

import com.hugqq.entity.OrmUserDept;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrmUserDeptMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrmUserDept record);

    int insertSelective(OrmUserDept record);

    OrmUserDept selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrmUserDept record);

    int updateByPrimaryKey(OrmUserDept record);
}