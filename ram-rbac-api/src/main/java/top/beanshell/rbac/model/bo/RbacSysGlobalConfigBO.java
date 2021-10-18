package top.beanshell.rbac.model.bo;

import cn.hutool.core.lang.Assert;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.beanshell.rbac.common.exception.RbacUserException;
import top.beanshell.rbac.common.exception.code.RbacUserStatusCode;
import top.beanshell.rbac.common.model.enums.CaptchaType;

import java.io.Serializable;
import java.util.List;

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
     * 登录服务实现列表
     */
    private List<RbacSysLoginTypeMetaBO> loginServiceMetaList;

    /**
     * Ticket超时时间 (单位：分钟)
     */
    private Long ticketTimeout;

    /**
     * 密码错误次数重置时间（单位：小时）
     */
    private Long passwordErrorExpireTime;


    public String getLoginTypeServiceName(String loginTypeName) {
        Assert.notNull(loginTypeName, "loginType必填");
        for (RbacSysLoginTypeMetaBO meta : loginServiceMetaList) {
            if (loginTypeName.equals(meta.getLoginType())) {
                if (Boolean.TRUE.equals(meta.getEnable())) {
                    return meta.getLoginFactoryServiceName();
                } else {
                    break;
                }
            }
        }
        throw new RbacUserException(RbacUserStatusCode.LOGIN_TYPE_UNSUPPORT);
    }
}
