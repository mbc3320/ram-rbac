package top.beanshell.rbac.controller.request;

import lombok.Data;
import top.beanshell.rbac.common.model.enums.LoginType;

import javax.validation.constraints.NotEmpty;
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
    @NotEmpty(message = "用户名/手机号/邮箱号不能为空")
    private String account;

    /**
     * 密码
     */
    @NotEmpty(message = "密码/验证码不能为空")
    @Size(min = 6, max = 40)
    private String accountAuth;

    /**
     * 登录类型
     */
    private LoginType loginType = LoginType.ACCOUNT;

    /**
     * 图形校验码id
     */
    private Long imgValidCodeId;

    /**
     * 图形校验码文本
     */
    private String imgValidCodeText;
}
