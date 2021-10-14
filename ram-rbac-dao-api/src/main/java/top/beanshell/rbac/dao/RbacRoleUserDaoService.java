package top.beanshell.rbac.dao;

import top.beanshell.common.service.ServiceI;
import top.beanshell.rbac.model.dto.RbacRoleUserDTO;

import java.util.List;

/**
 * 角色授权用户管理DAO业务接口
 * @author binchao
 */
public interface RbacRoleUserDaoService extends ServiceI<RbacRoleUserDTO> {

    /**
     * 通过唯一键撤权
     * @param roleId   角色ID
     * @param userId   用户ID
     * @return         撤权结果
     */
    boolean removeByUniqueKey(Long roleId, Long userId);

    /**
     * 通过角色ID查询已授权用户ID列表
     * @param roleId    角色ID
     * @return          用户ID列表
     */
    List<Long> findRoleUserIds(Long roleId);

    /**
     * 统计角色已授权用户数量
     * @param roleId   角色ID
     * @return         已授权用户数量
     */
    long countRoleUserAuth(Long roleId);

    /**
     * 通过用户ID撤销所有角色权限
     * @param userId   用户ID
     * @return         撤权结果
     */
    boolean removeByUserId(Long userId);
}
