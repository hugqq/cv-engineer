package com.hugqq.controller;

import com.hugqq.entity.User;
import com.hugqq.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/user/testInsert")
    public void testInsert() {
        User user1 = new User("张三",18);
        User user2 = new User("李四",17);
        userService.insert(user1);
        userService.insertAsync(user2);
    }

    @GetMapping("/user/testInsertMultiple")
    public void testInsertMultiple() {
        User user1 = new User("张三",18);
        User user2 = new User("李四",17);
        userService.insertMultiple(user1,user2);
    }



}
