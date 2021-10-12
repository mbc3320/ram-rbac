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
    @NotEmpty(message = "密码必填")
    @Length(min = 6, max = 36, message = "密码长度限制为6~36为字符串数字符号组合")
    @Pattern(regexp = "^\\w{6,36}$", message = "密码不符合复杂度要求")
    private String oldPwd;

    /**
     * 新密码
     */
    @NotEmpty(message = "密码必填")
    @Length(min = 6, max = 36, message = "密码长度限制为6~36为字符串数字符号组合")
    @Pattern(regexp = "^\\w{6,36}$", message = "密码不符合复杂度要求")
    private String newPwd;
}
