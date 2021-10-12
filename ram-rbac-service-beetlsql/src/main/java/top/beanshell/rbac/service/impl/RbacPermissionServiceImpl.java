package top.beanshell.rbac.service.impl;

import cn.hutool.core.bean.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.beetl.sql.core.page.PageResult;
import org.beetl.sql.core.query.LambdaQuery;
import org.beetl.sql.mapper.BaseMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.beanshell.beetlsql.service.impl.CRUDServiceImpl;
import top.beanshell.beetlsql.util.PageUtil;
import top.beanshell.common.model.dto.PageQueryDTO;
import top.beanshell.common.model.dto.PageResultDTO;
import top.beanshell.rbac.common.exception.RbacPermissionException;
import top.beanshell.rbac.common.exception.code.RbacPermissionStatusCode;
import top.beanshell.rbac.mapper.RbacPermissionMapper;
import top.beanshell.rbac.model.dto.RbacPermissionDTO;
import top.beanshell.rbac.model.pojo.RbacPermission;
import top.beanshell.rbac.model.query.RbacPermissionQuery;
import top.beanshell.rbac.service.RbacPermissionService;
import top.beanshell.rbac.service.RbacRolePermissionService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 权限管理业务实现
 * @author binchao
 */
@Slf4j
@Service
public class RbacPermissionServiceImpl extends CRUDServiceImpl<RbacPermissionDTO, RbacPermission> implements RbacPermissionService {

    @Resource
    private RbacPermissionMapper permissionMapper;

    @Resource
    private RbacRolePermissionService rolePermissionService;

    @Override
    protected BaseMapper<RbacPermission> getDao() {
        return permissionMapper;
    }

    @Override
    public boolean saveEntity(RbacPermissionDTO dto) {
        // 检测根节点是否已存在
        if (null == dto.getPid()) {
            LambdaQuery<RbacPermission> query = createLambdaQuery();
            query.andIsNull(RbacPermission::getPid).andEq(RbacPermission::getPermissionType, dto.getPermissionType());
            long count = query.count();
            if (count > 0) {
                throw new RbacPermissionException(RbacPermissionStatusCode.PERMISSION_ROOT_NODE_IS_EXIST);
            }
        }
        return super.saveEntity(dto);
    }

    @Override
    public PageResultDTO<RbacPermissionDTO> page(PageQueryDTO<RbacPermissionQuery> pageQuery) {
        RbacPermissionQuery params = pageQuery.getParams();
        LambdaQuery<RbacPermission> lQuery = permissionMapper.createLambdaQuery();

        if (null != params) {
            if (null != params.getPermissionType()) {
                lQuery.andEq(RbacPermission::getPermissionType, params.getPermissionType());
            }
            if (StringUtils.hasText(params.getPermissionCode())) {
                lQuery.andLike(RbacPermission::getPermissionCode, params.getPermissionCode() + "%");
            }
        }

        PageResult<RbacPermission> page = lQuery.page(pageQuery.getCurrent(), pageQuery.getPageSize());
        return PageUtil.getPageResult(page, RbacPermissionDTO.class);
    }

    @Override
    public List<RbacPermissionDTO> list(RbacPermissionQuery query) {
        LambdaQuery<RbacPermission> lQuery = permissionMapper.createLambdaQuery();
        if (null != query) {
            if (null != query.getPermissionType()) {
                lQuery.andEq(RbacPermission::getPermissionType, query.getPermissionType());
            }
            if (StringUtils.hasText(query.getPermissionCode())) {
                lQuery.andEq(RbacPermission::getPermissionCode, query.getPermissionCode());
            }
            if (null != query.getPid()) {
                lQuery.andEq(RbacPermission::getPid, query.getPid());
            }
        }
        List<RbacPermission> list = lQuery.select();

        List<RbacPermissionDTO> dtos = new ArrayList<>();
        if (!list.isEmpty()) {
            for (RbacPermission p : list) {
                RbacPermissionDTO dto = BeanUtil.toBean(p, RbacPermissionDTO.class);
                dtos.add(dto);
            }
        }
        return dtos;
    }

    @Override
    public boolean removeById(Long id) {
        // 检测是否有授权信息
        long count = rolePermissionService.countPermissionAuth(id);

        if (count > 0) {
            throw new RbacPermissionException(RbacPermissionStatusCode.PERMISSION_IS_AUTH);
        }

        return super.removeById(id);
    }
}
