package top.beanshell.rbac.service.impl.custom;

import org.springframework.stereotype.Service;
import top.beanshell.rbac.service.custom.CustomLoginFactory;
import top.beanshell.rbac.service.custom.CustomLoginService;

import javax.annotation.Resource;

/**
 * 普通登录工厂实现
 * @author binchao
 */
@Service("normalLoginFactory")
public class CustomNormalLoginFactoryImpl implements CustomLoginFactory {


    @Resource(name = "normalLoginService")
    private CustomLoginService normalLoginService;



    @Override
    public CustomLoginService getLoginService() {
        return normalLoginService;
    }
}
