package top.beanshell.rbac.dao;

import top.beanshell.common.model.dto.PageQueryDTO;
import top.beanshell.common.model.dto.PageResultDTO;
import top.beanshell.common.service.ServiceI;
import top.beanshell.rbac.common.model.enums.PermissionType;
import top.beanshell.rbac.model.dto.RbacRoleDTO;
import top.beanshell.rbac.model.dto.RbacRolePermissionCheckedDTO;
import top.beanshell.rbac.model.dto.RbacRoleUserCheckedDTO;
import top.beanshell.rbac.model.query.RbacRoleQuery;
import top.beanshell.rbac.model.query.RbacRoleUserQuery;

import java.util.List;

/**
 * 角色管理DAO业务接口
 * @author binchao
 */
public interface RbacRoleDaoService extends ServiceI<RbacRoleDTO> {

    /**
     * 通过角色编码查询角色信息
     * @param roleCode   角色编码
     * @return           角色信息
     */
    RbacRoleDTO getByCode(String roleCode);

    /**
     * 分页查询
     * @param pageQuery      查询条件
     * @return               分页结果
     */
    PageResultDTO<RbacRoleDTO> page(PageQueryDTO<RbacRoleQuery> pageQuery);

    /**
     * 通过用户ID查询用户已获授权角色编码列表
     * @param userId   用户ID
     * @return         角色编码列表
     */
    List<String> findUserRoleCode(Long userId);

    /**
     * 通过用户ID查询用户已获授权的权限编码列表
     * @param userId      用户ID
     * @return            权限编码列表
     */
    List<String> findUserPermissionCode(Long userId);

    /**
     * 通过角色ID、权限类型查询权限列表（附带是否已授权状态）
     * @param roleId           角色ID
     * @param permissionType   权限类型
     * @return                 权限列表
     */
    List<RbacRolePermissionCheckedDTO> findRolePermission(Long roleId, PermissionType permissionType);

    /**
     * 用户信息分页查询（含是否已授权角色状态）
     * @param pageQuery        查询条件
     * @return                 用户信息
     */
    PageResultDTO<RbacRoleUserCheckedDTO> authRolePage(PageQueryDTO<RbacRoleUserQuery> pageQuery);
}
