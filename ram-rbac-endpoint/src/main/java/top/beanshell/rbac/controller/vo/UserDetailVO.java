package top.beanshell.rbac.controller.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import top.beanshell.rbac.common.converter.CommonStrDesensitizeConverter;
import top.beanshell.rbac.common.model.enums.AccountState;

import java.io.Serializable;
import java.util.List;

/**
 * 用户信息
 * @author binchao
 */
@Data
public class UserDetailVO implements Serializable {

    /**
     * 用户ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 用户帐号
     */
    @JsonSerialize(converter = CommonStrDesensitizeConverter.class)
    private String account;

    /**
     * 用户邮箱
     */
    @JsonSerialize(converter = CommonStrDesensitizeConverter.class)
    private String email;

    /**
     * 用户手机号
     */
    @JsonSerialize(converter = CommonStrDesensitizeConverter.class)
    private String phoneNumber;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 账户状态
     */
    private AccountState accountState;

    /**
     * 权限列表
     */
    private List<String> accessList;

    /**
     * 角色列表
     */
    private List<String> roleList;

}
