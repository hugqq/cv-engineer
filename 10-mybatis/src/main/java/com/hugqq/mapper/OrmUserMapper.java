package com.hugqq.mapper;

import com.hugqq.entity.InterceptAnnotation;
import com.hugqq.entity.OrmDepartment;
import com.hugqq.entity.OrmUser;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrmUserMapper {

    @InterceptAnnotation(value = 5)
    @Select("select * from orm_user")
    List<OrmUser> selectAll();

    List<OrmUser> selectAllUser();

    int deleteByPrimaryKey(Integer id);

    int insert(OrmUser record);

    int insertSelective(OrmUser record);

    int insertBatch(List<OrmUser> list);

    OrmUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrmUser record);

    int updateByPrimaryKey(OrmUser record);

    List<OrmDepartment> selectOrmDept();

    @Delete("delete from orm_user")
    void deleteAll();
}