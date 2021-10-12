package top.beanshell.rbac.common.model.enums;

import org.beetl.sql.annotation.entity.EnumValue;
import top.beanshell.common.model.enu.EnumCode;

/**
 * 登录类型
 * @author binchao
 */
public enum LoginType implements EnumCode {

    ACCOUNT(0, "帐号密码"),
    SMS_CODE(1, "手机验证码"),
    EMAIL_CODE(2, "邮箱验证码"),
    WX_MP(3, "微信公众号"),
    WX_MA(4, "微信小程序"),
    OTHER(5, "其他")
    ;

    @EnumValue
    @com.baomidou.mybatisplus.annotation.EnumValue
    private Integer code;

    private String text;

    LoginType(Integer code, String text) {
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
