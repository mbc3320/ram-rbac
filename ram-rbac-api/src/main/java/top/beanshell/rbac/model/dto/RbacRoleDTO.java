package top.beanshell.rbac.model.dto;

import lombok.*;
import top.beanshell.common.model.dto.BaseDTO;

/**
 * 角色信息
 * @author binchao
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RbacRoleDTO extends BaseDTO {

    /**
     * 角色编码
     */
    private String roleCode;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 是否默认角色
     */
    private Boolean roleDefault;

    /**
     * 角色简介
     */
    private String roleDesc;
}
