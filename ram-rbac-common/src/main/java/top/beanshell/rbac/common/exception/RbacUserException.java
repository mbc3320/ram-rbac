package top.beanshell.rbac.common.exception;


import top.beanshell.common.exception.BaseException;
import top.beanshell.rbac.common.exception.code.RbacUserStatusCode;

/**
 * 用户管理自定义异常
 * @author binchao
 */
public class RbacUserException extends BaseException {

    public RbacUserException(RbacUserStatusCode code) {
        super(code);
    }
}
