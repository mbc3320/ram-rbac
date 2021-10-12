package top.beanshell.rbac.model.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.beanshell.rbac.common.model.enums.AccountState;

import java.io.Serializable;

/**
 * 用户管理查询条件
 * @author binchao
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RbacUserQuery implements Serializable {

    /**
     * 帐号
     */
    private String account;

    /**
     * 邮箱地址
     */
    private String email;

    /**
     * 电话号码
     */
    private String phoneNumber;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 帐号状态
     */
    private AccountState accountState;
}
