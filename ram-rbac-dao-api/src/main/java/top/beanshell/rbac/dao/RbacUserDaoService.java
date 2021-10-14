package top.beanshell.rbac.dao;

import top.beanshell.common.model.dto.PageQueryDTO;
import top.beanshell.common.model.dto.PageResultDTO;
import top.beanshell.common.service.ServiceI;
import top.beanshell.rbac.model.dto.RbacUserDTO;
import top.beanshell.rbac.model.query.RbacUserQuery;

/**
 * 用户管理DAO业务接口
 * @author binchao
 */
public interface RbacUserDaoService extends ServiceI<RbacUserDTO> {

    /**
     * 分页查询
     * @param pageQuery   查询条件
     * @return            分页结果
     */
    PageResultDTO<RbacUserDTO> page(PageQueryDTO<RbacUserQuery> pageQuery);

    /**
     * 检测用户是否可用
     * @param userDTO    用户信息
     * @return           是否可用
     */
    boolean checkUserIfAvailable(RbacUserDTO userDTO);

    /**
     * 通过唯一键查询用户信息
     * @param account  唯一键： 账号、手机号、邮箱
     * @return         用户信息
     */
    RbacUserDTO getUserByUniqueKey(String account);
}
