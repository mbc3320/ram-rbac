package top.beanshell.rbac.model.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.beetl.sql.annotation.entity.Table;
import top.beanshell.beetlsql.model.pojo.BaseEntity;
import top.beanshell.rbac.common.model.enums.PermissionType;


/**
 * 权限信息
 * @author binchao
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "tb_rbac_permission")
public class RbacPermission extends BaseEntity {

    /**
     * 权限类型
     */
    private PermissionType permissionType;

    /**
     * 权限编码
     */
    private String permissionCode;

    /**
     * 权限名称
     */
    private String permissionName;

    /**
     * 上级权限ID
     */
    private Long pid;

}
