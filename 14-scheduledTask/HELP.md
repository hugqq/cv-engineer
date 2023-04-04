多线程事务解决方案 
用transactionTemplate全局
```java
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
```
