package top.beanshell.rbac.model.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.beanshell.mybatis.model.pojo.BaseEntity;

/**
 *  角色用户关联信息
 * @author binchao
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("tb_rbac_role_user")
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
