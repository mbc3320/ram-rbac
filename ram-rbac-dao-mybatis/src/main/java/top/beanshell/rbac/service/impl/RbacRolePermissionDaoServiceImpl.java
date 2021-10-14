package top.beanshell.rbac.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;
import top.beanshell.mybatis.impl.CRUDDaoServiceImpl;
import top.beanshell.rbac.common.model.enums.PermissionType;
import top.beanshell.rbac.dao.RbacRolePermissionDaoService;
import top.beanshell.rbac.mapper.RbacRolePermissionMapper;
import top.beanshell.rbac.model.dto.RbacRolePermissionDTO;
import top.beanshell.rbac.model.pojo.RbacRolePermission;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色权限管理DAO接口实现-mybatis
 * @author binchao
 */
@Service
public class RbacRolePermissionDaoServiceImpl
        extends CRUDDaoServiceImpl<RbacRolePermissionDTO, RbacRolePermission, RbacRolePermissionMapper>
        implements RbacRolePermissionDaoService {


    @Override
    public boolean removeByRoleId(Long roleId, PermissionType permissionType) {
        LambdaQueryWrapper<RbacRolePermission> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RbacRolePermission::getRoleId, roleId)
                .eq(RbacRolePermission::getPermissionType, permissionType);

        return remove(wrapper);
    }

    @Override
    public boolean saveBatch(List<RbacRolePermissionDTO> batch) {
        List<RbacRolePermission> list = new ArrayList<>(batch.size());
        batch.forEach(rel -> {
            RbacRolePermission r = BeanUtil.toBean(rel, RbacRolePermission.class);
            r.init();
            list.add(r);
        });
        return super.saveBatch(list);
    }

    @Override
    public List<String> findRoleGrantedPermission(Long roleId, PermissionType permissionType) {
        LambdaQueryWrapper<RbacRolePermission> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RbacRolePermission::getRoleId, roleId)
                .eq(RbacRolePermission::getPermissionType, permissionType);

        List<RbacRolePermission> list = list(wrapper);

        List<String> ids = new ArrayList<>(list.size());

        if (!list.isEmpty()) {
            list.forEach(rbacRolePermission -> ids.add(rbacRolePermission.getPermissionId().toString()));
        }

        return ids;
    }

    @Override
    public long countPermissionAuth(Long permissionId) {
        LambdaQueryWrapper<RbacRolePermission> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RbacRolePermission::getPermissionId, permissionId);
        return count(wrapper);
    }

    @Override
    public long countRolePermission(Long roleId) {
        LambdaQueryWrapper<RbacRolePermission> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RbacRolePermission::getRoleId, roleId);
        return count(wrapper);
    }
}
