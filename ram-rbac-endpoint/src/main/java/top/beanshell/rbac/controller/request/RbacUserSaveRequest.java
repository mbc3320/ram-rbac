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
    @NotEmpty(message = "{i18n.request.valid.rbac.user-save.account}")
    private String account;

    /**
     * 密码
     */
    @NotEmpty(message = "{i18n.request.valid.rbac.change-current-user-password.password}")
    @Length(min = 6, max = 36, message = "{i18n.request.valid.rbac.change-current-user-password.password.length}")
    @Pattern(regexp = "^\\w{6,36}$", message = "{i18n.request.valid.rbac.change-current-user-password.password.pattern}")
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
    @NotEmpty(message = "{i18n.request.valid.rbac.user-save.nick-name}")
    private String nickName;

    /**
     * 账户状态
     */
    @NotNull(message = "{i18n.request.valid.rbac.user-save.account-state}")
    private AccountState accountState;
}
