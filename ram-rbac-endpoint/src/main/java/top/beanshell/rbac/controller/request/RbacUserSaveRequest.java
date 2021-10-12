package top.beanshell.rbac.controller.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import top.beanshell.rbac.common.model.enums.AccountState;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 保存用户请求参数
 * @author binchao
 */
@Data
public class RbacUserSaveRequest implements Serializable {

    /**
     * 帐号
     */
    @NotEmpty(message = "帐号必填")
    private String account;

    /**
     * 密码
     */
    @NotEmpty(message = "密码必填")
    @Length(min = 6, max = 36, message = "密码长度限制为6~36为字符串数字符号组合")
    @Pattern(regexp = "^\\w{6,36}$", message = "密码不符合复杂度要求")
    private String password;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String phoneNumber;

    /**
     * 用户昵称
     */
    @NotEmpty(message = "昵称必填")
    private String nickName;

    /**
     * 账户状态
     */
    @NotNull(message = "帐号状态必填")
    private AccountState accountState;
}
