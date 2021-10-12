package top.beanshell.rbac.model.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.beanshell.rbac.common.model.enums.PermissionType;

import java.io.Serializable;

/**
 * 权限管理查询条件
 * @author binchao
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RbacPermissionQuery implements Serializable {

    /**
     * 上级权限id
     */
    private Long pid;

    /**
     * 权限类型
     */
    private PermissionType permissionType;

    /**
     * 权限编码
     */
    private String permissionCode;

}
