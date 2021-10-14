package top.beanshell.rbac.dao;

import top.beanshell.common.model.dto.PageQueryDTO;
import top.beanshell.common.model.dto.PageResultDTO;
import top.beanshell.common.service.ServiceI;
import top.beanshell.rbac.model.dto.RbacTicketDTO;
import top.beanshell.rbac.model.query.RbacTicketQuery;

import java.util.List;

/**
 * 用户凭证管理DAO业务接口
 * @author binchao
 */
public interface RbacTicketDaoService extends ServiceI<RbacTicketDTO> {

    /**
     * 通过ticket更新ticket信息
     * @param ticketInfo  凭证信息
     * @return            更新结果
     */
    boolean updateByTicket(RbacTicketDTO ticketInfo);

    /**
     * 分页查询
     * @param pageQuery   分页条件
     * @return            分页结果
     */
    PageResultDTO<RbacTicketDTO> page(PageQueryDTO<RbacTicketQuery> pageQuery);

    /**
     * 通过用户ID查询其当前有效凭证列表
     * @param userId   用户ID
     * @return         有效凭证列表
     */
    List<String> findUserAvailableTicket(Long userId);
}
