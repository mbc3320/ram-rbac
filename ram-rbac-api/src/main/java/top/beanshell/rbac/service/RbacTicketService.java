package top.beanshell.rbac.service;


import top.beanshell.common.model.dto.PageQueryDTO;
import top.beanshell.common.model.dto.PageResultDTO;
import top.beanshell.common.service.ServiceI;
import top.beanshell.rbac.common.model.bo.TicketInfoBO;
import top.beanshell.rbac.model.dto.RbacCaptchaDTO;
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
     * @param ticket   ticket令牌
     * @return         ticket信息
     */
    TicketInfoBO get(String ticket);

    /**
     * 刷新ticket
     * @param ticket  ticket令牌
     * @return        刷新结果
     */
    boolean refresh(String ticket);

    /**
     * 销毁ticket
     * @param ticket  ticket令牌
     * @return        销毁结果
     */
    boolean destroy(String ticket);

    /**
     * 创建ticket
     * @param loginForm   登录参数
     * @return            ticket信息
     */
    TicketInfoBO create(UserLoginFormDTO loginForm);

    /**
     * 分页查询
     * @param pageQuery   分页条件
     * @return            查询结果
     */
    PageResultDTO<RbacTicketDTO> page(PageQueryDTO<RbacTicketQuery> pageQuery);

    /**
     * 通过用户ID查询存活的ticket信息
     * @param userId   用户ID
     * @return         ticket令牌列表
     */
    List<String> findUserAvailableTicket(Long userId);

    /**
     * 刷新ticket信息
     * @param ticketInfoBO   ticket信息
     * @return               更新结果
     */
    boolean updateTicketInfo(TicketInfoBO ticketInfoBO);

    /**
     * 获取ticket默认有效时长 (分钟)
     * @return   ticket有效期
     */
    long getTicketTimeout();

    /**
     * 踢出当前用户有效ticket
     * @param userId    用户ID
     * @return          踢出结果
     */
    boolean kickOutUserTickets(Long userId);

    /**
     * 通过ID踢出Ticket
     * @param id    ticket id
     * @return      踢出结果
     */
    boolean kickOutTicketById(Long id);

    /**
     * 创建登录验证码
     * @return  验证码信息
     */
    RbacCaptchaDTO captchaCreate();
}
