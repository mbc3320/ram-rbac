package top.beanshell.rbac.model.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.beanshell.mybatis.model.pojo.BaseEntity;
import top.beanshell.rbac.common.model.enums.PermissionType;

/**
 * 角色权限信息
 * @author binchao
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("tb_rbac_role_permission")
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
