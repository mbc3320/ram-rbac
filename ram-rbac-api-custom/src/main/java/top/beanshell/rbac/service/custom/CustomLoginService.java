package top.beanshell.rbac.service.custom;

import top.beanshell.rbac.common.model.bo.UserDetailBO;
import top.beanshell.rbac.model.dto.UserLoginFormDTO;

/**
 * 自定义登录服务
 * @author binchao
 */
public interface CustomLoginService {

    /**
     * 自定义登录
     * @param loginForm
     * @return
     */
    UserDetailBO login(UserLoginFormDTO loginForm);
}
