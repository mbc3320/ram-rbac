package top.beanshell.rbac.common.exception;


import top.beanshell.common.exception.BaseException;
import top.beanshell.rbac.common.exception.code.RbacPermissionStatusCode;

/**
 * 权限管理自定义异常
 * @author binchao
 */
public class RbacPermissionException extends BaseException {

    public RbacPermissionException(RbacPermissionStatusCode status) {
        super(status);
    }
}
