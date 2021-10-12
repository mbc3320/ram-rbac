package top.beanshell.rbac.service;


import top.beanshell.common.model.dto.PageQueryDTO;
import top.beanshell.common.model.dto.PageResultDTO;
import top.beanshell.common.service.ServiceI;
import top.beanshell.rbac.common.model.bo.TicketInfoBO;
import top.beanshell.rbac.model.dto.RbacTicketDTO;
import top.beanshell.rbac.model.dto.UserLoginFormDTO;
import top.beanshell.rbac.model.query.RbacTicketQuery;

import java.util.List;

/**
 * Ticket业务接口
 * @author binchao
 */
public interface RbacTicketService extends ServiceI<RbacTicketDTO> {

    /**
     * 获取ticket信息
     * @param ticket
     * @return
     */
    TicketInfoBO get(String ticket);

    /**
     * 刷新ticket
     * @param ticket
     * @return
     */
    boolean refresh(String ticket);

    /**
     * 销毁ticket
     * @param ticket
     * @return
     */
    boolean destroy(String ticket);

    /**
     * 创建ticket
     * @param loginForm
     * @return
     */
    TicketInfoBO create(UserLoginFormDTO loginForm);

    /**
     * 分页查询
     * @param pageQuery
     * @return
     */
    PageResultDTO<RbacTicketDTO> page(PageQueryDTO<RbacTicketQuery> pageQuery);

    /**
     * 通过用户ID查询存活的ticket信息
     * @param userId
     * @return
     */
    List<String> findUserAvailableTicket(Long userId);

    /**
     * 刷新ticket信息
     * @param ticketInfoBO
     * @return
     */
    boolean updateTicketInfo(TicketInfoBO ticketInfoBO);

    /**
     * 获取ticket默认有效时长 (分钟)
     * @return
     */
    long getTicketTimeout();

    /**
     * 踢出当前用户有效ticket
     * @param userId
     * @return
     */
    boolean kickOutUserTickets(Long userId);
}
