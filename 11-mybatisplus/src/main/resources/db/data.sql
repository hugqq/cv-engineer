INSERT INTO `orm_user` (`id`, `name`, `password`, `salt`, `email`, `phone_number`, `remarks`, `create_by`, `create_date`, `update_by`, `update_date`, `del_flag`) VALUES (1, 'user_1', '1', '1', 'user1@user.com', '17300000001', 'remarks1', 'user1',  now(), 'user1',  now(), 0);
INSERT INTO `orm_user` (`id`, `name`, `password`, `salt`, `email`, `phone_number`, `remarks`, `create_by`, `create_date`, `update_by`, `update_date`, `del_flag`) VALUES (2, 'user_2', '1', '1', 'user2@user.com', '17300000002', 'remarks2', 'user2',  now(), 'user1',  now(), 0);
INSERT INTO `orm_user` (`id`, `name`, `password`, `salt`, `email`, `phone_number`, `remarks`, `create_by`, `create_date`, `update_by`, `update_date`, `del_flag`) VALUES (3, 'user_3', '1', '1', 'user3@user.com', '17300000003', 'remarks3', 'user3',  now(), 'user1',  now(), 1);

INSERT INTO `orm_role`(`id`,`name`) VALUES (1,'管理员'),(2,'普通用户');
