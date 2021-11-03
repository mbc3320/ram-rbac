package top.beanshell.rbac.common.exception.code;


import top.beanshell.common.model.enu.EnumCode;

/**
 * 用户管理模块状态码
 * 10100~10199
 * @author binchao
 */
public enum RbacUserStatusCode implements EnumCode {

    USER_EXIST(10100, "用户已存在"),
    USER_IS_NOT_EXIST(10101, "用户不存在"),
    USER_PASSWORD_ERROR(10102, "密码错误"),
    USER_STATE_ERROR(10103, "用户状态异常"),
    USER_PASSWORD_TRY_ERROR(10104, "用户密码错误次数过多，请稍后再试"),
    REG_FAILED(10105, "用户保存失败"),
    USER_PASSWORD_MODIFY_FAILED(10106, "用户密码修改失败"),
    LOGIN_TYPE_UNSUPPORTED(10107, "不受支持的登录方式")
    ;

    private Integer code;

    private String text;

    RbacUserStatusCode(Integer code, String text) {
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
