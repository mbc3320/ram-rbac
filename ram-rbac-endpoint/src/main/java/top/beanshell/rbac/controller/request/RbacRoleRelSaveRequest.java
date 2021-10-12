package top.beanshell.rbac.controller.request;

import lombok.Data;
import top.beanshell.rbac.common.model.enums.PermissionType;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 角色权限授权请求参数
 * @author binchao
 */
@Data
public class RbacRoleRelSaveRequest implements Serializable {

    /**
     * 角色ID
     */
    @NotNull(message = "角色ID必填")
    private Long roleId;

    /**
     * 权限类型
     */
    @NotNull(message = "权限类型必填")
    private PermissionType permissionType;

    /**
     * 权限ID列表
     */
    private List<Long> permissionIds;
}
