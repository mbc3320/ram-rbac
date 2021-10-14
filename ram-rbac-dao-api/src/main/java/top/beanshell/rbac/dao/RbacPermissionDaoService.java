package top.beanshell.rbac.dao;

import top.beanshell.common.model.dto.PageQueryDTO;
import top.beanshell.common.model.dto.PageResultDTO;
import top.beanshell.common.service.ServiceI;
import top.beanshell.rbac.model.dto.RbacPermissionDTO;
import top.beanshell.rbac.model.query.RbacPermissionQuery;

import java.util.List;


/**
 * 权限管理DAO业务接口
 * @author binchao
 */
public interface RbacPermissionDaoService extends ServiceI<RbacPermissionDTO> {
    /**
     * 分页查询
     * @param pageQuery     查询条件
     * @return              分页结果
     */
    PageResultDTO<RbacPermissionDTO> page(PageQueryDTO<RbacPermissionQuery> pageQuery);

    /**
     * 权限列表
     * @param query   查询条件
     * @return        权限列表
     */
    List<RbacPermissionDTO> list(RbacPermissionQuery query);
}
