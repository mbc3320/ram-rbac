package top.beanshell.rbac.service;

import top.beanshell.common.service.ServiceI;
import top.beanshell.rbac.common.model.enums.PermissionType;
import top.beanshell.rbac.model.dto.RbacRolePermissionDTO;

import java.util.List;

/**
 * 角色授权权限管理业务接口
 * @author binchao
 */
public interface RbacRolePermissionService extends ServiceI<RbacRolePermissionDTO> {

    /**
     * 通过角色ID移除权限授权
     * @param roleId          角色ID
     * @param permissionType  权限类型
     * @return                撤权结果
     */
    boolean removeByRoleId(Long roleId, PermissionType permissionType);

    /**
     * 批量保存授权信息
     * @param batch    授权信息
     * @return         授权结果
     */
    boolean insertBatch(List<RbacRolePermissionDTO> batch);

    /**
     * 查询角色已授权权限列表
     * @param roleId           角色ID
     * @param permissionType   权限类型
     * @return                 角色已授权权限ID列表
     */
    List<String> findRoleGrantedPermission(Long roleId, PermissionType permissionType);

    /**
     * 统计权限授权角色数量
     * @param permissionId    权限ID
     * @return                已授权角色数量
     */
    long countPermissionAuth(Long permissionId);

    /**
     * 统计角色授权权限数量
     * @param roleId        角色ID
     * @return              已授权权限数量
     */
    long countRolePermission(Long roleId);
}
