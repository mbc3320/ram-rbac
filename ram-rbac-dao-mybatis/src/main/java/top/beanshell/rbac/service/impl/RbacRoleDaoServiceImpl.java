package top.beanshell.rbac.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.beanshell.common.model.dto.PageQueryDTO;
import top.beanshell.common.model.dto.PageResultDTO;
import top.beanshell.mybatis.impl.CRUDDaoServiceImpl;
import top.beanshell.mybatis.util.PageUtil;
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
        LambdaQueryWrapper<RbacRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RbacRole::getRoleCode, roleCode);
        RbacRole rbacRole = getOne(wrapper);
        if (null != rbacRole) {
            return BeanUtil.toBean(rbacRole, RbacRoleDTO.class);
        }
        return null;
    }

    @Override
    public PageResultDTO<RbacRoleDTO> page(PageQueryDTO<RbacRoleQuery> pageQuery) {
        RbacRoleQuery params = pageQuery.getParams();
        LambdaQueryWrapper<RbacRole> wrapper = new LambdaQueryWrapper<>();

        if (null != params) {
            if (StringUtils.hasText(params.getRoleCode())) {
                wrapper.likeRight(RbacRole::getRoleCode, params.getRoleCode());
            }
            if (StringUtils.hasText(params.getRoleName())) {
                wrapper.likeRight(RbacRole::getRoleName, params.getRoleName());
            }
        }

        Page<RbacRole> page = PageUtil.getPage(pageQuery);
        page(page, wrapper);
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
        Page<RbacRoleUserCheckedDTO> page = PageUtil.getPage(pageQuery);
        page = baseMapper.authRolePage(page, pageQuery.getParams());
        return PageUtil.getPageResult(page, RbacRoleUserCheckedDTO.class);
    }
}
