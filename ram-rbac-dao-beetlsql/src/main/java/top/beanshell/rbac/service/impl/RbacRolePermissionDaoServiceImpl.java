package top.beanshell.rbac.service.impl;

import cn.hutool.core.bean.BeanUtil;
import org.beetl.sql.core.query.LambdaQuery;
import org.springframework.stereotype.Service;
import top.beanshell.beetlsql.service.impl.CRUDDaoServiceImpl;
import top.beanshell.rbac.common.model.enums.PermissionType;
import top.beanshell.rbac.dao.RbacRolePermissionDaoService;
import top.beanshell.rbac.mapper.RbacRolePermissionMapper;
import top.beanshell.rbac.model.dto.RbacRolePermissionDTO;
import top.beanshell.rbac.model.pojo.RbacRolePermission;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色权限管理DAO接口实现-beetlsql
 * @author binchao
 */
@Service
public class RbacRolePermissionDaoServiceImpl
        extends CRUDDaoServiceImpl<RbacRolePermissionDTO, RbacRolePermission, RbacRolePermissionMapper>
        implements RbacRolePermissionDaoService {


    @Override
    public boolean removeByRoleId(Long roleId, PermissionType permissionType) {
        LambdaQuery<RbacRolePermission> query = createLambdaQuery();
        query.andEq(RbacRolePermission::getRoleId, roleId)
                .andEq(RbacRolePermission::getPermissionType, permissionType);

        return query.delete() == 1;
    }

    @Override
    public boolean saveBatch(List<RbacRolePermissionDTO> batch) {
        List<RbacRolePermission> list = new ArrayList<>(batch.size());
        batch.forEach(rel -> {
            RbacRolePermission r = BeanUtil.toBean(rel, RbacRolePermission.class);
            r.init();
            list.add(r);
        });
        baseMapper.insertBatch(list);
        return true;
    }

    @Override
    public List<String> findRoleGrantedPermission(Long roleId, PermissionType permissionType) {
        LambdaQuery<RbacRolePermission> query = createLambdaQuery();
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
    public long countPermissionAuth(Long permissionId) {
        LambdaQuery<RbacRolePermission> query = createLambdaQuery();
        query.andEq(RbacRolePermission::getPermissionId, permissionId);
        return query.count();
    }

    @Override
    public long countRolePermission(Long roleId) {
        LambdaQuery<RbacRolePermission> query = createLambdaQuery();
        query.andEq(RbacRolePermission::getRoleId, roleId);
        return query.count();
    }
}
