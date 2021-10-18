package top.beanshell.rbac.service.impl.custom;

import org.springframework.stereotype.Service;
import top.beanshell.rbac.service.custom.CustomLoginFactory;
import top.beanshell.rbac.service.custom.CustomLoginService;

import javax.annotation.Resource;

@Service("emailCodeLoginFactory")
public class CustomEmailCodeLoginFactoryImpl implements CustomLoginFactory {

    @Resource(name = CustomEmailCodeLoginServiceImpl.SERVICE_NAME)
    private CustomLoginService loginService;

    @Override
    public CustomLoginService getLoginService() {
        return loginService;
    }

}
