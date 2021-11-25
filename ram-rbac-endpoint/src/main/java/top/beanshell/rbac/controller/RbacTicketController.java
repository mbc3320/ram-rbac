package top.beanshell.rbac.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.beanshell.common.annotation.Authorization;
import top.beanshell.common.annotation.Ticket;
import top.beanshell.common.model.dto.PageQueryDTO;
import top.beanshell.common.model.dto.PageResultDTO;
import top.beanshell.common.utils.JSON;
import top.beanshell.rbac.common.model.bo.TicketInfoBO;
import top.beanshell.rbac.controller.request.RbacChangeCurrentUserPasswordRequest;
import top.beanshell.rbac.controller.request.RbacTicketCreateRequest;
import top.beanshell.rbac.model.dto.RbacCaptchaDTO;
import top.beanshell.rbac.controller.vo.TicketInfoVO;
import top.beanshell.rbac.model.dto.RbacTicketDTO;
import top.beanshell.rbac.model.dto.UserLoginFormDTO;
import top.beanshell.rbac.model.query.RbacTicketQuery;
import top.beanshell.rbac.service.RbacTicketService;
import top.beanshell.rbac.service.RbacUserService;
import top.beanshell.web.controller.BaseController;
import top.beanshell.web.controller.request.PrimaryKeyRequest;
import top.beanshell.web.util.IpAddrUtil;
import top.beanshell.web.vo.BaseRequest;
import top.beanshell.web.vo.BaseResponse;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * ticket管理接口
 * @author binchao
 */
@RestController
@RequestMapping("/rbac/ticket")
public class RbacTicketController extends BaseController {

    @Resource
    private RbacTicketService ticketService;

    @Resource
    private RbacUserService userService;

    /**
     * 创建ticket
     * @param request  request parameter
     * @param req      servlet request
     * @return         ticket info
     */
    @PostMapping("/create")
    @Authorization(valid = false)
    public BaseResponse<TicketInfoVO> create(@Valid @RequestBody BaseRequest<RbacTicketCreateRequest> request,
                                             HttpServletRequest req) {
        UserLoginFormDTO loginFormDTO = BeanUtil.toBean(request.getData(), UserLoginFormDTO.class);
        loginFormDTO.setIpAddress(IpAddrUtil.getRealIp(req));
        String userAgent = req.getHeader("User-Agent");
        loginFormDTO.setUserAgent(userAgent);
        UserAgent ua = UserAgentUtil.parse(userAgent);
        loginFormDTO.setClientInfo(JSON.toJSONString(ua));
        TicketInfoBO ticketInfo = ticketService.create(loginFormDTO);
        return successResponse(convertTicketBO2VO(ticketInfo));
    }

    /**
     * 获取ticket信息
     * @param ticketInfo  ticket info
     * @return            ticket info
     */
    @RequestMapping("/info")
    @Authorization(valid = false)
    public BaseResponse<TicketInfoBO> info(@Ticket TicketInfoBO ticketInfo) {
        return successResponse(convertTicketBO2VO(ticketInfo));
    }

    /**
     * 刷新ticket
     * @param ticketInfo ticket info
     * @return           true or false
     */
    @RequestMapping("/refresh")
    @Authorization(valid = false)
    public BaseResponse refresh(@Ticket TicketInfoBO ticketInfo) {
        return baseResponse(ticketService.refresh(ticketInfo.getTicket()));
    }

    /**
     * 销毁ticket
     * @param ticketInfo  ticket info
     * @return            true or false
     */
    @RequestMapping("/destroy")
    @Authorization(valid = false)
    public BaseResponse destroy(@Ticket TicketInfoBO ticketInfo) {
        return baseResponse(ticketService.destroy(ticketInfo.getTicket()));
    }

    /**
     * 分页查询
     * @param request  request parameter
     * @return         ticket page info
     */
    @PostMapping("/page")
    @Authorization("api_rbac_ticket_page")
    public BaseResponse<PageResultDTO<RbacTicketDTO>> page(@Valid @RequestBody BaseRequest<PageQueryDTO<RbacTicketQuery>> request) {
        PageResultDTO<RbacTicketDTO> result = ticketService.page(request.getData());
        return successResponse(result);
    }

    /**
     * 修改当前用户密码
     * @param ticketInfo  ticket info
     * @param request     request parameter
     * @return            true or false
     */
    @PostMapping("/changeCurrentUserPassword")
    @Authorization(valid = false)
    public BaseResponse changeCurrentUserPassword(@Ticket TicketInfoBO ticketInfo,
                                                  @Valid @RequestBody BaseRequest<RbacChangeCurrentUserPasswordRequest> request) {
        Long userId = ticketInfo.getUserDetail().getId();
        boolean result = userService.changeCurrentUserPassword(userId,
                request.getData().getOldPwd(),
                request.getData().getNewPwd());
        // 剔除当前用户所有有效ticket
        if (result) {
            ticketService.kickOutUserTickets(userId);
        }
        return successResponse(result);
    }

    /**
     * 通过ID踢出Ticket
     * @param request  request parameter
     * @return         handle result
     */
    @PostMapping("/kickOutById")
    @Authorization("api_rbac_ticket_kickOutById")
    public BaseResponse kickOutById(@Valid @RequestBody BaseRequest<PrimaryKeyRequest> request) {
        boolean result = ticketService.kickOutTicketById(request.getData().getId());
        return baseResponse(result);
    }

    /**
     * 将凭证信息中部分用户信息脱敏
     * @param ticketInfoBO  ticket info bo
     * @return              ticket info vo
     */
    private TicketInfoVO convertTicketBO2VO(TicketInfoBO ticketInfoBO) {
        return BeanUtil.toBean(ticketInfoBO, TicketInfoVO.class);
    }

    @RequestMapping("/captchaCreate")
    @Authorization(valid = false)
    public BaseResponse<RbacCaptchaDTO> captchaCreate() {
        RbacCaptchaDTO captchaVO = ticketService.captchaCreate();
        return successResponse(captchaVO);
    }

}
