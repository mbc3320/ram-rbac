CREATE TABLE `tb_admin_permission` (
  `id` bigint(19) NOT NULL COMMENT 'ID',
  `permission_type` tinyint(2) NOT NULL COMMENT '权限类型',
  `permission_code` varchar(100) NOT NULL COMMENT '权限编码',
  `permission_name` varchar(50) DEFAULT NULL COMMENT '权限名称',
  `pid` bigint(19) DEFAULT NULL COMMENT '上级权限ID',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `tb_admin_permission_UN_permission_code` (`permission_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='权限信息';


CREATE TABLE `tb_admin_role` (
  `id` bigint(19) NOT NULL COMMENT 'ID',
  `role_code` varchar(50) NOT NULL COMMENT '角色编码',
  `role_name` varchar(50) NOT NULL COMMENT '角色名称',
  `role_default` tinyint(1) DEFAULT NULL COMMENT '是否默认角色',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `tb_admin_role_UN_role_code` (`role_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色信息';

CREATE TABLE `tb_admin_role_permission` (
  `id` bigint(19) NOT NULL COMMENT 'ID',
  `role_id` bigint(19) NOT NULL COMMENT '角色ID',
  `permission_id` bigint(19) NOT NULL COMMENT '权限ID',
  `permission_type` tinyint(2) NOT NULL COMMENT '权限类型',
  PRIMARY KEY (`id`),
  UNIQUE KEY `tb_admin_role_permission_UN` (`role_id`,`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色权限关联表';

CREATE TABLE `tb_admin_role_user` (
  `id` bigint(19) NOT NULL COMMENT 'ID',
  `role_id` bigint(19) NOT NULL COMMENT '角色ID',
  `user_id` bigint(19) NOT NULL COMMENT '用户ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `tb_admin_role_user_UN` (`role_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色用户关联表';

CREATE TABLE `tb_admin_user` (
  `id` bigint(19) NOT NULL COMMENT 'ID',
  `account` varchar(50) NOT NULL COMMENT '账号',
  `password` varchar(71) DEFAULT NULL COMMENT '密码',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱地址',
  `phone_number` varchar(30) DEFAULT NULL COMMENT '电话号码',
  `nick_name` varchar(50) DEFAULT NULL COMMENT '昵称',
  `account_state` tinyint(2) DEFAULT NULL COMMENT '帐号状态',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `tb_admin_user_UN_account` (`account`),
  UNIQUE KEY `tb_admin_user_UN_email` (`email`),
  UNIQUE KEY `tb_admin_user_UN_phone_number` (`phone_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户信息';