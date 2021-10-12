package top.beanshell.rbac.service.impl;

import cn.hutool.core.bean.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.beetl.sql.core.query.LambdaQuery;
import org.beetl.sql.mapper.BaseMapper;
import org.springframework.stereotype.Service;
import top.beanshell.beetlsql.service.impl.CRUDServiceImpl;
import top.beanshell.rbac.common.model.enums.PermissionType;
import top.beanshell.rbac.mapper.RbacRolePermissionMapper;
import top.beanshell.rbac.model.dto.RbacRolePermissionDTO;
import top.beanshell.rbac.model.pojo.RbacRolePermission;
import top.beanshell.rbac.service.RbacRolePermissionService;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * 角色权限授权管理业务实现
 * @author binchao
 */
@Slf4j
@Service
public class RbacRolePermissionServiceImpl
        extends CRUDServiceImpl<RbacRolePermissionDTO, RbacRolePermission>
        implements RbacRolePermissionService {

    @Resource
    private RbacRolePermissionMapper rolePermissionMapper;

    @Override
    protected BaseMapper<RbacRolePermission> getDao() {
        return rolePermissionMapper;
    }

    @Override
    public boolean removeByRoleId(@NotNull Long roleId, @NotNull PermissionType permissionType) {
        LambdaQuery<RbacRolePermission> query = getQuery();
        query.andEq(RbacRolePermission::getRoleId, roleId)
                .andEq(RbacRolePermission::getPermissionType, permissionType);

        return query.delete() == 1;
    }

    @Override
    public boolean insertBatch(List<RbacRolePermissionDTO> batch) {
        List<RbacRolePermission> list = new ArrayList<>(batch.size());
        batch.forEach(rel -> {
            RbacRolePermission r = BeanUtil.toBean(rel, RbacRolePermission.class);
            list.add(r);
        });
        rolePermissionMapper.insertBatch(list);
        return true;
    }

    @Override
    public List<String> findRoleGrantedPermission(@NotNull Long roleId, @NotNull PermissionType permissionType) {
        LambdaQuery<RbacRolePermission> query = getQuery();
        query.andEq(RbacRolePermission::getRoleId, roleId)
                .andEq(RbacRolePermission::getPermissionType, permissionType);

        List<RbacRolePermission> list = query.select();

        List<String> ids = new ArrayList<>(list.size());

        if (!list.isEmpty()) {
            list.forEach(rbacRolePermission -> ids.add(rbacRolePermission.getPermissionId().toString()));
        }

        return ids;
    }

    @Override
    public long countPermissionAuth(@NotNull Long permissionId) {
        LambdaQuery<RbacRolePermission> query = getQuery();
        query.andEq(RbacRolePermission::getPermissionId, permissionId);
        return query.count();
    }

    @Override
    public long countRolePermission(Long roleId) {
        LambdaQuery<RbacRolePermission> query = getQuery();
        query.andEq(RbacRolePermission::getRoleId, roleId);
        return query.count();
    }

    private LambdaQuery<RbacRolePermission> getQuery() {
        return rolePermissionMapper.createLambdaQuery();
    }

}
