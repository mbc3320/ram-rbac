package top.beanshell.rbac.common.constant;

/**
 * 项目常量
 * @author binchao
 */
public interface RamRbacConst {

    /**
     * 默认ticket格式
     */
    String DEFAULT_TICKET_KEY_FORMAT = "rbac:ticket:%s";

    /**
     * 默认Ticket请求头
     */
    String DEFAULT_TICKET_HEADER_KEY = "RAM-TICKET";

    /**
     * 系统配置默认键
     */
    String DEFAULT_SYS_GLOBAL_CONFIG_KEY = "_system_global_config";

    /**
     * 用户名/密码登录方式名称
     */
    String DEFAULT_LOGIN_TYPE_NORMAL_NAME = "normalLogin";

}
