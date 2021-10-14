package top.beanshell.rbac.service.impl;

import cn.hutool.core.bean.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import top.beanshell.common.exception.BaseException;
import top.beanshell.common.exception.code.GlobalStatusCode;
import top.beanshell.common.model.dto.PageQueryDTO;
import top.beanshell.common.model.dto.PageResultDTO;
import top.beanshell.common.service.impl.CRUDServiceImpl;
import top.beanshell.common.utils.IdUtil;
import top.beanshell.rbac.common.exception.RbacUserException;
import top.beanshell.rbac.common.exception.code.RbacUserStatusCode;
import top.beanshell.rbac.common.model.bo.UserDetailBO;
import top.beanshell.rbac.dao.RbacUserDaoService;
import top.beanshell.rbac.model.bo.RbacSysGlobalConfigBO;
import top.beanshell.rbac.model.dto.RbacUserDTO;
import top.beanshell.rbac.model.dto.UserLoginFormDTO;
import top.beanshell.rbac.model.query.RbacUserQuery;
import top.beanshell.rbac.service.RbacConfigService;
import top.beanshell.rbac.service.RbacRoleService;
import top.beanshell.rbac.service.RbacTicketService;
import top.beanshell.rbac.service.RbacUserService;
import top.beanshell.rbac.service.custom.CustomLoginFactoryService;
import top.beanshell.rbac.util.PasswordStorage;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 用户管理业务实现
 * @author binchao
 */
@Slf4j
@Service
public class RbacUserServiceImpl extends CRUDServiceImpl<RbacUserDTO, RbacUserDaoService> implements RbacUserService {

    /**
     * 用户密码错误次数缓存键码
     */
    private static final String PASSWORD_ERROR_COUNT_KEY_FORMAT = "rbac:user:passwordError:%s";

    @Resource
    private RbacRoleService roleService;

    @Resource
    private CustomLoginFactoryService customLoginFactoryService;

    @Resource
    private RbacTicketService ticketService;

    @Resource
    private RbacConfigService configService;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public UserDetailBO login(UserLoginFormDTO loginForm) {

        Integer passwordErrorCount = getUserPasswordErrorCount(loginForm.getAccount());

        if (null != passwordErrorCount && passwordErrorCount >= 5) {
            throw new RbacUserException(RbacUserStatusCode.USER_PASSWORD_TRY_ERROR);
        }

        RbacSysGlobalConfigBO globalConfig = configService.getGlobalConfig();

        // 检测是否受支持的登录方式
        globalConfig.checkLoginType(loginForm.getLoginType());

        try {
            UserDetailBO userDetailBO = customLoginFactoryService.getLoginService(loginForm.getLoginType()).login(loginForm, globalConfig);
            cleanUserPasswordErrorEvent(loginForm.getAccount());
            return userDetailBO;
        } catch (BaseException be) {
            // 记录密码失败次数
            if (RbacUserStatusCode.USER_PASSWORD_ERROR.equals(be.getStatus())) {
                cacheUserPasswordErrorEvent(loginForm.getAccount(), globalConfig);
                throw new RbacUserException(RbacUserStatusCode.USER_PASSWORD_ERROR,
                        String.format("%d小时内还可以尝试%d次",
                                globalConfig.getPasswordErrorExpireTime(),
                                null == passwordErrorCount ? 4 : 4 - passwordErrorCount));
            }

            throw be;
        } catch (Exception e) {
            log.error("user login error: account = {}, loginType = {}, errMsg = {}",
                    loginForm.getAccount(), loginForm.getLoginType(), e.getMessage(), e);
            throw new BaseException(GlobalStatusCode.UNKNOWN_ERROR);
        }
    }

    /**
     * 统计用户密码错误次数
     * @param account
     */
    private void cacheUserPasswordErrorEvent(String account, RbacSysGlobalConfigBO globalConfig) {
        redisTemplate.opsForValue().increment(getPasswordErrorCountKey(account), 1);
        // 默认锁定5小时
        redisTemplate.expire(getPasswordErrorCountKey(account), globalConfig.getPasswordErrorExpireTime(), TimeUnit.HOURS);
    }

    /**
     * 清除账号密码错误次数
     * @param account
     */
    private void cleanUserPasswordErrorEvent(String account) {
        Assert.hasText(account, "account必填");
        redisTemplate.delete(getPasswordErrorCountKey(account));
    }

    /**
     * 获取用户密码错误次数
     * @param account
     */
    private Integer getUserPasswordErrorCount(String account) {
        Assert.hasText(account, "account必填");
        return (Integer) redisTemplate.opsForValue().get(getPasswordErrorCountKey(account));
    }

    /**
     * 获取密码错误次数缓存键
     * @param account
     * @return
     */
    private String getPasswordErrorCountKey(String account) {
        return String.format(PASSWORD_ERROR_COUNT_KEY_FORMAT, account);
    }

