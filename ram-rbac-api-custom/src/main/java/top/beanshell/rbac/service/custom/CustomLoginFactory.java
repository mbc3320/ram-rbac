package top.beanshell.rbac.service.custom;

/**
 * 自定义登录工厂业务接口
 * @author binchao
 */
public interface CustomLoginFactory {

    /**
     * 根据登录方式获取登录服务
     * @return            登录业务服务实例
     */
    CustomLoginService getLoginService();
}
