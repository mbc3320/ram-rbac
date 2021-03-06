package top.beanshell.rbac.service.impl;

import cn.hutool.core.lang.Assert;
import org.springframework.stereotype.Service;
import top.beanshell.common.service.I18nService;
import top.beanshell.common.service.impl.CRUDServiceImpl;
import top.beanshell.rbac.dao.RbacRoleUserDaoService;
import top.beanshell.rbac.model.dto.RbacRoleUserDTO;
import top.beanshell.rbac.service.RbacRoleUserService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 角色用户授权业务实现
 * @author binchao
 */
@Service
public class RbacRoleUserServiceImpl
        extends CRUDServiceImpl<RbacRoleUserDTO, RbacRoleUserDaoService>
        implements RbacRoleUserService {

    @Resource
    private I18nService i18nService;

    @Override
    public boolean removeByUniqueKey(Long roleId, Long userId) {
        Assert.notNull(roleId, i18nService.getMessage("i18n.request.valid.common.required", "roleId"));
        Assert.notNull(userId, i18nService.getMessage("i18n.request.valid.common.required", "userId"));
        return daoService.removeByUniqueKey(roleId, userId);
    }

    @Override
    public List<Long> findRoleUserIds(Long roleId) {
        Assert.notNull(roleId, i18nService.getMessage("i18n.request.valid.common.required", "roleId"));
        return daoService.findRoleUserIds(roleId);
    }

    @Override
    public long countRoleUserAuth(Long roleId) {
        Assert.notNull(roleId, i18nService.getMessage("i18n.request.valid.common.required", "roleId"));
        return daoService.countRoleUserAuth(roleId);
    }

    @Override
    public boolean removeByUserId(Long userId) {
        Assert.notNull(userId, i18nService.getMessage("i18n.request.valid.common.required", "userId"));
        return daoService.removeByUserId(userId);
    }
}
