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
     * @param roleId
     * @param permissionType
     * @return
     */
    boolean removeByRoleId(Long roleId, PermissionType permissionType);

    /**
     * 批量保存授权信息
     * @param batch
     * @return
     */
    boolean insertBatch(List<RbacRolePermissionDTO> batch);

    /**
     * 查询角色已授权权限列表
     * @param roleId
     * @param permissionType
     * @return
     */
    List<String> findRoleGrantedPermission(Long roleId, PermissionType permissionType);

    /**
     * 统计权限授权角色数量
     * @param permissionId
     * @return
     */
    long countPermissionAuth(Long permissionId);

    /**
     * 统计角色授权权限数量
     * @param roleId
     * @return
     */
    long countRolePermission(Long roleId);
}
