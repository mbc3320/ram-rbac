package top.beanshell.rbac.common.exception.code;


import top.beanshell.common.model.enu.EnumCode;

/**
 * 权限状态码列表
 * 10300~10399
 * @author binchao
 */
public enum RbacPermissionStatusCode implements EnumCode {

    PERMISSION_IS_EXIST(10300, "权限已存在"),
    PERMISSION_IS_AUTH(10301, "权限已授权，无法删除"),
    PERMISSION_ROOT_NODE_IS_EXIST(10302, "权限根节点已存在"),
    PERMISSION_ROOT_NODE_ERROR(10303, "权限根节点不存在")
    ;

    private Integer code;

    private String text;

    RbacPermissionStatusCode(Integer code, String text) {
        this.code = code;
        this.text = text;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getText() {
        return text;
    }
}
