package top.beanshell.rbac.controller.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 保存角色请求参数
 * @author binchao
 */
@Data
public class RbacRoleSaveRequest implements Serializable {

    /**
     * 角色编码
     */
    @NotEmpty(message = "{i18n.request.valid.rbac.role-save.role-code}")
    private String roleCode;

    /**
     * 角色名称
     */
    @NotEmpty(message = "{i18n.request.valid.rbac.role-save.role-name}")
    private String roleName;

    /**
     * 是否默认角色
     */
    @NotNull(message = "{i18n.request.valid.rbac.role-save.default-role}")
    private Boolean roleDefault;

    /**
     * 角色简介
     */
    private String roleDesc;
}
