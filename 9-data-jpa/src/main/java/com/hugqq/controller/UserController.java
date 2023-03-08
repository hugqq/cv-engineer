package com.hugqq.controller;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;
import com.apifan.common.random.source.InternetSource;
import com.apifan.common.random.source.PersonInfoSource;
import com.google.common.collect.Lists;
import com.hugqq.entity.User;
import com.hugqq.repository.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * User Controller
 */
@RestController
@Slf4j
public class UserController {
    @Autowired
    private UserDao userDao;

    /**
     * 测试保存
     *
     * @return
     */
    @RequestMapping("/save")
    public ResponseEntity<User> save() {
        String salt = IdUtil.fastSimpleUUID();
        User user = User.builder().name(PersonInfoSource.getInstance().randomChineseName())
                .password(SecureUtil.md5("123456" + salt)).salt(salt)
                .email(InternetSource.getInstance().randomEmail(6, "hugqq.com"))
                .phoneNumber(PersonInfoSource.getInstance().randomChineseMobile())
                .status(1)
                .lastLoginTime(new DateTime()).build();
        userDao.save(user);
        User user1 = userDao.findById(user.getId()).orElse(null);
        return ResponseEntity.ok(user1);
    }

    /**
     * 测试删除
     */
    @RequestMapping("/delete")
    public void delete(Long id) {
        long count = userDao.count();
        userDao.findById(id).ifPresent(user -> userDao.delete(user));
        long left = userDao.count();
        log.debug("【count】= {}, 【delete left】= {}", count, left);
    }

    /**
     * 测试修改
     */
    @RequestMapping("/update")
    public void update(User user) {
        userDao.findById(user.getId()).ifPresent(getUser -> {
            getUser.setName(user.getName());
            userDao.save(user);
        });
    }

    /**
     * 测试查询单个
     */
    @RequestMapping("/queryOne")
    public void queryOne(Long id) {
        Optional<User> byId = userDao.findById(id);
        log.debug("【byId】= {}", byId.get());
    }

    /**
     * 测试查询所有
     */
    @RequestMapping("/queryAll")
    public void queryAll() {
        List<User> users = userDao.findAll();
        log.debug("【users】= {}", users);
    }

    /**
     * 测试分页排序查询
     */
    @RequestMapping("/queryPage")
    public void queryPage(@RequestParam(defaultValue = "0") Integer currentPage, @RequestParam(defaultValue = "10") Integer pageSize) {
        // JPA分页的时候起始页是页码减1
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        PageRequest pageRequest = PageRequest.of(currentPage, pageSize, sort);
        Page<User> userPage = userDao.findAll(pageRequest);
        log.debug("【id】= {}", userPage.getContent().stream().map(User::getId).collect(Collectors.toList()));
    }

    /**
     * 初始化20条数据
     */
    @PostConstruct
    @Async
    public void initData() {
        userDao.deleteAll();
        List<User> userList = Lists.newArrayList();
        for (int i = 0; i < 20; i++) {
            String salt = IdUtil.fastSimpleUUID();
            int index = 1 + i;
            String email = (RandomStringUtils.randomAlphanumeric(5, 8) + "@hugqq.com").toLowerCase();
            User user = User.builder().name(PersonInfoSource.getInstance().randomChineseName())
                    .password(SecureUtil.md5("123456" + salt)).salt(salt)
                    .email(email)
                    .phoneNumber(PersonInfoSource.getInstance().randomChineseMobile())
                    .status(1)
                    .lastLoginTime(new DateTime()).build();
            userList.add(user);
        }
        userDao.saveAll(userList);
    }


}
