package top.beanshell.rbac.common.exception.code;


import top.beanshell.common.model.enu.EnumCode;

/**
 * 角色管理状态码
 * 10200-10299
 * @author binchao
 */
public enum RbacRoleStatusCode implements EnumCode {

    ROLE_IS_EXIST(10200, "角色已存在"),
    ROLE_IS_AUTH(10201, "角色已授权，无法删除")
    ;

    private Integer code;

    private String text;

    RbacRoleStatusCode(Integer code, String text) {
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
