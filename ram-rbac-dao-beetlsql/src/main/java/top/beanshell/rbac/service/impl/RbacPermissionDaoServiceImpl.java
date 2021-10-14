package top.beanshell.rbac.service.impl;

import cn.hutool.core.bean.BeanUtil;
import org.beetl.sql.core.page.PageResult;
import org.beetl.sql.core.query.LambdaQuery;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.beanshell.beetlsql.service.impl.CRUDDaoServiceImpl;
import top.beanshell.beetlsql.util.PageUtil;
import top.beanshell.common.model.dto.PageQueryDTO;
import top.beanshell.common.model.dto.PageResultDTO;
import top.beanshell.rbac.common.exception.RbacPermissionException;
import top.beanshell.rbac.common.exception.code.RbacPermissionStatusCode;
import top.beanshell.rbac.dao.RbacPermissionDaoService;
import top.beanshell.rbac.mapper.RbacPermissionMapper;
import top.beanshell.rbac.model.dto.RbacPermissionDTO;
import top.beanshell.rbac.model.pojo.RbacPermission;
import top.beanshell.rbac.model.query.RbacPermissionQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限管理DAO实现-beetlsql
 * @author binchao
 */
@Service
public class RbacPermissionDaoServiceImpl
        extends CRUDDaoServiceImpl<RbacPermissionDTO, RbacPermission, RbacPermissionMapper>
        implements RbacPermissionDaoService {

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
        LambdaQuery<RbacPermission> lQuery = createLambdaQuery();

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
        LambdaQuery<RbacPermission> lQuery = createLambdaQuery();
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
}
