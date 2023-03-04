package com.hugqq.user.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hugqq.user.entity.User;
import com.hugqq.user.mapper.UserMapper;
import com.hugqq.user.service.UserService;

/**
 *  服务实现类
 * @author hugqq
 * @since 2023-02-25
 */

@Transactional
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    @Autowired
    private UserMapper userMapper;
    @Override
    public void deletedAll() {
        userMapper.deleteAll();
    }
}

