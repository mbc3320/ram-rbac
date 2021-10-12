package top.beanshell.rbac.model.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.beanshell.rbac.common.model.enums.PermissionType;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 角色权限查询条件
 * @author: binchao
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RbacRolePermissionQuery implements Serializable {

    /**
     * 角色id
     */
    @NotNull(message = "角色id必填")
    private Long roleId;

    /**
     * 上级权限id
     */
    private Long pid;

    /**
     * 权限类型
     */
    @NotNull(message = "权限类型必填")
    private PermissionType permissionType;
}
