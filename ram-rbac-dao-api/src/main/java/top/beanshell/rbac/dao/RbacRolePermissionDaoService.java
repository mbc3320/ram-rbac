package top.beanshell.rbac.dao;

import top.beanshell.common.service.ServiceI;
import top.beanshell.rbac.common.model.enums.PermissionType;
import top.beanshell.rbac.model.dto.RbacRolePermissionDTO;

import java.util.List;

/**
 * 角色权限管理DAO接口
 * @author binchao
 */
public interface RbacRolePermissionDaoService extends ServiceI<RbacRolePermissionDTO> {

    /**
     * 通过角色ID 权限类型撤销授权
     * @param roleId            角色ID
     * @param permissionType    权限类型
     * @return                  撤权结果
     */
    boolean removeByRoleId(Long roleId, PermissionType permissionType);

    /**
     * 批量保存授权信息
     * @param batch  授权列表
     * @return       授权结果
     */
    boolean saveBatch(List<RbacRolePermissionDTO> batch);

    /**
     * 查询角色已授权ID列表
     * @param roleId             角色ID
     * @param permissionType     权限类型
     * @return                   已获授权的权限ID列表
     */
    List<String> findRoleGrantedPermission(Long roleId, PermissionType permissionType);

    /**
     * 统计权限当前被授权次数（授权给一个角色记一次）
     * @param permissionId   权限ID
     * @return               已授权角色数量
     */
    long countPermissionAuth(Long permissionId);

    /**
     * 统计角色已授权权限数量
     * @param roleId       角色ID
     * @return             已授权权限数量
     */
    long countRolePermission(Long roleId);
}
