package top.beanshell.rbac.model.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.beetl.sql.annotation.entity.Table;
import top.beanshell.beetlsql.model.pojo.BaseEntity;

/**
 * 角色信息
 * @author binchao
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "tb_rbac_role")
public class RbacRole extends BaseEntity {

    /**
     * 角色编码
     */
    private String roleCode;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 是否默认角色
     */
    private Boolean roleDefault;

    /**
     * 角色简介
     */
    private String roleDesc;
}