    @Override
    public boolean changeCurrentUserPassword(Long userId, String oldPwd, String newPwd) {
        RbacUserDTO rbacUser = daoService.getById(userId);
        try {
            if (null != rbacUser && PasswordStorage.verifyPassword(oldPwd, rbacUser.getPassword())) {
                return changePwd(newPwd, rbacUser);
            } else {
                throw new RbacUserException(RbacUserStatusCode.USER_PASSWORD_ERROR);
            }
        } catch (BaseException be) {
            throw be;
        } catch (Exception e) {
            log.error("change password error: userId = {}, msg = {}", userId, e.getMessage(), e);
        }
        return false;
    }

    /**
     * 修改用户密码
     * @param newPwd
     * @param rbacUser
     * @return
     * @throws PasswordStorage.CannotPerformOperationException
     */
    private boolean changePwd(String newPwd, RbacUserDTO rbacUser) throws PasswordStorage.CannotPerformOperationException {
        String newHashPwd = PasswordStorage.createHash(newPwd);
        RbacUserDTO newRbacUser = new RbacUserDTO();
        newRbacUser.setId(rbacUser.getId());
        newRbacUser.setPassword(newHashPwd);
        newRbacUser.setUpdateTime(new Date());
        return daoService.updateEntityById(newRbacUser);
    }

    @Override
    public PageResultDTO<RbacUserDTO> page(PageQueryDTO<RbacUserQuery> pageQuery) {
        return daoService.page(pageQuery);
    }

    @Override
    public boolean saveEntity(RbacUserDTO dto) {

        if (!checkUserIfAvailable(dto)) {
            throw new RbacUserException(RbacUserStatusCode.USER_EXIST);
        }

        Long userId = dto.getId() == null ? IdUtil.getId() : dto.getId();

        RbacUserDTO rbacUser = new RbacUserDTO();
        BeanUtil.copyProperties(dto, rbacUser, "password");
        rbacUser.setId(userId);
        try {
            String password = PasswordStorage.createHash(dto.getPassword());
            rbacUser.setPassword(password);
            daoService.saveEntity(rbacUser);
            return true;
        } catch (Exception e) {
            log.error("Save user error: {}", e.getMessage(), e);
            throw new RbacUserException(RbacUserStatusCode.REG_FAILED);
        }
    }

    @Override
    public boolean updateEntityById(RbacUserDTO dto) {
        // 仅能更新昵称、帐号状态
        RbacUserDTO rbacUser = new RbacUserDTO();
        rbacUser.setId(dto.getId());
        rbacUser.setNickName(dto.getNickName());
        rbacUser.setAccountState(dto.getAccountState());
        rbacUser.setUpdateTime(new Date());

        return daoService.updateEntityById(rbacUser);
    }

    @Override
    public RbacUserDTO getById(Long id) {
        RbacUserDTO userDTO = super.getById(id);
        // 不输出密码
        userDTO.setPassword(null);
        return userDTO;
    }

    /**
     * 通过主键检测用户是否存在
     * @param userDTO   用户信息
     * @return true: 用户可新增  false: 用户已存在
     */
    @Override
    public boolean checkUserIfAvailable(RbacUserDTO userDTO) {
        return daoService.checkUserIfAvailable(userDTO);
    }

    @Override
    public RbacUserDTO getUserByUniqueKey(String account) {
        return daoService.getUserByUniqueKey(account);
    }

    @Override
    public UserDetailBO getUserRoleAndPermission(UserDetailBO userDetailBO) {

        List<String> roleCodeList = roleService.findUserRoleCode(userDetailBO.getId());

        List<String> permissionCodeList = roleService.findUserPermissionCode(userDetailBO.getId());

        userDetailBO.setRoleList(roleCodeList);
        userDetailBO.setAccessList(permissionCodeList);

        return userDetailBO;
    }

    @Override
    public boolean changeUserPassword(Long userId, String newPwd) {
        RbacUserDTO rbacUser = getById(userId);
        try {
            if (null != rbacUser) {
                return changePwd(newPwd, rbacUser);
            } else {
                throw new RbacUserException(RbacUserStatusCode.USER_PASSWORD_ERROR);
            }
        } catch (BaseException be) {
            throw be;
        } catch (Exception e) {
            log.error("change password error: userId = {}, msg = {}", userId, e.getMessage(), e);
        }
        return false;
    }

    @Override
    public boolean removeById(Long id) {
        boolean removeUserResult = super.removeById(id);
        if (removeUserResult) {
            // 清除角色
            roleService.revokeUserAllRole(id);
            // 注销ticket
            ticketService.kickOutUserTickets(id);
        }
        return removeUserResult;
    }
}
