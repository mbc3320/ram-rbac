package top.beanshell.rbac.service.custom;

import top.beanshell.rbac.common.model.enums.LoginType;

/**
 * 自定义登录工厂业务接口
 * @author binchao
 */
public interface CustomLoginFactoryService {

    /**
     * 根据登录方式获取登录服务
     * @param loginType
     * @return
     */
    CustomLoginService getLoginService(LoginType loginType);
}
