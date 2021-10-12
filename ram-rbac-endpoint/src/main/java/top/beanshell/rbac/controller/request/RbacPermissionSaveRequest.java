package top.beanshell.rbac.controller.request;

import lombok.Data;
import top.beanshell.rbac.common.model.enums.PermissionType;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 保存权限信息请求参数
 * @author binchao
 */
@Data
public class RbacPermissionSaveRequest implements Serializable {

    /**
     * 权限类型
     */
    @NotNull(message = "权限类型必填")
    private PermissionType permissionType;

    /**
     * 权限编码
     */
    @NotEmpty(message = "权限编码必填")
    private String permissionCode;

    /**
     * 权限名称
     */
    @NotEmpty(message = "权限名称必填")
    private String permissionName;

    /**
     * 上级权限ID
     */
    private Long pid;
}
