package top.beanshell.rbac.model.dto;

import lombok.*;


/**
 * 角色授权用户信息
 * @author binchao
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RbacRoleUserCheckedDTO extends RbacRoleDTO {

    /**
     * 是否已授权
     */
    private Boolean checked;
}
