package com.hugqq.user.mapper;

import com.hugqq.user.entity.User;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;


@Mapper
public interface UserMapper extends BaseMapper<User> {

    void deleteAll();
}

