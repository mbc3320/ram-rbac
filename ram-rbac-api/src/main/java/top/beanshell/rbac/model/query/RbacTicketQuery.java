package top.beanshell.rbac.model.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户凭证查询条件
 * @author binchao
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RbacTicketQuery implements Serializable {

    /**
     * 用户ID
     */
    private Long userId;

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
     * 用户来源
     * 0: 普通用户
     * 1: 微信公众号用户
     * 2: 小程序用户
     * 3: 其他用户
     */
    private Integer userFrom;
}
