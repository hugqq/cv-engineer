DROP TABLE IF EXISTS `orm_user`;
CREATE TABLE `orm_user` (
  `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '主键',
  `name` VARCHAR(32)   COMMENT '用户名',
  `password` VARCHAR(32)  COMMENT '加密后的密码',
  `salt` VARCHAR(32)  COMMENT '加密使用的盐',
  `email` VARCHAR(32)  COMMENT '邮箱',
  `phone_number` VARCHAR(15)  COMMENT '手机号码',
  `remarks` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `create_by` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_by` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `del_flag` tinyint(1) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Spring Boot Demo Orm 系列示例表';
