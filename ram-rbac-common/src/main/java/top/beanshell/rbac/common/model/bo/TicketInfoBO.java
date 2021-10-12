package top.beanshell.rbac.common.model.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 令牌信息
 * @author binchao
 */
@Data
public class TicketInfoBO implements Serializable {

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
    private UserDetailBO userDetail;
}
