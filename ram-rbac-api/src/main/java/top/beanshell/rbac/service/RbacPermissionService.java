package top.beanshell.rbac.service;


import top.beanshell.common.model.dto.PageQueryDTO;
import top.beanshell.common.model.dto.PageResultDTO;
import top.beanshell.common.service.ServiceI;
import top.beanshell.rbac.model.dto.RbacPermissionDTO;
import top.beanshell.rbac.model.query.RbacPermissionQuery;

import java.util.List;

/**
 * 权限管理业务接口
 * @author binchao
 */
public interface RbacPermissionService extends ServiceI<RbacPermissionDTO> {

    /**
     * 分页查询
     * @param pageQuery
     * @return
     */
    PageResultDTO<RbacPermissionDTO> page(PageQueryDTO<RbacPermissionQuery> pageQuery);

    /**
     *  获取权限列表
     * @param query
     * @return
     */
    List<RbacPermissionDTO> list(RbacPermissionQuery query);
}
