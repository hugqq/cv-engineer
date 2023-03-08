package com.hugqq.service;

import com.hugqq.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class UserService2 {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private TransactionTemplate transactionTemplate;


    public void insert(User user) {
        // 解决方案
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                try {
                    // 在新线程中执行数据库操作
                    jdbcTemplate.execute(insertSql(user));
                    throw new RuntimeException();
                } catch (Exception ex) {
                    // 如果发生异常，则标记事务为回滚
                    status.setRollbackOnly();
                    throw ex;
                }
            }
        });

//        jdbcTemplate.execute(insertSql(user));
    }

    private static String insertSql(User user) {
        return "INSERT INTO `t_user` ( `name`, `age`, `create_by`, `create_time`, `update_by`, `update_time`, `deleted`, `remark` )" +
                " VALUES " +
                " ( '" + user.getName() + "', " + user.getAge() + ", 'sys', now(), 'sys', now(), '0', NULL )";
    }

}
