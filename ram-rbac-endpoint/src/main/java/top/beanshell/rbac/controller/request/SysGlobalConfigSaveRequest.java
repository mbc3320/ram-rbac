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
    @NotEmpty(message = "属性值必填")
    private String keyValue;
}
