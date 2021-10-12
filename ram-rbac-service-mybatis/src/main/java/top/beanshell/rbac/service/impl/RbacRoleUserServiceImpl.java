package top.beanshell.rbac.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;
import top.beanshell.mybatis.impl.CRUDServiceImpl;
import top.beanshell.rbac.mapper.RbacRoleUserMapper;
import top.beanshell.rbac.model.dto.RbacRoleUserDTO;
import top.beanshell.rbac.model.pojo.RbacRoleUser;
import top.beanshell.rbac.service.RbacRoleUserService;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色用户授权业务实现
 * @author binchao
 */
@Service
public class RbacRoleUserServiceImpl
        extends CRUDServiceImpl<RbacRoleUserDTO, RbacRoleUser, RbacRoleUserMapper>
        implements RbacRoleUserService {

    @Override
    public boolean removeByUniqueKey(Long roleId, Long userId) {
        LambdaQueryWrapper<RbacRoleUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RbacRoleUser::getRoleId, roleId)
                .eq(RbacRoleUser::getUserId, userId);
        return remove(wrapper);
    }

    @Override
    public List<Long> findRoleUserIds(Long roleId) {
        LambdaQueryWrapper<RbacRoleUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(RbacRoleUser::getUserId);
        wrapper.eq(RbacRoleUser::getRoleId, roleId);
        List<RbacRoleUser> rels = list(wrapper);
        List<Long> userIds = new ArrayList<>(rels.size());
        rels.forEach(rel -> userIds.add(rel.getUserId()));
        return userIds;
    }

    @Override
    public long countRoleUserAuth(Long roleId) {
        LambdaQueryWrapper<RbacRoleUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RbacRoleUser::getRoleId, roleId);
        return count(wrapper);
    }
}
