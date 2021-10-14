package top.beanshell.rbac.service.custom;

import top.beanshell.rbac.common.model.enums.LoginType;

/**
 * 自定义登录工厂业务接口
 * @author binchao
 */
public interface CustomLoginFactoryService {

    /**
     * 根据登录方式获取登录服务
     * @param loginType   登录类型
     * @return            登录业务服务实例
     */
    CustomLoginService getLoginService(LoginType loginType);
}
