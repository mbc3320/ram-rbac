package top.beanshell.rbac.controller.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * 更新角色信息请求参数
 * @author binchao
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RbacRoleUpdateRequest extends RbacRoleSaveRequest {

    /**
     * ID必填
     */
    @NotNull(message = "ID必填")
    private Long id;
}
