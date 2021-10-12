package top.beanshell.rbac.controller.request;

import lombok.Data;
import top.beanshell.rbac.common.model.enums.AccountState;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 *  更新用户信息
 * @author binchao
 */
@Data
public class RbacUserUpdateRequest implements Serializable {

    /**
     * ID
     */
    @NotNull(message = "ID必填")
    private Long id;

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
    @NotEmpty(message = "昵称必填")
    private String nickName;

    /**
     * 账户状态
     */
    @NotNull(message = "帐号状态必填")
    private AccountState accountState;
}
