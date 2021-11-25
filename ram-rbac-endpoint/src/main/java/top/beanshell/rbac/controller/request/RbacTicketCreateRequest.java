package top.beanshell.rbac.controller.request;

import lombok.Data;
import top.beanshell.rbac.common.constant.RamRbacConst;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 创建ticket请求参数
 * @author binchao
 */
@Data
public class RbacTicketCreateRequest implements Serializable {

    /**
     * 帐号
     */
    @NotEmpty(message = "{i18n.request.valid.rbac.ticket-create-request.account}")
    private String account;

    /**
     * 密码
     */
    @NotEmpty(message = "{i18n.request.valid.rbac.ticket-create-request.password}")
    @Size(min = 6, max = 40)
    private String accountAuth;

    /**
     * 登录类型
     */
    @NotNull(message = "{i18n.request.valid.rbac.ticket-create-request.login-type}")
    private String loginType = RamRbacConst.DEFAULT_LOGIN_TYPE_NORMAL_NAME;

    /**
     * 图形校验码id
     */
    private String imgValidCodeId;

    /**
     * 图形校验码文本
     */
    private String imgValidCodeText;

    /**
     * 其他自定义参数
     * 可用是json字符串
     * 供自定义登录方式客制化使用
     */
    private String extra;
}
