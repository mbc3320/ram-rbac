package top.beanshell.rbac.service;

import top.beanshell.common.model.dto.PageQueryDTO;
import top.beanshell.common.model.dto.PageResultDTO;
import top.beanshell.common.service.ServiceI;
import top.beanshell.rbac.model.bo.RbacSysGlobalConfigBO;
import top.beanshell.rbac.model.dto.RbacConfigDTO;
import top.beanshell.rbac.model.query.RbacConfigQuery;

/**
 * 系统k/v字典管理业务接口
 * @author binchao
 */
public interface RbacConfigService extends ServiceI<RbacConfigDTO> {

    /**
     * 分页查询
     * @param pageQuery
     * @return
     */
    PageResultDTO<RbacConfigDTO> page(PageQueryDTO<RbacConfigQuery> pageQuery);

    /**
     * 通过键码获取键值
     * @param keyCode
     * @return
     */
    RbacConfigDTO getByKeyCode(String keyCode);

    /**
     * 获取系统全局配置
     * @return
     */
    RbacSysGlobalConfigBO getGlobalConfig();
}
