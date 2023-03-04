DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
                          `id` int NOT NULL AUTO_INCREMENT,
                          `name` varchar(255) DEFAULT NULL,
                          `age` int DEFAULT NULL,
                          `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                          `create_time` datetime DEFAULT NULL,
                          `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                          `update_time` datetime DEFAULT NULL,
                          `deleted` varchar(255) DEFAULT NULL,
                          `remark` varchar(255) DEFAULT NULL,
                          PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
