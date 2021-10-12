package top.beanshell.rbac.model.bo;

import lombok.Data;
import top.beanshell.rbac.common.model.enums.CaptchaType;

import java.io.Serializable;

/**
 * 系统全局配置
 * @author binchao
 */
@Data
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

}
