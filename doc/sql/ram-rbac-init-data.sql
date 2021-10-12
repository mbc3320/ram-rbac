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


-- ----------------------------
-- Records of tb_rbac_role
-- ----------------------------
INSERT INTO `tb_rbac_role` VALUES (1447481227263725570, 'system_admin', '系统管理员', 0, '系统管理员，拥有配置系统基础数据的权限。', '2021-10-11 16:36:32', '2021-10-11 16:40:13');

-- ----------------------------
-- Records of tb_rbac_role_permission
-- ----------------------------
INSERT INTO `tb_rbac_role_permission` VALUES (1447811288881295361, 1447481227263725570, 1447415824789336065, 0, NULL, NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1447811288881295362, 1447481227263725570, 1447415882276466690, 0, NULL, NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1447811288881295363, 1447481227263725570, 1447417445673934850, 0, NULL, NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1447811288881295364, 1447481227263725570, 1447417505585373186, 0, NULL, NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1447811288881295365, 1447481227263725570, 1447417559796752385, 0, NULL, NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1447811288881295366, 1447481227263725570, 1447417621125865474, 0, NULL, NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1447811288881295367, 1447481227263725570, 1447417801950699522, 0, NULL, NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1447811288881295368, 1447481227263725570, 1447477451698061313, 0, NULL, NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1447811288881295369, 1447481227263725570, 1447477496644222977, 0, NULL, NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1447811288881295370, 1447481227263725570, 1447477553334435841, 0, NULL, NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1447811288881295371, 1447481227263725570, 1447477621139554306, 0, NULL, NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1447811288881295372, 1447481227263725570, 1447477677972373506, 0, NULL, NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1447811288881295373, 1447481227263725570, 1447477733291048961, 0, NULL, NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1447811288881295374, 1447481227263725570, 1447477824684933121, 0, NULL, NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1447811288881295375, 1447481227263725570, 1447477919711084546, 0, NULL, NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1447811288881295376, 1447481227263725570, 1447478760971026434, 0, NULL, NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1447811288881295377, 1447481227263725570, 1447478849940602882, 0, NULL, NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1447811288881295378, 1447481227263725570, 1447478908543418370, 0, NULL, NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1447811288881295379, 1447481227263725570, 1447478967121068033, 0, NULL, NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1447811288881295380, 1447481227263725570, 1447479126076801025, 0, NULL, NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1447811288881295381, 1447481227263725570, 1447479240396750850, 0, NULL, NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1447811288881295382, 1447481227263725570, 1447479351721967617, 0, NULL, NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1447811288881295383, 1447481227263725570, 1447479513693405186, 0, NULL, NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1447811288881295384, 1447481227263725570, 1447479749258104833, 0, NULL, NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1447811288881295385, 1447481227263725570, 1447479822549372929, 0, NULL, NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1447811288881295386, 1447481227263725570, 1447479961854791682, 0, NULL, NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1447811288881295387, 1447481227263725570, 1447480109431377921, 0, NULL, NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1447811288881295388, 1447481227263725570, 1447480179216207874, 0, NULL, NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1447811288881295389, 1447481227263725570, 1447480258484359169, 0, NULL, NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1447811288881295390, 1447481227263725570, 1447480321784795137, 0, NULL, NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1447811288881295391, 1447481227263725570, 1447480395105423362, 0, NULL, NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1447811288881295392, 1447481227263725570, 1447480444694679553, 0, NULL, NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1447811288881295393, 1447481227263725570, 1447480514018136066, 0, NULL, NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1447811288881295394, 1447481227263725570, 1447480562214883330, 0, NULL, NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1447811288881295395, 1447481227263725570, 1447415739108093954, 0, NULL, NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1447811288881295396, 1447481227263725570, 1447415327718174721, 0, NULL, NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1447823582688309250, 1447481227263725570, 1447415378427310082, 1, NULL, NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1447823582688309251, 1447481227263725570, 1447821238722490369, 1, NULL, NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1447823582688309252, 1447481227263725570, 1447821335875153921, 1, NULL, NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1447823582688309253, 1447481227263725570, 1447821448047620097, 1, NULL, NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1447823582688309254, 1447481227263725570, 1447821500816158722, 1, NULL, NULL);
INSERT INTO `tb_rbac_role_permission` VALUES (1447823582688309255, 1447481227263725570, 1447821558030659586, 1, NULL, NULL);

-- ----------------------------
-- Records of tb_rbac_role_user
-- ----------------------------
INSERT INTO `tb_rbac_role_user` VALUES (1447770024845037570, 1447481227263725570, 1446461532242776066, NULL, NULL);

-- ----------------------------
-- Records of tb_rbac_user
-- ----------------------------
INSERT INTO `tb_rbac_user` VALUES (1446461532242776066, 'admin', 'sha1:64000:18:2oavyC5AwEzF1zhFjY4PGcf6sLKcXGc2:sby6ddfhi4ZvYxPDOs91zAhl', 'mobinchao@hotmail.com', NULL, 'BeanShell.More', 0, NULL, '2021-10-08 21:04:38', NULL);
