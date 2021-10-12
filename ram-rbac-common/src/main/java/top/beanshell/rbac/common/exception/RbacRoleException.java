package top.beanshell.rbac.common.exception;

import top.beanshell.common.exception.BaseException;
import top.beanshell.rbac.common.exception.code.RbacRoleStatusCode;

/**
 * 角色管理自定义异常
 * @author binchao
 */
public class RbacRoleException extends BaseException {

    public RbacRoleException(RbacRoleStatusCode status) {
        super(status);
    }
}
