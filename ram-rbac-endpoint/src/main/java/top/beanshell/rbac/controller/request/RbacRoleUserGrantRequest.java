package top.beanshell.rbac.controller.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 用户角色授权请求参数
 * @author binchao
 */
@Data
public class RbacRoleUserGrantRequest implements Serializable {

    /**
     * 角色ID
     */
    @NotNull(message = "角色ID必填")
    private Long roleId;

    /**
     * 用户ID
     */
    @NotNull(message = "用户ID必填")
    private Long userId;
}
