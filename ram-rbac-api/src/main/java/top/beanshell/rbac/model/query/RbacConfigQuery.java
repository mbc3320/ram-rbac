package top.beanshell.rbac.model.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 系统kv字典查询条件
 * @author binchao
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RbacConfigQuery implements Serializable {

    /**
     * 键码
     */
    private String keyCode;
}
