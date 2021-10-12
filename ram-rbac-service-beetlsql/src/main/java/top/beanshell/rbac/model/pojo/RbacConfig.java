package top.beanshell.rbac.model.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.beetl.sql.annotation.entity.Table;
import top.beanshell.beetlsql.model.pojo.BaseEntity;

/**
 * 系统k/v字典信息
 * @author binchao
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "tb_rbac_config")
public class RbacConfig extends BaseEntity {

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
