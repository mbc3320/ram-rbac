package top.beanshell.rbac.service.impl;

import cn.hutool.core.bean.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.beetl.sql.core.page.PageResult;
import org.beetl.sql.core.query.LambdaQuery;
import org.beetl.sql.mapper.BaseMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.beanshell.beetlsql.service.impl.CRUDServiceImpl;
import top.beanshell.beetlsql.util.PageUtil;
import top.beanshell.common.exception.BaseException;
import top.beanshell.common.model.dto.PageQueryDTO;
import top.beanshell.common.model.dto.PageResultDTO;
import top.beanshell.common.utils.IdUtil;
import top.beanshell.rbac.common.exception.RbacUserException;
import top.beanshell.rbac.common.exception.code.RbacUserStatusCode;
import top.beanshell.rbac.common.model.bo.UserDetailBO;
import top.beanshell.rbac.mapper.RbacUserMapper;
import top.beanshell.rbac.model.dto.RbacUserDTO;
import top.beanshell.rbac.model.dto.UserLoginFormDTO;
import top.beanshell.rbac.model.pojo.RbacUser;
import top.beanshell.rbac.model.query.RbacUserQuery;
import top.beanshell.rbac.service.RbacRoleService;
import top.beanshell.rbac.service.RbacUserService;
import top.beanshell.rbac.service.custom.CustomLoginFactoryService;
import top.beanshell.rbac.util.PasswordStorage;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 用户管理业务实现
 * @author binchao
 */
@Slf4j
@Service
public class RbacUserServiceImpl extends CRUDServiceImpl<RbacUserDTO, RbacUser> implements RbacUserService {

    @Resource
    private RbacUserMapper userMapper;

    @Resource
    private RbacRoleService roleService;

    @Resource
    private CustomLoginFactoryService customLoginFactoryService;

    @Override
    protected BaseMapper<RbacUser> getDao() {
        return userMapper;
    }

    @Override
    public UserDetailBO login(UserLoginFormDTO loginForm) {

        return customLoginFactoryService.getLoginService(loginForm.getLoginType()).login(loginForm);
    }

    @Override
    public boolean changeCurrentUserPassword(Long userId, String oldPwd, String newPwd) {
        RbacUser rbacUser = userMapper.single(userId);
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
    private boolean changePwd(String newPwd, RbacUser rbacUser) throws PasswordStorage.CannotPerformOperationException {
        String newHashPwd = PasswordStorage.createHash(newPwd);
        RbacUser newRbacUser = new RbacUser();
        newRbacUser.setId(rbacUser.getId());
        newRbacUser.setPassword(newHashPwd);
        newRbacUser.setUpdateTime(new Date());
        return userMapper.updateTemplateById(newRbacUser) == 1;
    }

    @Override
    public PageResultDTO<RbacUserDTO> page(PageQueryDTO<RbacUserQuery> pageQuery) {
        RbacUserQuery params = pageQuery.getParams();
        LambdaQuery<RbacUser> lQuery = createLambdaQuery();
        lQuery.select(RbacUser::getId, RbacUser::getAccount, RbacUser::getAccountState,
                RbacUser::getEmail, RbacUser::getPhoneNumber, RbacUser::getNickName);
        if (null != params) {
            if (StringUtils.hasText(params.getAccount())) {
                lQuery.andLike(RbacUser::getAccount, params.getAccount() + "%");
            }
            if (StringUtils.hasText(params.getEmail())) {
                lQuery.andLike(RbacUser::getEmail, params.getEmail() + "%");
            }
            if (StringUtils.hasText(params.getPhoneNumber())) {
                lQuery.andLike(RbacUser::getPhoneNumber, params.getPhoneNumber() + "%");
            }
            if (null != params.getAccountState()) {
                lQuery.andEq(RbacUser::getAccountState, params.getAccountState());
            }
            if (StringUtils.hasText(params.getNickname())) {
                lQuery.andEq(RbacUser::getNickName, params.getNickname());
            }
        }

        PageResult<RbacUser> page = lQuery.page(pageQuery.getCurrent(), pageQuery.getPageSize());
        return PageUtil.getPageResult(page, RbacUserDTO.class);
    }

    @Override
    public boolean saveEntity(RbacUserDTO dto) {

        if (!checkUserIfAvailable(dto)) {
            throw new RbacUserException(RbacUserStatusCode.USER_EXIST);
        }

        Long userId = dto.getId() == null ? IdUtil.getId() : dto.getId();

        RbacUser rbacUser = new RbacUser();
        BeanUtil.copyProperties(dto, rbacUser, "password");
        rbacUser.init();
        rbacUser.setId(userId);
        try {
            String password = PasswordStorage.createHash(dto.getPassword());
            rbacUser.setPassword(password);
            userMapper.insert(rbacUser);
            return true;
        } catch (Exception e) {
            log.error("Save user error: {}", e.getMessage(), e);
            throw new RbacUserException(RbacUserStatusCode.REG_FAILED);
        }
    }

    @Override
    public boolean updateEntityById(RbacUserDTO dto) {
        // 仅能更新昵称、帐号状态
        RbacUser rbacUser = new RbacUser();
        rbacUser.setId(dto.getId());
        rbacUser.setNickName(dto.getNickName());
        rbacUser.setAccountState(dto.getAccountState());
        rbacUser.setUpdateTime(new Date());

        return userMapper.updateTemplateById(rbacUser) == 1;
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
     * @param userDTO
     * @return true: 用户可新增  false: 用户已存在
     */
    @Override
    public boolean checkUserIfAvailable(RbacUserDTO userDTO) {

        LambdaQuery<RbacUser> userLambdaQuery = createLambdaQuery();

        userLambdaQuery.andEq(RbacUser::getAccount, userDTO.getAccount());

        if (StringUtils.hasText(userDTO.getEmail())) {
            userLambdaQuery.orEq(RbacUser::getEmail, userDTO.getEmail());
        }
        if (StringUtils.hasText(userDTO.getPhoneNumber())) {
            userLambdaQuery.orEq(RbacUser::getPhoneNumber, userDTO.getPhoneNumber());
        }

        return userLambdaQuery.count() == 0;
    }

    @Override
    public RbacUserDTO getUserByUniqueKey(String account) {
        LambdaQuery<RbacUser> query = createLambdaQuery();
        query.andEq(RbacUser::getAccount, account)
                .orEq(RbacUser::getEmail, account)
                .orEq(RbacUser::getPhoneNumber, account);

        RbacUser rbacUser = query.single();
        if (null != rbacUser) {
            return BeanUtil.toBean(rbacUser, RbacUserDTO.class);
        }
        return null;
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
        RbacUser rbacUser = userMapper.single(userId);
        try {
            if (null != rbacUser) {
                return changePwd(newPwd, rbacUser);
            } else {
                throw new RbacUserException(RbacUserStatusCode.USER_PASSWORD_MODIFY_FAILED);
            }
        } catch (BaseException be) {
            throw be;
        } catch (Exception e) {
            log.error("change password error: userId = {}, msg = {}", userId, e.getMessage(), e);
        }
        return false;
    }
}
