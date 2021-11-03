package top.beanshell.rbac.controller.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 更改用户密码
 * @author binchao
 */
@Data
public class RbacChangeUserPasswordRequest implements Serializable {

    /**
     * 用户ID
     */
    @NotNull(message = "{i18n.request.valid.rbac.change-user-password.user-id}")
    private Long userId;

    /**
     * 新密码
     */
    @NotEmpty(message = "{i18n.request.valid.rbac.change-user-password.new-password}")
    @Length(min = 6, max = 36, message = "{i18n.request.valid.rbac.change-current-user-password.password.length}")
    @Pattern(regexp = "^\\w{6,36}$", message = "{i18n.request.valid.rbac.change-current-user-password.password.pattern}")
    private String newPwd;
}
