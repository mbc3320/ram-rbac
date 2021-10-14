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
 * 权限管理DAO实现-mybatis
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
            LambdaQueryWrapper<RbacPermission> wrapper = new LambdaQueryWrapper<>();
            wrapper.isNull(RbacPermission::getPid).eq(RbacPermission::getPermissionType, dto.getPermissionType());
            long count = count(wrapper);
            if (count > 0) {
                throw new RbacPermissionException(RbacPermissionStatusCode.PERMISSION_ROOT_NODE_IS_EXIST);
            }
        }
        return super.saveEntity(dto);
    }

    @Override
    public PageResultDTO<RbacPermissionDTO> page(PageQueryDTO<RbacPermissionQuery> pageQuery) {
        RbacPermissionQuery params = pageQuery.getParams();
        LambdaQueryWrapper<RbacPermission> wrapper = new LambdaQueryWrapper<>();

        if (null != params) {
            if (null != params.getPermissionType()) {
                wrapper.eq(RbacPermission::getPermissionType, params.getPermissionType());
            }
            if (StringUtils.hasText(params.getPermissionCode())) {
                wrapper.likeRight(RbacPermission::getPermissionCode, params.getPermissionCode());
            }
        }

        Page<RbacPermission> page = PageUtil.getPage(pageQuery);
        page(page, wrapper);
        return PageUtil.getPageResult(page, RbacPermissionDTO.class);
    }

    @Override
    public List<RbacPermissionDTO> list(RbacPermissionQuery query) {
        LambdaQueryWrapper<RbacPermission> wrapper = new LambdaQueryWrapper<>();
        if (null != query) {
            if (null != query.getPermissionType()) {
                wrapper.eq(RbacPermission::getPermissionType, query.getPermissionType());
            }
            if (StringUtils.hasText(query.getPermissionCode())) {
                wrapper.eq(RbacPermission::getPermissionCode, query.getPermissionCode());
            }
            if (null != query.getPid()) {
                wrapper.eq(RbacPermission::getPid, query.getPid());
            }
        }
        List<RbacPermission> list = list(wrapper);

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
