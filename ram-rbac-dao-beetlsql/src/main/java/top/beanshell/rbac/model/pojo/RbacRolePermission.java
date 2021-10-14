package top.beanshell.rbac.model.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.beetl.sql.annotation.entity.Table;
import top.beanshell.beetlsql.model.pojo.BaseEntity;
import top.beanshell.rbac.common.model.enums.PermissionType;

/**
 * 角色权限信息
 * @author binchao
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "tb_rbac_role_permission")
public class RbacRolePermission extends BaseEntity {

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     *  权限ID
     */
    private Long permissionId;

    /**
     * 权限类型
     */
    private PermissionType permissionType;
}
