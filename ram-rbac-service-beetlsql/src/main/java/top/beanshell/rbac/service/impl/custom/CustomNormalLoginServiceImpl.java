package top.beanshell.rbac.service.impl.custom;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import top.beanshell.common.exception.BaseException;
import top.beanshell.rbac.common.exception.RbacUserException;
import top.beanshell.rbac.common.exception.code.RbacUserStatusCode;
import top.beanshell.rbac.common.model.bo.UserDetailBO;
import top.beanshell.rbac.common.model.enums.AccountState;
import top.beanshell.rbac.model.dto.RbacUserDTO;
import top.beanshell.rbac.model.dto.UserLoginFormDTO;
import top.beanshell.rbac.service.RbacRoleService;
import top.beanshell.rbac.service.RbacUserService;
import top.beanshell.rbac.service.custom.CustomLoginService;
import top.beanshell.rbac.util.PasswordStorage;

import javax.annotation.Resource;

/**
 * 普通账号密码登录实现
 * @author binchao
 */
@Slf4j
@Service("normalLoginService")
public class CustomNormalLoginServiceImpl implements CustomLoginService {

    @Resource
    private RbacRoleService roleService;

    @Resource
    private RbacUserService userService;

    @Override
    public UserDetailBO login(UserLoginFormDTO loginForm) {
        RbacUserDTO user = userService.getUserByUniqueKey(loginForm.getAccount());
        if (null == user) {
            throw new RbacUserException(RbacUserStatusCode.USER_IS_NOT_EXIST);
        }

        try {
            if (!PasswordStorage.verifyPassword(loginForm.getAccountAuth(), user.getPassword())) {
                throw new RbacUserException(RbacUserStatusCode.USER_PASSWORD_ERROR);
            }
        } catch (BaseException be) {
            throw be;
        } catch (Exception e) {
            log.error("Password valid error: account = {}", loginForm.getAccount(), e);
        }

        if (!AccountState.NORMAL.equals(user.getAccountState())) {
            throw new RbacUserException(RbacUserStatusCode.USER_STATE_ERROR);
        }

        UserDetailBO userDetailBO = new UserDetailBO();
        BeanUtils.copyProperties(user, userDetailBO);

        // 组装用户角色、权限编码列表
        userDetailBO = userService.getUserRoleAndPermission(userDetailBO);

        return userDetailBO;
    }


}
