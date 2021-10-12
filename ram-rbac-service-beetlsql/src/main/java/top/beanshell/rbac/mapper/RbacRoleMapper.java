package top.beanshell.rbac.mapper;

import org.beetl.sql.core.page.PageRequest;
import org.beetl.sql.core.page.PageResult;
import org.beetl.sql.mapper.BaseMapper;
import org.beetl.sql.mapper.annotation.Param;
import top.beanshell.rbac.common.model.enums.PermissionType;
import top.beanshell.rbac.model.dto.RbacRolePermissionCheckedDTO;
import top.beanshell.rbac.model.dto.RbacRoleUserCheckedDTO;
import top.beanshell.rbac.model.pojo.RbacRole;
import top.beanshell.rbac.model.query.RbacRoleUserQuery;

import java.util.List;

/**
 * 角色管理DAO
 * @author binchao
 */
public interface RbacRoleMapper extends BaseMapper<RbacRole> {
    /**
     * 通过用户ID查询角色编码列表
     * @param userId
     * @return
     */
    List<String> findUserRoleCode(@Param("userId") Long userId);

    /**
     * 通过用户ID查询权限编码列表
     * @param userId
     * @return
     */
    List<String> findUserPermissionCode(@Param("userId") Long userId);

    /**
     * 通过角色ID和权限类型查询权限列表
     * @param roleId
     * @param permissionType
     * @return
     */
    List<RbacRolePermissionCheckedDTO> findRolePermission(@Param("roleId") Long roleId,
                                                          @Param("permissionType") PermissionType permissionType);


    /**
     * 分页查询角色授权信息
     * @param params
     * @param pageRequest
     * @return
     */
    PageResult<RbacRoleUserCheckedDTO> authRolePage(@Param("params") RbacRoleUserQuery params, PageRequest pageRequest);
}
