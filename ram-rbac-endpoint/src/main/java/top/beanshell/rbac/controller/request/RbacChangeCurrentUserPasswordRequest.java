package top.beanshell.rbac.controller.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 更改当前用户密码请求参数
 * @author binchao
 */
@Data
public class RbacChangeCurrentUserPasswordRequest implements Serializable {


    /**
     * 原密码
     */
    @NotEmpty(message = "{i18n.request.valid.rbac.change-current-user-password.password}")
    @Length(min = 6, max = 36, message = "{i18n.request.valid.rbac.change-current-user-password.password.length}")
    @Pattern(regexp = "^\\w{6,36}$", message = "{i18n.request.valid.rbac.change-current-user-password.password.pattern}")
    private String oldPwd;

    /**
     * 新密码
     */
    @NotEmpty(message = "{i18n.request.valid.rbac.change-current-user-password.password}")
    @Length(min = 6, max = 36, message = "{i18n.request.valid.rbac.change-current-user-password.password.length}")
    @Pattern(regexp = "^\\w{6,36}$", message = "{i18n.request.valid.rbac.change-current-user-password.password.pattern}")
    private String newPwd;
}
