package top.beanshell.rbac.service.impl;

import org.beetl.sql.core.query.LambdaQuery;
import org.springframework.stereotype.Service;
import top.beanshell.beetlsql.service.impl.CRUDDaoServiceImpl;
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
        LambdaQuery<RbacRoleUser> query = createLambdaQuery();
        query.andEq(RbacRoleUser::getRoleId, roleId)
                .andEq(RbacRoleUser::getUserId, userId);
        return query.delete() == 1;
    }

    @Override
    public List<Long> findRoleUserIds(Long roleId) {
        LambdaQuery<RbacRoleUser> query = createLambdaQuery();
        query.andEq(RbacRoleUser::getRoleId, roleId);
        List<RbacRoleUser> rels = query.select(RbacRoleUser::getUserId);
        List<Long> userIds = new ArrayList<>(rels.size());
        rels.forEach(rel -> userIds.add(rel.getUserId()));
        return userIds;
    }

    @Override
    public long countRoleUserAuth(Long roleId) {
        LambdaQuery<RbacRoleUser> query = createLambdaQuery();
        query.andEq(RbacRoleUser::getRoleId, roleId);
        return query.count();
    }

    @Override
    public boolean removeByUserId(Long userId) {
        LambdaQuery<RbacRoleUser> query = createLambdaQuery();
        query.andEq(RbacRoleUser::getUserId, userId);
        query.delete();
        // 用户有可能未获任何角色授权
        return true;
    }
}
