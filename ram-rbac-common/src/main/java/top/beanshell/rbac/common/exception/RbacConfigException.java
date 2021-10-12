package top.beanshell.rbac.common.exception;

import top.beanshell.common.exception.BaseException;
import top.beanshell.common.model.enu.EnumCode;

/**
 * KV字典自定义异常
 * @author binchao
 */
public class RbacConfigException extends BaseException {

    public RbacConfigException(EnumCode status) {
        super(status);
    }

    public RbacConfigException(EnumCode status, String message) {
        super(status, message);
    }
}
