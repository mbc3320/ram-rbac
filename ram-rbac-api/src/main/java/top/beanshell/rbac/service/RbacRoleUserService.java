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
     * @param roleId    角色ID
     * @param userId    角色ID
     * @return          撤权结果
     */
    boolean removeByUniqueKey(Long roleId, Long userId);

    /**
     * 通过角色ID查询已授权用户ID
     * @param roleId   角色ID
     * @return         角色已授权用户ID列表
     */
    List<Long> findRoleUserIds(Long roleId);

    /**
     * 统计角色授权数量
     * @param roleId   角色ID
     * @return         统计结果
     */
    long countRoleUserAuth(Long roleId);

    /**
     * 撤销用户所有角色授权
     * @param userId   用户ID
     * @return         撤权结果
     */
    boolean removeByUserId(Long userId);
}
