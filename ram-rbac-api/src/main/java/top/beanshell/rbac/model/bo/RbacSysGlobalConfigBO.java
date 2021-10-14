package top.beanshell.rbac.model.bo;

import cn.hutool.core.lang.Assert;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.beanshell.rbac.common.exception.RbacUserException;
import top.beanshell.rbac.common.exception.code.RbacUserStatusCode;
import top.beanshell.rbac.common.model.enums.CaptchaType;
import top.beanshell.rbac.common.model.enums.LoginType;

import java.io.Serializable;

/**
 * 系统全局配置
 * @author binchao
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RbacSysGlobalConfigBO implements Serializable {

    /**
     * 控制台登录是否需要验证码
     */
    private Boolean consoleCaptcha;

    /**
     * 校验码类型
     */
    private CaptchaType captchaType;

    /**
     * 常规账号密码登录
     */
    private Boolean normalLogin;

    /**
     * 短信验证码登录
     */
    private Boolean smsCodeLogin;

    /**
     * 邮箱验证码登录
     */
    private Boolean emailCodeLogin;

    /**
     * 微信公众号登录
     */
    private Boolean wxMpLogin;

    /**
     * 微信小程序登录
     */
    private Boolean wxMaLogin;

    /**
     * 自定义登录
     */
    private Boolean customLogin;

    /**
     * Ticket超时时间 (单位：分钟)
     */
    private Long ticketTimeout;

    /**
     * 密码错误次数重置时间（单位：小时）
     */
    private Long passwordErrorExpireTime;


    public void checkLoginType(LoginType loginType) {
        Assert.notNull(loginType, "loginType必填");
        switch (loginType) {
            case ACCOUNT:
                if (Boolean.FALSE.equals(normalLogin)) {
                    throw new RbacUserException(RbacUserStatusCode.LOGIN_TYPE_UNSUPPORT);
                }
                break;
            case WX_MP:
                if (Boolean.FALSE.equals(wxMpLogin)) {
                    throw new RbacUserException(RbacUserStatusCode.LOGIN_TYPE_UNSUPPORT);
                }
                break;
            case WX_MA:
                if (Boolean.FALSE.equals(wxMaLogin)) {
                    throw new RbacUserException(RbacUserStatusCode.LOGIN_TYPE_UNSUPPORT);
                }
                break;
            case SMS_CODE:
                if (Boolean.FALSE.equals(smsCodeLogin)) {
                    throw new RbacUserException(RbacUserStatusCode.LOGIN_TYPE_UNSUPPORT);
                }
                break;
            case EMAIL_CODE:
                if (Boolean.FALSE.equals(emailCodeLogin)) {
                    throw new RbacUserException(RbacUserStatusCode.LOGIN_TYPE_UNSUPPORT);
                }
                break;
            case OTHER:
                if (Boolean.FALSE.equals(customLogin)) {
                    throw new RbacUserException(RbacUserStatusCode.LOGIN_TYPE_UNSUPPORT);
                }
                break;
            default:
                throw new RbacUserException(RbacUserStatusCode.LOGIN_TYPE_UNSUPPORT);
        }
    }
}
