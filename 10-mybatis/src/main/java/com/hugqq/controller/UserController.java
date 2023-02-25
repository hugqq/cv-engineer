package com.hugqq.controller;

import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;
import com.apifan.common.random.source.PersonInfoSource;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.hugqq.entity.OrmUser;
import com.hugqq.mapper.OrmUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@Slf4j
public class UserController {


    @Autowired
    private OrmUserMapper ormUserMapper;


    @RequestMapping("/initData")
    public void initData() {
        List<OrmUser> userList = Lists.newArrayList();
        for (int i = 0; i < 20; i++) {
            String salt = IdUtil.fastSimpleUUID();
            int index = 1 + i;
            String email = (RandomStringUtils.randomAlphanumeric(5, 8) + "@hugqq.com").toLowerCase();
            OrmUser user = OrmUser.builder().name(PersonInfoSource.getInstance().randomChineseName())
                    .password(SecureUtil.md5("123456" + salt)).salt(salt)
                    .email(email)
                    .phoneNumber(PersonInfoSource.getInstance().randomChineseMobile())
                    .status(1)
                    .createTime(LocalDateTime.now())
                    .lastLoginTime(LocalDateTime.now())
                    .lastUpdateTime(LocalDateTime.now())
                    .build();
            userList.add(user);
        }
        ormUserMapper.deleteAll();
        ormUserMapper.insertBatch(userList);
    }

    /**
     * 测试查询所有 注解拦截限制
     */
    @RequestMapping("/selectAll")
    public ResponseEntity selectAll() {
        List<OrmUser> userList = ormUserMapper.selectAll();
        return ResponseEntity.ok(userList);
    }


    @RequestMapping("/selectAllUser")
    public ResponseEntity selectAllUser() {
        List<OrmUser> userList = ormUserMapper.selectAllUser();
        return ResponseEntity.ok(userList);
    }

    /**
     * 测试查询分页
     */
    @RequestMapping("/selectAllUserByPage")
    public ResponseEntity selectAllUser(Integer currentPage, Integer pageSize, String orderBy) {
        PageHelper.startPage(currentPage, pageSize, orderBy);
        List<OrmUser> userList = ormUserMapper.selectAllUser();
        PageInfo<OrmUser> userPageInfo = new PageInfo<>(userList);
        return ResponseEntity.ok(userPageInfo);
    }

    /**
     * 测试根据主键查询单个
     */
    @RequestMapping("/selectUserById")
    public ResponseEntity selectUserById(Integer id) {
        OrmUser user = ormUserMapper.selectByPrimaryKey(id);
        return ResponseEntity.ok(user);
    }

    /**
     * 测试保存
     */
    @RequestMapping("/saveUser")
    public ResponseEntity saveUser() {
        String salt = IdUtil.fastSimpleUUID();
        OrmUser user = OrmUser.builder().name("testSave3").password(SecureUtil.md5("123456" + salt)).salt(salt).email("testSave3@hugqq.com").phoneNumber("13300000003").status(1).lastLoginTime(LocalDateTime.now()).lastUpdateTime(LocalDateTime.now()).build();
        boolean b = ormUserMapper.insert(user) > 1;
        return ResponseEntity.ok(b);
    }

    /**
     * 测试根据主键删除
     */
    @RequestMapping("/deleteUserById")
    public ResponseEntity deleteById(Integer id) {
        boolean b = ormUserMapper.deleteByPrimaryKey(id) > 1;
        return ResponseEntity.ok(b);
    }

}
