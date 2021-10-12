package top.beanshell.rbac.model.dto;

import lombok.*;

/**
 * 角色权限是否授权信息
 * @author binchao
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RbacRolePermissionCheckedDTO extends RbacPermissionDTO {

    /**
     * 是否已授权
     */
    private Boolean checked;
}
