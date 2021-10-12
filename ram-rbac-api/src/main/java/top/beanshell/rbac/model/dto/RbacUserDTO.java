package top.beanshell.rbac.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import top.beanshell.common.model.dto.BaseDTO;
import top.beanshell.rbac.common.model.enums.AccountState;

/**
 * 用户信息
 * @author binchao
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RbacUserDTO extends BaseDTO {

    /**
     * 用户帐号
     */
    private String account;

    /**
     * 密码
     */
    @JsonIgnore
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
