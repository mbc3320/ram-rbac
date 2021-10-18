/*
 Navicat Premium Data Transfer

 Source Server         : local-c3-dev
 Source Server Type    : MySQL
 Source Server Version : 50735
 Source Host           : 192.168.137.5:3306
 Source Schema         : ram-rbac

 Target Server Type    : MySQL
 Target Server Version : 50735
 File Encoding         : 65001

 Date: 18/10/2021 14:47:16
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_rbac_config
-- ----------------------------
DROP TABLE IF EXISTS `tb_rbac_config`;
CREATE TABLE `tb_rbac_config`  (
  `id` bigint(19) NOT NULL COMMENT 'id',
  `key_code` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_croatian_ci NULL DEFAULT NULL COMMENT '键名',
  `key_value` text CHARACTER SET utf8mb4 COLLATE utf8mb4_croatian_ci NULL COMMENT '键值',
  `key_desc` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_croatian_ci NULL DEFAULT NULL COMMENT '简介',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_rbac_config_key_code`(`key_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_croatian_ci COMMENT = '系统K/V字典' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_rbac_config
-- ----------------------------
INSERT INTO `tb_rbac_config` VALUES (1447849911735382017, 'test001', 'test001', 'test001', '2021-10-12 17:01:33', NULL);
INSERT INTO `tb_rbac_config` VALUES (1447849939019329537, 'test002', 'test002', 'test002', '2021-10-12 17:01:40', NULL);
INSERT INTO `tb_rbac_config` VALUES (1447849964101267458, 'test003', 'test003', 'test003', '2021-10-12 17:01:46', NULL);
INSERT INTO `tb_rbac_config` VALUES (1447849987484512258, 'test004', 'test004', 'test004', '2021-10-12 17:01:51', NULL);
INSERT INTO `tb_rbac_config` VALUES (1447850012188962817, 'test005', 'test005', 'test005', '2021-10-12 17:01:57', NULL);
INSERT INTO `tb_rbac_config` VALUES (1447850055910387714, 'test006', 'test006', 'test006', '2021-10-12 17:02:08', NULL);
INSERT INTO `tb_rbac_config` VALUES (1447850084284854274, 'test007', 'test007', 'test007', '2021-10-12 17:02:15', NULL);
INSERT INTO `tb_rbac_config` VALUES (1447851468275142657, 'test008', 'test008', 'test008', '2021-10-12 17:07:44', NULL);
INSERT INTO `tb_rbac_config` VALUES (1447851797305708546, 'test009', 'test009', 'test009', '2021-10-12 17:09:03', NULL);
INSERT INTO `tb_rbac_config` VALUES (1447851826070245378, 'test010', 'test010', 'test010', '2021-10-12 17:09:10', NULL);
INSERT INTO `tb_rbac_config` VALUES (1447851861977681921, 'test011', 'test011', 'test011', '2021-10-12 17:09:18', NULL);
INSERT INTO `tb_rbac_config` VALUES (1449972865457790977, '_system_global_config', '{\"consoleCaptcha\":false,\"captchaType\":null,\"loginServiceMetaList\":[{\"loginType\":\"normalLogin\",\"typeName\":\"账号密码\",\"enable\":true,\"loginFactoryServiceName\":\"normalLoginFactory\"}],\"ticketTimeout\":120,\"passwordErrorExpireTime\":5}', NULL, '2021-10-18 13:37:25', NULL);

-- ----------------------------
-- Table structure for tb_rbac_permission
-- ----------------------------
DROP TABLE IF EXISTS `tb_rbac_permission`;
CREATE TABLE `tb_rbac_permission`  (
  `id` bigint(19) NOT NULL COMMENT 'ID',
  `permission_type` tinyint(2) NOT NULL COMMENT '权限类型',
  `permission_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限编码',
  `permission_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限名称',
  `pid` bigint(19) NULL DEFAULT NULL COMMENT '上级权限ID',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_rbac_permission_permission_code`(`permission_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '权限信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_rbac_permission
-- ----------------------------
INSERT INTO `tb_rbac_permission` VALUES (1447415327718174721, 0, 'api', '系统接口', NULL, '2021-10-11 12:14:40', NULL);
INSERT INTO `tb_rbac_permission` VALUES (1447415378427310082, 1, 'menu', '系统菜单', NULL, '2021-10-11 12:14:53', NULL);
INSERT INTO `tb_rbac_permission` VALUES (1447415420823334914, 2, 'fun', '系统功能', NULL, '2021-10-11 12:15:03', NULL);
INSERT INTO `tb_rbac_permission` VALUES (1447415739108093954, 0, 'api_rbac', 'RBAC模块', 1447415327718174721, '2021-10-11 12:16:19', NULL);
INSERT INTO `tb_rbac_permission` VALUES (1447415824789336065, 0, 'api_rbac_permission', '权限管理接口', 1447415739108093954, '2021-10-11 12:16:39', NULL);
INSERT INTO `tb_rbac_permission` VALUES (1447415882276466690, 0, 'api_rbac_permission_save', '保存', 1447415824789336065, '2021-10-11 12:16:53', NULL);
INSERT INTO `tb_rbac_permission` VALUES (1447417445673934850, 0, 'api_rbac_permission_updateById', '通过ID更新', 1447415824789336065, '2021-10-11 12:23:05', NULL);
INSERT INTO `tb_rbac_permission` VALUES (1447417505585373186, 0, 'api_rbac_permission_getById', '通过ID查询', 1447415824789336065, '2021-10-11 12:23:20', NULL);
INSERT INTO `tb_rbac_permission` VALUES (1447417559796752385, 0, 'api_rbac_permission_removeById', '通过ID删除', 1447415824789336065, '2021-10-11 12:23:33', NULL);
INSERT INTO `tb_rbac_permission` VALUES (1447417621125865474, 0, 'api_rbac_permission_page', '分页查询', 1447415824789336065, '2021-10-11 12:23:47', NULL);
INSERT INTO `tb_rbac_permission` VALUES (1447417801950699522, 0, 'api_rbac_permission_simpleTree', '权限树列表', 1447415824789336065, '2021-10-11 12:24:30', NULL);
INSERT INTO `tb_rbac_permission` VALUES (1447477451698061313, 0, 'api_rbac_config', '系统字典管理接口', 1447415739108093954, '2021-10-11 16:21:32', NULL);
INSERT INTO `tb_rbac_permission` VALUES (1447477496644222977, 0, 'api_rbac_config_save', '保存', 1447477451698061313, '2021-10-11 16:21:43', NULL);
INSERT INTO `tb_rbac_permission` VALUES (1447477553334435841, 0, 'api_rbac_config_updateById', '通过ID更新', 1447477451698061313, '2021-10-11 16:21:56', NULL);
INSERT INTO `tb_rbac_permission` VALUES (1447477621139554306, 0, 'api_rbac_config_getById', '通过ID查询', 1447477451698061313, '2021-10-11 16:22:12', NULL);
INSERT INTO `tb_rbac_permission` VALUES (1447477677972373506, 0, 'api_rbac_config_removeById', '通过ID删除', 1447477451698061313, '2021-10-11 16:22:26', NULL);
INSERT INTO `tb_rbac_permission` VALUES (1447477733291048961, 0, 'api_rbac_config_page', '分页查询', 1447477451698061313, '2021-10-11 16:22:39', NULL);
INSERT INTO `tb_rbac_permission` VALUES (1447477824684933121, 0, 'api_rbac_config_getGlobalConfig', '获取系统全局设置', 1447477451698061313, '2021-10-11 16:23:01', NULL);
INSERT INTO `tb_rbac_permission` VALUES (1447477919711084546, 0, 'api_rbac_config_saveOrUpdateGlobalConfig', '保存/更新系统全局设置', 1447477451698061313, '2021-10-11 16:23:24', NULL);
INSERT INTO `tb_rbac_permission` VALUES (1447478760971026434, 0, 'api_rbac_role', '角色管理接口', 1447415739108093954, '2021-10-11 16:26:44', NULL);
INSERT INTO `tb_rbac_permission` VALUES (1447478849940602882, 0, 'api_rbac_role_save', '保存', 1447478760971026434, '2021-10-11 16:27:05', NULL);
INSERT INTO `tb_rbac_permission` VALUES (1447478908543418370, 0, 'api_rbac_role_updateById', '通过ID更新', 1447478760971026434, '2021-10-11 16:27:19', NULL);
INSERT INTO `tb_rbac_permission` VALUES (1447478967121068033, 0, 'api_rbac_role_removeById', '通过ID删除', 1447478760971026434, '2021-10-11 16:27:33', NULL);
INSERT INTO `tb_rbac_permission` VALUES (1447479126076801025, 0, 'api_rbac_role_getById', '通过ID查询', 1447478760971026434, '2021-10-11 16:28:11', NULL);
INSERT INTO `tb_rbac_permission` VALUES (1447479240396750850, 0, 'api_rbac_role_page', '分页查询', 1447478760971026434, '2021-10-11 16:28:38', NULL);
INSERT INTO `tb_rbac_permission` VALUES (1447479351721967617, 0, 'api_rbac_role_grant', '授权用户角色', 1447478760971026434, '2021-10-11 16:29:05', NULL);
INSERT INTO `tb_rbac_permission` VALUES (1447479513693405186, 0, 'api_rbac_role_revoke', '撤销用户角色授权', 1447478760971026434, '2021-10-11 16:29:44', NULL);
INSERT INTO `tb_rbac_permission` VALUES (1447479749258104833, 0, 'api_rbac_role_permissionGrant', '角色权限授权', 1447478760971026434, '2021-10-11 16:30:39', NULL);
INSERT INTO `tb_rbac_permission` VALUES (1447479822549372929, 0, 'api_rbac_role_findAuthPermissionTree', '查询权限树', 1447478760971026434, '2021-10-11 16:30:57', NULL);
INSERT INTO `tb_rbac_permission` VALUES (1447479961854791682, 0, 'api_rbac_role_authRolePage', '角色分页查询-用户授权', 1447478760971026434, '2021-10-11 16:31:30', NULL);
INSERT INTO `tb_rbac_permission` VALUES (1447480109431377921, 0, 'api_rbac_ticket', '凭证信息管理接口', 1447415739108093954, '2021-10-11 16:32:06', NULL);
INSERT INTO `tb_rbac_permission` VALUES (1447480179216207874, 0, 'api_rbac_ticket_page', '分页查询', 1447480109431377921, '2021-10-11 16:32:22', NULL);
INSERT INTO `tb_rbac_permission` VALUES (1447480258484359169, 0, 'api_rbac_user', '用户管理接口', 1447415739108093954, '2021-10-11 16:32:41', NULL);
INSERT INTO `tb_rbac_permission` VALUES (1447480321784795137, 0, 'api_rbac_user_save', '保存', 1447480258484359169, '2021-10-11 16:32:56', NULL);
INSERT INTO `tb_rbac_permission` VALUES (1447480395105423362, 0, 'api_rbac_user_updateById', '通过ID更新', 1447480258484359169, '2021-10-11 16:33:14', NULL);
INSERT INTO `tb_rbac_permission` VALUES (1447480444694679553, 0, 'api_rbac_user_getById', '通过ID查询', 1447480258484359169, '2021-10-11 16:33:26', NULL);
INSERT INTO `tb_rbac_permission` VALUES (1447480514018136066, 0, 'api_rbac_user_removeById', '通过ID删除', 1447480258484359169, '2021-10-11 16:33:42', NULL);
INSERT INTO `tb_rbac_permission` VALUES (1447480562214883330, 0, 'api_rbac_user_page', '分页查询', 1447480258484359169, '2021-10-11 16:33:54', NULL);
INSERT INTO `tb_rbac_permission` VALUES (1447821238722490369, 1, 'menu_system', '系统设置', 1447415378427310082, '2021-10-12 15:07:37', NULL);
INSERT INTO `tb_rbac_permission` VALUES (1447821335875153921, 1, 'menu_system_config', '系统KV字典', 1447821238722490369, '2021-10-12 15:08:00', NULL);
INSERT INTO `tb_rbac_permission` VALUES (1447821448047620097, 1, 'menu_system_permission', '权限管理', 1447821238722490369, '2021-10-12 15:08:27', NULL);
INSERT INTO `tb_rbac_permission` VALUES (1447821500816158722, 1, 'menu_system_role', '角色管理', 1447821238722490369, '2021-10-12 15:08:40', NULL);
INSERT INTO `tb_rbac_permission` VALUES (1447821558030659586, 1, 'menu_system_user', '用户管理', 1447821238722490369, '2021-10-12 15:08:53', NULL);
INSERT INTO `tb_rbac_permission` VALUES (1448243871289024514, 2, 'fun_system', '系统设置功能', 1447415420823334914, '2021-10-13 19:07:01', NULL);
INSERT INTO `tb_rbac_permission` VALUES (1448243959918862338, 2, 'fun_system_config', 'KV字典管理功能', 1448243871289024514, '2021-10-13 19:07:22', NULL);
INSERT INTO `tb_rbac_permission` VALUES (1448244024704081921, 2, 'fun_system_config_add', '新增', 1448243959918862338, '2021-10-13 19:07:37', NULL);
INSERT INTO `tb_rbac_permission` VALUES (1448244072342986753, 2, 'fun_system_config_edit', '编辑', 1448243959918862338, '2021-10-13 19:07:49', NULL);
INSERT INTO `tb_rbac_permission` VALUES (1448244118643908610, 2, 'fun_system_config_remove', '删除', 1448243959918862338, '2021-10-13 19:08:00', NULL);
INSERT INTO `tb_rbac_permission` VALUES (1448244186168008706, 2, 'fun_system_permission', '权限管理功能', 1448243871289024514, '2021-10-13 19:08:16', NULL);
INSERT INTO `tb_rbac_permission` VALUES (1448244243147628545, 2, 'fun_system_permission_add', '新增', 1448244186168008706, '2021-10-13 19:08:29', NULL);
INSERT INTO `tb_rbac_permission` VALUES (1448244291147243522, 2, 'fun_system_permission_edit', '编辑', 1448244186168008706, '2021-10-13 19:08:41', NULL);
INSERT INTO `tb_rbac_permission` VALUES (1448244332662464514, 2, 'fun_system_permission_remove', '删除', 1448244186168008706, '2021-10-13 19:08:51', NULL);
INSERT INTO `tb_rbac_permission` VALUES (1448244408759721986, 2, 'fun_system_role', '角色管理功能', 1448243871289024514, '2021-10-13 19:09:09', NULL);
INSERT INTO `tb_rbac_permission` VALUES (1448244446688813058, 2, 'fun_system_role_add', '新增', 1448244408759721986, '2021-10-13 19:09:18', NULL);
INSERT INTO `tb_rbac_permission` VALUES (1448244487054794753, 2, 'fun_system_role_edit', '编辑', 1448244408759721986, '2021-10-13 19:09:27', NULL);
INSERT INTO `tb_rbac_permission` VALUES (1448244528901365762, 2, 'fun_system_role_remove', '删除', 1448244408759721986, '2021-10-13 19:09:37', NULL);
INSERT INTO `tb_rbac_permission` VALUES (1448244583133716482, 2, 'fun_system_role_grant', '授权', 1448244408759721986, '2021-10-13 19:09:50', NULL);
INSERT INTO `tb_rbac_permission` VALUES (1448245557336317953, 2, 'fun_system_user', '用户管理功能', 1448243871289024514, '2021-10-13 19:13:43', NULL);
INSERT INTO `tb_rbac_permission` VALUES (1448245592274870273, 2, 'fun_system_user_add', '新增', 1448245557336317953, '2021-10-13 19:13:51', NULL);
INSERT INTO `tb_rbac_permission` VALUES (1448245637015511041, 2, 'fun_system_user_edit', '编辑', 1448245557336317953, '2021-10-13 19:14:02', NULL);
INSERT INTO `tb_rbac_permission` VALUES (1448245683270295554, 2, 'fun_system_user_remove', '删除', 1448245557336317953, '2021-10-13 19:14:13', NULL);
INSERT INTO `tb_rbac_permission` VALUES (1448245721820143618, 2, 'fun_system_user_grant', '授权', 1448245557336317953, '2021-10-13 19:14:22', NULL);
INSERT INTO `tb_rbac_permission` VALUES (1448578197264289793, 1, 'menu_system_settings', '全局设定', 1447821238722490369, '2021-10-14 17:15:30', NULL);
INSERT INTO `tb_rbac_permission` VALUES (1448578252218060801, 1, 'menu_system_ticket', '凭证管理', 1447821238722490369, '2021-10-14 17:15:43', NULL);
INSERT INTO `tb_rbac_permission` VALUES (1448590575657885698, 0, 'api_rbac_ticket_kickOutById', '踢出Ticket', 1447480109431377921, '2021-10-14 18:04:41', NULL);
INSERT INTO `tb_rbac_permission` VALUES (1448590655077031938, 2, 'fun_system_ticket', '用户凭证管理功能', 1448243871289024514, '2021-10-14 18:05:00', NULL);
INSERT INTO `tb_rbac_permission` VALUES (1448590707392585729, 2, 'fun_system_ticket_kick', '踢出Ticket', 1448590655077031938, '2021-10-14 18:05:13', NULL);
INSERT INTO `tb_rbac_permission` VALUES (1448630681601073154, 0, 'api_rbac_user_changeUserPassword', '修改用户密码', 1447480258484359169, '2021-10-14 20:44:03', NULL);

-- ----------------------------
-- Table structure for tb_rbac_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_rbac_role`;
CREATE TABLE `tb_rbac_role`  (
  `id` bigint(19) NOT NULL COMMENT 'ID',
  `role_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色编码',
  `role_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名称',
  `role_default` tinyint(1) NULL DEFAULT NULL COMMENT '是否默认角色',
  `role_desc` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色介绍',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_rbac_role_role_code`(`role_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_rbac_role
-- ----------------------------
INSERT INTO `tb_rbac_role` VALUES (1447481336877666305, 'system_test001', '测试角色001', 1, 'test', '2021-10-11 16:36:58', '2021-10-11 17:15:50');
INSERT INTO `tb_rbac_role` VALUES (1447845022078693378, 'system_test002', '测试角色002', 0, '测试角色002', '2021-10-12 16:42:08', NULL);
INSERT INTO `tb_rbac_role` VALUES (1447845080501153793, 'system_test003', '测试角色003', 0, '测试角色003', '2021-10-12 16:42:22', NULL);
INSERT INTO `tb_rbac_role` VALUES (1447845113770373121, 'test004', 'test004', 0, 'test004', '2021-10-12 16:42:29', NULL);
INSERT INTO `tb_rbac_role` VALUES (1447845151917568001, 'test005', 'test005', 0, 'test005', '2021-10-12 16:42:39', NULL);
INSERT INTO `tb_rbac_role` VALUES (1447845202920304642, 'test006', 'test006', 0, 'test006', '2021-10-12 16:42:51', NULL);
INSERT INTO `tb_rbac_role` VALUES (1447845232578228226, 'test007', 'test007', 0, 'test007', '2021-10-12 16:42:58', NULL);
INSERT INTO `tb_rbac_role` VALUES (1447845264899534850, 'test008', 'test008', 0, 'test008', '2021-10-12 16:43:05', NULL);
INSERT INTO `tb_rbac_role` VALUES (1447845302593744898, 'test009', 'test009', 0, 'test009', '2021-10-12 16:43:14', NULL);
INSERT INTO `tb_rbac_role` VALUES (1447847048145629186, 'test010', 'test010', 0, 'test010', '2021-10-12 16:50:11', NULL);
INSERT INTO `tb_rbac_role` VALUES (1447847308913897473, 'test011', 'test011', 0, 'test011', '2021-10-12 16:51:13', NULL);
INSERT INTO `tb_rbac_role` VALUES (1448243470309371905, 'system_admin', '系统管理员', 0, '拥有管理系统基础数据的权限', '2021-10-13 19:05:25', NULL);

-- ----------------------------
-- Table structure for tb_rbac_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `tb_rbac_role_permission`;
CREATE TABLE `tb_rbac_role_permission`  (
  `id` bigint(19) NOT NULL COMMENT 'ID',
  `role_id` bigint(19) NOT NULL COMMENT '角色ID',
  `permission_id` bigint(19) NOT NULL COMMENT '权限ID',
  `permission_type` tinyint(2) NOT NULL COMMENT '权限类型',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_rbac_rp_role_id_permission_id`(`role_id`, `permission_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色权限关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_rbac_role_permission
-- ----------------------------
INSERT INTO `tb_rbac_role_permission` VALUES (1448579707117260802, 1448243470309371905, 1447415378427310082, 1, '2021-10-14 17:21:30', NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1448579707117260803, 1448243470309371905, 1447821238722490369, 1, '2021-10-14 17:21:30', NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1448579707117260804, 1448243470309371905, 1447821335875153921, 1, '2021-10-14 17:21:30', NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1448579707117260805, 1448243470309371905, 1447821448047620097, 1, '2021-10-14 17:21:30', NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1448579707117260806, 1448243470309371905, 1447821500816158722, 1, '2021-10-14 17:21:30', NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1448579707117260807, 1448243470309371905, 1447821558030659586, 1, '2021-10-14 17:21:30', NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1448579707117260808, 1448243470309371905, 1448578197264289793, 1, '2021-10-14 17:21:30', NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1448579707117260809, 1448243470309371905, 1448578252218060801, 1, '2021-10-14 17:21:30', NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1448591465626279938, 1448243470309371905, 1448244024704081921, 2, '2021-10-14 18:08:14', NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1448591465626279939, 1448243470309371905, 1448244072342986753, 2, '2021-10-14 18:08:14', NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1448591465626279940, 1448243470309371905, 1448244118643908610, 2, '2021-10-14 18:08:14', NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1448591465626279941, 1448243470309371905, 1448244243147628545, 2, '2021-10-14 18:08:14', NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1448591465626279942, 1448243470309371905, 1448244291147243522, 2, '2021-10-14 18:08:14', NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1448591465626279943, 1448243470309371905, 1448244332662464514, 2, '2021-10-14 18:08:14', NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1448591465626279944, 1448243470309371905, 1448244446688813058, 2, '2021-10-14 18:08:14', NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1448591465626279945, 1448243470309371905, 1448244487054794753, 2, '2021-10-14 18:08:14', NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1448591465626279946, 1448243470309371905, 1448244528901365762, 2, '2021-10-14 18:08:14', NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1448591465626279947, 1448243470309371905, 1448244583133716482, 2, '2021-10-14 18:08:14', NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1448591465626279948, 1448243470309371905, 1448245592274870273, 2, '2021-10-14 18:08:14', NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1448591465626279949, 1448243470309371905, 1448245637015511041, 2, '2021-10-14 18:08:14', NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1448591465626279950, 1448243470309371905, 1448245683270295554, 2, '2021-10-14 18:08:14', NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1448591465626279951, 1448243470309371905, 1448245721820143618, 2, '2021-10-14 18:08:14', NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1448591465626279952, 1448243470309371905, 1448590707392585729, 2, '2021-10-14 18:08:14', NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1448630736722616322, 1448243470309371905, 1447415327718174721, 0, '2021-10-14 20:44:17', NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1448630736722616323, 1448243470309371905, 1447415739108093954, 0, '2021-10-14 20:44:17', NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1448630736722616324, 1448243470309371905, 1447415824789336065, 0, '2021-10-14 20:44:17', NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1448630736722616325, 1448243470309371905, 1447415882276466690, 0, '2021-10-14 20:44:17', NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1448630736722616326, 1448243470309371905, 1447417445673934850, 0, '2021-10-14 20:44:17', NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1448630736722616327, 1448243470309371905, 1447417505585373186, 0, '2021-10-14 20:44:17', NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1448630736722616328, 1448243470309371905, 1447417559796752385, 0, '2021-10-14 20:44:17', NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1448630736722616329, 1448243470309371905, 1447417621125865474, 0, '2021-10-14 20:44:17', NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1448630736722616330, 1448243470309371905, 1447417801950699522, 0, '2021-10-14 20:44:17', NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1448630736722616331, 1448243470309371905, 1447477451698061313, 0, '2021-10-14 20:44:17', NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1448630736722616332, 1448243470309371905, 1447477496644222977, 0, '2021-10-14 20:44:17', NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1448630736789725185, 1448243470309371905, 1447477553334435841, 0, '2021-10-14 20:44:17', NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1448630736789725186, 1448243470309371905, 1447477621139554306, 0, '2021-10-14 20:44:17', NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1448630736789725187, 1448243470309371905, 1447477677972373506, 0, '2021-10-14 20:44:17', NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1448630736789725188, 1448243470309371905, 1447477733291048961, 0, '2021-10-14 20:44:17', NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1448630736789725189, 1448243470309371905, 1447477824684933121, 0, '2021-10-14 20:44:17', NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1448630736789725190, 1448243470309371905, 1447477919711084546, 0, '2021-10-14 20:44:17', NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1448630736789725191, 1448243470309371905, 1447478760971026434, 0, '2021-10-14 20:44:17', NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1448630736789725192, 1448243470309371905, 1447478849940602882, 0, '2021-10-14 20:44:17', NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1448630736789725193, 1448243470309371905, 1447478908543418370, 0, '2021-10-14 20:44:17', NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1448630736789725194, 1448243470309371905, 1447478967121068033, 0, '2021-10-14 20:44:17', NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1448630736789725195, 1448243470309371905, 1447479126076801025, 0, '2021-10-14 20:44:17', NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1448630736789725196, 1448243470309371905, 1447479240396750850, 0, '2021-10-14 20:44:17', NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1448630736789725197, 1448243470309371905, 1447479351721967617, 0, '2021-10-14 20:44:17', NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1448630736789725198, 1448243470309371905, 1447479513693405186, 0, '2021-10-14 20:44:17', NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1448630736789725199, 1448243470309371905, 1447479749258104833, 0, '2021-10-14 20:44:17', NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1448630736789725200, 1448243470309371905, 1447479822549372929, 0, '2021-10-14 20:44:17', NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1448630736789725201, 1448243470309371905, 1447479961854791682, 0, '2021-10-14 20:44:17', NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1448630736789725202, 1448243470309371905, 1447480109431377921, 0, '2021-10-14 20:44:17', NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1448630736789725203, 1448243470309371905, 1447480179216207874, 0, '2021-10-14 20:44:17', NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1448630736789725204, 1448243470309371905, 1448590575657885698, 0, '2021-10-14 20:44:17', NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1448630736789725205, 1448243470309371905, 1447480258484359169, 0, '2021-10-14 20:44:17', NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1448630736789725206, 1448243470309371905, 1447480321784795137, 0, '2021-10-14 20:44:17', NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1448630736789725207, 1448243470309371905, 1447480395105423362, 0, '2021-10-14 20:44:17', NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1448630736789725208, 1448243470309371905, 1447480444694679553, 0, '2021-10-14 20:44:17', NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1448630736789725209, 1448243470309371905, 1447480514018136066, 0, '2021-10-14 20:44:17', NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1448630736789725210, 1448243470309371905, 1447480562214883330, 0, '2021-10-14 20:44:17', NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1448630736856834050, 1448243470309371905, 1448630681601073154, 0, '2021-10-14 20:44:17', NULL);

-- ----------------------------
-- Table structure for tb_rbac_role_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_rbac_role_user`;
CREATE TABLE `tb_rbac_role_user`  (
  `id` bigint(19) NOT NULL COMMENT 'ID',
  `role_id` bigint(19) NOT NULL COMMENT '角色ID',
  `user_id` bigint(19) NOT NULL COMMENT '用户ID',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_rbac_ru_role_id_user_id`(`role_id`, `user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色用户关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_rbac_role_user
-- ----------------------------
INSERT INTO `tb_rbac_role_user` VALUES (1448243470900768770, 1448243470309371905, 1448243466811322369, '2021-10-13 19:05:25', NULL);

-- ----------------------------
-- Table structure for tb_rbac_ticket
-- ----------------------------
DROP TABLE IF EXISTS `tb_rbac_ticket`;
CREATE TABLE `tb_rbac_ticket`  (
  `id` bigint(19) NOT NULL COMMENT 'id',
  `user_from` tinyint(2) NULL DEFAULT NULL COMMENT '用户来源',
  `user_id` bigint(19) NULL DEFAULT NULL COMMENT '用户ID',
  `ticket` varchar(38) CHARACTER SET utf8mb4 COLLATE utf8mb4_croatian_ci NULL DEFAULT NULL COMMENT '凭证',
  `client_type` tinyint(2) NULL DEFAULT NULL COMMENT '客户端类型',
  `login_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_croatian_ci NULL DEFAULT NULL COMMENT '登录方式',
  `ip_address` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_croatian_ci NULL DEFAULT NULL COMMENT 'ip地址',
  `user_agent` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_croatian_ci NULL DEFAULT NULL COMMENT '用户UA',
  `account` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_croatian_ci NULL DEFAULT NULL COMMENT '账号',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_croatian_ci NULL DEFAULT NULL COMMENT 'email',
  `phone_number` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_croatian_ci NULL DEFAULT NULL COMMENT '电话号码',
  `nick_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_croatian_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `last_refresh_time` timestamp(0) NULL DEFAULT NULL COMMENT '上次刷新时间',
  `time_expire` timestamp(0) NULL DEFAULT NULL COMMENT '预估失效时间',
  `client_hash` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_croatian_ci NULL DEFAULT NULL COMMENT '客户端hash标识',
  `client_info` text CHARACTER SET utf8mb4 COLLATE utf8mb4_croatian_ci NULL COMMENT '客户端信息',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_rbac_ticket_ticket`(`ticket`) USING BTREE,
  INDEX `idx_rbac_ticket_user_id`(`user_id`) USING BTREE,
  INDEX `idx_rbac_ticket_account`(`account`) USING BTREE,
  INDEX `idx_rbac_ticket_email`(`email`) USING BTREE,
  INDEX `idx_rbac_ticket_phone_number`(`phone_number`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_croatian_ci COMMENT = '用户凭证信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_rbac_ticket
-- ----------------------------
INSERT INTO `tb_rbac_ticket` VALUES (1446754256804126721, NULL, 123456, '05aead9876d04f4abb9e43a5a3497530', 0, '0', '127.0.0.1', 'Chrome 88', 'junit_test', 'junit_test@hotmail.com', '13800138000', 'Junit Test', '2021-10-09 16:27:49', '2021-10-09 16:27:50', NULL, NULL, '2021-10-09 16:27:49', NULL);
INSERT INTO `tb_rbac_ticket` VALUES (1446754261325586434, NULL, 123456, '107467e335324481a9c502576b82094b', 0, '0', '127.0.0.1', 'Chrome 88', 'junit_test', 'junit_test@hotmail.com', '13800138000', 'Junit Test', '2021-10-09 16:27:50', '2021-10-09 16:27:50', NULL, NULL, '2021-10-09 16:27:50', NULL);
INSERT INTO `tb_rbac_ticket` VALUES (1446776265617453058, NULL, 1446461532242776066, '23fee70cca6149909335f64f12f5ae5d', NULL, '0', NULL, NULL, 'admin', 'mobinchao@hotmail.com', NULL, 'BeanShell.More', '2021-10-09 17:55:15', '2021-10-09 19:55:15', NULL, NULL, '2021-10-09 17:55:15', NULL);
INSERT INTO `tb_rbac_ticket` VALUES (1446927674950336513, NULL, 1446461532242776066, '00ade2fe99504bc696a502b100da181e', NULL, '0', NULL, NULL, 'admin', 'mobinchao@hotmail.com', NULL, 'BeanShell.More', '2021-10-10 03:56:55', '2021-10-10 05:56:55', NULL, NULL, '2021-10-10 03:56:55', NULL);
INSERT INTO `tb_rbac_ticket` VALUES (1447070038973005826, NULL, 1446461532242776066, '3144e78698ff43af95575c72227fc122', NULL, '0', NULL, NULL, 'admin', 'mobinchao@hotmail.com', NULL, 'BeanShell.More', '2021-10-10 13:22:37', '2021-10-10 15:22:37', NULL, NULL, '2021-10-10 13:22:37', NULL);
INSERT INTO `tb_rbac_ticket` VALUES (1447071388536455169, NULL, 1446461532242776066, '0debea34c2ac4421be81fe43f81b4a58', NULL, '0', NULL, NULL, 'admin', 'mobinchao@hotmail.com', NULL, 'BeanShell.More', '2021-10-10 13:27:59', '2021-10-10 15:27:59', NULL, NULL, '2021-10-10 13:27:59', NULL);
INSERT INTO `tb_rbac_ticket` VALUES (1447124220195143681, NULL, 1446461532242776066, 'f8c9529b9fb14f2585e07bedaef8c31f', NULL, '0', NULL, NULL, 'admin', 'mobinchao@hotmail.com', NULL, 'BeanShell.More', '2021-10-10 16:57:54', '2021-10-10 18:57:54', NULL, NULL, '2021-10-10 16:57:54', NULL);
INSERT INTO `tb_rbac_ticket` VALUES (1447200919280713730, NULL, 1446461532242776066, '6dd45b810e6547f595979eac10b522dd', NULL, '0', NULL, NULL, 'admin', 'mobinchao@hotmail.com', NULL, 'BeanShell.More', '2021-10-10 22:02:42', '2021-10-10 23:24:57', NULL, NULL, '2021-10-10 22:02:42', NULL);
INSERT INTO `tb_rbac_ticket` VALUES (1447221870550667266, NULL, 1446461532242776066, '0c6d04786df943c99e9a268e4361593b', NULL, '0', NULL, NULL, 'admin', 'mobinchao@hotmail.com', NULL, 'BeanShell.More', '2021-10-10 23:25:55', '2021-10-10 23:27:52', NULL, NULL, '2021-10-10 23:25:55', NULL);
INSERT INTO `tb_rbac_ticket` VALUES (1447222396893831170, NULL, 1446461532242776066, '3989b36f905b46809682c9dc04ed5317', NULL, '0', NULL, NULL, 'admin', 'mobinchao@hotmail.com', NULL, 'BeanShell.More', '2021-10-10 23:28:02', '2021-10-10 23:31:02', NULL, NULL, '2021-10-10 23:28:02', NULL);
INSERT INTO `tb_rbac_ticket` VALUES (1447223173225992193, NULL, 1446461532242776066, '1d0d80c07b2b4f94bdf949ffbb7e1070', NULL, '0', NULL, NULL, 'admin', 'mobinchao@hotmail.com', NULL, 'BeanShell.More', '2021-10-10 23:31:07', '2021-10-11 01:31:07', NULL, NULL, '2021-10-10 23:31:07', NULL);
INSERT INTO `tb_rbac_ticket` VALUES (1447224807423221762, NULL, 1446461532242776066, '32181eecc89b43e6b76590d3b481f5e3', NULL, '0', NULL, NULL, 'admin', 'mobinchao@hotmail.com', NULL, 'BeanShell.More', '2021-10-10 23:37:37', '2021-10-11 01:37:37', NULL, NULL, '2021-10-10 23:37:37', NULL);
INSERT INTO `tb_rbac_ticket` VALUES (1447225400757919746, NULL, 1446461532242776066, 'd673edeaff82434395427934e9fd4b01', NULL, '0', NULL, NULL, 'admin', 'mobinchao@hotmail.com', NULL, 'BeanShell.More', '2021-10-10 23:39:58', '2021-10-10 23:47:08', NULL, NULL, '2021-10-10 23:39:58', NULL);
INSERT INTO `tb_rbac_ticket` VALUES (1447227234604105729, NULL, 1446461532242776066, 'edc23b9d022542108ad1832e2e56103f', NULL, '0', NULL, NULL, 'admin', 'mobinchao@hotmail.com', NULL, 'BeanShell.More', '2021-10-10 23:47:15', '2021-10-11 01:47:15', NULL, NULL, '2021-10-10 23:47:15', NULL);
INSERT INTO `tb_rbac_ticket` VALUES (1447385801420247042, NULL, 1446461532242776066, 'cd4cfeb5918d4f789a127d496fa00b94', NULL, '0', NULL, NULL, 'admin', 'mobinchao@hotmail.com', NULL, 'BeanShell.More', '2021-10-11 10:17:21', '2021-10-11 12:17:21', NULL, NULL, '2021-10-11 10:17:21', NULL);
INSERT INTO `tb_rbac_ticket` VALUES (1447475443234619394, NULL, 1446461532242776066, '3aa6e207c7ae4b53af20e5894d563de7', NULL, '0', NULL, NULL, 'admin', 'mobinchao@hotmail.com', NULL, 'BeanShell.More', '2021-10-11 16:13:33', '2021-10-11 18:13:33', NULL, NULL, '2021-10-11 16:13:33', NULL);
INSERT INTO `tb_rbac_ticket` VALUES (1447506928998121474, NULL, 1446461532242776066, 'e47c209217bd443dac42206ef8055e9a', NULL, '0', NULL, NULL, 'admin', 'mobinchao@hotmail.com', NULL, 'BeanShell.More', '2021-10-11 18:18:40', '2021-10-11 20:18:40', NULL, NULL, '2021-10-11 18:18:40', NULL);
INSERT INTO `tb_rbac_ticket` VALUES (1447573949517496321, NULL, 1446461532242776066, '7a799489209a411a8834e7e619fcfb0c', NULL, '0', NULL, NULL, 'admin', 'mobinchao@hotmail.com', NULL, 'BeanShell.More', '2021-10-11 22:44:59', '2021-10-12 00:44:59', NULL, NULL, '2021-10-11 22:44:59', NULL);
INSERT INTO `tb_rbac_ticket` VALUES (1447760202863026177, NULL, 1446461532242776066, 'e861427bc355417f8dfbc391ccf62096', NULL, '0', NULL, NULL, 'admin', 'mobinchao@hotmail.com', NULL, 'BeanShell.More', '2021-10-12 11:44:07', '2021-10-12 13:44:07', NULL, NULL, '2021-10-12 11:05:05', '2021-10-12 11:44:07');
INSERT INTO `tb_rbac_ticket` VALUES (1447805500452323330, NULL, 123456, '1c3b82e8bf06420aaf3a89dd5cfba853', 0, '0', '127.0.0.1', 'Chrome 88', 'junit_test', 'junit_test@hotmail.com', '13800138000', 'Junit Test', '2021-10-12 14:05:04', '2021-10-12 16:05:04', NULL, NULL, '2021-10-12 14:05:05', NULL);
INSERT INTO `tb_rbac_ticket` VALUES (1447805501937106946, NULL, 123456, '9e252a271c0f4bb2b86bdc647b6651c9', 0, '0', '127.0.0.1', 'Chrome 88', 'junit_test', 'junit_test@hotmail.com', '13800138000', 'Junit Test', '2021-10-12 14:05:05', '2021-10-12 16:05:05', NULL, NULL, '2021-10-12 14:05:05', NULL);
INSERT INTO `tb_rbac_ticket` VALUES (1447805784679350273, NULL, 123456, '21887caa26fc49b49f2060da17a1fbbd', 0, '0', '127.0.0.1', 'Chrome 88', 'junit_test', 'junit_test@hotmail.com', '13800138000', 'Junit Test', '2021-10-12 14:06:12', '2021-10-12 14:06:13', NULL, NULL, '2021-10-12 14:06:13', NULL);
INSERT INTO `tb_rbac_ticket` VALUES (1447805785958612993, NULL, 123456, '8936ba063c554367bb874b8488e08941', 0, '0', '127.0.0.1', 'Chrome 88', 'junit_test', 'junit_test@hotmail.com', '13800138000', 'Junit Test', '2021-10-12 14:06:13', '2021-10-12 16:06:13', NULL, NULL, '2021-10-12 14:06:13', NULL);
INSERT INTO `tb_rbac_ticket` VALUES (1447805984273698817, NULL, 123456, 'fcafcf4ae2bb48668db2dfde918fc2a7', 0, '0', '127.0.0.1', 'Chrome 88', 'junit_test', 'junit_test@hotmail.com', '13800138000', 'Junit Test', '2021-10-12 14:06:59', '2021-10-12 14:07:00', NULL, NULL, '2021-10-12 14:07:00', NULL);
INSERT INTO `tb_rbac_ticket` VALUES (1447805985502629889, NULL, 123456, '3b52917d3f2b4fb29e4ddfb795d0d17e', 0, '0', '127.0.0.1', 'Chrome 88', 'junit_test', 'junit_test@hotmail.com', '13800138000', 'Junit Test', '2021-10-12 14:07:01', '2021-10-12 16:07:01', NULL, NULL, '2021-10-12 14:07:01', '2021-10-12 14:07:01');
INSERT INTO `tb_rbac_ticket` VALUES (1447806043849572353, NULL, 123456, '03301c9a6ae4481ea6dfeedf4642cb02', 0, '0', '127.0.0.1', 'Chrome 88', 'junit_test', 'junit_test@hotmail.com', '13800138000', 'Junit Test', '2021-10-12 14:07:13', '2021-10-12 14:07:15', NULL, NULL, '2021-10-12 14:07:14', NULL);
INSERT INTO `tb_rbac_ticket` VALUES (1447806045133029378, NULL, 123456, 'efcbe3906224403293e058cd19e4447b', 0, '0', '127.0.0.1', 'Chrome 88', 'junit_test', 'junit_test@hotmail.com', '13800138000', 'Junit Test', '2021-10-12 14:07:15', '2021-10-12 16:07:15', NULL, NULL, '2021-10-12 14:07:15', '2021-10-12 14:07:15');
INSERT INTO `tb_rbac_ticket` VALUES (1447810200761397250, NULL, 1446461532242776066, '67d22596846c4685b0d256a3e742caa8', NULL, '0', NULL, NULL, 'admin', 'mobinchao@hotmail.com', NULL, 'BeanShell.More', '2021-10-12 14:23:45', '2021-10-12 14:26:33', NULL, NULL, '2021-10-12 14:23:46', NULL);
INSERT INTO `tb_rbac_ticket` VALUES (1447811094265589762, NULL, 1446461532242776066, '4c252b6dfc1f477996809da4c5054102', NULL, '0', NULL, NULL, 'admin', 'mobinchao@hotmail.com', NULL, 'BeanShell.More', '2021-10-12 17:16:54', '2021-10-12 19:16:54', NULL, NULL, '2021-10-12 14:27:19', '2021-10-12 17:16:54');
INSERT INTO `tb_rbac_ticket` VALUES (1447905517548687362, NULL, 1446461532242776066, '7327b2d29a9b495782ccccea6e81eaa8', NULL, '0', NULL, NULL, 'admin', 'mobinchao@hotmail.com', NULL, 'BeanShell.More', '2021-10-12 20:42:31', '2021-10-12 22:42:31', NULL, NULL, '2021-10-12 20:42:31', NULL);
INSERT INTO `tb_rbac_ticket` VALUES (1447925415704383489, NULL, 123456, '3ed58505367d430fbe2d8dd403602ce0', 0, '0', '127.0.0.1', 'Chrome 88', 'junit_test', 'junit_test@hotmail.com', '13800138000', 'Junit Test', '2021-10-12 22:01:34', '2021-10-12 22:01:35', NULL, NULL, '2021-10-12 22:01:35', NULL);
INSERT INTO `tb_rbac_ticket` VALUES (1447925416987840513, NULL, 123456, '93b6111b41584a0b8eb67e6638e0c062', 0, '0', '127.0.0.1', 'Chrome 88', 'junit_test', 'junit_test@hotmail.com', '13800138000', 'Junit Test', '2021-10-12 22:01:35', '2021-10-13 00:01:35', NULL, NULL, '2021-10-12 22:01:35', '2021-10-12 22:01:35');
INSERT INTO `tb_rbac_ticket` VALUES (1447930500077330433, NULL, 123456, 'aa500010309e4b149c4c4847dbb4ac35', 0, '0', '127.0.0.1', 'Chrome 88', 'junit_test', 'junit_test@hotmail.com', '13800138000', 'Junit Test', '2021-10-12 22:21:46', '2021-10-12 22:21:47', NULL, NULL, '2021-10-12 22:21:47', NULL);
INSERT INTO `tb_rbac_ticket` VALUES (1447930501339815938, NULL, 123456, 'ae998500b4ee43c08dfa1be1122955ed', 0, '0', '127.0.0.1', 'Chrome 88', 'junit_test', 'junit_test@hotmail.com', '13800138000', 'Junit Test', '2021-10-12 22:21:48', '2021-10-13 00:21:48', NULL, NULL, '2021-10-12 22:21:47', '2021-10-12 22:21:48');
INSERT INTO `tb_rbac_ticket` VALUES (1448243696373964801, NULL, 1448243466811322369, '6c81102610f942af944d432a32b1c8d2', NULL, '0', NULL, NULL, 'admin', 'mobinchao@hotmail.com', NULL, 'BeanShell.More', '2021-10-13 23:31:26', '2021-10-14 01:31:26', NULL, NULL, '2021-10-13 19:06:19', '2021-10-13 23:31:26');
INSERT INTO `tb_rbac_ticket` VALUES (1448571406224789506, NULL, 123456, 'd9423b8b60d74b168e83208b4e9b43bf', 0, '0', '127.0.0.1', 'Chrome 88', 'junit_test', 'junit_test@hotmail.com', '13800138000', 'Junit Test', '2021-10-14 16:48:31', '2021-10-14 16:48:31', NULL, NULL, '2021-10-14 16:48:31', NULL);
INSERT INTO `tb_rbac_ticket` VALUES (1448571407529218050, NULL, 123456, 'b05a6d54aa7744c38ba02b6314e4bec6', 0, '0', '127.0.0.1', 'Chrome 88', 'junit_test', 'junit_test@hotmail.com', '13800138000', 'Junit Test', '2021-10-14 16:48:31', '2021-10-14 18:48:31', NULL, NULL, '2021-10-14 16:48:31', '2021-10-14 16:48:31');
INSERT INTO `tb_rbac_ticket` VALUES (1448573424893550593, NULL, NULL, 'e15b858bdc424d22aa2dbcb7345c9b50', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-10-14 16:56:32', NULL, NULL, NULL, NULL);
INSERT INTO `tb_rbac_ticket` VALUES (1448573426072150018, NULL, NULL, 'eb40f70e3b264e03b3dd7db2a4cce2f7', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-10-14 16:56:33', '2021-10-14 18:56:33', NULL, NULL, NULL, '2021-10-14 16:56:33');
INSERT INTO `tb_rbac_ticket` VALUES (1448574470511996929, NULL, 1448243466811322369, '5ac711d46eea4592b1cc4bdf8f56725d', NULL, '0', NULL, NULL, 'admin', 'mobinchao@hotmail.com', NULL, 'BeanShell.More', '2021-10-14 17:00:42', '2021-10-14 17:02:41', NULL, NULL, '2021-10-14 17:00:42', NULL);
INSERT INTO `tb_rbac_ticket` VALUES (1448575137574047746, NULL, 1448243466811322369, '17d88e2adc8545d88c9a224e7dacdb46', NULL, '0', NULL, NULL, 'admin', 'mobinchao@hotmail.com', NULL, 'BeanShell.More', '2021-10-14 17:21:30', '2021-10-14 17:32:30', NULL, NULL, '2021-10-14 17:03:21', '2021-10-14 17:21:30');
INSERT INTO `tb_rbac_ticket` VALUES (1448582759098372097, NULL, 1448243466811322369, '0560b3b5657647b6b291bcf39cd05c45', NULL, '0', NULL, NULL, 'admin', 'mobinchao@hotmail.com', NULL, 'BeanShell.More', '2021-10-14 18:09:00', '2021-10-14 18:11:41', NULL, NULL, '2021-10-14 17:33:38', '2021-10-14 18:09:00');
INSERT INTO `tb_rbac_ticket` VALUES (1448592392223531010, NULL, 1448243466811322369, 'c836bd9fab124fbdb50a81ba92b8a3cb', NULL, '0', '192.168.137.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.71 Safari/537.36 Edg/94.0.992.38', 'admin', 'mobinchao@hotmail.com', NULL, 'BeanShell.More', '2021-10-14 18:11:55', '2021-10-14 18:45:59', NULL, '{\"mobile\":false,\"browser\":{\"name\":\"MSEdge\",\"pattern\":\"Edge|Edg\",\"mobile\":false,\"unknown\":false},\"version\":\"94.0.992.38\",\"platform\":{\"name\":\"Windows\",\"pattern\":\"windows\",\"mobile\":false,\"ios\":false,\"ipad\":false,\"iphoneOrIPod\":false,\"android\":false,\"unknown\":false},\"os\":{\"name\":\"Windows 10 or Windows Server 2016\",\"pattern\":\"windows nt 10\\\\.0\",\"unknown\":false},\"osVersion\":\"10.0\",\"engine\":{\"name\":\"Webkit\",\"pattern\":\"webkit\",\"unknown\":false},\"engineVersion\":\"537.36\"}', '2021-10-14 18:11:55', NULL);
INSERT INTO `tb_rbac_ticket` VALUES (1448601485122281474, NULL, 1448243466811322369, 'e3efccc0ddba4c1291a0801e83b92a0f', NULL, '0', '192.168.137.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.71 Safari/537.36 Edg/94.0.992.38', 'admin', 'mobinchao@hotmail.com', NULL, 'BeanShell.More', '2021-10-14 20:48:41', '2021-10-14 20:48:48', NULL, '{\"mobile\":false,\"browser\":{\"name\":\"MSEdge\",\"pattern\":\"Edge|Edg\",\"mobile\":false,\"unknown\":false},\"version\":\"94.0.992.38\",\"platform\":{\"name\":\"Windows\",\"pattern\":\"windows\",\"mobile\":false,\"ios\":false,\"ipad\":false,\"iphoneOrIPod\":false,\"android\":false,\"unknown\":false},\"os\":{\"name\":\"Windows 10 or Windows Server 2016\",\"pattern\":\"windows nt 10\\\\.0\",\"unknown\":false},\"osVersion\":\"10.0\",\"engine\":{\"name\":\"Webkit\",\"pattern\":\"webkit\",\"unknown\":false},\"engineVersion\":\"537.36\"}', '2021-10-14 18:48:02', '2021-10-14 20:48:41');
INSERT INTO `tb_rbac_ticket` VALUES (1448639374317133826, NULL, 123456, 'f25e599b583446c08a0677a1ecba76cc', 0, '0', '127.0.0.1', 'Chrome 88', 'junit_test', 'junit_test@hotmail.com', '13800138000', 'Junit Test', '2021-10-14 21:18:36', '2021-10-14 21:18:36', NULL, NULL, '2021-10-14 21:18:36', NULL);
INSERT INTO `tb_rbac_ticket` VALUES (1448639375168577537, NULL, 123456, '613c83a9600641669e0ae1ca99311407', 0, '0', '127.0.0.1', 'Chrome 88', 'junit_test', 'junit_test@hotmail.com', '13800138000', 'Junit Test', '2021-10-14 21:18:36', '2021-10-15 21:18:36', NULL, NULL, '2021-10-14 21:18:36', '2021-10-14 21:18:36');
INSERT INTO `tb_rbac_ticket` VALUES (1448640964713345026, NULL, 123456, '2f529879cb554efea781af907a333614', 0, '0', '127.0.0.1', 'Chrome 88', 'junit_test', 'junit_test@hotmail.com', '13800138000', 'Junit Test', '2021-10-14 21:24:55', '2021-10-14 21:24:55', NULL, NULL, '2021-10-14 21:24:55', NULL);
INSERT INTO `tb_rbac_ticket` VALUES (1448640965430571009, NULL, 123456, '025aff7efe504bbcba17a2df9e470b80', 0, '0', '127.0.0.1', 'Chrome 88', 'junit_test', 'junit_test@hotmail.com', '13800138000', 'Junit Test', '2021-10-14 21:24:55', '2021-10-15 21:24:55', NULL, NULL, '2021-10-14 21:24:55', '2021-10-14 21:24:55');
INSERT INTO `tb_rbac_ticket` VALUES (1448642920869531649, NULL, 123456, 'a11df22df1874aa0a4af741b57eb0460', 0, '0', '127.0.0.1', 'Chrome 88', 'junit_test', 'junit_test@hotmail.com', '13800138000', 'Junit Test', '2021-10-14 21:32:41', '2021-10-14 21:32:42', NULL, NULL, '2021-10-14 21:32:41', NULL);
INSERT INTO `tb_rbac_ticket` VALUES (1448642921658060802, NULL, 123456, '6789ee0fb90f4b22a4494bf865610d41', 0, '0', '127.0.0.1', 'Chrome 88', 'junit_test', 'junit_test@hotmail.com', '13800138000', 'Junit Test', '2021-10-14 21:32:42', '2021-10-15 21:32:42', NULL, NULL, '2021-10-14 21:32:42', '2021-10-14 21:32:42');
INSERT INTO `tb_rbac_ticket` VALUES (1448645512475561985, NULL, 123456, 'b774dedf3003469597aa855cc32f5a1d', 0, '0', '127.0.0.1', 'Chrome 88', 'junit_test', 'junit_test@hotmail.com', '13800138000', 'Junit Test', '2021-10-14 21:42:59', '2021-10-14 21:42:59', NULL, NULL, '2021-10-14 21:42:59', NULL);
INSERT INTO `tb_rbac_ticket` VALUES (1448645513264091137, NULL, 123456, '5a3b4cbcf71f4b7485671bb7c26b9694', 0, '0', '127.0.0.1', 'Chrome 88', 'junit_test', 'junit_test@hotmail.com', '13800138000', 'Junit Test', '2021-10-14 21:43:00', '2021-10-15 21:43:00', NULL, NULL, '2021-10-14 21:43:00', '2021-10-14 21:43:00');
INSERT INTO `tb_rbac_ticket` VALUES (1449279938846875650, NULL, 123456, '7ee3a6e35eb84c87a72268374986dbfc', 0, '0', '127.0.0.1', 'Chrome 88', 'junit_test', 'junit_test@hotmail.com', '13800138000', 'Junit Test', '2021-10-16 15:43:58', '2021-10-16 15:43:58', NULL, NULL, '2021-10-16 15:43:58', NULL);
INSERT INTO `tb_rbac_ticket` VALUES (1449281068054188034, NULL, 123456, '776e51b2c8fe47babfec0b91fc81fa24', 0, '0', '127.0.0.1', 'Chrome 88', 'junit_test', 'junit_test@hotmail.com', '13800138000', 'Junit Test', '2021-10-16 15:48:28', '2021-10-17 15:48:28', NULL, NULL, '2021-10-16 15:48:28', '2021-10-16 15:48:28');
INSERT INTO `tb_rbac_ticket` VALUES (1449282086536048642, NULL, 123456, '4c5548e3e0f24841963b5776aa826afc', 0, '0', '127.0.0.1', 'Chrome 88', 'junit_test', 'junit_test@hotmail.com', '13800138000', 'Junit Test', '2021-10-16 15:52:31', '2021-10-16 15:52:32', NULL, NULL, '2021-10-16 15:52:30', '2021-10-16 15:52:31');
INSERT INTO `tb_rbac_ticket` VALUES (1449290528944377857, NULL, 123456, 'e0df3b7ebac84efdb6eed2e195ac462e', 0, '0', '127.0.0.1', 'Chrome 88', 'junit_test', 'junit_test@hotmail.com', '13800138000', 'Junit Test', '2021-10-16 16:26:03', '2021-10-16 16:26:03', NULL, NULL, '2021-10-16 16:26:03', NULL);
INSERT INTO `tb_rbac_ticket` VALUES (1449290530236223489, NULL, 123456, 'f8a324d4e5574b23826cb32f6a0e9138', 0, '0', '127.0.0.1', 'Chrome 88', 'junit_test', 'junit_test@hotmail.com', '13800138000', 'Junit Test', '2021-10-16 16:26:04', '2021-10-16 16:26:04', NULL, NULL, '2021-10-16 16:26:04', NULL);
INSERT INTO `tb_rbac_ticket` VALUES (1449290531007975425, NULL, 123456, '4fecc3ac226f478ea5639d15547b851b', 0, '0', '127.0.0.1', 'Chrome 88', 'junit_test', 'junit_test@hotmail.com', '13800138000', 'Junit Test', '2021-10-16 16:26:04', '2021-10-16 16:26:05', NULL, NULL, '2021-10-16 16:26:04', '2021-10-16 16:26:04');
INSERT INTO `tb_rbac_ticket` VALUES (1449293600059916289, NULL, 123456, '3f99831a79a24802995fcaec07deabdf', 0, '0', '127.0.0.1', 'Chrome 88', 'junit_test', 'junit_test@hotmail.com', '13800138000', 'Junit Test', '2021-10-16 16:38:15', '2021-10-16 16:38:16', NULL, NULL, '2021-10-16 16:38:15', NULL);
INSERT INTO `tb_rbac_ticket` VALUES (1449293601595031553, NULL, 123456, '935c0b4d530e4653bb9811b69200c91a', 0, '0', '127.0.0.1', 'Chrome 88', 'junit_test', 'junit_test@hotmail.com', '13800138000', 'Junit Test', '2021-10-16 16:38:16', '2021-10-16 16:38:16', NULL, NULL, '2021-10-16 16:38:16', NULL);
INSERT INTO `tb_rbac_ticket` VALUES (1449293602517778433, NULL, 123456, '60278bdf58ec454989b5f920058225f7', 0, '0', '127.0.0.1', 'Chrome 88', 'junit_test', 'junit_test@hotmail.com', '13800138000', 'Junit Test', '2021-10-16 16:38:16', '2021-10-16 16:38:17', NULL, NULL, '2021-10-16 16:38:16', NULL);
INSERT INTO `tb_rbac_ticket` VALUES (1449293780645699585, NULL, 123456, 'ddcb52d5f0e94405b42d9393eaca2bcf', 0, '0', '127.0.0.1', 'Chrome 88', 'junit_test', 'junit_test@hotmail.com', '13800138000', 'Junit Test', '2021-10-16 16:38:59', '2021-10-16 16:38:59', NULL, NULL, '2021-10-16 16:38:59', NULL);
INSERT INTO `tb_rbac_ticket` VALUES (1449293781430034433, NULL, 123456, '00bbac128b5f41a8b8c64488512480c3', 0, '0', '127.0.0.1', 'Chrome 88', 'junit_test', 'junit_test@hotmail.com', '13800138000', 'Junit Test', '2021-10-16 16:38:59', '2021-10-16 16:38:59', NULL, NULL, '2021-10-16 16:38:59', NULL);
INSERT INTO `tb_rbac_ticket` VALUES (1449293782025625601, NULL, 123456, '1c26469ff55a48698c6a0b01aaa42ff2', 0, '0', '127.0.0.1', 'Chrome 88', 'junit_test', 'junit_test@hotmail.com', '13800138000', 'Junit Test', '2021-10-16 16:38:59', '2021-10-16 16:39:00', NULL, NULL, '2021-10-16 16:38:59', NULL);
INSERT INTO `tb_rbac_ticket` VALUES (1449296055938158594, NULL, 123456, '11fd2dfdcf10469b8fabe7be2c1357cc', 0, '0', '127.0.0.1', 'Chrome 88', 'junit_test', 'junit_test@hotmail.com', '13800138000', 'Junit Test', '2021-10-16 16:48:01', '2021-10-16 16:48:01', NULL, NULL, '2021-10-16 16:48:01', NULL);
INSERT INTO `tb_rbac_ticket` VALUES (1449296056722493442, NULL, 123456, '78483b968eec4841b7b76ab4f5116148', 0, '0', '127.0.0.1', 'Chrome 88', 'junit_test', 'junit_test@hotmail.com', '13800138000', 'Junit Test', '2021-10-16 16:48:01', '2021-10-16 16:48:01', NULL, NULL, '2021-10-16 16:48:01', NULL);
INSERT INTO `tb_rbac_ticket` VALUES (1449296057380999170, NULL, 123456, '6ba25a1a7ffe4a65b72fefce1828a042', 0, '0', '127.0.0.1', 'Chrome 88', 'junit_test', 'junit_test@hotmail.com', '13800138000', 'Junit Test', '2021-10-16 16:48:01', '2021-10-16 16:48:02', NULL, NULL, '2021-10-16 16:48:01', NULL);
INSERT INTO `tb_rbac_ticket` VALUES (1449954824405708801, NULL, 123456, 'ab881f51f3234933aad1f64135d85a8c', 0, 'normalLogin', '127.0.0.1', 'Chrome 88', 'junit_test', 'junit_test@hotmail.com', '13800138000', 'Junit Test', '2021-10-18 12:25:44', '2021-10-18 12:25:45', NULL, NULL, '2021-10-18 12:25:44', NULL);
INSERT INTO `tb_rbac_ticket` VALUES (1449955029914042370, NULL, 123456, 'e970109744bb4546bf4eaf24f2aff927', 0, 'normalLogin', '127.0.0.1', 'Chrome 88', 'junit_test', 'junit_test@hotmail.com', '13800138000', 'Junit Test', '2021-10-18 12:26:33', '2021-10-18 12:26:33', NULL, NULL, '2021-10-18 12:26:33', NULL);
INSERT INTO `tb_rbac_ticket` VALUES (1449955030639656961, NULL, 123456, 'fd715f76de7e462bb80be04cddbf52b6', 0, 'normalLogin', '127.0.0.1', 'Chrome 88', 'junit_test', 'junit_test@hotmail.com', '13800138000', 'Junit Test', '2021-10-18 12:26:33', '2021-10-18 12:26:33', NULL, NULL, '2021-10-18 12:26:33', NULL);
INSERT INTO `tb_rbac_ticket` VALUES (1449955031239442433, NULL, 123456, 'ac0ff249e7a4439194e592b02ac14554', 0, 'normalLogin', '127.0.0.1', 'Chrome 88', 'junit_test', 'junit_test@hotmail.com', '13800138000', 'Junit Test', '2021-10-18 12:26:33', '2021-10-18 12:26:34', NULL, NULL, '2021-10-18 12:26:33', NULL);
INSERT INTO `tb_rbac_ticket` VALUES (1449957380196499458, NULL, 1448243466811322369, 'bcadf8f28d4a46578b41552ba6c19cf2', NULL, 'normalLogin', '192.168.137.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.81 Safari/537.36 Edg/94.0.992.50', 'admin', 'mobinchao@hotmail.com', NULL, 'BeanShell.More', '2021-10-18 12:35:53', '2021-10-18 14:35:53', NULL, '{\"mobile\":false,\"browser\":{\"name\":\"MSEdge\",\"pattern\":\"Edge|Edg\",\"mobile\":false,\"unknown\":false},\"version\":\"94.0.992.50\",\"platform\":{\"name\":\"Windows\",\"pattern\":\"windows\",\"android\":false,\"ios\":false,\"iphoneOrIPod\":false,\"ipad\":false,\"mobile\":false,\"unknown\":false},\"os\":{\"name\":\"Windows 10 or Windows Server 2016\",\"pattern\":\"windows nt 10\\\\.0\",\"unknown\":false},\"osVersion\":\"10.0\",\"engine\":{\"name\":\"Webkit\",\"pattern\":\"webkit\",\"unknown\":false},\"engineVersion\":\"537.36\"}', '2021-10-18 12:35:53', NULL);

-- ----------------------------
-- Table structure for tb_rbac_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_rbac_user`;
CREATE TABLE `tb_rbac_user`  (
  `id` bigint(19) NOT NULL COMMENT 'ID',
  `account` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '账号',
  `password` varchar(71) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱地址',
  `phone_number` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电话号码',
  `nick_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `account_state` tinyint(2) NULL DEFAULT NULL COMMENT '帐号状态',
  `user_from` tinyint(2) NULL DEFAULT NULL COMMENT '用户来源',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_rbac_user_account`(`account`) USING BTREE,
  UNIQUE INDEX `uk_rbac_user_email`(`email`) USING BTREE,
  UNIQUE INDEX `uk_rbac_user_phone_number`(`phone_number`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_rbac_user
-- ----------------------------
INSERT INTO `tb_rbac_user` VALUES (1447402588253777922, 'test001', 'sha1:64000:18:ij6gSs5cLfE6kqTGD/yVIW/hFrnFGOu/:hdzie/tViwcl6gkMz/VKpC6d', NULL, NULL, '测试账号001', 0, NULL, '2021-10-11 11:24:03', '2021-10-11 11:28:17');
INSERT INTO `tb_rbac_user` VALUES (1447403627501645825, 'test002', 'sha1:64000:18:6Hb/F7XCptxCl4ObOVTJV8iOae7YLbNe:aW1XTzNlnc9f9IR2eJB61AQY', NULL, NULL, '测试账号002', 0, NULL, '2021-10-11 11:28:11', NULL);
INSERT INTO `tb_rbac_user` VALUES (1448243466811322369, 'admin', 'sha1:64000:18:0/YDIGK5gwXLG4uZm8IYtq1QGwCZvDrJ:uHPXXd8XMRluiHXqZkKFZdzG', 'mobinchao@hotmail.com', NULL, 'BeanShell.More', 0, NULL, '2021-10-13 19:05:24', NULL);

SET FOREIGN_KEY_CHECKS = 1;
