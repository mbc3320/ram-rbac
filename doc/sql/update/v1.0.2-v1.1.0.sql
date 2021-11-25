-- 以下脚本可以不执行
-- 选择执行的话，按照以下步骤操作：
-- 1.需要清除全局设置的redis缓存，
-- 2.并删除tb_rbac_config表中key_code为_system_global_config的数据，
-- *3.再执行如下新增脚本

INSERT INTO `tb_rbac_config`(`id`, `key_code`, `key_value`, `key_desc`, `create_time`, `update_time`) VALUES (1463429048680136706, '_system_global_config', '{\"consoleCaptcha\":true,\"captchaMetaList\":[{\"enable\":false,\"captchaMetaName\":\"带横线的简单文本\",\"captchaServiceName\":\"simpleLineTextCaptchaService\",\"width\":100,\"height\":36,\"extJson\":\"{ \\\"codeCount\\\":  5, \\\"interferingLineCount\\\": 150}\"},{\"enable\":false,\"captchaServiceName\":\"simpleCircleTextCaptchaService\",\"captchaMetaName\":\"带圆圈的简单文本\",\"width\":100,\"height\":36,\"extJson\":\"{ \\\"codeCount\\\":  5, \\\"interferingLineCount\\\": 20}\"},{\"enable\":false,\"captchaServiceName\":\"simpleShearTextCaptchaService\",\"captchaMetaName\":\"扭曲干扰简单文本\",\"width\":100,\"height\":36,\"extJson\":null},{\"enable\":true,\"captchaServiceName\":\"simpleGifTextCaptchaService\",\"captchaMetaName\":\"Gif简单文本\",\"width\":100,\"height\":36,\"extJson\":null}],\"loginServiceMetaList\":[{\"loginType\":\"normalLogin\",\"typeName\":\"账号密码\",\"enable\":true,\"loginFactoryServiceName\":\"normalLoginFactory\"}],\"ticketTimeout\":120,\"passwordErrorExpireTime\":5}', NULL, '2021-11-24 16:47:29', '2021-11-25 14:36:40');
