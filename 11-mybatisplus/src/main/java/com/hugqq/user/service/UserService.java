package com.hugqq.user.service;


import com.baomidou.mybatisplus.extension.service.IService;

import com.hugqq.user.entity.User;


public interface UserService extends IService<User> {

    void deletedAll();
}

