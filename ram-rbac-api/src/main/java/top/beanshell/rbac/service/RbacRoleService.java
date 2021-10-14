package top.beanshell.rbac.service;


import top.beanshell.common.model.dto.PageQueryDTO;
import top.beanshell.common.model.dto.PageResultDTO;
import top.beanshell.common.model.vo.AntTreeNodeVO;
import top.beanshell.common.service.ServiceI;
import top.beanshell.rbac.common.model.enums.PermissionType;
import top.beanshell.rbac.model.dto.RbacRoleDTO;
import top.beanshell.rbac.model.dto.RbacRolePermissionCheckedDTO;
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
     * @param roleId    角色ID
     * @param userId    用户ID
     * @return          授权结果
     */
    boolean grantUser(Long roleId, Long userId);

    /**
     * 撤销用户角色授权
     * @param roleId   角色ID
     * @param userId   用户ID
     * @return         撤权结果
     */
    boolean revokeUser(Long roleId, Long userId);


    /**
     * 授权权限给角色-处理数据库数据
     * @param roleId          角色ID
     * @param permissionIds   权限ID列表
     * @param permissionType  权限类型
     * @return                授权结果
     */
    boolean grantPermission(Long roleId, List<Long> permissionIds, PermissionType permissionType);

    /**
     * 刷新角色关联的用户存活ticket缓存
     * @param roleId    角色ID
     * @return          处理结果
     */
    boolean refreshRoleUserPermission(Long roleId);


    /**
     * 查询角色已授权资源
     * @param roleId          角色ID
     * @param permissionType  权限类型
     * @return                角色已获授权的权限编码
     */
    List<String> findRoleGrantedPermission(Long roleId, PermissionType permissionType);

    /**
     * 通过编码查询角色信息
     * @param roleCode    角色编码
     * @return            角色信息
     */
    RbacRoleDTO getByCode(String roleCode);

    /**
     * 分页查询
     * @param pageQuery   分页查询条件
     * @return            分页结果
     */
    PageResultDTO<RbacRoleDTO> page(PageQueryDTO<RbacRoleQuery> pageQuery);

    /**
     * 通过用户ID查询角色编码
     * @param userId   用户ID
     * @return         用户已获授权的角色编码
     */
    List<String> findUserRoleCode(Long userId);

    /**
     * 通过用户ID查询权限编码
     * @param userId   用户ID
     * @return         用户已获授权的权限编码
     */
    List<String> findUserPermissionCode(Long userId);

    /**
     * 更新用户缓存权限
     * @param userId   用户ID
     * @return         刷新结果
     */
    boolean updateUserPermissionCacheByUserId(Long userId);

    /**
     * 查询角色权限树
     * @param query    查询条件
     * @return         权限树
     */
    AntTreeNodeVO findRolePermissionTree(RbacRolePermissionQuery query);


    /**
     * 查询角色权限列表（含是否授权状态）
     * @param roleId             角色ID
     * @param permissionType     权限类型
     * @return                   权限列表
     */
    List<RbacRolePermissionCheckedDTO> findRolePermission(Long roleId, PermissionType permissionType);

    /**
     * 角色授权用户信息分页查询
     * @param pageQuery   分页参数
     * @return            分页结果
     */
    PageResultDTO<RbacRoleUserCheckedDTO> authRolePage(PageQueryDTO<RbacRoleUserQuery> pageQuery);


    /**
     * 撤销用户所有角色授权
     * @param userId   用户ID
     * @return         处理结果
     */
    boolean revokeUserAllRole(Long userId);
}
