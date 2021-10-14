package top.beanshell.rbac.service.impl;

import cn.hutool.core.bean.BeanUtil;
import org.beetl.sql.core.page.DefaultPageRequest;
import org.beetl.sql.core.page.PageRequest;
import org.beetl.sql.core.page.PageResult;
import org.beetl.sql.core.query.LambdaQuery;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import top.beanshell.beetlsql.service.impl.CRUDDaoServiceImpl;
import top.beanshell.beetlsql.util.PageUtil;
import top.beanshell.common.model.dto.PageQueryDTO;
import top.beanshell.common.model.dto.PageResultDTO;
import top.beanshell.rbac.common.model.enums.PermissionType;
import top.beanshell.rbac.dao.RbacRoleDaoService;
import top.beanshell.rbac.mapper.RbacRoleMapper;
import top.beanshell.rbac.model.dto.RbacRoleDTO;
import top.beanshell.rbac.model.dto.RbacRolePermissionCheckedDTO;
import top.beanshell.rbac.model.dto.RbacRoleUserCheckedDTO;
import top.beanshell.rbac.model.pojo.RbacRole;
import top.beanshell.rbac.model.query.RbacRoleQuery;
import top.beanshell.rbac.model.query.RbacRoleUserQuery;

import java.util.List;

/**
 * 角色管理DAO业务实现
 * @author binchao
 */
@Service
public class RbacRoleDaoServiceImpl
        extends CRUDDaoServiceImpl<RbacRoleDTO, RbacRole, RbacRoleMapper>
        implements RbacRoleDaoService {

    @Override
    public RbacRoleDTO getByCode(String roleCode) {
        Assert.hasText(roleCode, "roleCode必填");
        LambdaQuery<RbacRole> query = createLambdaQuery();
        query.andEq(RbacRole::getRoleCode, roleCode);
        RbacRole rbacRole = query.singleSimple();
        if (null != rbacRole) {
            return BeanUtil.toBean(rbacRole, RbacRoleDTO.class);
        }
        return null;
    }

    @Override
    public PageResultDTO<RbacRoleDTO> page(PageQueryDTO<RbacRoleQuery> pageQuery) {
        RbacRoleQuery params = pageQuery.getParams();
        LambdaQuery<RbacRole> lQuery = createLambdaQuery();

        if (null != params) {
            if (StringUtils.hasText(params.getRoleCode())) {
                lQuery.andLike(RbacRole::getRoleCode, params.getRoleCode() + "%");
            }
            if (StringUtils.hasText(params.getRoleName())) {
                lQuery.andLike(RbacRole::getRoleName, params.getRoleName() + "%");
            }
        }

        PageResult<RbacRole> page = lQuery.page(pageQuery.getCurrent(), pageQuery.getPageSize());
        return PageUtil.getPageResult(page, RbacRoleDTO.class);
    }

    @Override
    public List<String> findUserRoleCode(Long userId) {
        return baseMapper.findUserRoleCode(userId);
    }

    @Override
    public List<String> findUserPermissionCode(Long userId) {
        return baseMapper.findUserPermissionCode(userId);
    }

    @Override
    public List<RbacRolePermissionCheckedDTO> findRolePermission(Long roleId, PermissionType permissionType) {
        return baseMapper.findRolePermission(roleId, permissionType);
    }

    @Override
    public PageResultDTO<RbacRoleUserCheckedDTO> authRolePage(PageQueryDTO<RbacRoleUserQuery> pageQuery) {
        PageRequest pageRequest = DefaultPageRequest.of(pageQuery.getCurrent(), pageQuery.getPageSize());
        PageResult<RbacRoleUserCheckedDTO> result = baseMapper.authRolePage(pageQuery.getParams(), pageRequest);
        return PageUtil.getPageResult(result, RbacRoleUserCheckedDTO.class);
    }
}
