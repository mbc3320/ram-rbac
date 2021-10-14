package top.beanshell.rbac.service.custom;

import top.beanshell.rbac.common.model.bo.UserDetailBO;
import top.beanshell.rbac.model.bo.RbacSysGlobalConfigBO;
import top.beanshell.rbac.model.dto.UserLoginFormDTO;

/**
 * 自定义登录服务
 * @author binchao
 */
public interface CustomLoginService {

    /**
     * 自定义登录
     * @param loginForm       登录参数
     * @param globalConfig    系统全局设置
     * @return                用户信息
     */
    UserDetailBO login(UserLoginFormDTO loginForm, RbacSysGlobalConfigBO globalConfig);
}
