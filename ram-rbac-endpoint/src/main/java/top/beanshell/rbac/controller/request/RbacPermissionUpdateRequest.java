package top.beanshell.rbac.controller.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * 更新权限信息请求参数
 * @author binchao
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RbacPermissionUpdateRequest extends RbacPermissionSaveRequest {

    /**
     * ID
     */
    @NotNull(message = "ID必填")
    private Long id;
}
