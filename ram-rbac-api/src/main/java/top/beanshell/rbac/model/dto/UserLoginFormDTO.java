package top.beanshell.rbac.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.beanshell.rbac.common.model.enums.ClientType;

import java.io.Serializable;

/**
 *  用户登录表单
 * @author binchao
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginFormDTO implements Serializable {

    /**
     * 帐号
     */
    private String account;

    /**
     * 密码
     */
    private String accountAuth;

    /**
     * 登录类型
     */
    private String loginType;

    /**
     * 图形校验码id
     */
    private String imgValidCodeId;

    /**
     * 图形校验码文本
     */
    private String imgValidCodeText;

    /**
     * 客户端IP地址
     */
    private String ipAddress;

    /**
     * 用户UA
     */
    private String userAgent;

    /**
     * 客户端类型
     */
    private ClientType clientType;

    /**
     * 客户端信息
     */
    private String clientInfo;

    /**
     * 其他自定义参数
     * 可用是json字符串
     * 供自定义登录方式客制化使用
     */
    private String extra;

}
