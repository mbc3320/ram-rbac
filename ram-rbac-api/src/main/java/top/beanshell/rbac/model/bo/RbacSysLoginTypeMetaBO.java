package top.beanshell.rbac.model.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 登录类型信息元数据
 * @author binchao
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RbacSysLoginTypeMetaBO implements Serializable {

    /**
     * 登录类型名称
     */
    private String loginType;

    /**
     * 登录方式名称
     */
    private String typeName;

    /**
     * 是否启用
     */
    private Boolean enable;

    /**
     * 登录工厂实现名称
     */
    private String loginFactoryServiceName;
}
