ALTER TABLE `tb_rbac_ticket`
MODIFY COLUMN `login_type` varchar(50) NULL DEFAULT NULL COMMENT '登录方式' AFTER `client_type`;