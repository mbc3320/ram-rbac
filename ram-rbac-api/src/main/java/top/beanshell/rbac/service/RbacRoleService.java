package top.beanshell.rbac.service;


import top.beanshell.common.model.dto.PageQueryDTO;
import top.beanshell.common.model.dto.PageResultDTO;
import top.beanshell.common.model.vo.AntTreeNodeVO;
import top.beanshell.common.service.ServiceI;
import top.beanshell.rbac.common.model.enums.PermissionType;
import top.beanshell.rbac.model.dto.RbacRoleDTO;
import top.beanshell.rbac.model.dto.RbacRoleUserCheckedDTO;
import top.beanshell.rbac.model.query.RbacRolePermissionQuery;
import top.beanshell.rbac.model.query.RbacRoleQuery;
import top.beanshell.rbac.model.query.RbacRoleUserQuery;

import java.util.List;

/**
 * 角色管理业务接口
 * @author binchao
 */
public interface RbacRoleService extends ServiceI<RbacRoleDTO> {


    /**
     * 授权角色给用户
     * @param roleId
     * @param userId
     * @return
     */
    boolean grantUser(Long roleId, Long userId);

    /**
     * 撤销用户角色授权
     * @param roleId
     * @param userId
     * @return
     */
    boolean revokeUser(Long roleId, Long userId);


    /**
     * 授权权限给角色->处理数据库数据
     * @param roleId
     * @param permissionIds
     * @param permissionType
     * @return
     */
    boolean grantPermissionBefore(Long roleId, List<Long> permissionIds, PermissionType permissionType);

    /**
     * 授权权限给角色->更新存活ticket缓存
     * @param roleId
     * @param permissionIds
     * @param permissionType
     * @return
     */
    boolean grantPermissionAfter(Long roleId, List<Long> permissionIds, PermissionType permissionType);


    /**
     * 查询角色已授权资源
     * @param roleId
     * @param permissionType
     * @return
     */
    List<String> findRoleGrantedPermission(Long roleId, PermissionType permissionType);

    /**
     * 通过编码查询角色信息
     * @param roleCode
     * @return
     */
    RbacRoleDTO getByCode(String roleCode);

    /**
     * 分页查询
     * @param pageQuery
     * @return
     */
    PageResultDTO<RbacRoleDTO> page(PageQueryDTO<RbacRoleQuery> pageQuery);

    /**
     * 通过用户ID查询角色编码
     * @param userId
     * @return
     */
    List<String> findUserRoleCode(Long userId);

    /**
     * 通过用户ID查询权限编码
     * @param userId
     * @return
     */
    List<String> findUserPermissionCode(Long userId);

    /**
     * 更新用户缓存权限
     * @param userId
     * @return
     */
    boolean updateUserPermissionCacheByUserId(Long userId);

    /**
     * 查询角色权限树
     * @param query
     * @return
     */
    AntTreeNodeVO findRolePermissionTree(RbacRolePermissionQuery query);

    /**
     * 角色授权用户信息分页查询
     * @param pageQuery
     * @return
     */
    PageResultDTO<RbacRoleUserCheckedDTO> authRolePage(PageQueryDTO<RbacRoleUserQuery> pageQuery);
}
