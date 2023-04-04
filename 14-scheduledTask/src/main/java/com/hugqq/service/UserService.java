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
public class UserService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private UserService2 userService2;

    @Transactional(rollbackFor = RuntimeException.class)
    public void insertMultiple(User user1, User user2) {
        // 主体方法加了@Transactional注解 开启默认事务REQUIRED
        // 都回滚
//        insert(user1);
//        insertErr(user2);

        // 主体方法加@Transactional注解 开启默认事务REQUIRED 第二个方法加@Async注解
        // 都回滚
//        insert(user1);
//        insertAsync(user2);

        // 主体方法加了@Transactional注解 开启默认事务REQUIRED 第二个方法加了@Transactional注解和@Async注解
        // 都回滚
//        insert(user1);
//        insertAsyncWithTran(user2);

        // 主体方法加了@Transactional注解 开启默认事务REQUIRED 用了this
        // 都回滚
//        this.insert(user1);
//        this.insertAsync(user2);

        //  主体方法加了@Transactional注解 开启默认事务 SpringUtil.getBean
        //  全部插入成功
//        UserService userService = SpringUtil.getBean(UserService.class);
//        userService.insert(user1);
//        userService.insertAsync(user2);

        //  主体方法加了@Transactional注解  第二个方法加了@Transactional注解 和 @Async注解
        //  第一个插入成功 第二个回滚
//        UserService userService = SpringUtil.getBean(UserService.class);
//        userService.insert(user1);
//        userService.insertAsyncWithTran(user2);

        //  主体方法加了@Transactional注解  第二个方法加了@Transactional注解 事务为Propagation.NOT_SUPPORTED
        //  第一个被事务传播导致回滚 第二个插入成功
//        UserService userService = SpringUtil.getBean(UserService.class);
//        userService.insert(user1);
//        userService.insertErrWithNOTSUPPORTED(user2);

        //  主体方法加了@Transactional注解  第二个方法加了@Transactional注解 事务为Propagation.REQUIRES_NEW
        //  均回滚
//        UserService userService = SpringUtil.getBean(UserService.class);
//        userService.insert(user1);
//        userService.insertErrWithREQUIRESNEW(user2);

        CompletableFuture.runAsync(() -> {
            insert(user1);
        });

        CompletableFuture.runAsync(() -> {
            userService2.insert(user1);
        });

        insertErrWithAsyncSolve(user1);

        CompletableFuture.runAsync(() -> {
            insertErrSolve(user2);
        });

    }

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

    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRES_NEW)
    public void insertErrWithREQUIRESNEW(User user) {
        jdbcTemplate.execute(insertSql(user));
        throw new RuntimeException();
    }


    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.NOT_SUPPORTED)
    public void insertErrWithNOTSUPPORTED(User user) {
        jdbcTemplate.execute(insertSql(user));
        throw new RuntimeException();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void insertErrWithTran(User user) {
        jdbcTemplate.execute(insertSql(user));
        throw new RuntimeException();
    }

    public void insertErr(User user) {
        jdbcTemplate.execute(insertSql(user));
        throw new RuntimeException();
    }


    public void insertErrSolve(User user) {
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
    }

    @Async
    public void insertErrWithAsyncSolve(User user) {
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
    }


    @Async
    public void insertAsync(User user) {
        jdbcTemplate.execute(insertSql(user));
        throw new RuntimeException();
    }

    @Async
    @Transactional(rollbackFor = RuntimeException.class)
    public void insertAsyncWithTran(User user) {
        jdbcTemplate.execute(insertSql(user));
        throw new RuntimeException();
    }

    private static String insertSql(User user) {
        return "INSERT INTO `t_user` ( `name`, `age`, `create_by`, `create_time`, `update_by`, `update_time`, `deleted`, `remark` )" +
                " VALUES " +
                " ( '" + user.getName() + "', " + user.getAge() + ", 'sys', now(), 'sys', now(), '0', NULL )";
    }

}
