package top.beanshell.rbac.service.impl;

import cn.hutool.core.bean.BeanUtil;
import org.beetl.sql.core.query.LambdaQuery;
import org.beetl.sql.mapper.BaseMapper;
import org.springframework.stereotype.Service;
import top.beanshell.beetlsql.service.impl.CRUDServiceImpl;
import top.beanshell.rbac.mapper.RbacRoleUserMapper;
import top.beanshell.rbac.model.dto.RbacRoleUserDTO;
import top.beanshell.rbac.model.pojo.RbacRoleUser;
import top.beanshell.rbac.service.RbacRoleUserService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 角色用户授权业务实现
 * @author binchao
 */
@Service
public class RbacRoleUserServiceImpl
        extends CRUDServiceImpl<RbacRoleUserDTO, RbacRoleUser>
        implements RbacRoleUserService {

    @Resource
    private RbacRoleUserMapper roleUserMapper;

    @Override
    protected BaseMapper<RbacRoleUser> getDao() {
        return roleUserMapper;
    }

    @Override
    public boolean removeByUniqueKey(Long roleId, Long userId) {
        LambdaQuery<RbacRoleUser> query = getQuery();
        query.andEq(RbacRoleUser::getRoleId, roleId)
                .andEq(RbacRoleUser::getUserId, userId);
        return query.delete() == 1;
    }

    @Override
    public boolean saveEntity(RbacRoleUserDTO roleUser) {
        RbacRoleUser rel = BeanUtil.toBean(roleUser, RbacRoleUser.class);
        roleUserMapper.insert(rel);
        return true;
    }

    @Override
    public List<Long> findRoleUserIds(Long roleId) {
        LambdaQuery<RbacRoleUser> query = getQuery();
        query.andEq(RbacRoleUser::getRoleId, roleId);
        List<RbacRoleUser> rels = query.select(RbacRoleUser::getUserId);
        List<Long> userIds = new ArrayList<>(rels.size());
        rels.forEach(rel -> userIds.add(rel.getUserId()));
        return userIds;
    }

    @Override
    public long countRoleUserAuth(Long roleId) {
        LambdaQuery<RbacRoleUser> query = getQuery();
        query.andEq(RbacRoleUser::getRoleId, roleId);
        return query.count();
    }

    private LambdaQuery<RbacRoleUser> getQuery() {
        return roleUserMapper.createLambdaQuery();
    }
}
