package top.beanshell.rbac.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.*;
import top.beanshell.common.model.dto.BaseDTO;
import top.beanshell.rbac.common.model.enums.PermissionType;

/**
 * 权限信息
 * @author binchao
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RbacPermissionDTO extends BaseDTO {

    /**
     * 权限类型
     */
    private PermissionType permissionType;

    /**
     * 权限编码
     */
    private String permissionCode;

    /**
     * 权限名称
     */
    private String permissionName;

    /**
     * 上级权限ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long pid;
}
