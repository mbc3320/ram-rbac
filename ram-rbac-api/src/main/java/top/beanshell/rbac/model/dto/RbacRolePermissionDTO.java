package top.beanshell.rbac.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.*;
import top.beanshell.common.model.dto.BaseDTO;
import top.beanshell.rbac.common.model.enums.PermissionType;

/**
 * 角色权限信息
 * @author binchao
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RbacRolePermissionDTO extends BaseDTO {

    /**
     * 角色ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long roleId;

    /**
     *  权限ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long permissionId;

    /**
     * 权限类型
     */
    private PermissionType permissionType;
}
