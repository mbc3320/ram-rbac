package top.beanshell.rbac.controller.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * 系统k/v字典保存请求参数
 * @author binchao
 */
@Data
public class RbacConfigSaveRequest implements Serializable {

    /**
     * 键名
     */
    @NotEmpty(message = "{i18n.request.valid.rbac.config-save.key-code}")
    private String keyCode;

    /**
     * 键值
     */
    @NotEmpty(message = "{i18n.request.valid.rbac.config-save.key-value}")
    private String keyValue;

    /**
     * 字典描述
     */
    private String keyDesc;
}
