package top.beanshell.rbac.interceptor;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import top.beanshell.common.annotation.Authorization;
import top.beanshell.common.exception.BaseException;
import top.beanshell.common.exception.code.GlobalStatusCode;
import top.beanshell.rbac.common.constant.RamRbacConst;
import top.beanshell.rbac.common.model.bo.TicketInfoBO;
import top.beanshell.rbac.common.model.bo.UserDetailBO;
import top.beanshell.rbac.service.RbacTicketService;
import top.beanshell.web.util.IpAddrUtil;
import top.beanshell.web.util.ResponseUtil;
import top.beanshell.web.vo.BaseResponse;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 权限拦截器
 * @author binchao
 */
@Slf4j
@Component
public class PermissionInterceptor extends HandlerInterceptorAdapter {

    @Resource
    private RbacTicketService ticketService;

    private static List<String> WHITE_PATH_LIST = new ArrayList<>();

    public PermissionInterceptor() {
        WHITE_PATH_LIST.add("/error");
    }

    /**
     * 异步刷新token线程
     */
    private static SimpleAsyncTaskExecutor refreshTokenThreads;

    static {
        refreshTokenThreads = new SimpleAsyncTaskExecutor("Refresh-Token-Thread");
        refreshTokenThreads.setConcurrencyLimit(30);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        // 白名单直接通过验证
        if (WHITE_PATH_LIST.contains(request.getRequestURI())) {
            return true;
        }

        if ( handler instanceof HandlerMethod) {
            /*
             * 权限验证合法
             */
            try {
                if ( isVerification(request, handler) ) {
                    return true;
                }
            } catch (BaseException be) {
                ResponseUtil.responseJson(response, new BaseResponse(be.getStatus()));
            } catch (Exception e) {
                log.error("Auth error: {}", e.getMessage(), e);
                ResponseUtil.responseJson(response, new BaseResponse(GlobalStatusCode.SERVER_ERROR));
            }
            /*
             * 无权限访问
             */
            return unauthorizedAccess(response);
        }
        return true;
    }

    /**
     * <p>
     * 判断权限是否合法，支持 1、请求地址 2、注解编码
     * </p>
     * @param request
     * @param handler
     * @return
     */
    protected boolean isVerification( HttpServletRequest request, Object handler) {
        /*
         * 注解权限认证
         */
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        Authorization auth = method.getAnnotation(Authorization.class);
        if ( auth != null ) {
            if (!auth.valid()) {
                /**
                 * 忽略拦截
                 */
                return true;
            } else {
                TicketInfoBO ticketInfo = getTicketInfo(request);
                UserDetailBO userDetail = ticketInfo == null ? null : ticketInfo.getUserDetail();
                if (null != userDetail) {
                    List<String> permissionList = userDetail.getAccessList();
                    if (null != permissionList && permissionList.contains(auth.value())) {
                        return true;
                    }
                    // todo 增加客户端hash校验
                } else {
                    // 用户未登录
                    throw new BaseException(GlobalStatusCode.PERMISSION_DENY);
                }
            }
        }
        /*
         * 非法访问
         */
        return false;
    }


    /**
     *
     * <p>
     * 无权限访问处理，默认返回 403
     * </p>
     *
     * @param response
     * @return
     * @throws Exception
     */
    protected boolean unauthorizedAccess(HttpServletResponse response ) {
        ResponseUtil.responseJson(response, new BaseResponse(GlobalStatusCode.PERMISSION_DENY));
        return false;
    }

    /**
     *  从头信息获取ticket
     * @param request
     * @return
     */
    private TicketInfoBO getTicketInfo(HttpServletRequest request) {
        String ticket = request.getHeader(RamRbacConst.DEFAULT_TICKET_HEADER_KEY);
        if (!StringUtils.hasText(ticket)) {
            ticket = request.getParameter(RamRbacConst.DEFAULT_TICKET_HEADER_KEY);
        }

        if (StringUtils.hasText(ticket)) {
            TicketInfoBO ticketInfo = ticketService.get(ticket);

            String finalTicket = ticket;
            refreshTokenThreads.execute(() -> {
                try {
                    // 暂时默认大于一小时的token才刷新，优化性能
                    if (null != ticketInfo
                            && ticketInfo.getLastRefreshTime() != null
                            && (DateUtil.between(ticketInfo.getLastRefreshTime(), new Date(), DateUnit.MINUTE) > 60)) {
                        ticketService.refresh(finalTicket);
                    }
                } catch (Exception e) {
                    log.warn("Refresh ticket failed: {}", e.getMessage(), e);
                }
            });

            log.info("remoteIp = {}, uri = {}, userId = {}",
                    IpAddrUtil.getRealIp(request), request.getRequestURI(), ticketInfo.getUserDetail().getId());
            return ticketInfo;
        }
        return null;
    }
}
