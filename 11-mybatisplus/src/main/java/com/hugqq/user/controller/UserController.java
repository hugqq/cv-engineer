package com.hugqq.user.controller;

import com.apifan.common.random.source.NumberSource;
import com.apifan.common.random.source.PersonInfoSource;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.hugqq.user.entity.User;
import com.hugqq.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author hugqq
 * @since 2023-02-25
 */

@RestController
@RequestMapping("user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @DS("master")
    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // 成功选择正确的数据源
        List<User> userList = Lists.newArrayList();
        for (int i = 0; i < 20; i++) {
            User user = new User();
            user.setName(PersonInfoSource.getInstance().randomChineseName());
            user.setAge(NumberSource.getInstance().randomInt(10, 100));
            user.setCreateTime(new Date());
            user.setCreateBy("system");
            user.setDeleted("0");
            userList.add(user);
        }
        userService.deletedAll();
        userService.saveBatch(userList);
    }


    @DS("slave_1")
    @EventListener
    public void onApplicationEvent2(ContextRefreshedEvent event) {
        // 成功选择正确的数据源
        List<User> userList = Lists.newArrayList();
        for (int i = 0; i < 20; i++) {
            User user = new User();
            user.setName(PersonInfoSource.getInstance().randomChineseName());
            user.setAge(NumberSource.getInstance().randomInt(10, 100));
            user.setCreateTime(new Date());
            user.setCreateBy("system");
            user.setDeleted("0");
            userList.add(user);
        }
        userService.deletedAll();
        userService.saveBatch(userList);
    }

    @GetMapping("list")
    public ResponseEntity list(User user, HttpServletRequest request) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<User>();
        List<User> list = userService.list(lambdaQueryWrapper);
        return ResponseEntity.ok(list);
    }

    @GetMapping("page")
    public ResponseEntity pageList(User user,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest request) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        Page<User> page = userService.page(new Page<User>(pageNo, pageSize), lambdaQueryWrapper);
        return ResponseEntity.ok(page);
    }

    @GetMapping("getOne")
    public ResponseEntity getOne(User user, HttpServletRequest request) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<User>();
        User one = userService.getOne(lambdaQueryWrapper);
        return ResponseEntity.ok(one);
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(String id, HttpServletRequest request) {
        User one = userService.getById(id);
        return ResponseEntity.ok(one);
    }

    @PostMapping("saveOrUpdate")
    public ResponseEntity saveOrUpdate(User user, HttpServletRequest request) {
        return ResponseEntity.ok(userService.saveOrUpdate(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity del(@PathVariable String id, HttpServletRequest request) {
        return ResponseEntity.ok(userService.removeById(id));
    }

}


