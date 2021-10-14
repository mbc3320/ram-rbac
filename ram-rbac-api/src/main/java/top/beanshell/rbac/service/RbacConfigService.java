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
     * @param pageQuery   分页条件
     * @return            分页结果
     */
    PageResultDTO<RbacConfigDTO> page(PageQueryDTO<RbacConfigQuery> pageQuery);

    /**
     * 通过键码获取键值
     * @param keyCode    键码
     * @return           字典信息
     */
    RbacConfigDTO getByKeyCode(String keyCode);

    /**
     * 获取系统全局配置
     * @return  全局设定
     */
    RbacSysGlobalConfigBO getGlobalConfig();
}
