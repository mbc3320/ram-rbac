package top.beanshell.rbac.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.beanshell.common.service.impl.CRUDServiceImpl;
import top.beanshell.rbac.common.model.enums.PermissionType;
import top.beanshell.rbac.dao.RbacRolePermissionDaoService;
import top.beanshell.rbac.model.dto.RbacRolePermissionDTO;
import top.beanshell.rbac.service.RbacRolePermissionService;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 角色权限授权管理业务实现
 * @author binchao
 */
@Slf4j
@Service
public class RbacRolePermissionServiceImpl
        extends CRUDServiceImpl<RbacRolePermissionDTO, RbacRolePermissionDaoService> implements RbacRolePermissionService {

    @Override
    public boolean removeByRoleId(@NotNull Long roleId, @NotNull PermissionType permissionType) {
        return daoService.removeByRoleId(roleId, permissionType);
    }

    @Override
    public boolean insertBatch(List<RbacRolePermissionDTO> batch) {
        return daoService.saveBatch(batch);
    }

    @Override
    public List<String> findRoleGrantedPermission(@NotNull Long roleId, @NotNull PermissionType permissionType) {
        return daoService.findRoleGrantedPermission(roleId, permissionType);
    }

    @Override
    public long countPermissionAuth(@NotNull Long permissionId) {
        return daoService.countPermissionAuth(permissionId);
    }

    @Override
    public long countRolePermission(Long roleId) {
        return daoService.countRolePermission(roleId);
    }
}
