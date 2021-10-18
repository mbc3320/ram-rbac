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
    @NotEmpty(message = "用户名/手机号/邮箱号必填")
    private String account;

    /**
     * 密码
     */
    @NotEmpty(message = "密码/验证码必填")
    @Size(min = 6, max = 40)
    private String accountAuth;

    /**
     * 登录类型
     */
    @NotNull(message = "登录类型必填")
    private String loginType = RamRbacConst.DEFAULT_LOGIN_TYPE_NORMAL_NAME;

    /**
     * 图形校验码id
     */
    private Long imgValidCodeId;

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
