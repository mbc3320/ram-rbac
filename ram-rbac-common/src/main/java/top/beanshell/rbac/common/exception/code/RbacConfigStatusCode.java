package top.beanshell.rbac.common.exception.code;

import top.beanshell.common.model.enu.EnumCode;

/**
 * KV字典错误码
 * 10400~10499
 * @author binchao
 */
public enum RbacConfigStatusCode implements EnumCode {

    KEY_CODE_IS_EXIST(10400, "键码已存在"),
    GLOBAL_CONFIG_OF_CAPTCHA_ERROR(10401, "登录验证码配置有误")
    ;

    private Integer code;

    private String text;

    RbacConfigStatusCode(Integer code, String text) {
        this.code = code;
        this.text = text;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getText() {
        return text;
    }
}
