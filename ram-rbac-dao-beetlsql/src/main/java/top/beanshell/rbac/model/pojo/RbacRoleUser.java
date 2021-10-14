package top.beanshell.rbac.model.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.beetl.sql.annotation.entity.Table;
import top.beanshell.beetlsql.model.pojo.BaseEntity;

/**
 *  角色用户关联信息
 * @author binchao
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "tb_rbac_role_user")
public class RbacRoleUser extends BaseEntity {

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 用户ID
     */
    private Long userId;
}
