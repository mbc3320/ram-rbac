package top.beanshell.rbac.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
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
     * @param userId    用户ID
     * @return          用户角色编码列表
     */
    List<String> findUserRoleCode(@Param("userId") Long userId);

    /**
     * 通过用户ID查询权限编码列表
     * @param userId    用户ID
     * @return          用户权限编码列表
     */
    List<String> findUserPermissionCode(@Param("userId") Long userId);

    /**
     * 通过角色ID和权限类型查询权限列表
     * @param roleId          角色ID
     * @param permissionType  权限类型
     * @return                权限信息列表
     */
    List<RbacRolePermissionCheckedDTO> findRolePermission(@Param("roleId") Long roleId,
                                                          @Param("permissionType") PermissionType permissionType);


    /**
     * 分页查询角色授权信息
     * @param page      分页条件
     * @param params    查询条件
     * @return          用户信息分页结果
     */
    Page<RbacRoleUserCheckedDTO> authRolePage(Page<RbacRoleUserCheckedDTO> page, @Param("params") RbacRoleUserQuery params);
}
