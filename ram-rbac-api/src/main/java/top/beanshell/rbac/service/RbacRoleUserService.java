package top.beanshell.rbac.service;

import top.beanshell.common.service.ServiceI;
import top.beanshell.rbac.model.dto.RbacRoleUserDTO;

import java.util.List;

/**
 * 角色授权用户管理接口
 * @author binchao
 */
public interface RbacRoleUserService extends ServiceI<RbacRoleUserDTO> {

    /**
     * 移除用户角色授权
     * @param roleId
     * @param userId
     * @return
     */
    boolean removeByUniqueKey(Long roleId, Long userId);

    /**
     * 通过角色ID查询已授权用户ID
     * @param roleId
     * @return
     */
    List<Long> findRoleUserIds(Long roleId);

    /**
     * 统计角色授权数量
     * @param roleId
     * @return
     */
    long countRoleUserAuth(Long roleId);
}
