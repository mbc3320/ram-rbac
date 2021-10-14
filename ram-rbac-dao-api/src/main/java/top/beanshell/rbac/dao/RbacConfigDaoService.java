package top.beanshell.rbac.dao;

import top.beanshell.common.model.dto.PageQueryDTO;
import top.beanshell.common.model.dto.PageResultDTO;
import top.beanshell.common.service.ServiceI;
import top.beanshell.rbac.model.dto.RbacConfigDTO;
import top.beanshell.rbac.model.query.RbacConfigQuery;

/**
 * 系统KV字典DAO业务接口
 * @author binchao
 */
public interface RbacConfigDaoService extends ServiceI<RbacConfigDTO> {

    /**
     * 通过keyCode查询字典信息
     * @param keyCode   键码
     * @return          字典信息
     */
    RbacConfigDTO getByCode(String keyCode);

    /**
     * 分页查询
     * @param pageQuery      查询条件
     * @return               分页结果
     */
    PageResultDTO<RbacConfigDTO> page(PageQueryDTO<RbacConfigQuery> pageQuery);
}
