package top.beanshell.rbac.model.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.beanshell.mybatis.model.pojo.BaseEntity;
import top.beanshell.rbac.common.model.enums.PermissionType;


/**
 * 权限信息
 * @author binchao
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("tb_rbac_permission")
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
