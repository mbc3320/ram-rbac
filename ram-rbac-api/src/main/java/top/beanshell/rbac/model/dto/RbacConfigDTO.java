package top.beanshell.rbac.model.dto;

import lombok.*;
import top.beanshell.common.model.dto.BaseDTO;

/**
 * 系统k/v字典信息
 * @author binchao
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RbacConfigDTO extends BaseDTO {

    /**
     * 键名
     */
    private String keyCode;

    /**
     * 键值
     */
    private String keyValue;

    /**
     * 字典描述
     */
    private String keyDesc;
}
