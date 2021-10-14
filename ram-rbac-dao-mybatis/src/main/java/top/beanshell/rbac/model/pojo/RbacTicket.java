package top.beanshell.rbac.model.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.beanshell.mybatis.model.pojo.BaseEntity;
import top.beanshell.rbac.common.model.enums.ClientType;
import top.beanshell.rbac.common.model.enums.LoginType;

import java.util.Date;

/**
 * Ticket信息
 * @author binchao
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("tb_rbac_ticket")
public class RbacTicket extends BaseEntity {

    /**
     * 用户来源
     * 0: 普通用户
     * 1: 微信公众号用户
     * 2: 小程序用户
     * 3: 其他用户
     */
    private Integer userFrom;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户凭证
     */
    private String ticket;

    /**
     * 客户端类型
     */
    private ClientType clientType;

    /**
     * 登录方式
     */
    private LoginType loginType;

    /**
     * 客户端信息
     */
    private String clientInfo;

    /**
     * ip地址
     */
    private String ipAddress;

    /**
     * 用户UA
     */
    private String userAgent;

    /**
     * 用户账号
     */
    private String account;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 用户电话号码
     */
    private String phoneNumber;

    /**
     * 用户姓名/昵称
     */
    private String nickName;

    /**
     * 上次刷新时间
     */
    private Date lastRefreshTime;

    /**
     * 预估失效时间
     */
    private Date timeExpire;

    /**
     * 客户端哈希值
     */
    private String clientHash;
}
