package top.beanshell.rbac.model.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.beetl.sql.annotation.entity.Table;
import top.beanshell.beetlsql.model.pojo.BaseEntity;
import top.beanshell.rbac.common.model.enums.AccountState;

/**
 * 用户信息
 * @author binchao
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "tb_rbac_user")
public class RbacUser extends BaseEntity {

    /**
     * 用户来源
     * 0: 普通用户
     * 1: 微信公众号用户
     * 2: 小程序用户
     * 3: 其他用户
     */
    private Integer userFrom;

    /**
     * 用户帐号
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String phoneNumber;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 账户状态
     */
    private AccountState accountState;
}
