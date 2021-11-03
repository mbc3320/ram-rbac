package top.beanshell.rbac.controller.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
public class SysGlobalConfigSaveRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 属性值
     */
    @NotEmpty(message = "{i18n.request.valid.rbac.config-save.key-value}")
    private String keyValue;
}
