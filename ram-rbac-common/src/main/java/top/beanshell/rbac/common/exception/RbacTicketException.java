package top.beanshell.rbac.common.exception;


import top.beanshell.common.exception.BaseException;
import top.beanshell.rbac.common.exception.code.RbacTicketStatusCode;

/**
 * Ticket自定义异常
 * @author binchao
 */
public class RbacTicketException extends BaseException {

    public RbacTicketException(RbacTicketStatusCode code) {
        super(code);
    }
}
