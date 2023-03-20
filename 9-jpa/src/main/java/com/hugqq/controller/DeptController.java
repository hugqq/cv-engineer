package com.hugqq.controller;

import cn.hutool.core.lang.Dict;
import com.hugqq.entity.Department;
import com.hugqq.repository.DepartmentDao;
import com.hugqq.repository.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * User Controller
 * </p>
 */
@RestController
@Slf4j
public class DeptController {

    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private UserDao userDao;


    @GetMapping("/dept/queryAll")
    public ResponseEntity queryAll() {

        return ResponseEntity.ok(departmentDao.findAll());
    }


    @GetMapping("/dept/queryByLevel")
    public ResponseEntity queryByLevel(Integer level) {
        return ResponseEntity.ok(departmentDao.findDepartmentsByLevels(level));
    }

}
