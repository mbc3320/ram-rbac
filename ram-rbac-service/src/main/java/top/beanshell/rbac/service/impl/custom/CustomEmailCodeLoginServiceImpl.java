package top.beanshell.rbac.service.impl.custom;

import org.springframework.stereotype.Service;
import top.beanshell.rbac.common.exception.RbacUserException;
import top.beanshell.rbac.common.exception.code.RbacUserStatusCode;
import top.beanshell.rbac.common.model.bo.UserDetailBO;
import top.beanshell.rbac.model.bo.RbacSysGlobalConfigBO;
import top.beanshell.rbac.model.dto.UserLoginFormDTO;
import top.beanshell.rbac.service.custom.CustomLoginService;

@Service(CustomEmailCodeLoginServiceImpl.SERVICE_NAME)
public class CustomEmailCodeLoginServiceImpl implements CustomLoginService {

    public static final String SERVICE_NAME = "emailCodeLoginService";

    @Override
    public UserDetailBO login(UserLoginFormDTO loginForm, RbacSysGlobalConfigBO globalConfig) {
        throw new RbacUserException(RbacUserStatusCode.LOGIN_TYPE_UNSUPPORTED);
    }
}
