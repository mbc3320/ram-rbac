package top.beanshell.rbac.controller.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 令牌信息
 * @author binchao
 */
@Data
public class TicketInfoVO implements Serializable {

    /**
     * 令牌信息
     */
    private String ticket;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 上次刷新时间
     */
    private Date lastRefreshTime;

    /**
     * 用户信息
     */
    private UserDetailVO userDetail;
}
