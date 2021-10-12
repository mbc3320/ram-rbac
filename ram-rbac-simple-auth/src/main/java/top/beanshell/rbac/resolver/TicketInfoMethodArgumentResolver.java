package top.beanshell.rbac.resolver;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import top.beanshell.common.annotation.Ticket;
import top.beanshell.rbac.common.constant.RamRbacConst;
import top.beanshell.rbac.common.exception.RbacTicketException;
import top.beanshell.rbac.common.exception.code.RbacTicketStatusCode;
import top.beanshell.rbac.common.model.bo.TicketInfoBO;
import top.beanshell.rbac.service.RbacTicketService;

import javax.annotation.Resource;

/**
 * Ticket解析器
 * @author binchao
 */
@Component
public class TicketInfoMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Resource
    private RbacTicketService ticketService;

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType().isAssignableFrom(TicketInfoBO.class) &&
                methodParameter.hasParameterAnnotation(Ticket.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        String ticket = nativeWebRequest.getHeader(RamRbacConst.DEFAULT_TICKET_HEADER_KEY);
        if (!StringUtils.hasText(ticket)) {
            ticket = nativeWebRequest.getParameter(RamRbacConst.DEFAULT_TICKET_HEADER_KEY);
        }

        if (StringUtils.hasText(ticket)) {
            return ticketService.get(ticket);
        } else {
            throw new RbacTicketException(RbacTicketStatusCode.HEADER_MISSING_TICKET);
        }
    }
}
