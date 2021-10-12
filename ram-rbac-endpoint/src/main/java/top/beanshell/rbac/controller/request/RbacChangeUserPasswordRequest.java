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
    @NotNull(message = "用户ID必填")
    private Long userId;

    /**
     * 新密码
     */
    @NotEmpty(message = "新密码必填")
    @Length(min = 6, max = 36, message = "密码长度限制为6~36为字符串数字符号组合")
    @Pattern(regexp = "^\\w{6,36}$", message = "密码不符合复杂度要求")
    private String newPwd;
}
