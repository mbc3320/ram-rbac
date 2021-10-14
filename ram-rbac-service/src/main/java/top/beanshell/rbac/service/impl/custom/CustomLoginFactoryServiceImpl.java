package top.beanshell.rbac.service.impl.custom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import top.beanshell.rbac.common.exception.RbacTicketException;
import top.beanshell.rbac.common.exception.code.RbacTicketStatusCode;
import top.beanshell.rbac.common.model.enums.LoginType;
import top.beanshell.rbac.service.custom.CustomLoginFactoryService;
import top.beanshell.rbac.service.custom.CustomLoginService;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

/**
 * 自定义登录工厂实现
 * @author binchao
 */
@Service
public class CustomLoginFactoryServiceImpl implements CustomLoginFactoryService {


    @Resource(name = "normalLoginService")
    private CustomLoginService normalLoginService;

    @Autowired(required = false)
    @Qualifier("wxMpLoginService")
    private CustomLoginService wxMpLoginService;

    @Autowired(required = false)
    @Qualifier("wxMaLoginService")
    private CustomLoginService wxMaLoginService;

    @Autowired(required = false)
    @Qualifier("smsCodeLoginService")
    private CustomLoginService smsCodeLoginService;

    @Autowired(required = false)
    @Qualifier("emailCodeLoginService")
    private CustomLoginService emailCodeLoginService;

    @Autowired(required = false)
    @Qualifier("otherLoginService")
    private CustomLoginService otherLoginService;




    @Override
    public CustomLoginService getLoginService(@NotNull LoginType loginType) {
        Assert.notNull(loginType, "loginType必填");
        switch (loginType) {
            case ACCOUNT:
                return normalLoginService;
            case WX_MP:
                return wxMpLoginService;
            case WX_MA:
                return wxMaLoginService;
            case SMS_CODE:
                return smsCodeLoginService;
            case EMAIL_CODE:
                return emailCodeLoginService;
            case OTHER:
                return otherLoginService;
            default:
                throw new RbacTicketException(RbacTicketStatusCode.LOGIN_TYPE_UN_SUPPORT);
        }
    }
}
