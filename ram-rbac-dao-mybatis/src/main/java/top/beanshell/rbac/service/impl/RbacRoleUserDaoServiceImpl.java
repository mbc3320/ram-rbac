package top.beanshell.rbac.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;
import top.beanshell.mybatis.impl.CRUDDaoServiceImpl;
import top.beanshell.rbac.dao.RbacRoleUserDaoService;
import top.beanshell.rbac.mapper.RbacRoleUserMapper;
import top.beanshell.rbac.model.dto.RbacRoleUserDTO;
import top.beanshell.rbac.model.pojo.RbacRoleUser;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色授权用户管理DAO业务实现
 * @author binchao
 */
@Service
public class RbacRoleUserDaoServiceImpl
        extends CRUDDaoServiceImpl<RbacRoleUserDTO, RbacRoleUser, RbacRoleUserMapper>
        implements RbacRoleUserDaoService {

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

    @Override
    public boolean removeByUserId(Long userId) {
        LambdaQueryWrapper<RbacRoleUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RbacRoleUser::getUserId, userId);
        remove(wrapper);
        // 用户有可能未获任何角色授权
        return true;
    }
}
