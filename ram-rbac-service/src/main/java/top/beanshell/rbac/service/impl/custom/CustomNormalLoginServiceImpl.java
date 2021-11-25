package top.beanshell.rbac.service.impl.custom;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import top.beanshell.captcha.common.exception.CaptchaException;
import top.beanshell.captcha.common.exception.code.CaptchaStatusCode;
import top.beanshell.captcha.model.dto.CaptchaValidDTO;
import top.beanshell.captcha.service.CaptchaBaseService;
import top.beanshell.common.exception.BaseException;
import top.beanshell.common.service.I18nService;
import top.beanshell.rbac.common.exception.RbacConfigException;
import top.beanshell.rbac.common.exception.RbacUserException;
import top.beanshell.rbac.common.exception.code.RbacConfigStatusCode;
import top.beanshell.rbac.common.exception.code.RbacUserStatusCode;
import top.beanshell.rbac.common.model.bo.UserDetailBO;
import top.beanshell.rbac.common.model.enums.AccountState;
import top.beanshell.rbac.model.bo.RbacSysGlobalConfigBO;
import top.beanshell.rbac.model.bo.RbacSysLoginCaptchaMetaBO;
import top.beanshell.rbac.model.dto.RbacUserDTO;
import top.beanshell.rbac.model.dto.UserLoginFormDTO;
import top.beanshell.rbac.service.RbacRoleService;
import top.beanshell.rbac.service.RbacUserService;
import top.beanshell.rbac.service.custom.CustomLoginService;
import top.beanshell.rbac.util.PasswordStorage;

import javax.annotation.Resource;
import java.util.Optional;

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

    @Resource
    private ApplicationContext applicationContext;

    @Resource
    private I18nService i18nService;

    @Override
    public UserDetailBO login(UserLoginFormDTO loginForm, RbacSysGlobalConfigBO globalConfig) {

        //检测图形校验码
        if (globalConfig.getConsoleCaptcha()) {
            Assert.notNull(loginForm.getImgValidCodeId(), i18nService.getMessage("i18n.request.valid.common.required", "captchaId"));
            Assert.notNull(loginForm.getImgValidCodeText(), i18nService.getMessage("i18n.request.valid.common.required", "captchaCode"));
            if (null != globalConfig.getCaptchaMetaList() && globalConfig.getCaptchaMetaList().size() >= 1) {
                try {
                    Optional<RbacSysLoginCaptchaMetaBO> captchaMetaBO = globalConfig.getCaptchaMetaList()
                            .stream().filter(meta -> meta.getEnable()).findFirst();

                    CaptchaBaseService captchaBaseService = applicationContext
                            .getBean(captchaMetaBO.get().getCaptchaServiceName(), CaptchaBaseService.class);

                    CaptchaValidDTO validDTO = CaptchaValidDTO
                            .builder()
                            .id(loginForm.getImgValidCodeId().toString())
                            .code(loginForm.getImgValidCodeText()).build();
                    boolean validResult = captchaBaseService.valid(validDTO);
                    if (!validResult) {
                        throw new CaptchaException(CaptchaStatusCode.CAPTCHA_INPUT_ERROR);
                    }
                } catch (BaseException be) {
                    throw be;
                } catch (Exception e) {
                    log.error("Valid captcha error: {}", e.getMessage(), e);
                    throw new CaptchaException(CaptchaStatusCode.UNSUPPORTED_CAPTCHA_TYPE);
                }

            } else {
                throw new RbacConfigException(RbacConfigStatusCode.GLOBAL_CONFIG_OF_CAPTCHA_ERROR);
            }
        }

        RbacUserDTO user = userService.getUserByUniqueKey(loginForm.getAccount());
        if (null == user) {
            throw new RbacUserException(RbacUserStatusCode.USER_IS_NOT_EXIST);
        }

        // check user state
        if (!AccountState.NORMAL.equals(user.getAccountState())) {
            throw new RbacUserException(RbacUserStatusCode.USER_STATE_ERROR);
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

        UserDetailBO userDetailBO = new UserDetailBO();
        BeanUtils.copyProperties(user, userDetailBO);

        // 组装用户角色、权限编码列表
        userDetailBO = userService.getUserRoleAndPermission(userDetailBO);

        return userDetailBO;
    }


}
