INSERT INTO `orm_department`(`id`, `name`, `superior`, `levels`, `order_no`, `create_time`, `last_update_time`) VALUES (1, '部门1', NULL, 0, 0, now(), now());
INSERT INTO `orm_department`(`id`, `name`, `superior`, `levels`, `order_no`, `create_time`, `last_update_time`) VALUES (2, '部门1-2', 1, 1, 0, now(), now());
INSERT INTO `orm_department`(`id`, `name`, `superior`, `levels`, `order_no`, `create_time`, `last_update_time`) VALUES (3, '部门1-3', 1, 1, 0, now(), now());
INSERT INTO `orm_department`(`id`, `name`, `superior`, `levels`, `order_no`, `create_time`, `last_update_time`) VALUES (4, '部门1-2-1', 2, 2, 0, now(), now());



INSERT INTO `orm_user_dept`(`id`, `user_id`, `dept_id`, `create_time`, `last_update_time`) VALUES (1, 1, 1, now(), now());
INSERT INTO `orm_user_dept`(`id`, `user_id`, `dept_id`, `create_time`, `last_update_time`) VALUES (2, 1, 2, now(), now());
INSERT INTO `orm_user_dept`(`id`, `user_id`, `dept_id`, `create_time`, `last_update_time`) VALUES (3, 2, 2, now(), now());
INSERT INTO `orm_user_dept`(`id`, `user_id`, `dept_id`, `create_time`, `last_update_time`) VALUES (4, 2, 3, now(), now());
INSERT INTO `orm_user_dept`(`id`, `user_id`, `dept_id`, `create_time`, `last_update_time`) VALUES (5, 3, 4, now(), now());