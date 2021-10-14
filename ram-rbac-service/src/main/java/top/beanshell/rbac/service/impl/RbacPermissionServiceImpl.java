package top.beanshell.rbac.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.beanshell.common.model.dto.PageQueryDTO;
import top.beanshell.common.model.dto.PageResultDTO;
import top.beanshell.common.service.impl.CRUDServiceImpl;
import top.beanshell.rbac.common.exception.RbacPermissionException;
import top.beanshell.rbac.common.exception.code.RbacPermissionStatusCode;
import top.beanshell.rbac.dao.RbacPermissionDaoService;
import top.beanshell.rbac.model.dto.RbacPermissionDTO;
import top.beanshell.rbac.model.query.RbacPermissionQuery;
import top.beanshell.rbac.service.RbacPermissionService;
import top.beanshell.rbac.service.RbacRolePermissionService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 权限管理业务实现
 * @author binchao
 */
@Slf4j
@Service
public class RbacPermissionServiceImpl extends CRUDServiceImpl<RbacPermissionDTO, RbacPermissionDaoService> implements RbacPermissionService {

    @Resource
    private RbacRolePermissionService rolePermissionService;

    @Override
    public boolean saveEntity(RbacPermissionDTO dto) {
        return daoService.saveEntity(dto);
    }

    @Override
    public PageResultDTO<RbacPermissionDTO> page(PageQueryDTO<RbacPermissionQuery> pageQuery) {
        return daoService.page(pageQuery);
    }

    @Override
    public List<RbacPermissionDTO> list(RbacPermissionQuery query) {
        return daoService.list(query);
    }

    @Override
    public boolean removeById(Long id) {
        // 检测是否有授权信息
        long count = rolePermissionService.countPermissionAuth(id);

        if (count > 0) {
            throw new RbacPermissionException(RbacPermissionStatusCode.PERMISSION_IS_AUTH);
        }

        return daoService.removeById(id);
    }
}
