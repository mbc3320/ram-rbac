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
    @NotNull(message = "{i18n.request.valid.rbac.role-user-grant.role-id}")
    private Long roleId;

    /**
     * 权限类型
     */
    @NotNull(message = "{i18n.request.valid.rbac.role-rel-save.permission-type}")
    private PermissionType permissionType;

    /**
     * 权限ID列表
     */
    private List<Long> permissionIds;
}
