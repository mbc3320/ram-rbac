package top.beanshell.rbac.common.exception.code;


import top.beanshell.common.model.enu.EnumCode;

/**
 * ticket状态码
 * 10000-10099
 * @author binchao
 */
public enum RbacTicketStatusCode implements EnumCode {

    HEADER_MISSING_TICKET(10000, "缺失请求头信息"),
    TICKET_CREATE_FAILED(10001, "Ticket创建失败"),
    LOGIN_TYPE_UN_SUPPORT(10002, "登录方式不支持"),
    TICKET_IS_NOT_EXIST(10003, "Ticket不存在"),
    TICKET_TIME_OUT(10004, "Ticket已失效")
    ;

    private Integer code;

    private String text;

    RbacTicketStatusCode(Integer code, String text) {
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
