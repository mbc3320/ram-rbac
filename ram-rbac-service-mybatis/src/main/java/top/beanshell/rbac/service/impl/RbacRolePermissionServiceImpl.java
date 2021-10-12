package top.beanshell.rbac.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.beanshell.mybatis.impl.CRUDServiceImpl;
import top.beanshell.rbac.common.model.enums.PermissionType;
import top.beanshell.rbac.mapper.RbacRolePermissionMapper;
import top.beanshell.rbac.model.dto.RbacRolePermissionDTO;
import top.beanshell.rbac.model.pojo.RbacRolePermission;
import top.beanshell.rbac.service.RbacRolePermissionService;

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
        extends CRUDServiceImpl<RbacRolePermissionDTO, RbacRolePermission, RbacRolePermissionMapper> implements RbacRolePermissionService {

    @Override
    public boolean removeByRoleId(@NotNull Long roleId, @NotNull PermissionType permissionType) {
        LambdaQueryWrapper<RbacRolePermission> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RbacRolePermission::getRoleId, roleId)
                .eq(RbacRolePermission::getPermissionType, permissionType);

        return remove(wrapper);
    }

    @Override
    public boolean insertBatch(List<RbacRolePermissionDTO> batch) {
        List<RbacRolePermission> list = new ArrayList<>(batch.size());
        batch.forEach(rel -> {
            RbacRolePermission r = BeanUtil.toBean(rel, RbacRolePermission.class);
            list.add(r);
        });
        return saveBatch(list);
    }

    @Override
    public List<String> findRoleGrantedPermission(@NotNull Long roleId, @NotNull PermissionType permissionType) {
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
    public long countPermissionAuth(@NotNull Long permissionId) {
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
