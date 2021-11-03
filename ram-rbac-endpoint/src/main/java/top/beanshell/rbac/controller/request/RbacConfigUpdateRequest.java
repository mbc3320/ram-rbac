package top.beanshell.rbac.controller.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * 系统kv字典更新请求参数
 * @author binchao
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RbacConfigUpdateRequest extends RbacConfigSaveRequest{

    /**
     * id
     */
    @NotNull(message = "{i18n.request.valid.id}")
    private Long id;
}
